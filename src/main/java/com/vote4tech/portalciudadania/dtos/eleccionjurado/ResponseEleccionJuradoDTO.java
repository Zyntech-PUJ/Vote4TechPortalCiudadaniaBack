package com.vote4tech.portalciudadania.dtos.eleccionjurado;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResponseEleccionJuradoDTO {

  private Long idAsignacionJurado;
  private String nombreEleccion;
  private String tipoJurado;
  private Integer numeroMesa;
  private LocalDateTime fechaCapacitacion;
  private String estado;
  private String nombreCiudadano;
  private String generoCiudadano;
}
