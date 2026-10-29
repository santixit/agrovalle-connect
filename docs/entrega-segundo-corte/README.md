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
| Modelo relacional, DER, DDL y mapeo JPA | [`docs/diseno/README.md`](../diseno/README.md), [`docs/diseno/schema.sql`](../diseno/schema.sql), migraciones Flyway y entidades JPA | Snapshot SQL consolidado a partir de V1–V7. La implementación local incorpora parcelas, ubicación normalizada, transferencias con agricultor anterior/nuevo/fecha y municipio derivado de la finca. Se conserva el municipio propio de productos sin finca y el destinatario histórico de contactos. Compilación local correcta; las pruebas automatizadas no se ejecutaron. |
| MVC, SOLID y patrón GoF | [`docs/diseno/arquitectura-mvc-gof.md`](../diseno/arquitectura-mvc-gof.md), [`docs/diseno/patrones.md`](../diseno/patrones.md), [`docs/evidencias/solid-sesion7.md`](../evidencias/solid-sesion7.md) | Mapeo actualizado de SRP/OCP/LSP/ISP/DIP a ejemplos reales, con alcance y límites; Strategy y Observer están implementados. |
| Revisión técnica del incremento | [`docs/evidencias/demo-staging-sprint2-2026-10-29.md`](../evidencias/demo-staging-sprint2-2026-10-29.md) | Comprobación complementaria de solo lectura en staging el 2026-10-29: página, salud y catálogo respondieron HTTP 200. Incluye guion y aclara que no fue una ceremonia ni demuestra selección formal de historias. |
| UML de componentes, despliegue y secuencia | Fuentes PlantUML y [`docs/diseno/exports/`](../diseno/exports/) | PNG por diagrama y PDF consolidado de cuatro páginas publicados en `main` mediante PR #59. |
| Wireframes y mockups | [`docs/diseno/mockups-ui.pdf`](../diseno/mockups-ui.pdf) y fuentes PNG | Siete wireframes y siete mockups organizados. |
| Pruebas, TDD y umbral JaCoCo | [`docs/estrategia-pruebas.md`](../estrategia-pruebas.md), [`docs/evidencias/piramide-pruebas-sesion8.md`](../evidencias/piramide-pruebas-sesion8.md), [`docs/evidencias/tdd-descuento-mayorista.md`](../evidencias/tdd-descuento-mayorista.md) | Actions #97 está verde en `main`, commit `59e3256`; [run y jobs](https://github.com/santixit/agrovalle-connect/actions/runs/40875809295) y [artefacto JaCoCo del mismo commit](https://github.com/santixit/agrovalle-connect/actions/runs/40875809295/artifacts/12897992950). El HTML JaCoCo #83 de `6c19aae` muestra 89% de instrucciones, 63% de ramas y 91% de líneas; se verificó que no cambiaron `src/` ni `pom.xml` hasta `59e3256`. La [captura #97](../evidencias/ci-run-main-59e3256.png) está incluida en el PDF. |
| Políticas Kanban y límites WIP | [`docs/kanban-policies.md`](../kanban-policies.md), [`docs/evidencias/kanban-metricas-sesion7.md`](../evidencias/kanban-metricas-sesion7.md) | Límites documentados como In Progress ≤3 y Code Review / Testing ≤2. La vista se revisó el 2026-10-29: In Progress está en 3/3, Code Review / Testing en 2/2 y Done en 14; la captura local del 2026-10-28 conserva la evidencia visual. |

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
| Plan, Review y Retrospective de Sprint 2 | Fechas y objetivo se identifican como reconstruidos. La bitácora suma actividad técnica fechada sin inventar Daily Scrums; Review/Retrospective son registros documentales, no actas de ceremonia. Hay una comprobación actual de staging y guion; historias elegidas formalmente siguen sin evidencia. | Integrados por PR #59; las fechas y el objetivo se presentan como reconstrucción y no como actas formales. |
| Conformidad final del modelo | El Product Owner definió parcela con finca/responsable, transferencia con agricultor anterior/nuevo/fecha, coincidencia del municipio y contacto histórico. | Modelo 3FN implementado localmente con V7; requiere revisión del equipo y ejecución de pruebas antes de integrar/publicar. |
| Estado del tablero | Captura local del 2026-10-28 muestra 3/3 en In Progress y 2/2 en Code Review / Testing; enlace y WIP están en este checklist. Si el tablero cambia al cierre, renovar la captura. | Evidencia disponible; revisar al cierre |
| CI de `main` | Actions #97 está verde para el commit actual `59e3256`; el PR #61 fue aprobado y fusionado. | El workflow pasó Build, tests, Checkstyle, JaCoCo y staging. La captura proporcionada del run #97 ya se incorporó al PDF. |
| Cobertura JaCoCo | Actions #97 publicó el artefacto [jacoco-coverage-report](https://github.com/santixit/agrovalle-connect/actions/runs/40875809295/artifacts/12897992950). El reporte #83 informa instrucciones 89%, ramas 63% y líneas 91%; se verificó que `src/` y `pom.xml` no cambiaron entre ambos commits. | El artefacto más reciente es #97; los porcentajes #83 aplican al mismo código y pruebas. Incorporar captura del reporte actual si la plataforma la solicita |
| Revisión por pares y protección de `main` | La regla activa `main-protection` exige Pull Request, una aprobación y el check `Build, tests, Checkstyle and JaCoCo`; además, bloquea borrado y force-push. | Configurada en GitHub; las aprobaciones se solicitan por PR |
| Resumen ejecutivo y PDF único | Esta rama contiene una versión local regenerada con CI #97 y captura actualizada; nombres sin documentos de identidad por decisión del Product Owner. El PDF publicado en `main` no se modifica hasta integrar esta rama. | Revisión del modelo de dominio sigue pendiente y no se deben afirmar historias formales sin evidencia; después revisar el PDF y cargar un único archivo en la plataforma |

## Métricas que no se deben inventar

Con los datos Kanban guardados no se calculan throughput por periodo, cycle time, lead time, una CFD confiable, valores reproducibles de burn-up ni Ley de Little. Dos snapshots aislados permiten comparar conteos netos entre esas fechas, pero no reconstruir movimientos ni tiempos por tarjeta. Para calcular esas métricas se necesitan eventos fechados y completos; véase [`kanban-metricas-sesion7.md`](../evidencias/kanban-metricas-sesion7.md).

## Nota sobre la fecha límite

La captura del aula compartida en la conversación indicaba miércoles 4 de noviembre de 2026 a las 23:59, mientras que el estado del aula observado el 28 de octubre decía que vencía “mañana”. Son referencias discrepantes; confirmar la fecha vigente directamente en el aula antes de enviar.

## Orden de cierre sugerido

1. El equipo acepta la excepción de portada sabiendo que la guía sí solicita números de documento.
2. Revisar con el equipo la implementación local de 3FN y ejecutar las pruebas/CI antes de integrar. No presentar los PR #51/#52 como historias formalmente seleccionadas sin evidencia.
3. Si se actualiza el tablero, guardar una nueva captura del estado final.
4. Integrar la actualización del paquete mediante Pull Request con una aprobación y revisar el CI del commit final de `main`.
5. Revisar la copia del PDF actualizada en esta rama; el entregable final debe ser el PDF único publicado en `main` antes de cargarlo en la plataforma.
