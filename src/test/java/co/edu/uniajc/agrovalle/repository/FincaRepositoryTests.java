package co.edu.uniajc.agrovalle.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Finca;
import co.edu.uniajc.agrovalle.domain.RolUsuario;
import co.edu.uniajc.agrovalle.domain.Usuario;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;

/** Verifies owner-scoped and active-farm queries used by HU-06. */
@DataJpaTest
class FincaRepositoryTests {

  @Autowired
  private FincaRepository fincaRepository;

  @Autowired
  private AgricultorRepository agricultorRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private EntityManager entityManager;

  @Test
  void listaSoloFincasActivasDelAgricultorOrdenadasPorNombre() {
    Agricultor agricultor = crearAgricultor("ana@example.test", "TEST-301");
    Agricultor otroAgricultor = crearAgricultor("luis@example.test", "TEST-302");

    Finca fincaZeta = fincaRepository.save(
        new Finca(agricultor, "Finca Zeta", "Dagua", "Vereda Zeta"));
    Finca fincaAlfa = fincaRepository.save(
        new Finca(agricultor, "Finca Alfa", "Dagua", "Vereda Alfa"));
    Finca fincaInactiva = fincaRepository.save(
        new Finca(agricultor, "Finca Inactiva", "Dagua", "Vereda Inactiva"));
    fincaRepository.save(new Finca(otroAgricultor,
        "Finca de otro agricultor", "Dagua", "Vereda Ajena"));
    ReflectionTestUtils.setField(fincaInactiva, "activa", false);
    entityManager.flush();
    entityManager.clear();

    List<Finca> fincas = fincaRepository
        .findByAgricultor_IdAndActivaTrueOrderByNombreAsc(agricultor.getId());

    assertEquals(List.of(fincaAlfa.getId(), fincaZeta.getId()),
        fincas.stream().map(Finca::getId).toList());
  }

  @Test
  void buscaFincaPorIdSoloParaSuPropietario() {
    Agricultor propietario = crearAgricultor("propietario@example.test", "TEST-303");
    Agricultor otroAgricultor = crearAgricultor("ajeno@example.test", "TEST-304");
    Finca finca = fincaRepository.save(
        new Finca(propietario, "Finca Propia", "Dagua", "Vereda Propia"));
    entityManager.flush();

    assertTrue(fincaRepository.findByIdAndAgricultor_Id(finca.getId(), propietario.getId())
        .isPresent());
    assertFalse(fincaRepository.findByIdAndAgricultor_Id(finca.getId(), otroAgricultor.getId())
        .isPresent());
  }

  private Agricultor crearAgricultor(String correo, String cedula) {
    Usuario usuario = usuarioRepository.save(
        new Usuario(correo, "hash-de-prueba", RolUsuario.AGRICULTOR));
    return agricultorRepository.save(new Agricultor(usuario, "Agricultor de prueba", cedula,
        "Dagua"));
  }
}

