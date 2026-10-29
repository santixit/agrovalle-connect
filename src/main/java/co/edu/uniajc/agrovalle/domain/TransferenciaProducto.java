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
import java.time.LocalDateTime;

@Entity
@Table(name = "transferencias_producto")
public class TransferenciaProducto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "producto_id", nullable = false)
  private Producto producto;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agricultor_anterior_id", nullable = false)
  private Agricultor agricultorAnterior;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agricultor_nuevo_id", nullable = false)
  private Agricultor agricultorNuevo;

  @Column(name = "fecha_transferencia", nullable = false)
  private LocalDateTime fechaTransferencia = LocalDateTime.now();

  protected TransferenciaProducto() { }

  public TransferenciaProducto(Producto producto, Agricultor agricultorAnterior,
      Agricultor agricultorNuevo) {
    if (producto == null || agricultorAnterior == null || agricultorNuevo == null
        || agricultorAnterior.getId().equals(agricultorNuevo.getId())) {
      throw new IllegalArgumentException("La transferencia requiere dos agricultores distintos");
    }
    this.producto = producto;
    this.agricultorAnterior = agricultorAnterior;
    this.agricultorNuevo = agricultorNuevo;
  }

  public Long getId() {
    return id;
  }

  public Producto getProducto() {
    return producto;
  }

  public Agricultor getAgricultorAnterior() {
    return agricultorAnterior;
  }

  public Agricultor getAgricultorNuevo() {
    return agricultorNuevo;
  }

  public LocalDateTime getFechaTransferencia() {
    return fechaTransferencia;
  }
}
