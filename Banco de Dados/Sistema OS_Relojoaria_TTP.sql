-- Sistema para gestão de OS e controle de estoque.
-- @autor Thaina Minga

show databases;
create database dbsistema;
use dbsistema;
show tables;

create table usuarios(
id int primary key auto_increment,
nome varchar(50) not null,
login varchar(15)not null unique,
senha varchar(250)not null,
perfil varchar(10)not null

);

insert into usuarios (nome,login,senha,perfil)
values ('Administrador','Admin',md5('admin'), 'admin');

create table clientes(
idcli int primary key auto_increment,
nome varchar(50) not null,
rg varchar(9)not null unique,
cpf varchar(11)not null unique,
fone varchar(15)not null,
cidade varchar(30)not null,
endereco varchar(150)not null,
bairro varchar(30)not null,
cep varchar(10),
numero varchar(10)not null,
complemento varchar(20),
uf char(2),
email varchar(100)
);

create table servicos(
os int primary key auto_increment,
dataOS timestamp default current_timestamp,
equipamento varchar(200) not null,
defeito varchar(200) not null,
diagnostico varchar(200) not null,
valor decimal(10,2),
idcli int not null,
foreign key (idcli) references clientes(idcli)
);

create table fornecedores(
idfor int primary key auto_increment,
razao varchar(50) not null,
fantasia varchar(50) not null,
cnpj varchar(14)not null unique,
fone varchar(15)not null,
cidade varchar(30)not null,
endereco varchar(150)not null,
bairro varchar(30)not null,
cep varchar(10),
numero varchar(10)not null,
complemento varchar(20),
uf char(2),
email varchar(100),
vendedor varchar(50),
ie varchar(50),
site varchar(50)
);

create table produtos(
codigo int primary key auto_increment,
idfor int not null,
barcode varchar(250) unique,
descricao varchar(100),
foto longblob ,
estoque int not null,
estoquemin int not null,
valor decimal(10,2) not null,
um char(2) not null,
locl varchar(50),
nome varchar(50)not null,
lote varchar(20)not null,
fabricante varchar(50),
lucro decimal(10,2),
dataent timestamp default current_timestamp,
dataval date,
razao varchar(50)not null,
foreign key (idfor) references fornecedores(idfor)
);