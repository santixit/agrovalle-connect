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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "productos")
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agricultor_id")
  private Agricultor agricultor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "finca_id")
  private Finca finca;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 80)
  private String categoria;

  @Column(nullable = false, length = 80)
  private String municipio;

  @Column(nullable = false)
  private boolean activo;

  @Column(name = "cantidad_kg", precision = 12, scale = 2)
  private BigDecimal cantidadKg;

  @Column(name = "precio_por_kg", precision = 12, scale = 2)
  private BigDecimal precioPorKg;

  @Column(name = "fecha_cosecha")
  private LocalDate fechaCosecha;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EstadoProducto estado = EstadoProducto.DISPONIBLE;

  protected Producto() { }

  public Producto(String nombre, String categoria, String municipio, boolean activo) {
    this.nombre = nombre;
    this.categoria = categoria;
    this.municipio = municipio;
    this.activo = activo;
    this.estado = activo ? EstadoProducto.DISPONIBLE : EstadoProducto.AGOTADO;
  }

  public Producto(Agricultor agricultor, Finca finca, String nombre, String categoria, String municipio,
      BigDecimal cantidadKg, BigDecimal precioPorKg, LocalDate fechaCosecha) {
    this.agricultor = agricultor;
    this.finca = finca;
    this.nombre = nombre;
    this.categoria = categoria;
    this.municipio = municipio;
    this.cantidadKg = cantidadKg;
    this.precioPorKg = precioPorKg;
    this.fechaCosecha = fechaCosecha;
    this.activo = true;
    this.estado = EstadoProducto.DISPONIBLE;
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getCategoria() {
    return categoria;
  }

  public String getMunicipio() {
    return municipio;
  }

  public boolean isActivo() {
    return activo;
  }

  public Finca getFinca() {
    return finca;
  }

  public Agricultor getAgricultor() {
    return agricultor;
  }

  public BigDecimal getCantidadKg() {
    return cantidadKg;
  }

  public BigDecimal getPrecioPorKg() {
    return precioPorKg;
  }

  public LocalDate getFechaCosecha() {
    return fechaCosecha;
  }

  public EstadoProducto getEstado() {
    return estado;
  }

  public void cambiarEstado(EstadoProducto nuevoEstado) {
    if (nuevoEstado == null) {
      throw new IllegalArgumentException("El estado del producto es obligatorio");
    }
    this.estado = nuevoEstado;
    this.activo = nuevoEstado == EstadoProducto.DISPONIBLE;
  }

  @PrePersist
  @PreUpdate
  private void sincronizarDisponibilidad() {
    if (estado != null) {
      activo = estado == EstadoProducto.DISPONIBLE;
    }
  }
}
