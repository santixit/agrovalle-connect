package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Producto;

/** Product fields exposed by the catalog query. */
public record ProductoResponse(Long id, String nombre, String categoria,
    String municipio) {

  public static ProductoResponse from(Producto producto) {
    return new ProductoResponse(producto.getId(), producto.getNombre(),
        producto.getCategoria(), producto.getMunicipio());
  }
}
