# Métricas ágiles de flujo — Tercer corte

## Estado y regla de evidencia

Este reporte separa métricas calculables de datos que faltan. Las cifras del PDF del laboratorio (por ejemplo, 10/8 SP o 4,2 días de Lead Time) aparecen como ejemplos de formato y **no se registran como mediciones reales** porque no están respaldadas por eventos verificables del tablero.

Fuentes locales: [políticas Kanban](../kanban-policies.md), [procedimiento de captura y cálculo](captura-de-metricas.md), [evidencia de Sesión 7](../evidencias/kanban-metricas-sesion7.md), [snapshots](../evidencias/kanban-snapshots-sesion7.csv), [periodos](../evidencias/kanban-periodos-sesion7.csv), [eventos por tarjeta](../evidencias/kanban-items-sesion7.csv) y [script reproducible](../../scripts/kanban_metrics.py).

## 1. Velocity

Velocity requiere comparar los SP comprometidos al inicio de cada sprint con los SP que terminaron al 100% del DoD. La planificación de Sprint 1 documenta 10 SP seleccionados, pero no hay un registro verificable de SP completados contra el DoD. Para Sprint 2 no existe una selección formal de HUs y suma comprometida verificable. Sprint 3 valida historias ya implementadas; sus 14 SP del backlog son estimaciones históricas de implementación y no un compromiso nuevo de sprint.

| Sprint | Comprometidos | Completados conforme al DoD | Resultado |
|---|---:|---:|---|
| Sprint 1 | 10 SP documentados | Sin dato verificable | No calcular porcentaje |
| Sprint 2 | Sin dato verificable | Sin dato verificable | No calculable |
| Sprint 3 | Sin compromiso formal registrado | Sin dato verificable | No calculable |

No se calcula promedio de velocity a partir de filas incompletas.

## 2. Burndown

No se encontraron puntos restantes registrados por día para Sprint 3. Sin puntos iniciales y observaciones fechadas, no es válido dibujar una línea ideal/real ni afirmar que el Sprint Goal se cumplió en un día particular. La plantilla para capturar futuros periodos está en `kanban-periodos-sesion7.csv`; debe completarse con fuente y fecha.

## 3. Cumulative Flow Diagram (CFD) y WIP

Hay dos snapshots históricos de conteos de la vista Backlog, del 17 y 19 de octubre de 2026. No son un CFD: no muestran la serie de movimientos por estado entre esas fechas.

| Fecha observada | Product Backlog | Sprint Backlog | In Progress | Code Review / Testing | Done |
|---|---:|---:|---:|---:|---:|
| 2026-10-17 | 16 | 0 | 0 | 2 | 12 |
| 2026-10-19 | 17 | 0 | 0 | 2 | 12 |

En la revisión del tablero del 19 de noviembre de 2026, HU-02, HU-06, HU-09 y HU-10 aparecían en `Done`; Sprint Backlog estaba en 0, In Progress en 0/3 y Code Review / Testing en 0/2. Ese snapshot actualiza solo el estado visible en esa fecha; no reconstruye un CFD.

## 4. Lead Time y Cycle Time

No hay fechas por tarjeta verificadas para entrada a Backlog, primer paso a In Progress, Done y despliegue. Por eso no se calculan medianas, promedios ni rangos.

- **Lead Time:** desde la creación/entrada de una necesidad a Backlog hasta que el cambio está desplegado y operativo.
- **Cycle Time:** desde el primer movimiento a In Progress hasta que el trabajo cumple DoD y llega a Done.

## 5. Resultado que sí puede reportarse

| Medida | Resultado disponible | Interpretación permitida |
|---|---|---|
| WIP al revisar el tablero el 2026-11-19 | In Progress 0/3; Code Review / Testing 0/2 | Colas vacías en ese instante; no informa duración ni throughput. |
| Historias seleccionadas para validación de Sprint 3 | HU-02, HU-06, HU-09 y HU-10 | Alcance elegido por el usuario; sus 14 SP son estimaciones históricas de implementación, no compromiso de validación. |
| Suite local de Sprint 3 reportada el 2026-11-19 | 37 pruebas, 0 fallos, 0 errores, 0 omitidas | Resultado de Maven compartido por el usuario; no es dato de flujo Kanban ni run de CI. |
| Velocity, burndown, CFD, throughput, Lead Time, Cycle Time | No calculables con la evidencia conservada | No completar con aproximaciones, fechas de commits o snapshots aislados. |

Este es el resultado final del periodo documental revisado: solo WIP puntual y el resultado de pruebas son observables; no hay una métrica de flujo histórica reproducible. Cuando se inicie un nuevo periodo de medición, guardar snapshots y eventos según [captura-de-metricas.md](captura-de-metricas.md); no reutilizar el 0/3 y 0/2 como resultado del periodo siguiente.

## Datos necesarios para cerrar el reporte

1. Acordar periodos de Sprint y SP comprometidos antes de iniciar cada uno.
2. Registrar el cierre de cada HU que cumpla DoD y su estimación original.
3. Capturar por tarjeta las fechas reales de Backlog, primer In Progress, Done y despliegue.
4. Guardar snapshots fechados para el CFD; los snapshots aislados no permiten inferir movimientos.
5. Ejecutar `python scripts/kanban_metrics.py` cuando las plantillas contengan evidencia suficiente y conservar el resultado.

El equipo debe revisar los datos y confirmar las decisiones antes de presentar una métrica como resultado del Sprint.
