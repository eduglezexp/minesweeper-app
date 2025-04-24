PRAGMA foreign_keys = OFF;

-- Elimina tablas en orden inverso de dependencias
DROP TABLE IF EXISTS objeto_traducciones;
DROP TABLE IF EXISTS objetos;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS nivel_traducciones;
DROP TABLE IF EXISTS niveles;
DROP TABLE IF EXISTS powerup_traducciones;
DROP TABLE IF EXISTS usuario_powerups;
DROP TABLE IF EXISTS powerups;
DROP TABLE IF EXISTS tema_traducciones;
DROP TABLE IF EXISTS usuario_temas;
DROP TABLE IF EXISTS temas; 

PRAGMA foreign_keys = ON;

-- 1. Tabla de niveles base
CREATE TABLE niveles (
    id INTEGER PRIMARY KEY AUTOINCREMENT
);

INSERT INTO niveles (id) VALUES (1), (2), (3);

-- 2. Traducciones de niveles: nombre (dificultad) + descripción para cada idioma
CREATE TABLE nivel_traducciones (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_nivel   INTEGER NOT NULL,
    idioma     TEXT    NOT NULL,
    dificultad      TEXT    NOT NULL,
    descripcion TEXT   NOT NULL,
    UNIQUE (id_nivel, idioma),
    FOREIGN KEY (id_nivel) REFERENCES niveles(id)
);

-- Ejemplos de inserción por idioma
-- Nivel 1 (Beginner / Principiante / Facile)
INSERT INTO nivel_traducciones (id_nivel, idioma, dificultad, descripcion) VALUES
    (1, 'en', 'Beginner',    '9×9 grid, 10 bombs'),
    (1, 'es', 'Principiante','Cuadrícula 9×9, 10 bombas'),
    (1, 'fr', 'Facile',      'Grille 9×9, 10 bombes');

-- Nivel 2 (Intermediate / Intermedio / Moyen)
INSERT INTO nivel_traducciones (id_nivel, idioma, dificultad, descripcion) VALUES
    (2, 'en', 'Intermediate','16×16 grid, 40 bombs'),
    (2, 'es', 'Intermedio', 'Cuadrícula 16×16, 40 bombas'),
    (2, 'fr', 'Moyen',      'Grille 16×16, 40 bombes');

-- Nivel 3 (Expert / Experto / Difficile)
INSERT INTO nivel_traducciones (id_nivel, idioma, dificultad, descripcion) VALUES
    (3, 'en', 'Expert',      '30×16 grid, 99 bombs'),
    (3, 'es', 'Experto',     'Cuadrícula 30×16, 99 bombas'),
    (3, 'fr', 'Difficile',   'Grille 30×16, 99 bombes');

-- 3. Usuarios
CREATE TABLE usuarios (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    user       TEXT    NOT NULL UNIQUE,
    email      TEXT    NOT NULL UNIQUE,
    name       TEXT    NOT NULL,
    password   TEXT    NOT NULL,
    points     INTEGER DEFAULT 0,
    victories  INTEGER DEFAULT 0,
    defeats    INTEGER DEFAULT 0,
    id_nivel   INTEGER NOT NULL,
    racha_actual INTEGER DEFAULT 0,
    mejor_racha INTEGER DEFAULT 0,
    FOREIGN KEY (id_nivel) REFERENCES niveles(id)
);

-- Ejemplo de inserción de usuarios
INSERT INTO usuarios (user, email, name, password, id_nivel) VALUES 
    ('Usuario-1', 'email1@example.com', 'Nombre1', 'contraseña123', 1),
    ('Usuario-2', 'email2@example.com', 'Nombre2', 'abc456', 1),
    ('Usuario-3', 'email3@example.com', 'Nombre3', 'xyz789', 1),
    ('Usuario-4', 'email4@example.com', 'Nombre4', '123456', 1),
    ('Usuario-5', 'email5@example.com', 'Nombre5', '123456', 1),
    ('Usuario-6', 'email6@example.com', 'Nombre6', '123456', 1),
    ('Usuario-7', 'email7@example.com', 'Nombre7', '123456', 1),
    ('Usuario-8', 'email8@example.com', 'Nombre8', '123456', 1);

-- 4. Objetos base
CREATE TABLE objetos (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    id_nivel INTEGER NOT NULL,
    FOREIGN KEY (id_nivel) REFERENCES niveles(id)
);

INSERT INTO objetos (id_nivel) VALUES
    (1),  -- bomba
    (1),  -- bandera
    (2);  -- cascada

-- 5. Traducciones de objetos: objeto + ejemplo
CREATE TABLE objeto_traducciones (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    id_objeto  INTEGER NOT NULL,
    idioma      TEXT    NOT NULL,
    objeto     TEXT    NOT NULL,
    ejemplo     TEXT    NOT NULL,
    UNIQUE (id_objeto, idioma),
    FOREIGN KEY (id_objeto) REFERENCES objetos(id)
);

-- Inserción de traducciones para objetos
-- Objeto 1: bomba / bomb / bombe
INSERT INTO objeto_traducciones (id_objeto, idioma, objeto, ejemplo) VALUES
    (1, 'en', 'bomb',   'A hidden dangerous element'),
    (1, 'es', 'bomba',  'Un elemento peligroso oculto'),
    (1, 'fr', 'bombe',  'Un élément dangereux caché');

-- Objeto 2: bandera / flag / drapeau
INSERT INTO objeto_traducciones (id_objeto, idioma, objeto, ejemplo) VALUES
    (2, 'en', 'flag',   'Marks a suspicious cell'),
    (2, 'es', 'bandera','Marca una casilla sospechosa'),
    (2, 'fr', 'drapeau','Marque une case suspecte');

-- Objeto 3: cascading reveal / cascada / cascade
INSERT INTO objeto_traducciones (id_objeto, idioma, objeto, ejemplo) VALUES
    (3, 'en', 'cascade','Reveal multiple cells at once'),
    (3, 'es', 'cascada','Revelar varias casillas a la vez'),
    (3, 'fr', 'cascade','Révéler plusieurs cases à la fois');

CREATE TABLE temas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    clave TEXT UNIQUE NOT NULL, 
    costo INTEGER NOT NULL DEFAULT 150,
    css TEXT NOT NULL 
);

-- Inserción de temas
INSERT INTO temas (clave, costo, css) VALUES
    ('oscuro', 150, '/es/ies/puerto/css/temas/oscuro.css'),
    ('naturaleza', 150, '/es/ies/puerto/css/temas/naturaleza.css'),
    ('retro', 150, '/es/ies/puerto/css/temas/retro.css');

CREATE TABLE usuario_temas (
    usuario_id INTEGER NOT NULL,
    tema_id INTEGER NOT NULL,
    activo BOOLEAN DEFAULT 0, 
    PRIMARY KEY (usuario_id, tema_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (tema_id) REFERENCES temas(id)
);

CREATE TABLE powerups (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    clave TEXT UNIQUE NOT NULL, 
    costo INTEGER NOT NULL
);

INSERT INTO powerups (clave, costo) VALUES
    ('mina_fantasma', 50),
    ('escudo', 100);

CREATE TABLE usuario_powerups (
    usuario_id INTEGER NOT NULL,
    powerup_id INTEGER NOT NULL,
    cantidad INTEGER DEFAULT 0,
    PRIMARY KEY (usuario_id, powerup_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (powerup_id) REFERENCES powerups(id)
);

-- Temas
CREATE TABLE tema_traducciones (
    tema_id INTEGER NOT NULL,
    idioma TEXT NOT NULL,
    nombre TEXT NOT NULL,
    descripcion TEXT NOT NULL,
    PRIMARY KEY (tema_id, idioma),
    FOREIGN KEY (tema_id) REFERENCES temas(id)
);

-- Power-Ups
CREATE TABLE powerup_traducciones (
    powerup_id INTEGER NOT NULL,
    idioma TEXT NOT NULL,
    nombre TEXT NOT NULL,
    descripcion TEXT NOT NULL,
    PRIMARY KEY (powerup_id, idioma),
    FOREIGN KEY (powerup_id) REFERENCES powerups(id)
);

-- Tema Oscuro (ID 1)
INSERT INTO tema_traducciones (tema_id, idioma, nombre, descripcion) VALUES
    (1, 'es', 'Tema Oscuro', 'Fondos negros y grises'),
    (1, 'en', 'Dark Theme', 'Black and gray backgrounds'),
    (1, 'fr', 'Thème Sombre', 'Fonds noirs et gris');

-- Tema Naturaleza (ID 2)
INSERT INTO tema_traducciones (tema_id, idioma, nombre, descripcion) VALUES
    (2, 'es', 'Tema Naturaleza', 'Minas representadas como flores'),
    (2, 'en', 'Nature Theme', 'Mines depicted as flowers'),
    (2, 'fr', 'Thème Nature', 'Mines représentées par des fleurs');

-- Tema Retro (ID 3)
INSERT INTO tema_traducciones (tema_id, idioma, nombre, descripcion) VALUES
    (3, 'es', 'Tema Retro', 'Estilo pixel art 8-bits'),
    (3, 'en', 'Retro Theme', '8-bit pixel art style'),
    (3, 'fr', 'Thème Rétro', 'Style pixel art 8-bits');

-- Mina Fantasma (ID 1)
INSERT INTO powerup_traducciones (powerup_id, idioma, nombre, descripcion) VALUES
    (1, 'es', 'Mina Fantasma', 'Revela una mina por 5 segundos'),
    (1, 'en', 'Ghost Mine', 'Reveals a mine for 5 seconds'),
    (1, 'fr', 'Mine Fantôme', 'Révèle une mine pendant 5 secondes');

-- Escudo Anti-Minas (ID 2)
INSERT INTO powerup_traducciones (powerup_id, idioma, nombre, descripcion) VALUES
    (2, 'es', 'Escudo Anti-Minas', 'Te protege de una mina una vez'),
    (2, 'en', 'Anti-Mine Shield', 'Protects you from one mine'),
    (2, 'fr', 'Bouclier Anti-Mines', 'Vous protège d''une mine');