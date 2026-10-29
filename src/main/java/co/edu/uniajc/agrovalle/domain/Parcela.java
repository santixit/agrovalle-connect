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
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Table(name = "parcelas", uniqueConstraints = @UniqueConstraint(
    name = "uq_parcela_codigo_finca", columnNames = {"finca_id", "codigo"}))
public class Parcela {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "finca_id", nullable = false)
  private Finca finca;

  @Column(nullable = false, length = 80)
  private String codigo;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agricultor_responsable_actual_id", nullable = false)
  private Agricultor agricultorResponsableActual;

  @Column(name = "creada_en", nullable = false)
  private LocalDateTime creadaEn = LocalDateTime.now();

  protected Parcela() { }

  public Parcela(Finca finca, String codigo, Agricultor agricultorResponsableActual) {
    this.finca = finca;
    this.codigo = codigo;
    this.agricultorResponsableActual = agricultorResponsableActual;
  }

  public Long getId() {
    return id;
  }

  public Finca getFinca() {
    return finca;
  }

  public String getCodigo() {
    return codigo;
  }

  public Agricultor getAgricultorResponsableActual() {
    return agricultorResponsableActual;
  }

  public LocalDateTime getCreadaEn() {
    return creadaEn;
  }

  public void transferirResponsabilidadA(Agricultor agricultorNuevo) {
    if (agricultorNuevo == null || agricultorNuevo.getId() == null
        || agricultorResponsableActual.getId().equals(agricultorNuevo.getId())) {
      throw new IllegalArgumentException("Selecciona otro agricultor como responsable");
    }
    agricultorResponsableActual = agricultorNuevo;
  }
}
