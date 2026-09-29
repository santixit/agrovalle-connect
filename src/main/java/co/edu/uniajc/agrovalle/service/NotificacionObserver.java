package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.domain.ContactoRegistradoEvent;
import co.edu.uniajc.agrovalle.domain.Notificacion;
import co.edu.uniajc.agrovalle.domain.PedidoEstadoCambiadoEvent;
import co.edu.uniajc.agrovalle.domain.TipoNotificacion;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.repository.NotificacionRepository;
import co.edu.uniajc.agrovalle.repository.UsuarioRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacionObserver {
  private final UsuarioRepository usuarioRepository;
  private final NotificacionRepository notificacionRepository;

  public NotificacionObserver(UsuarioRepository usuarioRepository,
      NotificacionRepository notificacionRepository) {
    this.usuarioRepository = usuarioRepository;
    this.notificacionRepository = notificacionRepository;
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void alRegistrarContacto(ContactoRegistradoEvent event) {
    usuarioRepository.findById(event.usuarioAgricultorId()).ifPresent(usuario ->
        guardarNotificacion(usuario, event));
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void alCambiarPedido(PedidoEstadoCambiadoEvent event) {
    usuarioRepository.findById(event.usuarioCompradorId()).ifPresent(usuario ->
        notificacionRepository.save(new Notificacion(usuario, TipoNotificacion.PEDIDO,
            "Pedido " + event.pedidoId() + ": " + event.estado() + ". " + event.detalle())));
  }

  private void guardarNotificacion(Usuario usuario, ContactoRegistradoEvent event) {
    String mensaje = "Nuevo contacto " + event.contactoId()
        + " por la oferta " + event.nombreProducto();
    notificacionRepository.save(new Notificacion(usuario, TipoNotificacion.CONTACTO, mensaje));
  }
}
