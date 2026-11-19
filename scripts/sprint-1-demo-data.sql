-- Datos sintéticos para HU-04. Ejecutar solo en una base local de demostración
-- después de que Flyway haya aplicado las migraciones V1–V7.
-- La ubicación del producto está normalizada en producto_ubicacion desde V7;
-- insertar municipio directamente en productos ya no es válido.
WITH agricultor_demo AS (
    INSERT INTO agricultores (nombre, cedula, municipio)
    VALUES ('Agricultor Demo Sprint 1', 'DEMO-SPRINT1-AGRICULTOR-001', 'Dagua')
    ON CONFLICT (cedula) DO UPDATE
        SET nombre = EXCLUDED.nombre,
            municipio = EXCLUDED.municipio
    RETURNING id
), ofertas_demo (nombre, categoria, municipio, cantidad_kg, precio_por_kg) AS (
    VALUES
        ('Mango de Dagua', 'Frutas', 'Dagua', 100.00, 2500.00),
        ('Platano de Cali', 'Frutas', 'Cali', 80.00, 1800.00),
        ('Cafe de Dagua', 'Granos', 'Dagua', 50.00, 9000.00)
), productos_nuevos AS (
    INSERT INTO productos (
        nombre, categoria, cantidad_kg, precio_por_kg, fecha_cosecha, activo, estado
    )
    SELECT oferta.nombre,
           oferta.categoria,
           oferta.cantidad_kg,
           oferta.precio_por_kg,
           CURRENT_DATE,
           TRUE,
           'DISPONIBLE'
    FROM ofertas_demo oferta
    WHERE NOT EXISTS (
        SELECT 1
        FROM productos producto
        JOIN producto_ubicacion ubicacion ON ubicacion.producto_id = producto.id
        LEFT JOIN parcelas parcela ON parcela.id = ubicacion.parcela_id
        LEFT JOIN fincas finca ON finca.id = parcela.finca_id
        WHERE producto.nombre = oferta.nombre
          AND producto.categoria = oferta.categoria
          AND producto.activo = TRUE
          AND COALESCE(ubicacion.municipio_sin_finca, finca.municipio) = oferta.municipio
    )
    RETURNING id, nombre, categoria
)
INSERT INTO producto_ubicacion (
    producto_id, agricultor_sin_finca_id, municipio_sin_finca
)
SELECT producto_nuevo.id,
       agricultor_demo.id,
       oferta.municipio
FROM productos_nuevos producto_nuevo
JOIN ofertas_demo oferta
  ON oferta.nombre = producto_nuevo.nombre
 AND oferta.categoria = producto_nuevo.categoria
CROSS JOIN agricultor_demo;
