package com.vote4tech.portalciudadania.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote4tech.portalciudadania.entities.Eleccion;

@Repository
public interface RepositorioEleccion extends JpaRepository<Eleccion, Long> {
}
