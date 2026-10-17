package co.edu.uniajc.agrovalle.service;

import java.math.BigDecimal;

/** Strategy for calculating agricultural wholesale discounts. */
public interface CalculadorDescuentoAgricola {
  BigDecimal calcularPrecioUnitario(BigDecimal kilos, BigDecimal precioUnitario);
}
