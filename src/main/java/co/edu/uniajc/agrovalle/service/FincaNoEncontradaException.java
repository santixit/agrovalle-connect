package co.edu.uniajc.agrovalle.service;

public class FincaNoEncontradaException extends RuntimeException {
  public FincaNoEncontradaException(Long id) {
    super("La finca " + id + " no existe o no pertenece al agricultor autenticado");
  }
}
