package com.vote4tech.portalciudadania.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vote4tech.portalciudadania.entities.Candidato;
import com.vote4tech.portalciudadania.entities.Ciudadano;
import com.vote4tech.portalciudadania.entities.Eleccion;
import com.vote4tech.portalciudadania.entities.EleccionJurado;
import com.vote4tech.portalciudadania.entities.Registrador;
import com.vote4tech.portalciudadania.enums.EstadoEleccion;
import com.vote4tech.portalciudadania.enums.EstadoEleccionJurado;
import com.vote4tech.portalciudadania.enums.TipoJurado;
import com.vote4tech.portalciudadania.repositories.RepositoryCandidato;
import com.vote4tech.portalciudadania.repositories.RepositoryCiudadano;
import com.vote4tech.portalciudadania.repositories.RepositoryEleccion;
import com.vote4tech.portalciudadania.repositories.RepositoryEleccionJurado;
import com.vote4tech.portalciudadania.repositories.RepositoryRegistrador;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private RepositoryRegistrador repositoryRegistrador;

    @Autowired
    private RepositoryEleccion repositoryEleccion;

    @Autowired
    private RepositoryCandidato repositoryCandidato;

        @Autowired
        private RepositoryCiudadano repositoryCiudadano;

        @Autowired
        private RepositoryEleccionJurado repositoryEleccionJurado;

    @Value("${app.seed-data.enabled:true}")
    private boolean seedDataEnabled;

    @Override
    public void run(String... args) throws Exception {
        if (!seedDataEnabled) {
            LOGGER.info("Inicializacion de datos de prueba deshabilitada (app.seed-data.enabled=false).");
            return;
        }

        LOGGER.info("Inicializando datos de prueba...");

        Registrador registrador = repositoryRegistrador.findByUsuario("seed_admin");
        if (registrador == null) {
            registrador = Registrador.builder()
                    .nombre("Registrador Seed")
                    .usuario("seed_admin")
                    .password("seed123")
                    .build();
            registrador = repositoryRegistrador.save(registrador);
            LOGGER.info("Registrador de prueba creado: {}", registrador.getUsuario());
        }

        if (repositoryEleccion.count() == 0) {
            LocalDateTime now = LocalDateTime.now();
            List<Eleccion> elecciones = List.of(
                    Eleccion.builder()
                            .nombre("Elecciones Presidenciales 2026")
                            .fechaInicio(now.plusDays(10))
                            .fechaFinalizacion(now.plusDays(11))
                            .fechaCreacion(now)
                            .tipo("PRESIDENCIAL")
                            .listaAbierta(false)
                            .estado(EstadoEleccion.CONFIGURACION)
                            .registrador(registrador)
                            .build(),
                    Eleccion.builder()
                            .nombre("Elecciones Legislativas 2026")
                            .fechaInicio(now.plusDays(20))
                            .fechaFinalizacion(now.plusDays(21))
                            .fechaCreacion(now)
                            .tipo("LEGISLATIVA")
                            .listaAbierta(true)
                            .estado(EstadoEleccion.LANZADA)
                            .registrador(registrador)
                            .build(),
                    Eleccion.builder()
                            .nombre("Consulta Nacional 2026")
                            .fechaInicio(now.plusDays(30))
                            .fechaFinalizacion(now.plusDays(31))
                            .fechaCreacion(now)
                            .tipo("CONSULTA")
                            .listaAbierta(false)
                            .estado(EstadoEleccion.EN_CURSO)
                            .registrador(registrador)
                            .build());

            repositoryEleccion.saveAll(elecciones);
            LOGGER.info("Elecciones de prueba creadas: {}", elecciones.size());
        }

        if (repositoryCandidato.count() == 0) {
            List<Candidato> candidatos = List.of(
                    Candidato.builder()
                            .nombre("Laura Martinez")
                            .numero("01")
                            .fotoUrl("https://example.com/candidatos/laura-martinez.jpg")
                            .partidoLogoUrl("https://example.com/partidos/alianza-verde.png")
                            .registrador(registrador)
                            .build(),
                    Candidato.builder()
                            .nombre("Carlos Rojas")
                            .numero("02")
                            .fotoUrl("https://example.com/candidatos/carlos-rojas.jpg")
                            .partidoLogoUrl("https://example.com/partidos/centro-democratico.png")
                            .registrador(registrador)
                            .build(),
                    Candidato.builder()
                            .nombre("Ana Pineda")
                            .numero("03")
                            .fotoUrl("https://example.com/candidatos/ana-pineda.jpg")
                            .partidoLogoUrl("https://example.com/partidos/pacto-historico.png")
                            .registrador(registrador)
                            .build(),
                    Candidato.builder()
                            .nombre("Miguel Herrera")
                            .numero("04")
                            .fotoUrl("https://example.com/candidatos/miguel-herrera.jpg")
                            .partidoLogoUrl("https://example.com/partidos/liberal.png")
                            .registrador(registrador)
                            .build());

            repositoryCandidato.saveAll(candidatos);
            LOGGER.info("Candidatos de prueba creados: {}", candidatos.size());
        }

                seedCiudadanos();
                seedJurados();

        LOGGER.info("Datos de prueba inicializados correctamente.");
    }

        private void seedCiudadanos() {
                ensureCiudadano("Juan Perez Garcia", "1001001001", "M", true);
                ensureCiudadano("Roberto Moreno Herrera", "1011011011", "M", true);
                ensureCiudadano("Ludwing Acero Palacio", "1013013013", "M", true);
        }

        private void ensureCiudadano(String nombre, String cedula, String genero, boolean votoObligatorio) {
                if (repositoryCiudadano.findByCedula(cedula).isPresent()) {
                        return;
                }

                Ciudadano ciudadano = Ciudadano.builder()
                                .nombre(nombre)
                                .cedula(cedula)
                                .genero(genero)
                                .votoObligatorio(votoObligatorio)
                                .build();
                repositoryCiudadano.save(ciudadano);
                LOGGER.info("Ciudadano seed creado: {}", cedula);
        }

        private void seedJurados() {
                List<Eleccion> elecciones = repositoryEleccion.findAll();
                Optional<Eleccion> eleccionPresidencial = elecciones.stream()
                                .filter(e -> "Elecciones Presidenciales 2026".equals(e.getNombre()))
                                .findFirst();
                Optional<Eleccion> eleccionConsulta = elecciones.stream()
                                .filter(e -> "Consulta Nacional 2026".equals(e.getNombre()))
                                .findFirst();

                if (eleccionPresidencial.isEmpty() || eleccionConsulta.isEmpty()) {
                        LOGGER.warn("No se pudieron sembrar jurados: faltan elecciones seed esperadas.");
                        return;
                }

                createJuradoIfMissing("1001001001", eleccionPresidencial.get(), TipoJurado.URNA, 3,
                                EstadoEleccionJurado.CAPACITADO, eleccionPresidencial.get().getFechaInicio().minusDays(2));

                createJuradoIfMissing("1011011011", eleccionConsulta.get(), TipoJurado.DOMICILIO, 6,
                                EstadoEleccionJurado.NO_PRESENTADO, eleccionConsulta.get().getFechaInicio().minusDays(3));
        }

        private void createJuradoIfMissing(
                        String cedula,
                        Eleccion eleccion,
                        TipoJurado tipo,
                        Integer numeroMesa,
                        EstadoEleccionJurado estado,
                        LocalDateTime fechaCapacitacion) {

                if (repositoryEleccionJurado.findFirstByCiudadano_CedulaOrderByIdAsignacionJuradoAsc(cedula).isPresent()) {
                        return;
                }

                Optional<Ciudadano> ciudadanoOpt = repositoryCiudadano.findByCedula(cedula);
                if (ciudadanoOpt.isEmpty()) {
                        LOGGER.warn("No se pudo crear jurado seed: cedula {} no existe.", cedula);
                        return;
                }

                EleccionJurado jurado = EleccionJurado.builder()
                                .ciudadano(ciudadanoOpt.get())
                                .eleccion(eleccion)
                                .tipoJurado(tipo)
                                .numeroMesa(numeroMesa)
                                .fechaCapacitacion(fechaCapacitacion)
                                .estado(estado)
                                .build();

                repositoryEleccionJurado.save(jurado);
                LOGGER.info("Jurado seed creado para cedula {} con estado {}", cedula, estado);
        }
}