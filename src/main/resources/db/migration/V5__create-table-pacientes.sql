create table pacientes(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    cpf varchar(14) not null unique,
    logradouro varchar(100) not null,
    bairro varchar(100) not null,
    cep varchar(9) not null,
    complemento varchar(100),
    numero varchar(20),
    uf char(2) not null,
    cidade varchar(100) not null,
    telefone varchar(20) not null,
    ativo tinyint not null,

    primary key(id)

);

--CREATE TABLE pacientes (id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, nome VARCHAR(100) NOT NULL, email VARCHAR(100) NOT NULL UNIQUE, cpf VARCHAR(14) NOT NULL UNIQUE, logradouro VARCHAR(100) NOT NULL, bairro VARCHAR(100) NOT NULL, cep VARCHAR(9) NOT NULL, complemento VARCHAR(100), numero VARCHAR(20), uf CHAR(2) NOT NULL, cidade VARCHAR(100) NOT NULL, telefone VARCHAR(20) NOT NULL, ativo SMALLINT NOT NULL);
--ALTER TABLE pacientes  ALTER COLUMN ativo TYPE BOOLEAN  USING ativo = 1; 
--ALTER TABLE pacientes ALTER COLUMN uf TYPE VARCHAR(2);
