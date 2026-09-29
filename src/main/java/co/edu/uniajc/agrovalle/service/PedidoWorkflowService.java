package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.domain.EstadoPedido;
import co.edu.uniajc.agrovalle.domain.EventoTrazabilidad;
import co.edu.uniajc.agrovalle.domain.Pedido;
import co.edu.uniajc.agrovalle.domain.PedidoEstadoCambiadoEvent;
import co.edu.uniajc.agrovalle.repository.EventoTrazabilidadRepository;
import co.edu.uniajc.agrovalle.repository.PedidoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoWorkflowService {
  private final PedidoRepository pedidoRepository;
  private final EventoTrazabilidadRepository eventoRepository;
  private final ApplicationEventPublisher eventPublisher;

  public PedidoWorkflowService(PedidoRepository pedidoRepository,
      EventoTrazabilidadRepository eventoRepository, ApplicationEventPublisher eventPublisher) {
    this.pedidoRepository = pedidoRepository;
    this.eventoRepository = eventoRepository;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  public void confirmar(Long pedidoId, Long usuarioAgricultorId) {
    Pedido pedido = pedidoRepository.findById(pedidoId)
        .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    if (pedido.getEstado() != EstadoPedido.PENDIENTE || pedido.getDetalles().stream()
        .anyMatch(detalle -> detalle.getProducto().getAgricultor() == null
            || detalle.getProducto().getAgricultor().getUsuario() == null
            || !detalle.getProducto().getAgricultor().getUsuario().getId()
                .equals(usuarioAgricultorId))) {
      throw new TransicionPedidoInvalidaException();
    }
    actualizar(pedido, EstadoPedido.CONFIRMADO, "Reserva confirmada por el agricultor");
  }

  @Transactional
  public void enviarADespacho(Pedido pedido) {
    if (pedido.getEstado() != EstadoPedido.CONFIRMADO) {
      throw new TransicionPedidoInvalidaException();
    }
    actualizar(pedido, EstadoPedido.EN_DESPACHO, "Despacho programado");
  }

  @Transactional
  public void entregar(Pedido pedido) {
    if (pedido.getEstado() != EstadoPedido.EN_DESPACHO) {
      throw new TransicionPedidoInvalidaException();
    }
    actualizar(pedido, EstadoPedido.ENTREGADO, "Entrega completada");
  }

  private void actualizar(Pedido pedido, EstadoPedido estado, String descripcion) {
    pedido.cambiarEstado(estado);
    pedidoRepository.save(pedido);
    eventoRepository.save(new EventoTrazabilidad(pedido, estado, descripcion));
    eventPublisher.publishEvent(new PedidoEstadoCambiadoEvent(
        pedido.getComprador().getUsuario().getId(), pedido.getId(), estado, descripcion));
  }
}
