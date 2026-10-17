package co.edu.uniajc.agrovalle.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class DescuentoMayoristaService implements CalculadorDescuentoAgricola {
  private static final BigDecimal LIMITE_KILOS_DESCUENTO = new BigDecimal("100");
  private static final BigDecimal DESCUENTO_MAYORISTA_CINCO_PORCIENTO =
      new BigDecimal("0.05");

  @Override
  public BigDecimal calcularPrecioUnitario(BigDecimal kilos, BigDecimal precioUnitario) {
    if (kilos.compareTo(LIMITE_KILOS_DESCUENTO) > 0) {
      BigDecimal factorDescuento = BigDecimal.ONE.subtract(
          DESCUENTO_MAYORISTA_CINCO_PORCIENTO);
      return precioUnitario.multiply(factorDescuento).setScale(2, RoundingMode.HALF_UP);
    }
    return precioUnitario;
  }
}
