# Sprint Review — Sprint 1

> Acta de la Sprint Review del 28 de septiembre de 2026. Resume los acuerdos que compartió el equipo. La hora y duración no constan en las notas.

## Contexto y alcance

AgroValle Connect busca conectar directamente a productores del Valle del Cauca con comerciantes y restaurantes de Cali y sus alrededores, reduciendo la intermediación y mejorando la visibilidad de la oferta agrícola. En este Sprint 1, el incremento acotado cubre el registro inicial de agricultores y la consulta de su perfil, además del filtro del catálogo por municipio y categoría. Pedidos, stock, logística y trazabilidad no forman parte de este Sprint.

- Sprint Goal: habilitar el registro inicial de agricultores del Valle del Cauca y la consulta filtrada del catálogo agrícola, validando la persistencia en PostgreSQL y la arquitectura REST.
- Capacidad planificada: 10 Story Points.
- Historias seleccionadas: HU-01 (5 SP), HU-07 (2 SP) y HU-04 (3 SP).

## Datos de la revisión

- Fecha: **2026-09-28**.
- Hora y duración: **no registradas en las notas**.
- Asistentes: Danny Alexander Gómez (Scrum Master), Michelle Guerrero Arboleda (Product Owner), Starlin Gómez Asprilla (Developer) y Raúl Santiago Carrillo (Developer).
- Decisión del Product Owner: HU-01, HU-07 y HU-04 aceptadas dentro del alcance funcional acordado para Sprint 1.
- Acuerdos y feedback: registrados en las notas compartidas por el equipo. La evidencia técnica se obtuvo durante la verificación en staging del 30 de septiembre.

## Historias revisadas

| Historia | Alcance revisado | Evidencia técnica | Decisión y feedback |
|---|---|---|---|
| HU-01 — Registro de agricultores | Registro de un agricultor y persistencia de sus datos de perfil. | [Postman, respuesta 201](evidencias/sprint-1/01-hu01-postman.png); [fila en PostgreSQL](evidencias/sprint-1/04-postgresql-agricultor.png). | **Aceptada.** El Product Owner consideró que cumple el alcance funcional acordado. |
| HU-07 — Consulta del perfil del agricultor | Consulta del perfil público de un agricultor existente. | [Postman, respuesta 200](evidencias/sprint-1/02-hu07-postman.png). | **Aceptada.** El Product Owner consideró que cumple el alcance funcional acordado. |
| HU-04 — Filtro del catálogo | Consulta del catálogo por municipio y categoría. | [Postman, respuesta 200 para Cali y verdura](evidencias/sprint-1/03-hu04-postman.png). | **Aceptada.** El Product Owner consideró que cumple el alcance funcional acordado. |

El Product Owner pidió conservar los flujos actuales de registro, consulta de perfiles y filtro del catálogo, y seguir ampliando la plataforma hacia publicación de cosechas, contacto y reservas. También recomendó mantener la validación de persistencia en PostgreSQL y las pruebas automatizadas.

## Evidencia técnica verificada en staging

El equipo verificó los flujos contra la aplicación publicada en Render y consultó la base PostgreSQL de staging el 30 de septiembre de 2026. Las capturas muestran los resultados observados:

1. HU-01: la petición POST respondió `201` y creó el registro sintético con id `2`.
2. HU-07: la petición GET del agricultor `2` respondió `200` y devolvió su perfil.
3. HU-04: el filtro `municipio=cali&categoria=verdura` respondió `200` e incluyó la oferta de aguacate.
4. PostgreSQL: la consulta por el identificador sintético `DEMO-RAUL-20260930-PM01` devolvió una fila de `agricultores`, id `2`, nombre `Ana Ruiz Demo` y municipio `Dagua`.

Las imágenes están guardadas en `docs/evidencias/sprint-1/`. Para repetir las consultas, usa la URL de staging y las credenciales de Render disponibles en su panel; no copies contraseñas ni tokens a este repositorio.

## Calidad y trazabilidad

- Validación inicial del primer corte en el commit `c2a0fd5`: 11 pruebas aprobadas y 95,00% de cobertura de líneas (304/320), ejecutada con H2; no demuestra ejecución sobre PostgreSQL.
- Validación de `feature/full-integrator-phase1` (2026-09-29): el commit `aa12a0d` pasó GitHub Actions con Java 17, PostgreSQL 16, las 31 pruebas, Checkstyle y el control de cobertura JaCoCo. [Run exitoso](https://github.com/santixit/agrovalle-connect/actions/runs/36608168866). Esto verifica CI; no sustituye la verificación funcional de la aplicación de staging.
- La conexión de la aplicación con PostgreSQL 18 en la base local `agrovalle conect` se autenticó y Flyway aplicó las migraciones V1–V4. Esta comprobación confirma la creación del esquema; no equivale a una demostración funcional de los endpoints ni a las pruebas completas sobre PostgreSQL.
- El historial conserva ejecuciones CI exitosas de commits anteriores; para el estado actual debe usarse el run enlazado al commit `aa12a0d` arriba.
- La regla pública `main-protection` aparece activa para la rama predeterminada `main`; exige una aprobación, el chequeo `Build, tests, Checkstyle and JaCoCo`, y bloquea borrado y force-push. [Configuración del ruleset](https://github.com/santixit/agrovalle-connect/rules/24141794).
- [PR #23](https://github.com/santixit/agrovalle-connect/pull/23) se fusionó el 2026-09-28. GitHub registra la aprobación de `DannyGomez02` el 2026-09-29, después de la fusión. Por tanto, #23 no demuestra revisión y aprobación previa a la integración ni satisface la política de peer review para ese merge.
- [PR #24](https://github.com/santixit/agrovalle-connect/pull/24) recibió aprobación de `DannyGomez02` antes de fusionarse el 2026-09-29 y reporta dos comprobaciones exitosas. Su único cambio fue documental (`docs/sprint-1-planning.md` y `docs/sprint-1-review.md`); demuestra aprobación previa de documentación, no una revisión previa del código de las historias del Sprint.
- El workflow no despliega en una rama feature. La aplicación de staging en Render respondió durante la verificación del 30 de septiembre; el despliegue automático desde `main` requiere el secreto `STAGING_DEPLOY_HOOK`.

## Acuerdos de la revisión

- Historias aceptadas: **HU-01, HU-07 y HU-04**.
- Historias no aceptadas: **ninguna según las notas compartidas**.
- Feedback del Product Owner: conservar los flujos de registro, consulta de perfiles y filtro del catálogo; continuar la validación de persistencia en PostgreSQL y las pruebas automatizadas.
- Cambios al Product Backlog: mantener HU-01, HU-04 y HU-07 como completadas en Sprint 1. Para el siguiente incremento, considerar HU-02 (publicación de cosechas), HU-05 (contacto con agricultor) y HU-08 (reservas), respetando las prioridades vigentes del backlog.
- Hora, duración y acciones con responsable y fecha objetivo: **no constan en las notas compartidas**.

## Evidencias

- Postman: HU-01 (`201`), HU-07 (`200`) y HU-04 (`200`), en `docs/evidencias/sprint-1/`.
- PostgreSQL: consulta del registro de prueba sintético, en `docs/evidencias/sprint-1/`.
- CI del commit `aa12a0d`: [ejecución exitosa](https://github.com/santixit/agrovalle-connect/actions/runs/36608168866).
