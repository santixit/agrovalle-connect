# Revisión técnica - CD a Staging y ADRs

**Fecha de preparación:** 2026-11-12
**Alcance:** configuración versionada para CD y arquitectura evolutiva.

## Entregables del repositorio

- Perfil Spring `staging` en `src/main/resources/application-staging.yml`, que requiere las
  variables de conexión y JWT configuradas en el servicio PaaS.
- Workflow `.github/workflows/cd.yml`, que solicita el despliegue solo después de un CI exitoso
  en `main` y requiere el secreto `DEPLOY_HOOK_URL`.
- Decisión de arquitectura en
  [`ADR-001-despliegue-staging-paas.md`](architecture-decisions/ADR-001-despliegue-staging-paas.md).
- Instrucciones operativas en [`deploy-staging.md`](deploy-staging.md).

## Verificación realizada

- `mvnw.cmd --batch-mode --no-transfer-progress clean verify` pasó con Java Temurin 17.0.21:
  69 pruebas sin fallos, Checkstyle sin violaciones y todas las reglas de cobertura JaCoCo
  satisfechas.
- GitHub Actions CI pasó en la rama de trabajo: [ejecución 43233986589](https://github.com/santixit/agrovalle-connect/actions/runs/43233986589).

## Verificaciones pendientes

- La consulta a `GET https://agrovalle-connect-staging.onrender.com/api/v1/health` agotó el
  tiempo de espera sin respuesta. No se registra como HTTP 200.
- El pipeline de CD se ejecutará cuando los cambios se integren en `main` y esté configurado
  `DEPLOY_HOOK_URL`.
- La configuración de `SPRING_PROFILES_ACTIVE`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` y
  `JWT_SECRET` debe comprobarse en el servicio Render. No se almacena una captura o registro
  HTTP de éxito hasta observar una respuesta real.

## Comprobación en vivo reproducible

Cuando termine el despliegue, consulta `GET /api/v1/health` en la URL pública. La respuesta
implementada por la API es `{"service":"agrovalle-connect","status":"UP"}` con HTTP 200.
La prueba de escritura de registro de agricultor de la guía no se ejecutó, porque crearía datos
persistentes en el entorno público.
