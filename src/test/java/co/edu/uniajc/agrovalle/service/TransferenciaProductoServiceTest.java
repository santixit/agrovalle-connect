package co.edu.uniajc.agrovalle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Finca;
import co.edu.uniajc.agrovalle.domain.Parcela;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.RolUsuario;
import co.edu.uniajc.agrovalle.domain.TransferenciaProducto;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.ParcelaRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import co.edu.uniajc.agrovalle.repository.TransferenciaProductoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/** Tests for product ownership transfer and history rules. */
@ExtendWith(MockitoExtension.class)
class TransferenciaProductoServiceTest {

  @Mock
  private AgricultorRepository agricultorRepository;

  @Mock
  private ProductoRepository productoRepository;

  @Mock
  private ParcelaRepository parcelaRepository;

  @Mock
  private TransferenciaProductoRepository transferenciaRepository;

  @InjectMocks
  private TransferenciaProductoService transferenciaService;

  @Test
  void transferirParcelaCambiaResponsableYRegistraCadaProducto() {
    Agricultor anterior = agricultor(11L, "Ana");
    Agricultor nuevo = agricultor(12L, "Luis");
    Parcela parcela = parcela(anterior, 31L);
    Producto mango = productoEnParcela(parcela, "Mango");
    Producto lulo = productoEnParcela(parcela, "Lulo");
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(anterior));
    when(agricultorRepository.findById(12L)).thenReturn(Optional.of(nuevo));
    when(productoRepository.buscarParaActualizar(41L)).thenReturn(Optional.of(mango));
    when(parcelaRepository.buscarParaActualizar(31L)).thenReturn(Optional.of(parcela));
    when(productoRepository.findByParcelaId(31L)).thenReturn(List.of(mango, lulo));
    when(transferenciaRepository.save(any(TransferenciaProducto.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    var resultado = transferenciaService.transferir(7L, 41L, 12L);

    assertEquals(2, resultado.size());
    assertEquals("Ana", resultado.get(0).agricultorAnterior());
    assertEquals("Luis", resultado.get(1).agricultorNuevo());
    assertEquals(nuevo, parcela.getAgricultorResponsableActual());
    verify(transferenciaRepository, org.mockito.Mockito.times(2))
        .save(any(TransferenciaProducto.class));
  }

  @Test
  void transferirProductoIndependienteCambiaTitularYRegistraEvento() {
    Agricultor anterior = agricultor(11L, "Ana");
    Agricultor nuevo = agricultor(12L, "Luis");
    Producto producto = Producto.sinFinca(anterior, "Dagua", "Mango", "Frutas",
        BigDecimal.TEN, BigDecimal.ONE, LocalDate.now());
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(anterior));
    when(agricultorRepository.findById(12L)).thenReturn(Optional.of(nuevo));
    when(productoRepository.buscarParaActualizar(41L)).thenReturn(Optional.of(producto));
    when(transferenciaRepository.save(any(TransferenciaProducto.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    var resultado = transferenciaService.transferir(7L, 41L, 12L);

    assertEquals(1, resultado.size());
    assertEquals(nuevo, producto.getAgricultor());
    assertEquals("Dagua", producto.getMunicipio());
  }

  @Test
  void transferirRechazaActorInexistente() {
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.empty());

    assertThrows(AgricultorNoEncontradoException.class,
        () -> transferenciaService.transferir(7L, 41L, 12L));

    verifyNoInteractions(productoRepository, parcelaRepository, transferenciaRepository);
  }

  @Test
  void transferirRechazaNuevoAgricultorInexistente() {
    when(agricultorRepository.findByUsuario_Id(7L))
        .thenReturn(Optional.of(agricultor(11L, "Ana")));
    when(agricultorRepository.findById(12L)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class,
        () -> transferenciaService.transferir(7L, 41L, 12L));

    verifyNoInteractions(productoRepository, parcelaRepository, transferenciaRepository);
  }

  @Test
  void transferirRechazaProductoInexistente() {
    when(agricultorRepository.findByUsuario_Id(7L))
        .thenReturn(Optional.of(agricultor(11L, "Ana")));
    when(agricultorRepository.findById(12L))
        .thenReturn(Optional.of(agricultor(12L, "Luis")));
    when(productoRepository.buscarParaActualizar(41L)).thenReturn(Optional.empty());

    assertThrows(ProductoNoEncontradoException.class,
        () -> transferenciaService.transferir(7L, 41L, 12L));

    verifyNoInteractions(parcelaRepository, transferenciaRepository);
  }

  @Test
  void transferirRechazaParcelaQueYaNoExiste() {
    Agricultor actor = agricultor(11L, "Ana");
    Agricultor nuevo = agricultor(12L, "Luis");
    Producto producto = productoEnParcela(parcela(actor, 31L), "Mango");
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(actor));
    when(agricultorRepository.findById(12L)).thenReturn(Optional.of(nuevo));
    when(productoRepository.buscarParaActualizar(41L)).thenReturn(Optional.of(producto));
    when(parcelaRepository.buscarParaActualizar(31L)).thenReturn(Optional.empty());

    assertThrows(IllegalStateException.class,
        () -> transferenciaService.transferir(7L, 41L, 12L));

    verifyNoInteractions(transferenciaRepository);
  }

  @Test
  void transferirRechazaParcelaQueNoEstaACargoDelActor() {
    Agricultor actor = agricultor(11L, "Ana");
    Agricultor responsable = agricultor(13L, "Marta");
    Agricultor nuevo = agricultor(12L, "Luis");
    Parcela parcela = parcela(responsable, 31L);
    Producto producto = productoEnParcela(parcela, "Mango");
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(actor));
    when(agricultorRepository.findById(12L)).thenReturn(Optional.of(nuevo));
    when(productoRepository.buscarParaActualizar(41L)).thenReturn(Optional.of(producto));
    when(parcelaRepository.buscarParaActualizar(31L)).thenReturn(Optional.of(parcela));

    assertThrows(OfertaNoPropiaException.class,
        () -> transferenciaService.transferir(7L, 41L, 12L));

    verify(productoRepository, never()).findByParcelaId(31L);
    verifyNoInteractions(transferenciaRepository);
  }

  @Test
  void transferirRechazaProductoIndependienteDeOtroAgricultor() {
    Agricultor actor = agricultor(11L, "Ana");
    Agricultor anterior = agricultor(13L, "Marta");
    Agricultor nuevo = agricultor(12L, "Luis");
    Producto producto = Producto.sinFinca(anterior, "Dagua", "Mango", "Frutas",
        BigDecimal.TEN, BigDecimal.ONE, LocalDate.now());
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(actor));
    when(agricultorRepository.findById(12L)).thenReturn(Optional.of(nuevo));
    when(productoRepository.buscarParaActualizar(41L)).thenReturn(Optional.of(producto));

    assertThrows(OfertaNoPropiaException.class,
        () -> transferenciaService.transferir(7L, 41L, 12L));

    verifyNoInteractions(parcelaRepository, transferenciaRepository);
  }

  @Test
  void historialRechazaProductoInexistente() {
    when(productoRepository.existsById(41L)).thenReturn(false);

    assertThrows(ProductoNoEncontradoException.class,
        () -> transferenciaService.historial(41L));

    verifyNoInteractions(transferenciaRepository);
  }

  @Test
  void historialDevuelveLosEventosEnOrdenDelRepositorio() {
    Agricultor anterior = agricultor(11L, "Ana");
    Agricultor nuevo = agricultor(12L, "Luis");
    Producto producto = Producto.sinFinca(anterior, "Dagua", "Mango", "Frutas",
        BigDecimal.TEN, BigDecimal.ONE, LocalDate.now());
    TransferenciaProducto transferencia = new TransferenciaProducto(producto, anterior, nuevo);
    when(productoRepository.existsById(41L)).thenReturn(true);
    when(transferenciaRepository.findByProducto_IdOrderByFechaTransferenciaAscIdAsc(41L))
        .thenReturn(List.of(transferencia));

    var resultado = transferenciaService.historial(41L);

    assertEquals(1, resultado.size());
    assertEquals("Ana", resultado.get(0).agricultorAnterior());
    assertEquals("Luis", resultado.get(0).agricultorNuevo());
  }

  private Agricultor agricultor(Long id, String nombre) {
    Agricultor agricultor = new Agricultor(
        new Usuario(nombre.toLowerCase() + "@example.test", "hash", RolUsuario.AGRICULTOR),
        nombre, "TEST-" + id, "Dagua");
    ReflectionTestUtils.setField(agricultor, "id", id);
    return agricultor;
  }

  private Parcela parcela(Agricultor agricultor, Long id) {
    Finca finca = new Finca(agricultor, "La Esperanza", "Dagua", "Vereda El Salado");
    ReflectionTestUtils.setField(finca, "id", 21L);
    Parcela parcela = new Parcela(finca, "Norte", agricultor);
    ReflectionTestUtils.setField(parcela, "id", id);
    return parcela;
  }

  private Producto productoEnParcela(Parcela parcela, String nombre) {
    return Producto.enParcela(parcela, nombre, "Frutas", BigDecimal.TEN,
        BigDecimal.ONE, LocalDate.now());
  }
}
