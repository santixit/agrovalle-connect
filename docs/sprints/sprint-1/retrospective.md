# Sprint 1 — análisis retrospectivo de artefactos

**Modalidad:** análisis documental asíncrono de evidencia guardada; no es una reunión retrospectiva formal.
**Fuente:** plan, capturas de Postman y PostgreSQL, issue #46, bitácora de repositorio y Pull Requests enlazados.

## Observaciones sustentadas

### Lo que ayudó

- El plan relaciona HU-01, HU-07 y HU-04 con 10 SP, endpoints y pruebas esperadas.
- Las capturas de Postman y PostgreSQL permiten seguir el recorrido de registro, consulta de perfil, filtrado y persistencia usando datos sintéticos.
- El issue #46 fija decisiones de base para HU-01, HU-04 y HU-07 y conserva las confirmaciones que dejaron integrantes.
- El uso de ramas, Pull Requests y CI deja rastros revisables de cambios técnicos.

### Lo que dificultó la revisión

- El equipo trabajó de forma informal y asíncrona; no hay evidencia de Daily Scrums ni de review/retrospective formal.
- Las capturas no identifican por sí solas fecha, commit o entorno de todas las pruebas; deben citarse como evidencia visual, sin usarlas como certificación del estado actual.
- Algunos Pull Requests se integraron antes de que apareciera una aprobación posterior; esa aprobación no cuenta como revisión previa al merge.
- La existencia de pruebas y de estado `Done` no acredita por sí sola aceptación funcional del PO.

## Acciones de proceso establecidas

| Acción desde el siguiente cambio | Comprobación concreta | Responsable operativo |
|---|---|---|
| Cada HU debe enlazar escenario BDD, endpoint y prueba automatizada. | El PR incluye los identificadores de HU y nombres de las pruebas; el revisor puede llegar a ambos desde el diff. | Autor del PR; revisor valida. |
| Las capturas de demostración deben indicar fecha, entorno y commit o run. | Nombre descriptivo y metadatos en el documento de evidencia; no incluir secretos ni datos personales reales. | Quien genera la captura. |
| La aceptación del PO se registra por historia. | Comentario o acta con estado, fecha, responsable y observación para cada HU. | PO que realiza la revisión. |
| La revisión independiente ocurre antes del merge. | Aprobación y checks requeridos aparecen antes de integrar. | Revisor asignado y mantenedor. |

## Resultado

Estas acciones corrigen los vacíos de trazabilidad observados y son aplicables sin reconstruir una ceremonia pasada. No se atribuyen a todo el equipo como acuerdos de una retrospectiva que no está documentada.
