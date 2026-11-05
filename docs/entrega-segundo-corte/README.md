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
| Modelo relacional, DER, DDL y mapeo JPA | [`docs/diseno/README.md`](../diseno/README.md), [`docs/diseno/schema.sql`](../diseno/schema.sql), migraciones Flyway y entidades JPA | Snapshot SQL consolidado a partir de V1–V7. La migración V7, las parcelas, las transferencias y el mapeo JPA están integrados en `main`. Actions #103 ejecutó pruebas, Checkstyle y JaCoCo sobre ese código y terminó correctamente. |
| MVC, SOLID y patrón GoF | [`docs/diseno/arquitectura-mvc-gof.md`](../diseno/arquitectura-mvc-gof.md), [`docs/diseno/patrones.md`](../diseno/patrones.md), [`docs/evidencias/solid-sesion7.md`](../evidencias/solid-sesion7.md) | Mapeo actualizado de SRP/OCP/LSP/ISP/DIP a ejemplos reales, con alcance y límites; Strategy y Observer están implementados. |
| Revisión técnica del incremento | [`docs/evidencias/demo-staging-sprint2-2026-10-29.md`](../evidencias/demo-staging-sprint2-2026-10-29.md) | Comprobación complementaria de solo lectura en staging el 2026-10-29: página, salud y catálogo respondieron HTTP 200. Incluye guion y aclara que no fue una ceremonia ni demuestra selección formal de historias. |
| UML de componentes, despliegue y secuencia | Fuentes PlantUML y [`docs/diseno/exports/`](../diseno/exports/) | PNG por diagrama y PDF consolidado de cuatro páginas publicados en `main` mediante PR #59. |
| Wireframes y mockups | [`docs/diseno/mockups-ui.pdf`](../diseno/mockups-ui.pdf) y fuentes PNG | Siete wireframes y siete mockups organizados. |
| Pruebas, TDD, pirámide y umbral JaCoCo | [`docs/estrategia-pruebas.md`](../estrategia-pruebas.md), [`docs/evidencias/piramide-pruebas-sesion8.md`](../evidencias/piramide-pruebas-sesion8.md), [`docs/evidencias/tdd-descuento-mayorista.md`](../evidencias/tdd-descuento-mayorista.md) | Actions #103 pasó en `main`, commit `a7e2097`; [run y jobs](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070) y [artefacto JaCoCo](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/artifacts/12906676338). El HTML #103 muestra 88% de instrucciones, 63% de ramas y 90% de líneas. La distribución 22/23/1 no cumple el objetivo 70/20/10; está declarada como pendiente. |
| Políticas Kanban, WIP y métricas de flujo | [`docs/kanban-policies.md`](../kanban-policies.md), [`docs/evidencias/kanban-metricas-sesion7.md`](../evidencias/kanban-metricas-sesion7.md), [análisis Burn up/CFD](../evidencias/kanban-burnup-sesion7.md) | Límites documentados como In Progress ≤3 y Code Review / Testing ≤2. El snapshot consultado el 2026-11-05 mostró 0/3 y 0/2. Insights mostró un Burn up con discrepancia entre 35 elementos coincidentes y aproximadamente 24 visibles al sumar las series finales; no hay CFD reproducible. |

## Estado del tablero — revisado 2026-11-05

El [GitHub Project](https://github.com/users/santixit/projects/3/views/1) se consultó el 2026-11-05. El snapshot visible mostraba **0/3** en In Progress y **0/2** en Code Review / Testing. Esos límites están activos; esta lectura puntual no reconstruye el flujo histórico. La captura guardada el 2026-10-28 y los conteos 3/3, 2/2 y 14 Done son evidencia histórica, no el estado actual.

- Los límites WIP son por columna: In Progress **3** y Code Review / Testing **2**. El conteo de issues activos y los Story Points son campos distintos.
- Insights mostró 35 elementos coincidentes con filtro `is:issue`; las series visibles del último punto sumaban aproximadamente 24 (`Open` + `Completed`). La fuente y la lectura limitada se documentan en [kanban-burnup-sesion7.md](../evidencias/kanban-burnup-sesion7.md).
- El gráfico visible es **Burn up**, no CFD. Sin historial completo de estados por tarjeta no se calculan throughput, cycle time, lead time, CFD ni Ley de Little. La nueva lectura no reemplaza la captura visual del tablero ni las plantillas vacías de eventos.

## Estado del paquete local y pendientes reales de cierre

El PDF actualizado está en [`Grupo01_Entrega_Segundo_Corte_AgroValle.pdf`](Grupo01_Entrega_Segundo_Corte_AgroValle.pdf). Incluye resumen ejecutivo, evidencia de CI/JaCoCo, páginas de análisis de diseño, flujo, refactor SOLID y pirámide, y anexos con los wireframes/mockups, UML y DER propuestos. El script reproducible es [`build_delivery_pdf.py`](build_delivery_pdf.py). La copia pública omite identificaciones personales; completar una copia privada antes de cargarla al aula porque la guía las solicita.

| Pendiente | Qué hace falta | Responsable de confirmarlo |
|---|---|---|
| Portada institucional | Incluye institución, programa, curso, proyecto, grupo y nombres. Por decisión del Product Owner omite los documentos de identidad solicitados en la guía; es una excepción frente al requisito. | La copia pública omite datos personales. La copia privada de aula debe completarse con las identificaciones reales por un integrante del grupo. |
| Plan, Review y Retrospective de Sprint 2 | Fechas y objetivo se identifican como reconstruidos. La bitácora suma actividad técnica fechada sin inventar Daily Scrums; Review/Retrospective son registros documentales, no actas de ceremonia. Hay una comprobación actual de staging y guion; historias elegidas formalmente siguen sin evidencia. | Integrados por PR #59; las fechas y el objetivo se presentan como reconstrucción y no como actas formales. |
| Conformidad final del modelo | El Product Owner definió parcela con finca/responsable, transferencia con agricultor anterior/nuevo/fecha, coincidencia del municipio y contacto histórico. | V7 y el código relacionado ya están en `main`; el CI #103 pasó las pruebas y controles de calidad para ese commit. La revisión del diseño de dominio sigue siendo responsabilidad del equipo. |
| Estado del tablero | Captura local del 2026-10-28 conserva el estado histórico. La lectura visual del 2026-11-05 mostró 0/3 en In Progress y 0/2 en Code Review / Testing. | Enlace de tablero y fecha de consulta documentados; no hay una captura archivada del snapshot del 2026-11-05. |
| CI de `main` | Actions #103 está verde para el commit `a7e2097`; el PR #61 fue aprobado y fusionado. | El workflow pasó Build, tests, Checkstyle, JaCoCo, E2E y staging. La captura del run #103 está incorporada al PDF local. |
| Cobertura JaCoCo | Actions #103 publicó el artefacto [jacoco-coverage-report](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/artifacts/12906676338). El HTML del mismo commit informa instrucciones 88%, ramas 63% y líneas 90%. | HTML local, artefacto, log y [captura renderizada desde el HTML #103](../evidencias/jacoco-main-a7e2097.png) enlazados e incluidos en el PDF. |
| Pirámide 70/20/10 | El inventario registra 22 pruebas unitarias, 23 de integración y 1 E2E: 47,8 %, 50,0 % y 2,2 %. | **No cumple** la proporción objetivo. Se documentó la diferencia y no se reclasificaron casos para maquillar el porcentaje. |
| Sesión 6 - validación de diseño | Se actualizaron la reconciliación DER hasta V7, el estado de mockups y la cronología Discovery-Diseño-Código. | Artefactos presentes. La aprobación completa de mockups, la aceptación del DER vigente y una prueba contemporánea de cronología requieren confirmación/evidencia del equipo y PO. |
| Sesión 7 - CFD / flujo | Se documentó la lectura del Burn up de Insights del 2026-11-05 y la discrepancia de conteos. | El Burn up no es una CFD; no hay historial de transición completo ni CFD reproducible. |
| Revisión por pares y protección de `main` | La regla activa `main-protection` exige Pull Request, una aprobación y el check `Build, tests, Checkstyle and JaCoCo`; además, bloquea borrado y force-push. | Configurada en GitHub; las aprobaciones se solicitan por PR |
| Resumen ejecutivo y PDF único | Esta rama contiene el PDF actualizado con evidencias técnicas y anexos de mockups, UML y DER; conserva enlaces al repositorio/tablero. | Revisar la copia privada, añadir las identificaciones requeridas y subir un solo PDF. La actualización de esta rama aún requiere revisión/fusión del PR abierto. |

## Métricas que no se deben inventar

Con los datos históricos no se calculan throughput por periodo, cycle time, lead time, una CFD ni Ley de Little. El Burn up de Insights sí se pudo leer visualmente, pero no hay exportación reproducible y sus series no coinciden con los 35 elementos que la vista indicó como coincidentes. Dos snapshots aislados tampoco reconstruyen movimientos ni tiempos por tarjeta. Véase [el análisis del Burn up/CFD](../evidencias/kanban-burnup-sesion7.md).

## Nota sobre la fecha límite

La captura del aula indicaba miércoles 4 de noviembre de 2026 a las 23:59, mientras que el estado consultado el 28 de octubre decía que vencía “mañana”. A fecha de esta actualización (5 de noviembre), confirmar en el aula si hubo prórroga o nueva fecha antes de cargar el archivo.

## Orden de cierre sugerido

1. El equipo acepta la excepción de portada sabiendo que la guía sí solicita números de documento.
2. Revisar con el equipo la implementación local de 3FN y ejecutar las pruebas/CI antes de integrar. No presentar los PR #51/#52 como historias formalmente seleccionadas sin evidencia.
3. Si se actualiza el tablero, guardar una nueva captura del estado final.
4. Integrar la actualización del paquete mediante Pull Request con una aprobación y revisar el CI del commit final de `main`.
5. Revisar la copia del PDF actualizada en esta rama; el entregable final debe ser el PDF único publicado en `main` antes de cargarlo en la plataforma.
