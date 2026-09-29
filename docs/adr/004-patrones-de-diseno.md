# ADR 004 — Patrones de diseño con uso real

## Estado

Aceptada.

## Contexto

La rúbrica exige Repository, Factory, Observer y Singleton, y pide que expliquemos su función dentro del sistema.

## Decisión

- **Repository:** Spring Data JPA encapsula consultas de persistencia en interfaces especializadas.
- **Factory:** `UsuarioFactory` construye cuentas de agricultores, compradores y administradores aplicando hash BCrypt y rol coherente.
- **Observer:** `ApplicationEventPublisher` publica contacto y cambios del pedido después de confirmar la operación; `NotificacionObserver` escucha y persiste una notificación en transacción nueva.
- **Singleton:** `ReglasDisponibilidad` es un componente stateless con alcance singleton de Spring e implementa la validación compartida de inventario.

## Consecuencias

Los patrones aparecen en flujos usados por casos de uso, pueden mostrarse en pruebas y evitan duplicar reglas de creación, inventario y notificación. Si cambia su responsabilidad, los diagramas y esta decisión deben cambiar junto con el código.
