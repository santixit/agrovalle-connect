package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.PedidoResumenResponse;
import co.edu.uniajc.agrovalle.service.PedidoConsultaService;
import java.util.List;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservas")
public class PedidoConsultaController {
  private final PedidoConsultaService consultaService;

  public PedidoConsultaController(PedidoConsultaService consultaService) {
    this.consultaService = consultaService;
  }

  @GetMapping("/mias")
  public List<PedidoResumenResponse> consultarComprador(@AuthenticationPrincipal Jwt jwt) {
    return consultaService.consultarComprador(Long.valueOf(jwt.getSubject()));
  }

  @GetMapping("/pendientes")
  public List<PedidoResumenResponse> consultarAgricultor(@AuthenticationPrincipal Jwt jwt) {
    return consultaService.consultarPendientesAgricultor(Long.valueOf(jwt.getSubject()));
  }
}
