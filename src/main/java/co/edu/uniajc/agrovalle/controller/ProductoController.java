package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.service.CatalogoProductoService;
import co.edu.uniajc.agrovalle.service.GestionProductoService;
import co.edu.uniajc.agrovalle.api.dto.PublicarProductoRequest;
import jakarta.validation.constraints.Size;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** Handles catalog search requests. */
@Validated
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

  private final CatalogoProductoService catalogoProductoService;
  private final GestionProductoService gestionProductoService;

  public ProductoController(CatalogoProductoService catalogoProductoService,
      GestionProductoService gestionProductoService) {
    this.catalogoProductoService = catalogoProductoService;
    this.gestionProductoService = gestionProductoService;
  }

  @GetMapping
  public List<ProductoResponse> buscar(
      @RequestParam(required = false) @Size(max = 80) String municipio,
      @RequestParam(required = false) @Size(max = 80) String categoria) {
    return catalogoProductoService.buscar(municipio, categoria);
  }

  @GetMapping("/mias")
  public List<ProductoResponse> buscarMias(@AuthenticationPrincipal Jwt jwt) {
    return catalogoProductoService.buscarDelAgricultor(Long.valueOf(jwt.getSubject()));
  }

  @PostMapping
  public ResponseEntity<ProductoResponse> publicar(@AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody PublicarProductoRequest request) {
    ProductoResponse response = gestionProductoService.publicar(
        Long.valueOf(jwt.getSubject()), request);
    return ResponseEntity.created(URI.create("/api/v1/productos/" + response.id()))
        .body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<co.edu.uniajc.agrovalle.api.dto.OfertaDetalleResponse> consultarDetalle(
      @PathVariable Long id) {
    return ResponseEntity.ok(catalogoProductoService.consultarDetalle(id));
  }

  @PatchMapping("/{id}/estado")
  public ResponseEntity<ProductoResponse> actualizarEstado(@AuthenticationPrincipal Jwt jwt,
      @org.springframework.web.bind.annotation.PathVariable Long id,
      @Valid @RequestBody co.edu.uniajc.agrovalle.api.dto.EstadoProductoRequest request) {
    return ResponseEntity.ok(gestionProductoService.actualizarEstado(
        Long.valueOf(jwt.getSubject()),
        id, request));
  }
}
