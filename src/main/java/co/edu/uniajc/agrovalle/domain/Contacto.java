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

/** Buyer inquiry associated with a farmer and a specific offer. */
@Entity
@Table(name = "contactos")
public class Contacto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "comprador_id", nullable = false)
  private Comprador comprador;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agricultor_id", nullable = false)
  private Agricultor agricultor;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "producto_id", nullable = false)
  private Producto producto;

  @Column(nullable = false, length = 1000)
  private String mensaje;

  @Column(length = 1000)
  private String respuesta;

  @Column(name = "respondido_en")
  private LocalDateTime respondidoEn;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EstadoContacto estado = EstadoContacto.NUEVO;

  @Column(name = "creado_en", nullable = false)
  private LocalDateTime creadoEn = LocalDateTime.now();

  protected Contacto() { }

  public Contacto(Comprador comprador, Agricultor agricultor, Producto producto, String mensaje) {
    this.comprador = comprador;
    this.agricultor = agricultor;
    this.producto = producto;
    this.mensaje = mensaje;
  }

  public Long getId() {
    return id;
  }

  public Comprador getComprador() {
    return comprador;
  }

  public Agricultor getAgricultor() {
    return agricultor;
  }

  public Producto getProducto() {
    return producto;
  }

  public String getMensaje() {
    return mensaje;
  }

  public String getRespuesta() {
    return respuesta;
  }

  public LocalDateTime getRespondidoEn() {
    return respondidoEn;
  }

  public void responder(String respuesta) {
    if (estado == EstadoContacto.CERRADO) {
      throw new IllegalStateException("Este contacto está cerrado");
    }
    this.respuesta = respuesta;
    this.respondidoEn = LocalDateTime.now();
    this.estado = EstadoContacto.RESPONDIDO;
  }

  public EstadoContacto getEstado() {
    return estado;
  }

  public LocalDateTime getCreadoEn() {
    return creadoEn;
  }
}
