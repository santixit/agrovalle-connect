# Sprint Review — Sprint 1

> Evidencia tecnica preparada el 2026-09-28 antes de la reunion de Sprint Review. Este registro no sustituye la demostracion ni los acuerdos con Product Owner y equipo; completar esa parte durante la reunion.

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
- La CI esta configurada para usar PostgreSQL 16; en GitHub el flujo de `main` aun esta rojo por dos clases principales detectadas en esa rama. La rama de trabajo elimina la clase duplicada. Adjuntar resultado verde de GitHub Actions y evidencia de API/PostgreSQL despues de ejecutar el flujo actualizado y realizar la demostracion.
