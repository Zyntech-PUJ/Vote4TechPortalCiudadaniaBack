package com.vote4tech.portalciudadania.dtos.eleccionjurado;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateEleccionJuradoDTO {

  private String tipoJurado;
  private String cedulaCiudadano;
  private Integer numeroMesa;
  private LocalDateTime fechaCapacitacion;
}
