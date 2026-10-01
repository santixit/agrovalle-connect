package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.service.AgricultorNoEncontradoException;
import co.edu.uniajc.agrovalle.service.CedulaDuplicadaException;
import co.edu.uniajc.agrovalle.service.CorreoDuplicadoException;
import co.edu.uniajc.agrovalle.service.DatosCuentaInvalidosException;
import co.edu.uniajc.agrovalle.service.ProductoNoEncontradoException;
import co.edu.uniajc.agrovalle.service.CompradorNoEncontradoException;
import co.edu.uniajc.agrovalle.service.PedidoNoEncontradoException;
import co.edu.uniajc.agrovalle.service.FincaNoEncontradaException;
import co.edu.uniajc.agrovalle.domain.StockInsuficienteException;
import co.edu.uniajc.agrovalle.service.OfertaNoPropiaException;
import co.edu.uniajc.agrovalle.service.EstadoProductoInvalidoException;
import co.edu.uniajc.agrovalle.service.RangoFechasInvalidoException;
import co.edu.uniajc.agrovalle.service.TransicionPedidoInvalidaException;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.dao.DataIntegrityViolationException;

/** Maps expected domain and validation errors to stable API responses. */
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(CedulaDuplicadaException.class)
  public ResponseEntity<Map<String, String>> cedulaDuplicada(
      CedulaDuplicadaException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler({CorreoDuplicadoException.class, DatosCuentaInvalidosException.class})
  public ResponseEntity<Map<String, String>> cuentaInvalida(RuntimeException exception) {
    HttpStatus status = exception instanceof CorreoDuplicadoException
        ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Map<String, String>> credencialesInvalidas(
      BadCredentialsException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("error", "Correo o contraseña incorrectos"));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> conflictoDePersistencia(
      DataIntegrityViolationException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", "El registro entra en conflicto con datos existentes"));
  }

  @ExceptionHandler(AgricultorNoEncontradoException.class)
  public ResponseEntity<Map<String, String>> agricultorNoEncontrado(
      AgricultorNoEncontradoException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler({ProductoNoEncontradoException.class, FincaNoEncontradaException.class,
      PedidoNoEncontradoException.class, CompradorNoEncontradoException.class})
  public ResponseEntity<Map<String, String>> productoNoEncontrado(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler({StockInsuficienteException.class, OfertaNoPropiaException.class})
  public ResponseEntity<Map<String, String>> conflicto(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler({TransicionPedidoInvalidaException.class, IllegalStateException.class})
  public ResponseEntity<Map<String, String>> conflictoEstado(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler(EstadoProductoInvalidoException.class)
  public ResponseEntity<Map<String, String>> estadoProductoInvalido(
      EstadoProductoInvalidoException exception) {
    return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler(RangoFechasInvalidoException.class)
  public ResponseEntity<Map<String, String>> rangoFechasInvalido(
      RangoFechasInvalidoException exception) {
    return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> datosInvalidos(
      MethodArgumentNotValidException exception) {
    String details = exception.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getDefaultMessage())
        .filter(message -> message != null && !message.isBlank())
        .distinct()
        .collect(Collectors.joining(". "));
    String message = details.isBlank() ? "Los datos enviados no son válidos"
        : "Revisa los datos: " + details;
    return ResponseEntity.badRequest().body(Map.of("error", message));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> parametroInvalido(
      ConstraintViolationException exception) {
    return ResponseEntity.badRequest().body(Map.of("error", "Los filtros no son validos"));
  }
}
