package co.edu.uniajc.agrovalle.api.dto;

import java.time.LocalDate;

public record ReporteActividadResponse(LocalDate desde, LocalDate hasta,
    long usuariosRegistrados, long ofertasPublicadas, long contactosRegistrados) { }
