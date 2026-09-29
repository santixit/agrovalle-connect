package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.ReservaRequest;
import co.edu.uniajc.agrovalle.api.dto.ReservaResponse;
import co.edu.uniajc.agrovalle.api.dto.ReservaCarritoRequest;
import co.edu.uniajc.agrovalle.api.dto.CarritoReservaResponse;
import co.edu.uniajc.agrovalle.api.dto.PedidoResumenResponse;
import co.edu.uniajc.agrovalle.domain.Comprador;
import co.edu.uniajc.agrovalle.domain.DetallePedido;
import co.edu.uniajc.agrovalle.domain.EstadoPedido;
import co.edu.uniajc.agrovalle.domain.EventoTrazabilidad;
import co.edu.uniajc.agrovalle.domain.Pedido;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.ReglasDisponibilidad;
import co.edu.uniajc.agrovalle.domain.StockInsuficienteException;
import co.edu.uniajc.agrovalle.domain.EstadoProducto;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.api.dto.ReservaItemRequest;
import co.edu.uniajc.agrovalle.repository.CompradorRepository;
import co.edu.uniajc.agrovalle.repository.EventoTrazabilidadRepository;
import co.edu.uniajc.agrovalle.repository.PedidoRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ReservaService {
  private final CompradorRepository compradorRepository;
  private final ProductoRepository productoRepository;
  private final PedidoRepository pedidoRepository;
  private final EventoTrazabilidadRepository eventoRepository;
  private final ReglasDisponibilidad reglasDisponibilidad;

  public ReservaService(CompradorRepository compradorRepository,
      ProductoRepository productoRepository, PedidoRepository pedidoRepository,
      EventoTrazabilidadRepository eventoRepository, ReglasDisponibilidad reglasDisponibilidad) {
    this.compradorRepository = compradorRepository;
    this.productoRepository = productoRepository;
    this.pedidoRepository = pedidoRepository;
    this.eventoRepository = eventoRepository;
    this.reglasDisponibilidad = reglasDisponibilidad;
  }

  @Transactional
  public ReservaResponse reservar(Long usuarioId, ReservaRequest request) {
    Comprador comprador = compradorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(CompradorNoEncontradoException::new);
    Producto producto = productoRepository.buscarParaActualizar(request.productoId())
        .orElseThrow(() -> new ProductoNoEncontradoException(request.productoId()));
    if (!producto.isActivo()) {
      throw new StockInsuficienteException(producto.getCantidadKg() == null
          ? java.math.BigDecimal.ZERO : producto.getCantidadKg());
    }
    reglasDisponibilidad.validarReserva(request.cantidadKg(), producto.getCantidadKg());
    producto.actualizarCantidad(producto.getCantidadKg().subtract(request.cantidadKg()));
    Pedido pedido = new Pedido(comprador);
    pedido.agregarDetalle(new DetallePedido(producto, request.cantidadKg(),
        producto.getPrecioPorKg()));
    Pedido guardado = pedidoRepository.save(pedido);
    eventoRepository.save(new EventoTrazabilidad(guardado, EstadoPedido.PENDIENTE,
        "Reserva creada para la oferta " + producto.getNombre()));
    if (producto.getEstado() != EstadoProducto.AGOTADO) {
      producto.cambiarEstado(EstadoProducto.DISPONIBLE);
    }
    return new ReservaResponse(guardado.getId(), guardado.getEstado().name(), producto.getId(),
        request.cantidadKg(), producto.getPrecioPorKg());
  }

  @Transactional
  public CarritoReservaResponse reservarCarrito(Long usuarioId,
      ReservaCarritoRequest request) {
    Comprador comprador = compradorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(CompradorNoEncontradoException::new);
    Map<Long, BigDecimal> cantidades = new TreeMap<>();
    for (ReservaItemRequest item : request.items()) {
      cantidades.merge(item.productoId(), item.cantidadKg(), BigDecimal::add);
    }

    Map<Long, List<Producto>> porAgricultor = new TreeMap<>();
    for (Map.Entry<Long, BigDecimal> entrada : cantidades.entrySet()) {
      Producto producto = productoRepository.buscarParaActualizar(entrada.getKey())
          .orElseThrow(() -> new ProductoNoEncontradoException(entrada.getKey()));
      if (!producto.isActivo()) {
        throw new StockInsuficienteException(producto.getCantidadKg() == null
            ? BigDecimal.ZERO : producto.getCantidadKg());
      }
      reglasDisponibilidad.validarReserva(entrada.getValue(), producto.getCantidadKg());
      Agricultor agricultor = producto.getAgricultor();
      if (agricultor == null || agricultor.getUsuario() == null
          || agricultor.getUsuario().getId() == null) {
        throw new TransicionPedidoInvalidaException();
      }
      porAgricultor.computeIfAbsent(agricultor.getUsuario().getId(), ignored -> new ArrayList<>())
          .add(producto);
    }

    List<PedidoResumenResponse> pedidos = new ArrayList<>();
    for (List<Producto> ofertas : porAgricultor.values()) {
      Pedido pedido = new Pedido(comprador);
      for (Producto producto : ofertas) {
        BigDecimal cantidad = cantidades.get(producto.getId());
        producto.actualizarCantidad(producto.getCantidadKg().subtract(cantidad));
        pedido.agregarDetalle(new DetallePedido(producto, cantidad, producto.getPrecioPorKg()));
      }
      Pedido guardado = pedidoRepository.save(pedido);
      eventoRepository.save(new EventoTrazabilidad(guardado, EstadoPedido.PENDIENTE,
          "Reserva creada desde el carrito de compra"));
      pedidos.add(PedidoResumenResponse.from(guardado));
    }
    return new CarritoReservaResponse(List.copyOf(pedidos));
  }
}
