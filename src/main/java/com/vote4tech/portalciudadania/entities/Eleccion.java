package com.vote4tech.portalciudadania.entities;

import java.time.LocalDateTime;

import com.vote4tech.portalciudadania.enums.EstadoEleccion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ELECCION")
public class Eleccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_eleccion")
  private Long idEleccion;

  @Column(name = "nombre", nullable = false, length = 64)
  private String nombre;

  @Column(name = "fecha_inicio", nullable = false)
  private LocalDateTime fechaInicio;

  @Column(name = "fecha_finalizacion", nullable = false)
  private LocalDateTime fechaFinalizacion;

  @Column(name = "fecha_creacion", nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  /**
   * Tipos permitidos: PRESIDENCIAL, LEGISLATIVA, CONSULTA
   */
  @Column(name = "tipo", nullable = false, length = 32)
  private String tipo;

  @Column(name = "lista_abierta", nullable = false)
  private Boolean listaAbierta;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado", nullable = false, length = 32)
  private EstadoEleccion estado;

  @ManyToOne
  @JoinColumn(name = "id_registrador", referencedColumnName = "id_registrador", nullable = false)
  private Registrador registrador;

}