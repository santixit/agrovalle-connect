# Registro de sprints

Cada iteración tiene una carpeta propia. El `README.md` de cada sprint resume su alcance y enlaza la planificación, revisión, retrospectiva y evidencias. La carpeta `sprint-diseno` conserva los artefactos de Discovery y diseño.

| Iteración | Estado documentado | Entrada |
|---|---|---|
| Sprint 0 | Infraestructura inicial; revisión documental de los artefactos disponibles. | [Sprint 0](sprint-0/README.md) |
| Sprint 1 | HU-01, HU-07 y HU-04; conserva planificación y coordinación asíncrona sin afirmar que hubo ceremonias formales. | [Sprint 1](sprint-1/README.md) |
| Sprint 2 | Registro reconstruido a partir de fuentes; selección formal inicial no acreditada. | [Sprint 2](sprint-2/README.md) |
| Sprint 3 | Validación de HU-02, HU-06, HU-09 y HU-10; no se declara cerrado hasta tener evidencia funcional y decisión del PO. | [Sprint 3](sprint-3/README.md) |
| Diseño | Revisión documental del diseño, DER, UML y mockups. | [Sprint de Diseño](sprint-diseno/README.md) |
| Proyección | Candidatos, dependencias y estimaciones futuras. | [Proyección de sprints](proyeccion.md) |

## Estructura de una iteración

```text
sprint-N/
├── README.md          # resumen y estado
├── planning.md        # objetivo, alcance, tareas y estimación
├── review.md          # incremento y criterios revisados
├── retrospective.md   # análisis y acciones de mejora
└── evidencias/        # archivos exclusivos de esta iteración
```

Los archivos opcionales (por ejemplo, `demo-runbook.md`, `checklist-inicio.md` o `bitacora-daily-scrum.md`) permanecen en la misma carpeta. No se agregan secciones vacías para ceremonias que no tienen registro.

## Criterio de registro

Los documentos separan hechos observables, reconstrucciones y pendientes. Una ejecución de pruebas no sustituye a la aceptación funcional del PO. Un archivo de planificación no prueba por sí solo que el sprint se planificó formalmente. Si falta una fuente, se registra la limitación y se indica qué evidencia concreta permitiría cerrar el punto.
