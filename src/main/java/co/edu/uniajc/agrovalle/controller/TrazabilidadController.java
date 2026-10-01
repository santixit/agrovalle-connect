package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.EventoTrazabilidadResponse;
import co.edu.uniajc.agrovalle.service.TrazabilidadService;
import java.util.List;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservas")
public class TrazabilidadController {
  private final TrazabilidadService trazabilidadService;

  public TrazabilidadController(TrazabilidadService trazabilidadService) {
    this.trazabilidadService = trazabilidadService;
  }

  @GetMapping("/{id}/trazabilidad")
  public List<EventoTrazabilidadResponse> consultar(@PathVariable Long id,
      @AuthenticationPrincipal Jwt jwt) {
    return trazabilidadService.consultar(Long.valueOf(jwt.getSubject()), id);
  }
}
