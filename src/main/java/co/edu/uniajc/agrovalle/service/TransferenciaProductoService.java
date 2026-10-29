package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.TransferenciaProductoResponse;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Parcela;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.TransferenciaProducto;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.ParcelaRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import co.edu.uniajc.agrovalle.repository.TransferenciaProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferenciaProductoService {
  private final AgricultorRepository agricultorRepository;
  private final ProductoRepository productoRepository;
  private final ParcelaRepository parcelaRepository;
  private final TransferenciaProductoRepository transferenciaRepository;

  public TransferenciaProductoService(AgricultorRepository agricultorRepository,
      ProductoRepository productoRepository, ParcelaRepository parcelaRepository,
      TransferenciaProductoRepository transferenciaRepository) {
    this.agricultorRepository = agricultorRepository;
    this.productoRepository = productoRepository;
    this.parcelaRepository = parcelaRepository;
    this.transferenciaRepository = transferenciaRepository;
  }

  @Transactional
  public List<TransferenciaProductoResponse> transferir(Long usuarioId, Long productoId,
      Long nuevoAgricultorId) {
    Agricultor actor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    Agricultor nuevo = agricultorRepository.findById(nuevoAgricultorId)
        .orElseThrow(() -> new IllegalArgumentException("El nuevo agricultor no existe"));
    Producto producto = productoRepository.buscarParaActualizar(productoId)
        .orElseThrow(() -> new ProductoNoEncontradoException(productoId));
    Parcela parcela = producto.getParcela();
    List<Producto> afectados;
    Agricultor anterior;
    if (parcela != null) {
      parcela = parcelaRepository.buscarParaActualizar(parcela.getId())
          .orElseThrow(() -> new IllegalStateException("La parcela ya no está disponible"));
      anterior = parcela.getAgricultorResponsableActual();
      if (!anterior.getId().equals(actor.getId())) {
        throw new OfertaNoPropiaException(productoId);
      }
      afectados = productoRepository.findByParcelaId(parcela.getId());
      parcela.transferirResponsabilidadA(nuevo);
    } else {
      anterior = producto.getAgricultor();
      if (anterior == null || !anterior.getId().equals(actor.getId())) {
        throw new OfertaNoPropiaException(productoId);
      }
      producto.getUbicacion().transferirTitularSinFinca(nuevo);
      afectados = List.of(producto);
    }
    return afectados.stream().map(afectado -> TransferenciaProductoResponse.from(
        transferenciaRepository.save(new TransferenciaProducto(afectado, anterior, nuevo))))
        .toList();
  }

  @Transactional(readOnly = true)
  public List<TransferenciaProductoResponse> historial(Long productoId) {
    if (!productoRepository.existsById(productoId)) {
      throw new ProductoNoEncontradoException(productoId);
    }
    return transferenciaRepository.findByProducto_IdOrderByFechaTransferenciaAscIdAsc(productoId)
        .stream().map(TransferenciaProductoResponse::from).toList();
  }
}
