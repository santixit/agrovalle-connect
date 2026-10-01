package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Finca;

public record FincaResponse(Long id, String nombre, String municipio, String direccion) {
  public static FincaResponse from(Finca finca) {
    return new FincaResponse(finca.getId(), finca.getNombre(), finca.getMunicipio(),
        finca.getDireccion());
  }
}
