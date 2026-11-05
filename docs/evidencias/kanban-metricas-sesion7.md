# Evidencia Kanban y métricas — Sesión 7

## Resumen de lo que permiten afirmar los datos

Hay dos snapshots históricos de conteos de la vista **Backlog** del proyecto [Agrovalle-Kamba](https://github.com/users/santixit/projects/3/views/1), fechados el 17 y el 19 de octubre de 2026. El 2026-11-05 se volvió a consultar el tablero y la vista Insights; el detalle de esa lectura y sus límites está en [Lectura del Burn up y datos para la CFD](kanban-burnup-sesion7.md). Los snapshots históricos no representan el estado del tablero en esa fecha posterior.

| Métrica / lectura | Estado | Qué respalda la evidencia |
|---|---|---|
| WIP por columna | Calculable en los dos instantes observados | El 2026-10-19: In Progress 0/3; Code Review / Testing 2/2. Es un snapshot histórico, no un valor actual. |
| Cambio neto de conteos | Calculable entre los dos snapshots | Product Backlog +1; las demás columnas no cambiaron de conteo. No identifica movimientos ni tarjetas. |
| Throughput por semana o Sprint | No calculable | No hay periodos registrados ni eventos completos de paso a Done; la plantilla está vacía. |
| Cycle time por tarjeta | No calculable | No hay fechas observadas del primer paso a In Progress y del paso a Done para las tarjetas completadas. |
| Lead time por tarjeta | No calculable | No hay fechas por tarjeta de entrada a Backlog y despliegue/operación. |
| Tendencia WIP / CFD | No calculable como serie confiable | Dos conteos aislados no contienen los movimientos ni la serie histórica completa por estado. El gráfico consultado era Burn up, no CFD; no se encontró historial/exportación por estado. |
| Burn up | Lectura visual disponible, con reserva | Se anotaron tramos aproximados del gráfico de 2026-10-22 a 2026-11-05. No hay CSV/captura guardada y el total del filtro (35) no coincide con la suma visual de las series finales (aprox. 24). Véase el análisis enlazado arriba. |
| Ley de Little / tiempo de entrega esperado | No calculable | Requiere un flujo estable y throughput/cycle time observados; esas series no están disponibles. |

No se deben rellenar los vacíos con cero, inferir fechas de commits o restar snapshots para reconstruir movimientos. En particular, el `+1` de Product Backlog es un cambio neto de conteo, no throughput.

## Registro de observaciones

Los conteos proceden del archivo [kanban-snapshots-sesion7.csv](kanban-snapshots-sesion7.csv):

| Fecha observada | Product Backlog | Sprint Backlog / To Do | In Progress | Code Review / Testing | Done | Fuente |
|---|---:|---:|---:|---:|---:|---|
| 2026-10-17 | 16 | 0 | 0 | 2 | 12 | Snapshot manual documentado previamente; no se conserva captura original en esta evidencia. |
| 2026-10-19 | 17 | 0 | 0 | 2 | 12 | Consulta de la vista Backlog del proyecto enlazado arriba. |

Los límites registrados son In Progress = 3 y Code Review / Testing = 2. En la observación del 17 de octubre, las dos tarjetas en revisión/pruebas eran issues T06.1 y T06.2, no Pull Requests. En el snapshot del 19, la columna Code Review / Testing estaba en su límite y había capacidad disponible en In Progress. Esto permite describir esos dos instantes; no permite atribuir causas ni afirmar una tendencia.

La inspección del 2026-11-05 registró la vista Burn up y sus limitaciones; los datos se leen visualmente y no constituyen exportación de eventos. No se trata ese gráfico como CFD. Esta lectura es una nota de análisis, no acta de retrospectiva ni evidencia de una ceremonia formal. El equipo coordina el trabajo de manera informal y asíncrona.

En el snapshot consultado el 2026-11-05, el tablero mostraba In Progress **0/3** y Code Review / Testing **0/2**. Esos valores reemplazan únicamente la descripción de estado actual; no reescriben las observaciones del 17 y 19 de octubre ni aportan una serie histórica.

## Inventario y uso de los archivos

| Archivo | Propósito | Estado de datos |
|---|---|---|
| [kanban-snapshots-sesion7.csv](kanban-snapshots-sesion7.csv) | Conteos por columna en una fecha. | Tiene dos observaciones: 2026-10-17 y 2026-10-19. |
| [kanban-periodos-sesion7.csv](kanban-periodos-sesion7.csv) | Periodos para throughput semanal/Sprint; `done_events_complete=true` solo cuando se verificaron todos los cierres. | Plantilla vacía. |
| [kanban-items-sesion7.csv](kanban-items-sesion7.csv) | Fechas de Backlog, primer In Progress, Done y despliegue/operación por tarjeta. | Plantilla vacía; no completar fechas que no consten en evidencia. |
| [kanban-burnup-sesion7.md](kanban-burnup-sesion7.md) | Lectura del Burn up visto en Insights el 2026-11-05 y requisitos para construir una CFD reproducible. | Análisis visual aproximado; no reemplaza el historial de eventos ni permite calcular métricas temporales. |
| [`scripts/kanban_metrics.py`](../../scripts/kanban_metrics.py) | Cálculo reproducible a partir de las dos plantillas de eventos. | Con las plantillas vacías informa que no hay periodos/datos; no infiere valores a partir de snapshots. |
| [Políticas Kanban](../kanban-policies.md) | Límites WIP, políticas de entrada/salida, definiciones de métricas y guía CFD. | Define cómo trabajar y qué capturar; no constituye evidencia histórica del tablero. |

## Captura de evidencia futura

1. Al cierre del periodo, agregarlo a `kanban-periodos-sesion7.csv` con identificador, fechas, fuente y `done_events_complete=true` solo tras comprobar que están todos los cierres. Sin esa confirmación, throughput debe decir “sin datos”.
2. Por cada tarjeta, registrar en `kanban-items-sesion7.csv` el identificador, periodo y fechas realmente observadas de entrada al Backlog, primer movimiento a In Progress, Done (cumple DoD) y despliegue/operación. Usar ISO-8601 (`YYYY-MM-DD` o timestamp) y enlazar fuente.
3. Guardar nuevos conteos fechados en `kanban-snapshots-sesion7.csv`. Los snapshots adicionales permiten comparar WIP entre fechas, pero no sustituyen eventos de tarjeta para calcular tiempos o throughput.
4. Cuando existan periodos y eventos, ejecutar `python scripts/kanban_metrics.py`. El programa informa mediana, rango y cantidad de datos disponibles; campos vacíos siguen siendo datos ausentes.
5. Si se analiza un cuello de botella, registrar el periodo, evidencia y acción verificable en el canal o issue que use el equipo. No describir una recomendación como acuerdo si no fue confirmada.
