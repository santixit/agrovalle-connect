package co.edu.uniajc.agrovalle.service;

import co.edu.uniajc.agrovalle.api.dto.FincaResponse;
import co.edu.uniajc.agrovalle.api.dto.RegistroFincaRequest;
import co.edu.uniajc.agrovalle.domain.Agricultor;
import co.edu.uniajc.agrovalle.domain.Finca;
import co.edu.uniajc.agrovalle.repository.AgricultorRepository;
import co.edu.uniajc.agrovalle.repository.FincaRepository;
import co.edu.uniajc.agrovalle.domain.Parcela;
import co.edu.uniajc.agrovalle.repository.ParcelaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FincaService {
  private final AgricultorRepository agricultorRepository;
  private final FincaRepository fincaRepository;
  private final ParcelaRepository parcelaRepository;

  public FincaService(AgricultorRepository agricultorRepository, FincaRepository fincaRepository,
      ParcelaRepository parcelaRepository) {
    this.agricultorRepository = agricultorRepository;
    this.fincaRepository = fincaRepository;
    this.parcelaRepository = parcelaRepository;
  }

  @Transactional
  public FincaResponse registrar(Long usuarioId, RegistroFincaRequest request) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    Finca finca = fincaRepository.save(new Finca(agricultor,
        request.nombre().trim(), request.municipio().trim(), request.direccion().trim()));
    parcelaRepository.save(new Parcela(finca, "GENERAL", agricultor));
    return FincaResponse.from(finca);
  }

  @Transactional(readOnly = true)
  public List<FincaResponse> listar(Long usuarioId) {
    Agricultor agricultor = agricultorRepository.findByUsuario_Id(usuarioId)
        .orElseThrow(() -> new AgricultorNoEncontradoException(usuarioId));
    return fincaRepository.findByAgricultor_IdAndActivaTrueOrderByNombreAsc(agricultor.getId())
        .stream().map(FincaResponse::from).toList();
  }
}
