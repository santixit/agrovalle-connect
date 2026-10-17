package co.edu.uniajc.agrovalle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class DescuentoMayoristaServiceTest {
  private final DescuentoMayoristaService service = new DescuentoMayoristaService();

  @Test
  void noAplicaDescuentoCuandoLaCompraEsDeCienKilos() {
    assertEquals(new BigDecimal("2000.00"),
        service.calcularPrecioUnitario(new BigDecimal("100"), new BigDecimal("2000.00")));
  }

  @Test
  void aplicaCincoPorCientoCuandoLaCompraSuperaCienKilos() {
    assertEquals(new BigDecimal("1900.00"),
        service.calcularPrecioUnitario(new BigDecimal("100.01"), new BigDecimal("2000.00")));
  }
}
