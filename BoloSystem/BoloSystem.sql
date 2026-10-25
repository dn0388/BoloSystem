DROP DATABASE IF EXISTS bolosystem;
CREATE DATABASE bolosystem;
USE bolosystem;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50),
    senha VARCHAR(50)
);

INSERT INTO usuario (login, senha)
VALUES ('administrador', 'adm123');

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    contato VARCHAR(100),
    cpf VARCHAR(20),
    endereco VARCHAR(200),
    ponto_referencia VARCHAR(200)
);

CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    preco DOUBLE,
    estoque INT
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    data DATETIME,
    total DOUBLE
);

CREATE TABLE item_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT,
    produto_id INT,
    quantidade INT,
    subtotal DOUBLE
);

