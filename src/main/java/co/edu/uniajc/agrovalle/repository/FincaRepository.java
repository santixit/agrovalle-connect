package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Finca;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FincaRepository extends JpaRepository<Finca, Long> {
  List<Finca> findByAgricultor_IdAndActivaTrueOrderByNombreAsc(Long agricultorId);

  Optional<Finca> findByIdAndAgricultor_Id(Long id, Long agricultorId);
}
