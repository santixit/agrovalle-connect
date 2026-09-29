package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.NotificacionResponse;
import co.edu.uniajc.agrovalle.service.NotificacionService;
import java.util.List;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {
  private final NotificacionService notificacionService;

  public NotificacionController(NotificacionService notificacionService) {
    this.notificacionService = notificacionService;
  }

  @GetMapping("/mias")
  public List<NotificacionResponse> listar(@AuthenticationPrincipal Jwt jwt) {
    return notificacionService.listar(Long.valueOf(jwt.getSubject()));
  }
}
