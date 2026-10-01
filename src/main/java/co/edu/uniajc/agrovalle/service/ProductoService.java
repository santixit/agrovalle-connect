package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Provides filtered catalog queries. */
@Service
@Transactional(readOnly = true)
public class ProductoService {

  private final ProductoRepository productoRepository;
  private final AgricultorRepository agricultorRepository;
  private final FincaRepository fincaRepository;

  public ProductoService(ProductoRepository productoRepository,
      AgricultorRepository agricultorRepository, FincaRepository fincaRepository) {
    this.productoRepository = productoRepository;
    this.agricultorRepository = agricultorRepository;
    this.fincaRepository = fincaRepository;
  }

  @Transactional
  public ProductoResponse publicar(Long usuarioId,
      co.edu.uniajc.agrovalle.api.dto.PublicarProductoRequest request) {
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

  public List<ProductoResponse> buscar(String municipio, String categoria) {
    String municipioNormalizado = normalizarFiltro(municipio);
    String categoriaNormalizada = normalizarFiltro(categoria);
    List<Producto> productos;
    if (municipioNormalizado != null && categoriaNormalizada != null) {
      productos = productoRepository
          .findByActivoTrueAndMunicipioIgnoreCaseAndCategoriaIgnoreCase(
              municipioNormalizado, categoriaNormalizada);
    } else if (municipioNormalizado != null) {
      productos = productoRepository.findByActivoTrueAndMunicipioIgnoreCase(municipioNormalizado);
    } else if (categoriaNormalizada != null) {
      productos = productoRepository.findByActivoTrueAndCategoriaIgnoreCase(categoriaNormalizada);
    } else {
      productos = productoRepository.findByActivoTrue();
    }
    return productos
        .stream().map(ProductoResponse::from).toList();
  }

  private String normalizarFiltro(String filtro) {
    return filtro == null || filtro.isBlank() ? null : filtro.trim();
  }

  @Transactional(readOnly = true)
  public co.edu.uniajc.agrovalle.api.dto.OfertaDetalleResponse consultarDetalle(Long id) {
    Producto producto = productoRepository.findById(id).filter(Producto::isActivo)
        .orElseThrow(() -> new ProductoNoEncontradoException(id));
    return co.edu.uniajc.agrovalle.api.dto.OfertaDetalleResponse.from(producto);
  }

  @Transactional
  public ProductoResponse actualizarEstado(Long usuarioId, Long productoId,
      co.edu.uniajc.agrovalle.api.dto.EstadoProductoRequest request) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    Producto producto = productoRepository.buscarParaActualizar(productoId)
        .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
    if (producto.getAgricultor() == null
        || !producto.getAgricultor().getId().equals(agricultor.getId())) {
      throw new OfertaNoPropiaException(productoId);
    }
    if (request.estado() == co.edu.uniajc.agrovalle.domain.EstadoProducto.RESERVADO) {
      throw new EstadoProductoInvalidoException();
    }
    if (request.estado() == co.edu.uniajc.agrovalle.domain.EstadoProducto.DISPONIBLE
        && (producto.getCantidadKg() == null || producto.getCantidadKg().signum() == 0)) {
      throw new EstadoProductoInvalidoException();
    }
    producto.cambiarEstado(request.estado());
    return ProductoResponse.from(productoRepository.save(producto));
  }
}
