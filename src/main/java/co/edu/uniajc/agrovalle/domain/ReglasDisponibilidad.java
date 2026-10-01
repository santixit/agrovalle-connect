package co.edu.uniajc.agrovalle.domain;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/** Stateless inventory policy; Spring manages one shared singleton instance. */
@Component
public class ReglasDisponibilidad {
  public void validarReserva(BigDecimal cantidadSolicitada, BigDecimal cantidadDisponible) {
    if (cantidadSolicitada == null || cantidadSolicitada.signum() <= 0) {
      throw new IllegalArgumentException("La cantidad a reservar debe ser mayor que cero");
    }
    if (cantidadDisponible != null && cantidadDisponible.signum() < 0) {
      throw new IllegalArgumentException("El inventario disponible no puede ser negativo");
    }
    if (cantidadDisponible == null || cantidadDisponible.compareTo(cantidadSolicitada) < 0) {
      throw new StockInsuficienteException(cantidadDisponible == null
          ? BigDecimal.ZERO : cantidadDisponible);
    }
  }
}
