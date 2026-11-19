# Sprint 3 — análisis de avance y mejora

**Fecha de registro:** 2026-11-19
**Modalidad:** análisis documental del estado disponible; no es acta de una retrospectiva formal ni una sesión que se declare celebrada.

## Evidencia revisada

- El usuario eligió HU-02, HU-06, HU-09 y HU-10 como alcance de validación y aceptación.
- La suite seleccionada se ejecutó con éxito: 37 pruebas, 0 fallos, 0 errores y 0 omitidas.
- El código contiene pruebas relacionadas con publicación, creación de finca, cambio de estado y detalle de oferta.
- No se encontró evidencia de una demo de esas cuatro historias en staging, un resultado de CI específico para este cierre o una decisión del PO por historia.

## Hallazgos y acciones

| Hallazgo basado en evidencia | Acción de mejora | Responsable operativo | Verificación de cierre |
|---|---|---|---|
| El resultado Maven se compartió como salida agregada; no identifica el resultado por HU. | Conservar el reporte Surefire y una matriz que enlace criterios BDD con métodos de prueba. | Autor de la evidencia técnica. | Los cuatro renglones de la matriz apuntan a métodos concretos y el reporte agregado está adjunto/enlazado. |
| Pruebas automatizadas y aceptación funcional son evidencias distintas. | Ejecutar la demostración con datos sintéticos y pedir decisión individual por historia. | Quien realiza la demo; el PO registra la decisión. | Capturas seguras y decisión fechada de cada HU. |
| No se encontró un run de calidad asociado a este cierre. | Esperar el workflow de la rama/PR final y registrar build, tests, Checkstyle, JaCoCo y E2E. | Autor del PR; revisor verifica. | Run verde enlazado al commit revisado. |
| El tablero muestra `Done`, pero el registro del PO está separado. | Mantener un campo/nota de aceptación funcional y actualizar `Done` según el DoD del equipo. | Responsable de la tarjeta y PO. | Estado del tablero coincide con review y evidencia. |

## Resultado

El principal aprendizaje es separar validación técnica, demostración y aceptación. La suite aprobada reduce riesgo técnico, pero las tres evidencias deben quedar enlazadas antes del cierre. Este análisis sustituye una plantilla vacía; no atribuye los hallazgos ni las acciones a una conversación retrospectiva del equipo.
