-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: nustudy_campus
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `campus_set`
--

DROP TABLE IF EXISTS `campus_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campus_set` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `campusname` varchar(100) DEFAULT NULL,
  `campuscode` varchar(30) DEFAULT NULL,
  `api_url` varchar(100) DEFAULT NULL,
  `sign_key` varchar(50) DEFAULT NULL,
  `contacts_name` varchar(20) DEFAULT NULL,
  `contacts_phone` varchar(11) DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campus_set`
--

LOCK TABLES `campus_set` WRITE;
/*!40000 ALTER TABLE `campus_set` DISABLE KEYS */;
INSERT INTO `campus_set` VALUES (1,'NEU — Silicon Valley','1000_00','http://localhost:9998','674c4139707728439a6510eae12deea9','Ying Tuo','66911111111',1,'2022-04-21 19:21:58','2022-04-28 04:44:51',0),(2,'NEU — San Francisco','1000_01','http://localhost:9994','347c4139706628439a6510eae12deea9','Xi Shen','6692222222',1,'2022-04-06 07:00:00','2022-04-28 04:44:51',0),(3,'NEU — Boston','1000_02','http://localhost:9999','9454d53784d0a92951e64ac6a6b83ad3','Yuqin Luo','6693333333',1,'2022-04-08 23:37:07','2022-04-28 04:44:51',0),(4,'NEU — Seattle','1000_03','http://localhost:9997','2d6b83c488603fbbd27f8273700db341','Huixin Huang','6695555555',1,'2022-04-20 00:08:46','2022-04-28 04:44:51',0);
/*!40000 ALTER TABLE `campus_set` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-28 13:10:42
