# Integración continua — Sesión 10

## Pipeline del repositorio

El archivo [`.github/workflows/ci.yml`](../../.github/workflows/ci.yml) ya define el pipeline usado por AgroValle Connect. Se activa en `push` a `main` y `feature/**`, en Pull Requests hacia `main` y manualmente. El job corre en Ubuntu, levanta PostgreSQL 16 y configura Temurin Java 17.

El comando `mvn --batch-mode --no-transfer-progress clean verify` ejecuta JUnit 5 y los dos controles estáticos/de cobertura configurados en `pom.xml`:

1. **Pruebas:** un test fallido hace fallar el job.
2. **Checkstyle:** corre en la fase `verify` y falla ante una violación.
3. **JaCoCo:** corre en `verify` y exige al menos 60% de instrucciones, ramas y líneas.

Después, el mismo job instala Node.js/Playwright y ejecuta el flujo crítico de navegador. El despliegue a staging solo se habilita después de que la calidad pase en un `push` a `main`.

## Ejercicio de fallo y corrección

El 29 de octubre de 2026 se cambió temporalmente la expectativa de una prueba de descuento de `1900.01` a `1800.01` en el commit `78362cf`. La prueba detectó la discrepancia: esperaba `1800.01` y recibió `1900.01`.

- Ejecución roja: [GitHub Actions #91](https://github.com/santixit/agrovalle-connect/actions/runs/40873499803), commit `78362cf`, rama `feature/sesion10-ci`. El job falló en Maven con 51 pruebas, 1 fallo y 0 errores; Checkstyle, JaCoCo y E2E no se ejecutaron porque `clean verify` terminó en el test fallido.
- PR de demostración: [#61](https://github.com/santixit/agrovalle-connect/pull/61), abierto contra `main`. GitHub marcó el check de CI como requerido y el merge quedó deshabilitado. La regla también exige al menos una aprobación, por lo que el autor no puede completar el merge.

La aserción temporal se restauró a `1900.01` en esta misma rama. La verificación CI verde de la corrección y el estado de revisión por pares se registrarán cuando estén disponibles. No se considera completado el merge hasta que un compañero del equipo apruebe el PR.

## Protección de `main`

Al abrir el PR #61, GitHub indicó que hace falta una aprobación antes del merge y clasificó el job `Build, tests, Checkstyle and JaCoCo` como requerido. El bloqueo se comprobó en la vista del PR mientras el check fallido estaba presente.
