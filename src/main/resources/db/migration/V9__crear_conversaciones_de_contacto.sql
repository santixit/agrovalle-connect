CREATE TABLE mensajes_contacto (
    id BIGSERIAL PRIMARY KEY,
    contacto_id BIGINT NOT NULL REFERENCES contactos(id) ON DELETE CASCADE,
    autor VARCHAR(20) NOT NULL CHECK (autor IN ('COMPRADOR', 'AGRICULTOR')),
    mensaje VARCHAR(1000) NOT NULL,
    creado_en TIMESTAMP NOT NULL
);

CREATE INDEX idx_mensajes_contacto_contacto_creado
    ON mensajes_contacto (contacto_id, creado_en, id);

INSERT INTO mensajes_contacto (contacto_id, autor, mensaje, creado_en)
SELECT id, 'COMPRADOR', mensaje, creado_en
FROM contactos;

INSERT INTO mensajes_contacto (contacto_id, autor, mensaje, creado_en)
SELECT id, 'AGRICULTOR', respuesta, COALESCE(respondido_en, creado_en)
FROM contactos
WHERE respuesta IS NOT NULL AND respuesta <> '';
