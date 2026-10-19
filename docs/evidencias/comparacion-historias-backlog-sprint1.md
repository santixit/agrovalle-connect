# Comparación de historias: Backlog y Sprint Planning

## Fuentes comparadas

- Backlog de producto: [`BACKLOG.md`](../../BACKLOG.md).
- Plan técnico de Sprint 1: [`docs/sprint-1-planning.md`](../sprint-1-planning.md).

El backlog contiene 15 historias. La planificación selecciona HU-01, HU-07 y HU-04, con un total de 10 SP. Según la síntesis publicada en el [issue #46](https://github.com/santixit/agrovalle-connect/issues/46), se adopta el Backlog como texto base de esas historias y los criterios técnicos de Sprint Planning como criterios de aceptación. El issue contiene confirmaciones explícitas de DannyGomez02, michelleguerrero19 y Star-Light-co; no se atribuyen respuestas a quienes no comentaron ni se consideran los comentarios eliminados.

| ID | Texto/alcance en el Backlog | Texto/alcance en Sprint Planning | Diferencias observables | Punto para decisión colectiva |
|---|---|---|---|---|
| HU-01 | “Como agricultor, quiero registrarme para ofrecer mis productos.” BDD: registra nombre, `ubicacion_valle` y cédula; `POST /api/v1/auth/register`; valida duplicados y persistencia. | “Registro de agricultores”. Criterio técnico: nombre, ubicación en el Valle y cédula válidos; PostgreSQL; rechaza cédulas duplicadas. El plan descompone DTO y validaciones, sin mencionar correo/contraseña en esta historia. | La necesidad del backlog explicita el beneficio de ofrecer productos y el BDD nombra el contrato. El plan usa un título corto y amplía el detalle técnico a validación de ubicación y PostgreSQL. No cambia la estimación: 5 SP. | Se adopta el Backlog como base y los criterios técnicos del plan como aceptación, según el issue #46. Sigue sin definirse en el issue cómo se determina “en el Valle”; el repositorio valida texto, no un catálogo geográfico. |
| HU-07 | Backlog actualizado: “Como usuario, quiero consultar el perfil público de un agricultor para conocer sus datos no sensibles.” | “Consulta del perfil del agricultor”. Criterio técnico: `GET /api/v1/productores/{id}`, incluye manejo de identificador inexistente y DTO. | Se conserva la historia base del Backlog y el plan precisa ruta y caso no encontrado. El issue #46 confirma perfil público con datos no sensibles; el DTO actual expone id, nombre y ubicación. No cambia la estimación: 2 SP. | Decisión resuelta en el issue #46: perfil público con datos no sensibles. No se atribuye autorización para exponer campos privados. |
| HU-04 | Backlog actualizado: filtros opcionales por municipio, categoría o ambos; si no hay coincidencias, lista vacía. | “Filtro por municipio y categoría”. Criterio técnico: `GET /api/v1/productos?municipio=Dagua&categoria=Frutas`, devuelve coincidencias; plan contempla filtros combinados y sin resultados. | El Backlog concreta las opciones de filtro y el resultado vacío que la descomposición técnica ya contemplaba. No cambia la estimación: 3 SP. | Decisión resuelta en el issue #46: se puede usar cada filtro por separado o combinarlos; sin coincidencias, devuelve lista vacía (reportado como verificado en staging). |

## Historias que no están en la selección del Sprint

HU-02, HU-03, HU-05, HU-06 y HU-08 a HU-15 aparecen en el Backlog, pero no en la selección de HU-01/HU-07/HU-04 de Sprint 1. Esto es diferencia de alcance del Sprint, no contradicción entre textos. La selección de HU-01/HU-07/HU-04 está respaldada por la decisión del issue #46.

## Estimaciones y registro

Las estimaciones de las tres historias coinciden: HU-01 = 5 SP, HU-07 = 2 SP, HU-04 = 3 SP; total = 10 SP. El plan también presenta estimaciones en horas, indicadas como estimaciones del documento, no como acuerdos adoptados en una reunión formal.

**Estado:** comparación documental completada y decisiones de base registradas con fuente en el issue #46. Las confirmaciones expresas visibles allí proceden de DannyGomez02, michelleguerrero19 y Star-Light-co. La confirmación de integrantes que no hayan comentado sigue **pendiente**; los comentarios eliminados no se interpretan como respuesta.
