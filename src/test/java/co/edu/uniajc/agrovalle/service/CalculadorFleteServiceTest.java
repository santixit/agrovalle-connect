package co.edu.uniajc.agrovalle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CalculadorFleteServiceTest {
  private final CalculadorFleteService calculador = new CalculadorFleteService(Map.of(
      "Dagua", new FleteDaguaStrategy(),
      "Buga", new FleteBugaStrategy(),
      "Tulua", new FleteTuluaStrategy()));

  @Test
  void calculaTarifaDeDaguaConCargoBase() {
    assertEquals(new BigDecimal("65000.00"), calculador.calcular("Dagua", new BigDecimal("50")));
  }

  @Test
  void seleccionaTarifaPorMunicipioIgnorandoMayusculasYTildes() {
    assertEquals(new BigDecimal("40000.00"), calculador.calcular("BUGA", new BigDecimal("50")));
    assertEquals(new BigDecimal("47500.00"), calculador.calcular("Tulua", new BigDecimal("50")));
  }

  @Test
  void rechazaMunicipiosSinEstrategiaYPesosInvalidos() {
    assertThrows(MunicipioFleteNoSoportadoException.class,
        () -> calculador.calcular("Cali", new BigDecimal("50")));
    assertThrows(IllegalArgumentException.class,
        () -> calculador.calcular("Dagua", BigDecimal.ZERO));
  }
}
