package co.edu.uniajc.agrovalle.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Validated data required to register a farmer. */
public record RegistroAgricultorRequest(
    @NotBlank @Size(max = 120) String nombre,
    @NotBlank @Size(max = 30) String cedula,
    @JsonProperty("ubicacion_valle") @NotBlank @Size(max = 80) String ubicacionValle) { }
