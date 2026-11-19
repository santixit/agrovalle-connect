# Sprint de Diseño — revisión documental

**Fecha de registro:** 2026-11-19
**Modalidad:** revisión retrospectiva de artefactos versionados; no se presenta como reunión formal ni como aprobación colectiva del PO/equipo.

## Incremento revisado

| Elemento | Evidencia | Resultado de revisión |
|---|---|---|
| Modelo de datos y DDL | DER implementado, DER propuestos, `schema.sql` y migraciones Flyway | Hay esquema ejecutable V1–V7 y snapshots de referencia. Flyway es canónico; la reconciliación documenta las diferencias y decisiones para continuar. |
| Arquitectura y patrones | `arquitectura-mvc-gof.md`, `patrones.md` y código Java | Capas y patrones están vinculados a la implementación actual. |
| UML | Fuentes PlantUML, renders en `exports/` y secuencias | Hay fuentes editables y exportaciones. Las secuencias que muestran una cola asíncrona son propuestas; Observer funciona de forma síncrona en la implementación descrita. |
| Mockups | `mockups-ui.pdf` y fuentes PNG | Se conservan siete wireframes y siete mockups. Issue #46 registra un visto bueno informal de algunos integrantes, no una aprobación individual de todas las pantallas o del equipo completo. |
| Historias y escenarios | Backlog BDD, contratos HTTP y comparación de historias | Quince HUs documentadas; issue #46 contiene precisiones de HU-01, HU-04 y HU-07. |
| Orden Discovery → Diseño → Código | Issue #46 y registro de Sesión 6 | No hay evidencia contemporánea suficiente para verificar que el diseño precedió a la codificación. |

## Decisiones para continuar

- Flyway V1–V7 es el esquema de referencia; los DER implementados se mantienen alineados con él.
- Los DER rotulados como propuesta permanecen como antecedentes de análisis.
- Se mantienen fuera de este alcance los catálogos normalizados de municipio/categoría; productos asociados a finca derivan municipio de finca.
- El PDF y PNG actuales se usan como referencia visual. Una aprobación completa de mockups no se afirma porque no está registrada.
- La cronología Discovery–Diseño–Código queda como no demostrada; no se intenta inferirla de timestamps posteriores.

Las decisiones se desarrollan en la [reconciliación DER](../../diseno/reconciliacion-der.md) y en la [línea base de requisitos](../../requirements/baseline-decisions-2026-11-19.md).

## Conclusión

El paquete de diseño está organizado, mantiene fuentes editables y distingue propuestas de implementación. Sus límites de aprobación e historia están descritos; no hay bloqueos documentales para continuar con la línea base actual del prototipo.
