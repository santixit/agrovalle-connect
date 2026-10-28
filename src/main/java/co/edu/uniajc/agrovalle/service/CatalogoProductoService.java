package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.OfertaDetalleResponse;
import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Provides read-only catalog searches and offer details. */
@Service
@Transactional(readOnly = true)
public class CatalogoProductoService {

  private final ProductoRepository productoRepository;

  public CatalogoProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
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
    return productos.stream().map(ProductoResponse::from).toList();
  }

  public List<ProductoResponse> buscarDelAgricultor(Long usuarioId) {
    return productoRepository.findByAgricultor_Usuario_IdOrderByCreadoEnDesc(usuarioId)
        .stream().map(ProductoResponse::from).toList();
  }

  public OfertaDetalleResponse consultarDetalle(Long id) {
    Producto producto = productoRepository.findById(id).filter(Producto::isActivo)
        .orElseThrow(() -> new ProductoNoEncontradoException(id));
    return OfertaDetalleResponse.from(producto);
  }

  private String normalizarFiltro(String filtro) {
    return filtro == null || filtro.isBlank() ? null : filtro.trim();
  }
}
