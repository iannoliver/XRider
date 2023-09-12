CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `login` varchar(20) NOT NULL,
  `senha` varchar(250) NOT NULL,
  `perfil` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

INSERT INTO `usuarios`(nome, login, senha, perfil) VALUES ('Administrador', 'admin', md5('admin'), 'admin');

CREATE TABLE `servicos` (
  `os` int NOT NULL AUTO_INCREMENT,
  `dataOS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `marca` varchar(30) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `cor` varchar(30) NUll,
  `numero de serie` varchar(50) NOT NULL,
  `observacoes especiais` varchar(200) NULL,
  `defeito` varchar(200) NOT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `idcli` int NOT NULL,
  PRIMARY KEY (`os`),
  KEY `idcli` (`idcli`),
  CONSTRAINT `servicos_ibfk_1` FOREIGN KEY (`idcli`) REFERENCES `clientes` (`idcli`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `clientes` (
  `idcli` int NOT NULL AUTO_INCREMENT,
  `cliente` varchar(50) NOT NULL,
  `contato` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `endereco` varchar(20) NOT NULL,
  `complemento` varchar(10) DEFAULT NULL,
  `cpfecnpj` varchar(14) NOT NULL,
  `cidade` varchar(40) DEFAULT NULL,
  `cep` varchar(10) NOT NULL,
  `bairro` varchar(30) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `numero` varchar(10) NOT NULL,
  PRIMARY KEY (`idcli`),
  UNIQUE KEY `contato` (`contato`),
  UNIQUE KEY `cpfecnpj` (`cpfecnpj`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;


create table fornecedores(
	idfornecedor int primary key auto_increment,
    razao varchar(50) not null,
    fantasia varchar(50) not null,
    fone varchar(15) not null,
	vendedor varchar(50) null,
    email varchar(30) not null,
    site varchar(100) null,
    endereco varchar(20) not null,
    complemento varchar(10) null,
    cpfecnpj varchar(14) not null unique,
    ie varchar(15) not null,
    cidade varchar(40) not null,
	cep varchar(10) not null,
	bairro varchar(30) not null,
	uf varchar(2) not null,
	numero varchar(10) not null
    );

    
    CREATE TABLE `produtos` (
  `idproduto` int NOT NULL AUTO_INCREMENT,
  `produto` varchar(50) NOT NULL,
  `barcode` varchar(30) DEFAULT NULL,
  `fabricante` varchar(45) NOT NULL,
  `descricao` varchar(200) DEFAULT NULL,
  `dataentrada` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `datavalidade` date NOT NULL,
  `foto` longblob,
  `estoque` int NOT NULL,
  `estoquemin` int NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `unidademedida` enum('UN','CX','PC','Kg','M') NOT NULL,
  `localarmazenagem` varchar(50) NOT NULL,
  `lote` varchar(45) NOT NULL,
  `lucro` int DEFAULT NULL,
  `idfornecedor` int NOT NULL,
  PRIMARY KEY (`idproduto`),
  KEY `idfornecedor` (`idfornecedor`),
  CONSTRAINT `produtos` FOREIGN KEY (`idfornecedor`) REFERENCES `fornecedores` (`idfornecedor`)
);
