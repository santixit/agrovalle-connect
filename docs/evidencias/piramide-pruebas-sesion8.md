# Evidencia de pirámide de pruebas — Sesión 8

## Inventario observado

Conteo manual registrado de métodos `@Test` en `src/test/java` y pruebas declaradas con `test()` en `e2e/`. Cada método/flujo se cuenta una vez; no se ponderan los casos de métodos parametrizados ni los pasos internos de un flujo E2E.

| Nivel | Cantidad | Proporción observada | Referencia 70/20/10 | Resultado |
|---|---:|---:|---:|---|
| Unitarias aisladas | 22 | 47,8 % | 70 % | Por debajo |
| Integración | 23 | 50,0 % | 20 % | Por encima |
| E2E de interfaz | 1 | 2,2 % | 10 % | Por debajo |
| **Total contado** | **46** | **100 %** | **100 %** | **La proporción objetivo no se cumple** |

La distribución **70/20/10 no está alcanzada**. El inventario tiene más pruebas de integración y menos unitarias/E2E que la referencia del taller. No se cambia la clasificación de pruebas ni se agregan pruebas artificiales para que el porcentaje parezca cumplido.

Como orientación matemática, para un inventario de 46 elementos una distribución cercana sería 32 unitarias, 9 de integración y 5 E2E. Esa equivalencia no es un compromiso de Sprint ni indica que deban crearse exactamente esos números; la calidad depende también del riesgo y el comportamiento cubierto.

## Cobertura JaCoCo del workflow #103

El [workflow #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070) pasó en `main`, commit `a7e2097`. El artefacto JaCoCo de ese mismo run informa:

| Métrica | Resultado | Umbral configurado | Resultado del control |
|---|---:|---:|---|
| Instrucciones | 88 % | 60 % | Cumple |
| Ramas | 63 % | 60 % | Cumple |
| Líneas | 90 % | 60 % | Cumple |

El log del job confirma que JaCoCo pasó y el workflow muestra estado exitoso. JaCoCo mide cobertura de código Java; no mide la proporción unitaria/integración/E2E. Por eso una cobertura superior al umbral **no corrige** el incumplimiento de la distribución 70/20/10.

El reporte HTML real se conserva como [artefacto `jacoco-coverage-report` del run #103](https://github.com/santixit/agrovalle-connect/actions/runs/40894179070/artifacts/12906676338); el PDF de entrega enlaza ese artefacto y transcribe el resultado del job. No se adjunta una captura del HTML de JaCoCo de ese mismo run, por lo que la captura solicitada por el taller sigue pendiente. La captura existente `jacoco-main-6c19aae.png` corresponde a otro commit y no se presenta como si fuera la captura de #103.

## Ejecución Java y E2E

El workflow #103 terminó correctamente y la configuración de CI ejecuta Maven con Java 17, PostgreSQL, pruebas Java, Checkstyle, JaCoCo, E2E y despliegue a staging. La evidencia no afirma que la ejecución local de Playwright haya funcionado: la nota histórica indica que esa máquina tuvo un problema de loopback, mientras que el job de CI sí ejecutó el flujo E2E.

## TDD

El ciclo Red–Green–Refactor de la regla de descuento mayorista está documentado con commits y resultados en [Evidencia TDD: descuento mayorista](tdd-descuento-mayorista.md). Los casos de regresión agregados en otras sesiones no se describen como TDD si no quedó registrada la prueba fallida anterior a la implementación.

## Acción para mejorar la pirámide

En el siguiente incremento, elegir comportamientos de riesgo y probar primero las reglas puras como unitarias; reservar integración para contratos Spring/DB que necesiten esa frontera y E2E para pocos recorridos de usuario completos. Después de acordar el objetivo con la profesora/equipo, actualizar este conteo a partir de las pruebas realmente ejecutables. No se afirma que la distribución objetivo ya esté cumplida.
