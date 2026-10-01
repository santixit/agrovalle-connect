package co.edu.uniajc.agrovalle.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/** Shared credentials and role for farmer, buyer, and administrator accounts. */
@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 254)
  private String correo;

  @Column(name = "password_hash", nullable = false, length = 100)
  private String passwordHash;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private RolUsuario rol;

  @Column(nullable = false)
  private boolean activo = true;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  protected Usuario() { }

  public Usuario(String correo, String passwordHash, RolUsuario rol) {
    this.correo = correo;
    this.passwordHash = passwordHash;
    this.rol = rol;
  }

  public Long getId() {
    return id;
  }

  public String getCorreo() {
    return correo;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public RolUsuario getRol() {
    return rol;
  }

  public boolean isActivo() {
    return activo;
  }

  public LocalDateTime getCreadoEn() {
    return creadoEn;
  }
}
