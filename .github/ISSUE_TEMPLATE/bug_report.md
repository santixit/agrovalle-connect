---
name: Reportar un error
about: Reporta un comportamiento incorrecto con pasos para reproducirlo y evidencia segura
title: "fix: [área] resumen breve del error"
labels: bug
assignees: ""
---

## Resumen del problema

Explica en una o dos frases qué no funciona y a quién afecta. Incluye el identificador de la HU o del requisito si lo conoces.

## Preparación

- Entorno: local / staging
- Rol de la cuenta: agricultor / comprador / administrador / público
- Datos usados: deben ser sintéticos; no incluyas cédulas, correos, teléfonos ni nombres reales.
- Estado previo necesario (sesión iniciada, oferta creada, inventario, etc.):

## Pasos para reproducirlo

1. Indica desde qué pantalla o endpoint comienzas.
2. Describe cada acción o solicitud en el orden exacto.
3. Anota los valores de prueba no sensibles que utilizaste.

## Resultado observado

Describe qué ocurrió. Si es una solicitud HTTP, indica método, ruta sin tokens, código de estado y un cuerpo de respuesta anonimizado.

## Resultado esperado

Describe qué debería ocurrir según la historia de usuario, el criterio BDD o el contrato REST relacionado. Enlaza el documento o issue si existe.

## Frecuencia e impacto

- Frecuencia: siempre / intermitente / una sola vez
- Impacto: bloquea una operación / resultado incorrecto / problema visual / otro
- ¿Hay una forma segura de continuar?: sí / no. Explica cuál, si aplica.

## Ambiente

- Rama y commit:
- Sistema operativo y versión:
- Navegador y versión (si aplica):
- URL base o endpoint (sin credenciales):
- Fecha y zona horaria de la prueba:

## Evidencia

Adjunta una captura, salida de consola o respuesta HTTP anonimizada. Para logs, incluye únicamente las líneas necesarias y elimina encabezados `Authorization`, cookies, contraseñas, tokens, secretos y datos personales.

## Comprobaciones antes de enviar

- [ ] Revisé que el problema no esté reportado ya.
- [ ] Puedo reproducirlo con los pasos descritos o indiqué por qué no.
- [ ] Usé datos sintéticos y eliminé información sensible.
- [ ] Enlacé la HU, el requisito o el flujo afectado cuando fue posible.
