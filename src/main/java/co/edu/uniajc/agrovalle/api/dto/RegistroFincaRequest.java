package co.edu.uniajc.agrovalle.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroFincaRequest(@NotBlank @Size(max = 120) String nombre,
    @NotBlank @Size(max = 80) String municipio,
    @NotBlank @Size(max = 200) String direccion) { }
