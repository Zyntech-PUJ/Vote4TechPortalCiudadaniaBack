package com.vote4tech.portalciudadania.dtos.candidato;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vote4tech.portalciudadania.dtos.registrador.MapperRegistrador;
import com.vote4tech.portalciudadania.entities.Candidato;

@Component
public class MapperCandidato {
  
  @Autowired
  MapperRegistrador mapperRegistrador;

  public Candidato toEntity(CreateCandidatoDTO dto) {
    if (dto == null) return null;

    Candidato candidato = new Candidato();
    candidato.setNombre(dto.getNombre());
    candidato.setNumero(dto.getNumero());
    candidato.setFotoUrl(dto.getFotoUrl());
    candidato.setPartidoLogoUrl(dto.getPartidoLogoUrl());

    return candidato;
  }

  public Candidato toEntity(UpdateCandidatoDTO dto) {
    if (dto == null) return null;

    Candidato candidato = new Candidato();
    candidato.setIdCandidato(dto.getIdCandidato());
    candidato.setNombre(dto.getNombre());
    candidato.setNumero(dto.getNumero());
    candidato.setFotoUrl(dto.getFotoUrl());
    candidato.setPartidoLogoUrl(dto.getPartidoLogoUrl());

    return candidato;
  }

  public ResponseCandidatoDTO toResponseDTO(Candidato candidato) {
    if (candidato == null) return null;

    ResponseCandidatoDTO dto = new ResponseCandidatoDTO();
    dto.setIdCandidato(candidato.getIdCandidato());
    dto.setNombre(candidato.getNombre());
    dto.setNumero(candidato.getNumero());
    dto.setFotoUrl(candidato.getFotoUrl());
    dto.setPartidoLogoUrl(candidato.getPartidoLogoUrl());
    dto.setRegistrador(mapperRegistrador.toResponseDTO(candidato.getRegistrador()));

    return dto;
  }

  public List<ResponseCandidatoDTO> toResponseDTOs(List<Candidato> candidatos) {
    return candidatos
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
}
