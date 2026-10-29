-- Snapshot consolidado de referencia de las migraciones Flyway V1-V7.
-- Solo lectura: no ejecutar junto con Flyway ni usar como migración.

-- ============================================================================
-- V1__create_agricultores_y_productos.sql
-- ============================================================================
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

-- ============================================================================
-- V2__ampliar_modelo_integrador.sql
-- ============================================================================
-- Modelo relacional base para usuarios, oferta, ventas, contactos y trazabilidad.
-- Las tablas de HU-01 y HU-04 existentes se conservan y amplían sin perder registros.

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    correo VARCHAR(254) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_usuarios_rol CHECK (rol IN ('AGRICULTOR', 'COMPRADOR', 'ADMIN'))
);

ALTER TABLE agricultores ADD COLUMN usuario_id BIGINT NULL;
ALTER TABLE agricultores
    ADD CONSTRAINT fk_agricultores_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id);
ALTER TABLE agricultores
    ADD CONSTRAINT uq_agricultores_usuario UNIQUE (usuario_id);

CREATE TABLE compradores (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE REFERENCES usuarios (id),
    nombre VARCHAR(120) NOT NULL,
    telefono VARCHAR(30),
    tipo_comercio VARCHAR(80),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fincas (
    id BIGSERIAL PRIMARY KEY,
    agricultor_id BIGINT NOT NULL REFERENCES agricultores (id),
    nombre VARCHAR(120) NOT NULL,
    municipio VARCHAR(80) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_finca_nombre_agricultor UNIQUE (agricultor_id, nombre)
);

ALTER TABLE productos
    ADD COLUMN finca_id BIGINT NULL REFERENCES fincas (id);
ALTER TABLE productos
    ADD COLUMN agricultor_id BIGINT NULL REFERENCES agricultores (id);
ALTER TABLE productos ADD COLUMN cantidad_kg NUMERIC(12, 2) NULL;
ALTER TABLE productos ADD COLUMN precio_por_kg NUMERIC(12, 2) NULL;
ALTER TABLE productos ADD COLUMN fecha_cosecha DATE NULL;
ALTER TABLE productos ADD COLUMN estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE';
ALTER TABLE productos ADD CONSTRAINT ck_productos_cantidad_no_negativa
    CHECK (cantidad_kg IS NULL OR cantidad_kg >= 0);
ALTER TABLE productos ADD CONSTRAINT ck_productos_precio_no_negativo
    CHECK (precio_por_kg IS NULL OR precio_por_kg >= 0);
ALTER TABLE productos ADD CONSTRAINT ck_productos_estado
    CHECK (estado IN ('DISPONIBLE', 'RESERVADO', 'AGOTADO', 'PAUSADO'));

CREATE INDEX idx_productos_finca ON productos (finca_id);

CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    comprador_id BIGINT NOT NULL REFERENCES compradores (id),
    estado VARCHAR(24) NOT NULL DEFAULT 'PENDIENTE',
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_pedidos_estado
        CHECK (estado IN ('PENDIENTE', 'CONFIRMADO', 'PREPARANDO', 'EN_DESPACHO',
                          'ENTREGADO', 'CANCELADO'))
);

CREATE TABLE detalle_pedido (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedidos (id) ON DELETE CASCADE,
    producto_id BIGINT NOT NULL REFERENCES productos (id),
    cantidad_kg NUMERIC(12, 2) NOT NULL,
    precio_por_kg NUMERIC(12, 2) NOT NULL,
    CONSTRAINT ck_detalle_pedido_cantidad_positiva CHECK (cantidad_kg > 0),
    CONSTRAINT ck_detalle_pedido_precio_no_negativo CHECK (precio_por_kg >= 0)
);

CREATE TABLE despachos (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL UNIQUE REFERENCES pedidos (id),
    estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADO',
    fecha_programada DATE NOT NULL,
    franja_horaria VARCHAR(80) NOT NULL,
    ruta VARCHAR(300),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_despachos_estado
        CHECK (estado IN ('PROGRAMADO', 'EN_RUTA', 'ENTREGADO', 'CANCELADO'))
);

CREATE TABLE eventos_trazabilidad (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedidos (id) ON DELETE CASCADE,
    estado VARCHAR(24) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    ocurrido_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_eventos_trazabilidad_pedido_fecha
    ON eventos_trazabilidad (pedido_id, ocurrido_en);

CREATE TABLE notificaciones (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuarios (id),
    tipo VARCHAR(30) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    leida BOOLEAN NOT NULL DEFAULT FALSE,
    creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contactos (
    id BIGSERIAL PRIMARY KEY,
    comprador_id BIGINT NOT NULL REFERENCES compradores (id),
    agricultor_id BIGINT NOT NULL REFERENCES agricultores (id),
    producto_id BIGINT NOT NULL REFERENCES productos (id),
    mensaje VARCHAR(1000) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'NUEVO',
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_contactos_estado CHECK (estado IN ('NUEVO', 'RESPONDIDO', 'CERRADO'))
);

CREATE TABLE favoritos (
    id BIGSERIAL PRIMARY KEY,
    comprador_id BIGINT NOT NULL REFERENCES compradores (id) ON DELETE CASCADE,
    producto_id BIGINT NOT NULL REFERENCES productos (id) ON DELETE CASCADE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_favorito_comprador_producto UNIQUE (comprador_id, producto_id)
);

CREATE TABLE transacciones_precio (
    id BIGSERIAL PRIMARY KEY,
    producto_id BIGINT NOT NULL REFERENCES productos (id),
    cantidad_kg NUMERIC(12, 2) NOT NULL,
    precio_por_kg NUMERIC(12, 2) NOT NULL,
    ocurrida_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_transaccion_cantidad_positiva CHECK (cantidad_kg > 0),
    CONSTRAINT ck_transaccion_precio_no_negativo CHECK (precio_por_kg >= 0)
);

CREATE INDEX idx_transacciones_precio_producto_fecha
    ON transacciones_precio (producto_id, ocurrida_en);

-- ============================================================================
-- V3__fecha_creacion_productos.sql
-- ============================================================================
ALTER TABLE productos ADD COLUMN creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE INDEX idx_productos_creado_en ON productos (creado_en);

-- ============================================================================
-- V4__estado_en_ruta.sql
-- ============================================================================
ALTER TABLE pedidos DROP CONSTRAINT ck_pedidos_estado;

ALTER TABLE pedidos ADD CONSTRAINT ck_pedidos_estado
    CHECK (estado IN ('PENDIENTE', 'CONFIRMADO', 'PREPARANDO', 'EN_DESPACHO',
                      'EN_RUTA', 'ENTREGADO', 'CANCELADO'));

-- ============================================================================
-- V5__agregar_calculo_flete_a_despachos.sql
-- ============================================================================
ALTER TABLE despachos
    ADD COLUMN municipio_destino VARCHAR(80);

ALTER TABLE despachos
    ADD COLUMN peso_total_kg NUMERIC(12, 2);

ALTER TABLE despachos
    ADD COLUMN costo_flete NUMERIC(12, 2);

-- ============================================================================
-- V6__agregar_datos_de_contacto_y_documento.sql
-- ============================================================================
ALTER TABLE agricultores ADD COLUMN tipo_documento VARCHAR(30);
ALTER TABLE agricultores ADD COLUMN telefono VARCHAR(30);

ALTER TABLE compradores ADD COLUMN tipo_documento VARCHAR(30);
ALTER TABLE compradores ADD COLUMN cedula VARCHAR(30);


-- ============================================================================
-- V7__normalizar_parcelas_y_transferencias.sql
-- ============================================================================
CREATE TABLE parcelas (
    id BIGSERIAL PRIMARY KEY,
    finca_id BIGINT NOT NULL REFERENCES fincas (id) ON DELETE CASCADE,
    codigo VARCHAR(80) NOT NULL,
    agricultor_responsable_actual_id BIGINT NOT NULL REFERENCES agricultores (id),
    creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_parcela_codigo_finca UNIQUE (finca_id, codigo)
);

CREATE INDEX idx_parcelas_responsable_actual
    ON parcelas (agricultor_responsable_actual_id);

CREATE TABLE producto_ubicacion (
    id BIGSERIAL PRIMARY KEY,
    producto_id BIGINT NOT NULL UNIQUE REFERENCES productos (id) ON DELETE CASCADE,
    parcela_id BIGINT NULL REFERENCES parcelas (id),
    agricultor_sin_finca_id BIGINT NULL REFERENCES agricultores (id),
    municipio_sin_finca VARCHAR(80) NULL,
    CONSTRAINT ck_producto_ubicacion_origen CHECK (
        (parcela_id IS NOT NULL AND agricultor_sin_finca_id IS NULL
            AND municipio_sin_finca IS NULL)
        OR (parcela_id IS NULL AND municipio_sin_finca IS NOT NULL)
    )
);

CREATE INDEX idx_producto_ubicacion_parcela ON producto_ubicacion (parcela_id);
CREATE INDEX idx_producto_ubicacion_municipio_sin_finca
    ON producto_ubicacion (municipio_sin_finca);

CREATE TABLE transferencias_producto (
    id BIGSERIAL PRIMARY KEY,
    producto_id BIGINT NOT NULL REFERENCES productos (id),
    agricultor_anterior_id BIGINT NOT NULL REFERENCES agricultores (id),
    agricultor_nuevo_id BIGINT NOT NULL REFERENCES agricultores (id),
    fecha_transferencia TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_transferencia_agricultores_distintos
        CHECK (agricultor_anterior_id <> agricultor_nuevo_id)
);

CREATE INDEX idx_transferencias_producto_fecha
    ON transferencias_producto (producto_id, fecha_transferencia, id);

-- V7 migra los productos existentes a parcelas/ubicaciones antes de eliminar
-- las columnas redundantes de productos.
DROP INDEX idx_productos_municipio_categoria_activo;
ALTER TABLE productos DROP COLUMN finca_id;
ALTER TABLE productos DROP COLUMN agricultor_id;
ALTER TABLE productos DROP COLUMN municipio;
