package com.vote4tech.portalciudadania.services.eleccionjurado;

import java.util.List;

import com.vote4tech.portalciudadania.dtos.dashboard.DashboardEleccionDTO;
import com.vote4tech.portalciudadania.dtos.eleccionjurado.CreateEleccionJuradoDTO;
import com.vote4tech.portalciudadania.dtos.eleccionjurado.ResponseEleccionJuradoDTO;

public interface IServiceEleccionJurado {

  ResponseEleccionJuradoDTO findByCedula(String cedula);
  ResponseEleccionJuradoDTO addEleccionJurado(Long idEleccion, CreateEleccionJuradoDTO eleccionJuradoDTO);
  List<ResponseEleccionJuradoDTO> generarSorteo(Long idEleccion);
  DashboardEleccionDTO getDashboard(Long idEleccion);

}
