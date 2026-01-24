create table usuarios(

    id bigint not null auto_increment,
    login varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)

);

--no postgresql
--CREATE TABLE usuarios (id BIGINT GENERATED ALWAYS AS IDENTITY, login VARCHAR(100) NOT NULL, senha VARCHAR(255) NOT NULL, PRIMARY KEY (id));
