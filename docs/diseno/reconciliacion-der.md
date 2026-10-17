# Reconciliacion del DER propuesto con el esquema implementado

## Alcance y fuente de verdad

Los DER entregados son propuestas de dominio. El esquema operativo se define en las migraciones Flyway V1-V4 y se resume en [schema.sql](schema.sql), junto con los modelos canonicos [conceptual](modelo-entidad-relacion.mmd) y [fisico](modelo-datos-der.mmd). Esta comparacion no modifica la base ni declara aprobadas las diferencias del negocio.

| Elemento de la propuesta | Estado implementado | Correspondencia / diferencia |
|---|---|---|
| Usuario y perfiles Agricultor/Comprador | `usuarios`, `agricultores`, `compradores` | Los perfiles especializados se relacionan con el usuario; el rol y estado viven en `usuarios`. |
| Municipio | Campo de texto en `agricultores` y `fincas` | No existe catalogo normalizado `municipios`; nombres/departamentos no tienen FK a una tabla maestra. |
| Finca | `fincas` | Conserva agricultor, municipio como texto, nombre, area y coordenadas; revisar reglas de negocio de ubicacion con PO/equipo. |
| Categoria | Campo de texto de `productos` | No existe tabla catalogo `categorias` ni FK `categoriaId`. |
| Producto y Oferta | `productos` + `pedidos` / `detalle_pedido` | `productos` describe el articulo; la disponibilidad y venta se materializan en pedidos y detalles. No hay entidad `ofertas` separada. |
| Reserva | `pedidos` + `detalle_pedido` | Una reserva no tiene tabla propia: el pedido y sus lineas representan la solicitud y cantidades. |
| Despacho y trazabilidad | `despachos`, `eventos_trazabilidad` | Hay despacho y eventos asociados al pedido; no equivalen a un historial de estado de oferta. |
| HistorialEstadoOferta | Sin tabla equivalente | No se persiste una bitacora especifica de cambios de estado de oferta. |
| PrecioRegion | `transacciones_precio` y consulta de precio regional | El precio de referencia se calcula a partir de transacciones; no hay tabla `precio_region` con una fila por producto/municipio/fecha. |
| Favorito, contacto y notificacion | `favoritos`, `contactos`, `notificaciones` | Entidades presentes con sus propias FK; ver migraciones para restricciones exactas. |

## Decisiones pendientes de negocio

- Confirmar si municipio y categoria necesitan catalogos normalizados y restricciones de integridad referencial.
- Confirmar si el dominio requiere entidad persistente `oferta` separada de producto y pedido, o si el modelo actual cubre las reglas acordadas.
- Confirmar si se debe auditar cada transicion de estado de la oferta y conservar historicos de precio regional.
- Si se aprueban cambios, actualizar primero el DER canonico y luego preparar migraciones y pruebas con el equipo; no editar el esquema solo para hacer coincidir un dibujo.

La aprobacion de los mockups reportada por el usuario el 17 de octubre de 2026 no se interpreta como aprobacion automatica de estas decisiones de datos.
