# Lista paso a paso para Sprint 3

Esta propuesta valida y busca la aceptación de HU-02, HU-06, HU-09 y HU-10. No se registra ninguna aprobación hasta que exista evidencia de pruebas y decisión del Product Owner (PO).

## 1. Revisar el tablero

- [x] Confirmar el estado de HU-02, HU-06, HU-09 y HU-10 en GitHub Projects.

  **Resultado (19 de noviembre de 2026):** las cuatro están en `Done`; `Sprint Backlog (To Do)` está vacío; `In Progress` muestra 0/3 y `Code Review / Testing` 0/2. `Done` no equivale automáticamente a aceptación del PO.

## 2. Confirmar qué significa “aceptada”

- [ ] Leer cada escenario de las cuatro historias en [`product-backlog-bdd.md`](../../requirements/product-backlog-bdd.md).
- [ ] Pedir al PO que confirme que esos escenarios describen lo que espera ver.
- [ ] Registrar el nombre del PO, fecha y decisión para cada historia después de la revisión.

## 3. Preparar pruebas automatizadas

- [x] Reforzar la prueba integrada para listar la finca, encontrar la cosecha en catálogo, revisar precio/cantidad y privacidad del detalle, y comprobar que `AGOTADO` la quite del catálogo.
- [x] Ejecutar en PowerShell, desde la raíz del repositorio:

  ```powershell
  .\mvnw.cmd '-Dtest=AgrovalleConnectApplicationTests,GestionProductoServiceTest,CatalogoProductoServiceTest,FincaRepositoryTests' test
  ```

- [x] Confirmar el resultado de Maven y registrar el total de pruebas.

  **Resultado reportado (19 de noviembre de 2026):** `BUILD SUCCESS`; 37 pruebas ejecutadas, 0 fallos, 0 errores y 0 omitidas. La ejecución anterior que quedó sin resumen no se usa como resultado; este resumen corresponde a la ejecución completada después.

## 4. Demostrar el flujo con datos sintéticos

- [ ] Usar únicamente la base de pruebas/staging, nunca la base de producción.
- [ ] Registrar o seleccionar un agricultor sintético y su finca.
- [ ] Publicar una cosecha con fecha de hoy o posterior.
- [ ] Consultar el detalle y confirmar producto, finca, agricultor, precio y cantidad, sin cédula ni credenciales.
- [ ] Cambiar el estado a `AGOTADO` y consultar el catálogo para confirmar que ya no aparece.
- [ ] Guardar capturas sin tokens, contraseñas ni información real bajo `docs/evidencias/sprint-3/`.

## 5. Obtener aceptación del PO

- [ ] Mostrar al PO las pruebas y la demostración.
- [ ] Para cada criterio, registrar `Aceptado`, `Requiere cambios` o `Pendiente`, junto con el comentario del PO.
- [ ] Corregir los rechazos y repetir pruebas/demostración.
- [ ] Cambiar la historia a `Done` solo cuando cumpla la definición de terminado del equipo y tenga la aceptación requerida.

  | Historia | Decisión del PO | Responsable/fecha | Evidencia o comentario |
  |---|---|---|---|
  | HU-02 | Pendiente | — | — |
  | HU-06 | Pendiente | — | — |
  | HU-09 | Pendiente | — | — |
  | HU-10 | Pendiente | — | — |

## 6. Formalizar la planificación

- [ ] Acordar Sprint Goal, fechas, responsables y capacidad real con el equipo.
- [ ] Estimar las tareas de validación; los 14 SP son estimaciones históricas de implementación del backlog, no la capacidad de este Sprint 3.
- [ ] Actualizar esta lista y `planificacion.md` con las decisiones efectivamente tomadas.
