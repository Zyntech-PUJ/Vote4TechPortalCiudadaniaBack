package com.vote4tech.portalciudadania.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote4tech.portalciudadania.dtos.candidato.ResponseCandidatoDTO;
import com.vote4tech.portalciudadania.services.candidato.IServiceCandidato;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/candidato")
@Tag(name = "Candidato", description = "Gestión de candidatos")
public class ControllerCandidato {

  @Autowired
  IServiceCandidato serviceCandidato;

  @Operation(summary = "Obtener todos los candidatos")
  @GetMapping("/candidatos")
  public ResponseEntity<List<ResponseCandidatoDTO>> obtenerCandidatos() {
    return ResponseEntity.ok(serviceCandidato.findAll());
  }

  @Operation(summary = "Obtener candidato por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Candidato encontrado"),
      @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
  })
  @GetMapping("/{idCandidato}")
  public ResponseEntity<ResponseCandidatoDTO> obtenerCandidatoById(@PathVariable Long idCandidato) {
    return ResponseEntity.ok(serviceCandidato.findById(idCandidato));
  }

}