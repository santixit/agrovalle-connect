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

## Dificultades observadas

- Las fuentes de código y pruebas locales coinciden con `main` en `6c19aae`, pero la documentación y los exports del paquete continúan locales. La ejecución CI #83 respalda el código; no publica ni valida los documentos aún no integrados.
- Algunas capacidades de los mockups, como guardar fotos y consultar historial regional por periodos, no están respaldadas completamente por la API actual.
- Faltan eventos Kanban fechados suficientes para calcular métricas de flujo como lead time, cycle time y throughput.
- La revisión de 3FN depende de confirmar reglas de dominio que no están expresadas en las restricciones actuales del esquema.

## Seguimiento del Product Owner

| Acción | Comprobación |
|---|---|
| Alinear los cambios que se presentarán y validar CI en el commit final de `main`. | Enlace al run y reporte JaCoCo del mismo commit. |
| Actualizar el tablero al cierre y conservar una captura fechada. | Tarjetas del Sprint actualizadas y límites WIP visibles. |
| Explicar en la demostración qué funciones son visuales y cuáles tienen soporte de API. | Comparación breve entre mockups y endpoints disponibles. |
| Mantener explícitas las métricas no calculables y las decisiones de dominio pendientes. | Revisión del checklist y del análisis de normalización. |

Estas acciones son el seguimiento que asume el Product Owner; el equipo no las ha confirmado como acuerdos de una retrospectiva colectiva.
