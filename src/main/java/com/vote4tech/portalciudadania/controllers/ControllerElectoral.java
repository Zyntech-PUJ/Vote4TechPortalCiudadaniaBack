package com.vote4tech.portalciudadania.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vote4tech.portalciudadania.dtos.ConsultaSancionResponseDTO;
import com.vote4tech.portalciudadania.dtos.ResponseEleccionJuradoDTO;
import com.vote4tech.portalciudadania.services.ServiceEleccionJurado;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/electoral")
@RequiredArgsConstructor
public class ControllerElectoral {

    private final ServiceEleccionJurado serviceEleccionJurado;

    /**
     * Consultada directamente por el Portal Ciudadanía Front (consulta-sancion.service.ts).
     * Verifica si un ciudadano tiene sanciones por no presentarse como jurado.
     */
    @GetMapping("/consulta-sancion")
    public ResponseEntity<ConsultaSancionResponseDTO> consultarSancion(@RequestParam String cedula) {
        return ResponseEntity.ok(serviceEleccionJurado.consultarSancion(cedula));
    }

    /**
     * Consulta si un ciudadano está asignado como jurado (todas sus asignaciones).
     */
    @GetMapping("/consulta-jurado/{cedula}")
    public ResponseEntity<List<ResponseEleccionJuradoDTO>> consultarJurado(@PathVariable String cedula) {
        return ResponseEntity.ok(serviceEleccionJurado.obtenerPorCedula(cedula));
    }

    /**
     * Lista todos los jurados de una elección específica.
     */
    @GetMapping("/jurados-eleccion/{idEleccion}")
    public ResponseEntity<List<ResponseEleccionJuradoDTO>> juradosPorEleccion(@PathVariable Long idEleccion) {
        return ResponseEntity.ok(serviceEleccionJurado.obtenerPorEleccion(idEleccion));
    }
}
