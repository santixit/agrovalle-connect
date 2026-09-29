package co.edu.uniajc.agrovalle.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProgramarDespachoRequest(@NotNull @Positive Long pedidoId,
    @NotNull @FutureOrPresent @JsonProperty("fecha_programada") LocalDate fechaProgramada,
    @NotBlank @Size(max = 80) String franjaHoraria,
    @Size(max = 300) String ruta) { }
