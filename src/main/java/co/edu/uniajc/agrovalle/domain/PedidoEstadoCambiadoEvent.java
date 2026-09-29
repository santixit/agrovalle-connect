package co.edu.uniajc.agrovalle.domain;

public record PedidoEstadoCambiadoEvent(Long usuarioCompradorId, Long pedidoId,
    EstadoPedido estado, String detalle) { }
