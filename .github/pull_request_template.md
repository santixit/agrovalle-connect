## Resumen

Explica qué problema resuelve este cambio y qué resultado aporta. Menciona la HU, requisito o issue relacionado.

## Trazabilidad

- HU / REQ / issue: #
- Sprint o iniciativa:
- ¿Cierra una historia completa según el DoD?: sí / no / cambio transversal

## Tipo de cambio

- [ ] Funcionalidad (`feat`)
- [ ] Corrección (`fix`)
- [ ] Pruebas (`test`)
- [ ] Refactorización (`refactor`)
- [ ] Documentación (`docs`)
- [ ] Configuración, mantenimiento o CI (`chore` / `ci`)

## Cambios incluidos

- Describe el comportamiento agregado o corregido.
- Indica decisiones de diseño o límites relevantes.
- Menciona componentes, rutas o documentos afectados.

## Verificación

| Comprobación aplicable | Resultado / evidencia |
|---|---|
| Comando ejecutado (por ejemplo, `./mvnw --batch-mode --no-transfer-progress clean verify`) | |
| Pruebas unitarias e integración (JUnit 5) | |
| Checkstyle y umbral JaCoCo | |
| Flujo E2E o prueba manual (indicar entorno y resultado) | |
| CI de GitHub Actions | |
| Validación en staging, si aplica | |

Si una comprobación no aplica o no se pudo ejecutar, indícalo y explica el motivo. No marques como aprobado algo que siga pendiente.

## Datos, API e interfaz

- [ ] No cambia la base de datos.
- [ ] Agrega una nueva migración Flyway; no modifica migraciones ya aplicadas.
- [ ] Cambia un endpoint o contrato REST; se documentan rutas, roles, validaciones y errores.
- [ ] Cambia la interfaz; incluyo capturas sin datos personales.
- [ ] Añade o cambia datos de prueba; son sintéticos y seguros.
- [ ] No aplica.

Describe compatibilidad, impacto y pasos de despliegue o reversión cuando correspondan. Nunca incluyas credenciales, tokens ni datos personales reales.

## Evidencias

Enlaza resultados de CI, reportes, capturas, escenarios BDD o una demo. Para cada criterio de aceptación, indica dónde se ve la evidencia. Si falta una evidencia, deja el estado explícito como pendiente.

## Lista de revisión

- [ ] El cambio corresponde al alcance descrito y no contiene archivos generados o temporales innecesarios.
- [ ] Los criterios BDD afectados se relacionan con pruebas automatizadas o una justificación.
- [ ] La documentación, contratos, diagramas y ejemplos afectados están actualizados.
- [ ] Revisé autorización, validación de entradas, privacidad y mensajes de error.
- [ ] No hay secretos, credenciales ni datos personales en cambios, pruebas, capturas o logs.
- [ ] Los controles de CI aplicables están verdes.
- [ ] Solicité revisión independiente; atendí los comentarios bloqueantes.
- [ ] La historia solo se pasará a Done después de cumplir el Definition of Done y registrar la aceptación necesaria.

## Revisor/a

Persona distinta de quien implementó el cambio:
