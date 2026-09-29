# ADR 002 — Modelo relacional base del integrador

## Estado

Aceptada para la fase de arquitectura; los casos de uso se implementan de forma incremental.

## Contexto

AgroValle Connect requiere soportar perfiles de agricultores y compradores, fincas y ofertas de cosechas, y más adelante reservas, despachos, trazabilidad, contactos, favoritos, notificaciones y datos de transacciones para consultar precios regionales. Las primeras historias ya usan las tablas `agricultores` y `productos`; los cambios deben conservar esos endpoints y datos existentes.

## Decisión

- Mantener PostgreSQL como base de producción y Flyway como único mecanismo de evolución del esquema.
- Conservar V1 sin cambios y agregar V2 para las tablas y columnas del modelo integrador.
- Centralizar credenciales y roles (`AGRICULTOR`, `COMPRADOR`, `ADMIN`) en `usuarios`. El rol `ADMIN` no requiere una tabla de perfil aparte mientras no tenga atributos propios.
- Asociar cuentas de agricultor de forma opcional durante la transición para conservar los perfiles creados antes de la autenticación. Los perfiles de comprador se vinculan a una cuenta.
- Relacionar productos con una finca cuando esta exista. Las columnas de cantidad, precio y fecha de cosecha permiten construir una oferta sin romper filas históricas de catálogo.
- Representar una reserva como un pedido con uno o más detalles que guardan una instantánea de cantidad y precio; despachos y eventos de trazabilidad se vinculan al pedido.
- Guardar estados como cadenas controladas por enumeraciones Java y restricciones SQL, y transacciones completadas como fuente de cálculos de precios.
- No guardar contraseñas en claro: `password_hash` solo acepta hashes producidos por el componente de seguridad que se incorporará antes de exponer el registro/login.

## Consecuencias

- El modelo cubre las entidades directamente asociadas a las historias del producto y evita crear una tabla de administrador redundante.
- Las asociaciones opcionales preservan compatibilidad mientras se migra desde perfiles sin cuenta autenticada.
- Las nuevas tablas son fundación del modelo; una tabla por sí sola no significa que su caso de uso ya esté expuesto o terminado.
- Las transiciones de estado y las reglas de inventario deberán implementarse transaccionalmente en servicios antes de aceptar reservas.
- Cada cambio futuro del esquema requiere una nueva migración Flyway, nunca editar migraciones ya aplicadas.
