package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.EventoTrazabilidadResponse;
import co.edu.uniajc.agrovalle.repository.EventoTrazabilidadRepository;
import co.edu.uniajc.agrovalle.repository.PedidoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TrazabilidadService {
  private final PedidoRepository pedidoRepository;
  private final EventoTrazabilidadRepository eventoRepository;

  public TrazabilidadService(PedidoRepository pedidoRepository,
      EventoTrazabilidadRepository eventoRepository) {
    this.pedidoRepository = pedidoRepository;
    this.eventoRepository = eventoRepository;
  }

  public List<EventoTrazabilidadResponse> consultar(Long usuarioId, Long pedidoId) {
    pedidoRepository.findByIdAndComprador_Usuario_Id(pedidoId, usuarioId)
        .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    return eventoRepository.findByPedido_IdOrderByOcurridoEnAsc(pedidoId).stream()
        .map(EventoTrazabilidadResponse::from).toList();
  }
}
