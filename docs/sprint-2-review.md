# Sprint Review — Sprint 2

> **Revisión documental validada.** El equipo confirmó que Sprint 2 comenzó, que el trabajo conversado pertenece a esta iteración y encargó a Raúl Santiago Carrillo Hernández, Product Owner, validar este resumen. No se afirma que haya ocurrido una reunión formal.

## Contexto

- Periodo de referencia: **2026-10-19 a 2026-10-29**. El inicio se reconstruye desde el primer registro local fechado de trabajo; el cierre fue indicado por el Product Owner.
- Fuente de esta revisión: artefactos integrados en `main` y evidencias del proyecto revisadas hasta el 2026-10-29.
- Modalidad de revisión: revisión documental asíncrona. El 2026-10-29 se realizó una demostración técnica complementaria, posterior al trabajo del Sprint, sobre la aplicación pública en staging; no se registra como una ceremonia ni como una demo histórica del equipo.
- Responsable de la validación del registro: Raúl Santiago Carrillo Hernández, Product Owner, por delegación comunicada por el equipo.

## Incremento revisado

| Resultado del trabajo | Evidencia | Estado y alcance observado |
|---|---|---|
| Mockups y wireframes corregidos | [PDF y fuentes PNG](diseno/README.md) | Se conservaron siete wireframes y siete mockups. La documentación distingue las vistas conectadas a la API de las capacidades que aún son solo locales o no tienen endpoint. |
| Flujos de interfaz del marketplace | [README del proyecto](../README.md) y [documentación de diseño](diseno/README.md) | Se trabajó sobre inicio, catálogo, detalle, registro, publicación y paneles. La foto solo tiene vista previa local; el historial de precios por periodos y parte de la gestión administrativa no están respaldados por endpoints. |
| Comprobación técnica en staging | [Recorrido y resultados observados](evidencias/demo-staging-sprint2-2026-10-29.md) | El 2026-10-29, `GET /`, `GET /api/v1/health` y `GET /api/v1/productos` respondieron HTTP 200. La prueba fue de solo lectura; no demuestra un flujo autenticado ni constituye una demo de ceremonia anterior. |
| Estrategia de fletes | [Patrones y arquitectura](diseno/README.md) | Se incorporó cálculo por peso y municipio mediante Strategy con tarifas demostrativas. Las tarifas son supuestos académicos, no una tarifa comercial validada. |
| Diagramas UML editables y exportados | [Fuentes y exports UML](diseno/exports/README.md) | Hay diagramas de componentes, despliegue y secuencias de registro y publicación, en PNG y en un PDF consolidado. Las secuencias de reserva, contacto y despacho aportadas se conservan como referencias de flujo. |
| Esquema y normalización | [Snapshot SQL](diseno/schema.sql), [revisión de normalización](diseno/decisiones-normalizacion.md) y [reconciliación del DER](diseno/reconciliacion-der.md) | Flyway V7 y el código están integrados en `main`; incluyen parcelas, responsables actuales, ubicación normalizada, historial de transferencias y municipio derivado de la finca. Actions #103 ejecutó las pruebas y controles de calidad correctamente. |
| Evidencia Kanban | [Registro y métricas](evidencias/kanban-metricas-sesion7.md) | Se organizaron los snapshots disponibles y se explicitaron las métricas que no pueden calcularse. El último snapshot guardado es del 2026-10-19 y no representa el tablero actual. |

## Calidad y límites de la evidencia

- La ejecución más reciente de GitHub Actions para el push a `main`, **#103**, pasó en el commit `a7e2097`: Build, tests, Checkstyle, JaCoCo, E2E y despliegue a staging. Consulta el [run #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070) y su [artefacto JaCoCo](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/artifacts/12906676338).
- El HTML JaCoCo del mismo run #103 informa 88% de instrucciones, 63% de ramas y 90% de líneas; los umbrales superan 60%. El [log de Maven](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/job/123519248186#step:5:169) confirma que se cumplieron los controles. La captura #97 y el reporte #83 se conservan como evidencia histórica de sus respectivos commits.
- Las métricas Kanban disponibles no permiten afirmar throughput, cycle time, lead time, una CFD confiable ni Ley de Little.
- El paquete documental base está integrado en `main` mediante los PR #59, #60 y #61. Esta rama local actualiza el PDF con la evidencia #103 y los enlaces al reporte actual; estos cambios documentales todavía no se han publicado en `main`.

## Comentarios, aceptación y decisiones

- El equipo informa que no tiene comentarios adicionales que registrar sobre el incremento en este momento.
- El Product Owner valida que el resumen representa el trabajo realizado, por encargo del equipo.
- La documentación y el código integrados en `main` implementan las reglas del PO para parcelas, transferencias fechadas, municipio derivado de la finca, productos sin finca y contacto histórico. Actions #103 pasó sobre ese incremento. El PDF local registra el estado del commit `a7e2097` y conserva su captura de CI.
- No se encontró evidencia de una selección formal de historias para Sprint 2. Los PR #51 y #52 permiten contextualizar trabajo integrado, pero no se presentan como historias comprometidas. La guía para explicar la comprobación actual de staging está en el registro de demo complementaria.
- La validación del Product Owner no reemplaza las evidencias de CI, cobertura ni Kanban. El CI #103 ejecutó las pruebas automatizadas y el gate JaCoCo sobre el código integrado; la aceptación funcional del alcance sigue siendo responsabilidad del equipo.

## Resultado de la revisión

El proyecto cuenta con artefactos de diseño, interfaz, arquitectura y pruebas que pueden demostrarse como trabajo de Sprint 2. El incremento incluye la implementación local del modelo de parcelas y transferencias; antes de integrarlo aún se requiere revisión del equipo, ejecución de pruebas y CI en el commit final. No se registra trabajo rechazado porque no se reportó una decisión de rechazo.

## Validación del resumen

El equipo confirmó que el resumen está bien y encargó la revisión final a Raúl Santiago Carrillo Hernández, Product Owner. **Validación: realizada. Fecha: 2026-10-28.** Registro: confirmación del equipo comunicada por Raúl y anotada en este documento. El periodo se registra como 2026-10-19 a 2026-10-29 con las salvedades indicadas en Contexto.
