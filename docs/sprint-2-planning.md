# Propuesta técnica para revisión de Sprint 2

**Estado: borrador para validar con el equipo.** No es evidencia de una sesión de planificación ni de un Sprint 2 ya iniciado o ejecutado. El repositorio describe trabajo informal y asíncrono, sin fechas ni roles permanentes; por eso no se asignan responsables ni se inventan acuerdos. La propuesta organiza la revisión del incremento existente y no plantea volver a implementar historias que el README marca como implementadas.

## Objetivo propuesto

Revisar la integridad de los flujos de publicación, consulta, contacto, reserva y despacho ya presentes en el código, y completar evidencia verificable por historia antes de considerar cerrado el incremento. El equipo debe confirmar si este objetivo corresponde a su siguiente iteración.

## Historias candidatas

La lista corresponde a las historias del Backlog que no aparecen en la selección de Sprint 1 documentada en `docs/sprint-1-planning.md`. Las historias HU-02 a HU-14 figuran implementadas en el README. Esta tabla propone validar ese estado; no demuestra que el equipo las haya asignado a un Sprint 2.

| HU | Prioridad del Backlog | Evidencia de implementación existente | Revisión propuesta |
|---|---|---|---|
| HU-02 | Must have | Publicación autenticada en `ProductoController`/servicios; flujo navegador Playwright. | Verificar permisos, validación del DTO, persistencia y respuesta; conservar evidencia del flujo. |
| HU-03 | Should have | `PrecioRegionalService` y pruebas de servicio. | Revisar ventana temporal, máximo de transacciones y caso sin ventas contra el BDD. |
| HU-05 | Must have | `ContactoController`, `ContactoService` y notificaciones. | Verificar acceso del comprador, asociación oferta/agricultor y persistencia. |
| HU-06 | Must have | `FincaController`, `FincaService` y asociación opcional a producto. | Comprobar autorización del propietario y restricciones del modelo. |
| HU-08 | Must have | `ReservaService`, control transaccional de inventario y E2E. | Validar concurrencia, consolidación de cantidades y rollback ante stock insuficiente. |
| HU-09 | Should have | `GestionProductoService` y transición de estado. | Revisar autorización del agricultor dueño y efecto en el catálogo. |
| HU-10 | Must have | Consulta de detalle de oferta y DTO público. | Comprobar que no se expongan datos sensibles y que un ID inexistente tenga respuesta controlada. |
| HU-11 | Should have | Eventos transaccionales y `NotificacionObserver`. | Revisar persistencia y destinatario de notificaciones de contacto/pedido. |
| HU-12 | Should have | Programación de despacho asociada a pedidos. | Verificar precondiciones de estado y persistencia de la fecha/ruta. |
| HU-13 | Should have | `TrazabilidadService` y eventos del pedido. | Verificar orden cronológico y autorización del comprador propietario. |
| HU-14 | Could have | `FavoritoService` y relación con restricción de unicidad. | Comprobar agregar, consultar, quitar y duplicado. |

HU-15 permanece fuera del incremento según su prioridad `Won't have this increment` en el Backlog. Cualquier cambio de alcance requiere una decisión verificable del equipo.

## Criterios de salida propuestos

- El equipo confirma o modifica la selección y el objetivo mediante un registro verificable; no basta este borrador.
- Cada HU seleccionada tiene trazabilidad entre BDD, endpoint/regla, prueba automatizada y evidencia de persistencia cuando aplique.
- La suite de CI pasa con Java 17, PostgreSQL, Checkstyle y JaCoCo; los cambios de interfaz crítica conservan el E2E.
- Un integrante distinto del autor revisa los cambios en el Pull Request antes de integrarlos, cuando el flujo del equipo permita esa revisión.
- La documentación registra resultados y pendientes sin inventar fechas de actividad, reuniones, asignaciones fijas ni firmas.

## Verificación documental pendiente

- Confirmar en equipo que esta propuesta constituye el alcance de la siguiente iteración.
- Acordar prioridad, capacidad y estimaciones de trabajo si se requiere tratarlas como un Sprint formal.
- Identificar qué evidencia de revisión puede verificarse en Pull Requests para las historias ya implementadas.
