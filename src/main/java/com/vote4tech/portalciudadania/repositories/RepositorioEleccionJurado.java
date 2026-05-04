package com.vote4tech.portalciudadania.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote4tech.portalciudadania.entities.EleccionJurado;

@Repository
public interface RepositorioEleccionJurado extends JpaRepository<EleccionJurado, Long> {

    List<EleccionJurado> findByCiudadano_Cedula(String cedula);

    List<EleccionJurado> findByEleccion_IdEleccion(Long idEleccion);
}
