package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Pedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResumenResponse(Long id, String estado, LocalDateTime creadoEn,
    List<LineaPedido> productos) {
  public static PedidoResumenResponse from(Pedido pedido) {
    List<LineaPedido> lineas = pedido.getDetalles().stream().map(detalle ->
        new LineaPedido(detalle.getProducto().getId(), detalle.getProducto().getNombre(),
            detalle.getCantidadKg(), detalle.getPrecioPorKg())).toList();
    return new PedidoResumenResponse(pedido.getId(), pedido.getEstado().name(),
        pedido.getCreadoEn(), lineas);
  }

  public record LineaPedido(Long productoId, String nombre, BigDecimal cantidadKg,
      BigDecimal precioPorKg) { }
}
