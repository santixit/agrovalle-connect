package co.edu.uniajc.agrovalle.api.dto;

import co.edu.uniajc.agrovalle.domain.Parcela;

public record ParcelaResponse(Long id, Long fincaId, String finca, String codigo,
    String municipio, Long agricultorResponsableId, String agricultorResponsable) {
  public static ParcelaResponse from(Parcela parcela) {
    return new ParcelaResponse(parcela.getId(), parcela.getFinca().getId(),
        parcela.getFinca().getNombre(), parcela.getCodigo(), parcela.getFinca().getMunicipio(),
        parcela.getAgricultorResponsableActual().getId(),
        parcela.getAgricultorResponsableActual().getNombre());
  }
}
