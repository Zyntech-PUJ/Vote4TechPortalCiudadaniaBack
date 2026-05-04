package com.vote4tech.portalciudadania.entities;

import java.time.LocalDateTime;

import com.vote4tech.portalciudadania.enums.EstadoEleccionJurado;
import com.vote4tech.portalciudadania.enums.TipoJurado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "eleccion_jurado")
public class EleccionJurado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_jurado")
    private Long idAsignacionJurado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciudadano", referencedColumnName = "id_ciudadano")
    private Ciudadano ciudadano;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_eleccion", referencedColumnName = "id_eleccion")
    private Eleccion eleccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_jurado", length = 32)
    private TipoJurado tipoJurado;

    @Column(name = "numero_mesa")
    private Integer numeroMesa;

    @Column(name = "fecha_capacitacion")
    private LocalDateTime fechaCapacitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 32)
    private EstadoEleccionJurado estado;
}
