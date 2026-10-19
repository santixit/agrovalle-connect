# Evidencia Kanban y métricas — Sesión 7

## Alcance y fuentes

Esta evidencia recoge dos observaciones puntuales de la vista **Backlog** del proyecto [Agrovalle-Kamba](https://github.com/users/santixit/projects/3/views/1): el snapshot manual del 17 de octubre de 2026 que ya estaba documentado y la consulta del tablero del 19 de octubre de 2026. El registro tabular está en [kanban-snapshots-sesion7.csv](kanban-snapshots-sesion7.csv). Son conteos de columnas en dos fechas; no son un registro de movimientos de tarjetas ni una serie suficiente para afirmar tendencias.

| Fecha | Product Backlog | Sprint Backlog / To Do | In Progress | Code Review / Testing | Done |
|---|---:|---:|---:|---:|---:|
| 2026-10-17 | 16 | 0 | 0 | 2 | 12 |
| 2026-10-19 | 17 | 0 | 0 | 2 | 12 |

Los límites visibles siguen en In Progress = 3 y Code Review / Testing = 2. En la observación del 17, las dos tarjetas de revisión/pruebas eran issues T06.1 y T06.2, no Pull Requests. El cambio neto observado entre ambos snapshots es +1 en Product Backlog y 0 en las demás columnas. No permite saber qué tarjetas se movieron, cuándo ocurrió cada movimiento, ni calcular throughput.

## Lectura del flujo disponible

El WIP visible es 2 en Code Review / Testing (límite 2) y 0 en In Progress (límite 3). La etapa de revisión está en el límite; la respuesta prudente es atender esas tarjetas y sus bloqueos antes de iniciar más trabajo, de acuerdo con la política Kanban del equipo.

La inspección de Insights encontró un Burn up y un gráfico de conteo por estado; no se encontró un CFD guardado que muestre el flujo acumulado por estado a través del tiempo. Los conteos actuales y dos snapshots aislados no bastan para calcular lead time, cycle time, throughput semanal/Sprint ni interpretar tendencias de CFD. Tampoco hay eventos históricos por tarjeta para reconstruirlos con fiabilidad.

Esta lectura es una nota de análisis basada en el tablero, no un acta ni evidencia de que se haya celebrado una retrospectiva formal. El equipo trabaja de forma informal y asíncrona.

## Registro hacia adelante

- Al cierre de cada periodo que el equipo quiera analizar, registrar en [kanban-periodos-sesion7.csv](kanban-periodos-sesion7.csv) su identificador, fechas inicio/fin y enlace de fuente.
- Por cada tarjeta, guardar en [kanban-items-sesion7.csv](kanban-items-sesion7.csv) su identificador y las fechas observadas de entrada al Backlog, primer movimiento a In Progress, paso a Done (cumple DoD) y despliegue/operación. Usar ISO-8601 (`YYYY-MM-DD` o timestamp) y enlazar la evidencia. No rellenar retrospectivamente fechas que no estén disponibles.
- Guardar un nuevo snapshot fechado en [kanban-snapshots-sesion7.csv](kanban-snapshots-sesion7.csv). Con varios snapshots se puede comparar WIP; para calcular tiempos y throughput hacen falta eventos por tarjeta.
- Ejecutar `python scripts/kanban_metrics.py` cuando existan periodos y eventos. El script calcula throughput por fecha de Done, cycle time desde el primer In Progress hasta Done y lead time desde Backlog hasta despliegue/operación. Informa mediana, rango y cantidad de datos disponibles; no transforma campos vacíos en ceros.
- Al revisar el flujo, anotar el cuello de botella y una acción de mejora verificable en el canal o issue que el equipo use. No presentar esta recomendación como acuerdo ya tomado.

Las plantillas están vacías porque el tablero consultado no aporta los eventos históricos necesarios. El resultado del cálculo seguirá indicando que faltan datos hasta que el equipo capture periodos y movimientos reales.
