package com.vote4tech.portalciudadania.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote4tech.portalciudadania.entities.Candidato;

@Repository
public interface RepositorioCandidato extends JpaRepository<Candidato, Long> {

    List<Candidato> findByLista_Eleccion_IdEleccion(Long idEleccion);
}
