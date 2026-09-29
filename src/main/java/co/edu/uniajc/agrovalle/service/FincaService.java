package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.FincaResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroFincaRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Finca;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FincaService {
  private final AgricultorRepository agricultorRepository;
  private final FincaRepository fincaRepository;

  public FincaService(AgricultorRepository agricultorRepository, FincaRepository fincaRepository) {
    this.agricultorRepository = agricultorRepository;
    this.fincaRepository = fincaRepository;
  }

  @Transactional
  public FincaResponse registrar(Long usuarioId, RegistroFincaRequest request) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    return FincaResponse.from(fincaRepository.save(new Finca(agricultor,
        request.nombre().trim(), request.municipio().trim(), request.direccion().trim())));
  }

  @Transactional(readOnly = true)
  public List<FincaResponse> listar(Long usuarioId) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    return fincaRepository.findByAgricultor_IdAndActivaTrueOrderByNombreAsc(agricultor.getId())
        .stream().map(FincaResponse::from).toList();
  }
}
