package com.vote4tech.portalciudadania.dtos.candidato;

import com.vote4tech.portalciudadania.dtos.registrador.ResponseRegistradorDTO;
import lombok.Data;

@Data
public class ResponseCandidatoDTO {

  private Long idCandidato;
  private String nombre;
  private String numero;
  private String fotoUrl;
  private String partidoLogoUrl;
  private ResponseRegistradorDTO registrador;
  
}
