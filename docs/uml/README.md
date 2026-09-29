# Modelo UML evolutivo

Estos diagramas describen los endpoints del incremento inicial y el modelo relacional base del proyecto. La fase de arquitectura incluye entidades persistentes para cuentas, compradores, fincas, lotes, pedidos, despachos, trazabilidad, contactos, favoritos, notificaciones y transacciones. Tener una entidad no significa que su caso de uso REST ya este implementado; las funcionalidades se incorporaran progresivamente con sus servicios, validaciones y pruebas.

## Casos de uso

```mermaid
flowchart LR
  agricultor[Actor: Agricultor]
  comprador[Actor: Comerciante o restaurante]
  registrar((Registrarse))
  consultar((Consultar perfil))
  filtrar((Filtrar catalogo))
  agricultor --> registrar
  agricultor --> consultar
  comprador --> filtrar
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

## Comunicacion: filtro de catalogo

```mermaid
flowchart LR
  Cliente[Cliente HTTP] -->|1 buscar municipio y categoria| Controller[ProductoController]
  Controller -->|2 delegar filtros| Service[ProductoService]
  Service -->|3 consultar activos| Repository[ProductoRepository]
  Repository -->|4 SELECT filtrado| DB[(PostgreSQL)]
  DB -->|5 filas coincidentes| Repository
  Repository -->|6 entidades| Service
  Service -->|7 lista de DTO| Controller
  Controller -->|8 HTTP 200 JSON| Cliente
```

## Despliegue fisico (objetivo del primer corte)

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
  subgraph Staging pendiente de proveedor
    Stage[Servicio Java 17]
    StageDB[(PostgreSQL administrado)]
    Stage --> StageDB
  end
```

## Patrones y decisiones

- **Repository:** aplicado con Spring Data JPA para aislar el acceso a datos.
- **Singleton:** los servicios y controladores de Spring usan el ciclo de vida singleton por defecto.
- **Factory y Observer:** se reservan para cuando el dominio incorpore tipos diferenciados de pedidos/usuarios y eventos de cambio de estado. No se simulan en este Sprint porque esos flujos no existen todavia.
- La separacion controller-service-repository-domain materializa MVC para la API; la interfaz web y las vistas se desarrollaran en un incremento posterior.

## Pendientes de evolucion

Completar autenticacion/autorizacion y los casos de uso REST de publicacion, precios, contacto, fincas, pedidos y estados. Las entidades de pedidos, trazabilidad, notificaciones, favoritos y transacciones ya tienen migracion y mapeo JPA, pero sus servicios/controladores deben implementarse antes de presentar esos flujos como funcionales. Actualizar los diagramas cuando cambien contratos o reglas del dominio.
