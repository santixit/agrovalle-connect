package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {
  boolean existsByCedula(String cedula);
}
