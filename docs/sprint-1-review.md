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

- Ejecución local del 2026-09-29 con `mvnw.cmd --batch-mode --no-transfer-progress clean verify`: build exitoso; 11 pruebas aprobadas, 0 fallidas, 0 errores de Checkstyle y 95,00% de cobertura de líneas (304/320; mínimo configurado 60%). Se usó JDK 26 con compilación `--release 17` y perfil local H2; este resultado no demuestra ejecución sobre PostgreSQL.
- Ejecución de GitHub Actions con Java 17 y PostgreSQL 16 para el commit revisado: **adjuntar enlace al run y confirmar el resultado actual**.
- Pull Request de implementación: **adjuntar enlace y comprobar que la evidencia de aprobación corresponde a una revisión anterior al merge**.
- Resultado de Checkstyle y pruebas en el incremento revisado: **confirmar en el run actual; no inferirlo de este borrador**.

En la revisión anterior del repositorio no se encontró una aprobación registrada en PR #23 antes de su merge. El equipo debe comprobar el historial del PR: si la aprobación ocurrió después, no satisface el requisito de aprobación previa. No debe presentarse una revisión posterior como si hubiera ocurrido antes; conviene documentar el hecho y aplicar la regla correctamente en los próximos PR.

El repositorio no conserva en este borrador el enlace a un run actual de GitHub Actions. Antes de entregar, el equipo debe añadir el run que corresponde al commit demostrado y confirmar allí el resultado de la suite sobre PostgreSQL.

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
