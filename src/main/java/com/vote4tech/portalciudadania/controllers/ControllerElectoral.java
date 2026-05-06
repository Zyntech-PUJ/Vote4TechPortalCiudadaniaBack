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
  public ResponseEntity<ConsultaJuradoDetalleResponse> consultarJurado(@RequestParam String cedula) {
    validarCedula(cedula);

    try {
      ResponseEleccionJuradoDTO jurado = serviceEleccionJurado.findByCedula(cedula);
      String[] ubicacion = getMockUbicacion(cedula);
      String direccion = ubicacion[0];
      String municipio = ubicacion[1];
      String puesto = jurado.getNombreEleccion();
      String mesa = String.valueOf(jurado.getNumeroMesa());
      
      String mensaje = "El ciudadano fue designado como jurado de votación.";
      return ResponseEntity.ok(new ConsultaJuradoDetalleResponse(true, mensaje, puesto, direccion, municipio, mesa));
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.ok(
          new ConsultaJuradoDetalleResponse(false, "Usted no fue designado como jurado de votacion para estas elecciones.", null, null, null, null));
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
      String puesto = jurado.getNombreEleccion();
      
      String[] ubicacion = getMockUbicacion(cedula);
      String direccion = ubicacion[0];
      String municipio = ubicacion[1];

      return ResponseEntity.ok(new LugarVotacionResponse(true, puesto, direccion, municipio, mesa));
    } catch (ResourceNotFoundException ex) {
      return ResponseEntity.ok(new LugarVotacionResponse(false, null, null, null, null));
    }
  }

  private String[] getMockUbicacion(String cedula) {
      char lastDigit = cedula.charAt(cedula.length() - 1);
      switch(lastDigit) {
          case '1':
          case '2':
              return new String[]{"Cra 7 # 9-96", "Bogotá, D.C."};
          case '3':
          case '4':
              return new String[]{"Calle 5 # 38-12", "Medellín, Antioquia"};
          case '5':
          case '6':
              return new String[]{"Cra 44 # 32-15", "Cali, Valle del Cauca"};
          default:
              return new String[]{"Calle 10 # 5-20", "Bucaramanga, Santander"};
      }
  }

  private void validarCedula(String cedula) {
    if (cedula == null || cedula.trim().isEmpty()) {
      throw new BadRequestException("Debe ingresar una cédula válida.");
    }
  }

  public static record ConsultaEstadoResponse(boolean found, String mensaje) {
  }

  public static record ConsultaJuradoDetalleResponse(boolean found, String mensaje, String puesto, String direccion, String municipio, String mesa) {
  }

  public static record LugarVotacionResponse(boolean found, String puesto, String direccion, String municipio,
      String mesa) {
  }
}
