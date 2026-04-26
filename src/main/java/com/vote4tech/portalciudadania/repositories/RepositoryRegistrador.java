package com.vote4tech.portalciudadania.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote4tech.portalciudadania.entities.Registrador;


@Repository
public interface RepositoryRegistrador extends JpaRepository<Registrador, Long> {

  public Registrador findByUsuario(String usuario);

}