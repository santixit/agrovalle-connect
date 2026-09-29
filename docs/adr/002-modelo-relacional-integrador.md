# ADR 002 — Modelo relacional base del integrador

## Estado

Aceptada; los flujos REST consumen actualmente las entidades descritas.

## Contexto

AgroValle Connect requiere soportar perfiles de agricultores y compradores, fincas y ofertas de cosechas, y más adelante reservas, despachos, trazabilidad, contactos, favoritos, notificaciones y datos de transacciones para consultar precios regionales. Las primeras historias ya usan las tablas `agricultores` y `productos`; los cambios deben conservar esos endpoints y datos existentes.

## Decisión

- Mantener PostgreSQL como base de producción y Flyway como único mecanismo de evolución del esquema.
- Conservar V1 y V2 sin cambios después de aplicadas; V3 agrega la fecha de creación de oferta requerida para el reporte administrativo.
- Centralizar credenciales y roles (`AGRICULTOR`, `COMPRADOR`, `ADMIN`) en `usuarios`. El rol `ADMIN` no requiere una tabla de perfil aparte mientras no tenga atributos propios.
- Asociar cuentas de agricultor de forma opcional durante la transición para conservar los perfiles creados antes de la autenticación. Los perfiles de comprador se vinculan a una cuenta.
- Relacionar productos con una finca cuando esta exista. Las columnas de cantidad, precio y fecha de cosecha permiten construir una oferta sin romper filas históricas de catálogo.
- Representar una reserva como un pedido con uno o más detalles que guardan una instantánea de cantidad y precio; despachos y eventos de trazabilidad se vinculan al pedido.
- Guardar estados como cadenas controladas por enumeraciones Java y restricciones SQL, y transacciones completadas como fuente de cálculos de precios.
- No guardar contraseñas en claro: `password_hash` almacena hashes BCrypt; el acceso usa JWT con los roles persistidos.

## Consecuencias

- El modelo cubre las entidades directamente asociadas a las historias del producto y evita crear una tabla de administrador redundante.
- Las asociaciones opcionales preservan compatibilidad mientras se migra desde perfiles sin cuenta autenticada.
- Las operaciones de inventario y transición de pedidos se implementan en transacciones; la reserva bloquea la fila de oferta para prevenir sobreventa concurrente.
- Solo las entregas completadas crean filas de `transacciones_precio`, que alimentan el promedio de las 50 operaciones más recientes.
- Cada cambio futuro del esquema requiere una nueva migración Flyway, nunca editar migraciones ya aplicadas.
