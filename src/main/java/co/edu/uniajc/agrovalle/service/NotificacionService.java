package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.NotificacionResponse;
import co.edu.uniajc.agrovalle.repository.NotificacionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NotificacionService {
  private final NotificacionRepository repository;

  public NotificacionService(NotificacionRepository repository) {
    this.repository = repository;
  }

  public List<NotificacionResponse> listar(Long usuarioId) {
    return repository.findByUsuario_IdOrderByCreadaEnDesc(usuarioId).stream()
        .map(NotificacionResponse::from).toList();
  }
}
