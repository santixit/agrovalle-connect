# Sprint 3 — revisión del incremento de validación

**Actualizado:** 2026-11-25
**Estado:** CI y readiness técnica de staging aprobadas; no hay evidencia de la demo funcional de las cuatro historias ni aceptación del PO. Este documento no se presenta como acta de una ceremonia celebrada.

## Resultado automatizado disponible

La salida Maven compartida reportó **37 pruebas ejecutadas, 0 fallos, 0 errores, 0 omitidas; `BUILD SUCCESS`** y está en [resultado-maven-2026-11-19.md](evidencias/resultado-maven-2026-11-19.md). Además, [CI de main y verificación de staging](https://github.com/santixit/agrovalle-connect/actions/runs/44596816750) acredita que Build/tests/Checkstyle/JaCoCo y el flujo crítico E2E pasaron en main. El mismo workflow aprobó el despliegue y la verificación técnica de staging. Ninguna de esas pruebas equivale a la demo funcional de Sprint 3 ni a la aceptación del PO.

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
| Checkstyle, JaCoCo y E2E del estado final | **Cumplido** | [CI de main y verificación de staging](https://github.com/santixit/agrovalle-connect/actions/runs/44596816750); el workflow del commit integrado a main terminó exitosamente. |
| Readiness técnica de staging | **Cumplida** | El workflow confirmó deploy, readiness, base, Flyway y tablas núcleo. |
| Demostración funcional en staging | **No acreditada** | Evidencia sintética por flujo, vinculada a ambiente, fecha y commit. |
| Decisión del PO por cada HU | **No registrada** | `Aceptada` o `Requiere cambios`, comentario y fecha por HU. |

## Conclusión

La evidencia actual permite afirmar que la suite Maven compartida pasó, que el pipeline completo del commit integrado a main pasó y que staging superó sus comprobaciones técnicas. Sprint 3 aún no está aceptado ni cerrado: las cuatro historias conservan el estado **“aceptación funcional no acreditada”** hasta que se realice la demo de negocio y exista una decisión explícita por historia. `Done` en GitHub Projects se mantiene como estado técnico del trabajo y no se utiliza como sustituto de dicha decisión.

## Referencias

- [Planificación y matriz de pruebas](planning.md)
- [Checklist operativo de cierre](checklist-inicio.md)
- [Backlog BDD](../../requirements/product-backlog-bdd.md)
- [Definition of Done](../../dod.md)
- [`AgrovalleConnectApplicationTests`](../../../src/test/java/co/edu/uniajc/agrovalle/AgrovalleConnectApplicationTests.java)
