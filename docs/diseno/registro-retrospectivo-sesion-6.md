# Registro retrospectivo — diseño y Sprint Discovery (Sesión 6)

**Estado:** registro retrospectivo actualizado con las decisiones visibles del issue #46 y la línea base del 2026-11-19.
**Primera versión documental:** 2026-10-19. **Última revisión:** 2026-11-19.
**Fecha de las actividades informales:** no consta en el repositorio; no se reconstruye a partir de fechas de commits.
**Tipo de registro:** documentación retrospectiva de trabajo informal/asíncrono. No es acta de una reunión celebrada en la fecha de registro.

## Motivo y límite de este registro

La síntesis del [issue #46](https://github.com/santixit/agrovalle-connect/issues/46) registra que el Sprint Discovery y el visto bueno de los mockups se hicieron de manera informal y asíncrona y que se dejan asentados retrospectivamente. El issue también registra como decisiones mantener el Backlog como base de HU-01, HU-07 y HU-04 con los criterios técnicos del Sprint Planning como aceptación; perfil público no sensible para HU-07; filtros opcionales y lista vacía sin coincidencias para HU-04; y esquema actual con catálogos normalizados como mejora futura. No atribuyo estas respuestas a integrantes que no comentaron ni invento fecha de actividad, asistentes, duración, reunión, firma o detalles adicionales del modelo.

## Elementos para confirmar entre el equipo

| Elemento | Registro actual | Validación colectiva |
|---|---|---|
| Metodología | El issue registra trabajo informal y asíncrono; no documenta una reunión formal ni roles fijos. | Hay tres comentarios explícitos de confirmación (DannyGomez02, michelleguerrero19 y Star-Light-co). No hay evidencia de unanimidad de todo el grupo. |
| Mockups | El issue registra visto bueno informal, retrospectivo y asíncrono. No constan fecha de actividad ni identidad de quienes dieron el visto bueno. | Se usan los archivos versionados como referencia visual actual; el repositorio no acredita aprobación individual por pantalla o aprobación total del grupo. |
| Sprint Discovery | El issue registra que se hizo informalmente y de manera asíncrona, con constancia retrospectiva. | La fecha exacta y la secuencia frente a la codificación no están demostradas. |
| Historias y alcance | El issue adopta el Backlog como versión base de HU-01, HU-07 y HU-04, con criterios técnicos del Sprint Planning como aceptación. HU-04 y HU-07 están ajustadas en el Backlog. | Decisión documentada en el issue #46 y resumida en la [comparación](../evidencias/comparacion-historias-backlog-sprint1.md). |
| Modelo de datos | El issue #46 acuerda mantener el esquema inicial y aplazar catálogos. V7 posterior añade parcela, ubicación e historial de transferencia. | La línea base del 2026-11-19 fija Flyway V1–V7 como esquema canónico para continuar el prototipo; la [reconciliación](reconciliacion-der.md) explica el alcance y sus diferencias. |

## Artefactos revisados

- [Mockups de interfaz](mockups-ui.pdf) y sus fuentes PNG en `mockups/`.
- Modelos y DDL de referencia: `modelo-entidad-relacion.mmd`, `modelo-datos-der.mmd`, `schema.sql` y migraciones Flyway V1–V7. V7 normaliza la ubicación del producto mediante parcelas y `producto_ubicacion`; la [reconciliación](reconciliacion-der.md) documenta el cambio frente a los DER propuestos.
- [Reconciliación entre DER propuesto e implementado](reconciliacion-der.md).
- [Comparación Backlog/Sprint Planning](../evidencias/comparacion-historias-backlog-sprint1.md).

## Decisiones de línea base y acciones

Para continuar el proyecto, la versión actual de requisitos fija como referencia los criterios del Backlog/BDD, el esquema Flyway V1–V7 y los mockups versionados. Los detalles de privacidad, funciones fuera de alcance y modelo canónico se registran en [decisiones de línea base](../requirements/baseline-decisions-2026-11-19.md). Esta decisión operativa no se atribuye retroactivamente a la sesión de Discovery ni sustituye confirmaciones faltantes.

El registro futuro de una aprobación debe incluir identidad, artefacto/versión, decisión y fecha. Las decisiones técnicas que modifiquen el esquema se registran en ADR/PR y nueva migración; no se modifican migraciones aplicadas.

## Confirmación

Este registro no contiene firmas ni presenta a todo el equipo como aprobador. La fuente y los tres nombres de usuario que dejaron confirmación se conservan en el issue #46. No se añaden firmas ni se afirma aprobación general.
