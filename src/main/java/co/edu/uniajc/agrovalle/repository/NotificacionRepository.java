package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Notificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
  List<Notificacion> findByUsuario_IdOrderByCreadaEnDesc(Long usuarioId);
}
