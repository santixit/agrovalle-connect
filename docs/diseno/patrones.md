# Patrones GoF aplicados en AgroValle Connect

## Alcance

La Sesión 9 solicita implementar al menos un patrón de comportamiento y documentar el problema que resuelve junto con su diagrama de clases. El código actual ya aplica **Strategy** al calcular el precio por kilogramo en una reserva y **Observer** para emitir notificaciones después de publicar eventos de contacto o cambios de pedido. Esta documentación describe esos flujos existentes; no registra una actividad de Sprint 2 ni un acuerdo del equipo.

## Strategy: cálculo del descuento mayorista

### Problema de negocio

Las reservas individuales y las reservas desde el carrito deben aplicar la misma regla de precio por kilogramo cuando la cantidad reservada supera los 100 kg. Mantener esa regla dentro de cada flujo duplicaría lógica y acoplaría `ReservaService` a la fórmula del descuento.

### Aplicación

- `CalculadorDescuentoAgricola` define la estrategia de cálculo del precio unitario.
- `DescuentoMayoristaService` implementa el algoritmo actual: aplica 5 % de descuento si la cantidad supera 100 kg; en 100 kg o menos conserva el precio.
- `ReservaService` recibe la interfaz por inyección de constructor y la usa tanto en `reservar` como en `reservarCarrito`.
- `DescuentoMayoristaServiceTest` cubre el límite de 100 kg y el caso que supera ese límite. `AgrovalleConnectApplicationTests` verifica además el precio de una reserva a través del endpoint.

La interfaz permite sustituir o añadir estrategias sin cambiar el contrato HTTP. En el estado actual solo hay una implementación Spring registrada; si se agregan varias, deberá definirse una selección de estrategia explícita antes de conectarlas al contexto para evitar una inyección ambigua.

```mermaid
classDiagram
    class CalculadorDescuentoAgricola {
        <<interface>>
        +calcularPrecioUnitario(kilos, precioUnitario) BigDecimal
    }
    class DescuentoMayoristaService {
        -limiteKilosDescuento
        -descuentoMayoristaCincoPorciento
        +calcularPrecioUnitario(kilos, precioUnitario) BigDecimal
    }
    class ReservaService {
        -calculadorDescuentoAgricola
        +reservar(usuarioId, request) ReservaResponse
        +reservarCarrito(usuarioId, request) CarritoReservaResponse
    }
    CalculadorDescuentoAgricola <|.. DescuentoMayoristaService
    ReservaService --> CalculadorDescuentoAgricola : inyecta y delega
```

## Observer: notificaciones transaccionales

### Problema de negocio

Al crear un contacto sobre una oferta o cambiar el estado de un pedido, la persona interesada debe recibir una notificación persistida. El servicio que ejecuta la operación no necesita conocer cómo se construye ni se guarda esa notificación.

### Aplicación

- `ContactoService` publica `ContactoRegistradoEvent` cuando registra el contacto.
- `PedidoWorkflowService` publica `PedidoEstadoCambiadoEvent` al guardar cada transición válida del pedido.
- `NotificacionObserver` escucha ambos eventos con `@TransactionalEventListener(AFTER_COMMIT)` y guarda la notificación en una transacción nueva.
- Si la operación principal falla y hace rollback, el listener no procesa el evento. El procesamiento es síncrono después del commit; el código no configura `@Async`.
- Las pruebas de `NotificacionObserverTest` verifican por separado cada evento y que no se persista una notificación cuando el usuario destinatario no existe. Los escenarios de integración REST comprueban el registro de contacto y su notificación.

```mermaid
classDiagram
    class ContactoService {
        +contactar(usuarioId, request) ContactoResponse
    }
    class PedidoWorkflowService {
        +confirmar(pedidoId, usuarioAgricultorId)
        +preparar(pedidoId, usuarioAgricultorId)
        +salirARuta(pedido)
        +entregar(pedido)
    }
    class ApplicationEventPublisher {
        <<Spring>>
        +publishEvent(event)
    }
    class ContactoRegistradoEvent {
        +usuarioAgricultorId
        +contactoId
        +nombreProducto
    }
    class PedidoEstadoCambiadoEvent {
        +usuarioCompradorId
        +pedidoId
        +estado
        +detalle
    }
    class NotificacionObserver {
        +alRegistrarContacto(event)
        +alCambiarPedido(event)
    }
    class UsuarioRepository
    class NotificacionRepository
    ContactoService --> ApplicationEventPublisher : publica
    PedidoWorkflowService --> ApplicationEventPublisher : publica
    ApplicationEventPublisher ..> ContactoRegistradoEvent : entrega
    ApplicationEventPublisher ..> PedidoEstadoCambiadoEvent : entrega
    ContactoRegistradoEvent --> NotificacionObserver : observa
    PedidoEstadoCambiadoEvent --> NotificacionObserver : observa
    NotificacionObserver --> UsuarioRepository : busca destinatario
    NotificacionObserver --> NotificacionRepository : persiste después del commit
```

## Verificación de calidad

El proyecto aplica Checkstyle durante `verify` y en el hook local `pre-commit`. JaCoCo exige al menos 60 % de cobertura de instrucciones, ramas y líneas para el bundle. Estas condiciones describen la configuración del repositorio; el resultado vigente debe verificarse en la ejecución local y en CI del commit evaluado.

## Pendiente de alcance

El PDF incluye Strategy de fletes como ejemplo, pero no define tarifas de transporte aprobadas por el negocio. No se agregan tarifas ficticias ni se modifica el contrato de despacho. El equipo tendría que confirmar esas reglas antes de extender el cálculo de costos con ese caso.
