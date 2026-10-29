package co.edu.uniajc.agrovalle.domain;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "producto", fetch = FetchType.LAZY,
      cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
  private ProductoUbicacion ubicacion;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 80)
  private String categoria;

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

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  protected Producto() { }

  public Producto(String nombre, String categoria, String municipio, boolean activo) {
    this.nombre = nombre;
    this.categoria = categoria;
    this.setUbicacion(ProductoUbicacion.sinFinca(this, null, municipio));
    this.activo = activo;
    this.estado = activo ? EstadoProducto.DISPONIBLE : EstadoProducto.AGOTADO;
  }

  public Producto(Agricultor agricultor, Finca finca, String nombre, String categoria, String municipio,
      BigDecimal cantidadKg, BigDecimal precioPorKg, LocalDate fechaCosecha) {
    this.nombre = nombre;
    this.categoria = categoria;
    if (finca == null) {
      this.setUbicacion(ProductoUbicacion.sinFinca(this, agricultor, municipio));
    } else {
      this.setUbicacion(ProductoUbicacion.enParcela(this,
          new Parcela(finca, "LEGACY", agricultor)));
    }
    this.cantidadKg = cantidadKg;
    this.precioPorKg = precioPorKg;
    this.fechaCosecha = fechaCosecha;
    this.activo = true;
    this.estado = EstadoProducto.DISPONIBLE;
  }

  public static Producto enParcela(Parcela parcela, String nombre, String categoria,
      BigDecimal cantidadKg, BigDecimal precioPorKg, LocalDate fechaCosecha) {
    Producto producto = new Producto();
    producto.nombre = nombre;
    producto.categoria = categoria;
    producto.cantidadKg = cantidadKg;
    producto.precioPorKg = precioPorKg;
    producto.fechaCosecha = fechaCosecha;
    producto.activo = true;
    producto.estado = EstadoProducto.DISPONIBLE;
    producto.setUbicacion(ProductoUbicacion.enParcela(producto, parcela));
    return producto;
  }

  public static Producto sinFinca(Agricultor agricultor, String municipio, String nombre,
      String categoria, BigDecimal cantidadKg, BigDecimal precioPorKg, LocalDate fechaCosecha) {
    Producto producto = new Producto();
    producto.nombre = nombre;
    producto.categoria = categoria;
    producto.cantidadKg = cantidadKg;
    producto.precioPorKg = precioPorKg;
    producto.fechaCosecha = fechaCosecha;
    producto.activo = true;
    producto.estado = EstadoProducto.DISPONIBLE;
    producto.setUbicacion(ProductoUbicacion.sinFinca(producto, agricultor, municipio));
    return producto;
  }

  private void setUbicacion(ProductoUbicacion nuevaUbicacion) {
    this.ubicacion = nuevaUbicacion;
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
    return ubicacion == null ? null : ubicacion.getMunicipio();
  }

  public boolean isActivo() {
    return activo;
  }

  public Finca getFinca() {
    return ubicacion == null || ubicacion.getParcela() == null
        ? null : ubicacion.getParcela().getFinca();
  }

  public Agricultor getAgricultor() {
    return ubicacion == null ? null : ubicacion.getAgricultorActual();
  }

  public Parcela getParcela() {
    return ubicacion == null ? null : ubicacion.getParcela();
  }

  public ProductoUbicacion getUbicacion() {
    return ubicacion;
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

  public LocalDateTime getCreadoEn() {
    return creadoEn;
  }

  public void cambiarEstado(EstadoProducto nuevoEstado) {
    if (nuevoEstado == null) {
      throw new IllegalArgumentException("El estado del producto es obligatorio");
    }
    this.estado = nuevoEstado;
    this.activo = nuevoEstado == EstadoProducto.DISPONIBLE;
  }

  public void actualizarCantidad(BigDecimal cantidad) {
    if (cantidad == null || cantidad.signum() < 0) {
      throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
    }
    this.cantidadKg = cantidad;
    if (cantidad.signum() == 0) {
      cambiarEstado(EstadoProducto.AGOTADO);
    } else if (estado == EstadoProducto.AGOTADO || estado == EstadoProducto.RESERVADO) {
      cambiarEstado(EstadoProducto.DISPONIBLE);
    }
  }

  @PrePersist
  @PreUpdate
  private void sincronizarDisponibilidad() {
    if (estado != null) {
      activo = estado == EstadoProducto.DISPONIBLE;
    }
  }
}
