package com.vote4tech.portalciudadania.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vote4tech.portalciudadania.dtos.eleccionjurado.ResponseEleccionJuradoDTO;
import com.vote4tech.portalciudadania.exceptions.BadRequestException;
import com.vote4tech.portalciudadania.exceptions.ResourceNotFoundException;
import com.vote4tech.portalciudadania.services.eleccionjurado.IServiceEleccionJurado;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/electoral")
@Tag(name = "Electoral", description = "Endpoints de consulta pública para el portal de ciudadanía")
public class ControllerElectoral {

  @Autowired
  private IServiceEleccionJurado serviceEleccionJurado;

  @Operation(summary = "Consulta si una cédula está designada como jurado")
  @GetMapping("/consulta-jurado")
  public ResponseEntity<ConsultaEstadoResponse> consultarJurado(@RequestParam String cedula) {
    validarCedula(cedula);

    try {
      serviceEleccionJurado.findByCedula(cedula);
      return ResponseEntity.ok(new ConsultaEstadoResponse(true, "El ciudadano fue designado como jurado de votación."));
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.ok(
          new ConsultaEstadoResponse(false, "No se encontró designación de jurado para el documento ingresado."));
    }
  }

  @Operation(summary = "Consulta sanciones electorales de una cédula")
  @GetMapping("/consulta-sancion")
  public ResponseEntity<ConsultaEstadoResponse> consultarSancion(@RequestParam String cedula) {
    validarCedula(cedula);

    try {
      ResponseEleccionJuradoDTO jurado = serviceEleccionJurado.findByCedula(cedula);
      boolean sancionado = "NO_PRESENTADO".equalsIgnoreCase(jurado.getEstado());

      String mensaje = sancionado
          ? "El ciudadano registra una sanción por inasistencia como jurado."
          : "No se encontraron sanciones para el documento ingresado.";

      return ResponseEntity.ok(new ConsultaEstadoResponse(sancionado, mensaje));
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.ok(new ConsultaEstadoResponse(false, "No se encontraron sanciones para el documento ingresado."));
    }
  }

  @Operation(summary = "Consulta lugar de votación por cédula")
  @GetMapping("/lugar-votacion")
  public ResponseEntity<LugarVotacionResponse> consultarLugarVotacion(@RequestParam String cedula) {
    validarCedula(cedula);

    try {
      ResponseEleccionJuradoDTO jurado = serviceEleccionJurado.findByCedula(cedula);

      String mesa = String.valueOf(jurado.getNumeroMesa());
      String puesto = "Puesto Electoral " + jurado.getNombreEleccion();
      String direccion = null;
      String municipio = null;

      return ResponseEntity.ok(new LugarVotacionResponse(true, puesto, direccion, municipio, mesa));
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.ok(new LugarVotacionResponse(false, null, null, null, null));
    }
  }

  private void validarCedula(String cedula) {
    if (cedula == null || cedula.trim().isEmpty()) {
      throw new BadRequestException("Debe ingresar una cédula válida.");
    }
  }

  public static record ConsultaEstadoResponse(boolean found, String mensaje) {
  }

  public static record LugarVotacionResponse(boolean found, String puesto, String direccion, String municipio,
      String mesa) {
  }
}
