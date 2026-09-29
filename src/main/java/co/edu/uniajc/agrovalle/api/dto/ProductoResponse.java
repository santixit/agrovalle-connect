package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Producto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Product fields exposed by the catalog query. */
public record ProductoResponse(Long id, String nombre, String categoria,
    String municipio, @JsonProperty("cantidad_kg") BigDecimal cantidadKg,
    @JsonProperty("precio_por_kg") BigDecimal precioPorKg,
    @JsonProperty("fecha_cosecha") LocalDate fechaCosecha) {

  public static ProductoResponse from(Producto producto) {
    return new ProductoResponse(producto.getId(), producto.getNombre(),
        producto.getCategoria(), producto.getMunicipio(), producto.getCantidadKg(),
        producto.getPrecioPorKg(), producto.getFechaCosecha());
  }
}
