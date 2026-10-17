package co.edu.uniajc.agrovalle.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class DescuentoMayoristaService {
  public BigDecimal calcularPrecioUnitario(BigDecimal kilos, BigDecimal precioUnitario) {
    if (kilos.compareTo(new BigDecimal("100")) > 0) {
      return precioUnitario.multiply(new BigDecimal("0.95")).setScale(2, RoundingMode.HALF_UP);
    }
    return precioUnitario;
  }
}
