# Sprints y entregas

Esta carpeta agrupa planning, review, retrospective y evidencias de cada iteración. Los registros distinguen hechos del repositorio de ceremonias o decisiones colectivas que no quedaron documentadas.

| Iteración | Contenido |
|---|---|
| [Sprint 0](sprint-0/README.md) | Inventario, revisión y análisis documental de la infraestructura base |
| [Sprint 1](sprint-1/planning.md) | Planning, review, retrospective y bitácora asíncrona |
| [Sprint 2](sprint-2/planning.md) | Alcance reconstruido, review y retrospectiva documental |
| [Sprint 3](sprint-3/planning.md) | Validación en curso de HU-02, HU-06, HU-09 y HU-10; [guion de demo](sprint-3/demo-runbook.md), resultado JUnit comunicado y cierre operativo documentado |
| [Sprint de Diseño](sprint-diseno/review.md) | Revisión y retrospectiva documental de diseño |
| [Proyección](proyeccion.md) | Candidatos y dependencias para sprints futuros |

## Alcance de la reorganización

Se normalizaron las rutas de documentación, no el paquete Java. El PDF del laboratorio presenta una estructura ejemplo; se conservaron el código, las migraciones Flyway y los artefactos originales de diseño en sus rutas funcionales. La calidad y entrega se encuentran en [CI](../../.github/workflows/ci.yml) y su reusable [CD](../../.github/workflows/cd.yml).

## Estado actual

Los documentos de Sprint 0, Sprint 1, Sprint de Diseño y Sprint 3 son análisis basados en evidencia, no actas de reuniones no verificadas. Sprint 2 conserva el periodo y objetivo reconstruidos con sus fuentes. La selección inicial de historias de Sprint 2 no está registrada. Sprint 3 no se declara cerrado hasta que exista gate de calidad para el commit final, demostración y decisión del PO por historia.
