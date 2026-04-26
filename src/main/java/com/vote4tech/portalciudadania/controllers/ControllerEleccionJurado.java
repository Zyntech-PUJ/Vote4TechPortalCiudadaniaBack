package com.vote4tech.portalciudadania.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote4tech.portalciudadania.dtos.dashboard.DashboardEleccionDTO;
import com.vote4tech.portalciudadania.dtos.eleccionjurado.ResponseEleccionJuradoDTO;
import com.vote4tech.portalciudadania.services.eleccionjurado.IServiceEleccionJurado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/eleccion-jurado")
@Tag(name = "Eleccion Jurado", description = "Gestión del sorteo y listado de jurados por elección")
public class ControllerEleccionJurado {

  @Autowired
  IServiceEleccionJurado serviceEleccionJurado;

  @Operation(summary = "Consultar una asignación de jurado por cédula")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Jurado consultado correctamente"),
      @ApiResponse(responseCode = "404", description = "No existe jurado asignado para la cédula")
  })
  @GetMapping("/cedula/{cedula}")
  public ResponseEntity<ResponseEleccionJuradoDTO> obtenerJuradoPorCedula(@PathVariable String cedula) {
    return ResponseEntity.ok(serviceEleccionJurado.findByCedula(cedula));
  }



}
