# Reconciliación del DER propuesto con el esquema implementado

## Alcance y fuentes de verdad

Los DER entregados son propuestas del dominio. El esquema ejecutable está definido por Flyway V1–V7 y se resume en [schema.sql](schema.sql). Los modelos conceptuales y físicos vigentes están en [modelo-entidad-relacion.mmd](modelo-entidad-relacion.mmd) y [modelo-datos-der.mmd](modelo-datos-der.mmd). Esta comparación es técnica: no reemplaza la aprobación del Product Owner ni declara que las diferencias de negocio estén aceptadas.

La revisión se hizo contra las migraciones V1–V7, los modelos JPA en `src/main/java/co/edu/uniajc/agrovalle/domain/` y la documentación de [normalización](decisiones-normalizacion.md). V7 ya está integrada en `main`; el CI #103 pasó pruebas y controles de calidad para el commit `a7e2097`, pero ese resultado no es una aprobación funcional del modelo.

## Correspondencia entre propuesta e implementación

| Elemento de la propuesta | Esquema implementado | Resultado de reconciliación |
|---|---|---|
| Usuario y perfiles Agricultor/Comprador | `usuarios`, `agricultores`, `compradores` | Los perfiles se relacionan con el usuario; el rol y estado se guardan en `usuarios`. La relación se refleja en las entidades JPA `Usuario`, `Agricultor` y `Comprador`. |
| Municipio | Columnas de texto en `agricultores` y `fincas`; `municipio_sin_finca` en `producto_ubicacion` | No existe catálogo normalizado `municipios`. Para un producto ubicado en parcela, el municipio se deriva de la finca; para un producto independiente se conserva en `producto_ubicacion`. |
| Finca y parcela | `fincas`, `parcelas` | Una finca puede contener parcelas. Cada parcela tiene código único dentro de su finca y agricultor responsable actual. V7 crea una parcela `GENERAL` para fincas sin productos antiguos y crea parcelas de migración para las ubicaciones preexistentes. |
| Producto y ubicación | `productos`, `producto_ubicacion` | V7 separa la ubicación/titularidad del producto. `producto_id` es único en `producto_ubicacion`, por lo que hay como máximo una ubicación por producto. Los productos asociados a finca usan parcela; los independientes guardan municipio y titular opcional. |
| Categoría | Campo de texto `productos.categoria` | No hay tabla catálogo `categorias` ni FK a un catálogo. La normalización de este valor queda como mejora futura registrada. |
| Oferta | `productos` | No existe tabla independiente `ofertas`. El producto representa la oferta publicada y conserva estado, disponibilidad y precio. El equipo debe confirmar que esta decisión cubre el lenguaje y las reglas del negocio. |
| Reserva y pedido | `pedidos`, `detalle_pedido` | La reserva se representa como pedido y sus líneas, no como una entidad `reservas` separada. Las restricciones de estado, cantidad y precio están en el DDL de referencia. |
| Despacho y trazabilidad | `despachos`, `eventos_trazabilidad` | El despacho se asocia 1:1 con un pedido; los eventos registran la evolución logística del pedido. No son un historial de estado de la oferta. |
| Transferencia e historial de titularidad | `transferencias_producto` y `parcelas.agricultor_responsable_actual_id` | V7 registra producto, agricultor anterior, agricultor nuevo y fecha. La restricción impide que anterior y nuevo sean la misma persona. No equivale a una entidad genérica `HistorialEstadoOferta`. |
| Datos de contacto y documento | `agricultores`, `compradores`, `contactos` | V6 añade tipo de documento/teléfono a agricultores y tipo de documento/cédula a compradores. `contactos.agricultor_id` conserva el destinatario del contacto; esa referencia es una instantánea histórica. |
| Precio por región | `transacciones_precio` y consulta de precio | La consulta existente calcula un promedio de transacciones recientes por categoría; no hay tabla `precio_region` ni municipio persistido en `transacciones_precio`. |
| Favoritos, contactos y notificaciones | `favoritos`, `contactos`, `notificaciones` | Están implementados con FK y restricciones propias, detalladas en `schema.sql` y las migraciones. |

## Cambio de V7 que debe verse en los diagramas

Antes de V7, `productos` guardaba directamente `finca_id`, `agricultor_id` y `municipio`. V7 migra esos datos a `parcelas` y `producto_ubicacion`, y después elimina esas tres columnas de `productos`. El modelo JPA correspondiente incluye `Parcela`, `ProductoUbicacion` y `TransferenciaProducto`; `Producto` mantiene relación con `ProductoUbicacion`.

La dependencia de ubicación de un producto de finca queda expresada como `producto → ubicación → parcela → finca → municipio`. El municipio deja de duplicarse en cada producto de finca. La regla y el proceso de migración están descritos en [decisiones-normalizacion.md](decisiones-normalizacion.md). El DDL consolidado es un snapshot legible; las migraciones Flyway siguen siendo la fuente ejecutable.

## Diferencias que requieren decisión del equipo/PO

| Pregunta de dominio | Evidencia disponible | Decisión que falta |
|---|---|---|
| ¿Producto y oferta son el mismo concepto para este sistema? | `productos` representa el artículo publicado y tiene estado/precio/cantidad. No hay tabla `ofertas`. | PO/equipo confirma si este modelo satisface el vocabulario y ciclo de vida de la oferta. |
| ¿Se debe guardar historial de cada cambio de estado de oferta? | `transferencias_producto` conserva cambios de titularidad; no guarda todas las transiciones de estado. | Confirmar si se requiere una tabla de auditoría de estados. |
| ¿Municipio y categoría requieren catálogos? | Ambos valores siguen siendo texto en sus ubicaciones descritas. La decisión de aplazar catálogos está en el issue #46. | Confirmar si esa decisión sigue vigente para el alcance evaluado. |
| ¿Los dos DER propuestos deben sustituirse o conservarse como propuesta? | Los documentos se titulan y describen como propuestas; los modelos canónicos y V1–V7 reflejan el esquema actual. | PO/equipo determina cuál modelo se acepta como diseño de dominio final. |

No se debe cambiar el esquema solo para hacer coincidir un dibujo. Si se aprueban cambios, primero se actualizan el DER canónico y la decisión de dominio; después se diseña una nueva migración Flyway y sus pruebas. Nunca se reescribe una migración ya aplicada.

## Validación pendiente para cerrar Sesión 6

- **Mockups:** el issue #46 respalda un visto bueno informal y asíncrono de algunas personas, pero no identifica la versión aceptada por el Product Owner ni demuestra aprobación de cada pantalla por todo el equipo.
- **DER:** esta reconciliación expone las diferencias técnicas; la aceptación de las decisiones de dominio sigue pendiente del equipo/PO.
- **Discovery antes de código:** no se encontró evidencia contemporánea que compruebe la secuencia temporal. La documentación retrospectiva no puede demostrarla ni debe inferirla de fechas de commits.

Registrar las confirmaciones en el issue o PR correspondiente con responsable, artefacto/versión revisada y fecha real. Hasta entonces, la revisión de diseño se considera documentada, pero no completamente aprobada.
