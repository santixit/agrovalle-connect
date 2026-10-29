# Reconciliacion del DER propuesto con el esquema implementado

## Alcance y fuente de verdad

Los DER entregados son propuestas de dominio. El esquema operativo se define en las migraciones Flyway V1-V6 y se resume en [schema.sql](schema.sql), junto con los modelos canónicos [conceptual](modelo-entidad-relacion.mmd) y [físico](modelo-datos-der.mmd). Esta comparación no modifica la base ni declara aprobadas las diferencias del negocio.

| Elemento de la propuesta | Estado implementado | Correspondencia / diferencia |
|---|---|---|
| Usuario y perfiles Agricultor/Comprador | `usuarios`, `agricultores`, `compradores` | Los perfiles especializados se relacionan con el usuario; el rol y estado viven en `usuarios`. |
| Municipio | Campo de texto en `agricultores` y `fincas` | No existe catalogo normalizado `municipios`; nombres/departamentos no tienen FK a una tabla maestra. |
| Finca | `fincas` | Conserva agricultor, nombre, municipio como texto, direccion, estado y fecha de creacion. El esquema actual no incluye area ni coordenadas. |
| Categoria | Campo de texto de `productos` | No existe tabla catalogo `categorias` ni FK `categoriaId`. |
| Producto y Oferta | `productos` + `pedidos` / `detalle_pedido` | `productos` describe el articulo; la disponibilidad y venta se materializan en pedidos y detalles. No hay entidad `ofertas` separada. |
| Reserva | `pedidos` + `detalle_pedido` | Una reserva no tiene tabla propia: el pedido y sus lineas representan la solicitud y cantidades. |
| Despacho y trazabilidad | `despachos`, `eventos_trazabilidad` | El despacho conserva destino, peso total y costo de flete (V5), y los eventos se asocian al pedido; no equivalen a un historial de estado de oferta. |
| Datos de contacto y documento | Agricultores y compradores | V6 añade tipo de documento y teléfono a agricultores y tipo de documento y cédula a compradores. |
| HistorialEstadoOferta | Sin tabla equivalente | No se persiste una bitacora especifica de cambios de estado de oferta. |
| PrecioRegion | `transacciones_precio` y consulta de precio regional | La consulta promedia hasta 50 transacciones recientes de las ultimas 24 horas por categoria; no hay tabla `precio_region` ni municipio en las transacciones del esquema actual. |
| Favorito, contacto y notificacion | `favoritos`, `contactos`, `notificaciones` | Entidades presentes con sus propias FK; ver migraciones para restricciones exactas. |

## Decisiones pendientes de negocio

- **Decisión parcial registrada en el [issue #46](https://github.com/santixit/agrovalle-connect/issues/46):** conservar el esquema actual y dejar los catálogos normalizados como mejora futura. Sigue pendiente definir si se derivará municipio de una sola fuente y las reglas específicas de integridad de esos datos.
- **Pendiente de validacion del equipo:** confirmar si el dominio requiere entidad persistente `oferta` separada de producto y pedido, o si el modelo actual cubre las reglas acordadas.
- **Pendiente de validacion del equipo:** confirmar si se debe auditar cada transicion de estado de la oferta y conservar historicos de precio regional.
- **Pendiente de validacion del equipo:** aclarar si debe existir una sola fuente para municipio, ya que el texto aparece tanto en agricultor/finca como en producto.
- Si se acuerdan cambios, actualizar primero el DER canonico y luego preparar migraciones y pruebas; no editar el esquema solo para hacer coincidir un dibujo.

El visto bueno informal de los mockups no equivale a validacion del modelo de datos ni de estas decisiones de normalizacion.
