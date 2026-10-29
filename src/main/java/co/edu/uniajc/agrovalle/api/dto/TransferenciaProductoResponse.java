package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.TransferenciaProducto;
import java.time.LocalDateTime;

public record TransferenciaProductoResponse(Long id, Long productoId,
    Long agricultorAnteriorId, String agricultorAnterior, Long agricultorNuevoId,
    String agricultorNuevo, LocalDateTime fechaTransferencia) {
  public static TransferenciaProductoResponse from(TransferenciaProducto transferencia) {
    return new TransferenciaProductoResponse(transferencia.getId(),
        transferencia.getProducto().getId(), transferencia.getAgricultorAnterior().getId(),
        transferencia.getAgricultorAnterior().getNombre(),
        transferencia.getAgricultorNuevo().getId(), transferencia.getAgricultorNuevo().getNombre(),
        transferencia.getFechaTransferencia());
  }
}
