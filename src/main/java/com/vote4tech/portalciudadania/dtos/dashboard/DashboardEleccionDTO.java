package com.vote4tech.portalciudadania.dtos.dashboard;

import lombok.Data;

@Data
public class DashboardEleccionDTO {

  private Long idEleccion;
  private String nombreEleccion;
  private Long totalJurados;
  private Long capacitados;
  private Long pendientes;
  private Long noPresentados;

}
