package com.vote4tech.portalciudadania.dtos.eleccion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vote4tech.portalciudadania.dtos.registrador.MapperRegistrador;
import com.vote4tech.portalciudadania.entities.Eleccion;


@Component
public class MapperEleccion {
  
  @Autowired
  MapperRegistrador mapperRegistrador;
  
  public Eleccion toEntity(CreateEleccionDTO dto) {
    if (dto == null) return null;

    Eleccion eleccion = new Eleccion();
    eleccion.setNombre(dto.getNombre());
    eleccion.setFechaInicio(dto.getFechaInicio());
    eleccion.setFechaFinalizacion(dto.getFechaFinalizacion());
    eleccion.setTipo(dto.getTipo());
    eleccion.setListaAbierta(dto.getListaAbierta());

    return eleccion;
  }

  public Eleccion toEntity(UpdateEleccionDTO dto) {
    if (dto == null) return null;

    Eleccion eleccion = new Eleccion();
    eleccion.setIdEleccion(dto.getIdEleccion());
    eleccion.setNombre(dto.getNombre());
    eleccion.setFechaInicio(dto.getFechaInicio());
    eleccion.setFechaFinalizacion(dto.getFechaFinalizacion());
    eleccion.setTipo(dto.getTipo());
    eleccion.setListaAbierta(dto.getListaAbierta());

    return eleccion;
  }

  public ResponseEleccionDTO toResponseDTO(Eleccion eleccion) {
    if(eleccion == null) return null;

    ResponseEleccionDTO dto = new ResponseEleccionDTO();
    dto.setIdEleccion(eleccion.getIdEleccion());
    dto.setNombre(eleccion.getNombre());
    dto.setFechaInicio(eleccion.getFechaInicio());
    dto.setFechaFinalizacion(eleccion.getFechaFinalizacion());
    dto.setFechaCreacion(eleccion.getFechaCreacion());
    dto.setTipo(eleccion.getTipo());
    dto.setListaAbierta(eleccion.getListaAbierta());
    dto.setEstado(eleccion.getEstado());
    dto.setRegistrador(mapperRegistrador.toResponseDTO(eleccion.getRegistrador()));

    return dto; 
  }

  public List<ResponseEleccionDTO> toResponseDTOs(List<Eleccion> elecciones){
    return elecciones
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
}
