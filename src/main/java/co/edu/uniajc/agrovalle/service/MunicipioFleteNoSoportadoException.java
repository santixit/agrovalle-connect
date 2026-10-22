package co.edu.uniajc.agrovalle.service;

public class MunicipioFleteNoSoportadoException extends RuntimeException {
  public MunicipioFleteNoSoportadoException(String municipio) {
    super("No hay una tarifa de flete configurada para el municipio: " + municipio);
  }
}
