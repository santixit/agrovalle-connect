# Arquitectura MVC en cuatro capas y patrones

## Arquitectura observada

La aplicacion es un monolito Spring Boot 3.3.4 sobre Java 17. El navegador consume JSON por HTTPS en staging o HTTP en desarrollo. El artefacto Spring Boot integra la interfaz estatica, la API REST y el acceso al motor PostgreSQL mediante Spring Data JPA y JDBC. Flyway aplica el esquema con migraciones versionadas.

| Capa | Responsabilidad | Implementacion actual |
|---|---|---|
| 1. Presentacion y API REST | Recibir HTTP, validar entradas, serializar DTO y responder codigos HTTP. | `src/main/resources/static/`; `controller/`; `api/dto/`; `ApiExceptionHandler`. |
| 2. Servicio y dominio | Aplicar reglas, coordinar transacciones y expresar entidades/reglas del dominio. | `service/` y `domain/`; `@Service`, `@Transactional`, excepciones de dominio. |
| 3. Repositorio y acceso a datos | Abstraer consultas y operaciones CRUD del almacenamiento. | `repository/`; interfaces Spring Data JPA. |
| 4. Persistencia relacional | Guardar datos y restricciones con integridad transaccional. | PostgreSQL; `src/main/resources/db/migration/V1` a `V4`; entidades `@Entity`. |

## Mapeo del DER a entidades JPA

| Tabla PostgreSQL | Entidad JPA |
|---|---|
| `usuarios` | `Usuario` |
| `agricultores` | `Agricultor` |
| `compradores` | `Comprador` |
| `fincas` | `Finca` |
| `productos` | `Producto` |
| `pedidos` | `Pedido` |
| `detalle_pedido` | `DetallePedido` |
| `despachos` | `Despacho` |
| `eventos_trazabilidad` | `EventoTrazabilidad` |
| `contactos` | `Contacto` |
| `favoritos` | `Favorito` |
| `notificaciones` | `Notificacion` |
| `transacciones_precio` | `TransaccionPrecio` |

Los nombres corresponden a las clases del paquete `domain`; las migraciones Flyway definen las columnas, llaves y restricciones efectivamente ejecutadas.

La guia usa `ProductorDTO` como ejemplo; el codigo actual separa solicitudes y respuestas (`RegistroAgricultorRequest`, `AgricultorResponse`) y no expone la entidad JPA al cliente.

## Patrones presentes

| Patron | Uso en el codigo | Aporte de calidad |
|---|---|---|
| Repository | `AgricultorRepository`, `ProductoRepository` y los demas repositorios Spring Data encapsulan persistencia. | Mantenibilidad y modificabilidad al separar SQL/ORM de reglas. |
| Singleton / IoC | Spring administra beans `@Service`, `@Repository` y `@Component` como singletons por defecto; dependencias se inyectan por constructor. | Testabilidad, bajo acoplamiento y ciclo de vida centralizado. |
| DTO | Records `*Request` y `*Response` separan JSON del modelo JPA y validan entradas. | Seguridad de informacion, compatibilidad y validacion. |
| Factory | `UsuarioFactory` crea credenciales/rol para agricultor y comprador. | Consistencia al crear cuentas. |
| Data Mapper | Metodos `from(...)` de respuestas convierten entidades a DTO; el servicio crea entidades a partir de solicitudes. | Separa modelo persistente del contrato externo. |

El proyecto no implementa un Builder dedicado. La guia de evaluacion permite Factory/Mapper; se documentan los patrones realmente usados, sin agregar un patron solo para completar una lista.

## Despliegue

La topologia vigente se representa en [diagrama-despliegue.puml](diagrama-despliegue.puml): navegador, servicio Spring Boot en Render y base PostgreSQL administrada. La ejecucion CI usa PostgreSQL de servicio en GitHub Actions y no forma parte de la red productiva.

## Restricciones y trazabilidad

- `schema.sql` en esta carpeta es una vista consolidada solo para lectura; Flyway V1-V4 son la autoridad ejecutable.
- Los perfiles de agricultor historicos pueden no tener cuenta `Usuario`, por eso la relacion de cuenta es opcional.
- Una oferta puede conservar municipio propio y una finca opcional para soportar registros historicos; el modelo no debe asumir que todo producto tiene finca.
- Los detalles de pedido conservan precio y cantidad como instantanea de la transaccion, aunque cambien los datos actuales de la oferta.
- La revision de PO de los mockups se deja pendiente hasta que el equipo la realice.
