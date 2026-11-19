# Checklist de cierre — Sprint 3

**Actualizado:** 2026-11-19. Los estados describen evidencia disponible y no reconstruyen una reunión.

## Estado por criterio

| Criterio | Estado | Evidencia / próximo paso |
|---|---|---|
| Alcance HU-02, HU-06, HU-09 y HU-10 | Definido | Selección explícita del usuario; ver [planificación](planning.md). |
| Pruebas automatizadas seleccionadas | Cumplido según resultado comunicado | Maven: `BUILD SUCCESS`, 37 pruebas, 0 fallos, 0 errores, 0 omitidas. Revisar el reporte Surefire para detalle por método. |
| Mapeo BDD ↔ JUnit por HU | Documentado en el plan; falta adjuntar el reporte individual | Usar los nombres de método indicados en `planning.md` y conservar `target/surefire-reports`. |
| Checkstyle / JaCoCo / E2E para el commit que se presentará | No acreditado para este cierre | Consultar el workflow de la rama/PR final y enlazar el run. |
| Demo en staging con datos sintéticos | No realizada o no registrada | Ejecutar los cuatro flujos con una cuenta y datos de prueba; guardar evidencias en `docs/sprints/sprint-3/evidencias/`. |
| Aceptación del PO por HU | No registrada | Después de la demo, registrar decisión `Aceptada` o `Requiere cambios`, fecha, responsable y comentario en la matriz de review. |
| Sincronización tablero / review / DoD | Por hacer después de la decisión PO | Actualizar tarjetas para que no contradigan la review. |

## Evidencia Maven comunicada

```text
Tests run: 37, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

El fragmento es la transcripción del resultado que compartió el usuario el 2026-11-19 y está conservado en [resultado-maven-2026-11-19.md](evidencias/resultado-maven-2026-11-19.md). El enlace a un log descargable no se conserva; no se debe presentar como artefacto de CI.

## Decisión actual por historia

| HU | Resultado JUnit agregado | Demo | Aceptación PO | Estado que puede afirmarse |
|---|---|---|---|---|
| HU-02 | Suite aprobada; resultado individual no publicado | Sin evidencia | No registrada | Aceptación funcional no acreditada |
| HU-06 | Suite aprobada; resultado individual no publicado | Sin evidencia | No registrada | Aceptación funcional no acreditada |
| HU-09 | Suite aprobada; resultado individual no publicado | Sin evidencia | No registrada | Aceptación funcional no acreditada |
| HU-10 | Suite aprobada; resultado individual no publicado | Sin evidencia | No registrada | Aceptación funcional no acreditada |

## Criterio para declarar cerrado el sprint

El cierre documental procede cuando CI del commit final pasa, la demo de staging tiene evidencia segura, el PO decide cada HU y el tablero refleja la misma decisión. Hasta entonces el Sprint 3 permanece **en validación**; no se marcan casillas para tareas que todavía no tienen evidencia.
