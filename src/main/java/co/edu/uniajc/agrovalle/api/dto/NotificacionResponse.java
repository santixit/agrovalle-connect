package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Notificacion;
import java.time.LocalDateTime;

public record NotificacionResponse(Long id, String tipo, String mensaje,
    boolean leida, LocalDateTime creadaEn) {
  public static NotificacionResponse from(Notificacion notificacion) {
    return new NotificacionResponse(notificacion.getId(), notificacion.getTipo().name(),
        notificacion.getMensaje(), notificacion.isLeida(), notificacion.getCreadaEn());
  }
}
