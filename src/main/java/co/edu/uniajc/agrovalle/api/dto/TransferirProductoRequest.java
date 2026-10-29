package co.edu.uniajc.agrovalle.api.dto;

import jakarta.validation.constraints.NotNull;

public record TransferirProductoRequest(@NotNull Long nuevoAgricultorId) { }
