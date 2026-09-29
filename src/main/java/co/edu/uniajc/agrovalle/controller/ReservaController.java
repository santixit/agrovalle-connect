package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.ReservaRequest;
import co.edu.uniajc.agrovalle.api.dto.ReservaResponse;
import co.edu.uniajc.agrovalle.service.ReservaService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {
  private final ReservaService reservaService;

  public ReservaController(ReservaService reservaService) {
    this.reservaService = reservaService;
  }

  @PostMapping
  public ResponseEntity<ReservaResponse> reservar(@AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody ReservaRequest request) {
    ReservaResponse response = reservaService.reservar(Long.valueOf(jwt.getSubject()), request);
    return ResponseEntity.created(URI.create("/api/v1/reservas/" + response.id()))
        .body(response);
  }
}
