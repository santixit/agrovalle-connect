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
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/** Notification addressed to an application account. */
@Entity
@Table(name = "notificaciones")
public class Notificacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private TipoNotificacion tipo;

  @Column(nullable = false, length = 500)
  private String mensaje;

  @Column(nullable = false)
  private boolean leida;

  @Column(name = "creada_en", nullable = false)
  private LocalDateTime creadaEn = LocalDateTime.now();

  protected Notificacion() { }

  public Notificacion(Usuario usuario, TipoNotificacion tipo, String mensaje) {
    this.usuario = usuario;
    this.tipo = tipo;
    this.mensaje = mensaje;
  }

  public Long getId() {
    return id;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public TipoNotificacion getTipo() {
    return tipo;
  }

  public String getMensaje() {
    return mensaje;
  }

  public boolean isLeida() {
    return leida;
  }

  public LocalDateTime getCreadaEn() {
    return creadaEn;
  }
}
