package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.EstadoProducto;
import jakarta.validation.constraints.NotNull;

public record EstadoProductoRequest(@NotNull EstadoProducto estado) { }
