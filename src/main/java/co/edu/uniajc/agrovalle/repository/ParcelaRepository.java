package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Parcela;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
  List<Parcela> findByAgricultorResponsableActual_Usuario_IdOrderByFinca_NombreAscCodigoAsc(
      Long usuarioId);

  List<Parcela> findByFinca_IdAndAgricultorResponsableActual_IdOrderByCodigoAsc(
      Long fincaId, Long agricultorId);

  Optional<Parcela> findByIdAndAgricultorResponsableActual_Id(Long id, Long agricultorId);

  Optional<Parcela> findByFinca_IdAndCodigoAndAgricultorResponsableActual_Id(
      Long fincaId, String codigo, Long agricultorId);

  boolean existsByFinca_IdAndAgricultorResponsableActual_Id(Long fincaId, Long agricultorId);

  boolean existsByFinca_IdAndCodigo(Long fincaId, String codigo);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Parcela p where p.id = :id")
  Optional<Parcela> buscarParaActualizar(@Param("id") Long id);
}
