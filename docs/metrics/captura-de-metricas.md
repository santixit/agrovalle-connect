# Captura reproducible de métricas Kanban

## Propósito

Establecer qué eventos y fuentes hay que registrar para calcular velocity, burndown, cumulative flow diagram (CFD), throughput, Lead Time y Cycle Time. Este procedimiento complementa las [políticas Kanban](../kanban-policies.md) y el [reporte del tercer corte](sprint-metrics-corte3.md).

## Fuentes actuales y límite

El equipo conserva dos snapshots de conteos de octubre y una observación posterior del tablero. No hay una serie completa de movimientos fechados por tarjeta. Por ello esos datos permiten describir los instantes observados, pero no calcular CFD, Lead Time, Cycle Time ni throughput histórico. El [reporte de métricas](sprint-metrics-corte3.md) deja esos resultados como no disponibles; no completar los vacíos con timestamps de commits ni con estimaciones.

## Registro requerido por sprint

Al inicio, guardar el ID/enlace del sprint, fecha y hora de inicio, fecha y hora de fin acordada, Sprint Goal y los Story Points comprometidos. Al cierre, registrar los SP que cumplieron todos los puntos del DoD y enlazar evidencia de cada HU. La velocity del periodo es la suma de SP terminados conforme al DoD, no los SP que solo llegaron a `Done` en el tablero.

Para el burndown, guardar el trabajo restante al final de cada día laborable del sprint y su fecha. Sin una serie diaria y el alcance inicial, solo debe presentarse la plantilla, no una curva.

## Registro requerido por tarjeta

Por cada elemento y periodo, anotar únicamente eventos observados en GitHub Projects o en un registro contemporáneo del equipo:

| Evento | Campo de la plantilla CSV | Regla |
|---|---|---|
| Entrada a Product Backlog | `backlog_at` | Fecha/hora en que la necesidad quedó registrada; no sustituir por fecha de commit. |
| Primer paso a In Progress | `started_at` | Primer movimiento verificable a trabajo activo. |
| Paso a Done conforme al DoD | `done_at` | Debe tener CI y evidencia requeridos; si el tablero lo marca Done antes, registrar el momento del cumplimiento del DoD. |
| Despliegue/operación | `deployed_at` | Momento verificable en que el cambio está operativo en el entorno acordado. |
| Referencia | `source` | Enlace al evento, issue, PR, exportación o captura fechada. |

La plantilla actual es [`kanban-items-sesion7.csv`](../evidencias/kanban-items-sesion7.csv); se puede continuar en una copia por periodo sin sobrescribir la evidencia histórica. Usar ISO-8601 y registrar la zona horaria. Si se desconoce una fecha, dejarla vacía y anotar la razón.

## Cálculo y evidencia

| Métrica | Cálculo | Datos mínimos |
|---|---|---|
| Velocity | Suma de SP de historias completadas conforme al DoD dentro del sprint. | Compromiso original, estimación y cierre DoD por HU. |
| Burndown | Trabajo restante por fecha frente al compromiso inicial. | Estimación inicial y puntos restantes observados cada día. |
| CFD | Conteo de tarjetas por estado en cada fecha del periodo. | Historial completo de movimientos o snapshots diarios consistentes, definición estable de columnas. |
| Throughput | Cantidad de elementos terminados por periodo. | Eventos completos de finalización y ventana de tiempo definida. |
| Lead Time | `deployed_at - backlog_at`. | Entrada a Backlog y puesta en operación verificables por tarjeta. |
| Cycle Time | `done_at - started_at`. | Primer paso a In Progress y cumplimiento DoD verificables por tarjeta. |
| WIP | Elementos actualmente activos, agrupados por estado y fecha de observación. | Snapshot fechado de las columnas operativas. |

El cálculo automatizado existente está en [`scripts/kanban_metrics.py`](../../scripts/kanban_metrics.py) y usa [`kanban-periodos-sesion7.csv`](../evidencias/kanban-periodos-sesion7.csv) junto con [`kanban-items-sesion7.csv`](../evidencias/kanban-items-sesion7.csv). El script debe ejecutarse cuando los archivos tengan eventos suficientes; su salida y los CSV usados deben adjuntarse al informe, indicando periodo, fecha de extracción y fuente. Las plantillas vacías deben seguir vacías hasta contar con evidencia.

## Captura durante la operación

1. Al mover una tarjeta, asegúrate de que GitHub Projects conserve el evento de cambio de estado.
2. Al cierre de cada día o periodo, exporta/conserva los conteos por columna y enlaza el snapshot a su fecha.
3. Registra el inicio y cierre DoD de cada HU. Si el historial del tablero no permite obtenerlos, acuerda registrar esos eventos en un issue o PR.
4. En el cierre del sprint, verifica que no falten tarjetas del periodo y que cada fecha tenga una fuente enlazada.
5. Calcula las métricas y revisa anomalías con el equipo antes de compartir conclusiones.
6. Si la fuente no ofrece datos suficientes, publica “no calculable con la evidencia disponible” y explica qué evento falta.

Las métricas describen el flujo del sistema de trabajo y no se usan para comparar ni calificar personas. Los ejemplos del laboratorio pueden servir para practicar el cálculo, pero deben mantenerse etiquetados como datos de ejemplo y separados de los resultados del equipo.
