package com.vote4tech.portalciudadania.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya hay datos en la tabla eleccion
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM eleccion", Integer.class);
        if (count != null && count > 0) {
            System.out.println("Datos de prueba ya existen, omitiendo inicialización.");
            return;
        }

        System.out.println("Inicializando datos de prueba...");

        // Ejecutar el script de datos de prueba
        String sql = """
            -- Limpiar datos anteriores para evitar errores de duplicados si el script falló a medias
            DELETE FROM jurado;
            DELETE FROM censo_electoral_snapshot;
            DELETE FROM mesa_votacion;
            DELETE FROM puesto_votacion;
            DELETE FROM centro_votacion;
            DELETE FROM ciudadano;
            DELETE FROM eleccion;
            DELETE FROM municipio;
            DELETE FROM departamento;

            -- Insertar datos de la elección
            INSERT INTO eleccion (id, nombre) VALUES
            ('eeeeeeee-0000-0000-0000-000000000001', 'Elecciones Congreso 2026');

            -- Insertar departamento y municipio
            INSERT INTO departamento (id, nombre) VALUES
            (1, 'Cundinamarca');

            INSERT INTO municipio (id, nombre, departamento_id) VALUES
            (11001, 'Bogota', 1);

            -- Insertar centro de votación
            INSERT INTO centro_votacion (id, nombre, direccion, municipio_id) VALUES
            ('cccccccc-0000-0000-0000-000000000001', 'Colegio San Bartolome', 'Cra 7 # 9-96', 11001);

            -- Insertar puesto de votación
            INSERT INTO puesto_votacion (id, codigo, capacidad, centro_id) VALUES
            ('dddddddd-0000-0000-0000-000000000001', 'P-01', 5000, 'cccccccc-0000-0000-0000-000000000001');

            -- Insertar mesa de votación
            INSERT INTO mesa_votacion (id, numero_mesa, puesto_id) VALUES
            ('bbbbbbbb-0000-0000-0000-000000000001', 1, 'dddddddd-0000-0000-0000-000000000001');

            -- Insertar ciudadanos
            INSERT INTO ciudadano (
                id, tipo_doc, numero_doc, nombres, apellidos, fecha_nacimiento,
                municipio_residencia_id, discapacidad_certificada, comunidad_indigena,
                poblacion_indigena_nombre, derechos_politicos_estado, causal_inhabilitacion,
                email, password_hash, rol, activo, created_at, updated_at
            ) VALUES
            ('11111111-0000-0000-0000-000000000001', 'CC', '1001001001', 'Juan', 'Perez', '1990-05-15',
             11001, false, false, null, 'ACTIVO', null,
             'juan.perez@example.com', 'hash_falso_123', 'CIUDADANO', true, NOW(), NOW()),

            ('22222222-0000-0000-0000-000000000002', 'CC', '2002002002', 'Maria', 'Gomez', '1985-08-20',
             11001, true, false, null, 'ACTIVO', null,
             'maria.gomez@example.com', 'hash_falso_123', 'CIUDADANO', true, NOW(), NOW()),

            ('33333333-0000-0000-0000-000000000003', 'CC', '4004004004', 'Ana', 'Martinez', '1975-03-25',
             11001, false, true, 'Embera', 'ACTIVO', null,
             'ana.martinez@example.com', 'hash_falso_123', 'CIUDADANO', true, NOW(), NOW());

            -- Insertar censo electoral snapshot
            INSERT INTO censo_electoral_snapshot (id, eleccion_id, ciudadano_id, mesa_id, puesto_id, centro_id, municipio_id, departamento_id) VALUES
            ('aaaaaaaa-0000-0000-0000-000000000001', 'eeeeeeee-0000-0000-0000-000000000001', '11111111-0000-0000-0000-000000000001', 'bbbbbbbb-0000-0000-0000-000000000001', 'dddddddd-0000-0000-0000-000000000001', 'cccccccc-0000-0000-0000-000000000001', 11001, 1),
            ('aaaaaaaa-0000-0000-0000-000000000002', 'eeeeeeee-0000-0000-0000-000000000001', '22222222-0000-0000-0000-000000000002', 'bbbbbbbb-0000-0000-0000-000000000001', 'dddddddd-0000-0000-0000-000000000001', 'cccccccc-0000-0000-0000-000000000001', 11001, 1),
            ('aaaaaaaa-0000-0000-0000-000000000003', 'eeeeeeee-0000-0000-0000-000000000001', '33333333-0000-0000-0000-000000000003', 'bbbbbbbb-0000-0000-0000-000000000001', 'dddddddd-0000-0000-0000-000000000001', 'cccccccc-0000-0000-0000-000000000001', 11001, 1);

            -- Insertar jurado
            INSERT INTO jurado (id, ciudadano_id, eleccion_id, mesa_id, puesto_id, centro_id, municipio_id, departamento_id) VALUES
            ('99999999-0000-0000-0000-000000000001', '11111111-0000-0000-0000-000000000001', 'eeeeeeee-0000-0000-0000-000000000001', 'bbbbbbbb-0000-0000-0000-000000000001', 'dddddddd-0000-0000-0000-000000000001', 'cccccccc-0000-0000-0000-000000000001', 11001, 1);
            """;

        // Ejecutar el SQL
        jdbcTemplate.execute(sql);

        System.out.println("Datos de prueba inicializados correctamente.");
    }
}