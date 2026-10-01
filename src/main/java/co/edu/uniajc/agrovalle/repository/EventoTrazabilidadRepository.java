package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.EventoTrazabilidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoTrazabilidadRepository extends JpaRepository<EventoTrazabilidad, Long> {
  List<EventoTrazabilidad> findByPedido_IdOrderByOcurridoEnAsc(Long pedidoId);
}
