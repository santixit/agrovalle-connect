package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.EstadoProductoRequest;
import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.api.dto.PublicarProductoRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.EstadoProducto;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles offer creation and owner-authorized state changes. */
@Service
@Transactional
public class GestionProductoService {

  private final ProductoRepository productoRepository;
  private final AgricultorRepository agricultorRepository;
  private final FincaRepository fincaRepository;

  public GestionProductoService(ProductoRepository productoRepository,
      AgricultorRepository agricultorRepository, FincaRepository fincaRepository) {
    this.productoRepository = productoRepository;
    this.agricultorRepository = agricultorRepository;
    this.fincaRepository = fincaRepository;
  }

  public ProductoResponse publicar(Long usuarioId, PublicarProductoRequest request) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    co.edu.uniajc.agrovalle.domain.Finca finca = request.fincaId() == null ? null
        : fincaRepository.findByIdAndAgricultor_Id(request.fincaId(), agricultor.getId())
            .orElseThrow(() -> new FincaNoEncontradaException(request.fincaId()));
    Producto producto = new Producto(agricultor, finca, request.nombre().trim(),
        request.categoria().trim(), request.municipio().trim(), request.cantidadKg(),
        request.precioPorKg(), request.fechaCosecha());
    return ProductoResponse.from(productoRepository.save(producto));
  }

  public ProductoResponse actualizarEstado(Long usuarioId, Long productoId,
      EstadoProductoRequest request) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    Producto producto = productoRepository.buscarParaActualizar(productoId)
        .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
    if (producto.getAgricultor() == null
        || !producto.getAgricultor().getId().equals(agricultor.getId())) {
      throw new OfertaNoPropiaException(productoId);
    }
    if (request.estado() == EstadoProducto.RESERVADO) {
      throw new EstadoProductoInvalidoException();
    }
    if (request.estado() == EstadoProducto.DISPONIBLE
        && (producto.getCantidadKg() == null || producto.getCantidadKg().signum() == 0)) {
      throw new EstadoProductoInvalidoException();
    }
    producto.cambiarEstado(request.estado());
    return ProductoResponse.from(productoRepository.save(producto));
  }
}
