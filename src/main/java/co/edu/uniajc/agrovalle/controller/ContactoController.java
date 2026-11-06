package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.ContactoRequest;
import co.edu.uniajc.agrovalle.api.dto.ContactoResponse;
import co.edu.uniajc.agrovalle.api.dto.ContactoBandejaResponse;
import co.edu.uniajc.agrovalle.api.dto.ResponderContactoRequest;
import co.edu.uniajc.agrovalle.service.ContactoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contactos")
public class ContactoController {
  private final ContactoService contactoService;

  public ContactoController(ContactoService contactoService) {
    this.contactoService = contactoService;
  }

  @PostMapping
  public ResponseEntity<ContactoResponse> contactar(@AuthenticationPrincipal Jwt jwt,
      @Valid @RequestBody ContactoRequest request) {
    ContactoResponse response = contactoService.contactar(Long.valueOf(jwt.getSubject()), request);
    return ResponseEntity.created(URI.create("/api/v1/contactos/" + response.id()))
        .body(response);
  }

  @GetMapping("/recibidos")
  public List<ContactoBandejaResponse> recibidos(@AuthenticationPrincipal Jwt jwt) {
    return contactoService.recibidos(Long.valueOf(jwt.getSubject()));
  }

  @GetMapping("/enviados")
  public List<ContactoBandejaResponse> enviados(@AuthenticationPrincipal Jwt jwt) {
    return contactoService.enviados(Long.valueOf(jwt.getSubject()));
  }

  @PostMapping("/{contactoId}/respuesta")
  public ContactoBandejaResponse responder(@AuthenticationPrincipal Jwt jwt,
      @PathVariable Long contactoId, @Valid @RequestBody ResponderContactoRequest request) {
    return contactoService.responder(Long.valueOf(jwt.getSubject()), contactoId, request.mensaje());
  }
}
