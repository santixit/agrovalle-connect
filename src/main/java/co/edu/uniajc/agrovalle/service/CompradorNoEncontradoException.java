package co.edu.uniajc.agrovalle.service;

public class CompradorNoEncontradoException extends RuntimeException {
  public CompradorNoEncontradoException() {
    super("La cuenta autenticada no tiene un perfil de comprador");
  }
}
