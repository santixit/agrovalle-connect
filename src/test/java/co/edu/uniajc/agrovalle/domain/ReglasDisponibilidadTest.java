package co.edu.uniajc.agrovalle.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ReglasDisponibilidadTest {

  private final ReglasDisponibilidad reglas = new ReglasDisponibilidad();

  @Test
  void aceptaReservaIgualAlInventarioDisponible() {
    assertDoesNotThrow(() -> reglas.validarReserva(new BigDecimal("5.00"),
        new BigDecimal("5.00")));
  }

  @Test
  void rechazaReservaCuandoSuperaElInventario() {
    assertThrows(StockInsuficienteException.class, () -> reglas.validarReserva(
        new BigDecimal("5.01"), new BigDecimal("5.00")));
  }

  @Test
  void rechazaCantidadSolicitadaNoPositiva() {
    assertThrows(IllegalArgumentException.class, () -> reglas.validarReserva(
        BigDecimal.ZERO, new BigDecimal("5.00")));
  }

  @Test
  void rechazaInventarioNegativo() {
    assertThrows(IllegalArgumentException.class, () -> reglas.validarReserva(
        BigDecimal.ONE, new BigDecimal("-1.00")));
  }
}
