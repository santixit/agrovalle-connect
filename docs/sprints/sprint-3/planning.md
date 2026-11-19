# Planificación de Sprint 3 — validación y aceptación

**Fecha de actualización documental:** 2026-11-19
**Estado:** trabajo de validación iniciado; no hay acta de planning formal ni fechas originales de inicio/cierre registradas.
**Alcance elegido por el usuario:** revisar y obtener aceptación de HU-02, HU-06, HU-09 y HU-10.

## Sprint Goal

Comprobar con JUnit 5 y una demostración segura en staging los criterios BDD de HU-02, HU-06, HU-09 y HU-10; registrar por historia la decisión explícita del Product Owner y corregir cualquier incumplimiento antes de cerrar.

## Capacidad y estimación

Las HUs suman **14 SP de implementación histórica**: HU-02 (5), HU-06 (3), HU-09 (3) y HU-10 (3). No se reutilizan como capacidad de validación. Para planificar el cierre de esta validación se usa una **estimación de trabajo de 10 SP** basada en las actividades de abajo; es una línea base de planificación documental, no un registro retroactivo de compromiso inicial del equipo.

| Actividad | Estimación | Estado al 2026-11-19 | Hecho cuando |
|---|---:|---|---|
| T01 — relacionar criterios BDD con pruebas JUnit/MockMvc y revisar salida Surefire | 2 SP | Completada parcialmente: se compartió `BUILD SUCCESS`, 37 pruebas, 0 fallos, 0 errores y 0 omitidas | El reporte agregado y los métodos pertinentes están enlazados a cada historia. |
| T02 — ejecutar el gate de calidad sobre el estado final del sprint | 2 SP | Sin evidencia específica de ejecución de Sprint 3 en este registro | CI verde para el commit que se quiere presentar; resultado de Checkstyle/JaCoCo anotado. |
| T03 — demostrar en staging con datos sintéticos los flujos de las cuatro HUs | 3 SP | Sin evidencia de ejecución | Capturas o video breve muestran petición, respuesta, resultado y commit/entorno; no incluyen secretos ni datos reales. |
| T04 — revisar por historia con el PO y actualizar tablero/documentos | 3 SP | Sin decisión de aceptación registrada | Cada HU tiene `Aceptada` o `Requiere cambios`, fecha, responsable y evidencia; el tablero y review coinciden. |
| **Total plan de validación** | **10 SP** |  |  |

La distribución permite ordenar el trabajo pendiente; no afirma que esas tareas fueran estimadas en una reunión previa. El resultado de Maven compartido se conserva en [resultado-maven-2026-11-19.md](evidencias/resultado-maven-2026-11-19.md). Si la ejecución demuestra que una historia no cumple, el arreglo y su regresión se agregan como trabajo nuevo con su propia estimación.

## Historias y evidencia de prueba

| Historia | Criterio que se valida | Pruebas automatizadas existentes | Calidad ISO/IEC 25010 |
|---|---|---|---|
| HU-02 — publicar cosecha | Agricultor autenticado publica; fecha pasada se rechaza; oferta aparece en catálogo. | `cuentaAgricultorPuedeAutenticarseYPublicarOferta`, `publicacionDeOfertaExigeAutenticacion`, `explicaPorQueNoAceptaLaFechaPasadaDeUnaCosecha` y escenario integrado `fincaOfertaReservaDespachoTrazabilidadYFavoritos` en `AgrovalleConnectApplicationTests`. | Adecuación funcional, seguridad, fiabilidad. |
| HU-06 — registrar finca | Agricultor autenticado crea finca propia y la consulta. | `fincaOfertaReservaDespachoTrazabilidadYFavoritos`; `FincaRepositoryTests`. | Adecuación funcional, seguridad, mantenibilidad. |
| HU-09 — cambiar estado | Solo propietario cambia estado; oferta agotada no se ofrece en el catálogo. | `actualizarEstadoRechazaOfertaDeOtroAgricultor`, `actualizarEstadoNoPublicaOfertaSinInventario` y escenario integrado. | Seguridad, fiabilidad, adecuación funcional. |
| HU-10 — consultar detalle | Público consulta producto, finca/agricultor, precio y disponibilidad sin cédula ni credenciales; inactiva no está disponible. | Escenario integrado `fincaOfertaReservaDespachoTrazabilidadYFavoritos`; `consultarDetalleRechazaOfertaInactiva`. | Adecuación funcional, seguridad, privacidad. |

Los métodos citados demuestran que hay casos automatizados en las clases nombradas; no se atribuye un resultado individual por método que el reporte agregado no presenta. La transcripción compartida y su alcance están en [resultado Maven](evidencias/resultado-maven-2026-11-19.md).

## Orden de trabajo

1. Revisar los criterios exactos de las cuatro HUs en el [Backlog BDD](../../requirements/product-backlog-bdd.md).
2. Relacionar cada criterio con el método de prueba y revisar `target/surefire-reports` de la ejecución de 37 pruebas.
3. Ejecutar el pipeline sobre el commit final. La ejecución local de JUnit no sustituye Checkstyle, JaCoCo ni E2E del workflow.
4. Seguir el [guion de demo reproducible](demo-runbook.md) en staging usando solo datos sintéticos: crear finca, publicar cosecha con fecha válida, consultar detalle y marcar agotado; repetir lectura del catálogo.
5. Guardar evidencia por historia en `docs/sprints/sprint-3/evidencias/` y añadir el commit, ambiente y fecha.
6. El PO revisa cada criterio y registra su decisión real en la [review](review.md). Si pide cambios, abrir tareas y repetir las pruebas afectadas.
7. Sincronizar el tablero, el informe y la Definition of Done antes de declarar el cierre.

## Roles operativos por actividad

No se asignan personas a tareas sin que el tablero lo registre. La regla de trabajo es: quien implementa/prepara evidencia se asigna la tarjeta; otra persona revisa el PR; el PO emite la decisión funcional. Cada asignación individual se conserva en GitHub Projects.

## Estado de cierre

El Sprint 3 tiene una comprobación automatizada reportada, pero no cumple aún los criterios de cierre documental: faltan el gate de calidad del incremento, la evidencia de staging y las decisiones de aceptación por historia. La lista operativa detallada está en [checklist-inicio.md](checklist-inicio.md).
