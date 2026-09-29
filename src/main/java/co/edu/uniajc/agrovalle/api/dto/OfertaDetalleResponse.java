package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Producto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

public record OfertaDetalleResponse(Long id, String nombre, String categoria,
    String municipio, @JsonProperty("cantidad_disponible_kg") BigDecimal cantidadDisponibleKg,
    @JsonProperty("precio_por_kg") BigDecimal precioPorKg,
    @JsonProperty("fecha_cosecha") LocalDate fechaCosecha,
    String agricultor, String finca) {
  public static OfertaDetalleResponse from(Producto producto) {
    String nombreAgricultor = producto.getAgricultor() == null
        ? null : producto.getAgricultor().getNombre();
    String nombreFinca = producto.getFinca() == null ? null : producto.getFinca().getNombre();
    return new OfertaDetalleResponse(producto.getId(), producto.getNombre(), producto.getCategoria(),
        producto.getMunicipio(), producto.getCantidadKg(), producto.getPrecioPorKg(),
        producto.getFechaCosecha(), nombreAgricultor, nombreFinca);
  }
}
