package com.vote4tech.portalciudadania.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEleccionJuradoDTO {
    private Long idAsignacionJurado;
    private String nombreEleccion;
    private String tipoJurado;
    private Integer numeroMesa;
    private String fechaCapacitacion;
    private String estado;
    private String nombreCiudadano;
    private String cedulaCiudadano;
}
