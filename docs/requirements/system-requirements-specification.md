# Especificación de Requisitos del Sistema (SRS)

| Campo | Valor |
|---|---|
| Producto | AgroValle Connect |
| Versión documental | 1.0 |
| Estado | Línea base consolidada para revisión del Product Owner y del equipo. |
| Fuente | Repositorio, Product Backlog, contratos REST, migraciones y documentación de arquitectura disponibles en esta revisión. |

> Este documento reúne los requisitos conocidos y traza cada requisito a su historia y verificación. No inventa acuerdos de negocio ni objetivos de calidad que el equipo no haya aprobado. Las decisiones abiertas al final deben resolverse antes de declarar esta SRS aprobada o afirmar cumplimiento integral de IEEE 29148.

## 1. Introducción

### 1.1 Propósito

Especificar el comportamiento observable, interfaces, datos, restricciones y requisitos de calidad conocidos de AgroValle Connect. La SRS sirve de referencia para desarrollo, pruebas, revisión del Product Owner y cambios futuros.

### 1.2 Alcance del producto

AgroValle Connect es una aplicación web y API REST que conecta agricultores del Valle del Cauca con compradores, comercios y restaurantes. Permite publicar cosechas, buscar ofertas, contactar productores, reservar inventario y coordinar entregas. El sistema también conserva trazabilidad, notificaciones de aplicación, favoritos y un reporte administrativo. La aplicación contiene además funciones técnicas del integrador —por ejemplo, registro de comprador, gestión de parcelas y transferencias— que complementan las 15 HU del Backlog y se identifican por separado más adelante.

La versión documentada implementa las historias HU-01 a HU-15. El alcance histórico de MoSCoW y los criterios BDD canónicos están en el [Product Backlog](product-backlog-bdd.md); su prioridad de primer incremento no se debe confundir con el estado actual del código.

### 1.3 Convenciones y términos

- **SRS:** Software/System Requirements Specification.
- **HU:** historia de usuario del Product Backlog.
- **PO:** Product Owner.
- **BDD:** criterios de comportamiento en formato Given–When–Then.
- **Debe:** requisito obligatorio para el comportamiento descrito.
- **Pendiente de decisión:** el repositorio no contiene una decisión aprobada; no se presupone un valor.

### 1.4 Fuentes de referencia

- [Product Backlog y escenarios BDD](product-backlog-bdd.md).
- [Especificación formal REQ-01](ieee-29148-req01.md).
- [Contratos REST y flujos](../api-sprint-1.md).
- [Arquitectura y UML](../architecture/diagramas-uml.md).
- [Modelo y migraciones de datos](../diseno/README.md) y `src/main/resources/db/migration/`.
- [Definition of Done](../dod.md), [estrategia de pruebas](../estrategia-pruebas.md) y workflow de [CI](../../.github/workflows/ci.yml) / [CD](../../.github/workflows/cd.yml).

## 2. Descripción general

### 2.1 Contexto del sistema

El navegador consume la interfaz web y los recursos REST desplegados por Spring Boot. Los controladores validan solicitudes y delegan reglas de negocio a servicios; los repositorios Spring Data JPA persisten en PostgreSQL. Flyway es la fuente ejecutable para evolucionar el esquema. Las pruebas de integración locales pueden usar H2; CI ejecuta el perfil `ci` con PostgreSQL.

El entorno público documentado es staging en Render con PostgreSQL de staging en Neon. Esto no constituye una aprobación de ambiente de producción.

### 2.2 Clases de usuario

| Actor | Capacidades documentadas |
|---|---|
| Agricultor autenticado | Mantener fincas y parcelas, publicar y actualizar cosechas, gestionar reservas, programar despachos y consultar notificaciones. |
| Comprador autenticado | Contactar agricultores, reservar ofertas, seguir pedidos y administrar favoritos. |
| Usuario público | Consultar ofertas activas, detalles públicos y perfiles de agricultor sin datos sensibles. |
| Administrador | Consultar el reporte de actividad; no existe registro público de cuentas `ADMIN`. |

### 2.3 Entorno operativo

- Java 17 y Spring Boot 3.3.4 con Maven.
- PostgreSQL 16 como base de datos de CI; PostgreSQL gestionado en staging.
- Flyway para migraciones y Hibernate en modo `validate`.
- Navegador web moderno con JavaScript habilitado; API JSON bajo `/api/v1`.
- GitHub Actions para compilación, pruebas, Checkstyle, JaCoCo, pruebas E2E y entrega a staging tras éxito en `main`.

### 2.4 Restricciones conocidas

1. Las migraciones aplicadas no se editan; se agrega una migración Flyway nueva.
2. Las credenciales y los secretos de despliegue se configuran fuera del repositorio.
3. Los endpoints privados requieren credenciales y autorización por rol.
4. El registro público no permite crear una cuenta `ADMIN`.
5. La API no debe incluir cédula, hash de contraseña ni secretos en respuestas públicas.
6. El histórico de precios corresponde al promedio de transacciones completadas disponible en el sistema; no es una serie histórica semanal.

## 3. Interfaces externas

### 3.1 Interfaz de usuario

La aplicación sirve su interfaz web desde `/`. Los formularios y acciones consumen la API disponible en el mismo servicio. Las capacidades no persistidas por el backend deben presentarse como tales; la documentación de diseño señala, por ejemplo, que la foto solo tiene vista previa local.

### 3.2 Interfaz de software

- API REST versionada bajo `/api/v1`, con solicitudes y respuestas JSON.
- Autenticación mediante `POST /api/v1/auth/login`; las rutas protegidas reciben `Authorization: Bearer <token>`.
- Códigos observados/documentados: `400` validación, `401` no autenticado, `403` sin permiso, `404` recurso inexistente y `409` conflicto de unicidad, inventario o estado.
- Readiness: `GET /api/v1/health`.

### 3.3 Interfaz de datos

PostgreSQL almacena perfiles, usuarios, compradores, fincas, parcelas, productos, pedidos, detalle de pedidos, despachos, eventos de trazabilidad, notificaciones, contactos, favoritos y transacciones de precio. Las llaves, restricciones y nombres ejecutables se definen en las migraciones V1–V7; el snapshot `docs/diseno/schema.sql` es solo referencia y no se ejecuta junto con Flyway.

## 4. Requisitos funcionales

Cada requisito de esta tabla se desglosa en un escenario BDD verificable en el [Product Backlog](product-backlog-bdd.md). La ruta corresponde al contrato documentado; una ruta listada no sustituye pruebas de permisos ni de errores.

| ID | Requisito del sistema | Actor | Interfaz principal | Trazabilidad y verificación |
|---|---|---|---|---|
| FR-01 | El sistema debe registrar un perfil de agricultor con datos válidos, rechazar duplicados y devolver un identificador sin exponer su cédula. Si se envían credenciales, deben validarse y persistirse de forma consistente con el perfil. | Público / agricultor | `POST /api/v1/auth/register` | HU-01; REQ-01; pruebas de registro y privacidad. |
| FR-02 | El sistema debe permitir que un agricultor autenticado publique una cosecha válida y la incorpore al catálogo de ofertas activas. | Agricultor | `POST /api/v1/productos` | HU-02; pruebas de publicación autenticada y validación de fecha. |
| FR-03 | El sistema debe calcular el promedio regional de precios con las transacciones completadas disponibles dentro de la ventana documentada de 24 horas, usando como máximo las 50 más recientes. | Usuario | `GET /api/v1/precios/regionales` | HU-03; prueba de servicio de precios. |
| FR-04 | El sistema debe filtrar el catálogo por municipio, categoría, ambos filtros o ninguno y devolver únicamente ofertas activas que coincidan. | Público / comprador | `GET /api/v1/productos` | HU-04; pruebas de filtros y lista vacía. |
| FR-05 | El sistema debe registrar el contacto de un comprador con el agricultor asociado a una oferta activa. | Comprador | `POST /api/v1/contactos` | HU-05; pruebas de persistencia y asociación del contacto. |
| FR-06 | El sistema debe permitir que un agricultor autenticado cree una finca y la asocie a su perfil. | Agricultor | `POST /api/v1/fincas` | HU-06; pruebas de creación, propiedad y consulta. |
| FR-07 | El sistema debe mostrar el perfil público existente con identificador, nombre y ubicación, sin cédula ni credenciales; si no existe debe responder `404`. | Público | `GET /api/v1/productores/{id}` | HU-07; prueba de consulta y privacidad. |
| FR-08 | El sistema debe permitir reservar cantidades disponibles, descontar inventario de manera atómica y rechazar una operación que produzca sobreventa. En una reserva de carrito, el descuento de todas las líneas debe confirmarse o revertirse como una unidad. | Comprador | `POST /api/v1/reservas`, `POST /api/v1/reservas/carrito` | HU-08; pruebas transaccionales, de duplicados y concurrencia. |
| FR-09 | El sistema debe permitir al agricultor propietario cambiar el estado de su lote y dejar de ofrecerlo en el catálogo cuando ya no esté disponible. | Agricultor | `PATCH /api/v1/productos/{id}/estado` | HU-09; prueba de permiso del propietario y efecto en catálogo. |
| FR-10 | El sistema debe mostrar el detalle de una oferta pública con producto, agricultor, finca, precio y disponibilidad, sin datos privados. | Público / comprador | `GET /api/v1/productos/{id}` | HU-10; pruebas de contenido y privacidad. |
| FR-11 | Al registrarse un contacto, el sistema debe persistir una notificación de aplicación para el agricultor destinatario y permitirle consultarla. | Agricultor | `GET /api/v1/notificaciones/mias` | HU-11; pruebas de evento y consulta de notificación. |
| FR-12 | El sistema debe permitir programar el despacho de un pedido confirmado con fecha y franja horaria y registrar las transiciones de despacho soportadas. | Agricultor | `POST /api/v1/despachos`, `PATCH /api/v1/despachos/{pedidoId}/en-ruta`, `PATCH /api/v1/despachos/{pedidoId}/entregado` | HU-12; pruebas de permisos, estados y transición. |
| FR-13 | El sistema debe mostrar al comprador propietario del pedido sus eventos de trazabilidad en orden cronológico y no permitir consultar pedidos ajenos. | Comprador | `GET /api/v1/reservas/{id}/trazabilidad` | HU-13; pruebas de orden y autorización. |
| FR-14 | El sistema debe permitir a un comprador guardar, consultar y quitar ofertas favoritas, con unicidad por comprador y producto. | Comprador | `/api/v1/favoritos` | HU-14; pruebas de creación, listado y eliminación. |
| FR-15 | El sistema debe permitir a un administrador consultar un resumen de actividad limitado a un intervalo de fechas; usuarios sin rol `ADMIN` no deben acceder. | Administrador | `GET /api/v1/admin/reportes/actividad` | HU-15; pruebas de rol, fechas y agregados. |

### 4.1 Funciones adicionales implementadas

Las funciones siguientes están presentes en el contrato/código actual, pero no se agregan como historias nuevas al Backlog histórico sin aprobación del PO.

| ID | Requisito adicional | Actor | Interfaz principal | Verificación |
|---|---|---|---|---|
| FR-16 | El sistema debe permitir registrar una cuenta de comprador con correo y contraseña, y autenticar las cuentas habilitadas para emitir un token de acceso. No debe permitir registro público con rol administrador. | Comprador / agricultor | `POST /api/v1/auth/register/comprador`, `POST /api/v1/auth/login` | Pruebas de registro, login, hash y permisos. |
| FR-17 | El sistema debe permitir al agricultor consultar sus parcelas y crear una parcela asociada a una finca que le pertenece. | Agricultor | `GET /api/v1/parcelas/mias`, `POST /api/v1/fincas/{fincaId}/parcelas` | Pruebas de pertenencia y persistencia. |
| FR-18 | El sistema debe permitir transferir una oferta o parcela a otro agricultor autorizado y consultar el historial cronológico de transferencias, preservando titular anterior, nuevo y fecha. | Agricultor | `POST /api/v1/productos/{id}/transferencias`, `GET /api/v1/productos/{id}/transferencias` | Migración V7 y pruebas de servicio/repositorio. |
| FR-19 | El sistema debe permitir al comprador consultar sus pedidos, al agricultor consultar pedidos pendientes y a la persona autorizada confirmar/preparar el pedido antes del despacho. | Comprador / agricultor | `GET /api/v1/reservas/mias`, `GET /api/v1/reservas/pendientes`, `POST /api/v1/reservas/{id}/confirmar`, `POST /api/v1/reservas/{id}/preparar` | Pruebas de autorización y transiciones del pedido. |

## 5. Requisitos de datos

| ID | Requisito | Verificación |
|---|---|---|
| DR-01 | Cada tabla de negocio debe tener clave primaria y las relaciones del modelo deben usar claves foráneas cuando correspondan. | Migraciones Flyway y validación Hibernate. |
| DR-02 | Correo de usuario y cédula de agricultor deben respetar las restricciones únicas definidas por el esquema. | Restricciones de base y pruebas de duplicidad. |
| DR-03 | Cantidades y precios deben respetar restricciones de signo del esquema; una reserva no puede reducir disponibilidad por debajo de cero. | `CHECK`/servicios transaccionales y pruebas. |
| DR-04 | Los eventos de transferencia y trazabilidad deben conservar su secuencia histórica; el responsable actual se deriva del modelo de parcela/ubicación vigente. | V7, repositorios y pruebas de dominio. |
| DR-05 | El esquema debe evolucionar exclusivamente mediante migraciones Flyway versionadas. | CI aplica migraciones; `ddl-auto=validate`. |

## 6. Requisitos no funcionales

Los requisitos siguientes expresan controles observables o umbrales configurados. No se establecen metas numéricas sin acuerdo del equipo.

| ID | Calidad ISO/IEC 25010 | Requisito | Evidencia / verificación |
|---|---|---|---|
| NFR-SEC-01 | Seguridad | Las contraseñas de cuenta deben almacenarse como hash BCrypt y no deben devolverse en DTOs. | Configuración de seguridad, entidades/servicio y pruebas de autenticación. |
| NFR-SEC-02 | Seguridad | El sistema debe autenticar con JWT y limitar operaciones privadas por identidad y rol. | Spring Security, pruebas de acceso `401`/`403`. |
| NFR-SEC-03 | Seguridad | Los endpoints públicos de perfil y oferta deben omitir cédula y credenciales. | DTOs de respuesta y pruebas JSON. |
| NFR-REL-01 | Fiabilidad | La reserva de inventario y la operación de carrito deben preservar consistencia transaccional ante conflicto o falta de inventario. | Transacciones JPA, bloqueo de escritura y pruebas. |
| NFR-COMP-01 | Compatibilidad | Las integraciones del cliente deben usar HTTP(S), JSON y el prefijo de API `/api/v1`. | Contratos REST y pruebas MockMvc/E2E. |
| NFR-MAIN-01 | Mantenibilidad | El backend debe conservar separación controller/service/repository/domain, validación DTO y manejo centralizado de errores. | Revisión de paquetes y pruebas de capas. |
| NFR-MAIN-02 | Mantenibilidad | El proyecto debe compilar con Java 17; Checkstyle debe pasar y JaCoCo debe cumplir el umbral de línea configurado de 60%. | `pom.xml`; job `quality` de CI. |
| NFR-TEST-01 | Fiabilidad / mantenibilidad | Los escenarios BDD acordados deben tener pruebas automatizadas JUnit 5 pertinentes; el workflow ejecuta además pruebas de navegador E2E para el flujo crítico. | `src/test/`, `npm run test:e2e` y GitHub Actions. |
| NFR-OBS-01 | Fiabilidad | El servicio debe exponer readiness para verificar disponibilidad de la aplicación después de iniciar y aplicar migraciones. | `GET /api/v1/health`; verificación en CD. |

No hay en las fuentes revisadas objetivos aprobados para tiempos de respuesta, concurrencia máxima, disponibilidad/SLA, RPO/RTO, accesibilidad, retención/eliminación de datos personales o compatibilidad con navegadores por versión. Se registran como decisiones abiertas; no se asignan valores arbitrarios.

## 7. Verificación, trazabilidad y gestión de cambios

1. El criterio BDD de cada HU es la fuente de comportamiento de producto.
2. El plan de Sprint y los contratos REST definen la descomposición técnica sin reemplazar las reglas de negocio.
3. Las pruebas automatizadas deben identificar el requisito o historia cubierta y demostrar resultados observables.
4. CI verifica build, pruebas, Checkstyle, JaCoCo, E2E y migraciones; CD solo se ejecuta en `main` tras éxito de calidad.
5. Un cambio a esta SRS debe actualizar el BDD, contrato, modelo, pruebas y evidencia afectados, además de registrar su revisión en PR.

| Artefacto | Fuente canónica |
|---|---|
| Historias y escenarios | [Product Backlog BDD](product-backlog-bdd.md) |
| Diseño y trazabilidad REQ-01 | [IEEE 29148 REQ-01](ieee-29148-req01.md) |
| Contratos y ejemplos HTTP | [API](../api-sprint-1.md) y colección en `docs/postman/` |
| Modelo y esquema | [Diseño de datos](../diseno/README.md) y migraciones Flyway |
| Pruebas y calidad | `src/test/`, `pom.xml`, [CI](../../.github/workflows/ci.yml) y [CD](../../.github/workflows/cd.yml) |
| Aceptación de producto | PR, issue o Sprint Review con decisión explícita del PO |

## 8. Decisiones abiertas para aprobación

Estas preguntas no bloquean la lectura de los requisitos implementados, pero deben responderse para convertir esta línea base en una SRS aprobada sin decisiones supuestas.

| ID | Decisión pendiente | Responsable propuesto | Evidencia de cierre |
|---|---|---|---|
| OQ-01 | Confirmar reglas de negocio de campos, límites, municipios, cancelación de reservas y transiciones válidas de pedidos/despachos. | PO y equipo | Comentario aprobado en issue/PR con escenarios actualizados. |
| OQ-02 | Definir clasificación, retención, rectificación y eliminación de datos personales, incluidos cédula, teléfono y correo. | PO y equipo | Política escrita y requisito trazable. |
| OQ-03 | Aprobar objetivos cuantitativos de rendimiento, concurrencia, disponibilidad y recuperación (SLA, RPO/RTO), o declarar que no aplican al prototipo académico. | PO / responsable de despliegue | Decisión versionada; no inventar umbrales. |
| OQ-04 | Acordar navegadores soportados y criterios de accesibilidad de la interfaz. | PO y equipo | Matriz de compatibilidad/accesibilidad aprobada. |
| OQ-05 | Confirmar si habrá pagos, almacenamiento remoto de imágenes o notificaciones externas; hoy no se declaran como funciones implementadas. | PO | Decisión de alcance y HU correspondiente si se aprueba. |
| OQ-06 | Confirmar aceptación funcional de HU-02, HU-06, HU-09 y HU-10 después de demo y revisar el registro de Sprint 3. | PO | Decisión por historia con fecha, evidencia y observaciones. |
| OQ-07 | Validar el diseño de dominio y los DER propuestos contra el esquema Flyway vigente. | Equipo / PO | Reconciliación revisada y decisión registrada. |

### Aprobación

| Revisión | Responsable | Fecha | Estado |
|---|---|---|---|
| Revisión de contenido técnico | Pendiente de confirmación del equipo | — | Borrador consolidado |
| Aprobación de requisitos de producto | Product Owner | — | Pendiente |
