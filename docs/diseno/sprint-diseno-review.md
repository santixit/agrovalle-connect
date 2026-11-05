# Revisión documental del Sprint de Diseño

**Estado:** revisión retrospectiva basada en los artefactos disponibles. No se presenta como una reunión celebrada ni como aprobación del Product Owner o de todo el equipo.

## Incremento de diseño revisado

| Elemento | Evidencia del repositorio | Resultado de la revisión |
|---|---|---|
| Modelo de datos y DDL | DER implementado, DER propuestos, `schema.sql` y migraciones Flyway | Hay un modelo ejecutable y propuestas de referencia. La reconciliación documenta diferencias; el equipo aún debe confirmar las reglas de dominio. |
| Arquitectura y patrones | `arquitectura-mvc-gof.md` y `patrones.md` | Las capas y patrones están descritos con referencia a la implementación actual. |
| Diagramas UML | Fuentes PlantUML y exportaciones en `exports/` y `secuencias/` | Hay diagramas editables y renders. Algunas secuencias representan flujos propuestos y no el comportamiento asíncrono actual. |
| Mockups | `mockups-ui.pdf` y fuentes PNG | Se conservan siete wireframes y siete mockups. El issue #46 registra un visto bueno informal y asíncrono de algunos integrantes; no permite afirmar aprobación de todo el equipo ni quién aprobó cada pantalla. |
| Historias y escenarios | `BACKLOG.md` y `api-sprint-1.md` | Hay 15 historias con criterios BDD; el issue #46 conserva precisiones de HU-01, HU-04 y HU-07. |
| Secuencia Discovery → Diseño → Código | Issue #46 y registro retrospectivo de Sesión 6 | La secuencia cronológica frente a la codificación no está acreditada. Parte de la documentación expresa la implementación vigente, no una prueba de que el diseño se completara antes de programar. |

## Resultado

Los artefactos de diseño están presentes y se pueden revisar, pero el expediente no demuestra una aprobación completa de mockups ni que el diseño precediera a la codificación. La reconciliación entre los DER propuestos y el esquema vigente está documentada y mantiene explícitas las diferencias. La revisión queda abierta hasta que el equipo confirme esos puntos con evidencia verificable.

## Acciones pendientes

- El Product Owner y el equipo confirman si los mockups representan el alcance que se acepta.
- El equipo valida las diferencias entre DER propuestos y modelo implementado, así como las reglas de dominio que siguen abiertas.
- Si existen evidencias contemporáneas de Discovery anterior a la codificación, se enlazan aquí; no se infieren fechas a partir de commits.
- Se registra la decisión y sus responsables en el issue o PR correspondiente. Este documento no sustituye ese registro.
