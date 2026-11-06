package co.edu.uniajc.agrovalle.service;

public class ContactoNoEncontradoException extends RuntimeException {
  public ContactoNoEncontradoException(Long id) {
    super("No existe un contacto asignado a este agricultor con identificador " + id);
  }
}
