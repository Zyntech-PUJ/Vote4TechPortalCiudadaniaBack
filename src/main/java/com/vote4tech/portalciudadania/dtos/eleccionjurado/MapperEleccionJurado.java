package com.vote4tech.portalciudadania.dtos.eleccionjurado;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vote4tech.portalciudadania.entities.Ciudadano;
import com.vote4tech.portalciudadania.entities.Eleccion;
import com.vote4tech.portalciudadania.entities.EleccionJurado;
import com.vote4tech.portalciudadania.enums.TipoJurado;

@Component
public class MapperEleccionJurado {

  public EleccionJurado toEntity(Ciudadano ciudadano, Eleccion eleccion) {
    if (ciudadano == null || eleccion == null) return null;

    EleccionJurado eleccionJurado = new EleccionJurado();
    eleccionJurado.setCiudadano(ciudadano);
    eleccionJurado.setEleccion(eleccion);

    return eleccionJurado;
  }

  public EleccionJurado toEntity(CreateEleccionJuradoDTO dto, Ciudadano ciudadano, Eleccion eleccion) {
    if (dto == null || ciudadano == null || eleccion == null) return null;

    EleccionJurado eleccionJurado = new EleccionJurado();
    eleccionJurado.setCiudadano(ciudadano);
    eleccionJurado.setEleccion(eleccion);
    eleccionJurado.setTipoJurado(TipoJurado.from(dto.getTipoJurado()));
    eleccionJurado.setNumeroMesa(dto.getNumeroMesa());
    eleccionJurado.setFechaCapacitacion(dto.getFechaCapacitacion());

    return eleccionJurado;
  }

public ResponseEleccionJuradoDTO toResponseDTO(EleccionJurado eleccionJurado) {
    if (eleccionJurado == null) return null;

    ResponseEleccionJuradoDTO dto = new ResponseEleccionJuradoDTO();
    dto.setIdAsignacionJurado(eleccionJurado.getIdAsignacionJurado());
    dto.setNombreEleccion(eleccionJurado.getEleccion().getNombre());
    dto.setTipoJurado(eleccionJurado.getTipoJurado().name());
    dto.setNumeroMesa(eleccionJurado.getNumeroMesa());
    dto.setFechaCapacitacion(eleccionJurado.getFechaCapacitacion());
    dto.setEstado(eleccionJurado.getEstado().name());

    dto.setNombreCiudadano(eleccionJurado.getCiudadano().getNombre());
    dto.setGeneroCiudadano(eleccionJurado.getCiudadano().getGenero());

    return dto;
}

  public List<ResponseEleccionJuradoDTO> toResponseDTOs(List<EleccionJurado> eleccionesJurados) {
    return eleccionesJurados
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
}
