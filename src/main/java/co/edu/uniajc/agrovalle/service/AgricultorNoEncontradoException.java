package co.edu.uniajc.agrovalle.service;

/** Signals that a farmer profile does not exist. */
public class AgricultorNoEncontradoException extends RuntimeException {
  public AgricultorNoEncontradoException(Long id) {
    super("No existe un agricultor con id " + id);
  }
}
