package co.edu.uniajc.agrovalle.service;

public class OfertaNoPropiaException extends RuntimeException {
  public OfertaNoPropiaException(Long id) {
    super("La oferta " + id + " no pertenece al agricultor autenticado");
  }
}
