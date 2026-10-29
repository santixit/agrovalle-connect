# Retrospectiva documental — Sprint 2

**Preparada por Raúl Santiago Carrillo Hernández, Product Owner, el 2026-10-28.** Resume hechos observables del trabajo y de la conversación del equipo; no representa una reunión formal ni atribuye acuerdos colectivos que no fueron confirmados.

## Periodo y base de revisión

- Periodo de referencia: **2026-10-19 a 2026-10-29**. El inicio se infiere del primer registro local fechado de trabajo asociado al Sprint; el cierre fue indicado por el Product Owner.
- Fuentes: cambios de interfaz, UML, estrategia de fletes, análisis del esquema/DER, evidencia de pruebas y snapshots Kanban.
- El equipo confirmó que el resumen del incremento estaba bien y delegó su revisión al Product Owner. Indicó que no tenía comentarios adicionales.

## Qué funcionó

- Se trabajó con los mockups corregidos y se conservaron sus fuentes y exportaciones para comparar el diseño con las vistas implementadas.
- Se documentaron Strategy para el cálculo demostrativo de fletes y los flujos UML relacionados.
- Se organizó la evidencia Kanban distinguiendo conteos observables de métricas que no pueden calcularse con snapshots incompletos.
- El 2026-10-29 se hizo una comprobación complementaria de solo lectura en staging: la página, salud y catálogo respondieron HTTP 200. Queda una guía reproducible y un guion para explicarla, sin presentarla como una ceremonia Sprint Review previa.

## Dificultades observadas

- El paquete de documentación base ya se integró a `main` con los PR #59, #60 y #61. La ejecución actual de `main`, Actions #103 para el commit `a7e2097`, pasó y publicó su reporte JaCoCo; esta rama local incorpora la captura y el resultado de ese run en el PDF actualizado.
- Algunas capacidades de los mockups, como guardar fotos y consultar historial regional por periodos, no están respaldadas completamente por la API actual.
- Faltan eventos Kanban fechados suficientes para calcular métricas de flujo como lead time, cycle time y throughput.
- La implementación de 3FN y Flyway V7 ya está integrada en `main` mediante el PR #62. Actions #103 ejecutó las pruebas y controles de calidad sobre ese código. El equipo aún debe validar que el modelo represente sus decisiones de dominio.
- El Product Owner definió el 2026-10-29 que cada parcela debe tener registro propio vinculado a finca y agricultor responsable, que el producto puede asociarse a ella y que cada transferencia del producto conserve producto, agricultor anterior, nuevo agricultor y fecha. También definió que el municipio del producto coincida con el de la finca cuando esté asociado, que debe conservarse el agricultor destinatario del contacto al momento del contacto y que pueden existir productos sin finca si el sistema lo permite. Flyway V7 y el código integrados en `main` modelan parcelas y transferencias, derivan el municipio desde finca y mantienen municipio/titular para productos sin finca.

## Seguimiento del Product Owner

| Acción | Comprobación |
|---|---|
| Alinear los cambios que se presentarán y validar CI en el commit final de `main`. | [Actions #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070) para `a7e2097` y artefacto JaCoCo del mismo commit. |
| Actualizar el tablero al cierre y conservar una captura fechada. | Tarjetas del Sprint actualizadas y límites WIP visibles. |
| Explicar en la demostración qué funciones son visuales y cuáles tienen soporte de API. | [Recorrido técnico complementario en staging](evidencias/demo-staging-sprint2-2026-10-29.md), de solo lectura; repetir la comprobación en vivo durante la presentación. |
| Mantener explícitas las métricas no calculables y las decisiones de dominio pendientes. | Revisión del checklist y del análisis de normalización. |

Estas acciones son el seguimiento que asume el Product Owner; el equipo no las ha confirmado como acuerdos de una retrospectiva colectiva.
