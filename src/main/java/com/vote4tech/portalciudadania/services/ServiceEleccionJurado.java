package com.vote4tech.portalciudadania.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vote4tech.portalciudadania.dtos.ConsultaSancionResponseDTO;
import com.vote4tech.portalciudadania.dtos.ResponseEleccionJuradoDTO;
import com.vote4tech.portalciudadania.entities.EleccionJurado;
import com.vote4tech.portalciudadania.enums.EstadoEleccionJurado;
import com.vote4tech.portalciudadania.repositories.RepositorioEleccionJurado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceEleccionJurado {

    private final RepositorioEleccionJurado repositorioEleccionJurado;

    public List<ResponseEleccionJuradoDTO> obtenerPorCedula(String cedula) {
        return repositorioEleccionJurado.findByCiudadano_Cedula(cedula)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ResponseEleccionJuradoDTO> obtenerPorEleccion(Long idEleccion) {
        return repositorioEleccionJurado.findByEleccion_IdEleccion(idEleccion)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ConsultaSancionResponseDTO consultarSancion(String cedula) {
        List<EleccionJurado> asignaciones = repositorioEleccionJurado.findByCiudadano_Cedula(cedula);

        if (asignaciones.isEmpty()) {
            return ConsultaSancionResponseDTO.builder()
                    .found(false)
                    .mensaje("No se encontraron registros de jurado para el documento ingresado.")
                    .build();
        }

        boolean tieneSancion = asignaciones.stream()
                .anyMatch(j -> EstadoEleccionJurado.NO_PRESENTADO.equals(j.getEstado()));

        if (tieneSancion) {
            return ConsultaSancionResponseDTO.builder()
                    .found(true)
                    .mensaje("El ciudadano registra una sanción por no presentarse como jurado de votación.")
                    .build();
        }

        return ConsultaSancionResponseDTO.builder()
                .found(false)
                .mensaje("No se encontraron sanciones para el documento ingresado.")
                .build();
    }

    private ResponseEleccionJuradoDTO toDTO(EleccionJurado ej) {
        return ResponseEleccionJuradoDTO.builder()
                .idAsignacionJurado(ej.getIdAsignacionJurado())
                .nombreEleccion(ej.getEleccion() != null ? ej.getEleccion().getNombre() : null)
                .tipoJurado(ej.getTipoJurado() != null ? ej.getTipoJurado().name() : null)
                .numeroMesa(ej.getNumeroMesa())
                .fechaCapacitacion(ej.getFechaCapacitacion() != null ? ej.getFechaCapacitacion().toString() : null)
                .estado(ej.getEstado() != null ? ej.getEstado().name() : null)
                .nombreCiudadano(ej.getCiudadano() != null ? ej.getCiudadano().getNombre() : null)
                .cedulaCiudadano(ej.getCiudadano() != null ? ej.getCiudadano().getCedula() : null)
                .build();
    }
}
