package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Agricultor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {
  boolean existsByCedula(String cedula);

  Optional<Agricultor> findByUsuario_Id(Long usuarioId);
}
