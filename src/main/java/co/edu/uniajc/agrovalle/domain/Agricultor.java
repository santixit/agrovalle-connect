package co.edu.uniajc.agrovalle.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "agricultores")
public class Agricultor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", unique = true)
  private Usuario usuario;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, unique = true, length = 30)
  private String cedula;

  @Column(name = "tipo_documento", length = 30)
  private String tipoDocumento;

  @Column(length = 30)
  private String telefono;

  @Column(nullable = false, length = 80)
  private String municipio;

  protected Agricultor() { }

  public Agricultor(String nombre, String cedula, String municipio) {
    this(null, nombre, cedula, null, null, municipio);
  }

  public Agricultor(String nombre, String cedula, String tipoDocumento, String telefono,
      String municipio) {
    this(null, nombre, cedula, tipoDocumento, telefono, municipio);
  }

  public Agricultor(Usuario usuario, String nombre, String cedula, String municipio) {
    this(usuario, nombre, cedula, null, null, municipio);
  }

  public Agricultor(Usuario usuario, String nombre, String cedula, String tipoDocumento,
      String telefono, String municipio) {
    this.usuario = usuario;
    this.nombre = nombre;
    this.cedula = cedula;
    this.tipoDocumento = tipoDocumento;
    this.telefono = telefono;
    this.municipio = municipio;
  }

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public String getCedula() {
    return cedula;
  }

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public String getTelefono() {
    return telefono;
  }

  public String getMunicipio() {
    return municipio;
  }
}
