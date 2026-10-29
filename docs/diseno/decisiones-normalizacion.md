# Decisiones de normalización del modelo de datos

**Estado:** decisiones parciales confirmadas en el [issue #46](https://github.com/santixit/agrovalle-connect/issues/46); el resto de las decisiones de dominio sigue pendiente. El issue registra mantener el esquema actual y considerar los catálogos normalizados como mejora futura. Esto no aprueba otras modificaciones al modelo ni atribuye confirmación a quienes no comentaron.

## Fuentes revisadas

- Migraciones Flyway V1–V6 en `src/main/resources/db/migration/`.
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

## Revisión de dependencias funcionales para 3FN

La regla usada para revisar 3FN es: para cada dependencia funcional no trivial `X → A`, `X` debe ser superclave o `A` debe ser atributo primo. Las claves indicadas por PK/UK en DDL dan dependencias claras; las reglas de negocio que no están impuestas por restricciones se identifican como hipótesis y no como defectos confirmados.

| Grupo / relación | Dependencia que sí respalda el DDL | Lectura 3FN y límite |
|---|---|---|
| `usuarios` | `id → correo, password_hash, rol, activo, creado_en`; `correo → id, password_hash, rol, activo, creado_en` por `UNIQUE(correo)`. | Cumple estructuralmente 3FN si correo identifica una cuenta y los demás atributos describen esa cuenta. |
| `agricultores`, `compradores` | `id → atributos del perfil`; `usuario_id → atributos del perfil` donde `usuario_id` es único. En agricultores la FK puede ser nula; en compradores es obligatoria. | Separar perfiles de cuenta evita repetir los atributos de usuario. La coexistencia de perfiles para una cuenta y la nulabilidad del vínculo de agricultor son reglas pendientes, no violaciones 3FN demostradas. |
| `fincas` | `id → agricultor_id, nombre, municipio, dirección, activa, creada_en`; ` (agricultor_id, nombre) → resto` por clave única. | Cumple estructuralmente 3FN según las claves declaradas, suponiendo que los datos de finca dependan de su identificador/clave de negocio. |
| `productos` | `id → agricultor_id, finca_id, nombre, categoria, municipio, activo, cantidad_kg, precio_por_kg, fecha_cosecha, estado, creado_en`. | La tabla tiene una dependencia de identificador clara. **A validar:** si `finca_id → agricultor_id` por regla del dominio, guardar ambos en producto introduce una dependencia entre atributos no clave; hoy las FK independientes no garantizan que el agricultor sea dueño de esa finca. Resolver con el equipo si se acepta copia por historia/autoría o se impone consistencia mediante diseño relacional. |
| `pedidos`, `detalle_pedido` | `pedidos.id → comprador_id, estado, tiempos`; `detalle_pedido.id → pedido_id, producto_id, cantidad_kg, precio_por_kg`. | La cabecera y sus líneas están separadas; el precio por kg es una instantánea transaccional acordada al reservar, no debe reemplazarse por el precio vigente del producto. Confirmar si en el futuro se permiten varias líneas del mismo producto por pedido y cuál sería la clave de negocio. |
| `despachos` | `id → pedido_id, estado, fechas, destino, peso, costo_flete`; `pedido_id → resto` porque tiene `UNIQUE`. | Cumple estructuralmente 3FN respecto a las claves explícitas. `peso_total_kg` y `costo_flete` guardan la cotización/programación asociada al despacho; aunque el costo se calcula con destino y peso, conservar el resultado es una instantánea histórica deliberada si esa es la regla. Confirmar el significado del peso agregado y el momento de fijación del flete. |
| `contactos` | `id → comprador_id, agricultor_id, producto_id, mensaje, estado, creado_en`. | **A validar:** si `producto_id → agricultor_id` (el productor dueño del producto), guardar también `agricultor_id` repite esa relación. Puede ser instantánea del destinatario al momento del contacto; de lo contrario requiere consistencia que hoy las FK separadas no fuerzan. |
| Relaciones de hechos y eventos | `favoritos` tiene `UNIQUE(comprador_id, producto_id)`; eventos de trazabilidad, notificaciones y transacciones de precio tienen identificador y fecha propios. | La clave única evita duplicar el mismo favorito. En tablas de eventos, cantidades, precio/mensaje/estado describen el hecho ocurrido; deben preservarse como historial, no recalcularse a partir del estado actual. |
| Municipio y categoría | No hay dependencias impuestas entre los textos; `categoria` no tiene catálogo. | La repetición textual **no prueba por sí sola** una violación 3FN. Si el equipo define una entidad municipio/categoría con atributos propios, se deberá revisar si esos atributos dependen transitivamente del identificador guardado y entonces decidir catálogo/migración. La decisión registrada en issue #46 es posponer esos catálogos. |

### Resultado de la revisión

Las relaciones con PK/UK explícitas y atributos que describen su fila tienen una estructura compatible con 3FN bajo las dependencias que actualmente impone el DDL. No hay base suficiente para certificar 3FN para todo el dominio: en particular, propiedad finca–agricultor, autoría de producto y contacto, significado de los textos geográficos/de categoría, y conservación histórica del flete deben ser acordados o confirmados por el equipo. Esta revisión documenta riesgos condicionales; no cambia migraciones ni afirma que ya exista una anomalía en datos.

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
