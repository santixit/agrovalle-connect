package co.edu.uniajc.agrovalle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import co.edu.uniajc.agrovalle.api.dto.EstadoProductoRequest;
import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.api.dto.PublicarProductoRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.EstadoProducto;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.RolUsuario;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/** Isolated tests for offer creation and owner-authorized state changes. */
@ExtendWith(MockitoExtension.class)
class GestionProductoServiceTest {

  @Mock
  private ProductoRepository productoRepository;

  @Mock
  private AgricultorRepository agricultorRepository;

  @Mock
  private FincaRepository fincaRepository;

  @InjectMocks
  private GestionProductoService gestionProductoService;

  @Test
  void publicarNormalizaCamposYGuardaOferta() {
    Agricultor agricultor = new Agricultor(
        new Usuario("ana@example.test", "hash", RolUsuario.AGRICULTOR),
        "Ana Ruiz", "TEST-1", "Dagua");
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(agricultor));
    when(productoRepository.save(any(Producto.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    PublicarProductoRequest solicitud = new PublicarProductoRequest(" Mango ", " Frutas ",
        " Dagua ", new BigDecimal("12.00"), new BigDecimal("4000.00"),
        LocalDate.now().plusDays(1), null);

    ProductoResponse respuesta = gestionProductoService.publicar(7L, solicitud);

    assertEquals("Mango", respuesta.nombre());
    assertEquals("Frutas", respuesta.categoria());
    assertEquals("Dagua", respuesta.municipio());
    verify(agricultorRepository).findByUsuario_Id(7L);
    verify(productoRepository).save(any(Producto.class));
    verifyNoInteractions(fincaRepository);
  }

  @Test
  void publicarRechazaFincaQueNoPerteneceAlAgricultor() {
    Agricultor agricultor = new Agricultor(
        new Usuario("ana@example.test", "hash", RolUsuario.AGRICULTOR),
        "Ana Ruiz", "TEST-1", "Dagua");
    ReflectionTestUtils.setField(agricultor, "id", 12L);
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(agricultor));
    when(fincaRepository.findByIdAndAgricultor_Id(4L, 12L)).thenReturn(Optional.empty());
    PublicarProductoRequest solicitud = new PublicarProductoRequest("Mango", "Frutas", "Dagua",
        BigDecimal.ONE, BigDecimal.ONE, LocalDate.now().plusDays(1), 4L);

    assertThrows(FincaNoEncontradaException.class,
        () -> gestionProductoService.publicar(7L, solicitud));

    verify(productoRepository, never()).save(any(Producto.class));
  }

  @Test
  void actualizarEstadoRechazaOfertaDeOtroAgricultor() {
    Agricultor propietario = new Agricultor("Ana Ruiz", "TEST-1", "Dagua");
    ReflectionTestUtils.setField(propietario, "id", 12L);
    Agricultor solicitante = new Agricultor("Luis Pérez", "TEST-2", "Palmira");
    ReflectionTestUtils.setField(solicitante, "id", 13L);
    Producto producto = new Producto(propietario, null, "Mango", "Frutas", "Dagua",
        BigDecimal.TEN, BigDecimal.ONE, LocalDate.now());
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(solicitante));
    when(productoRepository.buscarParaActualizar(20L)).thenReturn(Optional.of(producto));

    assertThrows(OfertaNoPropiaException.class, () -> gestionProductoService.actualizarEstado(
        7L, 20L, new EstadoProductoRequest(EstadoProducto.AGOTADO)));

    verify(productoRepository, never()).save(any(Producto.class));
  }

  @Test
  void actualizarEstadoNoPublicaOfertaSinInventario() {
    Agricultor agricultor = new Agricultor("Ana Ruiz", "TEST-1", "Dagua");
    ReflectionTestUtils.setField(agricultor, "id", 12L);
    Producto producto = new Producto(agricultor, null, "Mango", "Frutas", "Dagua",
        BigDecimal.ZERO, BigDecimal.ONE, LocalDate.now());
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(agricultor));
    when(productoRepository.buscarParaActualizar(20L)).thenReturn(Optional.of(producto));

    assertThrows(EstadoProductoInvalidoException.class,
        () -> gestionProductoService.actualizarEstado(7L, 20L,
            new EstadoProductoRequest(EstadoProducto.DISPONIBLE)));

    verify(productoRepository, never()).save(any(Producto.class));
  }
}
