package co.edu.uniajc.agrovalle.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 80)
  private String categoria;

  @Column(nullable = false, length = 80)
  private String municipio;

  @Column(nullable = false)
  private boolean activo;

  protected Producto() { }

  public Producto(String nombre, String categoria, String municipio, boolean activo) {
    this.nombre = nombre;
    this.categoria = categoria;
    this.municipio = municipio;
    this.activo = activo;
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
}
