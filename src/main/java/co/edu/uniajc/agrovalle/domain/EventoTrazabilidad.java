package co.edu.uniajc.agrovalle.domain;

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
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/** Immutable timeline entry for an order state or logistics event. */
@Entity
@Table(name = "eventos_trazabilidad")
public class EventoTrazabilidad {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "pedido_id", nullable = false)
  private Pedido pedido;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 24)
  private EstadoPedido estado;

  @Column(nullable = false, length = 500)
  private String descripcion;

  @Column(name = "ocurrido_en", nullable = false)
  private LocalDateTime ocurridoEn = LocalDateTime.now();

  protected EventoTrazabilidad() { }

  public EventoTrazabilidad(Pedido pedido, EstadoPedido estado, String descripcion) {
    this.pedido = pedido;
    this.estado = estado;
    this.descripcion = descripcion;
  }

  public Long getId() {
    return id;
  }

  public Pedido getPedido() {
    return pedido;
  }

  public EstadoPedido getEstado() {
    return estado;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public LocalDateTime getOcurridoEn() {
    return ocurridoEn;
  }
}
