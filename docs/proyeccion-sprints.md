# Proyección de sprints de AgroValle Connect

**Estado:** proyección inicial para revisar con el equipo y el Product Owner. No acredita planificación, compromiso, inicio ni aceptación formal de los sprints futuros.

## Criterios usados

- El Sprint 0 es infraestructura y el Sprint de Diseño es una iteración separada; ninguno consume Story Points de historias del producto.
- Sprint 1 conserva la asignación documentada: HU-01, HU-07 y HU-04, por 10 SP.
- Sprint 2 se conserva como trabajo realizado entre 2026-10-19 y 2026-10-29, con revisión y retrospectiva documentales. Como no se encontró una selección verificable de historias, no se le asignan HUs ni SP retrospectivamente.
- Las historias futuras se agrupan por dependencias y flujo funcional. La capacidad de 12–14 SP por sprint es una referencia mencionada en clase, no una velocidad medida del equipo.
- Los puntos de la tabla son las estimaciones existentes en [`BACKLOG.md`](../BACKLOG.md). La proyección distribuye trabajo para revisión y aceptación; no afirma que las funcionalidades aún no existan en el código.

## Propuesta para la siguiente entrega

| Iteración | Historias propuestas | Total | Enfoque y dependencias |
|---|---|---:|---|
| Sprint 0 | Infraestructura | 0 SP | Configuración base del repositorio y entorno. Se mantiene separado de las historias de producto. |
| Sprint de Diseño | Artefactos de diseño | 0 SP | Discovery, modelo de datos, DDL, arquitectura, UML y mockups. Se registra aparte de Sprint 0 y del cálculo de historias. |
| Sprint 1 — documentado | HU-01 (5), HU-07 (2), HU-04 (3) | 10 SP | Registro, perfil público y filtro de ofertas. La planificación de Sprint 1 ya establece esta selección. |
| Sprint 2 — alcance reconstruido | Sin asignación verificable | No calculable | La revisión y retrospectiva documentan el trabajo de la iteración, pero no hay una lista comprobable de HUs comprometidas ni puntos consumidos. |
| **Sprint 3 — propuesta para el siguiente corte** | **HU-02 (5), HU-06 (3), HU-09 (3), HU-10 (3)** | **14 SP** | Publicar y mantener ofertas consultables. HU-06 debe estar disponible para asociar finca al publicar HU-02; HU-09 y HU-10 se verifican sobre una oferta publicada. |
| **Sprint 4 — propuesta para el siguiente corte** | **HU-05 (5), HU-08 (8)** | **13 SP** | Contactar y reservar una oferta. Ambas historias dependen de ofertas publicadas; HU-08 debe comprobar disponibilidad e impedir sobreventa. |
| Sprint 5 — propuesta | HU-11 (5), HU-12 (8) | 13 SP | Completar el flujo posterior a la interacción: notificar el contacto y programar el despacho. HU-11 depende de HU-05; HU-12 depende de una reserva de HU-08. |
| Sprint 6 — propuesta | HU-03 (5), HU-13 (8) | 13 SP | Consultar precios regionales y trazabilidad. HU-13 depende del despacho de HU-12 y de eventos del pedido. HU-03 necesita transacciones de prueba completadas. |
| Reserva opcional | HU-14 (3) | 3 SP | Historia Could have. No se propone un sprint dedicado de 3 SP; el Product Owner puede incorporarla si una refinación encuentra capacidad o intercambia alcance. |
| Fuera del incremento actual | HU-15 (13) | 13 SP | La prioridad del Backlog es Won’t have this increment. Solo se estima como sprint futuro si el Product Owner cambia el alcance. |

Los Sprints 3 y 4 son las dos iteraciones candidatas para una ventana de cuatro semanas con una duración máxima de dos semanas cada una. El equipo debe confirmar alcance, fechas, capacidad real y criterios de aceptación antes de tratarlos como compromisos.

## Cálculo de capacidad y alcance

| Alcance contado | SP | A 14 SP por sprint | A 12 SP por sprint |
|---|---:|---:|---:|
| Todo el Backlog, incluidas HU-14 y HU-15 | 79 | 6 sprints (79 ÷ 14 = 5,64; se redondea hacia arriba) | 7 sprints (79 ÷ 12 = 6,58; se redondea hacia arriba) |
| Must, Should y Could; excluye HU-15 fuera del incremento | 66 | 5 sprints | 6 sprints |
| Must del Backlog | 34 | 3 sprints | 3 sprints |

La distribución propuesta ubica las historias Must en Sprint 1, Sprint 3 y Sprint 4: Sprint 1 suma 10 SP de Must; Sprint 3 suma 11 SP Must y 3 SP Should; Sprint 4 suma 13 SP Must. Si el alcance propuesto se acepta, el conjunto Must queda cubierto al cierre de Sprint 4. Sprint 2 no se cuenta para esa conclusión porque su selección histórica no está documentada.

El cálculo de 79 SP equivale a seis o siete sprints de capacidad teórica. La distribución funcional no fuerza a que cada iteración tenga exactamente 12–14 SP: se conserva Sprint 1 con 10 SP, se reserva HU-14 sin crear un sprint ineficiente y se deja HU-15 fuera del incremento. La velocidad observada y las prioridades del Product Owner deben ajustar la proyección.

## Revisiones antes de aceptar la propuesta

- Confirmar en el tablero qué historias están aceptadas, abiertas o aún pendientes; el estado implementado en el código no equivale a aceptación funcional.
- Revisar con el Product Owner el orden de dependencias y los escenarios BDD de Sprint 3 y Sprint 4.
- Confirmar si la capacidad histórica del equipo está cerca de 12–14 SP o si se debe recalcular la cantidad de sprints.
- Registrar la selección aprobada, el Sprint Goal, las tareas y la fecha de cada sprint en su planificación correspondiente.
- Actualizar esta proyección cuando cambien el alcance, las estimaciones o el resultado de una revisión.
