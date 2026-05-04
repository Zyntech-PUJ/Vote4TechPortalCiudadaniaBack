package com.vote4tech.portalciudadania.entities;

import java.time.LocalDateTime;

import com.vote4tech.portalciudadania.enums.EstadoEleccion;
import com.vote4tech.portalciudadania.enums.TipoEleccion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "eleccion")
public class Eleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eleccion")
    private Long idEleccion;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 32)
    private TipoEleccion tipo;

    @Column(name = "lista_abierta")
    private Boolean listaAbierta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 32)
    private EstadoEleccion estado;
}
