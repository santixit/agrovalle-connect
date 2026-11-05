# Retrospectiva documental del Sprint de Diseño

**Estado:** síntesis retrospectiva de los artefactos y comentarios disponibles. No representa una ceremonia ni atribuye acuerdos colectivos no registrados.

## Qué facilitó el trabajo

- El repositorio conserva fuentes editables para el modelo de datos, DDL, arquitectura, UML y mockups, junto con exportaciones que permiten revisar la propuesta.
- El issue #46 registra decisiones acotadas sobre la base del Backlog, el perfil público de HU-07, los filtros de HU-04 y el esquema que se conservaría para aquel alcance.
- La documentación distingue el modelo ejecutable y las migraciones Flyway de los DER aportados como propuesta.

## Dificultades observables

- No hay evidencia verificable de que Sprint Discovery, la revisión de mockups y el diseño completo se hicieran antes de iniciar la codificación.
- Las fuentes de diseño se actualizaron para reflejar partes de la aplicación actual; por ello no siempre sirven como evidencia histórica del diseño previo.
- El issue registra confirmación de algunos integrantes, pero no documenta la aprobación de todo el equipo ni de cada mockup.
- Persisten diferencias entre DER propuestos y el esquema implementado, aunque la reconciliación las enumera para revisión.

## Aprendizajes y acciones siguientes

| Aprendizaje observado | Acción propuesta | Responsable / estado |
|---|---|---|
| Un artefacto actualizado contra el código ayuda a explicar el sistema, pero no prueba cuándo se diseñó. | En próximos sprints, guardar versiones revisadas con fecha y enlazar historias, issues o decisiones antes de codificar. | Equipo por acordar. |
| Una aprobación informal de mockups debe indicar quién revisó y qué versión aceptó. | Registrar en el issue o PR la versión aprobada y las confirmaciones pendientes. | Product Owner y equipo por confirmar. |
| Las diferencias entre propuesta de DER y esquema vigente necesitan una decisión del dominio, además de una explicación técnica. | Revisar la reconciliación del DER y documentar las decisiones pendientes en una fuente verificable. | Equipo por acordar. |

Esta retrospectiva no afirma que esas acciones ya se hayan acordado o ejecutado. El equipo puede actualizar el estado cuando exista confirmación verificable.
