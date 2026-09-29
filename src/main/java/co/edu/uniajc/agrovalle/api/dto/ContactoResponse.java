package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Contacto;
import java.time.LocalDateTime;

public record ContactoResponse(Long id, Long productoId, Long agricultorId,
    String estado, LocalDateTime creadoEn) {
  public static ContactoResponse from(Contacto contacto) {
    return new ContactoResponse(contacto.getId(), contacto.getProducto().getId(),
        contacto.getAgricultor().getId(), contacto.getEstado().name(), contacto.getCreadoEn());
  }
}
