package co.edu.uniajc.agrovalle.service;

import java.math.BigDecimal;

/** Strategy for calculating a demonstration freight rate by destination municipality. */
public interface CalculadorFleteStrategy {
  String getMunicipio();

  BigDecimal calcular(BigDecimal pesoKg);
}
