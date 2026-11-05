# Índice de evidencias

## Kanban y flujo

- [Evidencia Kanban de Sesión 7](kanban-metricas-sesion7.md): snapshots disponibles, interpretación del WIP, estado de cada métrica y límites de los datos.
- [Lectura del Burn up y datos para la CFD](kanban-burnup-sesion7.md): consulta visual de Insights del 2026-11-05, diferencia entre Burn up y CFD, discrepancia de conteos y datos que todavía faltan.
- [Snapshots por columna](kanban-snapshots-sesion7.csv): dos observaciones históricas, del 17 y 19 de octubre de 2026.
- [Plantilla de periodos](kanban-periodos-sesion7.csv) y [plantilla de eventos por tarjeta](kanban-items-sesion7.csv): vacías hasta contar con evidencia verificable.
- [Políticas Kanban y métricas](../kanban-policies.md): reglas operativas y definiciones; no confundirlas con evidencia de actividad ya ocurrida.
- [Calculador reproducible](../../scripts/kanban_metrics.py): usa los periodos y eventos por tarjeta; no deduce métricas a partir de conteos.

## Sprint 1 y calidad

- [Revisión funcional del Sprint 1](../sprint-1-review.md).
- [Pirámide de pruebas de Sesión 8](piramide-pruebas-sesion8.md): conteo 22/23/1, comparación con 70/20/10 y cobertura JaCoCo de Actions #103; se declara que la proporción objetivo sigue pendiente.
- [Evidencia de historias y BDD](comparacion-historias-backlog-sprint1.md).
- [Colección de capturas Postman](sprint-1/).

## Prácticas y patrones

- [Evidencia SOLID de Sesión 7 y Sprint 2](solid-sesion7.md): refactor antes/después de `ProductoService` y mapeo acotado de los cinco principios.
- [Evidencia TDD del descuento mayorista](tdd-descuento-mayorista.md).

## CI y cobertura

- [Integración continua de Sesión 10](integracion-continua-sesion10.md): workflow y quality gates, simulación de fallo, corrección, aprobación y merge del PR #61; también enlaza la ejecución actual #103.
- [Demostración técnica complementaria de staging (2026-10-29)](demo-staging-sprint2-2026-10-29.md): página, salud y catálogo verificados con solicitudes GET; incluye límites y guion reproducible.
- [GitHub Actions #103 en `main`](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070): Success para el commit `a7e2097`; incluye el job de calidad, despliegue a staging y [artefacto JaCoCo del mismo run](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/artifacts/12906676338). La [captura visual #103](ci-run-main-a7e2097.png) corresponde al mismo run y commit.
- [Cobertura JaCoCo #103](../entrega-segundo-corte/jacoco-main-a7e2097/index.html): 88% instrucciones, 63% ramas y 90% líneas; el [log de verificación](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/job/123519248186#step:5:169) termina con `All coverage checks have been met` y `BUILD SUCCESS`.
- [Captura del reporte HTML JaCoCo #103](jacoco-main-a7e2097.png): render local del HTML archivado para `a7e2097`; muestra 88% de instrucciones y 63% de ramas. La captura se agrega también al PDF de entrega.
- [Captura de GitHub Actions #86](ci-run-main-77b5141.png): ejecución verde en `main`, commit `77b5141`, con el título neutral `AgroValle Connect - CI (push | main)`.
- [Captura del reporte HTML JaCoCo #83](jacoco-main-6c19aae.png): 89% instrucciones, 63% ramas y 91% líneas para el mismo commit.
- [Reporte HTML completo](../entrega-segundo-corte/jacoco-main-6c19aae/index.html) y [artefacto original de GitHub Actions](https://github.com/santixit/agrovalle-connect/actions/runs/40816952882/artifacts/12873380141).

Actions #103 acredita que `main` pasó Build, tests, Checkstyle, JaCoCo, E2E y despliegue en `a7e2097`. Las métricas actuales provienen del reporte JaCoCo del mismo run: 88% de instrucciones, 63% de ramas y 90% de líneas. El reporte #83 y la captura #97 se conservan como evidencia histórica, no como cifras del código actual.
