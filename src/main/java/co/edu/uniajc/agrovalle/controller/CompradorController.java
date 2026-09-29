package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.CompradorResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroCompradorRequest;
import co.edu.uniajc.agrovalle.service.CompradorService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/register/comprador")
public class CompradorController {
  private final CompradorService compradorService;

  public CompradorController(CompradorService compradorService) {
    this.compradorService = compradorService;
  }

  @PostMapping
  public ResponseEntity<CompradorResponse> registrar(
      @Valid @RequestBody RegistroCompradorRequest request) {
    CompradorResponse comprador = compradorService.registrar(request);
    return ResponseEntity.created(URI.create("/api/v1/compradores/" + comprador.id()))
        .body(comprador);
  }
}
