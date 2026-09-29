package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.PrecioRegionalResponse;
import co.edu.uniajc.agrovalle.domain.TransaccionPrecio;
import co.edu.uniajc.agrovalle.repository.TransaccionPrecioRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PrecioRegionalService {
  private static final int MAX_TRANSACCIONES = 50;
  private final TransaccionPrecioRepository repository;

  public PrecioRegionalService(TransaccionPrecioRepository repository) {
    this.repository = repository;
  }

  public PrecioRegionalResponse consultar(String categoria) {
    List<TransaccionPrecio> transacciones = repository.buscarRecientesPorCategoria(
        categoria.trim(), PageRequest.of(0, MAX_TRANSACCIONES));
    BigDecimal promedio = transacciones.stream().map(TransaccionPrecio::getPrecioPorKg)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    if (!transacciones.isEmpty()) {
      promedio = promedio.divide(BigDecimal.valueOf(transacciones.size()), 2, RoundingMode.HALF_UP);
    } else {
      promedio = null;
    }
    return new PrecioRegionalResponse(categoria.trim(), "COP", promedio, transacciones.size());
  }
}
