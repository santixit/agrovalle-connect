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

/** Snapshot of product, quantity, and unit price at order time. */
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "pedido_id", nullable = false)
  private Pedido pedido;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "producto_id", nullable = false)
  private Producto producto;

  @Column(name = "cantidad_kg", nullable = false, precision = 12, scale = 2)
  private BigDecimal cantidadKg;

  @Column(name = "precio_por_kg", nullable = false, precision = 12, scale = 2)
  private BigDecimal precioPorKg;

  protected DetallePedido() { }

  public DetallePedido(Producto producto, BigDecimal cantidadKg, BigDecimal precioPorKg) {
    this.producto = producto;
    this.cantidadKg = cantidadKg;
    this.precioPorKg = precioPorKg;
  }

  void asociarPedido(Pedido pedido) {
    this.pedido = pedido;
  }

  public Long getId() {
    return id;
  }

  public Pedido getPedido() {
    return pedido;
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
}
