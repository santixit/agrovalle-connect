package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.AgricultorResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroAgricultorRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Coordinates farmer registration and profile queries. */
@Service
@Transactional(readOnly = true)
public class AgricultorService {

  private final AgricultorRepository agricultorRepository;

  public AgricultorService(AgricultorRepository agricultorRepository) {
    this.agricultorRepository = agricultorRepository;
  }

  @Transactional
  public AgricultorResponse registrar(RegistroAgricultorRequest request) {
    String cedula = request.cedula().trim();
    if (agricultorRepository.existsByCedula(cedula)) {
      throw new CedulaDuplicadaException(cedula);
    }
    Agricultor agricultor = new Agricultor(request.nombre().trim(),
        cedula, request.ubicacionValle().trim());
    return AgricultorResponse.from(agricultorRepository.save(agricultor));
  }

  public AgricultorResponse consultar(Long id) {
    Agricultor agricultor = agricultorRepository.findById(id)
        .orElseThrow(() -> new AgricultorNoEncontradoException(id));
    return AgricultorResponse.from(agricultor);
  }
}
