# Índice de documentación

Este es el punto de entrada a la documentación de AgroValle Connect. Cada tema tiene una ubicación canónica para evitar copias divergentes. Si un documento pertenece a un sprint, se guarda dentro de `sprints/<sprint>/`; las evidencias generales permanecen en `evidencias/`.

## Documentación por tema

| Tema | Ubicación canónica | Qué contiene |
|---|---|---|
| Requisitos y producto | [`requirements/`](requirements/README.md) | Product Backlog BDD, SRS, trazabilidad IEEE 29148 y línea base de decisiones. |
| Sprints | [`sprints/`](sprints/README.md) | Planning, review, retrospectiva, bitácoras y evidencia propia de cada iteración. |
| Diseño de datos e interfaz | [`diseno/`](diseno/README.md) | DER/MER, normalización, DDL, mockups, secuencias y exportaciones. |
| Diagramas UML | [`architecture/`](architecture/README.md) | Diagramas vigentes y diagramas históricos identificados como tales. |
| Decisiones técnicas | [`architecture-decisions/`](architecture-decisions/README.md) | ADR numerados y su estado. |
| API | [`api/contratos-rest.md`](api/contratos-rest.md) | Rutas, ejemplos de solicitud/respuesta y comportamiento documentado. |
| Calidad y operación | Documentos de esta carpeta | [`DoD`](dod.md), [estrategia de pruebas](estrategia-pruebas.md), [staging](deploy-staging.md), [políticas Kanban](kanban-policies.md) y [protección de ramas](branch-protection.md). |
| Evidencia transversal | [`evidencias/`](evidencias/README.md) | CI, cobertura, Kanban, patrones y resultados que no pertenecen a una sola iteración. |
| Métricas de flujo | [`metrics/`](metrics/README.md) | Procedimiento de captura, reporte del periodo y límites de los datos disponibles. |
| Entregas del curso | [`entregables/`](entregables/README.md) | Copias históricas de paquetes de entrega y sus artefactos originales. |
| Pruebas de API | [`postman/README.md`](postman/README.md) | Importación, variables y orden de ejecución de las colecciones Postman. |

## Estado documentado

- La [línea base](requirements/baseline-decisions-2026-11-19.md) y la [SRS](requirements/system-requirements-specification.md) describen el prototipo, sus reglas y sus límites; no declaran servicio de producción, SLA ni uso de datos personales reales.
- Sprint 0, Sprint 1, Sprint de Diseño y Sprint 3 tienen revisiones documentales basadas en artefactos. No se presentan como actas de ceremonias que no estén acreditadas.
- Sprint 2 conserva el alcance reconstruido y sus fuentes. La selección formal inicial de HUs/SP no se inventa donde no existe registro.
- Sprint 3 continúa en validación. La salida Maven comunicada el 2026-11-19 informa 37 pruebas aprobadas; la demo en staging y la decisión explícita del PO por cada historia aún requieren evidencia.
- Las métricas de flujo se calculan únicamente cuando hay eventos fuente suficientes. Los documentos indican cuándo un valor no se puede comprobar.

“No acreditado” significa que el repositorio no conserva evidencia suficiente; no afirma que una actividad nunca ocurriera. La autoría, fechas, métricas, decisiones y aceptación se registran solo cuando su fuente es verificable.

## Reglas para mantener la estructura

1. Añade nuevos documentos a la carpeta canónica de su tema, no a la raíz de `docs/`.
2. Guarda planning, review, retrospectiva y evidencia específica debajo de `sprints/<sprint>/`.
3. Conserva las entregas originales bajo `entregables/`; no edites un PDF histórico para simular un estado actual.
4. Mantén una sola copia vigente. Si algo es histórico, etiquétalo como histórico y enlázalo desde el índice correspondiente.
5. Actualiza los enlaces al mover un archivo y no rellenes vacíos de evidencia con fechas, firmas, aprobaciones o métricas supuestas.

El código está en `src/`, las pruebas en `src/test/`, los scripts en `scripts/` y los workflows en `.github/workflows/`. `BACKLOG.md` en la raíz es un acceso de compatibilidad; la fuente vigente es [`requirements/product-backlog-bdd.md`](requirements/product-backlog-bdd.md).
