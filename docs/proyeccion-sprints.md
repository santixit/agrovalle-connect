# Proyección de Sprint 3 y Sprint 4

**Estado:** propuesta para revisión del equipo y del Product Owner. No registra compromisos ni aprobaciones que aún no se hayan confirmado.

## Contexto mínimo

- Sprint 0 y el Sprint de Diseño son actividades separadas; sus artefactos no se suman a los Story Points de producto.
- Sprint 1 tiene documentadas HU-01 (5 SP), HU-07 (2 SP) y HU-04 (3 SP): **10 SP**.
- Sprint 2 cuenta con planificación, revisión y retrospectiva documentales, pero no se encontró una selección verificable de historias. No se le atribuyen HUs ni SP retrospectivamente.
- La capacidad de **12–14 SP por sprint** es la referencia indicada en clase; no es una velocidad medida del equipo.

## Historias candidatas

| Iteración | Historias candidatas | Total | Dependencia / foco |
|---|---|---:|---|
| **Sprint 3** | **HU-02 (5 SP, Must), HU-06 (3 SP, Must), HU-09 (3 SP, Should), HU-10 (3 SP, Must)** | **14 SP** | Registrar finca, publicar la cosecha y comprobar el estado y el detalle de la oferta. La finca habilita asociar ubicación a la publicación; estado y detalle se verifican sobre una oferta existente. |
| **Sprint 4** | **HU-05 (5 SP, Must), HU-08 (8 SP, Must)** | **13 SP** | Contactar y reservar ofertas publicadas. La reserva debe validar disponibilidad y evitar sobreventa. |

La propuesta ubica las historias **Must** en Sprint 1, Sprint 3 y Sprint 4. El conjunto Must suma **34 SP** y, si el equipo acepta esta distribución, quedaría cubierto al cierre propuesto de Sprint 4. La selección histórica de Sprint 2 no está documentada, por lo que no se atribuye parte de ese alcance a dicho sprint.

Las historias del backlog aparecen implementadas en el estado técnico descrito por el README. Por eso esta proyección sirve para decidir qué criterios faltan por validar o aceptar; no afirma que todo ese código aún deba desarrollarse.

## Estimación general del backlog

| Alcance | Story Points | Con capacidad de 14 SP | Con capacidad de 12 SP |
|---|---:|---:|---:|
| Backlog completo, 15 historias | **79** | **6 sprints** (79 ÷ 14 = 5,64; redondeo hacia arriba) | **7 sprints** (79 ÷ 12 = 6,58; redondeo hacia arriba) |
| Must + Should + Could, excluye HU-15 Won’t | **66** | **5 sprints** | **6 sprints** |
| Solo Must | **34** | **3 sprints** | **3 sprints** |

El cálculo estima capacidad total, no fija el calendario de iteraciones posteriores. Sprint 3 y Sprint 4 son las dos candidatas proyectadas para el siguiente periodo de cuatro semanas, con duración máxima de dos semanas cada una.

## Antes de registrar el compromiso

- Confirmar en GitHub Projects el estado real de las historias y si ya fueron aceptadas.
- Revisar la distribución, dependencias, capacidad y criterios BDD con el Product Owner y el equipo.
- Registrar las fechas y el Sprint Goal definitivo solo cuando el equipo acuerde la planificación.
- Ajustar esta proyección si una historia ya está aceptada o si cambia la capacidad observada.
