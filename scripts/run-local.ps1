param(
  [string]$DbUrl = 'jdbc:postgresql://localhost:5432/agrovalle_connect',
  [string]$DbUsername = 'agrovalle'
)

$ErrorActionPreference = 'Stop'
$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
Set-Location $repoRoot

# Neon env pull writes PostgreSQL URLs into .env.local. Read them without
# displaying values, and prefer the direct URL because Flyway runs migrations
# when Spring Boot starts.
$neonDbPassword = $null
$usingNeonEnv = $false
try {
  if (-not $PSBoundParameters.ContainsKey('DbUrl') -and -not $PSBoundParameters.ContainsKey('DbUsername')) {
    $envFile = Join-Path $repoRoot '.env.local'
    if (Test-Path -LiteralPath $envFile) {
      $neonValues = @{}
      foreach ($line in [System.IO.File]::ReadLines($envFile)) {
        $match = [regex]::Match($line, '^\s*(?:export\s+)?([A-Za-z_][A-Za-z0-9_]*)\s*=\s*(.*?)\s*$')
        if ($match.Success) {
          $key = $match.Groups[1].Value
          if ($key -notin @('DATABASE_URL_UNPOOLED', 'DATABASE_URL')) {
            continue
          }
          $value = $match.Groups[2].Value
          if ($value.Length -ge 2 -and
            (($value[0] -eq '"' -and $value[$value.Length - 1] -eq '"') -or
             ($value[0] -eq "'" -and $value[$value.Length - 1] -eq "'"))) {
            $value = $value.Substring(1, $value.Length - 2)
          }
          $neonValues[$key] = $value
        }
      }

      $neonConnectionString = $null
      foreach ($key in @('DATABASE_URL_UNPOOLED', 'DATABASE_URL')) {
        if ($neonValues.ContainsKey($key) -and -not [string]::IsNullOrWhiteSpace($neonValues[$key])) {
          $neonConnectionString = $neonValues[$key]
          break
        }
      }

      if ($neonConnectionString) {
        $connectionUri = $null
        if (-not [Uri]::TryCreate($neonConnectionString, [UriKind]::Absolute, [ref]$connectionUri) -or
          $connectionUri.Scheme -notin @('postgres', 'postgresql')) {
          throw 'DATABASE_URL de .env.local no tiene un formato PostgreSQL válido. No se mostraron credenciales.'
        }

        $separatorIndex = $connectionUri.UserInfo.IndexOf(':')
        $databaseName = [Uri]::UnescapeDataString($connectionUri.AbsolutePath.TrimStart('/'))
        if ($separatorIndex -lt 1 -or -not $databaseName) {
          throw 'DATABASE_URL de .env.local debe incluir usuario, contraseña y base de datos. No se mostraron credenciales.'
        }

        $DbUsername = [Uri]::UnescapeDataString($connectionUri.UserInfo.Substring(0, $separatorIndex))
        $neonDbPassword = [Uri]::UnescapeDataString($connectionUri.UserInfo.Substring($separatorIndex + 1))
        $databasePort = $connectionUri.Port
        if ($databasePort -lt 1) {
          $databasePort = 5432
        }
        $DbUrl = "jdbc:postgresql://$($connectionUri.Host):$databasePort/$databaseName?sslmode=require"
        $usingNeonEnv = $true
      }
    }
  }
  Remove-Variable line, match, key, value, neonValues, neonConnectionString, connectionUri -ErrorAction SilentlyContinue
} catch {
  Remove-Variable neonDbPassword, line, match, key, value, neonValues, neonConnectionString, connectionUri -ErrorAction SilentlyContinue
  throw
}

$java17Home = $null
$jdkCandidates = @()
if ($env:JAVA_HOME) {
  $jdkCandidates += $env:JAVA_HOME
}
$temporaryJdk = Join-Path $env:TEMP 'agrovalle-temurin17-17.0.20.1\jdk-17.0.20.1+1'
$jdkCandidates += $temporaryJdk

foreach ($candidate in ($jdkCandidates | Select-Object -Unique)) {
  $javaExe = Join-Path $candidate 'bin\java.exe'
  if (Test-Path $javaExe) {
    $savedErrorActionPreference = $ErrorActionPreference
    $ErrorActionPreference = 'Continue'
    try {
      $versionOutput = (& $javaExe -version 2>&1 | Out-String)
    } finally {
      $ErrorActionPreference = $savedErrorActionPreference
    }
    if ($versionOutput -match 'version "17(?:\.|"|\+)') {
      $java17Home = $candidate
      break
    }
  }
}

if (-not $java17Home) {
  $javaCommand = Get-Command java -ErrorAction SilentlyContinue
  if ($javaCommand) {
    $savedErrorActionPreference = $ErrorActionPreference
    $ErrorActionPreference = 'Continue'
    try {
      $versionOutput = (& $javaCommand.Source -version 2>&1 | Out-String)
    } finally {
      $ErrorActionPreference = $savedErrorActionPreference
    }
    if ($versionOutput -match 'version "17(?:\.|"|\+)') {
      $java17Home = Split-Path (Split-Path $javaCommand.Source -Parent) -Parent
    }
  }
}

if (-not $java17Home) {
  Remove-Variable neonDbPassword -ErrorAction SilentlyContinue
  throw 'No se encontró Java 17. Instala JDK 17 o define JAVA_HOME antes de ejecutar este script.'
}

$env:JAVA_HOME = $java17Home
$env:Path = "$java17Home\bin;$env:Path"
$env:DB_URL = $DbUrl
$env:DB_USERNAME = $DbUsername
if ($usingNeonEnv) {
  $env:DB_PASSWORD = $neonDbPassword
  Write-Host 'Conexión Neon cargada desde .env.local; credenciales ocultas.'
} else {
  $env:DB_PASSWORD = [System.Net.NetworkCredential]::new(
    '', (Read-Host 'Contraseña del usuario PostgreSQL' -AsSecureString)
  ).Password
}
$jwtBytes = New-Object byte[] 32
$rng = [Security.Cryptography.RandomNumberGenerator]::Create()
try {
  $rng.GetBytes($jwtBytes)
  $env:JWT_SECRET = [Convert]::ToBase64String($jwtBytes)
} finally {
  $rng.Dispose()
}
Remove-Variable jwtBytes

try {
  Write-Host 'Iniciando AgroValle Connect con Java 17 y la base configurada.'
  & .\mvnw.cmd --no-transfer-progress spring-boot:run
  if ($LASTEXITCODE -ne 0) {
    throw "Spring Boot terminó con el código $LASTEXITCODE. Revisa los mensajes anteriores."
  }
} finally {
  Remove-Item Env:\DB_PASSWORD -ErrorAction SilentlyContinue
  Remove-Item Env:\JWT_SECRET -ErrorAction SilentlyContinue
  Remove-Variable neonDbPassword -ErrorAction SilentlyContinue
}
