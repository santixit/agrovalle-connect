package co.edu.uniajc.agrovalle.service;

public class TransicionPedidoInvalidaException extends RuntimeException {
  public TransicionPedidoInvalidaException() {
    super("El pedido no está en un estado que permita esta operación");
  }
}
