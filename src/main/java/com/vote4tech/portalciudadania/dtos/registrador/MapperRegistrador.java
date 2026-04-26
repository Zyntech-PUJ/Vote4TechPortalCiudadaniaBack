package com.vote4tech.portalciudadania.dtos.registrador;

import org.springframework.stereotype.Component;

import com.vote4tech.portalciudadania.entities.Registrador;

@Component
public class MapperRegistrador {

  public ResponseRegistradorDTO toResponseDTO(Registrador registrador) {
    if (registrador == null) {
      return null;
    }

    ResponseRegistradorDTO dto = new ResponseRegistradorDTO();
    dto.setIdRegistrador(registrador.getIdRegistrador());
    dto.setNombre(registrador.getNombre());
    dto.setUsuario(registrador.getUsuario());
    return dto;
  }
}
