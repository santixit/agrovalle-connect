package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Comprador;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompradorRepository extends JpaRepository<Comprador, Long> {
  Optional<Comprador> findByUsuario_Id(Long usuarioId);
}
