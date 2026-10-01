package co.edu.uniajc.agrovalle.domain;

public record ContactoRegistradoEvent(Long usuarioAgricultorId, Long contactoId,
    String nombreProducto) { }
