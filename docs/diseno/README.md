# Sprint de Diseno y cierre de Sprint 1

Este paquete documenta el diseno arquitectonico del sistema AgroValle Connect y enlaza los artefactos comprobables en el repositorio. Se basa en la implementacion actual; no se presenta como una fase historica realizada antes de programar. El [issue #46](https://github.com/santixit/agrovalle-connect/issues/46) registra el visto bueno informal y asíncrono de los mockups y el Sprint Discovery retrospectivo, además de las decisiones de historias y esquema. No se inventan la fecha de esas actividades ni quién dio el visto bueno. Hay confirmaciones explícitas de algunos integrantes; la confirmación de quienes no comentaron sigue pendiente. Véase el [registro retrospectivo de la Sesión 6](registro-retrospectivo-sesion-6.md).

## Artefactos

- Modelo entidad-relacion implementado: fuentes [conceptual](modelo-entidad-relacion.mmd) y [fisica](modelo-datos-der.mmd), alineadas con las migraciones actuales. Se retiraron sus antiguos exports graficos para que no sigan apareciendo junto con los nuevos disenos.
- [Snapshot DDL PostgreSQL](schema.sql). Es una consolidacion legible de Flyway V1-V4 para consulta y evaluacion. Las migraciones siguen siendo la unica fuente ejecutable del esquema; no ejecutar este snapshot en paralelo con Flyway.
- [Arquitectura MVC en cuatro capas y patrones](arquitectura-mvc-gof.md).
- Fuentes UML editables de componentes, despliegue y secuencia: [diagrama-componentes.puml](diagrama-componentes.puml), [diagrama-despliegue.puml](diagrama-despliegue.puml) y [diagrama-secuencia.puml](diagrama-secuencia.puml). Se quitaron las imagenes PNG y el PDF de las versiones visuales anteriores.
- [Prototipos de interfaz](mockups-ui.pdf), actualizados con los wireframes y mockups aportados para inicio, detalle y publicacion. Las seis laminas se conservan como PNG en [mockups/](mockups/); la interfaz estatica se ajusto al patron visual y mantiene sus flujos/API actuales. El issue #46 registra que el visto bueno fue informal y asíncrono; la fecha de actividad y la persona que lo comunicó no constan.
- Modelos de datos aportados como propuesta: [DER conceptual propuesto](modelo-entidad-relacion-propuesta.pdf) y [DER fisico propuesto](modelo-datos-der-propuesta.pdf), con sus [imagenes originales](mockups/). La [reconciliacion con el esquema implementado](reconciliacion-der.md) identifica equivalencias y diferencias; estos DER siguen siendo propuestas y no reemplazan los modelos canonicos ni el DDL.
- [Decisiones de normalizacion](decisiones-normalizacion.md), con la decisión del issue #46 de conservar el esquema actual y dejar los catálogos como mejora futura; otras preguntas siguen pendientes.
- Comparacion de las historias de usuario del backlog y Sprint Planning: [../evidencias/comparacion-historias-backlog-sprint1.md](../evidencias/comparacion-historias-backlog-sprint1.md).
- Especificacion IEEE 29148 y comparacion BDD: [../ieee29148-req01.md](../ieee29148-req01.md).
- Los contratos HTTP detallados de HU-01, HU-07 y HU-04 estan en [../api-sprint-1.md](../api-sprint-1.md); las 15 historias y escenarios BDD estan en [../../BACKLOG.md](../../BACKLOG.md).

## Alcance

La consigna de esta guia exige terminar HU-01, HU-07 y HU-04 en Sprint 1. Se conserva la capacidad de 10 SP ya acordada (5 + 2 + 3). La guia tambien menciona Sprint 2; se deja fuera de este trabajo para respetar la decision del equipo de no iniciarlo todavia.

No se preparo un PDF unico de entrega. Los artefactos quedan separados en esta carpeta y en `docs/ieee29148-req01.md`, como indica la guia, para revisarlos y versionarlos en GitHub.

## Prototipos entregados

El PDF de mockups agrupa primero las tres pantallas de baja fidelidad y despues las tres de alta fidelidad (inicio, detalle y publicar). Los originales PNG se guardan en `mockups/` para que puedan editarse o compararse. La pantalla de publicar refleja los datos soportados por el DTO vigente; no se agrega una carga de imagen ficticia porque el contrato y el backend actual no persisten imagenes.

Los dos diagramas ER suministrados se publican como referencias de propuesta. El [DER implementado](modelo-entidad-relacion.mmd), el [DER fisico implementado](modelo-datos-der.mmd) y las migraciones `V1` a `V4` siguen describiendo el estado real de la aplicacion. Se retiraron de este PR los exports graficos anteriores de esos modelos.

## Cobertura de la guía de Sesión 6

| Entregable de la guía | Evidencia en el repositorio | Estado honesto |
|---|---|---|
| Modelo de datos, DER y DDL PostgreSQL | DER implementado, DER conceptuales/físicos propuestos, `schema.sql` y migraciones Flyway V1–V4. | Artefactos disponibles. La conformidad del modelo con 3FN no está validada: el issue #46 confirma conservar el esquema actual y posponer catálogos normalizados, pero no resuelve todas las dependencias del dominio. |
| Mapeo JPA y pruebas | Entidades en `src/main/java/.../domain/` y pruebas automatizadas en `src/test/`. | Implementado en el código; no se afirma aquí una nueva ejecución de las pruebas. |
| MVC en capas y patrones | [Arquitectura y patrones](arquitectura-mvc-gof.md). | Documentado con base en el código existente. |
| UML de secuencia, componentes y despliegue | Fuentes PlantUML [secuencia](diagrama-secuencia.puml), [componentes](diagrama-componentes.puml) y [despliegue](diagrama-despliegue.puml). | Las tres fuentes editables están disponibles; no se publican exports PNG/PDF de esos diagramas en este paquete. |
| Wireframes/mockups de baja y alta fidelidad | [PDF de mockups](mockups-ui.pdf) y seis PNG fuente en `mockups/`. | Artefactos disponibles; el issue #46 registra el visto bueno informal y asíncrono de manera retrospectiva. |
| 15 historias con BDD y contratos REST | Escenarios de las 15 HU en [`BACKLOG.md`](../../BACKLOG.md); contratos de Sprint 1 en [`api-sprint-1.md`](../api-sprint-1.md). | Documentado. El issue #46 confirma Backlog como base de HU-01, HU-07 y HU-04 y adopta los criterios técnicos del plan como aceptación. |

## Validaciones del equipo pendientes

- Equipo: la versión base de HU-01, HU-07 y HU-04 y sus criterios técnicos se documentan según issue #46. Sigue pendiente la confirmación de integrantes que aún no comentaron.
- Equipo: revisar que el modelo conceptual y las decisiones de normalizacion representen las reglas reales del negocio.
- Peer review: el PR #23 se aprobo despues de fusionarse; esa aprobacion no demuestra peer review previo. Los futuros PR deben aprobarse antes del merge.
- Sprint 1: conservar el run CI y evidencias funcionales existentes, verificando que correspondan al commit que se entrega.
- DoD: el archivo actual es una lista de verificacion, no tiene firmas verificables; las firmas deben agregarlas los integrantes si la docente las solicita.
- Sprint Discovery: el issue #46 confirma su realización informal y asíncrona y su registro retrospectivo. La fecha exacta, participantes y secuencia frente a la codificación no están documentadas; las confirmaciones de quienes no comentaron siguen pendientes. Véase el [registro retrospectivo](registro-retrospectivo-sesion-6.md).
- 3FN: la guía lo solicita, pero su cumplimiento no se puede dar por validado con la decisión del issue #46; revisar las dependencias funcionales y normalización antes de declararlo conforme o cambiar el esquema.
