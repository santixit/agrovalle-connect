# Estado medido de la piramide — Sesion 8

## Metodo

Conteo estatico de metodos `@Test` en `src/test/java` y clasificacion por alcance visible en las clases. Se excluyen suites parametrizadas si se agregan en el futuro: este conteo es por metodo fuente, no por numero de casos ejecutados.

| Nivel | Metodos | Porcentaje observado | Evidencia |
|---|---:|---:|---|
| Unitarias aisladas | 18 | 43,9 % | Dominio y servicios con JUnit/Mockito, sin contexto Spring. |
| Integracion | 23 | 56,1 % | 22 pruebas REST con `@SpringBootTest`/MockMvc y una prueba de persistencia con contexto Spring. |
| E2E de interfaz | 0 | 0 % | No hay pruebas de navegador/UI en `src/test/java`. |
| **Total** | **41** | **100 %** | Conteo de metodos `@Test` del estado revisado. |

Los objetivos de referencia de la guia son 70/20/10; la suite observada todavia no tiene esa distribucion y carece de E2E. No se agregan pruebas artificiales para alcanzar porcentajes: conviene extraer casos puros del dominio a pruebas unitarias y seleccionar pocos flujos UI criticos cuando exista infraestructura E2E.

## Cobertura y limites de evidencia

El registro de la ejecucion existente en [evidencia TDD de descuento mayorista](tdd-descuento-mayorista.md) reporta JaCoCo: instrucciones 90,4 %, ramas 61,4 % y lineas 92,0 %, sobre 41 pruebas, y Checkstyle sin violaciones. La guia exige minimo 60 % en las tres metricas. Estos valores corresponden a esa ejecucion documentada; no se presentan como un reporte recién generado para este cambio.

CI publica el HTML JaCoCo como artefacto `jacoco-coverage-report`. No se encontro una captura del reporte adjunta a la PR #44; el artefacto CI es evidencia consultable, pero no sustituye una captura solicitada expresamente por la guia. El ciclo Red–Green–Refactor verificable esta enlazado con commits y resultados en el documento TDD; las pruebas de la separacion SOLID no se etiquetan retrospectivamente como TDD.
