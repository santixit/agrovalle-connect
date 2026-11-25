# Sprint 3 — análisis de avance y mejora

**Fecha de actualización del análisis:** 2026-11-25
**Modalidad:** análisis documental del estado disponible; no es acta de una retrospectiva formal ni una sesión que se declare celebrada.

## Evidencia revisada

- El usuario eligió HU-02, HU-06, HU-09 y HU-10 como alcance de validación y aceptación.
- La suite seleccionada se ejecutó con éxito: 37 pruebas, 0 fallos, 0 errores y 0 omitidas.
- El código contiene pruebas relacionadas con publicación, creación de finca, cambio de estado y detalle de oferta.
- El pipeline integrado a main y la verificación técnica de staging pasaron en el [workflow de CI/CD](https://github.com/santixit/agrovalle-connect/actions/runs/44596816750).
- Sigue sin encontrarse evidencia de la demo funcional de las cuatro historias ni decisión del PO por historia.

## Hallazgos y acciones

| Hallazgo basado en evidencia | Acción de mejora | Responsable operativo | Verificación de cierre |
|---|---|---|---|
| El resultado Maven se compartió como salida agregada; no identifica el resultado por HU. | Conservar el reporte Surefire y una matriz que enlace criterios BDD con métodos de prueba. | Autor de la evidencia técnica. | Los cuatro renglones de la matriz apuntan a métodos concretos y el reporte agregado está adjunto/enlazado. |
| Pruebas automatizadas y aceptación funcional son evidencias distintas. | Ejecutar la demostración con datos sintéticos y pedir decisión individual por historia. | Quien realiza la demo; el PO registra la decisión. | Capturas seguras y decisión fechada de cada HU. |
| El run de CI/CD del commit integrado a main pasó, pero el sprint aún no tiene evidencia de demo funcional ni aceptación. | Mantener enlazado el run verde y completar la demostración con datos sintéticos y la decisión del PO por HU. | Responsable de la demo y PO. | Evidencia funcional segura y decisión fechada por historia. |
| El tablero muestra `Done`, pero el registro del PO está separado. | Mantener un campo/nota de aceptación funcional y actualizar `Done` según el DoD del equipo. | Responsable de la tarjeta y PO. | Estado del tablero coincide con review y evidencia. |

## Resultado

El principal aprendizaje es separar validación técnica, readiness de staging, demostración funcional y aceptación. CI/CD ya acredita las dos primeras; la demo de negocio y la decisión del PO aún deben quedar enlazadas antes del cierre. Este análisis documental no atribuye hallazgos ni acciones a una retrospectiva formal del equipo.
