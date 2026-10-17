# Evidencia TDD: descuento mayorista agrícola

## Regla

Tomada del caso de estudio de la Sesión 8: al reservar más de 100 kg, se descuenta el 5 % del precio unitario. La condición es estricta: con exactamente 100 kg no hay descuento. El resultado monetario se redondea a dos decimales con `HALF_UP`.

## Red — prueba primero

Commit `6ef96d0` (`test: expose missing wholesale discount rule (RED)`). La prueba `aplicaCincoPorCientoCuandoLaCompraSuperaCienKilos` espera `1900.00` para 100,01 kg a `2000.00` por kg. Al ejecutar:

```powershell
.\mvnw.cmd -Dtest=DescuentoMayoristaServiceTest test
```

el resultado fue el fallo esperado de la aserción: `expected: <1900.00> but was: <2000.00>`. La prueba de límite de 100 kg pasó; Maven reportó 2 pruebas, 1 fallo y 0 errores. El fallo era de comportamiento, no de compilación o configuración.

El commit Red se creó con los hooks omitidos intencionalmente, porque el hook de pre-commit ejecuta la suite y debe rechazarse mientras esta prueba esté roja. Los hooks volvieron a ejecutarse en Green y Refactor.

## Green — implementación mínima

Commit `140c5d3` (`feat: apply five percent discount above one hundred kilos`). El servicio aplica el factor `0.95` cuando los kilos superan 100. El mismo comando enfocado pasó con 2 pruebas, 0 fallos y 0 errores. El hook de pre-commit también ejecutó la suite completa: 40 pruebas, 0 fallos y Checkstyle con 0 violaciones.

## Refactor — diseño extensible

La implementación ahora depende del contrato `CalculadorDescuentoAgricola`, extrae el límite de 100 kg y la constante `DESCUENTO_MAYORISTA_CINCO_PORCIENTO`, y conserva el comportamiento cubierto por las pruebas.

Verificación final con `mvnw clean verify`: 40 pruebas, 0 fallos, 0 errores; Checkstyle con 0 violaciones; JaCoCo superó los mínimos del 60 %:

| Métrica | Cobertura |
|---|---:|
| Instrucciones | 90,4 % |
| Ramas | 61,4 % |
| Líneas | 91,9 % |

El reporte HTML está en `target/site/jacoco/index.html`. En CI, el workflow publica `target/site/jacoco/` como artefacto `jacoco-coverage-report`.
