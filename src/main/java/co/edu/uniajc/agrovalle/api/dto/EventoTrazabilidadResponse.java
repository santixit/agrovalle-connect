package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.EventoTrazabilidad;
import java.time.LocalDateTime;

public record EventoTrazabilidadResponse(String estado, String descripcion,
    LocalDateTime ocurridoEn) {
  public static EventoTrazabilidadResponse from(EventoTrazabilidad evento) {
    return new EventoTrazabilidadResponse(evento.getEstado().name(), evento.getDescripcion(),
        evento.getOcurridoEn());
  }
}
