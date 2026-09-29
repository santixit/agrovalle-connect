package co.edu.uniajc.agrovalle.service;

public class PedidoNoEncontradoException extends RuntimeException {
  public PedidoNoEncontradoException(Long id) {
    super("No existe el pedido " + id + " o no está disponible para esta cuenta");
  }
}
