package com.vote4tech.portalciudadania.dtos.eleccion;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateEleccionDTO {

  private String nombre;
  private LocalDateTime fechaInicio;
  private LocalDateTime fechaFinalizacion;
  private String tipo;
  private Boolean listaAbierta;
  private Long idRegistrador;  

}
