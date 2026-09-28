package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Agricultor;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Public profile fields returned for a farmer. */
public record AgricultorResponse(Long id, String nombre,
    @JsonProperty("ubicacion_valle") String ubicacionValle) {

  public static AgricultorResponse from(Agricultor agricultor) {
    return new AgricultorResponse(agricultor.getId(), agricultor.getNombre(),
        agricultor.getMunicipio());
  }
}
