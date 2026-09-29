package co.edu.uniajc.agrovalle.service;

public class DatosCuentaInvalidosException extends RuntimeException {
  public DatosCuentaInvalidosException() {
    super("Debe enviar correo y contraseña juntos para crear una cuenta de acceso");
  }
}
