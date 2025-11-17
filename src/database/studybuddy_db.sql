DROP DATABASE IF EXISTS studybuddy_db;
CREATE DATABASE IF NOT EXISTS studybuddy_db;
USE studybuddy_db;

-- 1. Tabla Facultad
CREATE TABLE Facultad (
    id_facultad INT AUTO_INCREMENT PRIMARY KEY,
    nombre_facultad VARCHAR(100) NOT NULL UNIQUE
);

-- 2. Tabla Carrera
CREATE TABLE Carrera (
    id_carrera INT AUTO_INCREMENT PRIMARY KEY,
    nombre_carrera VARCHAR(100) NOT NULL,
    id_facultad INT,
    FOREIGN KEY (id_facultad) REFERENCES Facultad(id_facultad)
);

-- 3. Tabla Materia
CREATE TABLE Materia (
    id_materia INT AUTO_INCREMENT PRIMARY KEY,
    nombre_materia VARCHAR(100) NOT NULL,
    id_carrera INT,
    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

-- 4. Tabla Usuario (¡Con soporte para Security y Recuperación!)
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL, 
    id_carrera INT,
    preferencias VARCHAR(255) NULL,
    
    -- [NUEVO] Rol para Spring Security (Por defecto todos son USER)
    rol VARCHAR(20) DEFAULT 'USER', 
    
    -- [NUEVO] Campos para recuperar contraseña
    token_recuperacion VARCHAR(100) NULL,
    token_expiracion DATETIME NULL,

    FOREIGN KEY (id_carrera) REFERENCES Carrera(id_carrera)
);

-- 5. Tabla GrupoEstudio
CREATE TABLE GrupoEstudio (
    id_grupo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_grupo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    id_materia INT,
    id_creador INT,
    FOREIGN KEY (id_materia) REFERENCES Materia(id_materia),
    FOREIGN KEY (id_creador) REFERENCES Usuario(id_usuario)
);

-- 6. Tabla Usuario_Grupo (Relación muchos a muchos)
CREATE TABLE Usuario_Grupo (
    id_usuario INT,
    id_grupo INT,
    PRIMARY KEY (id_usuario, id_grupo),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_grupo) REFERENCES GrupoEstudio(id_grupo) ON DELETE CASCADE
);

-- 7. Tabla Recurso
CREATE TABLE Recurso (
    id_recurso INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    tipo VARCHAR(50),
    autor VARCHAR(100),
    id_grupo INT,
    FOREIGN KEY (id_grupo) REFERENCES GrupoEstudio(id_grupo) ON DELETE SET NULL
);

-- 8. Tabla Sesion (¡Ahora conectada a los Grupos!)
CREATE TABLE Sesion (
    id_sesion INT AUTO_INCREMENT PRIMARY KEY,
    tema VARCHAR(255) NOT NULL,
    fecha DATE,
    hora TIME,
    descripcion TEXT,
    
    -- [NUEVO] Vinculamos la sesión a un grupo. 
    -- Sin esto, ¡no sabríamos para quién es la sesión!
    id_grupo INT,
    FOREIGN KEY (id_grupo) REFERENCES GrupoEstudio(id_grupo) ON DELETE CASCADE
);



-- ---
-- Tabla 9: Usuario_Materia (Tabla Pivote)
-- (NUEVA TABLA PARA LA RELACIÓN USUARIO <-> MATERIA)
-- ---
CREATE TABLE Usuario_Materia (
    id_usuario INT,
    id_materia INT,
    PRIMARY KEY (id_usuario, id_materia),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_materia) REFERENCES Materia(id_materia) ON DELETE CASCADE
);