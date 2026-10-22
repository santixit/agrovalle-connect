package co.edu.uniajc.agrovalle.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/** Academic example rate: $800 COP/kg. */
@Component("Buga")
public class FleteBugaStrategy implements CalculadorFleteStrategy {
  private static final BigDecimal TARIFA_POR_KILO = new BigDecimal("800");

  @Override
  public String getMunicipio() {
    return "Buga";
  }

  @Override
  public BigDecimal calcular(BigDecimal pesoKg) {
    validarPeso(pesoKg);
    return pesoKg.multiply(TARIFA_POR_KILO);
  }

  private void validarPeso(BigDecimal pesoKg) {
    if (pesoKg == null || pesoKg.signum() <= 0) {
      throw new IllegalArgumentException("El peso del despacho debe ser mayor que cero");
    }
  }
}
