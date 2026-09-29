package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Favorito;
import java.time.LocalDateTime;

public record FavoritoResponse(Long productoId, String nombre, String categoria,
    String municipio, LocalDateTime creadoEn) {
  public static FavoritoResponse from(Favorito favorito) {
    return new FavoritoResponse(favorito.getProducto().getId(), favorito.getProducto().getNombre(),
        favorito.getProducto().getCategoria(), favorito.getProducto().getMunicipio(),
        favorito.getCreadoEn());
  }
}
