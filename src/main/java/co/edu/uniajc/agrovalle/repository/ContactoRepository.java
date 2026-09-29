package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
  long countByCreadoEnBetween(LocalDateTime inicio, LocalDateTime fin);
}
