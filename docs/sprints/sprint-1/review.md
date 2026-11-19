# Sprint 1 — revisión documental del incremento

**Estado del registro:** completado con evidencia técnica disponible; no se presenta como acta de una reunión formal.
**Base de revisión:** plan de Sprint 1, capturas de Postman/PostgreSQL, Pull Requests y ejecución de CI asociada a los commits publicados.

## Alcance planificado

| Historia | Prioridad | SP del plan | Evidencia guardada | Resultado documental |
|---|---|---:|---|---|
| HU-01 — Registro de agricultor | Must | 5 | [Postman](../../evidencias/sprint-1/01-hu01-postman.png), [persistencia PostgreSQL](../../evidencias/sprint-1/04-postgresql-agricultor.png) | Evidencia visual de respuesta y fila persistida; la captura no sustituye un resultado actual de CI. |
| HU-07 — Consulta del perfil | Must | 2 | [Postman](../../evidencias/sprint-1/02-hu07-postman.png) | Evidencia de respuesta del perfil público de prueba. |
| HU-04 — Filtro de catálogo | Must | 3 | [Postman](../../evidencias/sprint-1/03-hu04-postman.png) | Evidencia de consulta filtrada con datos de demostración. |
| **Total** |  | **10** |  | Alcance documentado en el plan. |

## Revisión de criterios

- HU-01: la evidencia muestra el registro con respuesta exitosa y la consulta de persistencia con identificador sintético.
- HU-07: el material guardado demuestra una consulta de perfil de prueba; el criterio de no exponer datos sensibles se define en el contrato y DTO.
- HU-04: el material guardado demuestra filtro con los parámetros de la demostración; la decisión de aceptar filtros opcionales y lista vacía está registrada en el [issue #46](https://github.com/santixit/agrovalle-connect/issues/46).
- La comparación entre historias y criterios técnicos está en [comparacion-historias-backlog-sprint1.md](../../evidencias/comparacion-historias-backlog-sprint1.md). El issue #46 contiene confirmaciones explícitas de integrantes sobre la versión base; no demuestra una ceremonia de review ni una aceptación individual por cada historia.
- Los enlaces de CI asociados a commits antiguos acreditan únicamente esos commits. Para cambios actuales, el resultado vigente se consulta en GitHub Actions.

## Conclusión y estado de aceptación

El repositorio conserva evidencia técnica de las tres historias del alcance y una línea base BDD/contrato revisada en el issue #46. No hay un acta de Sprint Review formal ni decisión de aceptación por HU asociada a esta revisión. Por tanto, este documento cierra la revisión documental del material, pero no inventa una aceptación del PO. El estado `Done` de GitHub Projects se mantiene separado de la aceptación funcional.

| Pregunta | Resultado registrado |
|---|---|
| ¿Hay evidencia técnica guardada? | Sí: tres respuestas Postman y una consulta de persistencia. |
| ¿Hay confirmación de criterios de alcance? | Sí, en issue #46; las confirmaciones visibles pertenecen a algunos integrantes. |
| ¿Se acredita una review formal con participantes y feedback? | No. |
| ¿Se acredita aceptación funcional explícita del PO para cada HU? | No se encontró registro por historia. |

## Referencias

- [Planificación de Sprint 1](planning.md)
- [Bitácora de coordinación asíncrona](bitacora-daily-scrum.md)
- [Definition of Done](../../dod.md)
