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

/** Farm owned by a registered farmer. */
@Entity
@Table(name = "fincas")
public class Finca {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agricultor_id", nullable = false)
  private Agricultor agricultor;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 80)
  private String municipio;

  @Column(nullable = false, length = 200)
  private String direccion;

  @Column(nullable = false)
  private boolean activa = true;

  @Column(name = "creada_en", nullable = false)
  private LocalDateTime creadaEn = LocalDateTime.now();

  protected Finca() { }

  public Finca(Agricultor agricultor, String nombre, String municipio, String direccion) {
    this.agricultor = agricultor;
    this.nombre = nombre;
    this.municipio = municipio;
    this.direccion = direccion;
  }

  public Long getId() {
    return id;
  }

  public Agricultor getAgricultor() {
    return agricultor;
  }

  public String getNombre() {
    return nombre;
  }

  public String getMunicipio() {
    return municipio;
  }

  public String getDireccion() {
    return direccion;
  }

  public boolean isActiva() {
    return activa;
  }

  public LocalDateTime getCreadaEn() {
    return creadaEn;
  }
}
