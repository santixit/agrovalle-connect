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

  @Column(nullable = false, length = 80)
  private String municipio;

  protected Agricultor() { }

  public Agricultor(String nombre, String cedula, String municipio) {
    this.nombre = nombre;
    this.cedula = cedula;
    this.municipio = municipio;
  }

  public Agricultor(Usuario usuario, String nombre, String cedula, String municipio) {
    this.usuario = usuario;
    this.nombre = nombre;
    this.cedula = cedula;
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

  public String getMunicipio() {
    return municipio;
  }
}
