package co.edu.uniajc.agrovalle.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ReservaRequest(@NotNull @Positive Long productoId,
    @NotNull @Positive @JsonProperty("cantidad_kg") BigDecimal cantidadKg) { }
