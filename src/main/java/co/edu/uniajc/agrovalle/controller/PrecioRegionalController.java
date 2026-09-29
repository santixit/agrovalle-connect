package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.PrecioRegionalResponse;
import co.edu.uniajc.agrovalle.service.PrecioRegionalService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/precios")
public class PrecioRegionalController {
  private final PrecioRegionalService precioRegionalService;

  public PrecioRegionalController(PrecioRegionalService precioRegionalService) {
    this.precioRegionalService = precioRegionalService;
  }

  @GetMapping("/regionales")
  public PrecioRegionalResponse consultar(
      @RequestParam @NotBlank @Size(max = 80) String categoria) {
    return precioRegionalService.consultar(categoria);
  }
}
