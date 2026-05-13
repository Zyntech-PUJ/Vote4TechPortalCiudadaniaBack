
-- Limpiar datos anteriores (en orden de dependencias inversas)
DELETE FROM eleccion_jurado;
DELETE FROM usuarios_roles;
DELETE FROM candidato;
DELETE FROM lista;
DELETE FROM eleccion;
DELETE FROM ciudadano;
DELETE FROM usuarios_entity;
DELETE FROM rol;
DELETE FROM partido;

-- ============================================================================
-- 1. ROLES (ROL)
-- ============================================================================
INSERT INTO rol (nombre) VALUES
('ADMINISTRADOR_ELECTORAL'),
('REGISTRADOR'),
('CONSEJO_NACIONAL'),
('CIUDADANO');

-- ============================================================================
-- 2. USUARIOS_ENTITY (Usuarios base para autenticación)
-- ============================================================================
INSERT INTO usuarios_entity (usuario, password) VALUES
('consejo_nacional', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P6.8DO');

-- ============================================================================
-- 3. ADMINISTRADOR_ELECTORAL (Crea elecciones)
-- ============================================================================
INSERT INTO administrador_electoral (usuario, password, id_usuario_entity) VALUES
('admin_electoral_1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P6.8DO', 1),
('admin_electoral_2', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P6.8DO', 2);
USUARIOS_ROLES (Asignar roles a usuarios)
-- ============================================================================
-- Consejo Nacional
INSERT INTO usuarios_roles (id_usuario_entity, id_rol) VALUES
(1SERT INTO partido (nombre, sigla, logo_url, fecha_creacion) VALUES
('Partido Progresista', 'PP', 'https://via.placeholder.com/100?text=PP', NOW()),
('Movimiento Liberal', 'ML', 'https://via.placeholder.com/100?text=ML', NOW()),
('Alianza Democrática', 'AD', 'https://via.placeholder.com/100?text=AD', NOW()),
('Partido Conservador', 'PC', 'https://via.placeholder.com/100?text=PC', NOW()),
('Izquierda Unida', 'IU', 'https://via.placeholder.com/100?text=IU', NOW()),
('Movimiento Verde', 'MV', 'https://via.placeholder.com/100?text=MV', NOW());

-- ============================================================================
-- 7. CIUDADANOS (Con diferentes géneros)
-- ============================================================================
INSERT INTO ciudadano (nombre, cedula, genero, voto_obligatorio) VALUES
-- Ciudadanos que serán JURADOS CAPACITADOS
('Juan Pérez García', '1001001001', 'M', true),
('María Gómez López', '1002002002', 'F', true),
('Pedro Silva Ramírez', '1003003003', 'M', true),
('Elena Vargas Martínez', '1004004004', 'F', true),
('Carlos Ruiz Hernández', '1005005005', 'M', true),
('Sofía Díaz García', '1006006006', 'F', true),

-- Ciudadanos que serán JURADOS PENDIENTES
('Francisco Torres López', '1007007007', 'M', true),
('Mariana Flores Sánchez', '1008008008', 'F', true),
('Alejandro Rivera González', '1009009009', 'M', true),
('Valentina García Rodríguez', '1010010010', 'F', true),

-- 4iudadanos que serán JURADOS NO PRESENTADOS (sancionados)
('Roberto Moreno Herrera', '1011011011', 'M', true),
('Patricia Sánchez Guzmán', '1012012012', 'F', true),

-- Ciudadanos que NO serán jurados (sin designación)
('Ludwing Acero Palacio', '1013013013', 'M', true),
('Yadira Quintero Vélez', '1014014014', 'F', true),
('Jorge Mejía López', '1015015015', 'M', true),
('Catalina Pulido Reyes', '1016016016', 'F', true),
('Andrés Castillo Vargas', '1017017017', 'M', true),
('Marcela Parra Núñez', '1018018018', 'F', true),
('V5ctor Duarte García', '1019019019', 'M', true),
('Cristina Navarro López', '1020020020', 'F', true);

-- ============================================================================
-- 8. ELECCIONES (En diferentes estados - creadas por ADMINISTRADOR_ELECTORAL)
-- ============================================================================
-- Elección en estado CONFIGURACION (editable)
INSERT INTO eleccion (nombre, fecha_inicio, fecha_finalizacion, fecha_creacion, tipo, lista_abierta, estado, id_administrador_electoral) VALUES
('Elecciones Congreso 2026 - CONFIGURACION', 
 '2026-06-15 07:00:00', '2026-06-15 18:00:00', NOW(), 'LEGISLATIVA', true, 'CONFIGURACION', 1);

-- Elección en estado LANZADA (inmutable)
INSERT INTO eleccion (nombre, fecha_inicio, fecha_finalizacion, fecha_creacion, tipo, lista_abierta, estado, id_administrador_electoral) VALUES
('Elecciones Congreso 2026 - LANZADA', 
 '2026-06-20 07:00:00', '2026-06-20 18:00:00', NOW(), 'LEGISLATIVA', true, 'LANZADA', 1);

-- Elección en estado EN_CURSO (jornada activa)
INSERT INTO eleccion (nombre, fecha_inicio, fecha_finalizacion, fecha_creacion, tipo, lista_abierta, estado, id_administrador_electoral) VALUES
('Elecciones Presidenciales 2026 - EN CURSO', 
 '2026-05-12 07:00:00', '2026-05-12 18:00:00', NOW(), 'PRESIDENCIAL', false, 'EN_CURSO', 1);

-- Elección en estado FINALIZADA
INSERT INTO eleccion (nombre, fecha_inicio, fecha_finalizacion, fecha_creacion, tipo, lista_abierta, estado, id_administrador_electoral) VALUES
('Elecciones Municipales 2025 - FINALIZADA', 
 '2025-10-29 07:00:00', '2025-10-29 18:00:00', '2025-09-01', 'LEGISLATIVA', true, 'FINALIZADA', 2);

-- ============================================================================
-- 6. ELECCIONES (En diferentes estados)
-- ============================================================================
-- Elección en estado CONFIGURACION (editable)
INSERT INTO eleccion (nombre, fecha_inicio, fecha_finalizacion, fecha_creacion, tipo, lista_abierta, estado, id_administrador_electoral) VALUES
('Elecciones Congreso 2026 - CONFIGURACION', 
 '2026-06-15 07:00:00', '2026-06-15 18:00:00', NOW(), 'LEGISLATIVA', true, 'CONFIGURACION', NULL);

-- Elección en estado LANZADA (inmutable)
INSERT INTO eleccion (nombre, fecha_inicio, fecha_finalizacion, fecha_creacion, tipo, lista_abierta, estado, id_administrador_electoral) VALUES
('Elecciones Congreso 2026 - LANZADA', 
 '2026-06-20 07:00:00', '2026-06-20 18:00:00', NOW(), 'LEGISLATIVA', true, 'LANZADA', NULL);

-- Elección en estado EN_CURSO (jornada activa)
INSERT INTO eleccion (nombre, fecha_inicio, fecha_finalizacion, fecha_creacion, tipo, lista_abierta, estado, id_administrador_electoral) VALUES
('Elecciones Presidenciales 2026 - EN CURSO', 
 '2026-05-12 07:00:00', '2026-05-12 18:00:00', NOW(), 'PRESIDENCIAL', false, 'EN_CURSO', NULL);

-- Elección en estado FINALIZADA
INSERT INTO eleccion (nombre, fecha_inicio, fecha_finalizacion, fecha_creacion, tipo, lista_abierta, estado, id_administrador_electoral) VALUES
('Elecciones Municipales 2025 - FINALIZADA', 
 '2025-10-29 07:00:00', '2025-10-29 18:00:00', '2025-09-01', 'LEGISLATIVA', true, 'FINALIZADA', NULL

-- ============================================================================
-- 70. CANDIDATOS (Registrados por REGISTRADOR, pertenecen a LISTA y PARTIDO)
-- ============================================================================
-- Candidatos para Lista 1 Congreso 2026 CONFIGURACION
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Carlos López', '1', 'https://via.placeholder.com/150?text=Carlos', true, 1, 1, 1),
('Sandra Rodríguez', '2', 'https://via.placeholder.com/150?text=Sandra', true, 1, 1, 2),
('Miguel Fernández', '3', 'https://via.placeholder.com/150?text=Miguel', true, 1, 1, 3),
('Lucía Gómez', '4', 'https://via.placeholder.com/150?text=Lucia', true, 1, 1, 4),
('Roberto Martínez', '5', 'https://via.placeholder.com/150?text=Roberto', true, 1, 1, 5);

-- Candidatos para Lista 2 Congreso 2026 CONFIGURACION
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Diana López Sánchez', '6', 'https://via.placeholder.com/150?text=Diana', true, 1, 2, 6),
('Felipe Gutiérrez', '7', 'https://via.placeholder.com/150?text=Felipe', true, 1, 2, 1),
('Natalia Herrera', '8', 'https://via.placeholder.com/150?text=Natalia', true, 1, 2, 2),
('Antonio González', '9', 'https://via.placeholder.com/150?text=Antonio', true, 1, 2, 3),
('Francisca Torres', '10', 'https://via.placeholder.com/150?text=Francisca', true, 1, 2, 4);

-- Candidatos para Listas Congreso 2026 LANZADA
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Pablo Mendoza', '1', 'https://via.placeholder.com/150?text=Pablo', true, 1, 3, 1),
('Gabriela Reyes', '2', 'https://via.placeholder.com/150?text=Gabriela', true, 1, 3, 2),
('Enrique Soto', '3', 'https://via.placeholder.com/150?text=Enrique', true, 1, 4, 3),
('V8. CANDIDATOS (Registrados, pertenecen a LISTA y PARTIDO)
-- ============================================================================
-- Candidatos para Lista 1 Congreso 2026 CONFIGURACION
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Carlos López', '1', 'https://via.placeholder.com/150?text=Carlos', true, NULL, 1, 1),
('Sandra Rodríguez', '2', 'https://via.placeholder.com/150?text=Sandra', true, NULL, 1, 2),
('Miguel Fernández', '3', 'https://via.placeholder.com/150?text=Miguel', true, NULL, 1, 3),
('Lucía Gómez', '4', 'https://via.placeholder.com/150?text=Lucia', true, NULL, 1, 4),
('Roberto Martínez', '5', 'https://via.placeholder.com/150?text=Roberto', true, NULL, 1, 5);

-- Candidatos para Lista 2 Congreso 2026 CONFIGURACION
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Diana López Sánchez', '6', 'https://via.placeholder.com/150?text=Diana', true, NULL, 2, 6),
('Felipe Gutiérrez', '7', 'https://via.placeholder.com/150?text=Felipe', true, NULL, 2, 1),
('N9alia Herrera', '8', 'https://via.placeholder.com/150?text=Natalia', true, NULL, 2, 2),
('Antonio González', '9', 'https://via.placeholder.com/150?text=Antonio', true, NULL, 2, 3),
('Francisca Torres', '10', 'https://via.placeholder.com/150?text=Francisca', true, NULL, 2, 4);

-- Candidatos para Listas Congreso 2026 LANZADA
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Pablo Mendoza', '1', 'https://via.placeholder.com/150?text=Pablo', true, NULL, 3, 1),
('Gabriela Reyes', '2', 'https://via.placeholder.com/150?text=Gabriela', true, NULL, 3, 2),
('Enrique Soto', '3', 'https://via.placeholder.com/150?text=Enrique', true, NULL, 4, 3),
('Valentina Cross', '4', 'https://via.placeholder.com/150?text=Valentina', true, NULL, 4, 4);

-- Candidatos para Elecciones Presidenciales 2026 EN_CURSO
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Valentina Silva', '1', 'https://via.placeholder.com/150?text=Valentina', true, NULL, 5, 1),
('Edmundo Castillo', '2', 'https://via.placeholder.com/150?text=Edmundo', true, NULL, 5, 2),
('Gabriela Moreno', '3', 'https://via.placeholder.com/150?text=Gabriela', true, NULL, 6, 3);

-- Candidatos para Elecciones Municipales 2025 FINALIZADA
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Javier Rodríguez', '1', 'https://via.placeholder.com/150?text=Javier', false, NULL, 7, 1),
('Mariana López', '2', 'https://via.placeholder.com/150?text=Mariana', false, NULL
(12, 4, 'DOMICILIO', 6, '2025-10-29 10:00:00', 'NO_PRESENTADO');

-- Jurados para Congreso 2026 LANZADA
INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado) VALUES
(1, 2, 'URNA', 1, '2026-06-18 10:00:00', 'CAPACITADO'),
(2, 2, 'URNA', 2, '2026-06-18 11:00:00', 'CAPACITADO'),
(3, 2, 'DOMICILIO', 1, '2026-06-18 10:00:00', 'PENDIENTE');
