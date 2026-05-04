package com.vote4tech.portalciudadania.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vote4tech.portalciudadania.dtos.ResponseEleccionDTO;
import com.vote4tech.portalciudadania.entities.Eleccion;
import com.vote4tech.portalciudadania.exceptions.ResourceNotFoundException;
import com.vote4tech.portalciudadania.repositories.RepositorioEleccion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceEleccion {

    private final RepositorioEleccion repositorioEleccion;

    public List<ResponseEleccionDTO> obtenerTodas() {
        return repositorioEleccion.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ResponseEleccionDTO obtenerPorId(Long id) {
        Eleccion eleccion = repositorioEleccion.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Elección no encontrada con id: " + id));
        return toDTO(eleccion);
    }

    private ResponseEleccionDTO toDTO(Eleccion e) {
        return ResponseEleccionDTO.builder()
                .idEleccion(e.getIdEleccion())
                .nombre(e.getNombre())
                .fechaInicio(e.getFechaInicio() != null ? e.getFechaInicio().toString() : null)
                .fechaFinalizacion(e.getFechaFinalizacion() != null ? e.getFechaFinalizacion().toString() : null)
                .fechaCreacion(e.getFechaCreacion() != null ? e.getFechaCreacion().toString() : null)
                .tipo(e.getTipo() != null ? e.getTipo().name() : null)
                .listaAbierta(e.getListaAbierta())
                .estado(e.getEstado() != null ? e.getEstado().name() : null)
                .build();
    }
}
