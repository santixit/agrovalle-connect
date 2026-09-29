package co.edu.uniajc.agrovalle.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroCompradorRequest(@NotBlank @Size(max = 120) String nombre,
    @NotBlank @Email @Size(max = 254) String correo,
    @NotBlank @Size(min = 10, max = 72) String contrasena,
    @Size(max = 30) String telefono,
    @Size(max = 80) String tipoComercio) { }
