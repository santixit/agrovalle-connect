package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Favorito;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
  boolean existsByComprador_IdAndProducto_Id(Long compradorId, Long productoId);

  Optional<Favorito> findByComprador_IdAndProducto_Id(Long compradorId, Long productoId);

  List<Favorito> findByComprador_IdOrderByCreadoEnDesc(Long compradorId);
}
