# Sprint de Diseno y cierre de Sprint 1

Este paquete documenta el diseno arquitectonico del sistema AgroValle Connect y enlaza los artefactos comprobables en el repositorio. Se basa en la implementacion actual; no se presenta como una fase historica realizada antes de programar. El usuario del proyecto informa que el Product Owner dio su visto bueno a los mockups el 17 de octubre de 2026; esta nota registra esa confirmacion, no un acta firmada ni la aprobacion de otros artefactos.

## Artefactos

- Modelo entidad-relacion implementado: fuentes [conceptual](modelo-entidad-relacion.mmd) y [fisica](modelo-datos-der.mmd), alineadas con las migraciones actuales. Se retiraron sus antiguos exports graficos para que no sigan apareciendo junto con los nuevos disenos.
- [Snapshot DDL PostgreSQL](schema.sql). Es una consolidacion legible de Flyway V1-V4 para consulta y evaluacion. Las migraciones siguen siendo la unica fuente ejecutable del esquema; no ejecutar este snapshot en paralelo con Flyway.
- [Arquitectura MVC en cuatro capas y patrones](arquitectura-mvc-gof.md).
- Fuentes UML editables de componentes, despliegue y secuencia: [diagrama-componentes.puml](diagrama-componentes.puml), [diagrama-despliegue.puml](diagrama-despliegue.puml) y [diagrama-secuencia.puml](diagrama-secuencia.puml). Se quitaron las imagenes PNG y el PDF de las versiones visuales anteriores.
- [Prototipos de interfaz](mockups-ui.pdf), actualizados con los wireframes y mockups aportados para inicio, detalle y publicacion. Las seis laminas se conservan como PNG en [mockups/](mockups/); la interfaz estatica se ajusto al patron visual y mantiene sus flujos/API actuales. Visto bueno de los mockups: confirmado por el usuario del proyecto el 17 de octubre de 2026; no se adjunto acta firmada.
- Modelos de datos aportados como propuesta: [DER conceptual propuesto](modelo-entidad-relacion-propuesta.pdf) y [DER fisico propuesto](modelo-datos-der-propuesta.pdf), con sus [imagenes originales](mockups/). La [reconciliacion con el esquema implementado](reconciliacion-der.md) identifica equivalencias y diferencias; estos DER siguen siendo propuestas y no reemplazan los modelos canonicos ni el DDL.
- Especificacion IEEE 29148 y comparacion BDD: [../ieee29148-req01.md](../ieee29148-req01.md).
- Los contratos HTTP detallados de HU-01, HU-07 y HU-04 estan en [../api-sprint-1.md](../api-sprint-1.md); las 15 historias y escenarios BDD estan en [../../BACKLOG.md](../../BACKLOG.md).

## Alcance

La consigna de esta guia exige terminar HU-01, HU-07 y HU-04 en Sprint 1. Se conserva la capacidad de 10 SP ya acordada (5 + 2 + 3). La guia tambien menciona Sprint 2; se deja fuera de este trabajo para respetar la decision del equipo de no iniciarlo todavia.

No se preparo un PDF unico de entrega. Los artefactos quedan separados en esta carpeta y en `docs/ieee29148-req01.md`, como indica la guia, para revisarlos y versionarlos en GitHub.

## Prototipos entregados

El PDF de mockups agrupa primero las tres pantallas de baja fidelidad y despues las tres de alta fidelidad (inicio, detalle y publicar). Los originales PNG se guardan en `mockups/` para que puedan editarse o compararse. La pantalla de publicar refleja los datos soportados por el DTO vigente; no se agrega una carga de imagen ficticia porque el contrato y el backend actual no persisten imagenes.

Los dos diagramas ER suministrados se publican como referencias de propuesta. El [DER implementado](modelo-entidad-relacion.mmd), el [DER fisico implementado](modelo-datos-der.mmd) y las migraciones `V1` a `V4` siguen describiendo el estado real de la aplicacion. Se retiraron de este PR los exports graficos anteriores de esos modelos.

## Validaciones del equipo pendientes

- Product Owner: el usuario del proyecto informa que los wireframes aportados recibieron visto bueno el 17 de octubre de 2026. Sigue pendiente validar la version definitiva de historias, especialmente las diferencias entre el backlog actual y el documento `Sprint Planning- Sprint1 - Qué`.
- Equipo: revisar que el modelo conceptual y las decisiones de normalizacion representen las reglas reales del negocio.
- Peer review: el PR #23 se aprobo despues de fusionarse; esa aprobacion no demuestra peer review previo. Los futuros PR deben aprobarse antes del merge.
- Sprint 1: conservar el run CI y evidencias funcionales existentes, verificando que correspondan al commit que se entrega.
- DoD: el archivo actual es una lista de verificacion, no tiene firmas verificables; las firmas deben agregarlas los integrantes si la docente las solicita.
- Sprint Discovery: los artefactos describen el diseno comprobable actual; no hay evidencia fechada suficiente para afirmar que todas las actividades de discovery ocurrieron antes de empezar a codificar.
