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

/** A message exchanged by the buyer and farmer in a contact conversation. */
@Entity
@Table(name = "mensajes_contacto")
public class MensajeContacto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "contacto_id", nullable = false)
  private Contacto contacto;

  @Column(nullable = false, length = 20)
  private String autor;

  @Column(nullable = false, length = 1000)
  private String mensaje;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  protected MensajeContacto() { }

  public MensajeContacto(Contacto contacto, String autor, String mensaje) {
    this.contacto = contacto;
    this.autor = autor;
    this.mensaje = mensaje;
  }

  public Long getId() {
    return id;
  }

  public String getAutor() {
    return autor;
  }

  public String getMensaje() {
    return mensaje;
  }

  public LocalDateTime getCreadoEn() {
    return creadoEn;
  }
}
