package com.vote4tech.portalciudadania.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "CANDIDATO")
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_candidato")
    private Long idCandidato;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "numero")
    private String numero;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_registrador", referencedColumnName = "id_registrador")
    private Registrador registrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lista", referencedColumnName = "id_lista")
    private Lista lista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_partido", referencedColumnName = "id_partido")
    private Partido partido;
}
