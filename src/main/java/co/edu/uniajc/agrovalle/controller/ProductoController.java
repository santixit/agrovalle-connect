package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.ProductoResponse;
import co.edu.uniajc.agrovalle.service.CatalogoProductoService;
import co.edu.uniajc.agrovalle.service.GestionProductoService;
import co.edu.uniajc.agrovalle.service.TransferenciaProductoService;
import co.edu.uniajc.agrovalle.api.dto.TransferirProductoRequest;
import co.edu.uniajc.agrovalle.api.dto.TransferenciaProductoResponse;
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
  private final TransferenciaProductoService transferenciaProductoService;

  public ProductoController(CatalogoProductoService catalogoProductoService,
      GestionProductoService gestionProductoService,
      TransferenciaProductoService transferenciaProductoService) {
    this.catalogoProductoService = catalogoProductoService;
    this.gestionProductoService = gestionProductoService;
    this.transferenciaProductoService = transferenciaProductoService;
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

  @PostMapping("/{id}/transferencias")
  public List<TransferenciaProductoResponse> transferir(@AuthenticationPrincipal Jwt jwt,
      @PathVariable Long id, @Valid @RequestBody TransferirProductoRequest request) {
    return transferenciaProductoService.transferir(Long.valueOf(jwt.getSubject()), id,
        request.nuevoAgricultorId());
  }

  @GetMapping("/{id}/transferencias")
  public List<TransferenciaProductoResponse> historialTransferencias(@PathVariable Long id) {
    return transferenciaProductoService.historial(id);
  }
}
