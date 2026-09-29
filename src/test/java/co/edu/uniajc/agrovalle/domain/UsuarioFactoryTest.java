package co.edu.uniajc.agrovalle.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UsuarioFactoryTest {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final UsuarioFactory factory = new UsuarioFactory(passwordEncoder);

  @Test
  void creaCuentaAgricultorConHashYRolCorrecto() {
    verificaCuenta(factory.crearAgricultor("campo@example.com", "ClaveSegura2026"),
        RolUsuario.AGRICULTOR);
  }

  @Test
  void creaCuentaCompradorConHashYRolCorrecto() {
    verificaCuenta(factory.crearComprador("compra@example.com", "ClaveSegura2026"),
        RolUsuario.COMPRADOR);
  }

  @Test
  void creaCuentaAdministradorConHashYRolCorrecto() {
    verificaCuenta(factory.crearAdministrador("admin@example.com", "ClaveSegura2026"),
        RolUsuario.ADMIN);
  }

  private void verificaCuenta(Usuario usuario, RolUsuario rolEsperado) {
    assertEquals(rolEsperado, usuario.getRol());
    assertNotEquals("ClaveSegura2026", usuario.getPasswordHash());
    assertTrue(passwordEncoder.matches("ClaveSegura2026", usuario.getPasswordHash()));
  }
}
