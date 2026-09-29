package co.edu.uniajc.agrovalle.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PublicarProductoRequest(
    @NotBlank @Size(max = 120) String nombre,
    @NotBlank @Size(max = 80) String categoria,
    @NotBlank @Size(max = 80) String municipio,
    @NotNull @Positive BigDecimal cantidadKg,
    @NotNull @DecimalMin(value = "0.01") BigDecimal precioPorKg,
    @NotNull @FutureOrPresent @JsonProperty("fecha_cosecha") LocalDate fechaCosecha,
    Long fincaId) { }
