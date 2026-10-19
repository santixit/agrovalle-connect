# Estado medido de la pirámide — Sesión 8

## Distribución observada

Conteo manual de métodos `@Test` en `src/test/java` más las pruebas declaradas con `test()` en `e2e/`. Cada método se cuenta una vez; el porcentaje describe el inventario de pruebas y no el número de casos parametrizados que se ejecutan.

| Nivel | Pruebas | Porcentaje observado | Evidencia |
|---|---:|---:|---|
| Unitarias aisladas | 22 | 47,8 % | JUnit/Mockito sin contexto Spring; incluye cuatro casos nuevos para redondeo, umbral e inventario nulo. |
| Integración | 23 | 50,0 % | Contratos REST con Spring Boot/MockMvc y persistencia con contexto Spring. |
| E2E de interfaz | 1 | 2,2 % | Playwright recorre registro/login de agricultor, creación de finca y publicación, y reserva como comprador. |
| **Total** | **46** | **100 %** | 45 pruebas Java y un flujo Playwright. |

La referencia de la guía es 70/20/10. La distribución observada aún está lejos de ese objetivo; no se añadieron pruebas artificiales para inflar porcentajes. La siguiente mejora es aumentar pruebas unitarias de reglas puras y mantener pocos flujos E2E críticos.

## Ejecución y cobertura

La ejecución local de `mvnw clean verify` pasó con **45 pruebas Java, cero fallos y cero errores**; Checkstyle reportó cero violaciones y JaCoCo cumplió el umbral configurado. El reporte HTML generado está en `target/site/jacoco/index.html` y puede regenerarse con `mvnw jacoco:report`.

| Métrica JaCoCo (Java) | Resultado local | Mínimo de la guía |
|---|---:|---:|
| Instrucciones | 90,5 % (3.878 / 4.287 cubiertas) | 60 % |
| Ramas | 63,6 % (117 / 184 cubiertas) | 60 % |
| Líneas | 92,0 % (859 / 934 cubiertas) | 60 % |

JaCoCo instrumenta la suite Java; el flujo Playwright se ejecuta aparte y no se incluye en esos porcentajes. En esta máquina Playwright no pudo iniciar su servidor local: el proceso Java 26 terminó al abrir el loopback (`Unable to establish loopback connection`). En GitHub Actions, que usa Java 17 y PostgreSQL como servicio, el workflow **#66** pasó y ejecutó el flujo E2E en 26 segundos: [resultado CI](https://github.com/santixit/agrovalle-connect/actions/runs/39302021661).

El workflow publica el HTML JaCoCo como artefacto `jacoco-coverage-report`. La captura del reporte real que pide la guía sigue pendiente de adjuntar a la PR; el reporte existe como artefacto CI, pero no se presenta una captura que no se haya podido obtener.

## Evidencia TDD

El ciclo Red–Green–Refactor de la regla de descuento mayorista está documentado con commits y resultados en [Evidencia TDD: descuento mayorista](tdd-descuento-mayorista.md). Los cuatro casos añadidos en esta sesión son regresiones de reglas ya existentes; no se etiquetan como TDD porque no se registró su fallo previo a la implementación.
