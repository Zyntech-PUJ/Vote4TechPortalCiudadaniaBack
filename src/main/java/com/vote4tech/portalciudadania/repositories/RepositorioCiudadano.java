package com.vote4tech.portalciudadania.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote4tech.portalciudadania.entities.Ciudadano;

@Repository
public interface RepositorioCiudadano extends JpaRepository<Ciudadano, Long> {

    Optional<Ciudadano> findByCedula(String cedula);
}
