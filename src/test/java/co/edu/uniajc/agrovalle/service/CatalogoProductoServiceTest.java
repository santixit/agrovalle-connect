package co.edu.uniajc.agrovalle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.domain.Producto;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Isolated tests for catalog filtering rules. */
@ExtendWith(MockitoExtension.class)
class CatalogoProductoServiceTest {

  @Mock
  private ProductoRepository productoRepository;

  @InjectMocks
  private CatalogoProductoService catalogoProductoService;

  @Test
  void buscarCombinaYNormalizaMunicipioYCategoria() {
    Producto producto = new Producto("Mango", "Frutas", "Dagua", true);
    when(productoRepository.findByActivoTrueAndMunicipioIgnoreCaseAndCategoriaIgnoreCase(
        "Dagua", "Frutas")).thenReturn(List.of(producto));

    List<ProductoResponse> respuesta = catalogoProductoService.buscar(" Dagua ", " Frutas ");

    assertEquals(1, respuesta.size());
    assertEquals("Mango", respuesta.get(0).nombre());
    assertEquals("Dagua", respuesta.get(0).municipio());
    verify(productoRepository).findByActivoTrueAndMunicipioIgnoreCaseAndCategoriaIgnoreCase(
        "Dagua", "Frutas");
    verifyNoMoreInteractions(productoRepository);
  }

  @Test
  void buscarSinFiltrosOmiteFiltrosEnRepositorio() {
    when(productoRepository.findByActivoTrue()).thenReturn(List.of());

    List<ProductoResponse> respuesta = catalogoProductoService.buscar(" ", null);

    assertEquals(List.of(), respuesta);
    verify(productoRepository).findByActivoTrue();
    verifyNoMoreInteractions(productoRepository);
  }

  @Test
  void consultarDetalleRechazaOfertaInactiva() {
    when(productoRepository.findById(42L))
        .thenReturn(java.util.Optional.of(new Producto("Mango", "Frutas", "Dagua", false)));

    assertThrows(ProductoNoEncontradoException.class,
        () -> catalogoProductoService.consultarDetalle(42L));

    verify(productoRepository).findById(42L);
    verifyNoMoreInteractions(productoRepository);
  }
}
