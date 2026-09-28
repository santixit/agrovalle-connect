# Despliegue a staging

GitHub Actions ejecuta `mvn clean verify` con Java 17 y PostgreSQL 16 en cada push de `main`, rama feature y Pull Request hacia `main`. Despues de un push exitoso a `main`, el job `Deploy to staging` invoca un webhook del proveedor configurado para el servicio de staging.

## Configuracion requerida en GitHub

1. Crear un servicio de staging conectado al repositorio y configurado para construir el `Dockerfile` en la rama `main`.
2. En **Settings > Secrets and variables > Actions**, guardar la URL privada del webhook como secreto `STAGING_DEPLOY_HOOK`.
3. En el servicio, configurar `DB_URL`, `DB_USERNAME` y `DB_PASSWORD` para su base PostgreSQL administrada. No reutilizar las credenciales de desarrollo del Compose.
4. La primera vez, desplegar y comprobar manualmente que la aplicacion queda disponible. Despues, cada push exitoso a `main` solicita el despliegue automaticamente.

Si el secreto `STAGING_DEPLOY_HOOK` no existe, GitHub Actions muestra que el paso se omitio; el build y las pruebas siguen ejecutandose. En ese estado el despliegue automatico aun no esta activado. La guia no proporciona un proveedor, una URL de staging ni credenciales, por lo que esos datos deben venir del equipo y no se inventan ni se guardan en Git.

El webhook confirma que el proveedor recibio la solicitud, no que el servicio ya haya terminado de iniciar. El equipo debe verificar la URL publica de staging y una consulta real a PostgreSQL despues de cada despliegue.
