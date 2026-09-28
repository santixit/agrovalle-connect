# Modelo UML evolutivo

Estos diagramas describen el incremento actual (registro y consulta de agricultores, filtro de productos) y la infraestructura prevista para el equipo. El alcance del modelo es evolutivo: pedidos, stock, autenticacion JWT, fincas y frontend aun no estan implementados en este incremento.

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
    +buscarActivos(municipio, categoria) List~Producto~
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

Agregar relaciones de fincas, agricultores y productos; modelar pedidos/stock, estados y notificaciones; y actualizar los diagramas despues de validar esos flujos con el equipo.
