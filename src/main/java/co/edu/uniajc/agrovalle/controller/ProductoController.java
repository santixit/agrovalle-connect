package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.service.ProductoService;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Handles catalog search requests. */
@Validated
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

  private final ProductoService productoService;

  public ProductoController(ProductoService productoService) {
    this.productoService = productoService;
  }

  @GetMapping
  public List<ProductoResponse> buscar(
      @RequestParam(required = false) @Size(max = 80) String municipio,
      @RequestParam(required = false) @Size(max = 80) String categoria) {
    return productoService.buscar(municipio, categoria);
  }
}
