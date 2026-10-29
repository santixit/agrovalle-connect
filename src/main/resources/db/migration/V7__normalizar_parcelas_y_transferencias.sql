CREATE TABLE parcelas (
    id BIGSERIAL PRIMARY KEY,
    finca_id BIGINT NOT NULL REFERENCES fincas (id) ON DELETE CASCADE,
    codigo VARCHAR(80) NOT NULL,
    agricultor_responsable_actual_id BIGINT NOT NULL REFERENCES agricultores (id),
    creada_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_parcela_codigo_finca UNIQUE (finca_id, codigo)
);

-- Migrate farm-linked offers into one legacy parcel per farm/current farmer pair.
INSERT INTO parcelas (finca_id, codigo, agricultor_responsable_actual_id)
SELECT DISTINCT p.finca_id,
       'MIGRACION-' || CAST(COALESCE(p.agricultor_id, f.agricultor_id) AS VARCHAR),
       COALESCE(p.agricultor_id, f.agricultor_id)
FROM productos p
JOIN fincas f ON f.id = p.finca_id
WHERE p.finca_id IS NOT NULL;

-- Farms with no linked offers start with a parcel representing the whole farm.
INSERT INTO parcelas (finca_id, codigo, agricultor_responsable_actual_id)
SELECT f.id, 'GENERAL', f.agricultor_id
FROM fincas f
WHERE NOT EXISTS (
    SELECT 1 FROM parcelas p WHERE p.finca_id = f.id
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
        (parcela_id IS NOT NULL
            AND agricultor_sin_finca_id IS NULL
            AND municipio_sin_finca IS NULL)
        OR
        (parcela_id IS NULL
            AND municipio_sin_finca IS NOT NULL)
    )
);

INSERT INTO producto_ubicacion (producto_id, parcela_id)
SELECT p.id, parcela.id
FROM productos p
JOIN fincas f ON f.id = p.finca_id
JOIN parcelas parcela
  ON parcela.finca_id = f.id
 AND parcela.agricultor_responsable_actual_id = COALESCE(p.agricultor_id, f.agricultor_id)
WHERE p.finca_id IS NOT NULL;

INSERT INTO producto_ubicacion (producto_id, agricultor_sin_finca_id, municipio_sin_finca)
SELECT p.id, p.agricultor_id, p.municipio
FROM productos p
WHERE p.finca_id IS NULL;

CREATE INDEX idx_producto_ubicacion_parcela
    ON producto_ubicacion (parcela_id);
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

DROP INDEX idx_productos_municipio_categoria_activo;
ALTER TABLE productos DROP COLUMN finca_id;
ALTER TABLE productos DROP COLUMN agricultor_id;
ALTER TABLE productos DROP COLUMN municipio;
