package co.edu.uniajc.agrovalle.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** A buyer reservation is represented as an order with one or more detail lines. */
@Entity
@Table(name = "pedidos")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "comprador_id", nullable = false)
  private Comprador comprador;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 24)
  private EstadoPedido estado = EstadoPedido.PENDIENTE;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  @Column(name = "actualizado_en", nullable = false)
  private LocalDateTime actualizadoEn = LocalDateTime.now();

  @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DetallePedido> detalles = new ArrayList<>();

  protected Pedido() { }

  public Pedido(Comprador comprador) {
    this.comprador = comprador;
  }

  public Long getId() {
    return id;
  }

  public Comprador getComprador() {
    return comprador;
  }

  public EstadoPedido getEstado() {
    return estado;
  }

  public LocalDateTime getCreadoEn() {
    return creadoEn;
  }

  public LocalDateTime getActualizadoEn() {
    return actualizadoEn;
  }

  public List<DetallePedido> getDetalles() {
    return List.copyOf(detalles);
  }

  public void agregarDetalle(DetallePedido detalle) {
    if (detalle == null) {
      throw new IllegalArgumentException("El detalle del pedido es obligatorio");
    }
    detalle.asociarPedido(this);
    detalles.add(detalle);
  }

  public void cambiarEstado(EstadoPedido nuevoEstado) {
    if (nuevoEstado == null) {
      throw new IllegalArgumentException("El estado del pedido es obligatorio");
    }
    estado = nuevoEstado;
    actualizadoEn = LocalDateTime.now();
  }
}
