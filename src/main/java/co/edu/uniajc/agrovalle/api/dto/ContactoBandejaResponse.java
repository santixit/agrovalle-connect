package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Contacto;
import java.time.LocalDateTime;
import java.util.List;

public record ContactoBandejaResponse(Long id, Long productoId, String producto,
    String comprador, String agricultor, String mensaje, String respuesta, String estado,
    LocalDateTime creadoEn, LocalDateTime respondidoEn,
    List<MensajeContactoResponse> conversacion) {
  public static ContactoBandejaResponse from(Contacto contacto) {
    return new ContactoBandejaResponse(contacto.getId(), contacto.getProducto().getId(),
        contacto.getProducto().getNombre(), contacto.getComprador().getNombre(),
        contacto.getAgricultor().getNombre(),
        contacto.getMensaje(), contacto.getRespuesta(), contacto.getEstado().name(),
        contacto.getCreadoEn(), contacto.getRespondidoEn(),
        contacto.getConversacion().stream().map(MensajeContactoResponse::from).toList());
  }
}
