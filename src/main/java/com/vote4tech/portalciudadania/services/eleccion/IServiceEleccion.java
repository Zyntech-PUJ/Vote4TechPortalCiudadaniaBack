package com.vote4tech.portalciudadania.services.eleccion;

import java.util.List;

import com.vote4tech.portalciudadania.dtos.eleccion.CreateEleccionDTO;
import com.vote4tech.portalciudadania.dtos.eleccion.ResponseEleccionDTO;
import com.vote4tech.portalciudadania.dtos.eleccion.UpdateEleccionDTO;

public interface IServiceEleccion {

  public List<ResponseEleccionDTO> findAll();
  public ResponseEleccionDTO findById(Long id);
  public ResponseEleccionDTO addEleccion(CreateEleccionDTO eleccionDTO);
  public ResponseEleccionDTO updateEleccion(UpdateEleccionDTO eleccionDTO);
  public ResponseEleccionDTO updateEleccion(Long id, UpdateEleccionDTO eleccionDTO);
  public ResponseEleccionDTO lanzarEleccion(Long id);
  public ResponseEleccionDTO finalizarEleccion(Long id);
  public void deleteById(Long id);

}
