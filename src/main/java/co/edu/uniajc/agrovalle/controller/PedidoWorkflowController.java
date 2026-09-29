package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.service.PedidoWorkflowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservas")
public class PedidoWorkflowController {
  private final PedidoWorkflowService workflowService;

  public PedidoWorkflowController(PedidoWorkflowService workflowService) {
    this.workflowService = workflowService;
  }

  @PostMapping("/{id}/confirmar")
  public ResponseEntity<Void> confirmar(@PathVariable Long id,
      @AuthenticationPrincipal Jwt jwt) {
    workflowService.confirmar(id, Long.valueOf(jwt.getSubject()));
    return ResponseEntity.noContent().build();
  }
}
