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
    @NotBlank(message = "Escribe el nombre del producto")
    @Size(max = 120, message = "El nombre del producto no puede superar 120 caracteres")
    String nombre,
    @NotBlank(message = "Selecciona o escribe la categoría")
    @Size(max = 80, message = "La categoría no puede superar 80 caracteres")
    String categoria,
    @NotBlank(message = "Escribe el municipio de origen")
    @Size(max = 80, message = "El municipio no puede superar 80 caracteres")
    String municipio,
    @NotNull(message = "Indica la cantidad disponible")
    @Positive(message = "La cantidad debe ser mayor que cero")
    BigDecimal cantidadKg,
    @NotNull(message = "Indica el precio por kilogramo")
    @DecimalMin(value = "0.01", message = "El precio por kilogramo debe ser mayor que cero")
    BigDecimal precioPorKg,
    @NotNull(message = "Selecciona la fecha de cosecha")
    @FutureOrPresent(message = "La fecha de cosecha debe ser hoy o posterior")
    @JsonProperty("fecha_cosecha") LocalDate fechaCosecha,
    Long fincaId) { }
