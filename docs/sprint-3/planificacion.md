# Sprint 3 — detalle de oferta y contacto

> **Estado:** borrador para revisar con el Product Owner. Este documento propone trabajo; no afirma que el Sprint 3 ya haya sido aprobado, iniciado o terminado.

## 1. Objetivo del Sprint

Permitir que un comprador consulte el detalle de una oferta y contacte al agricultor desde la aplicación, y dejar el recorrido demostrado con criterios de aceptación y evidencia reproducible.

## 2. Historias candidatas

| Historia | Prioridad | Estimación original | Estado encontrado en el repositorio |
|---|---|---:|---|
| HU-10: consultar el detalle público de una oferta | Must have | 3 SP | El README la declara implementada. |
| HU-05: contactar al agricultor desde una oferta | Must have | 5 SP | El README la declara implementada; existe una prueba de integración del backend. |
| **Total de referencia** |  | **8 SP** | Son las estimaciones originales del backlog, no puntos nuevos ni capacidad ya consumida. |

El Backlog estima HU-10 y HU-05 en 3 y 5 Story Points. Como las dos historias ya aparecen implementadas, el equipo debe confirmar si todavía están pendientes de aceptación, si requieren correcciones concretas o si se deben sustituir por otras historias antes de comprometer el Sprint. No se duplican ni se vuelven a contar como trabajo terminado.

## 3. Hechos revisados en el código

- La interfaz web consulta `GET /api/v1/productos/{id}` para mostrar nombre, finca, municipio, agricultor, precio y disponibilidad.
- Para un comprador autenticado, la interfaz muestra la acción **Contactar** y envía el mensaje mediante `POST /api/v1/contactos`.
- `ContactoService` persiste el contacto y publica un evento para generar una notificación al agricultor.
- `AgrovalleConnectApplicationTests` contiene una prueba de integración que registra un contacto y consulta la notificación del agricultor.
- La prueba de navegador `e2e/marketplace-flow.spec.js` cubre registro, publicación y reserva; no se encontró allí el recorrido de detalle y contacto.

> Estos puntos son una revisión del estado del código. No equivalen a una aprobación del Product Owner ni a una demo ejecutada en este Sprint.

## 4. Descomposición técnica propuesta

| ID | Tarea | Hecho cuando |
|---|---|---|
| S3-T1 | Revisar con el Product Owner los criterios BDD de HU-10 y HU-05. | Cada criterio queda marcado como aceptado, fallido o pendiente, con observación concreta. |
| S3-T2 | Verificar el contrato del detalle: oferta existente, oferta inexistente y campos privados. | El detalle devuelve los campos públicos acordados y no expone cédula, contraseñas, tokens ni otros secretos. |
| S3-T3 | Verificar el contacto: comprador autenticado, oferta activa, mensaje y notificación al agricultor. | El contacto queda vinculado al comprador, agricultor y oferta, y el agricultor recibe su notificación. |
| S3-T4 | Completar una prueba E2E de navegador para ver detalle y enviar contacto como comprador. | La prueba recorre la UI y comprueba el mensaje de éxito y la notificación visible para el agricultor. |
| S3-T5 | Comprobar errores y permisos del flujo. | Se registran resultados para usuario sin sesión, rol no comprador y oferta no disponible o inexistente. |
| S3-T6 | Guardar evidencia de la demo y actualizar la revisión del Sprint. | Se adjuntan capturas o resultados de ejecución sin datos reales ni secretos, y se anotan los resultados observados. |

Las tareas S3-T2 a S3-T5 son una propuesta de verificación y endurecimiento. Si la revisión inicial demuestra que un criterio ya pasa, se conserva la evidencia y no se reimplementa esa función. Si falla, se crea una tarea de corrección concreta antes de modificar código.

## 5. Criterios BDD para la revisión

### HU-10 — detalle de oferta

**Escenario: consultar una oferta activa**

- **Dado** que existe una oferta activa con agricultor y finca asociados
- **Cuando** un usuario abre su detalle desde el catálogo
- **Entonces** se muestran el producto, municipio, precio y disponibilidad
- **Y** no se exponen la cédula ni las credenciales del agricultor

**Escenario: consultar una oferta que no existe o no está disponible**

- **Dado** que el identificador no corresponde a una oferta consultable
- **Cuando** el usuario solicita el detalle
- **Entonces** la aplicación informa que no se pudo consultar la oferta y no muestra datos de otra oferta

### HU-05 — contacto con el agricultor

**Escenario: comprador contacta una oferta activa**

- **Dado** que un comprador inició sesión y existe una oferta activa
- **Cuando** envía un mensaje válido desde el detalle
- **Entonces** el sistema registra el contacto asociado a la oferta y al comprador
- **Y** genera una notificación para el agricultor propietario de la oferta

**Escenario: persona no autenticada intenta contactar**

- **Dado** que una persona no inició sesión
- **Cuando** intenta enviar un contacto
- **Entonces** el sistema solicita autenticación y no registra el contacto

## 6. Definition of Done para este Sprint

- El Product Owner revisó los criterios y dejó registrada su decisión.
- El flujo de detalle y contacto funciona en la interfaz con datos de prueba.
- Los campos privados no aparecen en la respuesta pública del detalle.
- La prueba automatizada de navegador cubre el recorrido acordado, si el equipo confirma esa tarea.
- Las verificaciones automáticas obligatorias del repositorio pasan en el commit final.
- La demo y sus resultados están documentados en la revisión del Sprint.
- El tablero refleja el estado real de las tareas y cumple sus límites WIP.

## 7. Orden para empezar

1. Confirmar en GitHub Projects si HU-05 y HU-10 siguen abiertas o si ya fueron aceptadas.
2. Ejecutar manualmente el detalle de una oferta con datos de prueba.
3. Iniciar sesión como comprador de prueba y enviar un mensaje.
4. Iniciar sesión como agricultor de prueba y comprobar que recibió la notificación.
5. Registrar qué pasó en la lista de verificación de este directorio.
6. Solo si el recorrido manual pasa, automatizar el recorrido E2E propuesto en S3-T4.
7. Corregir únicamente los fallos observados, guardar evidencia y actualizar la revisión.

## 8. Registro de decisiones y resultados

| Fecha | Decisión o comprobación | Resultado / enlace a evidencia | Responsable |
|---|---|---|---|
| Pendiente | Confirmación del Product Owner sobre alcance y estado de HU-05/HU-10 | Pendiente | Pendiente |
| Pendiente | Prueba manual de detalle y privacidad | Pendiente | Pendiente |
| Pendiente | Prueba manual de contacto y notificación | Pendiente | Pendiente |
| Pendiente | Automatización E2E y ejecución | Pendiente | Pendiente |

