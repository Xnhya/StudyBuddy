-- ---
-- Base de Datos: studybuddy_db
-- (Versión COMPLETA Y CORREGIDA)
-- ---
DROP DATABASE IF EXISTS studybuddy_db;
CREATE DATABASE IF NOT EXISTS studybuddy_db;
USE studybuddy_db;

-- ---
-- Tabla 1: Facultad
-- ---
CREATE TABLE Facultad (
    id_facultad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_facultad VARCHAR(100) NOT NULL UNIQUE
);

-- ---
-- Tabla 2: Carrera
-- (Depende de Facultad)
-- ---
CREATE TABLE Carrera (
    id_carrera INT AUTO_INCREMENT PRIMARY KEY,
    nombre_carrera VARCHAR(100) NOT NULL,
    id_facultad INT,
    FOREIGN KEY (id_facultad) REFERENCES Facultad(id_facultad)
);

-- ---
-- Tabla 3: Materia
-- (Depende de Carrera)
-- ---
CREATE TABLE Materia (
    id_materia INT AUTO_INCREMENT PRIMARY KEY,
    nombre_materia VARCHAR(100) NOT NULL,
    id_carrera INT,
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

-- ---
-- Tabla 4: Usuario
-- (ACTUALIZADA con 'preferencias' y HASHES REALES)
-- ---
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, -- Almacena el hash de BCrypt
    id_carrera INT,
    preferencias VARCHAR(255) NULL, -- Nueva columna para gustos
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

-- ---
-- Tabla 5: GrupoEstudio
-- ---
CREATE TABLE GrupoEstudio (
    id_grupo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_grupo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    id_materia INT,
    id_creador INT,
    FOREIGN KEY (id_materia) REFERENCES Materia(id_materia),
    FOREIGN KEY (id_creador) REFERENCES Usuario(id_usuario)
);

-- ---
-- Tabla 6: Usuario_Grupo (Tabla Pivote)
-- ---
CREATE TABLE Usuario_Grupo (
    id_usuario INT,
    id_grupo INT,
    PRIMARY KEY (id_usuario, id_grupo),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_grupo) REFERENCES GrupoEstudio(id_grupo) ON DELETE CASCADE
);

-- ---
-- Tabla 7: Recurso
-- (NUEVA TABLA - migrada de memoria)
-- ---
CREATE TABLE Recurso (
    id_recurso INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    tipo VARCHAR(50),
    autor VARCHAR(100),
    id_grupo INT,
    FOREIGN KEY (id_grupo) REFERENCES GrupoEstudio(id_grupo) ON DELETE SET NULL
);

-- ---
-- Tabla 8: Sesion
-- (NUEVA TABLA - migrada de memoria)
-- ---
CREATE TABLE Sesion (
    id_sesion INT AUTO_INCREMENT PRIMARY KEY,
    tema VARCHAR(255) NOT NULL,
    fecha DATE,
    hora TIME,
    descripcion TEXT
);

-- ---------------------------------
-- DATOS DE EJEMPLO (LIMPIOS Y FUNCIONALES)
-- ---------------------------------

-- 1. Insertar Facultades (Incluye las que pediste)
INSERT INTO Facultad (id_facultad, nombre_facultad) VALUES 
(1, 'Ingeniería y Ciencias'),
(2, 'Humanidades y Artes'),
(3, 'Ciencias Económicas'),
(4, 'Ciencias de la Salud');

-- 2. Insertar Carreras (Incluye las que pediste)
INSERT INTO Carrera (id_carrera, nombre_carrera, id_facultad) VALUES 
(1, 'Ingeniería de Software', 1),
(2, 'Ingeniería Civil', 1),
(3, 'Literatura', 2),
(4, 'Música', 2),
(5, 'Administración de Empresas', 3),
(6, 'Contabilidad y Finanzas', 3),
(7, 'Medicina Humana', 4),
(8, 'Enfermería', 4);

-- 3. Insertar Materias (Cursos)
INSERT INTO Materia (id_materia, nombre_materia, id_carrera) VALUES 
(1, 'Bases de Datos', 1),
(2, 'Programación Web', 1),
(3, 'Estructuras', 2),
(4, 'Literatura Medieval', 3),
(5, 'Microeconomía', 5),
(6, 'Matemática Financiera', 6),
(7, 'Anatomía Humana', 7),
(8, 'Cuidados Básicos del Paciente', 8);

-- 4. Insertar Usuarios
-- (¡IMPORTANTE! Contraseña para todos es '123456')
-- (El hash largo es '123456' encriptado con BCrypt)
SET @hash_real = '$2a$10$3zHzb.NpvLdckVv1eNphDeyweFBjO7DClv3EVl.aP.rLNFyGG1sCq';

INSERT INTO Usuario (id_usuario, nombre, apellido, email, password_hash, id_carrera, preferencias) VALUES 
(1, 'Admin', 'User', 'admin@studybuddy.com', @hash_real, 1, 'MUSICA,GRUPO'),
(2, 'María', 'García', 'maria@estudiante.com', @hash_real, 7, 'SILENCIO'),
(3, 'Carlos', 'López', 'carlos@estudiante.com', @hash_real, 5, 'GRUPO');

-- 5. Insertar Grupos de Estudio
INSERT INTO GrupoEstudio (id_grupo, nombre_grupo, descripcion, id_materia, id_creador) VALUES
(1, 'Grupo de estudio BDD', 'Grupo para el examen final de Bases de Datos', 1, 1),
(2, 'Repaso Programación Web', 'Proyecto final de Programación Web', 2, 1),
(3, 'Lectura Medieval', 'Discusión de textos', 4, 2),
(4, 'Club de Anatomía', 'Repaso para el parcial', 7, 2);

-- 6. Unir Usuarios a Grupos
INSERT INTO Usuario_Grupo (id_usuario, id_grupo) VALUES
(1, 1), -- Admin está en el grupo de BDD
(1, 2), -- Admin también está en el grupo de Prog. Web
(2, 3), -- Maria está en el grupo de Lectura Medieval
(2, 4), -- Maria también está en el de Anatomía
(3, 1); -- Carlos está en el grupo de BDD




select * from usuario