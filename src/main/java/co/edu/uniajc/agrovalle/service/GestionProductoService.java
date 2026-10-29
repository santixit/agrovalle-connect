package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.EstadoProductoRequest;
import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.api.dto.PublicarProductoRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.EstadoProducto;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.Parcela;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import co.edu.uniajc.agrovalle.repository.ParcelaRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Handles offer creation and owner-authorized state changes. */
@Service
@Transactional
public class GestionProductoService {

  private final ProductoRepository productoRepository;
  private final AgricultorRepository agricultorRepository;
  private final ParcelaRepository parcelaRepository;
  private final FincaRepository fincaRepository;

  public GestionProductoService(ProductoRepository productoRepository,
      AgricultorRepository agricultorRepository, ParcelaRepository parcelaRepository,
      FincaRepository fincaRepository) {
    this.productoRepository = productoRepository;
    this.agricultorRepository = agricultorRepository;
    this.parcelaRepository = parcelaRepository;
    this.fincaRepository = fincaRepository;
  }

  public ProductoResponse publicar(Long usuarioId, PublicarProductoRequest request) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    if (request.parcelaId() != null && request.fincaId() != null) {
      throw new IllegalArgumentException("Indica una parcela, no una parcela y finca a la vez");
    }
    Parcela parcela = null;
    if (request.parcelaId() != null) {
      parcela = parcelaRepository.findByIdAndAgricultorResponsableActual_Id(
          request.parcelaId(), agricultor.getId())
          .orElseThrow(() -> new IllegalArgumentException(
              "La parcela no existe o no está a tu cargo"));
    } else if (request.fincaId() != null) {
      if (parcelaRepository == null) {
        fincaRepository.findByIdAndAgricultor_Id(request.fincaId(), agricultor.getId())
            .orElseThrow(() -> new FincaNoEncontradaException(request.fincaId()));
        throw new IllegalArgumentException("La finca no tiene una parcela disponible");
      }
      var parcelas = parcelaRepository.findByFinca_IdAndAgricultorResponsableActual_IdOrderByCodigoAsc(
          request.fincaId(), agricultor.getId());
      if (parcelas.size() != 1) {
        throw new IllegalArgumentException(parcelas.isEmpty()
            ? "La finca no existe o no tienes parcelas a tu cargo"
            : "Selecciona una parcela específica para publicar el producto");
      }
      parcela = parcelas.get(0);
    }
    Producto producto;
    if (parcela != null) {
      if (request.municipio() != null && !request.municipio().isBlank()
          && !parcela.getFinca().getMunicipio().equalsIgnoreCase(request.municipio().trim())) {
        throw new IllegalArgumentException("El municipio debe coincidir con el de la finca");
      }
      producto = Producto.enParcela(parcela, request.nombre().trim(), request.categoria().trim(),
          request.cantidadKg(), request.precioPorKg(), request.fechaCosecha());
    } else {
      if (request.municipio() == null || request.municipio().isBlank()) {
        throw new IllegalArgumentException("Escribe el municipio para un producto sin finca");
      }
      producto = Producto.sinFinca(agricultor, request.municipio().trim(),
          request.nombre().trim(), request.categoria().trim(), request.cantidadKg(),
          request.precioPorKg(), request.fechaCosecha());
    }
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
