alter table medicos add ativo tinyint not null;
update medicos set ativo = 1;

--alter table medicos add ativo boolean not null default true; update medicos set ativo = true;
