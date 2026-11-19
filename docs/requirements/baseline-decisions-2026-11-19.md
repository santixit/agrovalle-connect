# Decisiones de línea base — AgroValle Connect

**Versión:** 1.0  
**Fecha de registro:** 2026-11-19  
**Alcance:** prototipo académico y continuidad del Sprint 3  
**Estado:** decisiones documentales fijadas para que el equipo pueda continuar sin dejar supuestos escondidos.

## Cómo leer este registro

La persona solicitante autorizó completar las decisiones documentales necesarias para continuar el proyecto. Las decisiones de alcance siguientes son la línea base de trabajo de esta versión y pueden cambiarse mediante issue o Pull Request. Esta autorización permite fijar defaults de documentación; no se presenta como una reunión del equipo, una firma de todos los integrantes ni como aceptación funcional de historias por el Product Owner.

Los hechos históricos se conservan como hechos solo cuando hay evidencia. Por eso una decisión puede cerrar una pregunta de alcance y, al mismo tiempo, declarar que una ceremonia, una métrica o una aceptación no quedó registrada.

## Registro de decisiones

| ID | Tema | Decisión para esta versión | Evidencia y límite |
|---|---|---|---|
| D-01 | Reglas funcionales | Los escenarios del Product Backlog BDD y los contratos REST publicados son la fuente de comportamiento. Se soporta únicamente lo descrito allí y comprobable en el código. Las transiciones válidas son las expuestas por esos contratos; no se incorpora cancelación de reservas ni auditoría completa de cambios de estado porque no tienen un contrato implementado. Un caso no especificado se trata como fuera de alcance hasta documentar su regla y prueba. | `docs/requirements/product-backlog-bdd.md`, `docs/api/contratos-rest.md`, controladores, servicios y pruebas. La decisión evita deducir reglas a partir de nombres de entidades. |
| D-02 | Datos personales | El repositorio, CI, capturas y ambientes de prueba usan datos sintéticos. Las respuestas públicas no exponen cédula ni credenciales. Retención, rectificación, eliminación y uso de datos personales reales no forman parte de este prototipo; no se afirma cumplimiento legal ni aptitud para producción hasta acordar y documentar una política de datos. | DTOs y pruebas de privacidad; `docs/evidencias/`. Los requisitos legales aplicables deben validarse antes de operar con datos reales. |
| D-03 | Rendimiento, disponibilidad y recuperación | Este incremento no declara SLA, límite de concurrencia, RPO ni RTO. La verificación de calidad exigible en esta versión es la que ejecuta CI: build, pruebas, Checkstyle, JaCoCo con umbral configurado y E2E; `GET /api/v1/health` verifica disponibilidad funcional del servicio. No se publican cifras de rendimiento sin una prueba de carga reproducible. | `.github/workflows/ci.yml`, `.github/workflows/cd.yml`, `pom.xml` y `docs/metrics/`. El prototipo no se presenta como servicio productivo con disponibilidad garantizada. |
| D-04 | Navegadores y accesibilidad | El entorno de demostración esperado es un navegador de escritorio Chromium actualizado (Chrome o Edge) y una ventana móvil adaptable para los flujos principales. La prueba E2E automatizada cubre el navegador configurado por el workflow. La compatibilidad con otros motores y la conformidad con WCAG no se declaran verificadas; las etiquetas, navegación por teclado y contraste deben revisarse al ampliar la interfaz. | `package.json`, workflow E2E, interfaz web y capturas existentes. No existe matriz histórica de navegadores ni auditoría formal de accesibilidad. |
| D-05 | Funciones externas | Pagos, carga persistente de imágenes, correo/SMS/push y tarifas comerciales de flete quedan fuera de alcance. La foto puede tener vista previa local; las notificaciones implementadas son internas a la aplicación; Strategy de flete usa tarifas demostrativas. Una capacidad visual sin API no se describe como persistida. | `docs/diseno/README.md`, `docs/diseno/patrones.md`, contrato REST y código actual. |
| D-06 | Estado de Sprint 3 | La ejecución comunicada el 2026-11-19 obtuvo `BUILD SUCCESS`: 37 pruebas, 0 fallos, 0 errores y 0 omitidas. Esto cierra la comprobación automatizada reportada. No hay en el expediente una demostración de las cuatro historias en staging ni una decisión explícita del PO para HU-02, HU-06, HU-09 y HU-10; por lo tanto, la aceptación funcional se registra como **no acreditada** y las historias no se declaran aceptadas en esta documentación. | [Transcripción de resultado Maven](../sprints/sprint-3/evidencias/resultado-maven-2026-11-19.md) y referencias a métodos de prueba en `docs/sprints/sprint-3/planning.md`. No se atribuye el resultado a CI ni se inventa fecha de demo. |
| D-07 | Modelo y DER | El esquema ejecutable canónico es el conjunto de migraciones Flyway V1–V7. `docs/diseno/schema.sql` es un snapshot de referencia, no una segunda vía de migración. El DER vigente debe representar ese esquema; los DER rotulados como propuesta permanecen como antecedentes. Para el alcance actual se conserva la decisión de no crear catálogos de municipio/categoría y de modelar la ubicación de productos mediante las relaciones implementadas en V7. | `docs/diseno/reconciliacion-der.md`, `docs/diseno/decisiones-normalizacion.md`, `docs/diseno/schema.sql`, `src/main/resources/db/migration/` e issue #46. |
| D-08 | Discovery y aprobación de mockups | El issue #46 documenta que el Discovery y el visto bueno de mockups fueron informales y asíncronos; confirma el criterio de algunos integrantes. El repositorio no prueba que Discovery ocurriera antes de codificar ni una aprobación individual de cada pantalla por todo el equipo. La documentación vigente se usa como referencia de diseño para continuar; su cronología y aprobación colectiva no se reescriben como hechos. | [Issue #46](https://github.com/santixit/agrovalle-connect/issues/46), `docs/sprints/sprint-diseno/registro-sesion-6.md` y `docs/diseno/mockups-ui.pdf`. |

## Acuerdos operativos que se aplican desde esta versión

1. La persona que abre una historia mantiene sus criterios BDD y enlaces a evidencia actualizados.
2. La persona que abre un Pull Request enlaza historias, pruebas ejecutadas y cambios documentales; la revisión independiente verifica esa relación antes del merge.
3. Una historia solo se marca aceptada si consta la decisión del PO con fecha y evidencia. El estado `Done` del tablero no sustituye esa decisión.
4. Un informe de métricas incluye periodo, fuente, fecha de extracción y datos suficientes para reproducir el cálculo. Si faltan eventos, registra “no calculable con la evidencia disponible”.
5. El esquema cambia mediante una nueva migración Flyway y sus pruebas; no se edita una migración ya aplicada.
6. Las decisiones que cambien comportamiento se reflejan primero en Backlog/BDD y luego en contrato, código, pruebas y DER.

## Trazabilidad

- [SRS consolidada](system-requirements-specification.md)
- [Backlog BDD](product-backlog-bdd.md)
- [Reconciliación DER](../diseno/reconciliacion-der.md)
- [Plan Sprint 3](../sprints/sprint-3/planning.md)
- [Review Sprint 3](../sprints/sprint-3/review.md)
- [Métricas ágiles](../metrics/sprint-metrics-corte3.md)
