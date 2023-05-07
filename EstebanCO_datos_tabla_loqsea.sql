-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: estebanco
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `conversacion`
--

DROP TABLE IF EXISTS `conversacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conversacion` (
  `idconversacion` int NOT NULL AUTO_INCREMENT,
  `estado` tinyint DEFAULT NULL,
  `fecha_inicio` datetime DEFAULT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `Persona_id` int NOT NULL,
  `Asistente_id` int NOT NULL,
  PRIMARY KEY (`idconversacion`),
  KEY `fk_conversacion_Persona1_idx` (`Persona_id`),
  KEY `fk_conversacion_Persona2_idx` (`Asistente_id`),
  CONSTRAINT `fk_conversacion_Persona1` FOREIGN KEY (`Persona_id`) REFERENCES `persona` (`id`),
  CONSTRAINT `fk_conversacion_Persona2` FOREIGN KEY (`Asistente_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversacion`
--

LOCK TABLES `conversacion` WRITE;
/*!40000 ALTER TABLE `conversacion` DISABLE KEYS */;
INSERT INTO `conversacion` VALUES (1,1,'2002-01-01 00:00:00','2002-01-01 00:00:00',1,2),(2,1,'2002-01-01 00:00:00','2002-01-01 00:00:00',1,3),(3,1,'2023-05-06 12:51:55','1970-01-01 01:00:00',6,2),(4,1,'2023-05-07 18:22:28','1970-01-01 01:00:00',8,3),(5,0,'2023-05-07 18:23:56','2023-05-07 18:24:12',6,3);
/*!40000 ALTER TABLE `conversacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `saldo` int DEFAULT NULL,
  `moneda` varchar(45) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `fecha_apertura` datetime DEFAULT NULL,
  `IBAN` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IBAN_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,1500,'euro','bien','2002-01-01 00:00:00','642764C'),(2,1000,'libra','bien','2002-01-01 00:00:00','12928472J'),(3,50,'euro','esperando_confirmacion','2002-01-01 00:00:00','5754348G'),(4,100,'euro','bien','2002-01-01 00:00:00','2124231J'),(5,0,'libra','bien','2023-05-06 16:47:24','ES54262143076429'),(6,2000,'euro','sospechosa','2002-01-01 00:00:00','230192841HX'),(7,0,'euro','bien','2023-05-07 01:40:22','ES49099913379101'),(8,0,'euro','esperandoConfirmacion',NULL,NULL),(9,0,'','bien','2023-05-07 18:05:37','ES94246235306727'),(10,250,'euro','bien','2023-05-07 18:08:05','ES68325783129006'),(11,99000,'euro','bien','2023-05-07 18:14:27','ES89632325093616');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mensaje` (
  `idmensaje` int NOT NULL AUTO_INCREMENT,
  `fecha_envio` datetime DEFAULT NULL,
  `texto` varchar(100) DEFAULT NULL,
  `conversacion_idconversacion` int NOT NULL,
  `conversacion_Emisor_id` int NOT NULL,
  `conversacion_Receptor_id` int NOT NULL,
  PRIMARY KEY (`idmensaje`),
  KEY `fk_mensaje_conversacion1_idx` (`conversacion_idconversacion`),
  CONSTRAINT `fk_mensaje_conversacion1` FOREIGN KEY (`conversacion_idconversacion`) REFERENCES `conversacion` (`idconversacion`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` VALUES (1,'2002-01-01 00:00:00','hola hdbhwsa',1,1,2),(2,'2002-01-01 00:00:00','alberto pesao',1,2,1),(3,'2002-01-01 00:00:00','sergio putero',2,1,3),(4,'2023-05-06 12:52:04','HOLA MU WNA',3,6,2),(5,'2023-05-06 12:52:20','ALBERTO ERE 1 PAYASO',3,2,6),(6,'2023-05-07 18:22:36','Hola mu wna estamo en curasao',4,8,3),(7,'2023-05-07 18:23:00','Recibido Paco',4,3,8),(8,'2023-05-07 18:24:05','Hola 2',5,6,3);
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operacion`
--

DROP TABLE IF EXISTS `operacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operacion` (
  `idoperacion` int NOT NULL AUTO_INCREMENT,
  `fecha_operacion` datetime DEFAULT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `moneda` varchar(45) DEFAULT NULL,
  `IBAN_cuentaDestinoOrigen` varchar(45) DEFAULT NULL,
  `Cuenta_id` int NOT NULL,
  `Persona_id` int NOT NULL,
  PRIMARY KEY (`idoperacion`),
  KEY `fk_operacion_Cuenta1_idx` (`Cuenta_id`),
  KEY `fk_operacion_Persona1_idx` (`Persona_id`),
  CONSTRAINT `fk_operacion_Cuenta1` FOREIGN KEY (`Cuenta_id`) REFERENCES `cuenta` (`id`),
  CONSTRAINT `fk_operacion_Persona1` FOREIGN KEY (`Persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operacion`
--

LOCK TABLES `operacion` WRITE;
/*!40000 ALTER TABLE `operacion` DISABLE KEYS */;
INSERT INTO `operacion` VALUES (1,'2002-01-01 00:00:00','sacar',16,NULL,NULL,1,1),(2,'2002-01-01 00:00:00','meter',17,NULL,NULL,1,1),(3,'2002-01-01 00:00:00','meter',1000,'euro','230192841HX',2,2),(4,'2023-05-07 18:09:01','cambio divisa',NULL,NULL,NULL,10,8),(5,'2023-05-07 18:09:50','sacar',100,NULL,'642764C',10,8),(6,'2023-05-07 18:09:50','meter',100,NULL,'ES68325783129006',1,8),(7,'2023-05-07 18:11:14','sacar',50,NULL,'642764C',10,8),(8,'2023-05-07 18:11:14','meter',50,NULL,'ES68325783129006',1,8),(9,'2023-05-07 18:13:14','sacar',100,NULL,'642764C',10,8),(10,'2023-05-07 18:13:14','meter',100,NULL,'ES68325783129006',1,8),(11,'2023-05-07 18:15:17','sacar',1000,NULL,'642764C',11,8),(12,'2023-05-07 18:25:29','meter',200000000,'libra',NULL,10,8),(13,'2023-05-07 18:26:54','cambio divisa',250,'euro','ES68325783129006',10,8);
/*!40000 ALTER TABLE `operacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido1` varchar(45) DEFAULT NULL,
  `apellido2` varchar(45) DEFAULT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `direccion` varchar(60) DEFAULT NULL,
  `telefono` varchar(11) DEFAULT NULL,
  `usuario` varchar(45) DEFAULT NULL,
  `contraseña` varchar(45) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `dni` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (1,'Misco','Jones','Smith','misco@gmail.com','C/Demosteles','555666','misco123','1234','esperandoConfirmacion','652456D'),(2,'jose','sancg','llex','jose@gmail.com','C/andromeda','133454','jose123','1234','bien','3214'),(3,'javi','gran','lui','javi@gmail.com','C/julio','64747','javi123','1234','bien','1243'),(4,'sergio','laura','yo','sergio@uma.es','UMA','1234','gestor','1234',NULL,'234512'),(6,'alberto','asdf','sd','asdsflkf','asdf','fasdlfkm','alb123','1234','bien','123451'),(7,'Prueba','Gestor','Condicional','uma.2','uma','12398','alberto','1234','bien','4928318'),(8,'Nico','Fernandez','Heredia','UMA','UMA','1924482727','nico123','1234',NULL,'283129487');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `rol` varchar(45) DEFAULT NULL,
  `Persona_id` int NOT NULL,
  `Cuenta_id` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `bloqueado_empresa` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Rol_Cuenta1_idx` (`Cuenta_id`),
  KEY `fk_Rol_Persona` (`Persona_id`),
  CONSTRAINT `fk_Rol_Cuenta1` FOREIGN KEY (`Cuenta_id`) REFERENCES `cuenta` (`id`),
  CONSTRAINT `fk_Rol_Persona` FOREIGN KEY (`Persona_id`) REFERENCES `persona` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES ('normal',1,1,1,0),('asistente',2,2,2,0),('asistente',3,3,3,0),('gestor',4,4,4,0),('normal',7,5,5,0),('normal',6,7,6,0),('normal',6,8,7,0),('normal',8,9,8,0),('normal',8,10,9,0),('empresa',8,11,10,0),('socio',7,11,11,0);
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_estado`
--

DROP TABLE IF EXISTS `tipo_estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_estado` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_estado`
--

LOCK TABLES `tipo_estado` WRITE;
/*!40000 ALTER TABLE `tipo_estado` DISABLE KEYS */;
INSERT INTO `tipo_estado` VALUES (1,'esperando_confirmacion'),(2,'bien'),(3,'bloqueado');
/*!40000 ALTER TABLE `tipo_estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_moneda`
--

DROP TABLE IF EXISTS `tipo_moneda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_moneda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `moneda` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_moneda`
--

LOCK TABLES `tipo_moneda` WRITE;
/*!40000 ALTER TABLE `tipo_moneda` DISABLE KEYS */;
INSERT INTO `tipo_moneda` VALUES (1,'euro'),(2,'libra');
/*!40000 ALTER TABLE `tipo_moneda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_operacion`
--

DROP TABLE IF EXISTS `tipo_operacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_operacion` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_operacion`
--

LOCK TABLES `tipo_operacion` WRITE;
/*!40000 ALTER TABLE `tipo_operacion` DISABLE KEYS */;
INSERT INTO `tipo_operacion` VALUES (1,'sacar'),(2,'meter'),(3,'cambio divisa');
/*!40000 ALTER TABLE `tipo_operacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_rol`
--

DROP TABLE IF EXISTS `tipo_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_rol` (
  `idtipo_rol` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtipo_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_rol`
--

LOCK TABLES `tipo_rol` WRITE;
/*!40000 ALTER TABLE `tipo_rol` DISABLE KEYS */;
INSERT INTO `tipo_rol` VALUES (1,'normal'),(2,'empresa');
/*!40000 ALTER TABLE `tipo_rol` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-07 18:47:43
