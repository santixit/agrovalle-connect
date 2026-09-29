# Modelo UML evolutivo

Estos diagramas describen el backend REST y la interfaz estática servida por Spring Boot. Los flujos representados están conectados con entidades, servicios, repositorios y pruebas de integración. Actualizar los diagramas junto con cambios del código.

## Casos de uso

```mermaid
flowchart LR
  agricultor[Actor: Agricultor]
  comprador[Actor: Comerciante o restaurante]
  admin[Actor: Administrador]
  registrar((Registrarse))
  login((Iniciar sesión))
  publicar((Publicar cosecha))
  reservar((Reservar inventario))
  despachar((Programar despacho))
  filtrar((Buscar catálogo))
  contactar((Contactar agricultor))
  favorito((Guardar oferta favorita))
  reporte((Consultar actividad))
  agricultor --> registrar
  agricultor --> login
  agricultor --> publicar
  agricultor --> despachar
  agricultor --> contactar
  comprador --> filtrar
  comprador --> login
  comprador --> reservar
  comprador --> contactar
  comprador --> favorito
  admin --> reporte
```

## Actividad: registro de agricultor

```mermaid
flowchart TD
  A([Inicio]) --> B[Recibir datos de registro]
  B --> C{Datos validos?}
  C -- No --> D[Responder errores de validacion]
  C -- Si --> E{Cedula ya registrada?}
  E -- Si --> F[Rechazar duplicado]
  E -- No --> G[Guardar Agricultor en PostgreSQL]
  G --> H[Responder 201 y DTO sin cedula]
  D --> I([Fin])
  F --> I
  H --> I
```

## Clases principales

```mermaid
classDiagram
  class AgricultorController {
    +registrar(request) AgricultorResponse
    +consultar(id) AgricultorResponse
  }
  class ProductoController {
    +buscar(municipio, categoria) List~ProductoResponse~
    +publicar(jwt, request) ProductoResponse
    +consultarDetalle(id) OfertaDetalleResponse
    +actualizarEstado(jwt, id, request) ProductoResponse
  }
  class AgricultorService {
    +registrar(request) AgricultorResponse
    +consultar(id) AgricultorResponse
  }
  class ProductoService {
    +buscar(municipio, categoria) List~ProductoResponse~
  }
  class AgricultorRepository {
    +existsByCedula(cedula) boolean
    +findById(id) Optional~Agricultor~
  }
  class ProductoRepository {
    +findByActivoTrue() List~Producto~
    +findByActivoTrueAndMunicipioIgnoreCase(municipio) List~Producto~
    +findByActivoTrueAndCategoriaIgnoreCase(categoria) List~Producto~
    +findByActivoTrueAndMunicipioIgnoreCaseAndCategoriaIgnoreCase(municipio, categoria) List~Producto~
  }
  class Agricultor {
    -Long id
    -String nombre
    -String cedula
    -String municipio
  }
  class Producto {
    -Long id
    -String nombre
    -String categoria
    -String municipio
    -boolean activo
  }
  AgricultorController --> AgricultorService
  AgricultorService --> AgricultorRepository
  AgricultorRepository --> Agricultor
  ProductoController --> ProductoService
  ProductoService --> ProductoRepository
  ProductoRepository --> Producto
  class UsuarioFactory
  class NotificacionObserver
  class ReglasDisponibilidad
  class ReservaService
  UsuarioFactory --> Usuario
  ReservaService --> ReglasDisponibilidad
  NotificacionObserver --> Notificacion
```

## Modelo de entidades persistentes

El siguiente diagrama representa las entidades JPA incorporadas en la fase de arquitectura. El rol `ADMIN` se conserva en `Usuario`; no se crea una tabla de administradores sin atributos propios. `DetallePedido` captura cantidad y precio por unidad al momento de reservar.

```mermaid
classDiagram
  class Usuario {
    Long id
    String correo
    String passwordHash
    RolUsuario rol
    boolean activo
  }
  class Agricultor {
    Long id
    String nombre
    String cedula
    String municipio
  }
  class Comprador {
    Long id
    String nombre
    String telefono
    String tipoComercio
  }
  class Finca {
    Long id
    String nombre
    String municipio
    String direccion
  }
  class Producto {
    Long id
    String nombre
    String categoria
    String municipio
    BigDecimal cantidadKg
    BigDecimal precioPorKg
    LocalDate fechaCosecha
    EstadoProducto estado
  }
  class Pedido {
    Long id
    EstadoPedido estado
  }
  class DetallePedido {
    Long id
    BigDecimal cantidadKg
    BigDecimal precioPorKg
  }
  class Despacho {
    Long id
    EstadoDespacho estado
    LocalDate fechaProgramada
  }
  class EventoTrazabilidad {
    Long id
    EstadoPedido estado
    String descripcion
  }
  class Contacto {
    Long id
    String mensaje
    EstadoContacto estado
  }
  class Favorito {
    Long id
  }
  class Notificacion {
    Long id
    TipoNotificacion tipo
    boolean leida
  }
  class TransaccionPrecio {
    Long id
    BigDecimal cantidadKg
    BigDecimal precioPorKg
  }
  Usuario "0..1" --> "0..1" Agricultor : cuenta
  Usuario "1" --> "0..1" Comprador : cuenta
  Agricultor "1" --> "0..*" Finca : posee
  Agricultor "0..1" --> "0..*" Producto : publica
  Finca "0..1" --> "0..*" Producto : publica
  Comprador "1" --> "0..*" Pedido : realiza
  Pedido "1" --> "1..*" DetallePedido : contiene
  Producto "1" --> "0..*" DetallePedido : reservado
  Pedido "1" --> "0..1" Despacho : coordina
  Pedido "1" --> "0..*" EventoTrazabilidad : registra
  Comprador "1" --> "0..*" Contacto : inicia
  Agricultor "1" --> "0..*" Contacto : recibe
  Producto "1" --> "0..*" Contacto : consulta
  Comprador "1" --> "0..*" Favorito : guarda
  Producto "1" --> "0..*" Favorito : marcado
  Usuario "1" --> "0..*" Notificacion : recibe
  Producto "1" --> "0..*" TransaccionPrecio : referencia
```

## Secuencia: registro

```mermaid
sequenceDiagram
  actor Agricultor
  participant C as AgricultorController
  participant S as AgricultorService
  participant R as AgricultorRepository
  participant DB as PostgreSQL
  Agricultor->>C: POST /api/v1/auth/register
  C->>S: registrar(request validado)
  S->>R: existsByCedula(cedula)
  R->>DB: consultar cedula
  DB-->>R: resultado
  R-->>S: no existe
  S->>R: save(agricultor)
  R->>DB: INSERT agricultores
  DB-->>R: agricultor persistido
  R-->>S: entidad guardada
  S-->>C: DTO publico
  C-->>Agricultor: 201 Created
```

## Diagrama de colaboracion (comunicacion UML): HU-04 filtro del catalogo

Los nodos representan objetos participantes y los numeros de los mensajes indican el orden de la interaccion. La respuesta recorre los mismos enlaces en sentido inverso. Este diagrama complementa la secuencia de registro y muestra como colaboran la interfaz, las capas MVC, el repositorio y PostgreSQL para aplicar ambos filtros.

```mermaid
flowchart LR
  comprador["comprador: Usuario"] -->|1. ingresar municipio y categoria| interfaz["interfaz: CatalogoWeb"]
  interfaz -->|2. GET /api/v1/productos?municipio=Dagua&categoria=Frutas| controller["controller: ProductoController"]
  controller -->|3. buscar(municipio, categoria)| service["service: ProductoService"]
  service -->|4. consultar ofertas activas coincidentes| repository["repository: ProductoRepository"]
  repository -->|5. SELECT de ofertas activas y filtros| db[("PostgreSQL")]
  db -->|6. filas coincidentes| repository
  repository -->|7. entidades Producto| service
  service -->|8. lista de ProductoResponse| controller
  controller -->|9. HTTP 200 y JSON| interfaz
  interfaz -->|10. mostrar tarjetas coincidentes| comprador
```

## Secuencia: reserva y despacho

```mermaid
sequenceDiagram
  actor Comprador
  actor Agricultor
  participant API as ReservaController
  participant Servicio as ReservaService
  participant Regla as ReglasDisponibilidad
  participant DB as PostgreSQL
  participant Observer as NotificacionObserver
  Comprador->>API: POST /api/v1/reservas/carrito con JWT
  API->>Servicio: reservarCarrito(usuario, items)
  Servicio->>DB: bloquear ofertas por ID y leer stock
  DB-->>Servicio: stock vigente
  Servicio->>Regla: validar todas las cantidades
  Servicio->>DB: agrupar por agricultor, descontar stock y crear pedidos/eventos
  DB-->>API: carrito consolidado o rollback total
  Agricultor->>API: confirmar, iniciar preparación y programar despacho
  Agricultor->>API: marcar salida a ruta
  API->>DB: persistir estado y trazabilidad
  DB-->>Observer: evento después del commit
  Observer->>DB: persistir notificación
```

## Despliegue físico actual

```mermaid
flowchart LR
  subgraph Desarrollo local
    Dev[Equipo Java 17]
    Compose[Docker Compose]
    LocalDB[(PostgreSQL 16)]
    Dev -->|REST| AppLocal[Spring Boot API]
    AppLocal --> LocalDB
    Compose -. inicia .-> LocalDB
  end
  subgraph Integracion continua
    Runner[GitHub Actions Ubuntu]
    CiDB[(PostgreSQL 16 service)]
    Runner -->|mvn clean verify| AppTest[Spring Boot y JUnit 5]
    AppTest --> CiDB
  end
  subgraph Render Staging
    Stage[Render Web Service Free<br/>Spring Boot Java 17]
    StageDB[(PostgreSQL administrado<br/>agrovalle_connect_staging)]
    Stage --> StageDB
  end
  Browser[Comprador o agricultor] -->|HTTPS| Stage
```

La instancia pública de staging es [AgroValle Connect en Render](https://agrovalle-connect-staging.onrender.com/). Al ser un servicio gratuito, puede suspenderse por inactividad; la base de datos gratuita también tiene una fecha de expiración indicada por Render.

## Patrones y decisiones

- **Repository:** repositorios Spring Data JPA aíslan persistencia y consultas.
- **Factory:** `UsuarioFactory` crea cuentas con hash BCrypt y un rol válido.
- **Observer:** `NotificacionObserver` observa eventos transaccionales de contacto y pedido para persistir notificaciones.
- **Singleton:** `ReglasDisponibilidad` es stateless y Spring lo administra como singleton para validar inventario.
- La interfaz demostrativa vive en `src/main/resources/static/index.html` y consume los endpoints REST.

## Pendientes de evolucion

Pendiente de evidencia del equipo: adjuntar fecha, participantes y resultado del Planning Poker si esa sesión ya ocurrió; si no ocurrió, realizarla antes de afirmar que las estimaciones fueron acordadas allí. La existencia de pruebas automatizadas no demuestra por sí sola que se siguió TDD: conservar evidencia real del ciclo Red-Green-Refactor o aplicarlo en los próximos cambios. Cada Pull Request necesita aprobación de un revisor distinto al autor antes de fusionarse. La configuración actual de staging y su enlace se documentan arriba. No registrar ceremonias ni aprobaciones que no hayan ocurrido.
