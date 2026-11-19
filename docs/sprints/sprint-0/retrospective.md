# Sprint 0 — análisis retrospectivo de artefactos

**Fecha de registro:** 2026-11-19
**Modalidad:** análisis posterior de la documentación y configuración actuales; no es una retrospectiva formal ni atribuye opiniones a integrantes.

## Hechos observables

- La configuración técnica se puede inspeccionar directamente en Maven, Checkstyle, Husky y GitHub Actions.
- La definición de terminado y el flujo de ramas tienen documentos consultables junto al código.
- La entrega original de Sprint 0 se conserva como PDF bajo `docs/entregables/sprint-0/`.
- No se encontró en el repositorio un acta fechada con asistentes, acuerdos de Sprint 0 o compromisos de mejora de una retrospectiva formal.

## Aprendizajes y acciones adoptadas para el flujo futuro

| Aprendizaje derivado de la evidencia | Acción operativa desde esta versión | Cómo comprobarla | Responsable del paso |
|---|---|---|---|
| La configuración queda más fácil de revisar cuando existe una sola fuente versionada. | Mantener `pom.xml`, workflows, Checkstyle, Husky y DoD bajo control de versiones; actualizar la documentación cuando cambie uno de ellos. | Pull Request enlaza la configuración afectada y el documento correspondiente. | Autor del PR; revisión independiente verifica. |
| Un entregable no demuestra que se haya celebrado una ceremonia. | Los registros retrospectivos indican modalidad, fuente y límites; no se rellenan con asistentes o fechas inferidas. | Cada review/retro indica si es acta o análisis documental. | Autor del registro. |
| Los cambios deben ser revisables antes de llegar a `main`. | Todo cambio de producto/documentación se integra por Pull Request con CI verde y revisión independiente conforme a las reglas activas del repositorio. | PR fusionado con revisión y checks visibles. | Autor y revisor del PR. |

## Resultado

Esta revisión convierte los artefactos existentes en una referencia de trabajo y deja definido cómo registrar evidencia en adelante. No reconstruye acuerdos pasados ni declara una mejora histórica como ya aplicada. La lista de acciones anteriores queda sustituida por las reglas operativas de esta tabla.
