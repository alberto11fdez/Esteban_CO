-- MySQL Script generated by MySQL Workbench
-- Tue Apr 11 12:52:02 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema estebanco
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema estebanco
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `estebanco` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `estebanco` ;

-- -----------------------------------------------------
-- Table `estebanco`.`Persona`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`Persona` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `apellido1` VARCHAR(45) NULL,
  `apellido2` VARCHAR(45) NULL,
  `correo` VARCHAR(45) NULL,
  `direccion` VARCHAR(60) NULL,
  `telefono` VARCHAR(11) NULL,
  `usuario` VARCHAR(45) NULL,
  `contraseña` VARCHAR(45) NULL,
  `estado` VARCHAR(45) NULL,
  `dni` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`Cuenta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`Cuenta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `saldo` INT NULL,
  `moneda` VARCHAR(45) NULL,
  `estado` VARCHAR(45) NULL,
  `fecha_apertura` DATETIME NULL,
  `IBAN` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `IBAN_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`rol` (
  `rol` VARCHAR(45) NULL,
  `Persona_id` INT NOT NULL,
  `Cuenta_id` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `bloqueado_empresa` TINYINT NULL,
  INDEX `fk_Rol_Cuenta1_idx` (`Cuenta_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Rol_Persona`
    FOREIGN KEY (`Persona_id`)
    REFERENCES `estebanco`.`Persona` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Rol_Cuenta1`
    FOREIGN KEY (`Cuenta_id`)
    REFERENCES `estebanco`.`Cuenta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`operacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`operacion` (
  `idoperacion` INT NOT NULL AUTO_INCREMENT,
  `fecha_operacion` DATETIME NULL,
  `tipo` VARCHAR(45) NULL,
  `cantidad` INT NULL,
  `moneda` VARCHAR(45) NULL,
  `IBAN_cuentaDestinoOrigen` VARCHAR(45) NULL,
  `Cuenta_id` INT NOT NULL,
  `Persona_id` INT NOT NULL,
  PRIMARY KEY (`idoperacion`),
  INDEX `fk_operacion_Cuenta1_idx` (`Cuenta_id` ASC) VISIBLE,
  INDEX `fk_operacion_Persona1_idx` (`Persona_id` ASC) VISIBLE,
  CONSTRAINT `fk_operacion_Cuenta1`
    FOREIGN KEY (`Cuenta_id`)
    REFERENCES `estebanco`.`Cuenta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_operacion_Persona1`
    FOREIGN KEY (`Persona_id`)
    REFERENCES `estebanco`.`Persona` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`conversacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`conversacion` (
  `idconversacion` INT NOT NULL AUTO_INCREMENT,
  `estado` TINYINT NULL,
  `fecha_inicio` DATETIME NULL,
  `fecha_fin` DATETIME NULL,
  `Persona_id` INT NOT NULL,
  `Asistente_id` INT NOT NULL,
  PRIMARY KEY (`idconversacion`),
  INDEX `fk_conversacion_Persona1_idx` (`Persona_id` ASC) VISIBLE,
  INDEX `fk_conversacion_Persona2_idx` (`Asistente_id` ASC) VISIBLE,
  CONSTRAINT `fk_conversacion_Persona1`
    FOREIGN KEY (`Persona_id`)
    REFERENCES `estebanco`.`Persona` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_conversacion_Persona2`
    FOREIGN KEY (`Asistente_id`)
    REFERENCES `estebanco`.`Persona` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`mensaje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`mensaje` (
  `idmensaje` INT NOT NULL AUTO_INCREMENT,
  `fecha_envio` DATETIME NULL,
  `texto` VARCHAR(100) NULL,
  `conversacion_idconversacion` INT NOT NULL,
  `conversacion_Emisor_id` INT NOT NULL,
  `conversacion_Receptor_id` INT NOT NULL,
  PRIMARY KEY (`idmensaje`),
  INDEX `fk_mensaje_conversacion1_idx` (`conversacion_idconversacion` ASC) VISIBLE,
  CONSTRAINT `fk_mensaje_conversacion1`
    FOREIGN KEY (`conversacion_idconversacion`)
    REFERENCES `estebanco`.`conversacion` (`idconversacion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`tipo_rol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`tipo_rol` (
  `idtipo_rol` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`idtipo_rol`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`tipo_Operacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`tipo_Operacion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`tipo_estado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`tipo_estado` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `estebanco`.`tipo_moneda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `estebanco`.`tipo_moneda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `moneda` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
