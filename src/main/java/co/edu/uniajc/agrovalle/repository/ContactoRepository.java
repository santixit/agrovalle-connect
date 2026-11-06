package co.edu.uniajc.agrovalle.repository;

import co.edu.uniajc.agrovalle.domain.Contacto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.time.LocalDateTime;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
  long countByCreadoEnBetween(LocalDateTime inicio, LocalDateTime fin);

  @EntityGraph(attributePaths = {"comprador", "producto", "agricultor", "conversacion"})
  List<Contacto> findByAgricultor_Usuario_IdOrderByCreadoEnDesc(Long usuarioId);

  @EntityGraph(attributePaths = {"comprador", "producto", "agricultor", "conversacion"})
  List<Contacto> findByComprador_Usuario_IdOrderByCreadoEnDesc(Long usuarioId);

  @EntityGraph(attributePaths = {"comprador", "producto", "agricultor", "conversacion"})
  Optional<Contacto> findByIdAndAgricultor_Usuario_Id(Long id, Long usuarioId);

  @EntityGraph(attributePaths = {"comprador", "producto", "agricultor", "conversacion"})
  Optional<Contacto> findByIdAndComprador_Usuario_Id(Long id, Long usuarioId);
}
