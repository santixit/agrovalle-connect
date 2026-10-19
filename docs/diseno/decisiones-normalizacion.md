# Decisiones de normalización del modelo de datos

**Estado:** decisiones parciales confirmadas en el [issue #46](https://github.com/santixit/agrovalle-connect/issues/46); el resto de las decisiones de dominio sigue pendiente. El issue registra mantener el esquema actual y considerar los catálogos normalizados como mejora futura. Esto no aprueba otras modificaciones al modelo ni atribuye confirmación a quienes no comentaron.

## Fuentes revisadas

- Migraciones Flyway V1–V4 en `src/main/resources/db/migration/`.
- Snapshot de referencia [`schema.sql`](schema.sql).
- Modelos [`conceptual`](modelo-entidad-relacion.mmd) y [`físico`](modelo-datos-der.mmd).
- Entidades JPA bajo `src/main/java/co/edu/uniajc/agrovalle/domain/` y consulta en `PrecioRegionalService`.
- DER de propuesta y diferencias descritas en [`reconciliacion-der.md`](reconciliacion-der.md).

Flyway es la fuente ejecutable del esquema. Los DER propuestos no reemplazan migraciones ni se consideran aprobados por aparecer en el repositorio.

## Observaciones del esquema actual

| Área | Hecho verificable en el repositorio | Decisión / validación |
|---|---|---|
| Perfiles | `usuarios` se relaciona con `agricultores` y `compradores`; ambos perfiles tienen `usuario_id` único, y el vínculo de agricultor es nullable en la migración. | Confirmar si ambos perfiles pueden coexistir para una cuenta y si el vínculo nullable de agricultor sigue siendo necesario. |
| Fincas y municipios | `fincas` pertenece a `agricultores`; municipio se almacena como texto. `agricultores` y `productos` también tienen municipio de texto. No hay catálogo `municipios` ni FK geográfica. | Issue #46: mantener el esquema actual y dejar los catálogos normalizados como mejora futura. La decisión específica sobre municipio duplicado entre agricultor, finca y producto, y el nivel territorial requerido, queda pendiente. |
| Categorías | `productos.categoria` es texto; no hay tabla `categorias` ni FK de categoría. | Issue #46: mantener el esquema actual y dejar los catálogos normalizados como mejora futura. La gestión futura de valores de categoría queda pendiente. |
| Producto y oferta | `productos` almacena nombre, categoría, municipio, finca/agricultor, cantidad, precio, fecha y estado. No hay tabla `ofertas` separada. | Confirmar si producto y oferta son el mismo concepto del dominio o si una finca/producto puede publicar varias ofertas independientes. |
| Pedidos y reservas | `pedidos` pertenece a comprador y `detalle_pedido` contiene producto, cantidad y precio unitario capturado. El pedido tiene estado y tiempos de creación/actualización. | Confirmar si el pedido representa la reserva en todos sus estados o si se necesita una entidad de reserva distinta. Preservar el precio capturado si debe mantenerse el precio acordado aunque luego cambie el producto. |
| Historial de estados | `eventos_trazabilidad` se asocia a pedidos; no hay historial de transiciones específico para `productos`/ofertas. | Confirmar qué cambios deben auditarse, por cuánto tiempo y para quién son visibles. |
| Precio regional | `transacciones_precio` guarda producto, cantidad, precio y fecha; no guarda municipio. `PrecioRegionalService` promedia hasta 50 transacciones recientes de las últimas 24 horas por categoría. No hay tabla agregada de precio regional. | Confirmar si ese promedio cumple el significado de “regional” sin ubicación en las transacciones, y si se necesita persistir agregados o mantener el cálculo bajo demanda. |
| Relaciones M:N | `favoritos` implementa una relación comprador-producto con unicidad por par; pedidos con detalles separan cabecera y líneas. | Confirmar si las restricciones y borrados en cascada actuales corresponden a las reglas de negocio. |

## Lectura provisional de formas normales

Esta es una revisión estructural basada en DDL; no es una certificación formal de 1FN/2FN/3FN ni una decisión del negocio.

- **1FN, pendiente de validación:** las columnas visibles almacenan valores escalares y las relaciones repetibles están separadas en filas/tablas (por ejemplo, `detalle_pedido`, `favoritos` y `eventos_trazabilidad`). El repositorio no aporta evidencia sobre la calidad de los valores ya cargados ni sobre convenciones de formato.
- **2FN, pendiente de validación:** las tablas usan claves primarias simples (`id`); `favoritos` además aplica unicidad al par comprador-producto. Debe confirmarse que cada atributo no clave depende de la fila identificada y que no se requieren claves naturales/compuestas.
- **3FN, pendiente de validación:** separar usuarios, perfiles, fincas, pedidos y líneas evita algunos grupos repetidos. Aun así, municipio y categoría como texto, y municipio tanto en finca como en producto, pueden producir variaciones y anomalías de actualización si representan el mismo dato. No se puede declarar 3FN satisfactoria sin acordar las dependencias del dominio.

## Alternativas para que el equipo decida

| Tema | Alternativa A | Alternativa B | Estado |
|---|---|---|---|
| Municipio y categoría | Mantener el esquema y los textos actuales por ahora. | Crear catálogos normalizados con claves foráneas y migración de valores existentes como mejora futura. | Confirmado en issue #46: no cambiar el esquema actual ahora; catálogos como mejora futura. |
| Municipio en producto/finca | Mantener copia en producto como ubicación de publicación histórica. | Derivar del vínculo con finca y no repetirlo en producto, si la regla permite una única ubicación. | Pendiente de validación colectiva. |
| Producto/oferta | Mantener publicación, cantidad y estado en `productos`. | Separar `ofertas` si hay publicaciones múltiples/temporales por producto o atributos propios. | Pendiente de validación colectiva. |
| Historial | Conservar solo eventos de pedido. | Agregar auditoría de cambios de estado del producto/oferta si hay necesidad de trazabilidad. | Pendiente de validación colectiva. |
| Precio regional | Calcular consulta desde transacciones recientes, como hoy. | Persistir agregados con dimensiones y período acordados, si se requiere histórico/región. | Pendiente de validación colectiva. |

## Cómo registrar la decisión

Las confirmaciones explícitas visibles en el issue #46 son de DannyGomez02, michelleguerrero19 y Star-Light-co; el propietario del repositorio publicó la síntesis de decisiones. Las confirmaciones de integrantes que no comentaron siguen pendientes. Hasta que exista una decisión posterior verificable, las migraciones actuales siguen siendo la fuente ejecutable de verdad; no se preparan migraciones basadas solo en una propuesta visual.
