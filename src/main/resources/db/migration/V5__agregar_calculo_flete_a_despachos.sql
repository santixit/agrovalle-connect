ALTER TABLE despachos
    ADD COLUMN municipio_destino VARCHAR(80);

ALTER TABLE despachos
    ADD COLUMN peso_total_kg NUMERIC(12, 2);

ALTER TABLE despachos
    ADD COLUMN costo_flete NUMERIC(12, 2);
