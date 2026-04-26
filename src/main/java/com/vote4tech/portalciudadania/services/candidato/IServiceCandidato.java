package com.vote4tech.portalciudadania.services.candidato;

import java.util.List;

import com.vote4tech.portalciudadania.dtos.candidato.CreateCandidatoDTO;
import com.vote4tech.portalciudadania.dtos.candidato.ResponseCandidatoDTO;
import com.vote4tech.portalciudadania.dtos.candidato.UpdateCandidatoDTO;

public interface IServiceCandidato {

  public List<ResponseCandidatoDTO> findAll();
  public ResponseCandidatoDTO findById(Long id);
  public ResponseCandidatoDTO addCandidato(CreateCandidatoDTO candidatoDTO);
  public ResponseCandidatoDTO updateCandidato(UpdateCandidatoDTO candidatoDTO);
  public ResponseCandidatoDTO updateCandidato(Long id, UpdateCandidatoDTO candidatoDTO);
  public void deleteById(Long id);
}