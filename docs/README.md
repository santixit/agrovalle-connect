# Índice de documentación

La documentación se organiza por necesidad de consulta. El [estado de proyecto y decisiones](requirements/baseline-decisions-2026-11-19.md) define la línea base del prototipo y enlaza los límites que se deben conservar.

## Fuentes principales

| Tema | Fuente |
|---|---|
| Requisitos | [Product Backlog BDD](requirements/product-backlog-bdd.md), [SRS](requirements/system-requirements-specification.md), [decisiones de línea base](requirements/baseline-decisions-2026-11-19.md) |
| Diseño y arquitectura | [Índice de diseño](diseno/README.md), [UML](architecture/diagramas-uml.md), [ADR](architecture-decisions/README.md) |
| Sprints | [Índice de sprints](sprints/README.md), [Sprint 3 en validación](sprints/sprint-3/planning.md) |
| Calidad y entrega | [DoD](dod.md), [estrategia de pruebas](estrategia-pruebas.md), [CI](../.github/workflows/ci.yml), [CD](../.github/workflows/cd.yml) |
| Flujo de trabajo | [Políticas Kanban](kanban-policies.md), [protección de ramas](branch-protection.md), [captura de métricas](metrics/captura-de-metricas.md) |
| Entornos | [Staging](deploy-staging.md) y [ADR-006 — no producción](architecture-decisions/ADR-006-estrategia-produccion-pendiente.md) |
| Evidencias | [Índice de evidencias](evidencias/README.md), incluyendo capturas, pruebas, CI, métricas y demos |
| Entregas de curso | [Sprint 0](entrega-sprint-0/README.md), [segundo corte](entrega-segundo-corte/README.md) |

El código Java permanece en src/, las pruebas en src/test/, los scripts en scripts/ y los workflows en .github/workflows/. BACKLOG.md en la raíz es un acceso de compatibilidad; la fuente vigente está en docs/requirements/product-backlog-bdd.md.

## Estado documental al 2026-11-19

- La SRS y sus decisiones de alcance están consolidadas para el prototipo. No declara producción, SLA ni uso de datos reales.
- Sprint 0, Sprint 1 y Sprint de Diseño cuentan con revisiones/retrospectivas documentales que no se hacen pasar por ceremonias históricas.
- Sprint 2 cuenta con registros documentales de su trabajo visible; la selección inicial de HUs/SP no se pudo verificar y no se reconstruye.
- Sprint 3 tiene resultado JUnit comunicado de 37 pruebas aprobadas. El gate de calidad del commit final, la demo en staging y la aceptación explícita del PO por HU no están acreditados; ver checklist y review.
- Lead Time, Cycle Time, Throughput, velocity, burndown y CFD solo se publican si los eventos fuente permiten calcularlos. El reporte actual explica qué cifras están disponibles y cuáles no.

“No acreditado” significa que el repositorio no conserva evidencia suficiente; no es un dato en blanco ni una afirmación de que la actividad nunca ocurriera. Las ceremonias, firmas, métricas y decisiones se registran únicamente con su fuente verificable.
