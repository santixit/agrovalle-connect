# Sprint Review — Sprint 1

> Borrador de apoyo para la revisión. Describe el incremento y prepara una demostración reproducible; no acredita que la reunión o la demostración ya hayan ocurrido. Los campos de reunión, aceptación y feedback deben completarse con datos reales del equipo.

## Contexto y alcance

AgroValle Connect busca conectar directamente a productores del Valle del Cauca con comerciantes y restaurantes de Cali y sus alrededores, reduciendo la intermediación y mejorando la visibilidad de la oferta agrícola. En este Sprint 1, el incremento acotado cubre el registro inicial de agricultores y la consulta de su perfil, además del filtro del catálogo por municipio y categoría. Pedidos, stock, logística y trazabilidad no forman parte de este Sprint.

- Sprint Goal: habilitar el registro inicial de agricultores del Valle del Cauca y la consulta filtrada del catálogo agrícola, validando la persistencia en PostgreSQL y la arquitectura REST.
- Capacidad planificada: 10 Story Points.
- Historias seleccionadas: HU-01 (5 SP), HU-07 (2 SP) y HU-04 (3 SP).

## Datos de la revisión

- Fecha de preparación de este borrador: 2026-09-29.
- Fecha y hora de la reunión: **por completar por el equipo**.
- Participantes y roles presentes: **por completar por el equipo**.
- Product Owner y decisión de aceptación: **por completar durante la revisión**.
- Evidencia de la reunión/demo: **adjuntar capturas o enlace real**.

## Historias revisadas

| Historia | Alcance que debe demostrarse | Evidencia técnica existente | Resultado y feedback de la revisión |
|---|---|---|---|
| HU-01 — Registro de agricultores | `POST /api/v1/auth/register`; registro persistido; respuesta `201 Created`; la respuesta no expone la cédula. | Implementación y escenarios automatizados descritos en el registro técnico previo. Colección Postman: `docs/postman/agrovalle-sprint-1.postman_collection.json`. Evidencia en PostgreSQL y resultado actual de CI: **pendientes de adjuntar**. | **Completar en la reunión**: aceptada/no aceptada, comentario del Product Owner y seguimiento. |
| HU-07 — Consulta del perfil del agricultor | `GET /api/v1/productores/{id}`; perfil existente en `200 OK`; identificador inexistente en `404 Not Found`; DTO sin cédula. | Implementación y escenarios automatizados descritos en el registro técnico previo. Colección Postman: `docs/postman/agrovalle-sprint-1.postman_collection.json`. Captura de respuestas: **pendiente**. | **Completar en la reunión**: aceptada/no aceptada, comentario del Product Owner y seguimiento. |
| HU-04 — Filtro del catálogo | `GET /api/v1/productos?municipio=Dagua&categoria=Frutas`; resultado `200 OK`; solo ofertas coincidentes. | Colección Postman: `docs/postman/agrovalle-sprint-1.postman_collection.json`; datos de ejemplo idempotentes: `scripts/sprint-1-demo-data.sql`. Captura del resultado y verificación PostgreSQL: **pendientes**. | **Completar en la reunión**: aceptada/no aceptada, comentario del Product Owner y seguimiento. |

## Guion técnico para la demostración

Este guion permite al equipo obtener evidencia real. Debe ejecutarse antes o durante la Sprint Review y completarse con los resultados observados; los resultados esperados de abajo no equivalen a resultados ya verificados.

1. Iniciar PostgreSQL local con `docker compose up -d db` y esperar que el contenedor esté saludable.
2. Preparar la oferta de demostración con datos sintéticos: `psql -h localhost -U agrovalle -d agrovalle_connect -f scripts/sprint-1-demo-data.sql`. El script agrega `Mango de Dagua` (Frutas), `Platano de Cali` (Frutas) y `Cafe de Dagua` (Granos) de forma idempotente.
3. Iniciar la API con `mvn spring-boot:run` y confirmar que quedó disponible en `http://localhost:8080`.
4. Importar y ejecutar en orden la colección `docs/postman/agrovalle-sprint-1.postman_collection.json` usando únicamente los datos de prueba. El registro configura el identificador del agricultor para la consulta HU-07.
5. Comprobar la persistencia con una consulta de solo lectura: `SELECT id, nombre, municipio FROM agricultores WHERE cedula = 'ID-DE-PRUEBA-001';`. Usar una cédula ficticia; no registrar datos personales reales.
6. Guardar evidencia auténtica de las tres respuestas HTTP y de la fila persistida. **Adjuntar aquí las capturas o enlaces resultantes:** por completar.

Resultados que se esperan según los contratos documentados: HU-01 devuelve `201`; HU-07 devuelve `200` para el ID registrado; HU-04 con Dagua y Frutas devuelve Mango de Dagua y excluye las ofertas de Cali o de otras categorías. Si la colección ya se ejecutó antes, cambiar el valor de prueba a una cédula ficticia nueva o reiniciar la base de demostración para evitar el conflicto de duplicado.

## Calidad y trazabilidad

- Validación inicial del primer corte en el commit `c2a0fd5`: 11 pruebas aprobadas y 95,00% de cobertura de líneas (304/320), ejecutada con H2; no demuestra ejecución sobre PostgreSQL.
- Validación local complementaria de `feature/full-integrator-phase1` (2026-09-29): `mvnw.cmd clean verify` terminó correctamente con Eclipse Temurin JDK 17.0.20.1: 30 pruebas aprobadas, 0 fallas, Checkstyle con 0 violaciones y JaCoCo 90,11% (820/910 líneas; umbral 60%). Se ejecutó el perfil local H2.
- La conexión de la aplicación con PostgreSQL 18 en la base local `agrovalle conect` se autenticó y Flyway aplicó las migraciones V1–V4. Esta comprobación confirma la creación del esquema; no equivale a una demostración funcional de los endpoints ni a las pruebas completas sobre PostgreSQL.
- GitHub Actions ejecutó correctamente el workflow con Java 17 y PostgreSQL 16 en el commit `bdf3990` de `feature/full-integrator-phase1`: [run de CI](https://github.com/santixit/agrovalle-connect/actions/runs/36601582622). En local, `mvnw.cmd clean verify` también pasó con Temurin 17.0.20.1, 30 pruebas, 0 violaciones de Checkstyle y 90,11% de cobertura JaCoCo.
- La regla pública `main-protection` aparece activa para la rama predeterminada `main`; exige una aprobación, el chequeo `Build, tests, Checkstyle and JaCoCo`, y bloquea borrado y force-push. [Configuración del ruleset](https://github.com/santixit/agrovalle-connect/rules/24141794).
- [PR #23](https://github.com/santixit/agrovalle-connect/pull/23) se fusionó el 2026-09-28. GitHub registra la aprobación de `DannyGomez02` el 2026-09-29, después de la fusión; no cumple el requisito de aprobación previa.
- [PR #24](https://github.com/santixit/agrovalle-connect/pull/24) recibió aprobación de `DannyGomez02` antes de fusionarse el 2026-09-29 y reporta dos comprobaciones exitosas. Ese PR modificó documentación; no valida por sí solo la implementación completa del integrador.
- El workflow no despliega en una rama feature. El despliegue automático desde `main` sigue pendiente de elegir proveedor y configurar `STAGING_DEPLOY_HOOK`; el equipo debe comprobar una URL pública y una consulta real antes de afirmar que staging funciona.
- La demostración con Postman, evidencia de fila persistida en PostgreSQL, resultado del Product Owner, Daily Scrums y retrospectiva siguen pendientes de evidencia real del equipo.

## Acuerdos de la revisión

- Historias aceptadas por el Product Owner: **por completar**.
- Historias no aceptadas y motivo: **por completar**.
- Observaciones del Product Owner/comerciantes: **por completar**.
- Cambios acordados al Product Backlog: **por completar; no mover historias a Sprint 2 sin acuerdo del equipo/profesora**.
- Acciones, responsables y fecha objetivo: **por completar**.

## Evidencias que debe adjuntar el equipo

- [ ] Captura o enlace de la ejecución real de HU-01, HU-07 y HU-04 en Postman/Swagger.
- [ ] Captura o salida de consulta que demuestre la fila persistida en PostgreSQL con datos sintéticos.
- [ ] Enlace al run de GitHub Actions del commit revisado, con build, pruebas, Checkstyle y cobertura visibles.
- [ ] Fecha, participantes, decisión y feedback reales de la Sprint Review.
- [ ] Enlace al PR y evidencia de revisión por pares previa al merge, cuando exista.
