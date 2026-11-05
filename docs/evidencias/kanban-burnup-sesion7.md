# Lectura del Burn up y datos disponibles para la CFD — Sesión 7

**Fecha de consulta:** 2026-11-05.  
**Fuente:** vista Insights del [GitHub Project](https://github.com/users/santixit/projects/3/insights), filtro visible `is:issue`.  
**Tipo de evidencia:** lectura visual de la vista en GitHub; no se obtuvo exportación CSV ni captura archivada en el repositorio.

## Qué se observó

La vista seleccionada es un gráfico **Burn up** con series `Open` y `Completed` durante el periodo visible 2026-10-22 a 2026-11-05. La lectura visual de los valores mostrados en la gráfica fue:

| Tramo visible | Open | Completed | Interpretación limitada |
|---|---:|---:|---|
| 2026-10-22 a 2026-10-28 | aproximadamente 19 | 0 | El gráfico muestra una base de trabajo abierto y ninguna tarjeta completada en el tramo. |
| 2026-10-29 a 2026-11-04 | aproximadamente 22 | aproximadamente 2 | Aumenta el conteo abierto y aparecen pocas tarjetas completadas. |
| 2026-11-05 | aproximadamente 1 | aproximadamente 23 | En el punto final visible, la serie muestra una bajada marcada de `Open` y una subida de `Completed`. |

Los valores se transcribieron de una gráfica interactiva y se presentan como aproximados por tramo; no son una exportación de cada punto diario. Insights mostró **35 elementos coincidentes** para el filtro, mientras que las series visibles del último punto sumaban aproximadamente 24 (`Open` + `Completed`). La diferencia de 11 no está explicada en la vista consultada. Por esa inconsistencia no se usa el Burn up para declarar un total definitivo del backlog, un throughput o una tasa de terminación.

El salto visible alrededor del último punto puede deberse a cambios de estado, de alcance/filtro o a una actualización en lote. La gráfica no permite identificar qué tarjetas cambiaron ni cuándo ocurrió cada transición; no se atribuye una causa al equipo.

## Qué demuestra y qué no

- **Sí permite afirmar:** GitHub Insights mostraba un Burn up en ese periodo, con una variación visual pronunciada al final y un conteo coincidente que no concuerda con la suma de las series.
- **No es un CFD:** el Burn up compara trabajo abierto/completado; una CFD requiere el volumen de elementos en **cada estado** del flujo a lo largo del tiempo.
- **No permite calcular throughput confiable:** no hay una lista completa de eventos de entrada a Done por periodo y existe la discrepancia 35 vs. aproximadamente 24.
- **No permite calcular cycle time ni lead time:** no se conservaron fechas de transición por tarjeta.
- **No permite aplicar la Ley de Little:** no hay series completas de WIP, throughput y tiempos para validar un flujo estable.
- **No se reconstruyen días ni transiciones** restando snapshots, leyendo fechas de commits o suponiendo que una subida de `Completed` fue trabajo hecho ese mismo día.

## Estado actual del tablero consultado

En la vista del tablero revisada el 2026-11-05, las columnas operativas mostraban **In Progress 0/3** y **Code Review / Testing 0/2**. El encabezado/listado indicaba 35 elementos coincidentes, lo cual no se debe confundir con 35 elementos terminados. Es un snapshot del día de consulta, no una serie histórica. Los WIP configurados siguen siendo In Progress ≤3 y Code Review / Testing ≤2.

## Qué se necesita para producir una CFD reproducible

1. Exportar el historial de eventos del proyecto o llevar un registro fechado de cuántas tarjetas había en cada estado al cierre de cada día/periodo.
2. Guardar el CSV original junto con la fecha de extracción, filtro, vista y enlace a la fuente.
3. Registrar por tarjeta su transición entre Backlog, To Do, In Progress, Code Review/Testing y Done; no inferir estados intermedios.
4. Verificar que la suma por estado coincida con el total de elementos del mismo filtro y periodo.
5. Generar la CFD desde esos datos y revisar que WIP total, entradas y salidas coincidan con el historial.

Las plantillas existentes [`kanban-snapshots-sesion7.csv`](kanban-snapshots-sesion7.csv), [`kanban-periodos-sesion7.csv`](kanban-periodos-sesion7.csv) y [`kanban-items-sesion7.csv`](kanban-items-sesion7.csv) siguen disponibles para capturar datos verificables. Esta lectura del Burn up mejora el análisis de flujo, pero no sustituye la CFD ni completa sus datos faltantes.
