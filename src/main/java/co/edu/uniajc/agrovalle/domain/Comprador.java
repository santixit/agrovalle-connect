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
import java.time.LocalDateTime;

/** Buyer profile linked to a role-bearing account. */
@Entity
@Table(name = "compradores")
public class Comprador {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "usuario_id", nullable = false, unique = true)
  private Usuario usuario;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(length = 30)
  private String telefono;

  @Column(name = "tipo_comercio", length = 80)
  private String tipoComercio;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  protected Comprador() { }

  public Comprador(Usuario usuario, String nombre, String telefono, String tipoComercio) {
    this.usuario = usuario;
    this.nombre = nombre;
    this.telefono = telefono;
    this.tipoComercio = tipoComercio;
  }

  public Long getId() {
    return id;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public String getNombre() {
    return nombre;
  }

  public String getTelefono() {
    return telefono;
  }

  public String getTipoComercio() {
    return tipoComercio;
  }

  public LocalDateTime getCreadoEn() {
    return creadoEn;
  }
}
