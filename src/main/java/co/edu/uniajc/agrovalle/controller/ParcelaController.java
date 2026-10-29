package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.ParcelaResponse;
import co.edu.uniajc.agrovalle.service.ParcelaService;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parcelas")
public class ParcelaController {
  private final ParcelaService parcelaService;

  public ParcelaController(ParcelaService parcelaService) {
    this.parcelaService = parcelaService;
  }

  @GetMapping("/mias")
  public List<ParcelaResponse> mias(@AuthenticationPrincipal Jwt jwt) {
    return parcelaService.mias(Long.valueOf(jwt.getSubject()));
  }
}
