# Diseño de datos, arquitectura e interfaz

Este paquete reúne artefactos de diseño y su relación con el sistema actual. Describe la implementación observada y no pretende demostrar que todo el diseño precediera históricamente a la codificación.

## Fuentes vigentes

- Modelo Entidad-Relación conceptual implementado: [modelo-entidad-relacion.mmd](modelo-entidad-relacion.mmd).
- DER físico implementado: [modelo-datos-der.mmd](modelo-datos-der.mmd).
- DDL consolidado como snapshot de consulta: [schema.sql](schema.sql). Las migraciones Flyway V1–V7 son la única fuente ejecutable; no ejecutar el snapshot en paralelo.
- Reconciliación entre el modelo propuesto y el esquema: [reconciliacion-der.md](reconciliacion-der.md).
- Análisis de normalización y parcelas/productos: [decisiones-normalizacion.md](decisiones-normalizacion.md) y [propuesta-modelo-3fn.md](propuesta-modelo-3fn.md).
- Arquitectura por capas, MVC y GoF: [arquitectura-mvc-gof.md](arquitectura-mvc-gof.md) y [patrones.md](patrones.md).
- Modelo conceptual inicial, conservado solo como antecedente: [modelo-inicial.md](modelo-inicial.md).
- UML editable: [componentes](diagrama-componentes.puml), [despliegue](diagrama-despliegue.puml), [secuencia de registro](diagrama-secuencia.puml) y [secuencia de publicación](diagrama-secuencia-publicacion.puml); exportaciones en [exports](exports/README.md).
- Wireframes y mockups: [PDF](mockups-ui.pdf) y 14 fuentes PNG en [mockups](mockups/).
- Diagramas de secuencia de referencia: [secuencias](secuencias/README.md).
- Registro documental de la sesión de Discovery/Diseño: [registro de la sesión 6](../sprints/sprint-diseno/registro-sesion-6.md).

Los dos DER con sufijo “propuesta” se conservan como antecedentes. No sustituyen al DER implementado ni a las migraciones. El issue #46 registra un visto bueno informal y asíncrono de algunos integrantes sobre mockups, no una aprobación de todo el equipo ni de cada pantalla. La decisión de alcance para continuar está documentada en la [línea base](../requirements/baseline-decisions-2026-11-19.md).

## Cobertura de diseño

| Entregable | Estado documental |
|---|---|
| DER / DDL / JPA | Esquema implementado en Flyway V1–V7, snapshot DDL y entidades JPA disponibles. La relación entre propuestas y modelo vigente está explicada en la reconciliación. |
| Normalización 3FN | La ubicación y titularidad actual del producto se normalizan mediante ubicación, parcela y finca; municipio y categoría siguen como texto por decisión de alcance. No se presenta el modelo conceptual aportado como si fuera DDL. |
| Arquitectura MVC y patrones | Las capas y patrones se relacionan con paquetes y código existentes en la guía MVC/GoF y la guía de patrones. |
| UML | Hay fuentes editables y exportaciones para componentes, despliegue y secuencias. Los diagramas asíncronos se identifican como propuesta cuando la implementación procesa eventos de forma síncrona. |
| Mockups y UI | Siete wireframes y siete mockups están conservados. La interfaz señala las funciones sin persistencia o sin API; la foto solo se previsualiza localmente. |
| Discovery y aceptación histórica | El issue #46 documenta Discovery y visto bueno informal/asíncrono. No acredita su secuencia antes de codificar, aprobación total de mockups ni aceptación colectiva del DER. No se inventan esas pruebas. |

## Sprint 1 y Sprint 2

- La consigna de Sprint 1 fija HU-01, HU-07 y HU-04, con 10 SP en total; su plan, evidencia y análisis están en [sprints/sprint-1](../sprints/sprint-1/planning.md).
- Sprint 2 tiene alcance/review/retrospectiva documentales para el trabajo visible del 19 al 29 de octubre de 2026. No se conserva una selección inicial verificable de HUs/SP; no se reconstruye.
- El [paquete de review y retrospectiva del Sprint de Diseño](../sprints/sprint-diseno/review.md) cierra la revisión de artefactos y registra explícitamente sus límites.

## Lectura recomendada

1. Revisar [la reconciliación DER](reconciliacion-der.md) para distinguir esquema ejecutable y propuesta.
2. Consultar [modelo y patrones](arquitectura-mvc-gof.md) para entender la separación de capas.
3. Abrir las fuentes UML y mockups; usar los PNG/PDF para comparar con las vistas y contratos reales.
4. Revisar la [SRS](../requirements/system-requirements-specification.md) y [decisiones de línea base](../requirements/baseline-decisions-2026-11-19.md) antes de introducir una regla nueva.
