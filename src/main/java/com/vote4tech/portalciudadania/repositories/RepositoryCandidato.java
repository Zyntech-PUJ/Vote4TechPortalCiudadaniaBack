package com.vote4tech.portalciudadania.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote4tech.portalciudadania.entities.Candidato;


@Repository
public interface RepositoryCandidato extends JpaRepository<Candidato, Long> {
  
  public Candidato findByNumero(String numero);

}
