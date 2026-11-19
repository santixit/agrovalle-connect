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

## Decisiones de modelo para la versión actual

La comparación técnica queda resuelta para continuar el prototipo con las siguientes decisiones de línea base. No son una aprobación colectiva de todos los integrantes ni una certificación de que cada alternativa sea el modelo ideal para producción.

| Tema | Línea base de esta versión | Motivo / evidencia | Consecuencia de cambio |
|---|---|---|---|
| Producto y oferta | Para los contratos actuales, el registro `productos` representa el artículo/oferta publicada. No se crea una tabla separada `ofertas`. | Código, estados, precio y disponibilidad están en `productos`; contrato REST usa `/productos`. | Si el dominio requiere ciclo de vida distinto, abrir una historia y ADR antes de migrar. |
| Historial de estado | No se persiste un evento por cada cambio de estado de oferta. La trazabilidad logística y las transferencias tienen sus propias tablas y no se interpretan como auditoría de estados. | V1–V7 y entidades vigentes. | Añadir tabla y pruebas solo si se incorpora requisito auditable. |
| Municipio y categoría | Municipio y categoría permanecen como texto; no se crean catálogos en este alcance. Para producto asociado a parcela, el municipio se deriva de finca. | Issue #46 acordó conservar el esquema y dejar catálogos como mejora; V7 normaliza la ubicación mediante finca/parcela. | La normalización a catálogos es mejora futura y requiere migración y pruebas. |
| DER canónico | Los diagramas vigentes deben representar Flyway V1–V7. Los archivos con sufijo `propuesta` quedan como antecedentes y no sustituyen el modelo implementado. | Las migraciones son el esquema ejecutable y el código JPA se mapea a ellas. | Al cambiar el modelo, actualizar DER y snapshot en el mismo PR que la migración. |

No se modifica el esquema solo para igualar un dibujo. Las migraciones ya aplicadas son inmutables; cualquier cambio se incorpora en una nueva migración versionada con sus pruebas.

## Cierre documental de Sesión 6

| Punto | Estado factual | Resolución para continuar |
|---|---|---|
| Mockups | El issue #46 conserva un visto bueno informal y asíncrono de algunos integrantes. No identifica aprobación del PO para cada pantalla ni acuerdo de todo el equipo. | Usar el PDF y PNG versionados como referencia visual vigente. Cualquier corrección futura se registra en issue/PR; no se afirma aceptación histórica completa. |
| DER y esquema | La reconciliación técnica de V1–V7 y la decisión sobre catálogos están documentadas. | Flyway V1–V7 es el modelo ejecutable de referencia para el prototipo. |
| Discovery antes de la codificación | No se encontró evidencia contemporánea de la secuencia. | Se registra como no demostrada; no se infiere una fecha ni se afirma que el diseño precedió al código. |

Este cierre deja una fuente de verdad para el desarrollo sin llenar vacíos históricos con supuestos. Véase también [Decisiones de línea base](../requirements/baseline-decisions-2026-11-19.md) y el [índice de diseño](README.md).
