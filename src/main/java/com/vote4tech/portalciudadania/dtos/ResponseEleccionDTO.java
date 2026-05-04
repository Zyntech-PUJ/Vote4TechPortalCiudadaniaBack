package com.vote4tech.portalciudadania.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEleccionDTO {
    private Long idEleccion;
    private String nombre;
    private String fechaInicio;
    private String fechaFinalizacion;
    private String fechaCreacion;
    private String tipo;
    private Boolean listaAbierta;
    private String estado;
}
