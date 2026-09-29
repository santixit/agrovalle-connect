package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.ReporteActividadResponse;
import co.edu.uniajc.agrovalle.repository.ContactoRepository;
import co.edu.uniajc.agrovalle.repository.ProductoRepository;
import co.edu.uniajc.agrovalle.repository.UsuarioRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReporteActividadService {
  private final UsuarioRepository usuarioRepository;
  private final ProductoRepository productoRepository;
  private final ContactoRepository contactoRepository;

  public ReporteActividadService(UsuarioRepository usuarioRepository,
      ProductoRepository productoRepository, ContactoRepository contactoRepository) {
    this.usuarioRepository = usuarioRepository;
    this.productoRepository = productoRepository;
    this.contactoRepository = contactoRepository;
  }

  public ReporteActividadResponse generar(LocalDate desde, LocalDate hasta) {
    if (desde.isAfter(hasta)) {
      throw new RangoFechasInvalidoException();
    }
    LocalDateTime inicio = desde.atStartOfDay();
    LocalDateTime fin = hasta.plusDays(1).atStartOfDay().minusNanos(1);
    return new ReporteActividadResponse(desde, hasta,
        usuarioRepository.countByCreadoEnBetween(inicio, fin),
        productoRepository.countByCreadoEnBetween(inicio, fin),
        contactoRepository.countByCreadoEnBetween(inicio, fin));
  }
}
