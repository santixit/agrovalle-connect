# Despliegue a staging

La instancia actual de staging está disponible en [AgroValle Connect en Render](https://agrovalle-connect-staging.onrender.com/). Puede tardar en responder después de un periodo de inactividad.

GitHub Actions ejecuta `mvn clean verify` con Java 17 y PostgreSQL 16, además de las pruebas E2E, en cada push de `main`, rama feature y Pull Request hacia `main`. El workflow separado `cd.yml` espera a que `Continuous Integration` termine exitosamente para la rama `main` y luego invoca el webhook de Render.

## Configuración requerida

1. Configura el servicio de Render para construir el `Dockerfile` desde la rama `main`.
2. En **Settings > Secrets and variables > Actions**, guarda la URL privada del webhook como secreto `DEPLOY_HOOK_URL`. El workflow también acepta el nombre existente `STAGING_DEPLOY_HOOK`.
3. En el servicio de Render, define `SPRING_PROFILES_ACTIVE=staging`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` y `JWT_SECRET`. Usa las credenciales de la base PostgreSQL administrada y una clave JWT aleatoria de al menos 32 bytes codificada en Base64. No reutilices credenciales de desarrollo.
4. Para el primer despliegue, comprueba manualmente que la aplicación queda disponible. Después, cada CI exitoso en `main` solicita el despliegue automáticamente.

El workflow falla con un mensaje claro si `DEPLOY_HOOK_URL` y `STAGING_DEPLOY_HOOK` no están configurados, en vez de marcar como exitoso un despliegue omitido. Las credenciales PostgreSQL y la clave JWT son variables de runtime de Render: GitHub Actions no las necesita porque el webhook solo solicita al servicio que se despliegue. No dupliques esos secretos en GitHub. Ninguno de estos valores debe guardarse en Git.

El webhook confirma que el proveedor recibió la solicitud, no que la aplicación ya haya terminado de iniciar. Verifica `GET /api/v1/health` en la URL pública después de cada despliegue. Este endpoint es el contrato implementado por el proyecto; el ejemplo `/api/v1/estado` de la guía del laboratorio no existe en esta API.
