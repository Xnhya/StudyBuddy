-- ---
-- Base de Datos: studybuddy_db
-- ---
CREATE DATABASE IF NOT EXISTS studybuddy_db;
USE studybuddy_db;

-- ---
-- Tabla 1: Facultad
-- (Para la primera lista desplegable en cascada)
-- ---
CREATE TABLE Facultad (
    id_facultad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_facultad VARCHAR(100) NOT NULL UNIQUE
);

-- ---
-- Tabla 2: Carrera
-- (Para la segunda lista desplegable en cascada, depende de Facultad)
-- ---
CREATE TABLE Carrera (
    id_carrera INT AUTO_INCREMENT PRIMARY KEY,
    nombre_carrera VARCHAR(100) NOT NULL,
    id_facultad INT,
    FOREIGN KEY (id_facultad) REFERENCES Facultad(id_facultad)
);

-- ---
-- Tabla 3: Materia
-- (Relacionada con Carrera)
-- ---
CREATE TABLE Materia (
    id_materia INT AUTO_INCREMENT PRIMARY KEY,
    nombre_materia VARCHAR(100) NOT NULL,
    id_carrera INT,
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

-- ---
-- Tabla 4: Usuario
-- (La tabla principal de usuarios)
-- ---
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL, -- Siempre guarda contraseñas hasheadas
    id_carrera INT, -- El usuario pertenece a una carrera
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

-- ---
-- Tabla 5: GrupoEstudio
-- (La tabla central de "StudyBuddy", un grupo se crea para una materia)
-- ---
CREATE TABLE GrupoEstudio (
    id_grupo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_grupo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    id_materia INT, -- El grupo es SOBRE una materia específica
    id_creador INT, -- Quién creó el grupo
    FOREIGN KEY (id_materia) REFERENCES Materia(id_materia),
    FOREIGN KEY (id_creador) REFERENCES Usuario(id_usuario)
);

-- ---
-- Tabla 6: Usuario_Grupo (Tabla PIVOTE)
-- (Para manejar la relación Mucho-a-Muchos entre Usuarios y Grupos)
-- (Un usuario puede estar en muchos grupos, un grupo tiene muchos usuarios)
-- ---
CREATE TABLE Usuario_Grupo (
    id_usuario INT,
    id_grupo INT,
    PRIMARY KEY (id_usuario, id_grupo), -- Clave primaria compuesta
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_grupo) REFERENCES GrupoEstudio(id_grupo) ON DELETE CASCADE
);


-- ---------------------------------
-- DATOS DE EJEMPLO (MUY IMPORTANTE PARA PROBAR)
-- ---------------------------------

-- 1. Insertar Facultades
INSERT INTO Facultad (nombre_facultad) VALUES 
('Ingeniería y Ciencias'),
('Humanidades y Artes');

-- 2. Insertar Carreras
INSERT INTO Carrera (nombre_carrera, id_facultad) VALUES 
('Ingeniería de Software', 1),
('Ingeniería Civil', 1),
('Literatura', 2),
('Música', 2);

-- 3. Insertar Materias
INSERT INTO Materia (nombre_materia, id_carrera) VALUES 
('Bases de Datos', 1),
('Programación Web', 1),
('Estructuras', 2),
('Literatura Medieval', 3);

-- 4. Insertar Usuarios
INSERT INTO Usuario (nombre, apellido, email, password_hash, id_carrera) VALUES 
('Ana', 'Gomez', 'ana@correo.com', 'hash123', 1),
('Luis', 'Perez', 'luis@correo.com', 'hash123', 2),
('Maria', 'Sol', 'maria@correo.com', 'hash123', 3);

-- 5. Insertar Grupos de Estudio
INSERT INTO GrupoEstudio (nombre_grupo, descripcion, id_materia, id_creador) VALUES
('Grupo de estudio BDD', 'Grupo para el examen final de Bases de Datos', 1, 1),
('Repaso Programación Web', 'Proyecto final de Programación Web', 2, 1),
('Lectura Medieval', 'Discusión de textos', 4, 3);

-- 6. Unir Usuarios a Grupos
INSERT INTO Usuario_Grupo (id_usuario, id_grupo) VALUES
(1, 1), -- Ana está en el grupo de BDD
(1, 2), -- Ana también está en el grupo de Prog. Web
(3, 3); -- Maria está en el grupo de Lectura Medieval