# Sprint Review — Sprint 2

> **Revisión documental validada.** El equipo confirmó que Sprint 2 comenzó, que el trabajo conversado pertenece a esta iteración y encargó a Raúl Santiago Carrillo Hernández, Product Owner, validar este resumen. No se afirma que haya ocurrido una reunión formal.

## Contexto

- Periodo de referencia: **2026-10-19 a 2026-10-29**. El inicio se reconstruye desde el primer registro local fechado de trabajo; el cierre fue indicado por el Product Owner.
- Fuente de esta revisión: artefactos del repositorio y trabajo local asociado al proyecto hasta el 2026-10-28.
- Modalidad de revisión: revisión documental asíncrona; confirmar si el equipo también hizo una demostración.
- Responsable de la validación del registro: Raúl Santiago Carrillo Hernández, Product Owner, por delegación comunicada por el equipo.

## Incremento revisado

| Resultado del trabajo | Evidencia | Estado y alcance observado |
|---|---|---|
| Mockups y wireframes corregidos | [PDF y fuentes PNG](diseno/README.md) | Se conservaron siete wireframes y siete mockups. La documentación distingue las vistas conectadas a la API de las capacidades que aún son solo locales o no tienen endpoint. |
| Flujos de interfaz del marketplace | [README del proyecto](../README.md) y [documentación de diseño](diseno/README.md) | Se trabajó sobre inicio, catálogo, detalle, registro, publicación y paneles. La foto solo tiene vista previa local; el historial de precios por periodos y parte de la gestión administrativa no están respaldados por endpoints. |
| Estrategia de fletes | [Patrones y arquitectura](diseno/README.md) | Se incorporó cálculo por peso y municipio mediante Strategy con tarifas demostrativas. Las tarifas son supuestos académicos, no una tarifa comercial validada. |
| Diagramas UML editables y exportados | [Fuentes y exports UML](diseno/exports/README.md) | Hay diagramas de componentes, despliegue y secuencias de registro y publicación, en PNG y en un PDF consolidado. Las secuencias de reserva, contacto y despacho aportadas se conservan como referencias de flujo. |
| Esquema y normalización | [Snapshot SQL](diseno/schema.sql), [revisión de normalización](diseno/decisiones-normalizacion.md) y [reconciliación del DER](diseno/reconciliacion-der.md) | El snapshot consolida Flyway V1–V6. La conclusión de 3FN sigue siendo provisional hasta confirmar reglas de dominio pendientes; el snapshot no se ha presentado como ejecución limpia contra PostgreSQL. |
| Evidencia Kanban | [Registro y métricas](evidencias/kanban-metricas-sesion7.md) | Se organizaron los snapshots disponibles y se explicitaron las métricas que no pueden calcularse. El último snapshot guardado es del 2026-10-19 y no representa el tablero actual. |

## Calidad y límites de la evidencia

- La ejecución de GitHub Actions **#83** en `main`, commit `6c19aae3cc74df7150777de5aeee1c4b06b63531`, terminó en verde: Java/Checkstyle/JaCoCo y E2E aprobaron. Consulta el [run #83](https://github.com/santixit/agrovalle-connect/actions/runs/40816952882) y descarga su [artefacto JaCoCo](https://github.com/santixit/agrovalle-connect/actions/runs/40816952882/artifacts/12873380141).
- El HTML JaCoCo de esa ejecución reporta 89% de instrucciones, 63% de ramas y 91% de líneas; los tres porcentajes superan 60%. Se conservaron capturas locales de [Actions #83](evidencias/ci-run-83.png) y del [resumen JaCoCo](evidencias/jacoco-main-6c19aae.png), además del reporte HTML descargado.
- El árbol de código y pruebas de la rama local coincide con `main` en `6c19aae`; las diferencias locales pendientes son documentación/diseño y no forman parte del commit del run. Por eso #83 respalda el código actual, pero la documentación aún debe integrarse para que el enlace público al repositorio contenga el paquete. Las métricas anteriores de 90,5% de instrucciones, 63,6% de ramas y 92,0% de líneas son de otra ejecución y no se mezclan con el reporte #83.
- Las métricas Kanban disponibles no permiten afirmar throughput, cycle time, lead time, una CFD confiable ni Ley de Little.
- Los cambios que se están preparando para esta entrega permanecen locales, de acuerdo con la decisión del equipo de subirlos después.

## Comentarios, aceptación y decisiones

- El equipo informa que no tiene comentarios adicionales que registrar sobre el incremento en este momento.
- El Product Owner valida que el resumen representa el trabajo realizado, por encargo del equipo.
- La documentación señala como pendientes la confirmación completa de 3FN y la captura de CI, cobertura y estado Kanban que correspondan al commit final de entrega.
- La validación del Product Owner no reemplaza las evidencias de CI, cobertura, Kanban ni la decisión de dominio pendiente para completar la revisión de 3FN.

## Resultado de la revisión

El proyecto cuenta con artefactos de diseño, interfaz, arquitectura y pruebas que pueden demostrarse como trabajo de Sprint 2. Para que el incremento quede listo para evaluación, aún se debe actualizar la evidencia al mismo commit final y completar las decisiones del equipo señaladas arriba. No se registra trabajo rechazado porque no se reportó una decisión de rechazo.

## Validación del resumen

El equipo confirmó que el resumen está bien y encargó la revisión final a Raúl Santiago Carrillo Hernández, Product Owner. **Validación: realizada. Fecha: 2026-10-28.** Registro: confirmación del equipo comunicada por Raúl y anotada en este documento. El periodo se registra como 2026-10-19 a 2026-10-29 con las salvedades indicadas en Contexto.
