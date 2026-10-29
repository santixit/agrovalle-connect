# Normalización del modelo de datos

**Estado:** implementación local en Flyway V7; no publicada.

## Reglas implementadas

- Una finca contiene parcelas y cada parcela tiene un agricultor responsable actual. Al crear una finca se crea `GENERAL`, que representa la finca completa hasta definir parcelas específicas.
- Todo producto asociado a finca referencia una parcela. Productos independientes guardan agricultor y municipio propios.
- El municipio de un producto con parcela se obtiene desde la finca de esa parcela; no se duplica en producto.
- Al transferir una parcela se cambia su responsable actual y se registra un evento por cada producto ligado a ella. Un producto independiente genera su evento individual.
- `contactos.agricultor_id` conserva el destinatario en el momento del contacto.

## Lectura de 3FN

La dependencia `parcela_id → finca_id → municipio` se resuelve almacenando municipio solo en `fincas`; el producto mantiene una referencia a `parcela` mediante `producto_ubicacion`. El agricultor actual se deriva de `parcelas.agricultor_responsable_actual_id`. Para productos sin finca, `municipio_sin_finca` y `agricultor_sin_finca_id` son atributos de la ubicación independiente. `transferencias_producto` registra hechos históricos y `contactos.agricultor_id` conserva una instantánea del destinatario, no el titular actual.

La migración V7 crea una parcela `MIGRACION-{agricultorId}` por cada pareja de finca y agricultor encontrado en los productos antiguos; cuando el producto carecía de agricultor, usa el agricultor registrante de la finca. Las fincas sin productos reciben `GENERAL`. Los productos sin finca conservan su municipio y titular. La FK de agricultor de los contactos no se modifica.

## Artefactos

- [`propuesta-modelo-3fn.md`](propuesta-modelo-3fn.md): modelo lógico, dependencias y decisión de parcela.
- [`schema.sql`](schema.sql): snapshot de referencia V1–V7.
- [`modelo-datos-der.mmd`](modelo-datos-der.mmd) y [`modelo-entidad-relacion.mmd`](modelo-entidad-relacion.mmd): DER físico y conceptual actualizado.
- [`docs/api-sprint-1.md`](../api-sprint-1.md): contratos de parcelas, publicación y transferencias.

La decisión previa de posponer catálogos de municipios/categorías se mantiene; esta implementación normaliza la ubicación/titularidad conforme a las reglas de negocio confirmadas.
