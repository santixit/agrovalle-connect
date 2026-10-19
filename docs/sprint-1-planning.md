# Planificacion del Sprint 1

> Documento de planificación técnica del Sprint 1: HU-01, HU-07 y HU-04 (10 SP). El equipo indica que trabajó informalmente y de forma asíncrona, sin una reunión formal de Sprint Planning ni roles permanentes. Este archivo describe el alcance y la descomposición técnica registrados; no es un acta ni prueba de una ceremonia. El issue #46 registra el Backlog como texto base y estos criterios técnicos como criterios de aceptación; las confirmaciones de integrantes que no comentaron siguen pendientes. Véase la [comparación](evidencias/comparacion-historias-backlog-sprint1.md).

## Sprint Goal

Habilitar el registro inicial de agricultores del Valle del Cauca y la consulta filtrada del catalogo agricola, validando la persistencia en PostgreSQL y la arquitectura REST.

## Capacidad y seleccion

| Historia | Prioridad | Estimacion |
|---|---|---:|
| HU-01 Registro de agricultores | Must have | 5 SP |
| HU-07 Consulta del perfil del agricultor | Must have | 2 SP |
| HU-04 Filtro por municipio y categoria | Must have | 3 SP |
| **Total** | | **10 SP** |

## Descomposicion tecnica

Estimaciones técnicas que aparecen en el plan documentado; no se atribuyen a una reunión formal. Ninguna tarea supera 8 horas según esta descomposición.

| ID / HU | Tarea tecnica y componente Java/Spring | Est. | ISO/IEC 25010 | Evidencia prevista |
|---|---|---:|---|---|
| T01 / HU-01 | Crear migracion SQL para tabla `agricultores`, cedula unica e indice | 4 h | Fiabilidad: integridad de datos | Migracion y restricciones verificadas |
| T02 / HU-01 | Crear entidad `Agricultor` y `AgricultorRepository` con `existsByCedula` | 4 h | Mantenibilidad: separacion de persistencia | Pruebas de repositorio |
| T03 / HU-01 | Crear DTO de entrada/salida y validaciones de datos | 4 h | Seguridad: validacion de entrada | Pruebas de validacion |
| T04 / HU-01 | Implementar `AgricultorService` y rechazo de cedulas duplicadas | 4 h | Fiabilidad: manejo de duplicados | Pruebas unitarias |
| T05 / HU-01 | Implementar `AgricultorController` para el registro REST | 4 h | Adecuacion funcional | Prueba de contrato |
| T06 / HU-01 | Traducir BDD de registro a JUnit 5 y MockMvc (obligatorio) | 6 h | Fiabilidad y mantenibilidad | Suite automatizada verde |
| T07 / HU-01 | Auditar y corregir Checkstyle en componentes de registro | 2 h | Mantenibilidad: conformidad de codigo | Checkstyle sin errores |
| T08 / HU-07 | Implementar consulta de perfil en `AgricultorRepository`, `AgricultorService` y `AgricultorController`, con DTO | 4 h | Seguridad: exponer solo datos necesarios | Pruebas perfil existente/no encontrado |
| T09 / HU-07 | Automatizar BDD de consulta con JUnit 5/MockMvc | 4 h | Fiabilidad: resultado asociado al ID | Prueba de integracion |
| T10 / HU-04 | Implementar filtros opcionales en `ProductoRepository` y servicio | 5 h | Adecuacion funcional: filtros coincidentes | Pruebas combinaciones |
| T11 / HU-04 | Exponer consulta en `ProductoController` y validar coleccion JSON | 3 h | Adecuacion funcional | Prueba MockMvc Dagua/Frutas |
| T12 / HU-04 | Automatizar BDD de filtros coincidentes, combinados y sin resultados | 4 h | Fiabilidad: casos limite | Suite JUnit 5/MockMvc |

## Criterios tecnicos

- HU-01: `POST /api/v1/auth/register` registra agricultor con nombre, ubicacion en el Valle y cedula validos en PostgreSQL, y evita cedulas duplicadas.
- HU-07: `GET /api/v1/productores/{id}` consulta el perfil público del agricultor con datos no sensibles y maneja identificadores inexistentes.
- HU-04: `GET /api/v1/productos` acepta municipio, categoría, ambos filtros o ninguno; devuelve las ofertas activas coincidentes como colección JSON, incluida una lista vacía si no hay resultados.
- Los escenarios BDD se traducen a pruebas JUnit 5/MockMvc; Checkstyle y DoD deben pasar antes de Done.

## Riesgos y dependencias

- La implementacion local declara JPA, driver PostgreSQL y Flyway; las pruebas usan H2 en modo compatible con PostgreSQL.
- La integracion continua levanta PostgreSQL 16 para ejecutar las pruebas de persistencia; la demostracion final debe adjuntar evidencia de persistencia.
- Pendiente de validación colectiva: si se mantienen las estimaciones en horas y los contratos de datos; el issue #46 resolvió la versión base de historias y la adopción de criterios técnicos, aunque aún falten comentarios de integrantes.
