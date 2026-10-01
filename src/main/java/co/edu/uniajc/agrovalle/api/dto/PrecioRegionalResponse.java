package co.edu.uniajc.agrovalle.api.dto;

import java.math.BigDecimal;

public record PrecioRegionalResponse(String categoria, String moneda,
    BigDecimal promedioPorKg, int transaccionesAnalizadas) { }
