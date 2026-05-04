package com.vote4tech.portalciudadania.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote4tech.portalciudadania.dtos.ResponseEleccionDTO;
import com.vote4tech.portalciudadania.services.ServiceEleccion;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/eleccion")
@RequiredArgsConstructor
public class ControllerEleccion {

    private final ServiceEleccion serviceEleccion;

    @GetMapping("/elecciones")
    public ResponseEntity<List<ResponseEleccionDTO>> obtenerElecciones() {
        return ResponseEntity.ok(serviceEleccion.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEleccionDTO> obtenerEleccion(@PathVariable Long id) {
        return ResponseEntity.ok(serviceEleccion.obtenerPorId(id));
    }
}
