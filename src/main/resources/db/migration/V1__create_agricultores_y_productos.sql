CREATE TABLE agricultores (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    cedula VARCHAR(30) NOT NULL UNIQUE,
    municipio VARCHAR(80) NOT NULL
);

CREATE TABLE productos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    categoria VARCHAR(80) NOT NULL,
    municipio VARCHAR(80) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_productos_municipio_categoria_activo
    ON productos (municipio, categoria, activo);
