package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Pedido;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import co.edu.uniajc.agrovalle.domain.EstadoPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
  Optional<Pedido> findByIdAndComprador_Usuario_Id(Long id, Long usuarioId);

  @Query("select distinct p from Pedido p join fetch p.detalles d join fetch d.producto "
      + "where p.comprador.usuario.id = :usuarioId order by p.creadoEn desc")
  List<Pedido> buscarDelComprador(@Param("usuarioId") Long usuarioId);

  @Query("select distinct p from Pedido p join fetch p.detalles d join fetch d.producto pr "
      + "join pr.ubicacion u left join u.parcela parcela "
      + "left join parcela.agricultorResponsableActual responsable "
      + "left join u.agricultorSinFinca independiente "
      + "where (responsable.usuario.id = :usuarioId or independiente.usuario.id = :usuarioId) "
      + "and p.estado in :estados "
      + "order by p.creadoEn asc")
  List<Pedido> buscarParaAgricultor(@Param("usuarioId") Long usuarioId,
      @Param("estados") Collection<EstadoPedido> estados);
}
