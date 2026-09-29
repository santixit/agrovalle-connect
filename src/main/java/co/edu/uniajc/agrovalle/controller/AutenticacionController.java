package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.LoginRequest;
import co.edu.uniajc.agrovalle.api.dto.TokenResponse;
import co.edu.uniajc.agrovalle.service.AutenticacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AutenticacionController {
  private final AutenticacionService autenticacionService;

  public AutenticacionController(AutenticacionService autenticacionService) {
    this.autenticacionService = autenticacionService;
  }

  @PostMapping("/login")
  public TokenResponse iniciarSesion(@Valid @RequestBody LoginRequest request) {
    return autenticacionService.iniciarSesion(request);
  }
}
