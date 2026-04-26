package com.vote4tech.portalciudadania.dtos.candidato;

import lombok.Data;

@Data
public class UpdateCandidatoDTO {

  private Long idCandidato;
  private String nombre;
  private String numero;
  private String fotoUrl;
  private String partidoLogoUrl;
  private Long idRegistrador;

}
