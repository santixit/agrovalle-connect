package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.ParcelaResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroParcelaRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Parcela;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import co.edu.uniajc.agrovalle.repository.ParcelaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParcelaService {
  private final AgricultorRepository agricultorRepository;
  private final FincaRepository fincaRepository;
  private final ParcelaRepository parcelaRepository;

  public ParcelaService(AgricultorRepository agricultorRepository,
      FincaRepository fincaRepository, ParcelaRepository parcelaRepository) {
    this.agricultorRepository = agricultorRepository;
    this.fincaRepository = fincaRepository;
    this.parcelaRepository = parcelaRepository;
  }

  @Transactional(readOnly = true)
  public List<ParcelaResponse> mias(Long usuarioId) {
    return parcelaRepository
        .findByAgricultorResponsableActual_Usuario_IdOrderByFinca_NombreAscCodigoAsc(usuarioId)
        .stream().map(ParcelaResponse::from).toList();
  }

  @Transactional
  public ParcelaResponse crear(Long usuarioId, Long fincaId, RegistroParcelaRequest request) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    var finca = fincaRepository.findById(fincaId)
        .orElseThrow(() -> new FincaNoEncontradaException(fincaId));
    boolean puedeAdministrar = finca.getAgricultor().getId().equals(agricultor.getId())
        || parcelaRepository.existsByFinca_IdAndAgricultorResponsableActual_Id(
            fincaId, agricultor.getId());
    if (!puedeAdministrar) {
      throw new OfertaNoPropiaException(fincaId);
    }
    String codigo = request.codigo().trim();
    if (codigo.equalsIgnoreCase("GENERAL") || parcelaRepository.existsByFinca_IdAndCodigo(
        fincaId, codigo)) {
      throw new IllegalStateException("Ese código de parcela ya existe en la finca");
    }
    return ParcelaResponse.from(parcelaRepository.save(new Parcela(finca, codigo, agricultor)));
  }
}
