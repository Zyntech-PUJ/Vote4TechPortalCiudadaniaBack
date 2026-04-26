package com.vote4tech.portalciudadania.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote4tech.portalciudadania.entities.Eleccion;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface RepositoryEleccion extends JpaRepository<Eleccion, Long> {

  List<Eleccion> findByEstado(String estado);
  List<Eleccion> findByFechaInicio(LocalDateTime fechaInicio);
}