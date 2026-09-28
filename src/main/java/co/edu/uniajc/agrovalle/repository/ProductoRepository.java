package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

  List<Producto> findByActivoTrue();

  List<Producto> findByActivoTrueAndMunicipioIgnoreCase(String municipio);

  List<Producto> findByActivoTrueAndCategoriaIgnoreCase(String categoria);

  List<Producto> findByActivoTrueAndMunicipioIgnoreCaseAndCategoriaIgnoreCase(
      String municipio, String categoria);
}
