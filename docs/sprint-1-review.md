# Sprint Review — Sprint 1

> Estado actualizado el 2026-09-28 con evidencia tecnica disponible. Este registro no sustituye la demostracion ni los acuerdos con Product Owner y equipo; completar esos datos despues de la reunion real.

## Datos de la revision

- Fecha de preparacion tecnica: 2026-09-28
- Fecha de reunion y participantes: por completar
- Sprint Goal: habilitar registro inicial de agricultores y consulta filtrada del catalogo, con persistencia PostgreSQL y arquitectura REST.

## Historias revisadas

| Historia | Estado demostrado | Evidencia (PR, prueba, captura o consulta PostgreSQL) | Feedback |
|---|---|---|---|
| HU-01 — Registro de agricultores | Implementado; pruebas automatizadas locales pasan | `AgrovalleConnectApplicationTests`: registro valido, cedula duplicada y validacion de campos | Pendiente demostracion de equipo y evidencia CI en PostgreSQL |
| HU-07 — Consulta del perfil del agricultor | Implementado; pruebas automatizadas locales pasan | `AgrovalleConnectApplicationTests`: perfil encontrado/no encontrado; la respuesta no expone cedula | Pendiente demostracion de equipo y evidencia CI en PostgreSQL |
| HU-04 — Filtro por municipio y categoria | Implementado; pruebas automatizadas locales pasan | `AgrovalleConnectApplicationTests`: filtro combinado, catalogo vacio y consulta sin filtros | Pendiente demostracion de equipo y evidencia CI en PostgreSQL |

## Incremento y acuerdos

- Resultado de `mvn clean verify`: exitoso en el entorno local; 10 pruebas pasan, 0 fallan; 0 violaciones Checkstyle; 93,4% de cobertura de lineas JaCoCo (71 de 76 lineas; umbral configurado: 60%). El entorno local usa Java 26 y emite avisos del agente JaCoCo por clases internas del JDK; el workflow configura Java 17.
- Funcionalidades aceptadas por Product Owner: completar durante la revision.
- Historias no aceptadas y motivo: completar.
- Feedback y cambios al Product Backlog: completar.
- GitHub Actions ejecuto correctamente el workflow de `main` con Java 17, PostgreSQL 16, pruebas, Checkstyle y JaCoCo el 2026-09-28. [Consultar la ejecucion](https://github.com/santixit/agrovalle-connect/actions/runs/36497630535). Esta evidencia confirma la CI; no reemplaza la demostracion funcional de los endpoints ni la consulta de persistencia en PostgreSQL durante la Sprint Review.
- El PR #23 recibio una revision `APPROVED` de `DannyGomez02` despues de que el PR ya se habia fusionado. La revision queda registrada, pero no demuestra aprobacion previa a la integracion. Para cumplir el flujo en adelante, las nuevas fusiones deben tener aprobacion antes de integrar.
- La proteccion de `main` ahora exige PR, una aprobacion y el check `Build, tests, Checkstyle and JaCoCo`. Esta regla aplica a fusiones futuras y no cambia el historial del PR #23.
- El job de staging puede finalizar sin desplegar si falta el secreto `STAGING_DEPLOY_HOOK`. Por eso, el estado de Actions no confirma por si solo que el servicio este publicado. Adjuntar la URL funcional y una consulta real a PostgreSQL cuando el equipo complete la demo.
