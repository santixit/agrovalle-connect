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

## Integración en reservas — Red/Green de extremo a extremo

Se añadió `reservaMayoristaAplicaDescuentoAlPrecioPorKilogramo` en la prueba de integración de la API. El commit `04980cc` guardó primero la prueba en rojo: para una reserva de 101 kg esperaba `1900.00` por kg y el endpoint devolvía `2000.00`. Después se conectó `CalculadorDescuentoAgricola` a `ReservaService`: el precio con descuento se guarda como detalle del pedido y se devuelve en la respuesta. También se aplica a cada línea del carrito según la cantidad reservada de esa oferta. La prueba enfocada pasó con la integración.

Verificación final con `mvnw clean verify`: 41 pruebas, 0 fallos, 0 errores; Checkstyle con 0 violaciones; JaCoCo superó los mínimos del 60 %:

| Métrica | Cobertura |
|---|---:|
| Instrucciones | 90,4 % |
| Ramas | 61,4 % |
| Líneas | 92,0 % |

El reporte HTML está en `target/site/jacoco/index.html`. En CI, el workflow publica `target/site/jacoco/` como artefacto `jacoco-coverage-report`.
