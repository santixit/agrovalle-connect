package co.edu.uniajc.agrovalle.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record ReservaResponse(Long id, String estado, @JsonProperty("producto_id") Long productoId,
    @JsonProperty("cantidad_kg") BigDecimal cantidadKg,
    @JsonProperty("precio_por_kg") BigDecimal precioPorKg) { }
