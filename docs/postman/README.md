# Colecciones de Postman

La colección [`agrovalle-sprint-1.postman_collection.json`](agrovalle-sprint-1.postman_collection.json) contiene los recorridos iniciales de HU-01, HU-07 y HU-04, además de solicitudes autenticadas para los flujos del integrador.

## Preparar la prueba local

1. En la raíz del repositorio, inicia PostgreSQL con `docker compose up -d db` y espera a que Spring Boot arranque y Flyway aplique las migraciones V1–V7.
2. Para que HU-04 devuelva resultados contrastables, carga los datos sintéticos **solo en la base local de demostración**:

   ```powershell
   docker compose cp scripts/sprint-1-demo-data.sql db:/tmp/sprint-1-demo-data.sql
   docker compose exec db psql -U agrovalle -d agrovalle_connect -f /tmp/sprint-1-demo-data.sql
   ```

   El script usa la estructura vigente, en la que `producto_ubicacion` guarda el municipio desde Flyway V7. Es idempotente para las ofertas de demostración y asegura que existan tres ofertas, incluida Mango de Dagua.

3. En Postman, importa el archivo JSON y abre las variables de la colección:
   - `baseUrl`: `http://localhost:8080`.
   - `testCedula`: reemplázala por un identificador sintético único, por ejemplo `DEMO-SPRINT1-20261119-01`. No uses una cédula real; la restricción UNIQUE impide repetir el mismo valor.
   - `farmerId`: se completa automáticamente al ejecutar HU-01; por eso inicia vacío y evita consultar por error un registro ajeno.
   - `accessToken`: queda vacío. Solo se usa en las solicitudes autenticadas de las carpetas adicionales; obtén un token para una cuenta sintética con `/api/v1/auth/login` y úsalo en la sesión local de Postman. No lo guardes en Git ni en capturas.

## Orden de ejecución

1. `HU-01 - Registrar agricultor`: espera HTTP 201; la prueba guarda el `id` en `farmerId` y comprueba que la respuesta no exponga la cédula.
2. `HU-07 - Consultar perfil`: espera HTTP 200 y comprueba nombre y ubicación usando el ID anterior.
3. `HU-04 - Filtrar frutas de Dagua`: espera HTTP 200 y una lista. Con el script de ejemplo debe aparecer Mango de Dagua; las otras ofertas no coinciden con ambos filtros.

La colección incluye pruebas JavaScript en Postman. Revisa `Test Results` después de cada solicitud y registra las capturas únicamente en la carpeta del sprint correspondiente. Los datos del ejemplo son ficticios; nunca apuntes esta colección a producción.
