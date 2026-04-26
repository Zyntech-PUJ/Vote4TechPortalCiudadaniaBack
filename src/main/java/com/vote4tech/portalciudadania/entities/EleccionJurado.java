package com.vote4tech.portalciudadania.entities;

import java.time.LocalDateTime;

import com.vote4tech.portalciudadania.enums.EstadoEleccionJurado;
import com.vote4tech.portalciudadania.enums.TipoJurado;
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
@Table(name = "ELECCION_JURADO")
public class EleccionJurado {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_asignacion_jurado")
  private Long idAsignacionJurado;

  @ManyToOne
  @JoinColumn(name = "id_ciudadano", referencedColumnName = "id_ciudadano", nullable = false)
  private Ciudadano ciudadano;

  @ManyToOne
  @JoinColumn(name = "id_eleccion", referencedColumnName = "id_eleccion", nullable = false)
  private Eleccion eleccion;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_jurado", nullable = false, length = 32)
  private TipoJurado tipoJurado;

  @Column(name = "numero_mesa", nullable = false)
  private Integer numeroMesa;

  @Column(name = "fecha_capacitacion", nullable = false)
  private LocalDateTime fechaCapacitacion;

  @Column(name = "estado", nullable = false, length = 32)
  private EstadoEleccionJurado estado;
}
