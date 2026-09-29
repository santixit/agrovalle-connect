param(
  [string]$DbUrl = 'jdbc:postgresql://localhost:5432/agrovalle_connect',
  [string]$DbUsername = 'agrovalle'
)

$ErrorActionPreference = 'Stop'
$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
Set-Location $repoRoot

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
  throw 'No se encontró Java 17. Instala JDK 17 o define JAVA_HOME antes de ejecutar este script.'
}

$env:JAVA_HOME = $java17Home
$env:Path = "$java17Home\bin;$env:Path"
$env:DB_URL = $DbUrl
$env:DB_USERNAME = $DbUsername
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new(
  '', (Read-Host 'Contraseña del usuario PostgreSQL' -AsSecureString)
).Password
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
  Write-Host "Iniciando AgroValle Connect con Java 17 y $DbUrl"
  & .\mvnw.cmd --no-transfer-progress spring-boot:run
  if ($LASTEXITCODE -ne 0) {
    throw "Spring Boot terminó con el código $LASTEXITCODE. Revisa los mensajes anteriores."
  }
} finally {
  Remove-Item Env:\DB_PASSWORD -ErrorAction SilentlyContinue
  Remove-Item Env:\JWT_SECRET -ErrorAction SilentlyContinue
}
