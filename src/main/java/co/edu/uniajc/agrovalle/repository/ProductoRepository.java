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

  @Query("select p from Producto p join p.ubicacion u left join u.parcela parcela "
      + "left join parcela.agricultorResponsableActual responsable "
      + "left join u.agricultorSinFinca independiente "
      + "where responsable.usuario.id = :usuarioId or independiente.usuario.id = :usuarioId "
      + "order by p.creadoEn desc")
  List<Producto> buscarDelAgricultor(@Param("usuarioId") Long usuarioId);

  @Query("select p from Producto p join p.ubicacion u left join u.parcela parcela "
      + "left join parcela.agricultorResponsableActual responsable "
      + "left join u.agricultorSinFinca independiente "
      + "where responsable.usuario.id = :usuarioId or independiente.usuario.id = :usuarioId "
      + "order by p.creadoEn desc")
  List<Producto> findByAgricultor_Usuario_IdOrderByCreadoEnDesc(
      @Param("usuarioId") Long usuarioId);

  @Query("select p from Producto p join p.ubicacion u left join u.parcela parcela "
      + "left join parcela.finca finca where p.activo = true "
      + "and lower(coalesce(finca.municipio, u.municipioSinFinca)) = lower(:municipio)")
  List<Producto> buscarActivosPorMunicipio(
      @Param("municipio") String municipio);

  @Query("select p from Producto p join p.ubicacion u left join u.parcela parcela "
      + "left join parcela.finca finca where p.activo = true "
      + "and lower(coalesce(finca.municipio, u.municipioSinFinca)) = lower(:municipio)")
  List<Producto> findByActivoTrueAndMunicipioIgnoreCase(@Param("municipio") String municipio);

  List<Producto> findByActivoTrueAndCategoriaIgnoreCase(String categoria);

  @Query("select p from Producto p join p.ubicacion u left join u.parcela parcela "
      + "left join parcela.finca finca where p.activo = true "
      + "and lower(coalesce(finca.municipio, u.municipioSinFinca)) = lower(:municipio) "
      + "and lower(p.categoria) = lower(:categoria)")
  List<Producto> buscarActivosPorMunicipioYCategoria(
      @Param("municipio") String municipio,
      @Param("categoria") String categoria);

  @Query("select p from Producto p join p.ubicacion u left join u.parcela parcela "
      + "left join parcela.finca finca where p.activo = true "
      + "and lower(coalesce(finca.municipio, u.municipioSinFinca)) = lower(:municipio) "
      + "and lower(p.categoria) = lower(:categoria)")
  List<Producto> findByActivoTrueAndMunicipioIgnoreCaseAndCategoriaIgnoreCase(
      @Param("municipio") String municipio, @Param("categoria") String categoria);

  @Query("select p from Producto p join p.ubicacion u where u.parcela.id = :parcelaId")
  List<Producto> findByParcelaId(
      @Param("parcelaId") Long parcelaId);
}
