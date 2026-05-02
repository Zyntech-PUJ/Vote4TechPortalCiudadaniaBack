package com.vote4tech.portalciudadania.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PARTIDO")
public class Partido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_partido")
  private Long idPartido;

  @Column(name = "nombre", nullable = false, length = 64)
  private String nombre;

  @Column(name = "sigla", nullable = false, length = 16)
  private String sigla;

  @Column(name = "logo_url", nullable = false)
  private String logoUrl;

  @Column(name = "fecha_creacion", nullable = false)
  private LocalDateTime fechaCreacion;

  @OneToMany(mappedBy = "partido")
  private List<Candidato> candidatos;
}
