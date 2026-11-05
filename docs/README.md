# Índice de documentación

Esta carpeta conserva planes, evidencia y entregables. El código de la aplicación permanece en `src/`; las utilidades de ejecución y generación permanecen en `scripts/` o junto al entregable que producen.

## Estructura del repositorio

- `src/main/java/`: código Java organizado en `controller`, `service`, `repository`, `domain`, `api/dto` y `config`.
- `src/main/resources/`: configuración de Spring, migraciones Flyway y archivos web estáticos.
- `src/test/`: pruebas JUnit y configuraciones de base de datos para pruebas.
- `e2e/`: flujos de navegador automatizados con Playwright.
- `scripts/`: inicio local, carga de datos de demostración y cálculo de métricas Kanban.
- `.github/workflows/`: integración continua y despliegue de staging.
- `.husky/`: validaciones locales ejecutadas antes de crear un commit.
- Raíz: `pom.xml` (Maven/Java), `package.json` (Husky/Playwright), `Dockerfile` y `docker-compose.yml` (contenedores), `BACKLOG.md` y `README.md`.

El código Java ya tiene una separación por capas; por eso se conserva su ubicación. Los reportes JaCoCo que acompañan la entrega son artefactos de evidencia, no código fuente.

## Por tema

| Carpeta | Contenido |
|---|---|
| [`adr/`](adr/) | Decisiones de arquitectura y sus motivos. |
| [`diseno/`](diseno/) | DER, schema, UML, mockups, arquitectura y reconciliación del diseño con el modelo vigente. |
| [`evidencias/`](evidencias/) | Capturas, comprobaciones, métricas y guías de evidencia de calidad/Kanban. |
| [`postman/`](postman/) | Colección de solicitudes para probar los endpoints. |
| [`entrega-sprint-0/`](entrega-sprint-0/) | PDF de la entrega de Sprint 0. |
| [`entrega-segundo-corte/`](entrega-segundo-corte/) | PDF, generador y artefactos de cobertura usados en la entrega del segundo corte. |
| [`sprint-3/`](sprint-3/) | Propuesta de alcance y checklist de Sprint 3, pendientes de confirmación. |
| [`uml/`](uml/) | Diagramas UML editables y sus exportaciones. |

## Guías principales

- [Definition of Done](dod.md)
- [Estrategia de pruebas](estrategia-pruebas.md)
- [Políticas Kanban](kanban-policies.md)
- [Backlog](../BACKLOG.md)
- [Planificación de Sprint 1](sprint-1-planning.md), [revisión](sprint-1-review.md) y [retrospectiva](sprint-1-retrospective.md)
- [Planificación reconstruida de Sprint 2](sprint-2-planning.md), [revisión](sprint-2-review.md) y [retrospectiva](sprint-2-retrospective.md)
- [Proyección global de sprints, Story Points y dependencias](proyeccion-sprints.md)
- [Propuesta de Sprint 3](sprint-3/planificacion.md) y [checklist](sprint-3/checklist-inicio.md); Sprint 4 está proyectado en la tabla global
- [Revisión](diseno/sprint-diseno-review.md) y [retrospectiva documental](diseno/sprint-diseno-retrospective.md) del Sprint de Diseño

Los documentos que dicen *borrador*, *propuesta* o *reconstruido* no acreditan por sí solos una aprobación, reunión o ejecución. Las decisiones y resultados se actualizan cuando el equipo los confirma.
