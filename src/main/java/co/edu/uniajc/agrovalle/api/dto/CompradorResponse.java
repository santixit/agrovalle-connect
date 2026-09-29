package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Comprador;

public record CompradorResponse(Long id, String nombre, String correo) {
  public static CompradorResponse from(Comprador comprador) {
    return new CompradorResponse(comprador.getId(), comprador.getNombre(),
        comprador.getUsuario().getCorreo());
  }
}
