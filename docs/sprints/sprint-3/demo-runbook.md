# Guion reproducible de demo — Sprint 3

**Propósito:** demostrar HU-06, HU-02, HU-10 y HU-09 en staging con datos sintéticos. Este guion prepara la demo; no afirma que ya se haya ejecutado.

## Reglas antes de comenzar

1. Usa solamente la URL pública de staging documentada en [deploy-staging.md](../../deploy-staging.md), nunca producción ni la base real de un agricultor.
2. Abre Postman y crea una variable local baseUrl con la URL de staging. Guarda token y contraseña solo en el entorno local de Postman; no los pongas en Git, capturas o chat.
3. Usa una cédula sintética, por ejemplo DEMO-SPRINT3-20261119-01, y un correo de prueba que controles. Si el valor ya existe porque la base conserva registros, usa otro sufijo.
4. Anota el commit o la rama desplegada y la hora de la demo.
5. En GitHub Projects, confirma quién hará la demostración y quién registrará la decisión del PO.

## 1. Crear una cuenta de agricultor de prueba

Envía POST {{baseUrl}}/api/v1/auth/register con Content-Type: application/json:

    {
      "nombre": "Ana Ruiz Demo",
      "cedula": "DEMO-SPRINT3-20261119-01",
      "ubicacion_valle": "Dagua",
      "correo": "agricultor-demo+UNICO@example.test",
      "contrasena": "REEMPLAZAR_POR_CLAVE_TEMPORAL_DE_PRUEBA"
    }

Usa un correo de prueba único que acepte el sistema; el marcador .test es solo un ejemplo. La respuesta esperada es **201 Created**. Guarda el id y revisa que la respuesta no incluya cédula ni contraseña. Si el agricultor de demo ya existe, usa sus credenciales locales y continúa al login.

## 2. Obtener token del agricultor

Envía POST {{baseUrl}}/api/v1/auth/login:

    {
      "correo": "CORREO_SINTETICO_USADO_EN_EL_REGISTRO",
      "contrasena": "LA_MISMA_CLAVE_TEMPORAL_LOCAL"
    }

Espera **200 OK**. Guarda el valor de accessToken en una variable local farmerToken de Postman. Para las llamadas protegidas agrega Authorization: Bearer {{farmerToken}}.

## 3. HU-06 — registrar finca

Envía POST {{baseUrl}}/api/v1/fincas con el token del agricultor:

    {
      "nombre": "Finca Demo Sprint 3",
      "municipio": "Dagua",
      "direccion": "Vereda de prueba"
    }

Espera **201 Created**. Guarda id como fincaId. El servidor crea automáticamente una parcela GENERAL para la finca. Envía GET {{baseUrl}}/api/v1/parcelas/mias y guarda el identificador de esa parcela como parcelaId.

## 4. HU-02 — publicar cosecha

Envía POST {{baseUrl}}/api/v1/productos con el token:

    {
      "nombre": "Mango Demo Sprint 3",
      "categoria": "Frutas",
      "cantidadKg": 25,
      "precioPorKg": 4200,
      "fecha_cosecha": "AAAA-MM-DD",
      "parcelaId": 1
    }

Reemplaza fecha_cosecha por la fecha actual o una posterior en formato ISO AAAA-MM-DD, y parcelaId por el ID devuelto en el paso anterior. No envíes municipio cuando uses parcela: la API lo deriva de la finca. Espera **201 Created** y guarda el ID del producto como productoId.

Envía GET {{baseUrl}}/api/v1/productos?municipio=Dagua&categoria=Frutas. La respuesta debe ser **200 OK** e incluir la oferta publicada mientras esté disponible.

## 5. HU-10 — consultar detalle público

Sin enviar token, envía GET {{baseUrl}}/api/v1/productos/{{productoId}}.

Espera **200 OK** y comprueba los campos de nombre, categoría, municipio, disponibilidad, precio, agricultor y finca. Confirma que no aparecen cédula ni credenciales.

## 6. HU-09 — marcar la oferta agotada

Con el token del mismo agricultor, envía PATCH {{baseUrl}}/api/v1/productos/{{productoId}}/estado:

    {
      "estado": "AGOTADO"
    }

Espera **200 OK**. Repite el filtro de catálogo del paso 4. La oferta agotada ya no debe aparecer en la lista pública de ofertas disponibles.

## 7. Capturas y registro de aceptación

Guarda una captura por respuesta principal en docs/evidencias/sprint-3/:

| Archivo sugerido | Historia | Qué debe verse |
|---|---|---|
| 01-hu06-finca.png | HU-06 | Método/ruta, HTTP 201 y cuerpo con ID de finca. |
| 02-hu02-publicar.png | HU-02 | Método/ruta, HTTP 201 y respuesta de la oferta. |
| 03-hu10-detalle.png | HU-10 | GET público, HTTP 200, detalle y ausencia de campos privados. |
| 04-hu09-agotado.png | HU-09 | PATCH autenticado, HTTP 200 y catálogo posterior sin la oferta. |

Antes de guardar, oculta el header Authorization, la contraseña, el token, correo personal y cualquier dato real. En [review.md](review.md), el PO registra para cada HU Aceptada o Requiere cambios, nombre/usuario, fecha y enlace a la evidencia. No marcar aceptación si se ejecutó solo el test automatizado.

## Resultado esperado del recorrido

El recorrido confirma la asociación agricultor–finca–parcela, publica una cosecha, permite consultar su detalle público y comprueba que el propietario puede retirarla del catálogo al marcarla agotada. La aceptación final depende de la decisión real del PO. La lista de cierre está en [checklist-inicio.md](checklist-inicio.md).
