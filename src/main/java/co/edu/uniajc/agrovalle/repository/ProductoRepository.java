package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Producto;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select p from Producto p where p.id = :id")
  Optional<Producto> buscarParaActualizar(@Param("id") Long id);

  long countByCreadoEnBetween(LocalDateTime inicio, LocalDateTime fin);

  List<Producto> findByActivoTrue();

  List<Producto> findByActivoTrueAndMunicipioIgnoreCase(String municipio);

  List<Producto> findByActivoTrueAndCategoriaIgnoreCase(String categoria);

  List<Producto> findByActivoTrueAndMunicipioIgnoreCaseAndCategoriaIgnoreCase(
      String municipio, String categoria);
}
