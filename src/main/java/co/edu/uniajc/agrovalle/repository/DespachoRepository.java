package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Despacho;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespachoRepository extends JpaRepository<Despacho, Long> {
  Optional<Despacho> findByPedido_Id(Long pedidoId);
}
