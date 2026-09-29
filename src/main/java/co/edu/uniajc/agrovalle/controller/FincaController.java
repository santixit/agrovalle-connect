package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.FincaResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroFincaRequest;
import co.edu.uniajc.agrovalle.service.FincaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fincas")
public class FincaController {
  private final FincaService fincaService;

  public FincaController(FincaService fincaService) {
    this.fincaService = fincaService;
  }

  @PostMapping
  public ResponseEntity<FincaResponse> registrar(@AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody RegistroFincaRequest request) {
    FincaResponse response = fincaService.registrar(Long.valueOf(jwt.getSubject()), request);
    return ResponseEntity.created(URI.create("/api/v1/fincas/" + response.id())).body(response);
  }

  @GetMapping
  public List<FincaResponse> listar(@AuthenticationPrincipal Jwt jwt) {
    return fincaService.listar(Long.valueOf(jwt.getSubject()));
  }
}
