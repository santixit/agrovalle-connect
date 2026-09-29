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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Planned or active delivery for an order. */
@Entity
@Table(name = "despachos")
public class Despacho {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "pedido_id", nullable = false, unique = true)
  private Pedido pedido;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EstadoDespacho estado = EstadoDespacho.PROGRAMADO;

  @Column(name = "fecha_programada", nullable = false)
  private LocalDate fechaProgramada;

  @Column(name = "franja_horaria", nullable = false, length = 80)
  private String franjaHoraria;

  @Column(length = 300)
  private String ruta;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  protected Despacho() { }

  public Despacho(Pedido pedido, LocalDate fechaProgramada, String franjaHoraria, String ruta) {
    this.pedido = pedido;
    this.fechaProgramada = fechaProgramada;
    this.franjaHoraria = franjaHoraria;
    this.ruta = ruta;
  }

  public Long getId() {
    return id;
  }

  public Pedido getPedido() {
    return pedido;
  }

  public EstadoDespacho getEstado() {
    return estado;
  }

  public LocalDate getFechaProgramada() {
    return fechaProgramada;
  }

  public String getFranjaHoraria() {
    return franjaHoraria;
  }

  public String getRuta() {
    return ruta;
  }

  public LocalDateTime getCreadoEn() {
    return creadoEn;
  }
}
