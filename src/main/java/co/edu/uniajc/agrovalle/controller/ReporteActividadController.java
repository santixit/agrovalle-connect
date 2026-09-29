package co.edu.uniajc.agrovalle.controller;

import co.edu.uniajc.agrovalle.api.dto.ReporteActividadResponse;
import co.edu.uniajc.agrovalle.service.ReporteActividadService;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/reportes")
public class ReporteActividadController {
  private final ReporteActividadService reporteService;

  public ReporteActividadController(ReporteActividadService reporteService) {
    this.reporteService = reporteService;
  }

  @GetMapping("/actividad")
  public ReporteActividadResponse generar(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
    return reporteService.generar(desde, hasta);
  }
}
