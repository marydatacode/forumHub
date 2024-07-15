create table perfis(

    id bigint not null auto_increment,
    nome varchar(50) not null unique,

    primary key(id)

);