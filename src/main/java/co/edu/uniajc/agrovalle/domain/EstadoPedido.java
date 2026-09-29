package co.edu.uniajc.agrovalle.domain;

/** Lifecycle states for a buyer order/reservation. */
public enum EstadoPedido {
  PENDIENTE,
  CONFIRMADO,
  PREPARANDO,
  EN_DESPACHO,
  EN_RUTA,
  ENTREGADO,
  CANCELADO
}
