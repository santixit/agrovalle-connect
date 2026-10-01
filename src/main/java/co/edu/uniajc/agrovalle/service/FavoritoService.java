package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.FavoritoResponse;
import co.edu.uniajc.agrovalle.domain.Comprador;
import co.edu.uniajc.agrovalle.domain.Favorito;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.repository.CompradorRepository;
import co.edu.uniajc.agrovalle.repository.FavoritoRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoritoService {
  private final CompradorRepository compradorRepository;
  private final ProductoRepository productoRepository;
  private final FavoritoRepository favoritoRepository;

  public FavoritoService(CompradorRepository compradorRepository,
      ProductoRepository productoRepository, FavoritoRepository favoritoRepository) {
    this.compradorRepository = compradorRepository;
    this.productoRepository = productoRepository;
    this.favoritoRepository = favoritoRepository;
  }

  @Transactional
  public void agregar(Long usuarioId, Long productoId) {
    Comprador comprador = buscarComprador(usuarioId);
    Producto producto = productoRepository.findById(productoId).filter(Producto::isActivo)
        .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
    if (!favoritoRepository.existsByComprador_IdAndProducto_Id(comprador.getId(), productoId)) {
      favoritoRepository.save(new Favorito(comprador, producto));
    }
  }

  @Transactional
  public void quitar(Long usuarioId, Long productoId) {
    Comprador comprador = buscarComprador(usuarioId);
    favoritoRepository.findByComprador_IdAndProducto_Id(comprador.getId(), productoId)
        .ifPresent(favoritoRepository::delete);
  }

  @Transactional(readOnly = true)
  public List<FavoritoResponse> listar(Long usuarioId) {
    Comprador comprador = buscarComprador(usuarioId);
    return favoritoRepository.findByComprador_IdOrderByCreadoEnDesc(comprador.getId()).stream()
        .map(FavoritoResponse::from).toList();
  }

  private Comprador buscarComprador(Long usuarioId) {
    return compradorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(CompradorNoEncontradoException::new);
  }
}
