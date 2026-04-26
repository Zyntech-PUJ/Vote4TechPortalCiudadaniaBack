package com.vote4tech.portalciudadania.dtos.eleccion;

import java.time.LocalDateTime;

import com.vote4tech.portalciudadania.dtos.registrador.ResponseRegistradorDTO;
import com.vote4tech.portalciudadania.enums.EstadoEleccion;
import lombok.Data;

@Data
public class ResponseEleccionDTO {

  private Long idEleccion;
  private String nombre;
  private LocalDateTime fechaInicio;
  private LocalDateTime fechaFinalizacion;
  private LocalDateTime fechaCreacion;
  private String tipo;
  private Boolean listaAbierta;
  private EstadoEleccion estado;
  private ResponseRegistradorDTO registrador; 

}
