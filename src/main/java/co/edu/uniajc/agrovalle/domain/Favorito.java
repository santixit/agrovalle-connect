package co.edu.uniajc.agrovalle.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;

/** Product saved by a buyer for later consultation. */
@Entity
@Table(name = "favoritos", uniqueConstraints = @UniqueConstraint(
    name = "uq_favorito_comprador_producto", columnNames = {"comprador_id", "producto_id"}))
public class Favorito {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "comprador_id", nullable = false)
  private Comprador comprador;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "producto_id", nullable = false)
  private Producto producto;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  protected Favorito() { }

  public Favorito(Comprador comprador, Producto producto) {
    this.comprador = comprador;
    this.producto = producto;
  }

  public Long getId() {
    return id;
  }

  public Comprador getComprador() {
    return comprador;
  }

  public Producto getProducto() {
    return producto;
  }

  public LocalDateTime getCreadoEn() {
    return creadoEn;
  }
}
