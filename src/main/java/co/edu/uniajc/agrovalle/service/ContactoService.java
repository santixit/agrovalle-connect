package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.ContactoRequest;
import co.edu.uniajc.agrovalle.api.dto.ContactoResponse;
import co.edu.uniajc.agrovalle.api.dto.ContactoBandejaResponse;
import co.edu.uniajc.agrovalle.domain.Comprador;
import co.edu.uniajc.agrovalle.domain.Contacto;
import co.edu.uniajc.agrovalle.domain.ContactoRegistradoEvent;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.repository.CompradorRepository;
import co.edu.uniajc.agrovalle.repository.ContactoRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

@Service
public class ContactoService {
  private final CompradorRepository compradorRepository;
  private final ProductoRepository productoRepository;
  private final ContactoRepository contactoRepository;
  private final ApplicationEventPublisher eventPublisher;

  public ContactoService(CompradorRepository compradorRepository,
      ProductoRepository productoRepository, ContactoRepository contactoRepository,
      ApplicationEventPublisher eventPublisher) {
    this.compradorRepository = compradorRepository;
    this.productoRepository = productoRepository;
    this.contactoRepository = contactoRepository;
    this.eventPublisher = eventPublisher;
  }

  @Transactional
  public ContactoResponse contactar(Long usuarioId, ContactoRequest request) {
    Comprador comprador = compradorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(CompradorNoEncontradoException::new);
    Producto producto = productoRepository.findById(request.productoId())
        .filter(Producto::isActivo).orElseThrow(
            () -> new ProductoNoEncontradoException(request.productoId()));
    if (producto.getAgricultor() == null) {
      throw new IllegalStateException("La oferta no tiene un agricultor asociado");
    }
    Contacto guardado = contactoRepository.save(new Contacto(comprador,
        producto.getAgricultor(), producto, request.mensaje().trim()));
    if (producto.getAgricultor().getUsuario() != null) {
      eventPublisher.publishEvent(new ContactoRegistradoEvent(
          producto.getAgricultor().getUsuario().getId(), guardado.getId(), producto.getNombre()));
    }
    return ContactoResponse.from(guardado);
  }

  @Transactional(readOnly = true)
  public List<ContactoBandejaResponse> recibidos(Long agricultorUsuarioId) {
    return contactoRepository.findByAgricultor_Usuario_IdOrderByCreadoEnDesc(agricultorUsuarioId)
        .stream().map(ContactoBandejaResponse::from).toList();
  }

  @Transactional(readOnly = true)
  public List<ContactoBandejaResponse> enviados(Long compradorUsuarioId) {
    return contactoRepository.findByComprador_Usuario_IdOrderByCreadoEnDesc(compradorUsuarioId)
        .stream().map(ContactoBandejaResponse::from).toList();
  }

  @Transactional
  public ContactoBandejaResponse responder(Long agricultorUsuarioId, Long contactoId,
      String mensaje) {
    Contacto contacto = contactoRepository
        .findByIdAndAgricultor_Usuario_Id(contactoId, agricultorUsuarioId)
        .orElseThrow(() -> new ContactoNoEncontradoException(contactoId));
    contacto.responder(mensaje.trim());
    return ContactoBandejaResponse.from(contacto);
  }
}
