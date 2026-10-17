# Sprint de Diseno y cierre de Sprint 1

Este paquete documenta el diseno arquitectonico del sistema AgroValle Connect y enlaza los artefactos comprobables en el repositorio. Se basa en la implementacion actual; no se presenta como una fase historica realizada antes de programar. Los prototipos son propuestas para revision del Product Owner y no implican una aprobacion que no conste en evidencia.

## Artefactos

- [Modelo entidad-relacion conceptual](modelo-entidad-relacion.pdf) y fuente editable [modelo-entidad-relacion.mmd](modelo-entidad-relacion.mmd). El PDF presenta las entidades unicas y sus relaciones en una sola lamina; usa una paleta verde y coral diferenciada. La guia acepta PNG o PDF para este artefacto.
- [DER fisico y notas de normalizacion](modelo-datos-der.pdf) y fuente [modelo-datos-der.mmd](modelo-datos-der.mmd). El PDF tiene dos laminas y se acompana del DDL de referencia.
- [Snapshot DDL PostgreSQL](schema.sql). Es una consolidacion legible de Flyway V1-V4 para consulta y evaluacion. Las migraciones siguen siendo la unica fuente ejecutable del esquema; no ejecutar este snapshot en paralelo con Flyway.
- [Arquitectura MVC en cuatro capas y patrones](arquitectura-mvc-gof.md).
- PNG exigidos por la estructura de la guia: [diagrama de componentes](diagrama-componentes.png), [diagrama de despliegue](diagrama-despliegue.png) y [secuencias HU-01/HU-02](diagrama-secuencia.png). El PDF combinado [diagramas-uml.pdf](diagramas-uml.pdf) es una copia de consulta. Fuentes editables: [diagrama-secuencia.puml](diagrama-secuencia.puml), [diagrama-componentes.puml](diagrama-componentes.puml) y [diagrama-despliegue.puml](diagrama-despliegue.puml).
- [Prototipos de interfaz](mockups-ui.pdf), alineados a los DTOs actuales. La guia solicita este PDF (o capturas); su revision por el Product Owner queda pendiente.
- Especificacion IEEE 29148 y comparacion BDD: [../ieee29148-req01.md](../ieee29148-req01.md).
- Los contratos HTTP detallados de HU-01, HU-07 y HU-04 estan en [../api-sprint-1.md](../api-sprint-1.md); las 15 historias y escenarios BDD estan en [../../BACKLOG.md](../../BACKLOG.md).

## Alcance

La consigna de esta guia exige terminar HU-01, HU-07 y HU-04 en Sprint 1. Se conserva la capacidad de 10 SP ya acordada (5 + 2 + 3). La guia tambien menciona Sprint 2; se deja fuera de este trabajo para respetar la decision del equipo de no iniciarlo todavia.

No se preparo un PDF unico de entrega. Los artefactos quedan separados en esta carpeta y en `docs/ieee29148-req01.md`, como indica la guia, para revisarlos y versionarlos en GitHub.

## Validaciones del equipo pendientes

- Product Owner: confirmar los wireframes y la version definitiva de historias, especialmente las diferencias entre el backlog actual y el documento `Sprint Planning- Sprint1 - Qué`.
- Equipo: revisar que el modelo conceptual y las decisiones de normalizacion representen las reglas reales del negocio.
- Peer review: el PR #23 se aprobo despues de fusionarse; esa aprobacion no demuestra peer review previo. Los futuros PR deben aprobarse antes del merge.
- Sprint 1: conservar el run CI y evidencias funcionales existentes, verificando que correspondan al commit que se entrega.
- DoD: el archivo actual es una lista de verificacion, no tiene firmas verificables; las firmas deben agregarlas los integrantes si la docente las solicita.
