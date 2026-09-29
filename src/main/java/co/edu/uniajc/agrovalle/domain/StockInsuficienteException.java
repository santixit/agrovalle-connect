package co.edu.uniajc.agrovalle.domain;

import java.math.BigDecimal;

public class StockInsuficienteException extends RuntimeException {
  public StockInsuficienteException(BigDecimal disponible) {
    super("Cantidad solicitada supera el inventario disponible: " + disponible + " kg");
  }
}
