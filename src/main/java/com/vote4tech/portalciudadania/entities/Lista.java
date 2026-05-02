package com.vote4tech.portalciudadania.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
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
@Table(name = "LISTA")
public class Lista {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_lista")
  private Long idLista;

  @Column(name = "tipo", nullable = false, length = 32)
  private String tipo;

  @Column(name = "fecha_creacion", nullable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "fecha_modificacion", nullable = false)
  private LocalDateTime fechaModificacion;

  @ManyToOne
  @JoinColumn(name = "id_eleccion", referencedColumnName = "id_eleccion", nullable = false)
  private Eleccion eleccion;
}
