package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.MensajeContacto;
import java.time.LocalDateTime;

public record MensajeContactoResponse(Long id, String autor, String mensaje,
    LocalDateTime creadoEn) {
  public static MensajeContactoResponse from(MensajeContacto mensaje) {
    return new MensajeContactoResponse(mensaje.getId(), mensaje.getAutor(),
        mensaje.getMensaje(), mensaje.getCreadoEn());
  }
}
