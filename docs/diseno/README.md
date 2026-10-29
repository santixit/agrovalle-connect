# Sprint de Diseno y cierre de Sprint 1

Este paquete documenta el diseno arquitectonico del sistema AgroValle Connect y enlaza los artefactos comprobables en el repositorio. Se basa en la implementacion actual; no se presenta como una fase historica realizada antes de programar. El [issue #46](https://github.com/santixit/agrovalle-connect/issues/46) registra el visto bueno informal y asíncrono de los mockups y el Sprint Discovery retrospectivo, además de las decisiones de historias y esquema. No se inventan la fecha de esas actividades ni quién dio el visto bueno. Hay confirmaciones explícitas de algunos integrantes; la confirmación de quienes no comentaron sigue pendiente. Véase el [registro retrospectivo de la Sesión 6](registro-retrospectivo-sesion-6.md).

## Artefactos

- Modelo entidad-relacion implementado: fuentes [conceptual](modelo-entidad-relacion.mmd) y [fisica](modelo-datos-der.mmd), alineadas con las migraciones actuales.
- [Snapshot DDL PostgreSQL](schema.sql). Consolida en orden Flyway V1–V7 para consulta y evaluación. Las migraciones siguen siendo la única fuente ejecutable; no ejecutar este snapshot en paralelo con Flyway.
- [Arquitectura MVC en cuatro capas y patrones](arquitectura-mvc-gof.md).
- [Patrones GoF de comportamiento: Strategy y Observer](patrones.md), con problema de negocio, diagramas de clases y pruebas vinculadas al código actual.
- Fuentes UML editables de componentes, despliegue y secuencia: [diagrama-componentes.puml](diagrama-componentes.puml), [diagrama-despliegue.puml](diagrama-despliegue.puml), [secuencia de registro](diagrama-secuencia.puml) y [secuencia de publicación](diagrama-secuencia-publicacion.puml). Sus exportaciones están en [exports UML](exports/README.md).
- [Prototipos de interfaz corregidos](mockups-ui.pdf): siete wireframes y siete mockups aportados para inicio, catálogo, detalle, registro, publicación y paneles de agricultor y administrador. Las 14 láminas están disponibles como PNG fuente en [mockups/](mockups/). La interfaz integra los flujos que admite la API; las métricas de contactos, ofertas y pedidos se consultan en los endpoints existentes. El promedio de precio continúa siendo acumulado en un periodo corto y no un historial por semanas. El borrador se guarda en el navegador y la foto solo tiene vista previa local porque el servicio actual no persiste imágenes. La gestión de usuarios del panel administrador y las cifras separadas por rol aún no tienen endpoints; no se muestran valores de ejemplo como datos reales.
- Diagramas de secuencia entregados como referencias visuales en [secuencias/](secuencias/): registro, autenticación y publicación, reserva, contacto y programación de despacho. El archivo de referencia del 22 de octubre queda identificado como versión anterior del flujo de publicación. Los diagramas que muestran una cola asíncrona describen el flujo propuesto en las láminas; el Observer implementado actualmente procesa sus eventos de forma síncrona.
- Modelos de datos aportados como propuesta: [DER conceptual propuesto](modelo-entidad-relacion-propuesta.pdf) y [DER físico propuesto](modelo-datos-der-propuesta.pdf), con imágenes fuente [conceptual](mockups/modelo-entidad-relacion-v2.png) y [física](mockups/modelo-datos-der-v2.png). Las imágenes proporcionadas corresponden a estos artefactos existentes. Los mockups están en [mockups/](mockups/) e incluidos en [mockups-ui.pdf](mockups-ui.pdf). La [reconciliación con el esquema implementado](reconciliacion-der.md) identifica equivalencias y diferencias; estos DER siguen siendo propuestas y no reemplazan los modelos canónicos ni el DDL.
- [Decisiones de normalizacion](decisiones-normalizacion.md), con la implementación local de parcela, titularidad, ubicación y transferencias; los catálogos de municipio/categoría siguen como mejora futura.
- Comparacion de las historias de usuario del backlog y Sprint Planning: [../evidencias/comparacion-historias-backlog-sprint1.md](../evidencias/comparacion-historias-backlog-sprint1.md).
- Especificacion IEEE 29148 y comparacion BDD: [../ieee29148-req01.md](../ieee29148-req01.md).
- Alcance reconstruido de Sprint 2: [../sprint-2-planning.md](../sprint-2-planning.md). El equipo confirmó que Sprint 2 comenzó y que el trabajo conversado pertenece a esa iteración; faltan la fecha, el objetivo y la selección formal de historias.
- Los contratos HTTP detallados de HU-01, HU-07 y HU-04 estan en [../api-sprint-1.md](../api-sprint-1.md); las 15 historias y escenarios BDD estan en [../../BACKLOG.md](../../BACKLOG.md).

## Alcance

La consigna de esta guia exige terminar HU-01, HU-07 y HU-04 en Sprint 1. Se conserva la capacidad de 10 SP ya acordada (5 + 2 + 3). El equipo confirmó posteriormente que Sprint 2 comenzó; su fecha, objetivo y alcance formal siguen pendientes de registrar.

Los artefactos fuente del Sprint de Diseño permanecen separados y versionados en esta carpeta y en `docs/ieee29148-req01.md`, como indica la guía. El PDF único de entrega del Segundo Corte incorpora un apartado de Diseño y Arquitectura con los 7 wireframes, 7 mockups y 6 diagramas de secuencia recibidos; este PDF no reemplaza las fuentes editables ni el resto de los artefactos de diseño.

## Prototipos entregados

El PDF de mockups agrupa primero las siete pantallas de baja fidelidad y después las siete de alta fidelidad. Los originales PNG se guardan en `mockups/`. La interfaz implementa las vistas de inicio, catálogo, detalle, registro, publicación, panel agricultor y panel administrador; las áreas todavía no respaldadas por una API se identifican sin datos inventados. La foto se previsualiza localmente y no se envía al servidor; el borrador se conserva en el almacenamiento local del navegador.

Los dos diagramas ER suministrados se conservan como referencias de propuesta. El [DER implementado](modelo-entidad-relacion.mmd), el [DER físico implementado](modelo-datos-der.mmd) y las migraciones `V1` a `V7` describen el estado de esta rama. La [revisión de 3FN](decisiones-normalizacion.md) y el [modelo de parcelas/productos](propuesta-modelo-3fn.md) documentan las dependencias implementadas.

## Cobertura de la guía de Sesión 6

| Entregable de la guía | Evidencia en el repositorio | Estado honesto |
|---|---|---|
| Modelo de datos, DER y DDL PostgreSQL | DER implementado, DER conceptuales/físicos propuestos, `schema.sql` y migraciones Flyway V1–V7. | Snapshot reconstruido en orden de las migraciones; V7 y el modelo 3FN están integrados en `main` según las reglas de dominio documentadas. Actions #103 pasó las pruebas y controles de calidad sobre el incremento. |
| Mapeo JPA y pruebas | Entidades en `src/main/java/.../domain/` y pruebas automatizadas en `src/test/`. | Implementado y comprobado en la ejecución local del 2026-10-19. PostgreSQL 16 se verifica en CI. |
| MVC en capas y patrones | [Arquitectura y patrones](arquitectura-mvc-gof.md). | Documentado con base en el código existente. |
| UML de secuencia, componentes y despliegue | Fuentes PlantUML [secuencia](diagrama-secuencia.puml), [componentes](diagrama-componentes.puml) y [despliegue](diagrama-despliegue.puml); diagramas Mermaid adicionales en [`../uml/README.md`](../uml/README.md). | Exportaciones visibles PNG por fuente y PDF consolidado en [`exports/`](exports/). |
| Wireframes/mockups de baja y alta fidelidad | [PDF de mockups](mockups-ui.pdf), 14 PNG fuente y las vistas responsivas en la aplicación. | Flujos implementados y conectados con la API disponible. El borrador y la previsualización de foto son locales; el backend aún no guarda imágenes. El historial de precios de varios períodos sigue pendiente: el endpoint actual solo proporciona un promedio acumulado. El issue #46 registra el visto bueno informal y asíncrono de manera retrospectiva. |
| 15 historias con BDD y contratos REST | Escenarios de las 15 HU en [`BACKLOG.md`](../../BACKLOG.md); contratos de Sprint 1 en [`api-sprint-1.md`](../api-sprint-1.md). | Documentado. El issue #46 confirma Backlog como base de HU-01, HU-07 y HU-04 y adopta los criterios técnicos del plan como aceptación. |
| README con visión, integrantes, diagrama y badges | [`README.md`](../../README.md), gráfico Mermaid y badge del workflow CI. | Disponible. El badge enlaza al workflow; el resultado de una ejecución concreta debe consultarse en GitHub Actions. |
| Planificación de Sprint 2 | [Alcance reconstruido](../sprint-2-planning.md). | El equipo confirmó que Sprint 2 comenzó y que el trabajo conversado pertenece a esa iteración. Faltan fecha, Sprint Goal y selección inicial verificable; este documento no los inventa. |
| Calidad ejecutable de Sprint 1 | Ejecución local `mvnw clean verify` del 2026-10-19 y workflow CI registrado en la evidencia de Sesión 8. | Última ejecución local: 41 pruebas Java aprobadas, 0 fallos/errores, 0 violaciones de Checkstyle y JaCoCo pasó sus umbrales configurados. Esto no equivale por sí solo a revisión por pares ni a una aprobación del Product Owner. |
| DoD técnico firmado y peer review | [`dod.md`](../dod.md) contiene una lista de verificación sin marcar; las evidencias de revisión deben estar en PRs. | Pendiente: no hay firmas en el archivo. No se inventan. La revisión independiente previa al merge debe comprobarse en el PR correspondiente; una revisión posterior no la sustituye. |

## Validaciones del equipo pendientes

- Equipo: la versión base de HU-01, HU-07 y HU-04 y sus criterios técnicos se documentan según issue #46. Sigue pendiente la confirmación de integrantes que aún no comentaron.
- Equipo: revisar que el modelo conceptual y las decisiones de normalizacion representen las reglas reales del negocio.
- Peer review: el PR #23 se aprobo despues de fusionarse; esa aprobacion no demuestra peer review previo. Para cerrar el criterio de la guia, debe existir evidencia de una revisión independiente previa al merge del cambio evaluado.
- Sprint 1: la verificacion local del 2026-10-19 paso (`45` pruebas, `0` fallos/errores, `0` violaciones Checkstyle y JaCoCo dentro de umbral). Al presentar la entrega, enlazar el CI verde y comprobar que corresponde al commit evaluado.
- DoD: el archivo actual es una lista de verificacion, no tiene firmas verificables; las firmas deben agregarlas los integrantes si la docente las solicita.
- Sprint Discovery: el issue #46 confirma su realización informal y asíncrona y su registro retrospectivo. La fecha exacta, participantes y secuencia frente a la codificación no están documentadas; las confirmaciones de quienes no comentaron siguen pendientes. Véase el [registro retrospectivo](registro-retrospectivo-sesion-6.md).
- 3FN: implementado localmente; revisar la [normalización](decisiones-normalizacion.md) y ejecutar las pruebas antes de integrar.
- Sprint 2: el equipo confirma que la iteración comenzó y que el trabajo conversado pertenece a ella. Siguen pendientes la fecha de inicio, el Sprint Goal, la selección de historias y su fuente verificable; véase el [alcance reconstruido](../sprint-2-planning.md).
