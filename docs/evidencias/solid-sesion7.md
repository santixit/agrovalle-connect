# Refactor SOLID antes/después — Sesión 7

## Cambio evaluado

El commit [`89edf4f`](https://github.com/santixit/agrovalle-connect/commit/89edf4f) separó dos responsabilidades que estaban juntas en `ProductoService`: consultar el catálogo y gestionar operaciones de escritura sobre productos. La separación principal demuestra **SRP (Single Responsibility Principle)** en este límite concreto; no certifica que todo el sistema cumpla SOLID.

## Antes del cambio

`ProductoService` concentraba responsabilidades y dependencias diferentes:

| Responsabilidad | Métodos que estaban en `ProductoService` | Dependencias asociadas |
|---|---|---|
| Consultar catálogo | `buscar(municipio, categoria)` | `ProductoRepository` |
| Consultar detalle | `consultarDetalle(id)` | `ProductoRepository` |
| Publicar producto | `publicar(usuarioId, request)` | `ProductoRepository`, `AgricultorRepository`, `FincaRepository` |
| Cambiar estado de oferta propia | `actualizarEstado(usuarioId, productoId, request)` | `ProductoRepository`, `AgricultorRepository` |

La clase tenía `@Transactional(readOnly = true)` a nivel de clase y sobrescribía esa configuración con `@Transactional` en los métodos de escritura. Un mismo servicio debía atender lecturas públicas y reglas de publicación/propiedad/estado, y mantenía juntas dependencias de consulta y de escritura.

## Después del cambio

| Componente actual | Responsabilidad enfocada | Transacción / dependencia principal |
|---|---|---|
| [`CatalogoProductoService`](../../src/main/java/co/edu/uniajc/agrovalle/service/CatalogoProductoService.java) | Filtrar productos, listar los del agricultor y consultar el detalle público. | `@Transactional(readOnly = true)`; depende de `ProductoRepository`. |
| [`GestionProductoService`](../../src/main/java/co/edu/uniajc/agrovalle/service/GestionProductoService.java) | Publicar productos y cambiar el estado de una oferta del agricultor autenticado. | `@Transactional`; depende de los repositorios que necesita para resolver agricultor, finca/parcela y producto. |
| [`ProductoController`](../../src/main/java/co/edu/uniajc/agrovalle/controller/ProductoController.java) | Recibir solicitudes HTTP, validar/extraer datos y delegar. | Recibe `CatalogoProductoService`, `GestionProductoService` y `TransferenciaProductoService` por constructor. |

### Ejemplo de delegación posterior al refactor

```java
// Lectura: el controller no consulta el repositorio directamente.
return catalogoProductoService.buscar(municipio, categoria);

// Escritura: la operación se delega al servicio de gestión.
ProductoResponse response = gestionProductoService.publicar(usuarioId, request);
```

La lectura y escritura tienen ahora puntos de entrada y límites transaccionales distintos. El controller conserva coordinación HTTP, mientras que las reglas del caso de uso permanecen en los servicios.

## Por qué este cambio aporta a SOLID

- **SRP:** catálogo y gestión de cambios tienen motivos de cambio distintos y están en clases separadas.
- **Responsabilidad transaccional más clara:** las consultas del catálogo son de solo lectura; las operaciones de gestión son transaccionales de escritura.
- **Pruebas más focalizadas:** existen [`CatalogoProductoServiceTest`](../../src/test/java/co/edu/uniajc/agrovalle/service/CatalogoProductoServiceTest.java) y [`GestionProductoServiceTest`](../../src/test/java/co/edu/uniajc/agrovalle/service/GestionProductoServiceTest.java), separadas por comportamiento.
- **Límite del resultado:** `ProductoController` sigue dependiendo de servicios concretos y `GestionProductoService` conserva más de un repositorio. Este cambio no demuestra DIP ni SRP globales por sí solo.

## Verificación y alcance de la evidencia

El cambio y los tests aparecen en el commit `89edf4f`; el [workflow #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070) ejecutado sobre `main` en `a7e2097` terminó en verde con pruebas, Checkstyle y JaCoCo. El resultado respalda que la suite del commit de `main` pasó; no demuestra una ceremonia de revisión ni que el refactor haya sido desarrollado mediante TDD.

Los otros ejemplos SOLID/GoF implementados están resumidos en [`patrones.md`](../diseno/patrones.md): Strategy para tarifas y descuentos, e inversión de dependencia en el cálculo de descuentos. La evidencia específica antes/después de este documento se limita a los dos servicios de producto descritos arriba.
