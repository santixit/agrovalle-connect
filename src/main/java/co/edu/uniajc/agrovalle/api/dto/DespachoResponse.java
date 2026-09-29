package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Despacho;
import java.time.LocalDate;

public record DespachoResponse(Long id, Long pedidoId, String estado,
    LocalDate fechaProgramada, String franjaHoraria, String ruta) {
  public static DespachoResponse from(Despacho despacho) {
    return new DespachoResponse(despacho.getId(), despacho.getPedido().getId(),
        despacho.getEstado().name(), despacho.getFechaProgramada(),
        despacho.getFranjaHoraria(), despacho.getRuta());
  }
}
