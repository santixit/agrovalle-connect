# Evidencia funcional del alcance documentado — Sprint 1

Este archivo resume alcance técnico y material verificable del repositorio. **No es un acta de Sprint Review ni prueba de que se celebrara una reunión formal.** El equipo informa que no mantiene ceremonias formales ni roles permanentes; por ello no se atribuyen decisiones a un Product Owner.

## Alcance que aparece en el plan

- Sprint Goal documentado: habilitar registro inicial de agricultores, consulta de perfil y filtro de catálogo.
- Historias seleccionadas en el documento de planificación: HU-01 (5 SP), HU-07 (2 SP) y HU-04 (3 SP), total 10 SP.
- La comparación entre el Backlog y el plan está en [comparacion-historias-backlog-sprint1.md](evidencias/comparacion-historias-backlog-sprint1.md). El issue #46 registra el Backlog como versión base y los criterios técnicos del plan como aceptación. Las confirmaciones de quienes no comentaron siguen pendientes.

## Evidencia técnica existente

El repositorio conserva capturas en `docs/evidencias/sprint-1/` descritas como pruebas de Postman (`201` para HU-01; `200` para HU-07 y HU-04) y consulta de persistencia. La fecha y el entorno exactos de esas capturas deben confirmarse con el equipo antes de presentarlos como evidencia fechada. La existencia de las imágenes no demuestra una reunión ni la aprobación de historias.

- HU-01: [captura Postman](evidencias/sprint-1/01-hu01-postman.png) y [consulta PostgreSQL](evidencias/sprint-1/04-postgresql-agricultor.png).
- HU-07: [captura Postman](evidencias/sprint-1/02-hu07-postman.png).
- HU-04: [captura Postman](evidencias/sprint-1/03-hu04-postman.png).
- CI del commit `aa12a0d`: [ejecución reportada](https://github.com/santixit/agrovalle-connect/actions/runs/36608168866). Es evidencia de ese commit, no del estado actual por sí sola.

Estas pruebas sustentan resultados técnicos reportados; no deciden si el equipo acepta los criterios funcionales, la privacidad de HU-07 ni el alcance final.

## Pendiente de confirmación colectiva

- [ ] Recabar las confirmaciones de integrantes que aún no comentaron sobre la síntesis del issue #46.
- [ ] Confirmar si se considera aceptado el comportamiento probado en las capturas.
- [ ] Añadir comentarios verificables del equipo si desean dejar constancia de aprobación colectiva.
- [ ] Validar por separado la conformidad del DER con 3FN; el issue #46 no resuelve esta revisión.

No se registran asistentes, fecha de reunión, feedback oral, firma ni aceptación atribuida a un cargo. La cronología de merges y aprobaciones en GitHub no prueba por sí sola una Sprint Review.
