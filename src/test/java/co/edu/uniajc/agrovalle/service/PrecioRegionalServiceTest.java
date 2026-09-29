package co.edu.uniajc.agrovalle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.edu.uniajc.agrovalle.api.dto.PrecioRegionalResponse;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.domain.TransaccionPrecio;
import co.edu.uniajc.agrovalle.repository.TransaccionPrecioRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

class PrecioRegionalServiceTest {

  private final TransaccionPrecioRepository repository =
      org.mockito.Mockito.mock(TransaccionPrecioRepository.class);
  private final PrecioRegionalService service = new PrecioRegionalService(repository);

  @Test
  void calculaPromedioAritmeticoDeTransaccionesRecientes() {
    Producto producto = new Producto("Cafe", "Cafe", "Dagua", true);
    when(repository.buscarRecientesPorCategoria(eq("Cafe"), any(LocalDateTime.class),
        eq(PageRequest.of(0, 50))))
        .thenReturn(List.of(new TransaccionPrecio(producto, BigDecimal.ONE,
                new BigDecimal("100.00")),
            new TransaccionPrecio(producto, BigDecimal.ONE, new BigDecimal("150.00"))));

    PrecioRegionalResponse respuesta = service.consultar(" Cafe ");

    assertEquals("Cafe", respuesta.categoria());
    assertEquals("COP", respuesta.moneda());
    assertEquals(new BigDecimal("125.00"), respuesta.promedioPorKg());
    assertEquals(2, respuesta.transaccionesAnalizadas());
    verify(repository).buscarRecientesPorCategoria(eq("Cafe"), any(LocalDateTime.class),
        eq(PageRequest.of(0, 50)));
  }

  @Test
  void noInventaPrecioCuandoNoHayTransacciones() {
    when(repository.buscarRecientesPorCategoria(eq("Frutas"), any(LocalDateTime.class),
        eq(PageRequest.of(0, 50))))
        .thenReturn(List.of());

    PrecioRegionalResponse respuesta = service.consultar("Frutas");

    assertNull(respuesta.promedioPorKg());
    assertEquals(0, respuesta.transaccionesAnalizadas());
  }
}
