# Base de datos de staging y despliegue

La aplicación pública de staging se ejecuta en [Render](https://agrovalle-connect-staging.onrender.com/). Esta guía prepara una base PostgreSQL separada llamada `agrovalle_staging` y conecta el despliegue a ella. Neon es la ruta documentada aquí; si el equipo elige otro proveedor PostgreSQL, debe conservar la misma configuración JDBC y las variables indicadas.

## Cómo se crea el esquema

La aplicación usa Flyway como única fuente ejecutable del esquema. Al iniciar Spring Boot, Flyway aplica en orden las migraciones `V1` a `V7`; Hibernate después valida el esquema. `docs/diseno/schema.sql` es un snapshot consolidado de referencia y su encabezado dice que es de solo lectura. **No ejecutes `schema.sql` en la base nueva**: duplicaría las tablas que Flyway crea y la aplicación no podría iniciar correctamente.

El flujo queda así:

```text
GitHub Actions --solicita despliegue--> Render (Spring Boot)
                                           |
                                           | DB_URL + DB_USERNAME + DB_PASSWORD
                                           v
                                  Neon: agrovalle_staging
                                           |
                                           | Flyway aplica V1–V7 al iniciar
                                           v
                        Actions espera /api/v1/health y valida la BD
```

## 1. Crear la base en Neon

1. Entra al proyecto del equipo en Neon y abre **Databases**.
2. Selecciona **New Database** y escribe exactamente `agrovalle_staging`.
3. Conserva el usuario/rol y la contraseña que el proyecto de Neon asigna. Si creas un usuario nuevo, debe poder crear objetos porque Flyway necesita aplicar las migraciones la primera vez.
4. En **Connect**, copia los datos de conexión del endpoint de escritura de esa base. Para las migraciones usa la conexión directa, no un endpoint de solo lectura.
5. Completa los valores en el formato que espera esta aplicación:

   ```text
   DB_URL=jdbc:postgresql://HOST:5432/agrovalle_staging?sslmode=require
   DB_USERNAME=USUARIO_DE_NEON
   DB_PASSWORD=CONTRASEÑA_DE_NEON
   ```

   `HOST` debe ser el hostname que muestra Neon. Conserva `sslmode=require`. No pegues la contraseña en este documento ni en Git. Si el panel muestra una URI `postgresql://...` en vez de una URL JDBC, usa su host, base, usuario y contraseña para formar las tres variables anteriores; no copies `postgresql://...` sin convertirla a `jdbc:postgresql://...`.

La base debe quedar vacía antes del primer despliegue. No importes registros de desarrollo o producción.

## 2. Conectar Render con Neon

1. Abre el servicio web `agrovalle-connect-staging` en Render.
2. Ve a **Environment** y agrega o actualiza estas tres variables con los valores del paso anterior:

   | Variable | Valor |
   | --- | --- |
   | `DB_URL` | JDBC URL de Neon para `agrovalle_staging`, con `sslmode=require` |
   | `DB_USERNAME` | Rol de Neon |
   | `DB_PASSWORD` | Contraseña de ese rol |

3. Guarda los cambios. Si Render no inicia un despliegue automáticamente, usa **Manual Deploy** para desplegar la rama `main`.
4. Espera a que el servicio termine de iniciar. El inicio correcto también confirma que Spring Boot pudo conectarse y Flyway aplicó las migraciones.

La URL del secreto de GitHub **no configura Render**. Render necesita sus propias variables de entorno; el webhook de Actions solo solicita un despliegue.

## 3. Guardar los secretos de GitHub Actions

El workflow usa el entorno `staging` para agrupar el despliegue. En GitHub abre **Settings → Secrets and variables → Actions → Repository secrets** (la página que muestra los secretos del repositorio) y agrega o verifica estos nombres:

| Secreto | Contenido |
| --- | --- |
| `STAGING_DEPLOY_HOOK` | Webhook privado del servicio de Render, si aún no está configurado |
| `STAGING_DB_URL` | La misma JDBC URL usada en Render, sin usuario ni contraseña |
| `STAGING_DB_USER` | Rol de Neon |
| `STAGING_DB_PASSWORD` | Contraseña de ese rol |

La URL, el usuario y la contraseña se entregan al workflow como variables de entorno; el workflow no los imprime. El repositorio guarda el usuario bajo `STAGING_DB_USER`; el workflow lo mapea internamente a `STAGING_DB_USERNAME` para la prueba JDBC. Los dos secretos de usuario y contraseña son necesarios porque pgJDBC recibe URL, usuario y contraseña por separado.

## 4. Despliegue y verificación automática

Al integrarse un cambio en `main`, el workflow:

1. Ejecuta build, pruebas, Checkstyle, JaCoCo y E2E contra la base temporal de CI.
2. Si `STAGING_DEPLOY_HOOK` está configurado, solicita el despliegue de Render.
3. Espera hasta seis minutos a que responda `https://agrovalle-connect-staging.onrender.com/api/v1/health` con HTTP 200. La aplicación solo llega a readiness si completó el inicio, incluida la conexión y la validación de Flyway.
4. Conecta a la base indicada por `STAGING_DB_URL` y reintenta la comprobación mientras termina el despliegue. Verifica que el nombre sea `agrovalle_staging`, que la última migración exitosa sea V7 y que existan `agricultores`, `productos`, `fincas` y `parcelas`.

El job `Verify staging database` debe quedar en verde. Si hay webhook pero faltan `STAGING_DB_URL`, `STAGING_DB_USER` o `STAGING_DB_PASSWORD`, la verificación falla con los nombres faltantes, sin mostrar sus valores.

## 5. Comprobación manual opcional

Después del primer despliegue, abre el SQL Editor de Neon y ejecuta consultas de solo lectura:

```sql
SELECT current_database();

SELECT version, description, success
FROM flyway_schema_history
ORDER BY installed_rank;

SELECT tablename
FROM pg_catalog.pg_tables
WHERE schemaname = 'public'
ORDER BY tablename;
```

Se espera que la primera consulta devuelva `agrovalle_staging`, que la última fila de Flyway sea la versión `7` con `success = true`, y que el listado incluya `agricultores`, `productos`, `fincas` y `parcelas`.

Si el primer despliegue aún no se ha iniciado, es normal que `flyway_schema_history` no exista todavía. Inicia la aplicación desde Render; Flyway crea y llena esa tabla al aplicar las migraciones.

## Problemas comunes

- **`28P01` / password authentication failed:** confirma que `DB_USERNAME` y `DB_PASSWORD` pertenecen al mismo rol y proyecto de Neon; actualiza el valor en Render y en los tres secretos `STAGING_DB_*`.
- **Timeout o conexión rechazada:** revisa el hostname, que el endpoint esté activo, que la base sea `agrovalle_staging` y que el JDBC URL conserve `sslmode=require`.
- **La aplicación falla durante Flyway:** revisa los logs de Render. No ejecutes el snapshot `docs/diseno/schema.sql`; verifica que la base esté vacía y que el rol pueda crear y modificar tablas.
- **El despliegue aparece omitido:** comprueba que `STAGING_DEPLOY_HOOK` esté guardado en Repository secrets o en Environment secrets del entorno `staging`.

Referencias del repositorio: [migraciones Flyway](../src/main/resources/db/migration/), [snapshot de referencia](diseno/schema.sql), [workflow de CI](../.github/workflows/ci.yml) y [workflow reutilizable de CD](../.github/workflows/cd.yml). Referencias oficiales: [formato y conexión JDBC de pgJDBC](https://jdbc.postgresql.org/documentation/use/), [uso de secretos en GitHub Actions](https://docs.github.com/en/actions/how-tos/write-workflows/choose-what-workflows-do/use-secrets), [webhooks de despliegue de Render](https://render.com/docs/deploy-hooks).
