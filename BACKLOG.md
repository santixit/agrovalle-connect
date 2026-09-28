# Product Backlog - AgroValle Connect

Las estimaciones fueron acordadas con Planning Poker usando Fibonacci. Las historias se revisaron contra INVEST: son independientes en lo posible, negociables, valiosas, estimables, pequenas y verificables mediante el escenario BDD. La prioridad se expresa con MoSCoW, no con codigos numericos como P0 o P1.

| ID | Historia de usuario | Prioridad MoSCoW | SP |
|---|---|---:|---:|
| HU-01 | Como agricultor, quiero registrarme para ofrecer mis productos. | Must have | 5 |
| HU-02 | Como agricultor, quiero publicar una cosecha para que sea visible. | Must have | 5 |
| HU-03 | Como usuario, quiero ver precios regionales para negociar mejor. | Should have | 5 |
| HU-04 | Como comprador, quiero filtrar ofertas por municipio y categoria para encontrar productos locales. | Must have | 3 |
| HU-05 | Como comprador, quiero contactar al agricultor para acordar una compra. | Must have | 5 |
| HU-06 | Como agricultor, quiero registrar mi finca para asociar sus ofertas a una ubicacion. | Must have | 3 |
| HU-07 | Como agricultor, quiero consultar mi perfil para verificar mis datos registrados. | Must have | 2 |
| HU-08 | Como comprador, quiero reservar una cantidad disponible para evitar sobreventa. | Must have | 8 |
| HU-09 | Como agricultor, quiero actualizar el estado del lote para informar su disponibilidad. | Should have | 3 |
| HU-10 | Como comprador, quiero consultar el detalle de una oferta antes de contactar al productor. | Must have | 3 |
| HU-11 | Como agricultor, quiero recibir una notificacion cuando alguien me contacte. | Should have | 5 |
| HU-12 | Como agricultor, quiero programar el despacho para coordinar la entrega. | Should have | 8 |
| HU-13 | Como comprador, quiero consultar la trazabilidad de mi pedido para conocer su avance. | Should have | 8 |
| HU-14 | Como usuario, quiero guardar ofertas favoritas para consultarlas despues. | Could have | 3 |
| HU-15 | Como administrador, quiero generar un reporte de actividad para revisar el uso de la plataforma. | Won't have this increment | 13 |


### Significado de las prioridades MoSCoW

- **Must have:** necesario para cumplir el objetivo del incremento.
- **Should have:** importante, pero el incremento puede funcionar sin ello temporalmente.
- **Could have:** deseable si queda capacidad despues de completar lo prioritario.
- **Won't have this increment:** se acuerda dejarlo fuera de este incremento; puede reconsiderarse despues.

## Criterios de aceptacion BDD

Los escenarios describen comportamiento de negocio. Los contratos REST y codigos HTTP se definen en la planificacion tecnica, no en este backlog de producto.

### HU-01 Registro de agricultores
**Given** una persona que proporciona datos personales validos y una cedula no registrada, **When** solicita registrarse como agricultor, **Then** su perfil queda creado y disponible para ofrecer productos.

### HU-02 Publicacion de cosechas
**Given** un agricultor con una cuenta habilitada, **When** publica una cosecha con tipo, cantidad y fecha valida, **Then** la oferta queda registrada y visible para compradores.

### HU-03 Consulta de precios regionales
**Given** que existen transacciones recientes de cafe en la region, **When** un usuario consulta los precios regionales, **Then** el sistema presenta el promedio en pesos colombianos para apoyar la negociacion.

### HU-04 Filtro por categoria y municipio
**Given** ofertas activas de frutas en Dagua y en otros municipios o categorias, **When** un comprador filtra por municipio y categoria, **Then** solo encuentra las ofertas coincidentes con ambos filtros.

### HU-05 Contacto directo
**Given** un comprador y una oferta activa, **When** el comprador envia un mensaje al agricultor, **Then** la interaccion queda registrada y asociada a la oferta.

### HU-06 Registro de finca
**Given** un agricultor registrado, **When** registra una finca con nombre, municipio y direccion validos, **Then** la finca queda asociada a su perfil.

### HU-07 Consulta del perfil del agricultor
**Given** un agricultor registrado, **When** consulta su perfil usando su identificador, **Then** el sistema muestra los datos asociados a ese agricultor y no los de otra persona.

### HU-08 Reserva de inventario
**Given** una oferta activa con 100 kg disponibles y un comprador, **When** reserva 20 kg, **Then** la reserva queda registrada y la disponibilidad se actualiza a 80 kg sin permitir sobreventa.

### HU-09 Estado del lote
**Given** un agricultor propietario de un lote, **When** actualiza su estado a agotado, **Then** el lote deja de aparecer entre las ofertas disponibles.

### HU-10 Detalle de oferta
**Given** una oferta publica existente, **When** un usuario consulta su detalle, **Then** el sistema presenta producto, agricultor, finca, precio y disponibilidad sin exponer datos privados.

### HU-11 Notificacion de contacto
**Given** que un comprador contacto correctamente a un agricultor, **When** se registra la interaccion, **Then** el agricultor recibe una notificacion del nuevo contacto.

### HU-12 Programacion de despacho
**Given** una reserva confirmada, **When** el agricultor programa fecha, franja horaria y ruta de despacho, **Then** el despacho queda asociado a la reserva.

### HU-13 Trazabilidad de pedido
**Given** un comprador con una reserva que tiene despacho, **When** consulta la trazabilidad de su pedido, **Then** el sistema muestra los eventos ordenados cronologicamente.

### HU-14 Favoritos
**Given** un comprador y una oferta activa, **When** guarda la oferta como favorita, **Then** puede encontrarla en su lista de favoritos.

### HU-15 Reporte administrativo
**Given** un administrador y registros de actividad dentro de un periodo, **When** solicita un reporte para ese periodo, **Then** el sistema resume usuarios, ofertas y contactos; esta historia queda fuera del alcance del primer incremento.
