CREATE DATABASE  IF NOT EXISTS `ukrzaliznica` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ukrzaliznica`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: ukrzaliznica
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cities` (
  `idcity` int(11) NOT NULL AUTO_INCREMENT,
  `cityname` varchar(45) NOT NULL,
  PRIMARY KEY (`idcity`),
  UNIQUE KEY `idcity_UNIQUE` (`idcity`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES (1,'Винница'),(2,'Днепр'),(3,'Донецк'),(4,'Ивано-Франковск'),(5,'Киев'),(6,'Кропивницкий'),(7,'Луганск'),(8,'Львов'),(9,'Николаев'),(10,'Одесса'),(11,'Ровно'),(12,'Сумы'),(13,'Тернополь'),(14,'Харьков'),(15,'Херсон'),(16,'Хмельницкий'),(17,'Черкассы'),(18,'Черновцы'),(19,'Чернигов'),(20,'Ужгород'),(21,'Полтава'),(22,'Запорожье'),(23,'Житомир'),(24,'Луцк');
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reis`
--

DROP TABLE IF EXISTS `reis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reis` (
  `idreis` int(11) NOT NULL,
  `namereis` varchar(45) DEFAULT NULL,
  `dateandtime` datetime NOT NULL,
  PRIMARY KEY (`idreis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reis`
--

LOCK TABLES `reis` WRITE;
/*!40000 ALTER TABLE `reis` DISABLE KEYS */;
INSERT INTO `reis` VALUES (1,'Херсон-Львів','2020-05-11 12:30:00'),(2,'Миколаїв-Київ','2020-05-11 13:30:00');
/*!40000 ALTER TABLE `reis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reis_cities`
--

DROP TABLE IF EXISTS `reis_cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reis_cities` (
  `idreis` int(11) NOT NULL,
  `idcity` int(11) NOT NULL,
  `orderofcity` int(11) NOT NULL,
  KEY `idreis_fk_idx` (`idreis`),
  KEY `idcity_fk_idx` (`idcity`),
  CONSTRAINT `idcity_fk` FOREIGN KEY (`idcity`) REFERENCES `cities` (`idcity`),
  CONSTRAINT `idreis_fk` FOREIGN KEY (`idreis`) REFERENCES `reis` (`idreis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reis_cities`
--

LOCK TABLES `reis_cities` WRITE;
/*!40000 ALTER TABLE `reis_cities` DISABLE KEYS */;
INSERT INTO `reis_cities` VALUES (1,15,1),(1,9,2),(1,6,3),(1,5,4),(1,17,5),(1,1,6),(1,16,7),(1,13,8),(1,8,9),(2,9,1),(1,3,10),(2,10,2),(2,1,3),(2,5,4);
/*!40000 ALTER TABLE `reis_cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `idticket` int(11) NOT NULL AUTO_INCREMENT,
  `idreis` int(11) NOT NULL,
  `pib` varchar(45) DEFAULT NULL,
  `idcityfrom` int(11) DEFAULT NULL,
  `idcityto` int(11) DEFAULT NULL,
  `place` int(11) DEFAULT NULL,
  PRIMARY KEY (`idticket`),
  UNIQUE KEY `idticket_UNIQUE` (`idticket`),
  KEY `idreis_fk_idx` (`idreis`),
  KEY `idcityto_fk_idx` (`idcityto`),
  KEY `idcityfrom_fk_idx` (`idcityfrom`),
  CONSTRAINT `idcityfrom_fk` FOREIGN KEY (`idcityfrom`) REFERENCES `cities` (`idcity`),
  CONSTRAINT `idcityto_fk` FOREIGN KEY (`idcityto`) REFERENCES `cities` (`idcity`),
  CONSTRAINT `idreis_tickets_fk` FOREIGN KEY (`idreis`) REFERENCES `reis` (`idreis`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (56,1,'Іван  Іванов',9,1,17),(59,1,'Максим Максимов',9,1,43);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train`
--

DROP TABLE IF EXISTS `train`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train` (
  `idreis` int(11) NOT NULL,
  `place` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL,
  KEY `idreis_fk_idx` (`idreis`),
  KEY `idreis_idx` (`idreis`) /*!80000 INVISIBLE */,
  CONSTRAINT `idreis_train_fk` FOREIGN KEY (`idreis`) REFERENCES `reis` (`idreis`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train`
--

LOCK TABLES `train` WRITE;
/*!40000 ALTER TABLE `train` DISABLE KEYS */;
INSERT INTO `train` VALUES (1,51,0),(1,15,0),(1,50,0),(1,49,0),(1,37,0),(1,43,1),(1,39,0),(1,24,0),(1,21,0),(1,44,0),(1,10,0),(1,6,0),(1,45,0),(1,36,0),(1,8,0),(1,23,0),(1,32,0),(1,54,0),(1,9,0),(1,29,0),(1,34,0),(1,26,0),(1,28,0),(1,41,0),(1,53,0),(1,25,0),(1,4,0),(1,12,0),(1,20,0),(1,33,0),(1,17,1),(1,35,0),(1,18,0),(1,5,0),(1,46,0),(1,22,0),(1,27,0),(1,42,0),(1,19,0),(1,3,0),(1,11,0),(1,13,0),(1,47,0),(1,38,0),(1,2,0),(1,31,0),(1,16,0),(1,40,0),(1,52,0),(1,48,0),(1,1,0),(1,14,0),(1,7,0),(1,30,0),(2,1,0),(2,2,0),(2,3,0),(2,4,0),(2,5,0),(2,6,0),(2,7,0),(2,8,0),(2,9,0),(2,10,0),(2,11,0),(2,12,0),(2,13,0),(2,14,0),(2,15,0),(2,16,0),(2,17,1),(2,18,0),(2,19,0),(2,20,0),(2,21,0),(2,22,0),(2,23,0),(2,24,0),(2,25,0),(2,26,0),(2,27,0),(2,28,0),(2,29,0),(2,30,0),(2,31,0),(2,32,0),(2,33,0),(2,34,0),(2,35,0),(2,36,0),(2,37,0),(2,38,0),(2,39,0),(2,40,0),(2,41,0),(2,42,0),(2,43,0),(2,44,0),(2,45,0),(2,46,0),(2,47,0),(2,48,0),(2,49,0),(2,50,0),(2,51,0),(2,52,0),(2,53,0),(2,54,0);
/*!40000 ALTER TABLE `train` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `fio` varchar(80) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `privileges` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `iduser_UNIQUE` (`iduser`),
  KEY `login_ix` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Бочков Максим Володимирович','bochkov13','0000',NULL),(3,'Василій Пупкін','pupkinofficial','vasya123',NULL),(4,'root','1234','1234',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_tickets`
--

DROP TABLE IF EXISTS `users_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_tickets` (
  `iduser` int(11) NOT NULL,
  `idticket` int(11) NOT NULL,
  KEY `iduser_fk_idx` (`iduser`),
  KEY `idticket_fk_idx` (`idticket`),
  CONSTRAINT `idticket_fk` FOREIGN KEY (`idticket`) REFERENCES `tickets` (`idticket`),
  CONSTRAINT `iduser_fk` FOREIGN KEY (`iduser`) REFERENCES `users` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_tickets`
--

LOCK TABLES `users_tickets` WRITE;
/*!40000 ALTER TABLE `users_tickets` DISABLE KEYS */;
INSERT INTO `users_tickets` VALUES (1,56),(1,59);
/*!40000 ALTER TABLE `users_tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'ukrzaliznica'
--

--
-- Dumping routines for database 'ukrzaliznica'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_user` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_user`(
in s_fio VARCHAR(80),
in s_login VARCHAR(45),
in s_password VARCHAR(45),
inout status INT
)
BEGIN
	if exists(select login from users where login = s_login) then
		set status = 0;
	else 
		if exists(select password from users where password = s_password) then
			set status = 1;
        else 
			INSERT INTO users (fio, login, password) values(s_fio, s_login, s_password);
            set status = 2;
            end if;
	end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `log_in` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `log_in`(
in s_login VARCHAR(45),
in s_password VARCHAR(45),
inout status INT
)
BEGIN
	if exists(select login, password from users where login = s_login and password = s_password) then
		set status = 1;
	else 
			set status = 0;
	end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `return_ticket` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `return_ticket`(
in s_idticket int,
in s_place int
)
BEGIN
	delete from users_tickets where idticket = s_idticket;
    delete from tickets where idticket = s_idticket;
    if not exists(select place from tickets where place = s_place) then
		update train set status = 0 where place = s_place;
    end if;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-31 12:26:02
