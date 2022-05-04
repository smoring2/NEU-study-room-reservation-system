-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: nustudy_manage
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
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `campuscode` varchar(30) DEFAULT NULL COMMENT 'campus id',
  `sign_key` varchar(50) DEFAULT NULL COMMENT 'private sign key',
  `api_url` varchar(100) DEFAULT NULL COMMENT 'api url for platforms,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT 'deletion logic',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='campus configuration form';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campus_set`
--

LOCK TABLES `campus_set` WRITE;
/*!40000 ALTER TABLE `campus_set` DISABLE KEYS */;
INSERT INTO `campus_set` VALUES (1,'1000_00','674c4139707728439a6510eae12deea9','http://localhost:8201','2022-04-06 07:00:00','2022-04-25 06:19:31',0),(2,'1000_01','674c4139707728439a6510eae12deea9','http://localhost:8201','2022-04-06 07:00:00','2022-04-25 06:19:31',0),(3,'1000_02','9454d53784d0a92951e64ac6a6b83ad3','http://localhost:8201','2022-04-06 07:00:00','2022-04-25 06:19:31',0),(4,'1000_03','2d6b83c488603fbbd27f8273700db341','http://localhost:8201','2022-04-06 07:00:00','2022-04-25 06:19:31',0);
/*!40000 ALTER TABLE `campus_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `schedule_id` varchar(100) DEFAULT NULL COMMENT 'schedule id',
  `student_id` bigint DEFAULT NULL COMMENT 'student id',
  `number` int DEFAULT NULL COMMENT 'number id',
  `fetch_time` varchar(50) DEFAULT NULL COMMENT 'suggested fetch time',
  `fetch_address` varchar(255) DEFAULT NULL COMMENT 'suggested fetch address',
  `amount` decimal(10,0) DEFAULT NULL COMMENT 'service amount',
  `pay_time` datetime DEFAULT NULL COMMENT 'payment time',
  `quit_time` datetime DEFAULT NULL COMMENT 'quit time',
  `order_status` tinyint DEFAULT NULL COMMENT 'order status',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT 'deletion logic',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb3 COMMENT='order list';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` VALUES (51,'11',1,1,'0 14:00','level 9',100,NULL,NULL,0,'2022-04-28 19:42:17','2022-04-28 19:42:17',0);
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `id` bigint NOT NULL DEFAULT '0' COMMENT 'id',
  `campuscode` varchar(30) DEFAULT NULL COMMENT 'campus id',
  `depcode` varchar(30) DEFAULT NULL COMMENT 'department id',
  `title` varchar(20) DEFAULT NULL COMMENT 'title',
  `docname` varchar(20) DEFAULT NULL COMMENT 'administration name',
  `skill` text COMMENT 'skill',
  `work_date` date DEFAULT NULL COMMENT 'work date',
  `work_time` tinyint DEFAULT '0' COMMENT 'work time',
  `reserved_number` int DEFAULT '0' COMMENT 'reserved number',
  `available_number` int DEFAULT '0' COMMENT 'available reservation number',
  `amount` decimal(10,0) DEFAULT NULL COMMENT 'amount',
  `status` tinyint DEFAULT NULL COMMENT 'schedule status',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT 'deletion logic',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='meeting room schedule';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,'1000_00','200040878','Small Meeting Room','903','For 1 people','2022-04-29',0,1,1,100,1,'2022-04-21 23:52:14','2022-04-28 19:37:23',0),(2,'1000_00','200040878','Medium Meeting Room','902','For 6 people','2022-04-29',0,1,1,100,1,'2022-04-22 04:07:34','2022-04-28 19:37:23',0),(3,'1000_00','200040878','Medium Meeting Room','903','FOR 6 PEOPLE','2022-04-30',1,1,1,100,1,'2022-04-22 04:07:34','2022-04-28 19:37:23',0),(4,'1000_00','200040878','Large Meeting Room','901','1 MONITORS; FOR 10 PEOPLE','2022-04-30',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-28 19:37:23',0),(5,'1000_00','200040878','Medium Meeting Room','902','FOR 2 PEOPLE','2022-05-03',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0),(6,'1000_00','200040878','Small Meeting Room','904','FOR 1 PEOPLE','2022-04-29',1,1,1,100,1,'2022-04-22 04:07:35','2022-04-28 19:37:23',0),(7,'1000_00','200040878','Large Meeting Room','901','1 MONITORS; FOR 10 PEOPLE','2022-04-28',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-28 18:49:14',0),(8,'1000_00','200040878','Medium Meeting Room','902','FOR 2 PEOPLE','2022-04-29',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-28 18:49:14',0),(9,'1000_00','200040878','Medium Meeting Room','902','FOR 6 PEOPLE','2022-04-30',1,1,1,100,1,'2022-04-22 04:07:35','2022-04-28 18:49:14',0),(10,'1000_00','200040878','Large Meeting Room','907','2 MONITORS; FOR 20 PEOPLE','2022-04-30',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-28 19:37:23',0),(11,'1000_00','200040878','Meduium Meeting Room','902','FOR 2 PEOPLE','2022-05-01',0,1,0,100,1,'2022-04-22 04:07:35','2022-04-28 18:31:54',0),(12,'1000_00','200040878','Small Meeting Room','903','FOR 6 PEOPLE','2022-05-01',1,1,0,100,1,'2022-04-22 04:07:35','2022-04-28 16:12:44',0),(13,'1000_00','200040878','Large Meeting Room','901','2 MONITORS; FOR 10 PEOPLE','2022-05-02',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0),(14,'1000_00','200040878','Small Meeting Room','903','FOR 2 PEOPLE','2022-05-02',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0),(15,'1000_00','200040878','Small Meeting Room','903','FOR 6 PEOPLE','2022-05-02',1,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0),(16,'1000_00','200040878','Small Meeting Room','903','1 MONITORS; FOR 10 PEOPLE','2020-12-18',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0),(17,'1000_00','200040878','Medium Meeting Room','902','FOR 2 PEOPLE','2022-05-03',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0),(18,'1000_00','200040878','Large Meeting Room','901','FOR 6 PEOPLE','2022-05-03',1,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0),(19,'1000_00','200040878','Large Meeting Room','901','1 MONITORS; FOR 10 PEOPLE','2022-04-27',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0),(20,'1000_00','200040878','Medium Meeting Room','902','FOR 2 PEOPLE','2022-04-27',0,1,1,100,1,'2022-04-22 04:07:35','2022-04-22 04:07:35',0);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-28 13:12:01
