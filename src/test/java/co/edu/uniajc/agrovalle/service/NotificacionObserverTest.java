package co.edu.uniajc.agrovalle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.edu.uniajc.agrovalle.domain.ContactoRegistradoEvent;
import co.edu.uniajc.agrovalle.domain.EstadoPedido;
import co.edu.uniajc.agrovalle.domain.Notificacion;
import co.edu.uniajc.agrovalle.domain.PedidoEstadoCambiadoEvent;
import co.edu.uniajc.agrovalle.domain.RolUsuario;
import co.edu.uniajc.agrovalle.domain.TipoNotificacion;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.repository.NotificacionRepository;
import co.edu.uniajc.agrovalle.repository.UsuarioRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class NotificacionObserverTest {
  private final UsuarioRepository usuarioRepository =
      org.mockito.Mockito.mock(UsuarioRepository.class);
  private final NotificacionRepository notificacionRepository =
      org.mockito.Mockito.mock(NotificacionRepository.class);
  private final NotificacionObserver observer = new NotificacionObserver(
      usuarioRepository, notificacionRepository);

  @Test
  void convierteEventoDeContactoEnNotificacionParaElAgricultor() {
    Usuario agricultor = new Usuario("agricultor@example.com", "hash", RolUsuario.AGRICULTOR);
    when(usuarioRepository.findById(10L)).thenReturn(Optional.of(agricultor));
    ContactoRegistradoEvent evento = new ContactoRegistradoEvent(10L, 25L, "Mango");

    observer.alRegistrarContacto(evento);

    ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
    verify(notificacionRepository).save(captor.capture());
    assertEquals(TipoNotificacion.CONTACTO, captor.getValue().getTipo());
    assertEquals(agricultor, captor.getValue().getUsuario());
    assertEquals("Nuevo contacto 25 por la oferta Mango", captor.getValue().getMensaje());
  }

  @Test
  void convierteCambioDePedidoEnNotificacionParaElComprador() {
    Usuario comprador = new Usuario("comprador@example.com", "hash", RolUsuario.COMPRADOR);
    when(usuarioRepository.findById(11L)).thenReturn(Optional.of(comprador));
    PedidoEstadoCambiadoEvent evento = new PedidoEstadoCambiadoEvent(11L, 30L,
        EstadoPedido.CONFIRMADO, "Reserva confirmada");

    observer.alCambiarPedido(evento);

    ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
    verify(notificacionRepository).save(captor.capture());
    assertEquals(TipoNotificacion.PEDIDO, captor.getValue().getTipo());
    assertEquals(comprador, captor.getValue().getUsuario());
    assertEquals("Pedido 30: CONFIRMADO. Reserva confirmada", captor.getValue().getMensaje());
  }

  @Test
  void noGuardaNotificacionSiElDestinatarioYaNoExiste() {
    when(usuarioRepository.findById(12L)).thenReturn(Optional.empty());

    observer.alCambiarPedido(new PedidoEstadoCambiadoEvent(12L, 31L,
        EstadoPedido.ENTREGADO, "Entrega completada"));

    verify(notificacionRepository, never()).save(any(Notificacion.class));
  }
}
