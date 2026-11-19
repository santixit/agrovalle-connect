# Sprint 3: validación y aceptación de historias implementadas

> **Estado:** propuesta de validación basada en el alcance que eligió el usuario. No es evidencia de aprobación formal del equipo ni del Product Owner (PO), y no indica que el sprint haya comenzado.

## Sprint Goal propuesto

Validar con pruebas automatizadas y una demostración las historias HU-02, HU-06, HU-09 y HU-10; corregir los defectos que se encuentren y registrar para cada criterio BDD la decisión del Product Owner: aceptado, rechazado con observaciones o pendiente.

El tablero muestra las cuatro historias en `Done`, pero ese estado por sí solo no demuestra la aceptación del PO. Las estimaciones originales del backlog suman 14 SP de implementación; **no deben tratarse como capacidad ni como trabajo nuevo de validación**. El equipo debe estimar las tareas de validación y confirmar su capacidad antes de declarar Sprint 3 formalmente planificado.

## Alcance y criterios de aceptación

| Historia | Qué debe demostrarse | Evidencia técnica que se prepara |
|---|---|---|
| HU-06: registrar finca | Un agricultor autenticado crea una finca; se asocia a su perfil y aparece en su listado. | Prueba de API `fincaOfertaReservaDespachoTrazabilidadYFavoritos`; prueba de repositorio `FincaRepositoryTests`. |
| HU-02: publicar cosecha | Un agricultor autenticado publica una cosecha con fecha de hoy o futura; recibe 201 y la oferta aparece en el catálogo. Sin autenticación se rechaza; una fecha pasada se rechaza. | Pruebas de API `fincaOfertaReservaDespachoTrazabilidadYFavoritos`, `cuentaAgricultorPuedeAutenticarseYPublicarOferta`, `publicacionDeOfertaExigeAutenticacion` y `explicaPorQueNoAceptaLaFechaPasadaDeUnaCosecha`. |
| HU-09: cambiar estado | El propietario puede marcar el lote `AGOTADO` y deja de aparecer en ofertas disponibles; otro agricultor no puede cambiarlo. | Prueba de API `fincaOfertaReservaDespachoTrazabilidadYFavoritos`; pruebas de servicio `actualizarEstadoRechazaOfertaDeOtroAgricultor` y `actualizarEstadoNoPublicaOfertaSinInventario`. |
| HU-10: consultar detalle | El público ve producto, agricultor, finca, precio y cantidad disponible; la respuesta no incluye cédula ni credenciales. | Prueba de API `fincaOfertaReservaDespachoTrazabilidadYFavoritos`; prueba de servicio `consultarDetalleRechazaOfertaInactiva`. |

### Traducción BDD a pruebas automatizadas

Los escenarios BDD se verifican en `src/test/java/co/edu/uniajc/agrovalle/AgrovalleConnectApplicationTests.java` mediante MockMvc contra la API y la base H2 aislada de pruebas. En particular, la prueba integrada comprueba que la finca se lista, la cosecha se publica y filtra en el catálogo, el detalle entrega precio y cantidad sin cédula, y al marcarla `AGOTADO` deja de aparecer en el catálogo. Las pruebas unitarias y de repositorio complementan los escenarios de permisos y consultas.

## Pasos de validación

1. Revisar que las cuatro historias y sus escenarios en [`product-backlog-bdd.md`](../../requirements/product-backlog-bdd.md) coincidan con lo que espera el PO.
2. Ejecutar las pruebas automatizadas en la rama de trabajo con `./mvnw -Dtest=AgrovalleConnectApplicationTests,GestionProductoServiceTest,CatalogoProductoServiceTest,FincaRepositoryTests test` (en PowerShell de Windows: `.\mvnw.cmd '-Dtest=AgrovalleConnectApplicationTests,GestionProductoServiceTest,CatalogoProductoServiceTest,FincaRepositoryTests' test`).
   **Resultado registrado (19 de noviembre de 2026):** Maven reportó `BUILD SUCCESS`, 37 pruebas, 0 fallos, 0 errores y 0 omitidas. Es resultado de JUnit; no demuestra por sí solo ejecución de Checkstyle/JaCoCo en CI ni aceptación del PO.
3. Si el resultado es `BUILD FAILURE`, abrir los informes de `target/surefire-reports`, corregir la causa y repetir. No marcar una historia aceptada mientras un criterio falle.
4. Si todas las pruebas pasan, demostrar el flujo en ambiente de pruebas con datos sintéticos: registrar finca, publicar cosecha, consultar detalle, marcar agotado y volver a consultar catálogo. No usar datos reales ni la base de producción.
5. Completar `checklist-inicio.md` y guardar capturas o salida de CI en `docs/evidencias/sprint-3/`.
6. El PO revisa cada criterio BDD y deja su decisión en GitHub Projects o en el acta de Sprint Review. Si solicita cambios, crear tareas de corrección; si acepta, registrar fecha y responsable de la aceptación.

## Tareas técnicas propuestas

| Tarea | Área | Relación de calidad (ISO/IEC 25010) | Hecho cuando |
|---|---|---|---|
| Automatizar el escenario de cada HU con JUnit 5 / MockMvc | API y pruebas | Adecuación funcional, fiabilidad | Se comprueban estado HTTP, respuesta y persistencia/comportamiento observable. |
| Comprobar autorización y datos privados | Seguridad | Seguridad y confidencialidad | Un actor no autorizado es rechazado y las respuestas públicas no exponen cédula/credenciales. |
| Comprobar actualización del estado y su efecto en catálogo | Servicio / repositorio | Fiabilidad, adecuación funcional | `AGOTADO` deja la oferta fuera de resultados disponibles. |
| Ejecutar las pruebas pertinentes y atender fallos | CI | Mantenibilidad, fiabilidad | JUnit 5 pasa; Checkstyle y JaCoCo se revisan en CI sin afirmar resultados antes de tenerlos. |
| Revisar criterios con el PO y registrar decisiones | Sprint Review | Adecuación funcional | Cada HU tiene estado de aceptación y comentario/evidencia verificable. |

La fecha, capacidad, responsables, sustituciones de historias ya aceptadas y estimación de estas tareas deben acordarse con el equipo antes de convertir esta propuesta en compromiso de sprint. La proyección general permanece en [proyeccion.md](../proyeccion.md).
