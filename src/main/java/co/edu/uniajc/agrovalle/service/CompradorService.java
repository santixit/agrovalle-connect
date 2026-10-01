package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.CompradorResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroCompradorRequest;
import co.edu.uniajc.agrovalle.domain.Comprador;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.domain.UsuarioFactory;
import co.edu.uniajc.agrovalle.repository.CompradorRepository;
import co.edu.uniajc.agrovalle.repository.UsuarioRepository;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompradorService {
  private final CompradorRepository compradorRepository;
  private final UsuarioRepository usuarioRepository;
  private final UsuarioFactory usuarioFactory;

  public CompradorService(CompradorRepository compradorRepository,
      UsuarioRepository usuarioRepository, UsuarioFactory usuarioFactory) {
    this.compradorRepository = compradorRepository;
    this.usuarioRepository = usuarioRepository;
    this.usuarioFactory = usuarioFactory;
  }

  @Transactional
  public CompradorResponse registrar(RegistroCompradorRequest request) {
    String correo = request.correo().trim().toLowerCase(Locale.ROOT);
    if (usuarioRepository.existsByCorreoIgnoreCase(correo)) {
      throw new CorreoDuplicadoException(correo);
    }
    Usuario usuario = usuarioRepository.save(usuarioFactory.crearComprador(correo,
        request.contrasena()));
    Comprador comprador = new Comprador(usuario, request.nombre().trim(), request.telefono(),
        request.tipoComercio());
    return CompradorResponse.from(compradorRepository.save(comprador));
  }
}
