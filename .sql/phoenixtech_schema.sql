CREATE DATABASE phoenixtech;
USE phoenixtech;

CREATE TABLE interesses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome ENUM(
        'IA Responsável',
        'Cibersegurança',
        'Privacidade & Ética Digital'
    ) UNIQUE NOT NULL
);

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT,
    usuario VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    tipo ENUM('admin','usuario') DEFAULT 'usuario',
    interesse1 VARCHAR(100),
    interesse2 VARCHAR(100),
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE postagens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    autor VARCHAR(100),
    titulo VARCHAR(200),
    texto TEXT,
    categoria VARCHAR(100)
);

INSERT IGNORE INTO interesses (nome) VALUES
 ('IA Responsável'),
 ('Cibersegurança'),
 ('Privacidade & Ética Digital');

-- Admin padrão
INSERT IGNORE INTO usuarios (nome, idade, usuario, senha, tipo)
VALUES ('Administrador', 30, 'admin', 'admin123', 'admin');
