package co.edu.uniajc.agrovalle.domain;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto_ubicacion")
public class ProductoUbicacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "producto_id", nullable = false, unique = true)
  private Producto producto;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "parcela_id")
  private Parcela parcela;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agricultor_sin_finca_id")
  private Agricultor agricultorSinFinca;

  @Column(name = "municipio_sin_finca", length = 80)
  private String municipioSinFinca;

  protected ProductoUbicacion() { }

  private ProductoUbicacion(Producto producto, Parcela parcela,
      Agricultor agricultorSinFinca, String municipioSinFinca) {
    this.producto = producto;
    this.parcela = parcela;
    this.agricultorSinFinca = agricultorSinFinca;
    this.municipioSinFinca = municipioSinFinca;
  }

  public static ProductoUbicacion enParcela(Producto producto, Parcela parcela) {
    if (parcela == null) {
      throw new IllegalArgumentException("La parcela es obligatoria");
    }
    return new ProductoUbicacion(producto, parcela, null, null);
  }

  public static ProductoUbicacion sinFinca(Producto producto, Agricultor agricultor,
      String municipio) {
    if (municipio == null || municipio.isBlank()) {
      throw new IllegalArgumentException("El municipio es obligatorio para un producto sin finca");
    }
    return new ProductoUbicacion(producto, null, agricultor, municipio.trim());
  }

  public Long getId() {
    return id;
  }

  public Producto getProducto() {
    return producto;
  }

  public Parcela getParcela() {
    return parcela;
  }

  public Agricultor getAgricultorActual() {
    return parcela == null ? agricultorSinFinca : parcela.getAgricultorResponsableActual();
  }

  public String getMunicipio() {
    return parcela == null ? municipioSinFinca : parcela.getFinca().getMunicipio();
  }

  public String getNombreFinca() {
    return parcela == null ? null : parcela.getFinca().getNombre();
  }

  public String getCodigoParcela() {
    return parcela == null ? null : parcela.getCodigo();
  }

  public Agricultor getAgricultorSinFinca() {
    return agricultorSinFinca;
  }

  public void transferirTitularSinFinca(Agricultor agricultorNuevo) {
    if (parcela != null) {
      throw new IllegalStateException("El titular se administra desde la parcela");
    }
    agricultorSinFinca = agricultorNuevo;
  }
}
