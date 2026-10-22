package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.DespachoResponse;
import co.edu.uniajc.agrovalle.api.dto.ProgramarDespachoRequest;
import co.edu.uniajc.agrovalle.domain.Despacho;
import co.edu.uniajc.agrovalle.domain.Pedido;
import co.edu.uniajc.agrovalle.domain.EstadoDespacho;
import co.edu.uniajc.agrovalle.domain.TransaccionPrecio;
import java.math.BigDecimal;
import co.edu.uniajc.agrovalle.repository.DespachoRepository;
import co.edu.uniajc.agrovalle.repository.PedidoRepository;
import co.edu.uniajc.agrovalle.repository.TransaccionPrecioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DespachoService {
  private final PedidoRepository pedidoRepository;
  private final DespachoRepository despachoRepository;
  private final PedidoWorkflowService workflowService;
  private final TransaccionPrecioRepository transaccionPrecioRepository;
  private final CalculadorFleteService calculadorFleteService;

  public DespachoService(PedidoRepository pedidoRepository,
      DespachoRepository despachoRepository, PedidoWorkflowService workflowService,
      TransaccionPrecioRepository transaccionPrecioRepository,
      CalculadorFleteService calculadorFleteService) {
    this.pedidoRepository = pedidoRepository;
    this.despachoRepository = despachoRepository;
    this.workflowService = workflowService;
    this.transaccionPrecioRepository = transaccionPrecioRepository;
    this.calculadorFleteService = calculadorFleteService;
  }

  @Transactional
  public DespachoResponse programar(Long usuarioAgricultorId,
      ProgramarDespachoRequest request) {
    Pedido pedido = pedidoRepository.findById(request.pedidoId())
        .orElseThrow(() -> new PedidoNoEncontradoException(request.pedidoId()));
    boolean propio = pedido.getDetalles().stream().allMatch(detalle ->
        detalle.getProducto().getAgricultor() != null
            && detalle.getProducto().getAgricultor().getUsuario() != null
            && detalle.getProducto().getAgricultor().getUsuario().getId()
                .equals(usuarioAgricultorId));
    if (!propio) {
      throw new TransicionPedidoInvalidaException();
    }
    BigDecimal pesoTotalKg = pedido.getDetalles().stream().map(detalle -> detalle.getCantidadKg())
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    String municipioDestino = request.municipioDestino().trim();
    BigDecimal costoFlete = calculadorFleteService.calcular(municipioDestino, pesoTotalKg);
    workflowService.enviarADespacho(pedido);
    Despacho despacho = despachoRepository.save(new Despacho(pedido,
        request.fechaProgramada(), request.franjaHoraria().trim(), request.ruta(),
        municipioDestino, pesoTotalKg, costoFlete));
    return DespachoResponse.from(despacho);
  }

  @Transactional
  public DespachoResponse marcarEntregado(Long usuarioAgricultorId, Long pedidoId) {
    Pedido pedido = pedidoRepository.findById(pedidoId)
        .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    boolean propio = pedido.getDetalles().stream().allMatch(detalle ->
        detalle.getProducto().getAgricultor() != null
            && detalle.getProducto().getAgricultor().getUsuario() != null
            && detalle.getProducto().getAgricultor().getUsuario().getId()
                .equals(usuarioAgricultorId));
    if (!propio) {
      throw new TransicionPedidoInvalidaException();
    }
    Despacho despacho = despachoRepository.findByPedido_Id(pedidoId)
        .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    despacho.marcarEntregado();
    workflowService.entregar(pedido);
    pedido.getDetalles().forEach(detalle -> transaccionPrecioRepository.save(
        new TransaccionPrecio(detalle.getProducto(), detalle.getCantidadKg(),
            detalle.getPrecioPorKg())));
    return DespachoResponse.from(despachoRepository.save(despacho));
  }

  @Transactional
  public DespachoResponse marcarEnRuta(Long usuarioAgricultorId, Long pedidoId) {
    Pedido pedido = pedidoRepository.findById(pedidoId)
        .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    boolean propio = pedido.getDetalles().stream().allMatch(detalle ->
        detalle.getProducto().getAgricultor() != null
            && detalle.getProducto().getAgricultor().getUsuario() != null
            && detalle.getProducto().getAgricultor().getUsuario().getId()
                .equals(usuarioAgricultorId));
    if (!propio) {
      throw new TransicionPedidoInvalidaException();
    }
    Despacho despacho = despachoRepository.findByPedido_Id(pedidoId)
        .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
    despacho.marcarEnRuta();
    workflowService.salirARuta(pedido);
    return DespachoResponse.from(despachoRepository.save(despacho));
  }
}
