ALTER TABLE pedidos DROP CONSTRAINT ck_pedidos_estado;

ALTER TABLE pedidos ADD CONSTRAINT ck_pedidos_estado
    CHECK (estado IN ('PENDIENTE', 'CONFIRMADO', 'PREPARANDO', 'EN_DESPACHO',
                      'EN_RUTA', 'ENTREGADO', 'CANCELADO'));
