
-- Limpiar datos en orden inverso de dependencias
DELETE FROM eleccion_jurado;
DELETE FROM candidato;
DELETE FROM lista;
DELETE FROM eleccion;
DELETE FROM ciudadano;
DELETE FROM usuarios_roles;
DELETE FROM usuarios_entity;
DELETE FROM rol;
DELETE FROM partido;

-- ============================================================================
-- 1) Datos base de seguridad (minimos)
-- ============================================================================
INSERT INTO rol (nombre) VALUES
('ADMINISTRADOR_ELECTORAL'),
('REGISTRADOR'),
('CONSEJO_NACIONAL'),
('CIUDADANO');

INSERT INTO usuarios_entity (usuario, password) VALUES
('consejo_nacional', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P6.8DO');

INSERT INTO usuarios_roles (id_usuario_entity, id_rol) VALUES
(1, 3);

-- ============================================================================
-- 2) Partidos
-- ============================================================================
INSERT INTO partido (nombre, sigla, logo_url, fecha_creacion) VALUES
('Partido Progresista', 'PP', 'https://via.placeholder.com/100?text=PP', NOW()),
('Movimiento Liberal', 'ML', 'https://via.placeholder.com/100?text=ML', NOW()),
('Alianza Democratica', 'AD', 'https://via.placeholder.com/100?text=AD', NOW()),
('Partido Conservador', 'PC', 'https://via.placeholder.com/100?text=PC', NOW()),
('Izquierda Unida', 'IU', 'https://via.placeholder.com/100?text=IU', NOW()),
('Movimiento Verde', 'MV', 'https://via.placeholder.com/100?text=MV', NOW());

-- ============================================================================
-- 3) Ciudadanos
-- ============================================================================
INSERT INTO ciudadano (nombre, cedula, genero, voto_obligatorio) VALUES
('Juan Perez Garcia', '1001001001', 'M', true),
('Maria Gomez Lopez', '1002002002', 'F', true),
('Pedro Silva Ramirez', '1003003003', 'M', true),
('Elena Vargas Martinez', '1004004004', 'F', true),
('Carlos Ruiz Hernandez', '1005005005', 'M', true),
('Sofia Diaz Garcia', '1006006006', 'F', true),
('Francisco Torres Lopez', '1007007007', 'M', true),
('Mariana Flores Sanchez', '1008008008', 'F', true),
('Alejandro Rivera Gonzalez', '1009009009', 'M', true),
('Valentina Garcia Rodriguez', '1010010010', 'F', true),
('Roberto Moreno Herrera', '1011011011', 'M', true),
('Patricia Sanchez Guzman', '1012012012', 'F', true),
('Ludwing Acero Palacio', '1013013013', 'M', true),
('Yadira Quintero Velez', '1014014014', 'F', true),
('Jorge Mejia Lopez', '1015015015', 'M', true),
('Catalina Pulido Reyes', '1016016016', 'F', true),
('Andres Castillo Vargas', '1017017017', 'M', true),
('Marcela Parra Nunez', '1018018018', 'F', true),
('Victor Duarte Garcia', '1019019019', 'M', true),
('Cristina Navarro Lopez', '1020020020', 'F', true);

-- ============================================================================
-- 4) Elecciones
-- Se conservan campos relacionados solo como valor (NULL) sin crear registros
-- en ADMINISTRADOR_ELECTORAL/REGISTRADOR
-- ============================================================================
INSERT INTO eleccion (
  nombre,
  fecha_inicio,
  fecha_finalizacion,
  fecha_creacion,
  tipo,
  lista_abierta,
  estado,
  id_administrador_electoral
) VALUES
('Elecciones Congreso 2026 - CONFIGURACION', '2026-06-15 07:00:00', '2026-06-15 18:00:00', NOW(), 'LEGISLATIVA', true, 'CONFIGURACION', NULL),
('Elecciones Congreso 2026 - LANZADA', '2026-06-20 07:00:00', '2026-06-20 18:00:00', NOW(), 'LEGISLATIVA', true, 'LANZADA', NULL),
('Elecciones Presidenciales 2026 - EN CURSO', '2026-05-12 07:00:00', '2026-05-12 18:00:00', NOW(), 'PRESIDENCIAL', false, 'EN_CURSO', NULL),
('Elecciones Municipales 2025 - FINALIZADA', '2025-10-29 07:00:00', '2025-10-29 18:00:00', '2025-09-01 00:00:00', 'LEGISLATIVA', true, 'FINALIZADA', NULL);

-- ============================================================================
-- 5) Listas
-- ============================================================================
INSERT INTO lista (tipo, fecha_creacion, fecha_modificacion, id_eleccion) VALUES
('LISTA_1_CONGRESO_CONFIG', NOW(), NOW(), 1),
('LISTA_2_CONGRESO_CONFIG', NOW(), NOW(), 1),
('LISTA_1_CONGRESO_LANZADA', NOW(), NOW(), 2),
('LISTA_2_CONGRESO_LANZADA', NOW(), NOW(), 2),
('LISTA_PRESIDENCIAL_1', NOW(), NOW(), 3),
('LISTA_PRESIDENCIAL_2', NOW(), NOW(), 3),
('LISTA_MUNICIPAL_1', NOW(), NOW(), 4),
('LISTA_MUNICIPAL_2', NOW(), NOW(), 4);

-- ============================================================================
-- 6) Candidatos
-- id_registrador se conserva como campo relacionado en NULL
-- ============================================================================
INSERT INTO candidato (nombre, numero, foto_url, activo, id_registrador, id_lista, id_partido) VALUES
('Carlos Lopez', '1', 'https://via.placeholder.com/150?text=Carlos', true, NULL, 1, 1),
('Sandra Rodriguez', '2', 'https://via.placeholder.com/150?text=Sandra', true, NULL, 1, 2),
('Miguel Fernandez', '3', 'https://via.placeholder.com/150?text=Miguel', true, NULL, 1, 3),
('Lucia Gomez', '4', 'https://via.placeholder.com/150?text=Lucia', true, NULL, 1, 4),
('Roberto Martinez', '5', 'https://via.placeholder.com/150?text=Roberto', true, NULL, 1, 5),
('Diana Lopez Sanchez', '6', 'https://via.placeholder.com/150?text=Diana', true, NULL, 2, 6),
('Felipe Gutierrez', '7', 'https://via.placeholder.com/150?text=Felipe', true, NULL, 2, 1),
('Natalia Herrera', '8', 'https://via.placeholder.com/150?text=Natalia', true, NULL, 2, 2),
('Antonio Gonzalez', '9', 'https://via.placeholder.com/150?text=Antonio', true, NULL, 2, 3),
('Francisca Torres', '10', 'https://via.placeholder.com/150?text=Francisca', true, NULL, 2, 4),
('Pablo Mendoza', '1', 'https://via.placeholder.com/150?text=Pablo', true, NULL, 3, 1),
('Gabriela Reyes', '2', 'https://via.placeholder.com/150?text=Gabriela', true, NULL, 3, 2),
('Enrique Soto', '3', 'https://via.placeholder.com/150?text=Enrique', true, NULL, 4, 3),
('Valentina Cross', '4', 'https://via.placeholder.com/150?text=Valentina', true, NULL, 4, 4),
('Valentina Silva', '1', 'https://via.placeholder.com/150?text=Valentina', true, NULL, 5, 1),
('Edmundo Castillo', '2', 'https://via.placeholder.com/150?text=Edmundo', true, NULL, 5, 2),
('Gabriela Moreno', '3', 'https://via.placeholder.com/150?text=Gabriela', true, NULL, 6, 3),
('Javier Rodriguez', '1', 'https://via.placeholder.com/150?text=Javier', false, NULL, 7, 1),
('Mariana Lopez', '2', 'https://via.placeholder.com/150?text=Mariana', false, NULL, 8, 2);

-- ============================================================================
-- 7) Jurados por eleccion
-- Usa SELECT para no depender de IDs autoincrement exactos
-- ============================================================================
INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'URNA', 1, '2026-05-10 10:00:00', 'CAPACITADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1001001001' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'DOMICILIO', 1, '2026-05-10 10:00:00', 'CAPACITADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1002002002' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'URNA', 2, '2026-05-10 11:00:00', 'CAPACITADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1003003003' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'DOMICILIO', 2, '2026-05-10 11:00:00', 'CAPACITADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1004004004' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'URNA', 3, '2026-05-10 14:00:00', 'CAPACITADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1005005005' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'DOMICILIO', 3, '2026-05-10 14:00:00', 'CAPACITADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1006006006' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'URNA', 4, '2026-05-13 10:00:00', 'PENDIENTE'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1007007007' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'DOMICILIO', 4, '2026-05-13 10:00:00', 'PENDIENTE'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1008008008' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'URNA', 5, '2026-05-13 11:00:00', 'PENDIENTE'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1009009009' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'DOMICILIO', 5, '2026-05-13 11:00:00', 'PENDIENTE'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1010010010' AND e.nombre = 'Elecciones Presidenciales 2026 - EN CURSO';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'URNA', 6, '2025-10-29 10:00:00', 'NO_PRESENTADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1011011011' AND e.nombre = 'Elecciones Municipales 2025 - FINALIZADA';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'DOMICILIO', 6, '2025-10-29 10:00:00', 'NO_PRESENTADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1012012012' AND e.nombre = 'Elecciones Municipales 2025 - FINALIZADA';

-- Jurados adicionales en otra eleccion
INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'URNA', 1, '2026-06-18 10:00:00', 'CAPACITADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1001001001' AND e.nombre = 'Elecciones Congreso 2026 - LANZADA';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'URNA', 2, '2026-06-18 11:00:00', 'CAPACITADO'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1002002002' AND e.nombre = 'Elecciones Congreso 2026 - LANZADA';

INSERT INTO eleccion_jurado (id_ciudadano, id_eleccion, tipo_jurado, numero_mesa, fecha_capacitacion, estado)
SELECT c.id_ciudadano, e.id_eleccion, 'DOMICILIO', 1, '2026-06-18 10:00:00', 'PENDIENTE'
FROM ciudadano c, eleccion e
WHERE c.cedula = '1003003003' AND e.nombre = 'Elecciones Congreso 2026 - LANZADA';
