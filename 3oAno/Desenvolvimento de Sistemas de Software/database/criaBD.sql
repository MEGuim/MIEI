-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema gestor_eleicoes
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `gestor_eleicoes` ;

-- -----------------------------------------------------
-- Schema gestor_eleicoes
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gestor_eleicoes` DEFAULT CHARACTER SET utf8 ;
USE `gestor_eleicoes` ;

-- -----------------------------------------------------
-- Table `gestor_eleicoes`.`Eleitor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestor_eleicoes`.`Eleitor` ;

CREATE TABLE IF NOT EXISTS `gestor_eleicoes`.`Eleitor` (
  `id_eleitor` INT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(45) NOT NULL,
  `local_voto` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_eleitor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestor_eleicoes`.`Cidadao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestor_eleicoes`.`Cidadao` ;

CREATE TABLE IF NOT EXISTS `gestor_eleicoes`.`Cidadao` (
  `cartao_cidadao` INT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  `nacionalidade` VARCHAR(45) NOT NULL,
  `eleitor_id` INT NOT NULL,
  `candidato` TINYINT(1) NOT NULL DEFAULT 0,
  `concelho` VARCHAR(45) NOT NULL,
  `distrito` VARCHAR(45) NOT NULL,
  INDEX `fk_Dados_Pessoais_Eleitor1_idx` (`eleitor_id` ASC),
  PRIMARY KEY (`cartao_cidadao`, `eleitor_id`),
  UNIQUE INDEX `cartao_cidadao_UNIQUE` (`cartao_cidadao` ASC),
  CONSTRAINT `fk_Dados_Pessoais_Eleitor1`
    FOREIGN KEY (`eleitor_id`)
    REFERENCES `gestor_eleicoes`.`Eleitor` (`id_eleitor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestor_eleicoes`.`Lista`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestor_eleicoes`.`Lista` ;

CREATE TABLE IF NOT EXISTS `gestor_eleicoes`.`Lista` (
  `id_lista` INT NOT NULL AUTO_INCREMENT,
  `nome_lista` VARCHAR(45) NOT NULL,
  `imagem` BLOB NULL DEFAULT NULL,
  `resultado` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_lista`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestor_eleicoes`.`Eleicao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestor_eleicoes`.`Eleicao` ;

CREATE TABLE IF NOT EXISTS `gestor_eleicoes`.`Eleicao` (
  `id_eleicao` INT NOT NULL AUTO_INCREMENT,
  `data_eleicao` DATE NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `activa` TINYINT(1) NOT NULL DEFAULT 0,
  `votos_nulos` INT NOT NULL DEFAULT 0,
  `votos_brancos` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_eleicao`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestor_eleicoes`.`CadernoVoto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestor_eleicoes`.`CadernoVoto` ;

CREATE TABLE IF NOT EXISTS `gestor_eleicoes`.`CadernoVoto` (
  `Eleicao_id_eleicao` INT NOT NULL,
  `Eleitor_id_eleitor` INT NOT NULL,
  `votou?` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`Eleicao_id_eleicao`, `Eleitor_id_eleitor`),
  INDEX `fk_Eleicao_has_Eleitor_Eleitor1_idx` (`Eleitor_id_eleitor` ASC),
  INDEX `fk_Eleicao_has_Eleitor_Eleicao1_idx` (`Eleicao_id_eleicao` ASC),
  CONSTRAINT `fk_Eleicao_has_Eleitor_Eleicao1`
    FOREIGN KEY (`Eleicao_id_eleicao`)
    REFERENCES `gestor_eleicoes`.`Eleicao` (`id_eleicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Eleicao_has_Eleitor_Eleitor1`
    FOREIGN KEY (`Eleitor_id_eleitor`)
    REFERENCES `gestor_eleicoes`.`Eleitor` (`id_eleitor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestor_eleicoes`.`EleicaoListaCandidato`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gestor_eleicoes`.`EleicaoListaCandidato` ;

CREATE TABLE IF NOT EXISTS `gestor_eleicoes`.`EleicaoListaCandidato` (
  `Eleicao_id_eleicao` INT NOT NULL,
  `Lista_id_lista` INT NOT NULL,
  `Cidadao_cartao_cidadao` INT NOT NULL,
  `hierarquia` VARCHAR(45) NOT NULL,
  `partido` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Eleicao_id_eleicao`, `Lista_id_lista`, `Cidadao_cartao_cidadao`),
  INDEX `fk_EleicaoListaCandidato_2_idx` (`Lista_id_lista` ASC),
  INDEX `fk_EleicaoListaCandidato_Cidadao1_idx` (`Cidadao_cartao_cidadao` ASC),
  CONSTRAINT `fk_EleicaoListaCandidato_1`
    FOREIGN KEY (`Eleicao_id_eleicao`)
    REFERENCES `gestor_eleicoes`.`Eleicao` (`id_eleicao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EleicaoListaCandidato_2`
    FOREIGN KEY (`Lista_id_lista`)
    REFERENCES `gestor_eleicoes`.`Lista` (`id_lista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_EleicaoListaCandidato_Cidadao1`
    FOREIGN KEY (`Cidadao_cartao_cidadao`)
    REFERENCES `gestor_eleicoes`.`Cidadao` (`cartao_cidadao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
GRANT USAGE ON *.* TO USER;
 DROP USER USER;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'USER' IDENTIFIED BY '123';

GRANT ALL ON `gestor_eleicoes`.* TO 'USER';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
