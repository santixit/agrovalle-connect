# Estrategia de pruebas y TDD

## Pirámide de pruebas

La suite debe privilegiar feedback rápido y aislado, manteniendo una base amplia de pruebas unitarias:

| Nivel | Proporción objetivo | Alcance |
|---|---:|---|
| Unitarias | 70% | Reglas de dominio y servicios aislados; JUnit 5 y Mockito, sin base de datos ni contexto Spring. |
| Integración | 20% | Persistencia, migraciones y contratos REST; H2 para persistencia local y MockMvc para HTTP. |
| E2E / UI | 10% | Flujos críticos completos desde la interfaz. Mantener pocos por su costo y sensibilidad a cambios de UI. |

Las proporciones son una guía de diseño de la suite, no un reemplazo de cobertura significativa. No se añaden pruebas E2E para inflar cantidades; cada nivel debe verificar un riesgo distinto. La suite actual incluye pruebas unitarias de dominio/servicios y pruebas de persistencia y REST con Spring Boot y MockMvc.

## Patrón AAA

Cada prueba sigue **Arrange – Act – Assert**: prepara datos y dobles, ejecuta una sola operación y comprueba resultado y colaboraciones relevantes. Usa `@ExtendWith(MockitoExtension.class)`, `@Mock` e `@InjectMocks` para probar servicios sin conectarse a PostgreSQL. En pruebas REST, `MockMvc` comprueba rutas, validaciones, códigos HTTP y JSON.

## Ciclo Red – Green – Refactor

Para una regla nueva, trabajar en pasos pequeños:

1. **Red:** escribir primero una prueba con nombre de comportamiento y aserciones observables; ejecutarla sola y confirmar que falla por la regla ausente (no por un error de compilación o configuración).
2. **Green:** implementar el cambio mínimo y volver a ejecutar esa prueba hasta que pase.
3. **Refactor:** simplificar el diseño sin cambiar el comportamiento y volver a ejecutar la prueba enfocada y la suite pertinente.

No afirmar que una regla siguió TDD si no se observó el fallo inicial. Las pruebas `CatalogoProductoServiceTest` y `GestionProductoServiceTest` agregan cobertura aislada de los servicios separados en la sesión 7; son pruebas de regresión del comportamiento existente, no evidencia retrospectiva de un ciclo Red–Green.

## Cobertura y comandos

JaCoCo exige al menos **60% de instrucciones, ramas y líneas a nivel de módulo** antes de `verify`. La cobertura mide ejecución, no fuerza de las aserciones; revisar ramas críticas y casos límite junto con el reporte.

```powershell
.\mvnw.cmd test
.\mvnw.cmd jacoco:report
.\mvnw.cmd clean verify
```

El reporte HTML queda en `target/site/jacoco/index.html`. Para demostrar TDD en un Pull Request, adjuntar el resultado del primer test fallido, el paso a verde y el resultado tras refactorizar, junto con el reporte JaCoCo.

### Ejercicio TDD registrado

El ciclo Red–Green–Refactor de la regla de descuento mayorista está documentado con resultados y cobertura en [Evidencia TDD: descuento mayorista](evidencias/tdd-descuento-mayorista.md).
