package com.vote4tech.portalciudadania.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote4tech.portalciudadania.entities.Ciudadano;

@Repository
public interface RepositoryCiudadano extends JpaRepository<Ciudadano, Long> {

  List<Ciudadano> findAllByOrderByIdCiudadanoAsc();

  java.util.Optional<Ciudadano> findByCedula(String cedula);
}
