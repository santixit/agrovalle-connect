package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.PedidoResumenResponse;
import co.edu.uniajc.agrovalle.domain.EstadoPedido;
import co.edu.uniajc.agrovalle.repository.PedidoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PedidoConsultaService {
  private final PedidoRepository pedidoRepository;

  public PedidoConsultaService(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  public List<PedidoResumenResponse> consultarComprador(Long usuarioId) {
    return pedidoRepository.buscarDelComprador(usuarioId).stream()
        .map(PedidoResumenResponse::from).toList();
  }

  public List<PedidoResumenResponse> consultarPendientesAgricultor(Long usuarioId) {
    return pedidoRepository.buscarParaAgricultor(usuarioId, List.of(
        EstadoPedido.PENDIENTE, EstadoPedido.CONFIRMADO, EstadoPedido.EN_DESPACHO)).stream()
        .map(PedidoResumenResponse::from).toList();
  }
}
