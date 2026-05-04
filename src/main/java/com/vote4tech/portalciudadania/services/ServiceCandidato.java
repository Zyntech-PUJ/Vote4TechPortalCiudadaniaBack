package com.vote4tech.portalciudadania.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vote4tech.portalciudadania.dtos.ResponseCandidatoDTO;
import com.vote4tech.portalciudadania.entities.Candidato;
import com.vote4tech.portalciudadania.exceptions.ResourceNotFoundException;
import com.vote4tech.portalciudadania.repositories.RepositorioCandidato;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceCandidato {

    private final RepositorioCandidato repositorioCandidato;

    public List<ResponseCandidatoDTO> obtenerTodos() {
        return repositorioCandidato.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ResponseCandidatoDTO obtenerPorId(Long id) {
        Candidato candidato = repositorioCandidato.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + id));
        return toDTO(candidato);
    }

    public List<ResponseCandidatoDTO> obtenerPorEleccion(Long idEleccion) {
        return repositorioCandidato.findByEleccion_IdEleccion(idEleccion)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ResponseCandidatoDTO toDTO(Candidato c) {
        return ResponseCandidatoDTO.builder()
                .idCandidato(c.getIdCandidato())
                .nombre(c.getNombre())
                .numero(c.getNumero())
                .fotoUrl(c.getFotoUrl())
                .partidoLogoUrl(c.getPartidoLogoUrl())
                .idEleccion(c.getEleccion() != null ? c.getEleccion().getIdEleccion() : null)
                .build();
    }
}
