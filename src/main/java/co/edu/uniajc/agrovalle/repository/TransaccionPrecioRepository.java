package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.TransaccionPrecio;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransaccionPrecioRepository extends JpaRepository<TransaccionPrecio, Long> {
  @Query("select t from TransaccionPrecio t join fetch t.producto p "
      + "where lower(p.categoria) = lower(:categoria) and t.ocurridaEn >= :desde "
      + "order by t.ocurridaEn desc")
  List<TransaccionPrecio> buscarRecientesPorCategoria(
      @Param("categoria") String categoria, @Param("desde") LocalDateTime desde,
      Pageable pageable);
}
