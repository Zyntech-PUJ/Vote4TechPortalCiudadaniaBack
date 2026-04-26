package com.vote4tech.portalciudadania.services.candidato;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vote4tech.portalciudadania.dtos.candidato.CreateCandidatoDTO;
import com.vote4tech.portalciudadania.dtos.candidato.MapperCandidato;
import com.vote4tech.portalciudadania.dtos.candidato.ResponseCandidatoDTO;
import com.vote4tech.portalciudadania.dtos.candidato.UpdateCandidatoDTO;
import com.vote4tech.portalciudadania.entities.Candidato;
import com.vote4tech.portalciudadania.entities.Registrador;
import com.vote4tech.portalciudadania.exceptions.ResourceNotFoundException;
import com.vote4tech.portalciudadania.repositories.RepositoryCandidato;
import com.vote4tech.portalciudadania.repositories.RepositoryRegistrador;

@Service
public class ServiceCandidato implements IServiceCandidato {

  @Autowired
  RepositoryCandidato repositoryCandidato;

  @Autowired
  RepositoryRegistrador repositoryRegistrador;

  @Autowired
  MapperCandidato mapperCandidato;

  @Override
  public List<ResponseCandidatoDTO> findAll() {
    return mapperCandidato.toResponseDTOs(repositoryCandidato.findAll());
  }

  @Override
  public ResponseCandidatoDTO findById(Long id) {
    Candidato candidato = repositoryCandidato.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + id));

    return mapperCandidato.toResponseDTO(candidato);
  }

  @Override
  public ResponseCandidatoDTO addCandidato(CreateCandidatoDTO candidatoDTO) {
    Registrador registrador = repositoryRegistrador.findById(candidatoDTO.getIdRegistrador())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Registrador no encontrado con id: " + candidatoDTO.getIdRegistrador()));

    Candidato candidato = mapperCandidato.toEntity(candidatoDTO);
    candidato.setRegistrador(registrador);

    return mapperCandidato.toResponseDTO(repositoryCandidato.save(candidato));
  }

  @Override
  public ResponseCandidatoDTO updateCandidato(UpdateCandidatoDTO candidatoDTO) {
    Candidato candidatoUpdate = mapperCandidato.toEntity(candidatoDTO);

    return mapperCandidato.toResponseDTO(candidatoUpdate);
  }

  @Override
  public ResponseCandidatoDTO updateCandidato(Long id, UpdateCandidatoDTO candidatoDTO) {
    Candidato candidato = repositoryCandidato.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + id));

    Registrador registrador = repositoryRegistrador.findById(candidatoDTO.getIdRegistrador())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Registrador no encontrado con id: " + candidatoDTO.getIdRegistrador()));

    candidato.setNombre(candidatoDTO.getNombre());
    candidato.setNumero(candidatoDTO.getNumero());
    candidato.setFotoUrl(candidatoDTO.getFotoUrl());
    candidato.setPartidoLogoUrl(candidatoDTO.getPartidoLogoUrl());
    candidato.setRegistrador(registrador);

    return mapperCandidato.toResponseDTO(repositoryCandidato.save(candidato));
  }

  @Override
  public void deleteById(Long id) {
    if (!repositoryCandidato.existsById(id))
      throw new ResourceNotFoundException("Candidato no encontrado con id: " + id);
    repositoryCandidato.deleteById(id);
  }

}
