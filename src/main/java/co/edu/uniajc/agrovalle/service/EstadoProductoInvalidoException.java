package co.edu.uniajc.agrovalle.service;

public class EstadoProductoInvalidoException extends RuntimeException {
  public EstadoProductoInvalidoException() {
    super("Estado incompatible con la disponibilidad actual del producto");
  }
}
