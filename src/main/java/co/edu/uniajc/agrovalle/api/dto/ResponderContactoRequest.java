package co.edu.uniajc.agrovalle.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResponderContactoRequest(@NotBlank @Size(max = 1000) String mensaje) { }
