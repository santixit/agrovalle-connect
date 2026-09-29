package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.AgricultorResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroAgricultorRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Usuario;
import co.edu.uniajc.agrovalle.domain.UsuarioFactory;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Coordinates farmer registration and profile queries. */
@Service
@Transactional(readOnly = true)
public class AgricultorService {

  private final AgricultorRepository agricultorRepository;
  private final UsuarioRepository usuarioRepository;
  private final UsuarioFactory usuarioFactory;

  public AgricultorService(AgricultorRepository agricultorRepository,
      UsuarioRepository usuarioRepository, UsuarioFactory usuarioFactory) {
    this.agricultorRepository = agricultorRepository;
    this.usuarioRepository = usuarioRepository;
    this.usuarioFactory = usuarioFactory;
  }

  @Transactional
  public AgricultorResponse registrar(RegistroAgricultorRequest request) {
    String cedula = request.cedula().trim();
    if (agricultorRepository.existsByCedula(cedula)) {
      throw new CedulaDuplicadaException(cedula);
    }
    if ((request.correo() == null) != (request.contrasena() == null)) {
      throw new DatosCuentaInvalidosException();
    }
    Usuario usuario = null;
    if (request.correo() != null) {
      String correo = request.correo().trim().toLowerCase(java.util.Locale.ROOT);
      if (correo.isBlank()) {
        throw new DatosCuentaInvalidosException();
      }
      if (usuarioRepository.existsByCorreoIgnoreCase(correo)) {
        throw new CorreoDuplicadoException(correo);
      }
      usuario = usuarioRepository.save(usuarioFactory.crearAgricultor(correo,
          request.contrasena()));
    }
    Agricultor agricultor = new Agricultor(usuario, request.nombre().trim(),
        cedula, request.ubicacionValle().trim());
    return AgricultorResponse.from(agricultorRepository.save(agricultor));
  }

  public AgricultorResponse consultar(Long id) {
    Agricultor agricultor = agricultorRepository.findById(id)
        .orElseThrow(() -> new AgricultorNoEncontradoException(id));
    return AgricultorResponse.from(agricultor);
  }
}
