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
- PR de demostración: [#61](https://github.com/santixit/agrovalle-connect/pull/61), abierto contra `main`. GitHub marcó el check de CI como requerido y el merge quedó deshabilitado. DannyGomez02 aprobó el PR y lo fusionó; la protección de rama se mantuvo activa.
- Corrección local: se restauró la expectativa `1900.01`. `mvnw --batch-mode --no-transfer-progress clean verify` terminó con 51 pruebas aprobadas, 0 violaciones Checkstyle y todos los umbrales JaCoCo cumplidos.
- CI verde: el check de Pull Request [#94](https://github.com/santixit/agrovalle-connect/actions/runs/40873938802) pasó en 1m51s. El evento `push` [#93](https://github.com/santixit/agrovalle-connect/actions/runs/40873934154) tuvo un primer intento con fallo al iniciar el servidor Playwright; el segundo intento pasó en 2m11s con el mismo commit `86cba9a`. Ambos generaron artefactos JaCoCo.
- Revisión: DannyGomez02 aprobó el PR #61. El merge se completó el 29 de octubre de 2026 en `main`, commit `59e3256`.
- CI final de `main`: [Actions #97](https://github.com/santixit/agrovalle-connect/actions/runs/40875809295) terminó en `Success` para `59e3256`; pasaron Build, tests, Checkstyle, JaCoCo y el despliegue a staging. El run publicó el [artefacto jacoco-coverage-report](https://github.com/santixit/agrovalle-connect/actions/runs/40875809295/artifacts/12897992950).

## Evidencia actual para la entrega del Segundo Corte

Después de los cambios integrados por los PR #62 y #63, la rama `main` avanzó al commit `a7e2097`. La ejecución [Actions #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070) pasó el 29 de octubre de 2026: Build, pruebas, Checkstyle, JaCoCo, E2E y despliegue a staging terminaron correctamente.

El [artefacto JaCoCo de #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/artifacts/12906676338) corresponde al mismo commit. Su HTML informa 88% de instrucciones, 63% de ramas y 90% de líneas. En el [log del paso Maven](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/job/123519248186#step:5:169), JaCoCo indica que todos los controles de cobertura se cumplieron y Maven termina en `BUILD SUCCESS`.

La captura `ci-run-main-a7e2097.png`, el reporte descargado y el extracto del log respaldan la versión local del PDF de entrega. Las cifras del reporte #83 y la captura del run #97 quedan como evidencia histórica; ya no se usan como prueba del estado actual de `main`.

El ejercicio de Sesión 10 está completado: se comprobó el fallo, se corrigió, pasó la revisión por pares y el cambio quedó integrado con CI verde.

## Protección de `main`

Al abrir el PR #61, GitHub indicó que hacía falta una aprobación antes del merge y clasificó el job `Build, tests, Checkstyle and JaCoCo` como requerido. El bloqueo se comprobó mientras el check fallido estaba presente. Luego se recibió la aprobación de un compañero, el PR se fusionó y el check del commit final de `main` pasó en Actions #97. Para la entrega actual, el último push a `main` documentado aquí es `a7e2097`, validado en Actions #103.
