package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.service.AgricultorNoEncontradoException;
import co.edu.uniajc.agrovalle.service.CedulaDuplicadaException;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Maps expected domain and validation errors to stable API responses. */
@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(CedulaDuplicadaException.class)
  public ResponseEntity<Map<String, String>> cedulaDuplicada(
      CedulaDuplicadaException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler(AgricultorNoEncontradoException.class)
  public ResponseEntity<Map<String, String>> agricultorNoEncontrado(
      AgricultorNoEncontradoException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", exception.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> datosInvalidos(
      MethodArgumentNotValidException exception) {
    return ResponseEntity.badRequest().body(Map.of("error", "Los datos enviados no son validos"));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> parametroInvalido(
      ConstraintViolationException exception) {
    return ResponseEntity.badRequest().body(Map.of("error", "Los filtros no son validos"));
  }
}
