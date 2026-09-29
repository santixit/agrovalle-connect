package co.edu.uniajc.agrovalle.service;

public class CorreoDuplicadoException extends RuntimeException {
  public CorreoDuplicadoException(String correo) {
    super("El correo ya está registrado: " + correo);
  }
}
