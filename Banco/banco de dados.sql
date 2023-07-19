use dbsistema;

select * from clientes;


-- selecionando o conteúdo de 2 ou mais tabelas
select * from servicos inner join clientes on servicos.idcli = clientes.idcli;


update servicos set brinquedo = 'jojo', defeito = 'nenhum', valor = '200.00' where os = 1;

delete from clientes where idcli = 8;



/** RELATÓRIOS **/

select cliente,contato from clientes order by cliente;
select nome,fone,email from clientes order by nome;

-- servicos
select 
servicos.os,servicos.dataOS,servicos.bicicleta,servicos.defeito,servicos.valor,clientes.cliente from servicos inner join clientes on servicos.idcli = clientes.idcli;

select * from servicos;

create table fornecedores(
	idfornecedor int primary key auto_increment,
    fornecedor varchar(50) not null,
    contato varchar(15) not null,
    email varchar(30) not null,
    endereco varchar(20) not null,
    complemento varchar(10) null,
    cpfecnpj varchar(14) not null unique,
    cidade varchar(40) not null,
	cep varchar(10) not null,
	bairro varchar(30) not null,
	uf varchar(2) not null,
	numero varchar(10) not null
    );

create table produtos(
	idproduto int primary key auto_increment,
    nome varchar(100) not null, 
    barcode varchar(20) null,
    descricao varchar(200) not null,
    foto longblob null,
    estoque int not null,
    estoquemin int not null,
    valor decimal(10,2) not null,
    medida varchar(10) not null,
    armazenagem varchar(50) not null,
    id int not null,
    foreign key (id) references fornecedores(idfornecedor)
    );
    
select produtos.idproduto,
produtos.nome,
produtos.descricao,
produtos.foto,
produtos.estoque,
produtos.estoquemin,
produtos.valor,
produtos.medida,
produtos.armazenagem,
fornecedores.fornecedor
from produtos
inner join fornecedores
on produtos.idproduto = fornecedores.idfornecedor;

insert into clientes (nome,fone,cep,endereco,numero,complemento,bairro,cidade,ufusuarios) values ('lucas','123456','endereco','214','214','a','a','a','a');

drop table produtos;

select * from produtos;

delete from produtos where idproduto = '5';

insert into produtos (nome, descricao, estoque, estoquemin, valor, medida, armazenagem, id) values ('bike', 'qdasd', 1, 1, 230, 'UN', 'Bikes', 1);


SELECT * FROM dbsistema.usuarios;

describe usuarios;

drop table usuarios;

create table usuarios(
id int primary key auto_increment,
nome varchar(30) not null,
login varchar(20) not null,
senha varchar(250) not null,
perfil varchar(10) not null
);

insert into usuarios(nome, login, senha, perfil) values ('Administrador', 'admin', md5('admin'), 'admin');

insert into usuarios(nome, login, senha, perfil) values ('Iann', 'iann', md5('123@senac'), 'user');

select nome from usuarios where nome like 'A%' order by nome;

select * from usuarios;