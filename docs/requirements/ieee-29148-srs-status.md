# Estado de la especificación IEEE 29148

## Estado actual

La [SRS consolidada](system-requirements-specification.md) mantiene requisitos funcionales, de datos y de calidad trazados al Backlog, contratos, migraciones y controles ejecutables. El 2026-11-19 se convirtió a una línea base de trabajo con decisiones explícitas de alcance en [baseline-decisions-2026-11-19.md](baseline-decisions-2026-11-19.md).

La especificación detallada de [REQ-01](ieee-29148-req01.md) y el [Product Backlog BDD](product-backlog-bdd.md) continúan como artefactos complementarios. La SRS describe una línea base documental para el prototipo; no afirma que todo el equipo la haya firmado ni que Sprint 3 haya sido aceptado funcionalmente.

## Qué está cerrado documentalmente

- El Backlog/BDD es la fuente para comportamiento de producto; el contrato REST y las pruebas muestran el comportamiento técnico.
- Flyway V1–V7 es la fuente ejecutable del esquema; el DER implementado corresponde a esa base.
- Para el prototipo se excluyen datos reales de producción, pagos, persistencia remota de imágenes, notificaciones externas y SLA.
- El resultado Maven de Sprint 3 comunicado el 2026-11-19 se registra como 37 pruebas aprobadas; no reemplaza demo ni aceptación del PO.

## Cómo se gestionan cambios y aceptación

1. Una nueva regla de negocio se incorpora primero al Backlog y al escenario BDD.
2. El cambio correspondiente actualiza contrato, implementación, migración si aplica, pruebas y trazabilidad en el mismo Pull Request.
3. El PR requiere CI y revisión independiente según las reglas del repositorio.
4. La aceptación de producto queda en review/issue por historia con decisión, fecha, responsable y evidencia.
5. Se actualiza esta SRS cuando cambie el alcance o una decisión de línea base.

No hay una acción vacía que impida continuar con el desarrollo del prototipo. Antes de operar con datos reales o presentar el sistema como producto de producción se requiere una política legal/operativa separada. La aceptación funcional de Sprint 3 permanece descrita en su [review](../sprints/sprint-3/review.md).
