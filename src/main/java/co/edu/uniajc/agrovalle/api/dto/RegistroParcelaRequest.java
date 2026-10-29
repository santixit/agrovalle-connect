package co.edu.uniajc.agrovalle.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroParcelaRequest(
    @NotBlank @Size(max = 80) String codigo) { }
