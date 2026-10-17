package co.edu.uniajc.agrovalle.service;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class DescuentoMayoristaService {
  public BigDecimal calcularPrecioUnitario(BigDecimal kilos, BigDecimal precioUnitario) {
    return precioUnitario;
  }
}
