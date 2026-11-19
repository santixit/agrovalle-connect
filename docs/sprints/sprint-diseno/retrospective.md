# Sprint de Diseño — análisis retrospectivo documental

**Fecha de registro:** 2026-11-19
**Modalidad:** análisis de artefactos y comentarios disponibles; no acredita una ceremonia ni atribuye opiniones a integrantes.

## Observaciones

### Prácticas útiles observables

- Se conservan fuentes editables y exportaciones de modelos, UML y mockups.
- La reconciliación compara DER propuestos con migraciones reales y evita tratar un dibujo como esquema ejecutable.
- El issue #46 deja decisiones específicas de alcance y confirmaciones de integrantes, con fecha e historial verificables en GitHub.

### Dificultades observables

- Los documentos actuales reflejan en parte la implementación y no pueden demostrar que el diseño se completó antes de programar.
- Issue #46 no identifica aprobación del PO para cada mockup ni confirmación de todos los integrantes.
- El repositorio contiene DER propuestos y DER implementados; sin una indicación explícita, una persona lectora podría confundir cuál se ejecuta.

## Reglas operativas aplicadas a partir de esta revisión

| Riesgo | Regla de trabajo | Comprobación |
|---|---|---|
| Confundir diseño vigente con documento histórico | Cada diagrama indica si representa propuesta o implementación y la versión del esquema a la que corresponde. | Revisión de enlaces y versión en PR. |
| Inferir aceptación desde comentarios informales | Las aprobaciones se registran con identidad, artefacto/versión, decisión y fecha; si falta algo se describe el alcance de la confirmación existente. | Comentario o review verificable enlazado. |
| Divergencia entre DER y DDL | Actualizar DER y snapshot en el mismo PR que la nueva migración Flyway. | CI aplica migraciones; revisión compara esquema y diagrama. |
| Afirmar temporalidad no probada | Mantener la cronología como “no demostrada” si no existe evidencia contemporánea. | No usar fecha de commit como sustituto de acta de diseño. |

## Resultado

El análisis se cierra con una referencia de diseño utilizable para el trabajo futuro y un registro claro de límites históricos. No se afirma que las reglas nuevas fueran acuerdos de una retrospectiva celebrada; se aplican como convención documental de esta línea base.
