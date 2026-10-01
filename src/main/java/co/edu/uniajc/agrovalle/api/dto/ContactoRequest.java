package co.edu.uniajc.agrovalle.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ContactoRequest(@NotNull @Positive Long productoId,
    @NotBlank @Size(max = 1000) String mensaje) { }
