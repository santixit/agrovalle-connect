package co.edu.uniajc.agrovalle.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/** Selects a freight strategy by destination municipality. */
@Service
public class CalculadorFleteService {
  private final Map<String, CalculadorFleteStrategy> estrategias;

  public CalculadorFleteService(Map<String, CalculadorFleteStrategy> estrategias) {
    this.estrategias = estrategias.values().stream().collect(Collectors.toUnmodifiableMap(
        estrategia -> normalizar(estrategia.getMunicipio()), Function.identity()));
  }

  public BigDecimal calcular(String municipio, BigDecimal pesoKg) {
    if (municipio == null || municipio.isBlank()) {
      throw new IllegalArgumentException("El municipio de destino es obligatorio");
    }
    CalculadorFleteStrategy estrategia = estrategias.get(normalizar(municipio));
    if (estrategia == null) {
      throw new MunicipioFleteNoSoportadoException(municipio);
    }
    return estrategia.calcular(pesoKg).setScale(2, RoundingMode.HALF_UP);
  }

  private static String normalizar(String municipio) {
    return municipio.trim().toLowerCase(java.util.Locale.ROOT)
        .replace("á", "a").replace("é", "e").replace("í", "i")
        .replace("ó", "o").replace("ú", "u");
  }
}
