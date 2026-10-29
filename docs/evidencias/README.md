# Índice de evidencias

## Kanban y flujo

- [Evidencia Kanban de Sesión 7](kanban-metricas-sesion7.md): snapshots disponibles, interpretación del WIP, estado de cada métrica y límites de los datos.
- [Snapshots por columna](kanban-snapshots-sesion7.csv): dos observaciones históricas, del 17 y 19 de octubre de 2026.
- [Plantilla de periodos](kanban-periodos-sesion7.csv) y [plantilla de eventos por tarjeta](kanban-items-sesion7.csv): vacías hasta contar con evidencia verificable.
- [Políticas Kanban y métricas](../kanban-policies.md): reglas operativas y definiciones; no confundirlas con evidencia de actividad ya ocurrida.
- [Calculador reproducible](../../scripts/kanban_metrics.py): usa los periodos y eventos por tarjeta; no deduce métricas a partir de conteos.

## Sprint 1 y calidad

- [Revisión funcional del Sprint 1](../sprint-1-review.md).
- [Pirámide de pruebas de Sesión 8](piramide-pruebas-sesion8.md).
- [Evidencia de historias y BDD](comparacion-historias-backlog-sprint1.md).
- [Colección de capturas Postman](sprint-1/).

## Prácticas y patrones

- [Evidencia SOLID de Sesión 7 y Sprint 2](solid-sesion7.md): mapeo concreto a los cinco principios, con límites de cada ejemplo.
- [Evidencia TDD del descuento mayorista](tdd-descuento-mayorista.md).

## CI y cobertura

- [Captura de GitHub Actions #86](ci-run-main-77b5141.png): ejecución verde en `main`, commit `77b5141`, con el título neutral `AgroValle Connect - CI (push | main)`.
- [Captura del reporte HTML JaCoCo #83](jacoco-main-6c19aae.png): 89% instrucciones, 63% ramas y 91% líneas para el mismo commit.
- [Reporte HTML completo](../entrega-segundo-corte/jacoco-main-6c19aae/index.html) y [artefacto original de GitHub Actions](https://github.com/santixit/agrovalle-connect/actions/runs/40816952882/artifacts/12873380141).

Actions #86 acredita que `main` pasó Build, tests, Checkstyle, JaCoCo y despliegue en `77b5141`. Las métricas de cobertura provienen del JaCoCo #83 para `6c19aae`; no cambiaron el código ni las pruebas entre ambos commits. El paquete documental y los exports están publicados en `main` mediante PR #59.
