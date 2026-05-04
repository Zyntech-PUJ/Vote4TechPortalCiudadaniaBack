package com.vote4tech.portalciudadania.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCandidatoDTO {
    private Long idCandidato;
    private String nombre;
    private String numero;
    private String fotoUrl;
    private String partidoLogoUrl;
    private Long idEleccion;
}
