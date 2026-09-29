package co.edu.uniajc.agrovalle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Comprador;
import co.edu.uniajc.agrovalle.domain.Contacto;
import co.edu.uniajc.agrovalle.domain.DetallePedido;
import co.edu.uniajc.agrovalle.domain.Despacho;
import co.edu.uniajc.agrovalle.domain.EstadoContacto;
import co.edu.uniajc.agrovalle.domain.EstadoDespacho;
import co.edu.uniajc.agrovalle.domain.EstadoPedido;
import co.edu.uniajc.agrovalle.domain.EstadoProducto;
import co.edu.uniajc.agrovalle.domain.EventoTrazabilidad;
import co.edu.uniajc.agrovalle.domain.Favorito;
import co.edu.uniajc.agrovalle.domain.Finca;
import co.edu.uniajc.agrovalle.domain.Notificacion;
import co.edu.uniajc.agrovalle.domain.Pedido;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.RolUsuario;
import co.edu.uniajc.agrovalle.domain.TipoNotificacion;
import co.edu.uniajc.agrovalle.domain.TransaccionPrecio;
import co.edu.uniajc.agrovalle.domain.Usuario;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/** Verifies persistence mappings for the expanded project domain model. */
@SpringBootTest
@Transactional
class ModeloIntegradorPersistenceTests {

  @Autowired
  private EntityManager entityManager;

  @Test
  void persisteLasRelacionesPrincipalesDelDominio() {
    Usuario usuarioAgricultor = new Usuario("ana@example.test", "hash-seguro", RolUsuario.AGRICULTOR);
    Usuario usuarioComprador = new Usuario("tienda@example.test", "otro-hash", RolUsuario.COMPRADOR);
    entityManager.persist(usuarioAgricultor);
    entityManager.persist(usuarioComprador);

    Agricultor agricultor = new Agricultor(usuarioAgricultor, "Ana Ruiz", "TEST-101", "Dagua");
    Comprador comprador = new Comprador(usuarioComprador, "Tienda Central", "3000000000", "Restaurante");
    entityManager.persist(agricultor);
    entityManager.persist(comprador);

    Finca finca = new Finca(agricultor, "La Esperanza", "Dagua", "Vereda El Carmen");
    entityManager.persist(finca);
    Producto producto = new Producto(agricultor, finca, "Mango", "Frutas", "Dagua",
        new BigDecimal("20.00"), new BigDecimal("4500.00"), LocalDate.now().plusDays(2));
    entityManager.persist(producto);

    Pedido pedido = new Pedido(comprador);
    pedido.agregarDetalle(new DetallePedido(
        producto, new BigDecimal("5.00"), new BigDecimal("4500.00")));
    pedido.cambiarEstado(EstadoPedido.CONFIRMADO);
    entityManager.persist(pedido);
    Despacho despacho = new Despacho(
        pedido, LocalDate.now().plusDays(3), "08:00-12:00", "Dagua-Cali");
    entityManager.persist(despacho);
    EventoTrazabilidad evento = new EventoTrazabilidad(
        pedido, EstadoPedido.CONFIRMADO, "La reserva fue confirmada");
    entityManager.persist(evento);

    Contacto contacto = new Contacto(comprador, agricultor, producto, "Consulta de disponibilidad");
    entityManager.persist(contacto);
    Favorito favorito = new Favorito(comprador, producto);
    entityManager.persist(favorito);
    Notificacion notificacion = new Notificacion(
        usuarioAgricultor, TipoNotificacion.CONTACTO, "Un comprador consultó el producto");
    entityManager.persist(notificacion);
    TransaccionPrecio transaccion = new TransaccionPrecio(
        producto, new BigDecimal("5.00"), new BigDecimal("4500.00"));
    entityManager.persist(transaccion);

    entityManager.flush();
    Long agricultorId = agricultor.getId();
    Long compradorId = comprador.getId();
    Long productoId = producto.getId();
    Long pedidoId = pedido.getId();
    Long despachoId = despacho.getId();
    Long eventoId = evento.getId();
    Long contactoId = contacto.getId();
    Long favoritoId = favorito.getId();
    Long notificacionId = notificacion.getId();
    Long transaccionId = transaccion.getId();
    entityManager.clear();

    assertEquals("ana@example.test", entityManager.find(Usuario.class, usuarioAgricultor.getId())
        .getCorreo());
    assertEquals(RolUsuario.AGRICULTOR,
        entityManager.find(Usuario.class, usuarioAgricultor.getId()).getRol());
    assertTrue(entityManager.find(Usuario.class, usuarioAgricultor.getId()).isActivo());
    assertNotNull(entityManager.find(Usuario.class, usuarioAgricultor.getId()).getCreadoEn());
    assertEquals("hash-seguro",
        entityManager.find(Usuario.class, usuarioAgricultor.getId()).getPasswordHash());

    Agricultor agricultorLeido = entityManager.find(Agricultor.class, agricultorId);
    assertEquals("Ana Ruiz", agricultorLeido.getNombre());
    assertEquals("TEST-101", agricultorLeido.getCedula());
    assertEquals("Dagua", agricultorLeido.getMunicipio());
    assertEquals("ana@example.test", agricultorLeido.getUsuario().getCorreo());

    Comprador compradorLeido = entityManager.find(Comprador.class, compradorId);
    assertEquals("Tienda Central", compradorLeido.getNombre());
    assertEquals("3000000000", compradorLeido.getTelefono());
    assertEquals("Restaurante", compradorLeido.getTipoComercio());
    assertNotNull(compradorLeido.getCreadoEn());

    Producto productoLeido = entityManager.find(Producto.class, productoId);
    assertEquals("Mango", productoLeido.getNombre());
    assertEquals("Frutas", productoLeido.getCategoria());
    assertEquals("Dagua", productoLeido.getMunicipio());
    assertEquals(agricultorId, productoLeido.getAgricultor().getId());
    assertEquals("La Esperanza", productoLeido.getFinca().getNombre());
    assertEquals(new BigDecimal("20.00"), productoLeido.getCantidadKg());
    assertEquals(new BigDecimal("4500.00"), productoLeido.getPrecioPorKg());
    assertEquals(LocalDate.now().plusDays(2), productoLeido.getFechaCosecha());
    assertEquals(EstadoProducto.DISPONIBLE, productoLeido.getEstado());
    assertTrue(productoLeido.isActivo());
    productoLeido.cambiarEstado(EstadoProducto.AGOTADO);
    assertFalse(productoLeido.isActivo());
    productoLeido.cambiarEstado(EstadoProducto.DISPONIBLE);
    assertTrue(productoLeido.isActivo());

    Pedido pedidoLeido = entityManager.find(Pedido.class, pedidoId);
    assertEquals(EstadoPedido.CONFIRMADO, pedidoLeido.getEstado());
    assertNotNull(pedidoLeido.getCreadoEn());
    assertNotNull(pedidoLeido.getActualizadoEn());
    assertEquals(1, pedidoLeido.getDetalles().size());
    DetallePedido detalleLeido = pedidoLeido.getDetalles().get(0);
    assertEquals(new BigDecimal("5.00"), detalleLeido.getCantidadKg());
    assertEquals(new BigDecimal("4500.00"), detalleLeido.getPrecioPorKg());
    assertEquals("Mango", detalleLeido.getProducto().getNombre());
    assertEquals(pedidoId, detalleLeido.getPedido().getId());
    assertNotNull(detalleLeido.getId());

    Despacho despachoLeido = entityManager.find(Despacho.class, despachoId);
    assertEquals(EstadoDespacho.PROGRAMADO, despachoLeido.getEstado());
    assertEquals(LocalDate.now().plusDays(3), despachoLeido.getFechaProgramada());
    assertEquals("08:00-12:00", despachoLeido.getFranjaHoraria());
    assertEquals("Dagua-Cali", despachoLeido.getRuta());
    assertNotNull(despachoLeido.getCreadoEn());
    assertEquals(pedidoId, despachoLeido.getPedido().getId());

    EventoTrazabilidad eventoLeido = entityManager.find(EventoTrazabilidad.class, eventoId);
    assertEquals(EstadoPedido.CONFIRMADO, eventoLeido.getEstado());
    assertEquals("La reserva fue confirmada", eventoLeido.getDescripcion());
    assertNotNull(eventoLeido.getOcurridoEn());
    assertEquals(pedidoId, eventoLeido.getPedido().getId());

    Contacto contactoLeido = entityManager.find(Contacto.class, contactoId);
    assertEquals("Consulta de disponibilidad", contactoLeido.getMensaje());
    assertEquals(EstadoContacto.NUEVO, contactoLeido.getEstado());
    assertNotNull(contactoLeido.getCreadoEn());
    assertEquals(compradorId, contactoLeido.getComprador().getId());
    assertEquals(agricultorId, contactoLeido.getAgricultor().getId());
    assertEquals(productoId, contactoLeido.getProducto().getId());

    Favorito favoritoLeido = entityManager.find(Favorito.class, favoritoId);
    assertNotNull(favoritoLeido.getCreadoEn());
    assertEquals(compradorId, favoritoLeido.getComprador().getId());
    assertEquals(productoId, favoritoLeido.getProducto().getId());

    Notificacion notificacionLeida = entityManager.find(Notificacion.class, notificacionId);
    assertEquals(TipoNotificacion.CONTACTO, notificacionLeida.getTipo());
    assertEquals("Un comprador consultó el producto", notificacionLeida.getMensaje());
    assertFalse(notificacionLeida.isLeida());
    assertNotNull(notificacionLeida.getCreadaEn());
    assertEquals(usuarioAgricultor.getId(), notificacionLeida.getUsuario().getId());

    TransaccionPrecio transaccionLeida = entityManager.find(TransaccionPrecio.class, transaccionId);
    assertEquals(new BigDecimal("5.00"), transaccionLeida.getCantidadKg());
    assertEquals(new BigDecimal("4500.00"), transaccionLeida.getPrecioPorKg());
    assertNotNull(transaccionLeida.getOcurridaEn());
    assertEquals(productoId, transaccionLeida.getProducto().getId());
  }
}
