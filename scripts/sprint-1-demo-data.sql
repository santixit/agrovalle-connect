INSERT INTO productos (nombre, categoria, municipio, activo)
SELECT 'Mango de Dagua', 'Frutas', 'Dagua', TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM productos
    WHERE nombre = 'Mango de Dagua' AND categoria = 'Frutas' AND municipio = 'Dagua'
);

INSERT INTO productos (nombre, categoria, municipio, activo)
SELECT 'Platano de Cali', 'Frutas', 'Cali', TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM productos
    WHERE nombre = 'Platano de Cali' AND categoria = 'Frutas' AND municipio = 'Cali'
);

INSERT INTO productos (nombre, categoria, municipio, activo)
SELECT 'Cafe de Dagua', 'Granos', 'Dagua', TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM productos
    WHERE nombre = 'Cafe de Dagua' AND categoria = 'Granos' AND municipio = 'Dagua'
);
