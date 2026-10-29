# Demostración técnica complementaria — Sprint 2

**Fecha:** 2026-10-29
**Entorno:** staging público de AgroValle Connect
**Tipo:** comprobación técnica complementaria realizada para preparar la revisión; no se registra como demo de una ceremonia Sprint Review anterior.

## Recorrido reproducible

1. Abrir la [aplicación en staging](https://agrovalle-connect-staging.onrender.com/).
2. Consultar `GET /api/v1/health` para verificar disponibilidad del servicio.
3. Consultar `GET /api/v1/productos` para comprobar que el catálogo responde.
4. Explicar que esta comprobación solo lee datos públicos: no crea ni modifica registros.

## Resultado observado

| Comprobación | Resultado | Alcance |
|---|---|---|
| `GET /` | HTTP 200; contenido de la aplicación recibido (48.443 bytes en esta consulta). | Confirma que la página pública respondió; no valida todos los flujos del navegador. |
| `GET /api/v1/health` | HTTP 200; `{"service":"agrovalle-connect","status":"UP"}`. | Confirma la respuesta de salud de la aplicación en ese momento. |
| `GET /api/v1/productos` | HTTP 200; un elemento en la respuesta. | Confirma que el endpoint público del catálogo respondió con datos; no valida autenticación ni persistencia mediante una escritura. |

La instancia de Render puede despertar después de inactividad. El resultado es una observación puntual del 2026-10-29; repetir el recorrido durante la presentación si staging sigue disponible.

## Límite de la evidencia

La demostración fue una verificación puntual de solo lectura, no una ejecución de historias con usuarios ni una ceremonia formal de Sprint Review. No se registran historias formalmente comprometidas para Sprint 2 porque no se encontró evidencia de esa selección. El incremento ilustrable puede contextualizarse con el trabajo integrado en [PR #51](https://github.com/santixit/agrovalle-connect/pull/51) (Strategy de fletes) y [PR #52](https://github.com/santixit/agrovalle-connect/pull/52) (mockups y pruebas de navegador), sin afirmar que fueran seleccionados formalmente en la planificación.

## Guion breve para presentarlo

> “Voy a mostrar una comprobación del incremento disponible en staging. Primero verifico la página y el endpoint de salud; después consulto el catálogo. El servicio respondió correctamente y el catálogo entregó un registro. El recorrido es de solo lectura. Como contexto, el incremento también incluye la estrategia demostrativa de fletes y el ajuste de mockups/pruebas integrados en los PR 51 y 52. No tenemos un acta que confirme una selección formal de historias ni una demo realizada durante una ceremonia previa, así que esta comprobación la presentamos como evidencia técnica complementaria.”
