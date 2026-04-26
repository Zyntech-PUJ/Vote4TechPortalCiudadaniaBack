package com.vote4tech.portalciudadania.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "REGISTRADOR")
public class Registrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registrador")
    private Long idRegistrador;

    @Column(name = "nombre", nullable = false, length = 32)
    private String nombre;

    @Column(name = "usuario", nullable = false, unique = true, length = 32)
    private String usuario;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "registrador", cascade = CascadeType.ALL)
    private List<Eleccion> elecciones;
}
