package co.edu.uniajc.agrovalle.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/** Academic example rate: $1,200 COP/kg plus a $5,000 COP road base charge. */
@Component("Dagua")
public class FleteDaguaStrategy implements CalculadorFleteStrategy {
  private static final BigDecimal TARIFA_POR_KILO = new BigDecimal("1200");
  private static final BigDecimal CARGO_BASE = new BigDecimal("5000");

  @Override
  public String getMunicipio() {
    return "Dagua";
  }

  @Override
  public BigDecimal calcular(BigDecimal pesoKg) {
    validarPeso(pesoKg);
    return pesoKg.multiply(TARIFA_POR_KILO).add(CARGO_BASE);
  }

  private void validarPeso(BigDecimal pesoKg) {
    if (pesoKg == null || pesoKg.signum() <= 0) {
      throw new IllegalArgumentException("El peso del despacho debe ser mayor que cero");
    }
  }
}
