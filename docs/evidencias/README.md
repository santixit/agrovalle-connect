# Evidencias transversales

Esta carpeta contiene registros que abarcan varias iteraciones o documentan prácticas compartidas. Las evidencias propias de una sola iteración están junto al sprint correspondiente: [Sprint 1](../sprints/sprint-1/evidencias/), [Sprint 2](../sprints/sprint-2/evidencias/) y [Sprint 3](../sprints/sprint-3/evidencias/).

## Kanban y métricas

- [Evidencia Kanban de Sesión 7](kanban-metricas-sesion7.md): snapshots disponibles, interpretación del WIP, estado de cada métrica y límites de los datos.
- [Lectura del Burn up y datos para la CFD](kanban-burnup-sesion7.md): consulta de Insights, diferencia entre Burn up y CFD, discrepancia de conteos y datos requeridos para un análisis reproducible.
- [Snapshots por columna](kanban-snapshots-sesion7.csv): observaciones históricas del 17 y 19 de octubre de 2026.
- [Plantilla de periodos](kanban-periodos-sesion7.csv) y [plantilla de eventos por tarjeta](kanban-items-sesion7.csv): se mantienen sin datos mientras no haya fuentes verificables.
- [Políticas Kanban](../kanban-policies.md) y [procedimiento de captura](../metrics/captura-de-metricas.md).
- [Calculador reproducible](../../scripts/kanban_metrics.py): procesa periodos y eventos; no deduce métricas a partir de conteos aislados.

## Arquitectura, prácticas y pruebas

- [Refactorizaciones SOLID de Sesión 7](solid-sesion7.md): cambios antes/después en catálogo/gestión de productos y descuento, con límites de la evidencia.
- [Pirámide de pruebas de Sesión 8](piramide-pruebas-sesion8.md): conteo observado, comparación con 70/20/10 y cobertura JaCoCo, con brechas explícitas.
- [Integración continua de Sesión 10](integracion-continua-sesion10.md): workflow, quality gates y ejecuciones históricas.
- [Evidencias del Sprint 3](../sprints/sprint-3/README.md): resultado Maven comunicado, recorrido de demo pendiente y estado de aceptación por historia.

## CI y cobertura archivada

- [GitHub Actions #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070): ejecución histórica del commit `a7e2097`, con job de calidad, E2E y despliegue a staging.
- [Captura visual de Actions #103](ci-run-main-a7e2097.png) y [artefacto JaCoCo del mismo run](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/artifacts/12906676338).
- [Reporte JaCoCo #103](../entregables/segundo-corte/jacoco-main-a7e2097/index.html): 88% de instrucciones, 63% de ramas y 90% de líneas para el commit archivado.
- [Captura del reporte JaCoCo #103](jacoco-main-a7e2097.png), renderizada desde ese HTML.
- [Captura de Actions #86](ci-run-main-77b5141.png): evidencia histórica del commit `77b5141`.
- [Captura del reporte JaCoCo #83](jacoco-main-6c19aae.png) y [reporte HTML completo](../entregables/segundo-corte/jacoco-main-6c19aae/index.html): evidencia histórica del commit `6c19aae`.
- [Artefacto JaCoCo #83](https://github.com/santixit/agrovalle-connect/actions/runs/40816952882/artifacts/12873380141).

Las cifras de un reporte se atribuyen al commit y ejecución que lo produjeron; no se presentan como cobertura actual de una versión posterior. Las capturas y ejemplos deben ocultar secretos y datos personales.
