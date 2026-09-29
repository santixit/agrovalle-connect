# Publicación en GitHub

El representante debe crear en GitHub un repositorio **público** llamado `agrovalle-connect`, sin README ni `.gitignore` iniciales. Cada integrante configura el nombre y el correo institucional de Git:

```powershell
git config --global user.name "Nombre completo"
git config --global user.email "correo@estudiante.uniajc.edu.co"
```

## Configurar autenticación SSH en Windows

Cada integrante crea su propia clave desde PowerShell. Cuando `ssh-keygen` solicite una frase de contraseña, se recomienda definir una y guardarla de forma segura:

```powershell
ssh-keygen -t ed25519 -C "correo@estudiante.uniajc.edu.co"
```

Inicia el agente SSH y agrega la clave privada al agente local:

```powershell
Start-Service ssh-agent
ssh-add "$env:USERPROFILE\.ssh\id_ed25519"
```

En GitHub, abre **Settings > SSH and GPG keys > New SSH key**. Copia el contenido de `~/.ssh/id_ed25519.pub` y registra esa clave pública en la cuenta correcta. La clave privada `id_ed25519` nunca se copia al repositorio ni se comparte. Verifica la autenticación y cambia el remoto HTTPS existente a SSH:

```powershell
ssh -T git@github.com
git remote set-url origin git@github.com:santixit/agrovalle-connect.git
git remote -v
```

La primera conexión SSH puede pedir confirmar la huella del servidor GitHub; compárala con la [lista oficial de huellas SSH de GitHub](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/githubs-ssh-key-fingerprints) antes de aceptarla. Después de registrar la clave, cada integrante clona el proyecto con la URL SSH y trabaja en una rama `feature/HU-XX-descripcion`.

## Flujo por historia

En GitHub se protege `main` para exigir Pull Request antes de fusionar y una aprobación de otra persona. Para cada historia:

```powershell
git switch main
git pull origin main
git switch -c feature/HU-XX-descripcion
git add .
git commit -m "feat(scope): descripcion breve"
git push -u origin feature/HU-XX-descripcion
```

Luego se crea el Pull Request, se solicita revisión a un compañero y se fusiona solo cuando CI esté en verde. Copia la URL final en el PDF de entrega.
