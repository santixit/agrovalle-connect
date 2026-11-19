# Refactorizaciones SOLID antes/después — Sesión 7

Este registro documenta dos cambios del código con su motivación, evidencia y límites. No afirma que el sistema completo cumpla SOLID.

## Caso 1 — separar catálogo y gestión de productos

El commit [89edf4f](https://github.com/santixit/agrovalle-connect/commit/89edf4f) separó las consultas de catálogo de las operaciones de escritura que estaban juntas en ProductoService. La separación aplica SRP en este límite concreto.

### Antes del cambio

| Responsabilidad | Métodos en ProductoService | Dependencias |
|---|---|---|
| Consultar catálogo | buscar(municipio, categoria) | ProductoRepository |
| Consultar detalle | consultarDetalle(id) | ProductoRepository |
| Publicar producto | publicar(usuarioId, request) | ProductoRepository, AgricultorRepository, FincaRepository |
| Cambiar estado de oferta propia | actualizarEstado(usuarioId, productoId, request) | ProductoRepository, AgricultorRepository |

La misma clase servía lecturas públicas y reglas de escritura, y combinaba transacciones de solo lectura con transacciones de modificación.

### Después del cambio

| Componente | Responsabilidad | Transacción y dependencias |
|---|---|---|
| [CatalogoProductoService](../../src/main/java/co/edu/uniajc/agrovalle/service/CatalogoProductoService.java) | Filtrar productos, listar los del agricultor y consultar detalle público. | Solo lectura; ProductoRepository. |
| [GestionProductoService](../../src/main/java/co/edu/uniajc/agrovalle/service/GestionProductoService.java) | Publicar productos y cambiar el estado de una oferta del agricultor autenticado. | Escritura transaccional; repositorios de agricultor, finca/parcela y producto. |
| ProductoController | Recibir solicitudes HTTP, validar/extraer datos y delegar. | Recibe servicios por constructor. |

La lectura y escritura tienen puntos de entrada y límites transaccionales distintos. El controller conserva coordinación HTTP y las reglas de caso de uso permanecen en los servicios.

## Caso 2 — extraer la política de descuento de la reserva

Los commits [f7d5097](https://github.com/santixit/agrovalle-connect/commit/f7d5097) y [bd02cb2](https://github.com/santixit/agrovalle-connect/commit/bd02cb2) introdujeron una regla mayorista y desacoplaron su cálculo de los flujos de reserva.

### Antes del cambio

- DescuentoMayoristaService contenía la condición de más de 100 kg y los valores 100 y 0.95 como literales.
- ReservaService no recibía una abstracción común para decidir el precio de reserva individual y carrito.
- Cambiar la política requería conocer la implementación concreta y sus puntos de uso.

### Después del cambio

| Componente | Responsabilidad después del cambio |
|---|---|
| CalculadorDescuentoAgricola | Contrato para calcular el precio unitario usando peso y precio base. |
| DescuentoMayoristaService | Implementa la política vigente; nombra el límite y el porcentaje en constantes. |
| ReservaService | Recibe el contrato por constructor y lo usa en reservar y reservarCarrito; persiste y devuelve el precio calculado. |

La política queda separada del caso de uso de reserva. Esto mejora DIP, porque el flujo depende de una abstracción, y permite agregar otra política sin duplicar la fórmula. Solo existe una implementación de descuento; no se afirma que haya varias estrategias activas.

La prueba de umbral, el caso de 100,01 kg y la integración con reservas están detallados en [TDD descuento mayorista](tdd-descuento-mayorista.md).

## Relación con SOLID y límites

- **SRP:** catálogo y gestión de productos quedan en servicios distintos.
- **DIP:** ReservaService consume CalculadorDescuentoAgricola y no contiene la fórmula del descuento.
- **OCP:** la regla puede ampliarse mediante otra implementación del contrato; no hay una segunda implementación actual.
- **Responsabilidad transaccional:** las consultas del catálogo son de solo lectura; cambios y reservas son transaccionales.
- **Límites:** ProductoController depende de servicios concretos; GestionProductoService mantiene varios repositorios; solo hay una política de descuento. Estos cambios no certifican DIP, OCP o SRP en todo el sistema.

## Pruebas y trazabilidad

El primer cambio y sus pruebas aparecen en 89edf4f; la estrategia de descuento se extrae en f7d5097 y se conecta a reserva en bd02cb2. El [workflow #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070) para el commit a7e2097 terminó en verde con pruebas, Checkstyle y JaCoCo. Para el descuento, el desarrollo TDD sí está documentado; para la separación de ProductoService no se conserva una secuencia Red–Green.

Strategy y Observer están explicados en [patrones.md](../diseno/patrones.md). Esta evidencia antes/después cubre las dos separaciones descritas arriba.
