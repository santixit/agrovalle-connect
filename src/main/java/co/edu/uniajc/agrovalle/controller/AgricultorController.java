package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.AgricultorResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroAgricultorRequest;
import co.edu.uniajc.agrovalle.service.AgricultorService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Handles farmer registration and profile REST requests. */
@RestController
@RequestMapping("/api/v1")
public class AgricultorController {

  private final AgricultorService agricultorService;

  public AgricultorController(AgricultorService agricultorService) {
    this.agricultorService = agricultorService;
  }

  @PostMapping("/auth/register")
  public ResponseEntity<AgricultorResponse> registrar(
      @Valid @RequestBody RegistroAgricultorRequest request) {
    AgricultorResponse response = agricultorService.registrar(request);
    return ResponseEntity.created(URI.create("/api/v1/productores/" + response.id()))
        .body(response);
  }

  @GetMapping("/productores/{id}")
  public ResponseEntity<AgricultorResponse> consultar(@PathVariable Long id) {
    return ResponseEntity.ok(agricultorService.consultar(id));
  }
}
