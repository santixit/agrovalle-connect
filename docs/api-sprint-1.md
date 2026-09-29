# Contratos REST — Sprint 1

Base local: `http://localhost:8080`. Levantar PostgreSQL con `docker compose up -d db` y la API con `mvn spring-boot:run`.

## HU-01 — Registrar agricultor

`POST /api/v1/auth/register`

```json
{
  "nombre": "Ana Ruiz",
  "cedula": "ID-DE-PRUEBA-001",
  "ubicacion_valle": "Dagua"
}
```

Devuelve `201 Created`, un encabezado `Location` con el perfil y un DTO con `id`, `nombre` y `ubicacion_valle`. La respuesta no incluye la cedula.

## HU-07 — Consultar perfil

`GET /api/v1/productores/{id}`

Devuelve `200 OK` con el DTO del perfil o `404 Not Found` si el agricultor no existe.

## HU-04 — Filtrar el catalogo

`GET /api/v1/productos?municipio=Dagua&categoria=Frutas`

Devuelve `200 OK` con una coleccion JSON que contiene solo las ofertas activas coincidentes. Los dos filtros son opcionales; si no hay coincidencias devuelve `[]`.

## Errores previstos

- `400 Bad Request`: datos obligatorios vacios, campo demasiado largo o filtros invalidos.
- `404 Not Found`: identificador de agricultor inexistente.
- `409 Conflict`: cedula ya registrada.

La coleccion de Postman en `docs/postman/agrovalle-sprint-1.postman_collection.json` cubre los contratos iniciales de Sprint 1.

## Flujos funcionales del integrador

El registro de agricultor admite credenciales para habilitar autenticación. En perfiles migrados que solo contienen nombre, cédula y municipio, estos campos siguen siendo válidos, pero no pueden publicar hasta vincular una cuenta. El campo `contrasena` debe tener de 10 a 72 caracteres.

`POST /api/v1/auth/register/comprador` recibe `nombre`, `correo`, `contrasena` y los campos opcionales `telefono` y `tipoComercio`. Luego ambos roles usan `POST /api/v1/auth/login`:

```json
{"correo":"ana@example.com","contrasena":"ClaveSegura2026"}
```

La respuesta incluye `accessToken`, `tokenType: Bearer` y `expiresIn`. Enviar el token en `Authorization: Bearer <token>` en rutas privadas.

### Publicar, reservar y completar una entrega

- `POST /api/v1/fincas` con `{ "nombre": "La Esperanza", "municipio": "Dagua", "direccion": "Vereda El Salado" }` crea una finca del agricultor autenticado.
- `POST /api/v1/productos` exige rol agricultor y recibe `nombre`, `categoria`, `municipio`, `cantidadKg`, `precioPorKg`, `fecha_cosecha` y `fincaId` opcional. La fecha debe ser hoy o futura.
- `GET /api/v1/productos?municipio=Dagua&categoria=Frutas` y `GET /api/v1/productos/{id}` permiten buscar y ver oferta pública sin datos privados.
- `POST /api/v1/reservas` exige comprador y recibe `{ "productoId": 1, "cantidad_kg": 20 }`. Un bloqueo de escritura protege el inventario contra reservas simultáneas; insuficiencia responde 409.
- El agricultor confirma con `POST /api/v1/reservas/{id}/confirmar`; programa con `POST /api/v1/despachos`, que requiere `pedidoId`, `fecha_programada`, `franjaHoraria` y ruta opcional.
- Al confirmar entrega con `PATCH /api/v1/despachos/{pedidoId}/entregado`, el sistema registra la transacción completada y `GET /api/v1/precios/regionales?categoria=Frutas` consulta la media aritmética de hasta 50 ventas completadas durante las últimas 24 horas.
- `GET /api/v1/reservas/{id}/trazabilidad` solo permite al comprador dueño consultar eventos ordenados cronológicamente.

### Contactos, notificaciones y favoritos

- `POST /api/v1/contactos` requiere comprador y recibe `{ "productoId": 1, "mensaje": "Deseo comprar" }`.
- El agricultor recibe un aviso de aplicación consultable en `GET /api/v1/notificaciones/mias`.
- El comprador usa `POST /api/v1/favoritos/{productoId}`, `GET /api/v1/favoritos` y `DELETE /api/v1/favoritos/{productoId}`.

### Reporte administrativo

`GET /api/v1/admin/reportes/actividad?desde=2026-09-01&hasta=2026-09-30` requiere rol `ADMIN` y devuelve cuentas, ofertas y contactos registrados en el rango. No existe registro público de administradores.

Las pruebas automatizadas usan H2 local y el perfil `ci` utiliza PostgreSQL. Para probar la API local, configurar `JWT_SECRET` como Base64 de 32 bytes aleatorios; no incluir el valor en Git.
