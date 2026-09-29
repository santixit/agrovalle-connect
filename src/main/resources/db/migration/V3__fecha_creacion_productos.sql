ALTER TABLE productos ADD COLUMN creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

CREATE INDEX idx_productos_creado_en ON productos (creado_en);
