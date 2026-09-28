package co.edu.uniajc.agrovalle.service;

/** Signals that a farmer registration uses an existing identity number. */
public class CedulaDuplicadaException extends RuntimeException {
  public CedulaDuplicadaException(String cedula) {
    super("Ya existe un agricultor registrado con la cedula " + cedula);
  }
}
