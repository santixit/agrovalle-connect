# Diagramas UML de AgroValle Connect

Este documento consolida diagramas Mermaid alineados con las rutas, clases y servicios que existen en `main`. Las figuras del laboratorio sirven como guía de tipos de diagrama; cuando un ID de HU o endpoint del ejemplo no coincide con el [Product Backlog](../requirements/product-backlog-bdd.md) y el código, aquí se usa la definición vigente del repositorio.

El código Java conserva el paquete `co.edu.uniajc.agrovalle`. La estructura `com.agrovalle.connect` del ejemplo del laboratorio no se copia porque cambiar el paquete completo no es una tarea de organización documental.

## 1. Secuencia: inicio de sesión con JWT

```mermaid
sequenceDiagram
  autonumber
  actor Cliente
  participant C as AutenticacionController
  participant S as AutenticacionService
  participant U as UsuarioRepository
  participant DB as PostgreSQL
  Cliente->>C: POST /api/v1/auth/login (LoginRequest)
  C->>S: iniciarSesion(request)
  S->>U: findByCorreoIgnoreCase(correo)
  U->>DB: SELECT usuario por correo
  DB-->>U: Usuario activo / sin coincidencia
  U-->>S: Optional<Usuario>
  S->>S: PasswordEncoder.matches(contrasena, passwordHash)
  alt Cuenta inexistente, inactiva o contraseña incorrecta
    S-->>C: BadCredentialsException
    C-->>Cliente: HTTP 401
  else Credenciales válidas
    S->>S: crear claims y firmar JWT HS256
    S-->>C: TokenResponse (Bearer, accessToken)
    C-->>Cliente: HTTP 200
  end
```

## 2. Secuencia: publicar cosecha (HU-02)

```mermaid
sequenceDiagram
  autonumber
  actor Agricultor
  participant Sec as Spring Security / JWT
  participant C as ProductoController
  participant S as GestionProductoService
  participant A as AgricultorRepository
  participant P as ParcelaRepository
  participant R as ProductoRepository
  participant DB as PostgreSQL
  Agricultor->>Sec: POST /api/v1/productos + Bearer JWT
  Sec->>Sec: validar firma, vencimiento y subject
  Sec->>C: publicar(jwt, PublicarProductoRequest)
  C->>S: publicar(usuarioId, request)
  S->>A: findByUsuario_Id(usuarioId)
  A->>DB: consultar perfil asociado
  DB-->>A: Agricultor
  A-->>S: Agricultor
  opt solicitud asociada a una finca
    S->>P: buscar parcela asignada a finca y agricultor
    P->>DB: consultar parcelas responsables
    DB-->>P: parcela(s)
    P-->>S: resultado
  end
  S->>S: validar finca/parcela, municipio y datos
  S->>R: save(producto)
  R->>DB: INSERT producto y ubicación
  DB-->>R: Producto persistido con id
  R-->>S: Producto
  S-->>C: ProductoResponse
  C-->>Agricultor: HTTP 201 Created
```

## 3. Secuencia: consulta de precios regionales (HU-03)

```mermaid
sequenceDiagram
  autonumber
  actor Cliente as Usuario público
  participant C as PrecioRegionalController
  participant S as PrecioRegionalService
  participant R as TransaccionPrecioRepository
  participant DB as PostgreSQL
  Cliente->>C: GET /api/v1/precios/regionales?categoria=Frutas
  C->>S: consultar(categoria)
  S->>R: buscarRecientesPorCategoria(categoria, últimas 24 h, máximo 50)
  R->>DB: seleccionar transacciones completadas recientes
  DB-->>R: lista de TransaccionPrecio
  R-->>S: hasta 50 transacciones
  S->>S: calcular promedio y redondear a 2 decimales
  S-->>C: PrecioRegionalResponse (COP, promedio, cantidad)
  C-->>Cliente: HTTP 200 JSON
```

## 4. Comunicación: programación de despacho (HU-12 según el Backlog vigente)

Mermaid no ofrece un tipo nativo de diagrama de comunicación UML; este flujo usa participantes y mensajes numerados para mostrar la colaboración.

```mermaid
flowchart LR
  A["1 Agricultor"] -->|"2 POST /api/v1/despachos"| C[":DespachoController"]
  C -->|"3 programar(usuarioId, request)"| S[":DespachoService"]
  S -->|"4 findById(pedidoId)"| PR[":PedidoRepository"]
  PR -->|"5 SELECT pedido y detalles"| DB[("PostgreSQL")]
  DB -->|"6 Pedido"| PR
  PR -->|"7 validar propietario y estado"| S
  S -->|"8 calcular(municipio, peso)"| F[":CalculadorFleteService / Strategy"]
  F -->|"9 costo de flete"| S
  S -->|"10 enviarADespacho(pedido)"| W[":PedidoWorkflowService"]
  S -->|"11 save(despacho)"| DR[":DespachoRepository"]
  DR -->|"12 INSERT despacho"| DB
  DB -->|"13 Despacho persistido"| DR
  DR -->|"14 DespachoResponse"| S
  S -->|"15 respuesta"| C
  C -->|"16 HTTP 201 Created"| A
```

## 5. Comunicación: notificación observada después de registrar contacto (HU-11)

El laboratorio asocia este ejemplo a HU-13, pero el [Product Backlog](../requirements/product-backlog-bdd.md) define las notificaciones de contacto en HU-11. La trazabilidad de pedidos se mantiene en HU-13.

```mermaid
flowchart LR
  B["1 Comprador autenticado"] -->|"2 POST /api/v1/contactos"| C[":ContactoController"]
  C -->|"3 contactar(usuarioId, request)"| S[":ContactoService"]
  S -->|"4 validar comprador y oferta activa"| R[":CompradorRepository + ProductoRepository"]
  R -->|"5 SELECT comprador y producto"| DB[("PostgreSQL")]
  DB -->|"6 entidades"| R
  R -->|"7 guardar contacto"| S
  S -->|"8 save(contacto)"| CR[":ContactoRepository"]
  CR -->|"9 INSERT contacto"| DB
  S -->|"10 publishEvent(ContactoRegistradoEvent)"| E["ApplicationEventPublisher"]
  S -->|"11 confirmar transacción"| C
  C -->|"12 HTTP 201 ContactoResponse"| B
  E -. "13 después del commit" .-> O[":NotificacionObserver"]
  O -->|"14 buscar usuario agricultor"| UR[":UsuarioRepository"]
  UR -->|"15 SELECT usuario"| DB
  O -->|"16 save(notificación), REQUIRES_NEW"| NR[":NotificacionRepository"]
  NR -->|"17 INSERT notificación"| DB
```

## 6. Componentes: arquitectura de cuatro capas

```mermaid
flowchart TB
  subgraph PRESENTACION["Presentación / MVC y REST"]
    VIEW["Navegador / vistas estáticas"]
    CTRL["Controllers + DTOs"]
  end
  subgraph NEGOCIO["Lógica de negocio"]
    SVC["Services transaccionales"]
    DOMAIN["Entidades y reglas de dominio"]
    STRAT["Strategy: cálculo de flete"]
    OBS["Observer: eventos y notificaciones"]
  end
  subgraph PERSISTENCIA["Acceso a datos"]
    REPO["Spring Data JPA Repositories"]
    FLYWAY["Flyway migrations V1-V7"]
  end
  DB[("PostgreSQL")]
  SEC["Spring Security + JWT (transversal)"]
  VIEW --> CTRL
  SEC -. protege endpoints privados .-> CTRL
  CTRL --> SVC
  SVC --> DOMAIN
  SVC --> STRAT
  SVC --> OBS
  SVC --> REPO
  REPO --> DB
  FLYWAY --> DB
```

## 7. Despliegue: CI y staging actuales

```mermaid
flowchart LR
  DEV["Desarrollador"] -->|"push / pull_request"| GH["GitHub: main y Pull Requests"]
  GH --> CI["Actions: ci.yml / quality\nJava 17, Maven verify, JUnit, Checkstyle, JaCoCo, E2E"]
  CI -->|"quality exitosa y push a main"| CD["Workflow reutilizable: cd.yml"]
  CD --> HOOK["Deploy hook protegido por secreto"]
  HOOK --> RENDER["Render: API Spring Boot staging"]
  RENDER -->|"JDBC por variables de entorno"| NEON[("Neon PostgreSQL staging")]
  CD -->|"verificar health y base"| STAGECHECK["verify"]
  CLIENT["Postman / interfaz web"] -->|"HTTPS REST"| RENDER
```

El workflow `ci.yml` ejecuta la calidad en cada push y Pull Request. Solo un push exitoso a `main` llama al workflow reutilizable `cd.yml`, que solicita el despliegue y después verifica readiness y base de datos; así se conservan las comprobaciones y no se crean dos disparadores de despliegue. La ruta de salud implementada es `/api/v1/health`. La aplicación lee la configuración desde `application.properties` y variables de entorno; no necesita un `application-staging.yml` para este despliegue.

## Fuentes editables y alcance

- Los diagramas PlantUML fuente, DER, esquema y mockups permanecen en [`../diseno/README.md`](../diseno/README.md).
- La versión UML anterior se conserva en [`diagramas-uml-historico.md`](diagramas-uml-historico.md) como referencia, no como fuente vigente.
- El repositorio implementa pedidos, reservas y despachos; los nombres de HU se toman del [Product Backlog](../requirements/product-backlog-bdd.md), no de IDs ilustrativos del laboratorio.
