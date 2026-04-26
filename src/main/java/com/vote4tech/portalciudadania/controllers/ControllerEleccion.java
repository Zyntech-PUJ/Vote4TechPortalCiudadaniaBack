package com.vote4tech.portalciudadania.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote4tech.portalciudadania.dtos.eleccion.ResponseEleccionDTO;
import com.vote4tech.portalciudadania.services.eleccion.IServiceEleccion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/eleccion")
@Tag(name = "Eleccion", description = "Gestión de elecciones y su ciclo de vida")
public class ControllerEleccion {

  @Autowired
  IServiceEleccion serviceEleccion;

  @Operation(summary = "Obtener todas las elecciones")
  @GetMapping("/elecciones")
  public ResponseEntity<List<ResponseEleccionDTO>> obtenerElecciones() {
    return ResponseEntity.ok(serviceEleccion.findAll());
  }

  @Operation(summary = "Obtener elección por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Elección encontrada"),
      @ApiResponse(responseCode = "404", description = "Elección no encontrada")
  })
  @GetMapping("/{idEleccion}")
  public ResponseEntity<ResponseEleccionDTO> obtenerEleccionById(@PathVariable Long idEleccion) {
    return ResponseEntity.ok(serviceEleccion.findById(idEleccion));
  }

}
