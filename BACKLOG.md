# Product Backlog - AgroValle Connect

Las estimaciones fueron acordadas con Planning Poker usando Fibonacci. Las historias se revisaron contra INVEST: son independientes en lo posible, negociables, valiosas, estimables, pequenas y verificables mediante el escenario BDD.

| ID | Historia de usuario | MoSCoW | SP |
|---|---|---:|---:|
| HU-01 | Como agricultor, quiero registrarme para ofrecer mis productos. | M | 5 |
| HU-02 | Como agricultor, quiero publicar una cosecha para que sea visible. | M | 5 |
| HU-03 | Como usuario, quiero ver precios regionales para negociar mejor. | S | 5 |
| HU-04 | Como comprador, quiero filtrar ofertas por municipio y categoria para encontrar productos locales. | M | 3 |
| HU-05 | Como comprador, quiero contactar al agricultor para acordar una compra. | M | 5 |
| HU-06 | Como agricultor, quiero registrar mi finca para asociar sus ofertas a una ubicacion. | M | 3 |
| HU-07 | Como comprador, quiero crear una cuenta para gestionar mis solicitudes. | M | 5 |
| HU-08 | Como comprador, quiero reservar una cantidad disponible para evitar sobreventa. | M | 8 |
| HU-09 | Como agricultor, quiero actualizar el estado del lote para informar su disponibilidad. | S | 3 |
| HU-10 | Como comprador, quiero consultar el detalle de una oferta antes de contactar al productor. | M | 3 |
| HU-11 | Como agricultor, quiero recibir una notificacion cuando alguien me contacte. | S | 5 |
| HU-12 | Como agricultor, quiero programar el despacho para coordinar la entrega. | S | 8 |
| HU-13 | Como comprador, quiero consultar la trazabilidad de mi pedido para conocer su avance. | S | 8 |
| HU-14 | Como usuario, quiero guardar ofertas favoritas para consultarlas despues. | C | 3 |
| HU-15 | Como administrador, quiero generar un reporte de actividad para revisar el uso de la plataforma. | W | 13 |

## Criterios de aceptacion BDD

### HU-01 Registro de agricultores
**Given** que un visitante envia `POST /api/v1/auth/register` con nombre, ubicacion_valle y cedula validos, **When** el servicio valida que la cedula no existe, **Then** responde `201 Created` y persiste el agricultor en PostgreSQL.

### HU-02 Publicacion de cosechas
**Given** un agricultor autenticado mediante JWT, **When** envia `POST /api/v1/productos` con tipo, cantidad y fecha_cosecha no anterior a hoy, **Then** responde `201 Created` con un identificador unico y persiste la oferta.

### HU-03 Precios regionales
**Given** 50 transacciones de Cafe en las ultimas 24 horas, **When** un usuario solicita `GET /api/v1/precios/promedio?producto=Cafe`, **Then** recibe `200 OK` con la media aritmetica exacta en COP.

### HU-04 Filtro por categoria y municipio
**Given** ofertas activas de Frutas en Dagua, **When** se consulta `GET /api/v1/productos?municipio=Dagua&categoria=Frutas`, **Then** responde `200 OK` con solo las ofertas coincidentes.

### HU-05 Contacto directo
**Given** un comprador autenticado y una oferta activa, **When** envia `POST /api/v1/contacto/mensaje` con `id_producto` y mensaje, **Then** responde `200 OK` y persiste la interaccion.

### HU-06 Registro de finca
**Given** un agricultor autenticado, **When** envia `POST /api/v1/fincas` con nombre, municipio y direccion validos, **Then** responde `201 Created` y asocia la finca con el agricultor.

### HU-07 Registro de comprador
**Given** un visitante con correo no registrado, **When** envia `POST /api/v1/auth/register-comprador` con datos validos, **Then** responde `201 Created` y guarda su cuenta con rol COMPRADOR.

### HU-08 Reserva de inventario
**Given** una oferta activa con 100 kg disponibles y un comprador autenticado, **When** envia `POST /api/v1/reservas` por 20 kg, **Then** responde `201 Created` y deja 80 kg disponibles en una transaccion atomica.

### HU-09 Estado del lote
**Given** un agricultor propietario de un lote, **When** envia `PATCH /api/v1/productos/{id}/estado` con `AGOTADO`, **Then** responde `200 OK` y el lote deja de aparecer en busquedas activas.

### HU-10 Detalle de oferta
**Given** una oferta publica existente, **When** un usuario consulta `GET /api/v1/productos/{id}`, **Then** responde `200 OK` con producto, agricultor, finca, precio y disponibilidad sin exponer datos privados.

### HU-11 Notificacion de contacto
**Given** un contacto creado correctamente, **When** se confirma la persistencia del mensaje, **Then** se registra una notificacion para el agricultor y el endpoint retorna `200 OK`.

### HU-12 Programacion de despacho
**Given** una reserva confirmada y un agricultor autenticado, **When** envia `POST /api/v1/despachos` con fecha, franja horaria y ruta, **Then** responde `201 Created` y relaciona el despacho con la reserva.

### HU-13 Trazabilidad de pedido
**Given** un comprador propietario de una reserva con despacho, **When** consulta `GET /api/v1/pedidos/{id}/trazabilidad`, **Then** recibe `200 OK` con los eventos ordenados por fecha.

### HU-14 Favoritos
**Given** un comprador autenticado y una oferta activa, **When** envia `POST /api/v1/favoritos/{productoId}`, **Then** responde `201 Created` y la oferta aparece en `GET /api/v1/favoritos`.

### HU-15 Reporte administrativo
**Given** un administrador autenticado, **When** consulta `GET /api/v1/reportes/actividad?desde=2026-09-01&hasta=2026-09-30`, **Then** recibe `200 OK` con totales de usuarios, ofertas y contactos; esta historia queda fuera del alcance del primer incremento.

