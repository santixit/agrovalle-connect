# Sprint 3 — revisión del incremento de validación

**Actualizado:** 2026-11-19
**Estado:** revisión técnica parcial documentada; no hay evidencia de demo en staging ni de aceptación funcional del PO. No se presenta como acta de una ceremonia celebrada.

## Resultado automatizado disponible

El usuario compartió la salida de Maven del 2026-11-19: **37 pruebas ejecutadas, 0 fallos, 0 errores, 0 omitidas; `BUILD SUCCESS`**. La transcripción y el alcance están en [resultado-maven-2026-11-19.md](../../evidencias/sprint-3/resultado-maven-2026-11-19.md). El resultado acredita la ejecución local de la suite seleccionada. No acredita por sí solo la ejecución de Checkstyle, JaCoCo, E2E, una demo de producto ni la aceptación del PO.

## Matriz de revisión por historia

| Historia | Pruebas en código / alcance | Resultado técnico disponible | Demo en staging | Decisión funcional del PO |
|---|---|---|---|---|
| HU-02 — publicar cosecha | Pruebas de autenticación, publicación, fecha pasada y escenario integrado; `AgrovalleConnectApplicationTests`. | Incluida en la suite comunicada de 37; el resultado es agregado, no individual por método. | No hay evidencia guardada. | No registrada; por ello no se declara aceptada. |
| HU-06 — registrar finca | Escenario integrado y `FincaRepositoryTests`. | Incluida en la suite comunicada de 37; el resultado es agregado. | No hay evidencia guardada. | No registrada; por ello no se declara aceptada. |
| HU-09 — cambiar estado | Pruebas de rechazo a otro propietario, inventario agotado y escenario integrado. | Incluida en la suite comunicada de 37; el resultado es agregado. | No hay evidencia guardada. | No registrada; por ello no se declara aceptada. |
| HU-10 — consultar detalle | Prueba de detalle inactivo y escenario integrado de datos/privacidad. | Incluida en la suite comunicada de 37; el resultado es agregado. | No hay evidencia guardada. | No registrada; por ello no se declara aceptada. |

## Comparación con el criterio de cierre

| Criterio | Estado al 2026-11-19 | Evidencia que lo cierra |
|---|---|---|
| Pruebas JUnit 5 de la suite seleccionada | **Cumplido según salida compartida** | Maven: 37/37, sin fallos, errores ni omitidas. |
| Checkstyle, JaCoCo y E2E del estado final | **No acreditado para este incremento** | Resultado verde del workflow asociado al commit que se presenta. |
| Demostración funcional en staging | **No acreditada** | Evidencia sintética por flujo, vinculada a ambiente, fecha y commit. |
| Decisión del PO por cada HU | **No registrada** | `Aceptada` o `Requiere cambios`, comentario y fecha por HU. |

## Conclusión

La evidencia actual permite decir que la suite seleccionada pasó en la ejecución local reportada. No permite declarar Sprint 3 aceptado ni cerrado. Las cuatro historias conservan el estado **“aceptación funcional no acreditada”** hasta que se realice la demostración y exista una decisión explícita por historia. `Done` en GitHub Projects se mantiene como estado técnico del trabajo y no se utiliza como sustituto de dicha decisión.

## Referencias

- [Planificación y matriz de pruebas](planning.md)
- [Checklist operativo de cierre](checklist-inicio.md)
- [Backlog BDD](../../requirements/product-backlog-bdd.md)
- [Definition of Done](../../dod.md)
- [`AgrovalleConnectApplicationTests`](../../../src/test/java/co/edu/uniajc/agrovalle/AgrovalleConnectApplicationTests.java)
