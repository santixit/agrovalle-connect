package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.FavoritoResponse;
import co.edu.uniajc.agrovalle.service.FavoritoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favoritos")
public class FavoritoController {
  private final FavoritoService favoritoService;

  public FavoritoController(FavoritoService favoritoService) {
    this.favoritoService = favoritoService;
  }

  @PostMapping("/{productoId}")
  public ResponseEntity<Void> agregar(@AuthenticationPrincipal Jwt jwt,
      @PathVariable Long productoId) {
    favoritoService.agregar(Long.valueOf(jwt.getSubject()), productoId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{productoId}")
  public ResponseEntity<Void> quitar(@AuthenticationPrincipal Jwt jwt,
      @PathVariable Long productoId) {
    favoritoService.quitar(Long.valueOf(jwt.getSubject()), productoId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public List<FavoritoResponse> listar(@AuthenticationPrincipal Jwt jwt) {
    return favoritoService.listar(Long.valueOf(jwt.getSubject()));
  }
}
