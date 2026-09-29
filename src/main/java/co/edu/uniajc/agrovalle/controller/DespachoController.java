package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.DespachoResponse;
import co.edu.uniajc.agrovalle.api.dto.ProgramarDespachoRequest;
import co.edu.uniajc.agrovalle.service.DespachoService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/api/v1/despachos")
public class DespachoController {
  private final DespachoService despachoService;

  public DespachoController(DespachoService despachoService) {
    this.despachoService = despachoService;
  }

  @PostMapping
  public ResponseEntity<DespachoResponse> programar(@AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody ProgramarDespachoRequest request) {
    DespachoResponse response = despachoService.programar(Long.valueOf(jwt.getSubject()), request);
    return ResponseEntity.created(URI.create("/api/v1/despachos/" + response.id())).body(response);
  }

  @PatchMapping("/{pedidoId}/entregado")
  public ResponseEntity<DespachoResponse> marcarEntregado(@PathVariable Long pedidoId,
      @AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok(despachoService.marcarEntregado(
        Long.valueOf(jwt.getSubject()), pedidoId));
  }
}
