package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.TransferenciaProducto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaProductoRepository
    extends JpaRepository<TransferenciaProducto, Long> {
  List<TransferenciaProducto> findByProducto_IdOrderByFechaTransferenciaAscIdAsc(Long productoId);
}
