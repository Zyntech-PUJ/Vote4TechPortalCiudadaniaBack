package com.vote4tech.portalciudadania.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote4tech.portalciudadania.dtos.ResponseCandidatoDTO;
import com.vote4tech.portalciudadania.services.ServiceCandidato;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/candidato")
@RequiredArgsConstructor
public class ControllerCandidato {

    private final ServiceCandidato serviceCandidato;

    @GetMapping("/candidatos")
    public ResponseEntity<List<ResponseCandidatoDTO>> obtenerCandidatos() {
        return ResponseEntity.ok(serviceCandidato.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCandidatoDTO> obtenerCandidato(@PathVariable Long id) {
        return ResponseEntity.ok(serviceCandidato.obtenerPorId(id));
    }

    @GetMapping("/por-eleccion/{idEleccion}")
    public ResponseEntity<List<ResponseCandidatoDTO>> obtenerPorEleccion(@PathVariable Long idEleccion) {
        return ResponseEntity.ok(serviceCandidato.obtenerPorEleccion(idEleccion));
    }
}
