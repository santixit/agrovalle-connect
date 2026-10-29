# Evidencia SOLID — Sesion 7 y Sprint 2

## Cambio verificable

El commit `89edf4f` (`feat: apply kanban solid and test strategy`), ya integrado en `main`, separo responsabilidades que antes estaban concentradas en `ProductoService`:

- `CatalogoProductoService` concentra consultas de catalogo.
- `GestionProductoService` concentra operaciones de escritura y gestion transaccional.
- `ProductoController` delega lectura y escritura en los servicios correspondientes.
- `CatalogoProductoServiceTest` y `GestionProductoServiceTest` prueban los comportamientos aislados de ambos componentes.

La separacion aplica principalmente **SRP**. El commit y los archivos son evidencia auditable; las pruebas agregadas son regresion del comportamiento existente y no se presentan como TDD retrospectivo.

## Mapeo de los cinco principios solicitado en el segundo corte

| Principio | Ejemplo observable | Alcance de la evidencia |
|---|---|---|
| SRP | `CatalogoProductoService` atiende consultas; `GestionProductoService` publica y cambia estado; `ProductoController` delega en ambos. | Separacion concreta que reduce responsabilidades mezcladas en el servicio anterior. |
| OCP | `CalculadorFleteService` recibe un mapa de `CalculadorFleteStrategy`; `FleteBugaStrategy`, `FleteDaguaStrategy` y `FleteTuluaStrategy` agregan tarifas municipales. | Para agregar un municipio soportado se incorpora otra estrategia; no hace falta cambiar el calculador central. Las tarifas son demostrativas. |
| LSP | Las tres estrategias implementan el mismo contrato `getMunicipio()` / `calcular(pesoKg)` y se entregan al mismo calculador. | `CalculadorFleteServiceTest` ejercita las tres implementaciones. Esto demuestra sustitucion dentro de ese contrato limitado; no es una prueba formal de todas las jerarquias del sistema. |
| ISP | `CalculadorFleteStrategy` es un contrato pequeno, enfocado en identificar municipio y calcular flete; no obliga a implementar metodos ajenos. | Ejemplo acotado de interfaz por rol. No se afirma que toda la API del proyecto siga ISP. |
| DIP | `ReservaService` recibe `CalculadorDescuentoAgricola` por constructor y no instancia `DescuentoMayoristaService`; la implementacion concreta satisface la interfaz. | Evidencia de inversion para la regla de descuento. `ProductoController` aun depende de dos servicios concretos, por lo que DIP no se presenta como aplicado universalmente. |

Los ejemplos OCP/LSP/ISP/DIP corresponden al codigo actualmente integrado en `main`. El test de estrategias valida el calculador con implementaciones concretas; no equivale a una certificacion exhaustiva de LSP.

## Evidencia del flujo

La politica de entrada a revision, limites WIP y criterios de Done esta en [politicas Kanban](../kanban-policies.md). El snapshot de conteos disponible y sus limites se registra en [metricas de la Sesion 7](kanban-metricas-sesion7.md). No hay base documental para atribuir metricas historicas a este refactor.
