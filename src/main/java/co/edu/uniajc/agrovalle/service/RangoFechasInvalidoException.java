package co.edu.uniajc.agrovalle.service;

public class RangoFechasInvalidoException extends RuntimeException {
  public RangoFechasInvalidoException() {
    super("La fecha inicial debe ser anterior o igual a la fecha final");
  }
}
