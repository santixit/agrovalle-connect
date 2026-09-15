# Publicacion en GitHub

El representante debe crear en GitHub un repositorio **publico** llamado `agrovalle-connect`, sin README ni .gitignore iniciales. Desde la carpeta del proyecto, ejecuta:

```bash
git remote add origin https://github.com/USUARIO_O_ORGANIZACION/agrovalle-connect.git
git push -u origin main
```

Despues, en GitHub se recomienda proteger `main`: exigir Pull Request antes de fusionar y una aprobacion. Cada integrante clona el proyecto, configura su nombre y correo institucional, y trabaja en una rama `feature/HU-XX-descripcion`.

Para cada historia:

```bash
git switch main
git pull origin main
git switch -c feature/HU-XX-descripcion
git add .
git commit -m "feat(scope): descripcion breve"
git push -u origin feature/HU-XX-descripcion
```

Luego crea el Pull Request, solicita revision de un companero y fusiona solo cuando CI este en verde. Copia la URL final en el PDF de entrega.

