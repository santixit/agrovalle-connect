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

La coleccion de Postman en `docs/postman/agrovalle-sprint-1.postman_collection.json` permite importar y ejecutar las tres consultas principales.
