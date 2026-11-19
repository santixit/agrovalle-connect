# Proyección de sprints y validación del backlog

**Actualizada:** 2026-11-19. Esta proyección distingue desarrollo implementado de aceptación funcional. No asigna fechas de sprints pasados que no estén registradas.

## Historia conocida y estado de trabajo

- Sprint 1 documenta HU-01 (5 SP), HU-07 (2 SP) y HU-04 (3 SP): 10 SP.
- Sprint 2 tiene review y retrospectiva documentales sobre el trabajo visible, pero no se conserva selección inicial verificable de HUs o SP.
- Para Sprint 3 el usuario eligió validar y obtener aceptación de HU-02 (5 SP), HU-06 (3 SP), HU-09 (3 SP) y HU-10 (3 SP). Sus 14 SP describen esfuerzo de implementación estimado en el Backlog, no el compromiso de validación.
- El resultado JUnit de 37 pruebas aprobado el 2026-11-19 cubre parte de la validación técnica comunicada. Demo en staging y aceptación individual del PO siguen sin evidencia.
- La capacidad histórica de 12–14 SP se conserva como referencia indicada en clase; no se presenta como velocidad medida del equipo.

## Siguiente trabajo recomendado

| Iteración | Alcance de validación sugerido | Esfuerzo de trabajo | Dependencia |
|---|---|---:|---|
| Sprint 3 (en validación) | Completar gate CI, demo sintética y aceptación de HU-02, HU-06, HU-09, HU-10. | 10 SP estimados para actividades de validación en el [plan Sprint 3](sprint-3/planning.md); no es compromiso histórico. | CI del commit final, staging disponible y decisión por HU del PO. |
| Sprint 4 (proyección) | Revisar aceptación de HU-05 y HU-08; demostrar contacto y reserva/carrito con control de inventario. | 13 SP históricos de implementación; validar capacidad y estimación de las tareas de aceptación al planificar. | HU-02/HU-06/HU-10 y catálogo disponible; datos sintéticos. |
| Sprints posteriores | Revisar criterios de HUs restantes conforme al orden del Product Backlog, comenzando por Must y dependencias técnicas. | Reestimar con velocity observada, no con capacidad supuesta. | Resultados de Sprint 3/4 y aceptación real del PO. |

## Capacidad matemática del backlog

El Backlog contiene 79 SP para 15 historias. La tabla solo proyecta cuántas iteraciones cabrían bajo una capacidad hipotética; no constituye calendario ni compromiso:

| Alcance | SP | A 14 SP por sprint | A 12 SP por sprint |
|---|---:|---:|---:|
| Backlog completo | 79 | 6 | 7 |
| Must + Should + Could (excluye HU-15 Won’t) | 66 | 5 | 6 |
| Solo Must | 34 | 3 | 3 |

Las divisiones se redondean hacia arriba. La velocidad real solo puede calcularse con SP comprometidos al inicio y terminados conforme al DoD. Véase el [reporte de métricas](../metrics/sprint-metrics-corte3.md).
