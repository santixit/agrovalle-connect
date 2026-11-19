# Métricas de flujo

Esta carpeta separa el procedimiento de medición de la lectura del periodo.

| Documento | Uso |
|---|---|
| [Captura reproducible](captura-de-metricas.md) | Define eventos, fuentes y pasos de registro para calcular métricas en periodos futuros. |
| [Reporte del tercer corte](sprint-metrics-corte3.md) | Expone los datos observables del periodo revisado y las métricas que no se pueden calcular. |

Los CSV de eventos y snapshots están en [evidencias](../evidencias/README.md), junto al análisis de Kanban. `scripts/kanban_metrics.py` procesa esos CSV cuando contienen registros completos. Las plantillas permanecen vacías cuando no existe una fuente verificable; no se rellenan con fechas de commits ni estimaciones.
