# Modelo inicial y alcance tecnico

```mermaid
classDiagram
  class Agricultor
  class Finca
  class Producto
  class Comprador
  class Reserva
  class Despacho
  Agricultor "1" --> "0..*" Finca
  Finca "1" --> "0..*" Producto
  Comprador "1" --> "0..*" Reserva
  Producto "1" --> "0..*" Reserva
  Reserva "1" --> "0..1" Despacho
```

El modelo se refinara en cada incremento. El backend separa controlador, servicio, dominio y repositorio; PostgreSQL sera la infraestructura de persistencia cuando se implemente el dominio.

