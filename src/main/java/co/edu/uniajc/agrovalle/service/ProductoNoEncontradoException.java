package co.edu.uniajc.agrovalle.service;

public class ProductoNoEncontradoException extends RuntimeException {
  public ProductoNoEncontradoException(Long id) {
    super("No existe una oferta activa con identificador " + id);
  }
}
