package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Usuario;
import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByCorreoIgnoreCase(String correo);

  boolean existsByCorreoIgnoreCase(String correo);

  long countByCreadoEnBetween(LocalDateTime inicio, LocalDateTime fin);
}
