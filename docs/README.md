# Índice de documentación

La documentación se agrupa por tema para que el equipo y la docente puedan encontrar los artefactos desde GitHub.

## Organización

| Carpeta | Contenido |
|---|---|
| [`architecture/`](architecture/README.md) | Diagramas UML Mermaid consolidados y fuentes de diseño relacionadas. |
| [`architecture-decisions/`](architecture-decisions/README.md) | ADRs con IDs conservados y estado de las decisiones de despliegue. |
| [`requirements/`](requirements/README.md) | Product Backlog BDD, REQ-01 formal y estado del SRS integral. |
| [`metrics/`](metrics/sprint-metrics-corte3.md) | Reporte de métricas ágiles; distingue mediciones de datos no disponibles. |
| [`sprints/`](sprints/README.md) | Planning, review, retrospective, bitácoras y proyección por sprint. |
| [`diseno/`](diseno/README.md) | DER, schema de referencia, mockups y fuentes editables de diseño. |
| [`evidencias/`](evidencias/README.md) | Capturas, CI, pruebas, métricas Kanban y demos. |
| [`postman/`](postman/) | Colecciones para probar endpoints. |
| [`entrega-sprint-0/`](entrega-sprint-0/README.md) | PDF original de Sprint 0. |
| [`entrega-segundo-corte/`](entrega-segundo-corte/README.md) | Entregable del segundo corte, generador y artefactos asociados. |

El código Java permanece en `src/`, las pruebas en `src/test/`, los scripts en `scripts/` y los workflows en `.github/workflows/`. `BACKLOG.md` en la raíz se conserva como acceso de compatibilidad; la fuente vigente está en `requirements/product-backlog-bdd.md`.

## Guías de trabajo

- [Definition of Done](dod.md)
- [Flujo de ramas y protección de `main`](branch-protection.md)
- [Estrategia de pruebas](estrategia-pruebas.md)
- [Políticas Kanban](kanban-policies.md)
- [Procedimiento para capturar métricas](metrics/captura-de-metricas.md)
- [Despliegue a staging](deploy-staging.md)
- [Evidencias técnicas](evidencias/README.md)

## Pendientes que muestra la organización

- La SRS consolidada y trazable está en borrador; quedan decisiones de producto y calidad por aprobar por el equipo/PO.
- `ci.yml` ejecuta calidad y llama al reusable `cd.yml` únicamente en push exitoso a `main`.
- El proyecto no tiene `application-staging.yml`; la configuración actual usa `application.properties` y variables de entorno.
- Las métricas de velocity, burndown, CFD, Lead Time y Cycle Time se reportan solo cuando hay registros suficientes; las plantillas y el procedimiento están en `docs/metrics/`.
- La aceptación del PO, la demo y la retrospectiva del Sprint 3 continúan pendientes.

Los términos *propuesta*, *reconstruido* y *pendiente* indican el estado real de la evidencia; no acreditan una ceremonia, aprobación o resultado que no conste.
