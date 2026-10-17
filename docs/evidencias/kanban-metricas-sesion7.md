# Evidencia Kanban y metricas — Sesion 7

## Fuente y fecha

Snapshot manual de la vista Backlog del proyecto GitHub Agrovalle-Kamba, revisada el 17 de octubre de 2026. Es una medicion puntual del estado visible, no un historico exportado.

| Columna | Elementos observados | Limite WIP configurado |
|---|---:|---:|
| Product Backlog | 16 | Sin limite |
| Sprint Backlog / To Do | 0 | Sin limite reportado |
| In Progress | 0 | 3 |
| Code Review / Testing | 2 | 2 |
| Done | 12 | Sin limite |

Los dos elementos de Code Review / Testing visibles son issues/tarjetas del proyecto (T06.1 y T06.2), no objetos Pull Request. Por eso el limite se interpreta y aplica a elementos de trabajo activos, como indica la politica del tablero; la descripcion anterior que lo llamaba “2 Pull Requests” era imprecisa. La politica corregida esta en [kanban-policies.md](../kanban-policies.md).

## Metricas que se pueden sostener

- WIP en el snapshot: 2 elementos en revision/pruebas y 0 en progreso; ambos dentro de los limites 2 y 3.
- La vista Insights consultada presenta un grafico de estado y el grafico predeterminado Burn up. No se encontro un CFD guardado con una ventana historica verificable.
- Con una sola observacion no se pueden calcular lead time, cycle time, throughput semanal/Sprint ni interpretar tendencias de un CFD. Los conteos acumulados de Backlog y Done no son throughput de un periodo.

## Seguimiento recomendado para la proxima retrospectiva

Guardar al cierre de cada Sprint un export/snapshot con fecha y los elementos incluidos, registrar movimientos de entrada/salida para lead y cycle time y habilitar/revisar el CFD. Anotar un cuello de botella observado y una accion con responsable y fecha de comprobacion. No reconstruir fechas que GitHub no conserva ni presentar el snapshot actual como serie historica.
