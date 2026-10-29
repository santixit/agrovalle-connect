# Cierre de entrega — Segundo corte

Este checklist prepara la entrega descrita en `Entregable_segundo_corte_agrovalle_connect.pdf`. Es un registro del trabajo y la evidencia del Sprint 2; no afirma que el equipo haya realizado ceremonias que no están documentadas.

## Datos confirmados para la portada

- Grupo/célula: **Grupo 1 — 6303B**.
- Integrantes confirmados: Danny Alexander Gómez; Michelle Guerrero Arboleda; Starlin Gómez Asprilla; Raúl Santiago Carrillo Hernández.
- La guía oficial solicita en la portada los números de documento de los cuatro integrantes. Por decisión explícita del Product Owner, esta copia local omite esos números; se trata de una excepción al requisito, no de un dato que la guía deje opcional.
- Sprint 2: periodo de referencia **2026-10-19 a 2026-10-29**. El equipo confirmó que el Sprint comenzó y que el trabajo conversado pertenece a él. El inicio es una reconstrucción basada en el primer registro local fechado; la fecha de cierre y el objetivo fueron definidos para este registro por el Product Owner.
- Objetivo reconstruido: dejar el incremento alineado con los mockups corregidos y preparado para revisión, documentando los flujos principales, estrategia de fletes, UML, esquema/DER y evidencia técnica/Kanban, con límites de integración claramente identificados.

## Lo que ya tiene evidencia en el repositorio

| Requisito | Evidencia disponible | Estado verificable |
|---|---|---|
| Historias de usuario y BDD | [`BACKLOG.md`](../../BACKLOG.md), [`docs/ieee29148-req01.md`](../ieee29148-req01.md) | HU-01 tiene una especificación formal y comparación BDD. |
| Modelo relacional, DER, DDL y mapeo JPA | [`docs/diseno/README.md`](../diseno/README.md), [`docs/diseno/schema.sql`](../diseno/schema.sql), migraciones Flyway y entidades JPA | Snapshot SQL consolidado a partir de V1–V6. La revisión de 3FN sigue condicionada a validar reglas de dominio; no presentarlo como certificación final. |
| MVC, SOLID y patrón GoF | [`docs/diseno/arquitectura-mvc-gof.md`](../diseno/arquitectura-mvc-gof.md), [`docs/diseno/patrones.md`](../diseno/patrones.md), [`docs/evidencias/solid-sesion7.md`](../evidencias/solid-sesion7.md) | Mapeo actualizado de SRP/OCP/LSP/ISP/DIP a ejemplos reales, con alcance y límites; Strategy y Observer están implementados. |
| UML de componentes, despliegue y secuencia | Fuentes PlantUML y [`docs/diseno/exports/`](../diseno/exports/) | PNG por diagrama y PDF consolidado de cuatro páginas publicados en `main` mediante PR #59. |
| Wireframes y mockups | [`docs/diseno/mockups-ui.pdf`](../diseno/mockups-ui.pdf) y fuentes PNG | Siete wireframes y siete mockups organizados. |
| Pruebas, TDD y umbral JaCoCo | [`docs/estrategia-pruebas.md`](../estrategia-pruebas.md), [`docs/evidencias/piramide-pruebas-sesion8.md`](../evidencias/piramide-pruebas-sesion8.md), [`docs/evidencias/tdd-descuento-mayorista.md`](../evidencias/tdd-descuento-mayorista.md) | Actions #86 está verde en `main`, commit `77b5141`; [captura](../evidencias/ci-run-main-77b5141.png) y [run](https://github.com/santixit/agrovalle-connect/actions/runs/40824085047). El reporte JaCoCo #83 de `6c19aae` muestra 89% de instrucciones, 63% de ramas y 91% de líneas, por encima del umbral de 60%. Entre ambos commits solo cambiaron documentos y workflow, no el código ni las pruebas. Captura local: [JaCoCo](../evidencias/jacoco-main-6c19aae.png); [artefacto](https://github.com/santixit/agrovalle-connect/actions/runs/40816952882/artifacts/12873380141). El paquete de documentación y diseño fue integrado en `main` mediante el PR #59. |
| Políticas Kanban y límites WIP | [`docs/kanban-policies.md`](../kanban-policies.md), [`docs/evidencias/kanban-metricas-sesion7.md`](../evidencias/kanban-metricas-sesion7.md) | Límites documentados como In Progress ≤3 y Code Review / Testing ≤2. El último snapshot conservado es del 2026-10-19 y no demuestra el estado actual. |

## Estado del tablero — revisado 2026-10-29

Revisé de nuevo el [GitHub Project](https://github.com/users/santixit/projects/3/views/1) el 2026-10-29: sigue mostrando **3/3** en In Progress, **2/2** en Code Review / Testing y **14** en Done. Las dos tareas de fincas continúan en revisión porque sus issues mantienen validaciones/pruebas pendientes. La captura de respaldo disponible fue guardada el 2026-10-28 y muestra los mismos conteos.

- En **Done**: [mockups actualizados (#57, PR #52)](https://github.com/santixit/agrovalle-connect/issues/57) y [Strategy de fletes/GoF (#58, PR #51)](https://github.com/santixit/agrovalle-connect/issues/58), ambos integrados y cerrados.
- En **In Progress**: [UML/DER/schema/normalización (#54)](https://github.com/santixit/agrovalle-connect/issues/54); [planeación, review y retrospectiva (#55)](https://github.com/santixit/agrovalle-connect/issues/55); [CI/JaCoCo, tablero y PDF (#56)](https://github.com/santixit/agrovalle-connect/issues/56). Las tres issues están abiertas y describen evidencia actual y criterios de cierre.
- Los límites WIP son por columna: In Progress **3** y Code Review / Testing **2**. Cada issue activa cuenta como un elemento; la estimación en puntos es un campo aparte y debe acordarla el equipo. No se asignó una estimación inventada. La vista no ofrece una iteración Sprint 2 para asignar.
- La captura [tablero-sprint-2-2026-10-28.png](../evidencias/tablero-sprint-2-2026-10-28.png) conserva el estado visible el día que se actualizaron las tarjetas; la vista se volvió a revisar el 29 y no habían cambiado los conteos. Las tarjetas de Sprint 2 son issues del repositorio, sin borradores.

## Estado del paquete local y pendientes reales de cierre

El PDF de trabajo está en [`Grupo01_Entrega_Segundo_Corte_AgroValle.pdf`](Grupo01_Entrega_Segundo_Corte_AgroValle.pdf). Tiene cinco páginas: portada institucional, resumen ejecutivo de dos páginas, evidencia de CI verde y reporte de cobertura JaCoCo. No incluye wireframes, mockups ni diagramas. El script que lo genera es [`build_delivery_pdf.py`](build_delivery_pdf.py).

| Pendiente | Qué hace falta | Responsable de confirmarlo |
|---|---|---|
| Portada institucional | Incluye institución, programa, curso, proyecto, grupo y nombres. Por decisión del Product Owner omite los documentos de identidad solicitados en la guía; es una excepción conocida frente al requisito. | Integrada en el PDF publicado en `main` por PR #59; se actualizará junto con la nueva evidencia de CI. |
| Plan, Review y Retrospective de Sprint 2 | Fechas y objetivo se identifican como reconstruidos. La bitácora suma actividad técnica fechada sin inventar Daily Scrums; Review/Retrospective son registros documentales, no actas de ceremonia. | Integrados por PR #59; las fechas y el objetivo se presentan como reconstrucción y no como actas formales. |
| Conformidad final del modelo | Faltan decisiones del equipo sobre dependencias y cardinalidades de dominio para poder afirmar 3FN. El SQL es una vista consolidada de referencia y Flyway es la fuente ejecutable. | Pendiente del equipo |
| Estado del tablero | Captura local del 2026-10-28 muestra 3/3 en In Progress y 2/2 en Code Review / Testing; enlace y WIP están en este checklist. Si el tablero cambia al cierre, renovar la captura. | Evidencia disponible; revisar al cierre |
| CI de `main` | Actions #86 está verde para `77b5141`; el código y las pruebas son iguales a los del commit cubierto por JaCoCo #83 (`6c19aae`). El paquete de documentación y exports ya está integrado en `main` por el PR #59. | Evidencia vigente para `main` tras integrar PR #59; la captura muestra el título actualizado sin `codex` |
| Cobertura JaCoCo | El artefacto #83 muestra instrucciones 89%, ramas 63% y líneas 91% para `6c19aae`; esas métricas siguen representando el mismo código y pruebas presentes en `main` `77b5141`. | Evidencia vigente para el código de ese commit; renovar si cambia código fuente o pruebas |
| Revisión por pares y protección de `main` | La regla activa `main-protection` exige Pull Request, una aprobación y el check `Build, tests, Checkstyle and JaCoCo`; además, bloquea borrado y force-push. | Configurada en GitHub; las aprobaciones se solicitan por PR |
| Resumen ejecutivo y PDF único | Incluidos en el PDF local: resumen de dos páginas, portada, enlaces a `main` y GitHub Project con WIP, evidencia de pipeline verde y cobertura. | Actualización preparada en PR #60; pendiente de revisión e integración |

## Métricas que no se deben inventar

Con los datos Kanban guardados no se calculan throughput por periodo, cycle time, lead time, una CFD confiable, valores reproducibles de burn-up ni Ley de Little. Dos snapshots aislados permiten comparar conteos netos entre esas fechas, pero no reconstruir movimientos ni tiempos por tarjeta. Para calcular esas métricas se necesitan eventos fechados y completos; véase [`kanban-metricas-sesion7.md`](../evidencias/kanban-metricas-sesion7.md).

## Nota sobre la fecha límite

La captura del aula compartida en la conversación indicaba miércoles 4 de noviembre de 2026 a las 23:59, mientras que el estado del aula observado el 28 de octubre decía que vencía “mañana”. Son referencias discrepantes; confirmar la fecha vigente directamente en el aula antes de enviar.

## Orden de cierre sugerido

1. El equipo acepta la excepción de portada sabiendo que la guía sí solicita números de documento.
2. Al cierre, confirmar si hubo demostración y validar la decisión de dominio sobre 3FN, o mantenerla expresamente como limitación.
3. Si se actualiza el tablero, guardar una nueva captura del estado final.
4. Integrar los cambios mediante Pull Request con una aprobación; luego revisar CI y JaCoCo para el commit final de `main`.
5. Sustituir esta copia candidata por el PDF con evidencia del commit integrado antes de subir a la plataforma.
