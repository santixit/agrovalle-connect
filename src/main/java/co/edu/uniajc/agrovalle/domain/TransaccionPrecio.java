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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Completed trade used as the source for regional price statistics. */
@Entity
@Table(name = "transacciones_precio")
public class TransaccionPrecio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "producto_id", nullable = false)
  private Producto producto;

  @Column(name = "cantidad_kg", nullable = false, precision = 12, scale = 2)
  private BigDecimal cantidadKg;

  @Column(name = "precio_por_kg", nullable = false, precision = 12, scale = 2)
  private BigDecimal precioPorKg;

  @Column(name = "ocurrida_en", nullable = false)
  private LocalDateTime ocurridaEn = LocalDateTime.now();

  protected TransaccionPrecio() { }

  public TransaccionPrecio(Producto producto, BigDecimal cantidadKg, BigDecimal precioPorKg) {
    this.producto = producto;
    this.cantidadKg = cantidadKg;
    this.precioPorKg = precioPorKg;
  }

  public Long getId() {
    return id;
  }

  public Producto getProducto() {
    return producto;
  }

  public BigDecimal getCantidadKg() {
    return cantidadKg;
  }

  public BigDecimal getPrecioPorKg() {
    return precioPorKg;
  }

  public LocalDateTime getOcurridaEn() {
    return ocurridaEn;
  }
}
