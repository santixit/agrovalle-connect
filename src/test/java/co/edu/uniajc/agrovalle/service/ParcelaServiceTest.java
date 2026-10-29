package co.edu.uniajc.agrovalle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import co.edu.uniajc.agrovalle.api.dto.RegistroParcelaRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Finca;
import co.edu.uniajc.agrovalle.domain.Parcela;
import co.edu.uniajc.agrovalle.domain.RolUsuario;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import co.edu.uniajc.agrovalle.repository.ParcelaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/** Tests for parcel ownership and registration rules. */
@ExtendWith(MockitoExtension.class)
class ParcelaServiceTest {

  @Mock
  private AgricultorRepository agricultorRepository;

  @Mock
  private FincaRepository fincaRepository;

  @Mock
  private ParcelaRepository parcelaRepository;

  @InjectMocks
  private ParcelaService parcelaService;

  @Test
  void miasDevuelveParcelasDelAgricultor() {
    Agricultor responsable = agricultor(11L, "Ana");
    Finca finca = finca(responsable, 21L);
    Parcela parcela = new Parcela(finca, "Norte", responsable);
    when(parcelaRepository
        .findByAgricultorResponsableActual_Usuario_IdOrderByFinca_NombreAscCodigoAsc(7L))
        .thenReturn(List.of(parcela));

    var resultado = parcelaService.mias(7L);

    assertEquals(1, resultado.size());
    assertEquals("Norte", resultado.get(0).codigo());
    assertEquals("La Esperanza", resultado.get(0).finca());
  }

  @Test
  void crearPermiteAlPropietarioYNormalizaElCodigo() {
    Agricultor propietario = agricultor(11L, "Ana");
    Finca finca = finca(propietario, 21L);
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(propietario));
    when(fincaRepository.findById(21L)).thenReturn(Optional.of(finca));
    when(parcelaRepository.existsByFinca_IdAndCodigo(21L, "Norte")).thenReturn(false);
    when(parcelaRepository.save(any(Parcela.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    var resultado = parcelaService.crear(7L, 21L, new RegistroParcelaRequest(" Norte "));

    assertEquals("Norte", resultado.codigo());
    assertEquals(11L, resultado.agricultorResponsableId());
    verify(parcelaRepository, never()).existsByFinca_IdAndAgricultorResponsableActual_Id(
        21L, 11L);
    verify(parcelaRepository).existsByFinca_IdAndCodigo(21L, "Norte");
  }

  @Test
  void crearPermiteAlResponsableDeOtraParcelaDeLaFinca() {
    Agricultor responsable = agricultor(11L, "Ana");
    Agricultor propietario = agricultor(12L, "Luis");
    Finca finca = finca(propietario, 21L);
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(responsable));
    when(fincaRepository.findById(21L)).thenReturn(Optional.of(finca));
    when(parcelaRepository.existsByFinca_IdAndAgricultorResponsableActual_Id(21L, 11L))
        .thenReturn(true);
    when(parcelaRepository.existsByFinca_IdAndCodigo(21L, "Sur")).thenReturn(false);
    when(parcelaRepository.save(any(Parcela.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    var resultado = parcelaService.crear(7L, 21L, new RegistroParcelaRequest("Sur"));

    assertEquals("Sur", resultado.codigo());
    verify(parcelaRepository).existsByFinca_IdAndCodigo(21L, "Sur");
  }

  @Test
  void crearRechazaAgricultorInexistente() {
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.empty());

    assertThrows(AgricultorNoEncontradoException.class,
        () -> parcelaService.crear(7L, 21L, new RegistroParcelaRequest("Norte")));

    verifyNoMoreInteractions(fincaRepository, parcelaRepository);
  }

  @Test
  void crearRechazaFincaInexistente() {
    when(agricultorRepository.findByUsuario_Id(7L))
        .thenReturn(Optional.of(agricultor(11L, "Ana")));
    when(fincaRepository.findById(21L)).thenReturn(Optional.empty());

    assertThrows(FincaNoEncontradaException.class,
        () -> parcelaService.crear(7L, 21L, new RegistroParcelaRequest("Norte")));

    verifyNoMoreInteractions(parcelaRepository);
  }

  @Test
  void crearRechazaFincaAjenaSinParcelasACargo() {
    Agricultor solicitante = agricultor(11L, "Ana");
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(solicitante));
    when(fincaRepository.findById(21L)).thenReturn(Optional.of(
        finca(agricultor(12L, "Luis"), 21L)));
    when(parcelaRepository.existsByFinca_IdAndAgricultorResponsableActual_Id(21L, 11L))
        .thenReturn(false);

    assertThrows(OfertaNoPropiaException.class,
        () -> parcelaService.crear(7L, 21L, new RegistroParcelaRequest("Norte")));

    verify(parcelaRepository, never()).save(any(Parcela.class));
  }

  @Test
  void crearRechazaCodigoGeneralReservado() {
    Agricultor propietario = agricultor(11L, "Ana");
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(propietario));
    when(fincaRepository.findById(21L)).thenReturn(Optional.of(finca(propietario, 21L)));

    assertThrows(IllegalStateException.class,
        () -> parcelaService.crear(7L, 21L, new RegistroParcelaRequest(" general ")));

    verify(parcelaRepository, never()).existsByFinca_IdAndCodigo(21L, "general");
    verify(parcelaRepository, never()).save(any(Parcela.class));
  }

  @Test
  void crearRechazaCodigoYaUsadoEnLaFinca() {
    Agricultor propietario = agricultor(11L, "Ana");
    when(agricultorRepository.findByUsuario_Id(7L)).thenReturn(Optional.of(propietario));
    when(fincaRepository.findById(21L)).thenReturn(Optional.of(finca(propietario, 21L)));
    when(parcelaRepository.existsByFinca_IdAndCodigo(21L, "Norte")).thenReturn(true);

    assertThrows(IllegalStateException.class,
        () -> parcelaService.crear(7L, 21L, new RegistroParcelaRequest("Norte")));

    verify(parcelaRepository, never()).save(any(Parcela.class));
  }

  private Agricultor agricultor(Long id, String nombre) {
    Agricultor agricultor = new Agricultor(
        new Usuario(nombre.toLowerCase() + "@example.test", "hash", RolUsuario.AGRICULTOR),
        nombre, "TEST-" + id, "Dagua");
    ReflectionTestUtils.setField(agricultor, "id", id);
    return agricultor;
  }

  private Finca finca(Agricultor agricultor, Long id) {
    Finca finca = new Finca(agricultor, "La Esperanza", "Dagua", "Vereda El Salado");
    ReflectionTestUtils.setField(finca, "id", id);
    return finca;
  }
}
