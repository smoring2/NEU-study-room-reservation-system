-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: nustudy_order
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
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint DEFAULT NULL,
  `out_trade_no` varchar(300) DEFAULT NULL COMMENT 'order id',
  `campuscode` varchar(30) DEFAULT NULL COMMENT 'campus code',
  `campusname` varchar(100) DEFAULT NULL COMMENT 'campus name',
  `depcode` varchar(30) DEFAULT NULL COMMENT 'department code',
  `depname` varchar(20) DEFAULT NULL COMMENT 'department name',
  `title` varchar(20) DEFAULT NULL COMMENT 'title',
  `schedule_id` varchar(50) DEFAULT NULL COMMENT 'schedule id',
  `reserve_date` date DEFAULT NULL COMMENT 'work date',
  `reserve_time` tinyint DEFAULT '0' COMMENT 'work time',
  `student_id` bigint DEFAULT NULL COMMENT 'student id',
  `student_name` varchar(20) DEFAULT NULL COMMENT 'student name',
  `student_email` varchar(50) DEFAULT NULL COMMENT 'Student email',
  `campus_record_id` varchar(30) DEFAULT NULL COMMENT 'registration reference number',
  `number` int DEFAULT NULL COMMENT 'number',
  `fetch_time` varchar(50) DEFAULT NULL COMMENT 'suggested fetch time',
  `fetch_address` varchar(255) DEFAULT NULL COMMENT 'suggested fetch address',
  `amount` decimal(10,0) DEFAULT NULL COMMENT 'service amount',
  `quit_time` datetime DEFAULT NULL COMMENT 'quit time',
  `order_status` tinyint DEFAULT NULL COMMENT 'order status',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT 'deletion logic',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_out_trade_no` (`out_trade_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_hoscode` (`campuscode`),
  KEY `idx_hos_schedule_id` (`schedule_id`),
  KEY `idx_hos_record_id` (`campus_record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb3 COMMENT='order list';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` VALUES (122,2,'165117433082171','1000_00','NEU — Silicon Valley','200040878','9 Floor','Large Meeting Room','4','2022-04-30',0,2,'Xi Shen','shen.x@northeastern.edu','49',1,'2022-04-3009:00前','一层114窗口',100,'2022-04-29 15:30:00',0,'2022-04-28 19:32:10','2022-04-28 19:32:11',0),(123,2,'165117454861447','1000_00','NEU — Silicon Valley','200040878','9 Floor','Medium Meeting Room','3','2022-04-30',1,2,'Xi Shen','shen.x@northeastern.edu','50',1,'2022-04-3009:00前','一层114窗口',100,'2022-04-29 15:30:00',0,'2022-04-28 19:35:48','2022-04-28 19:35:48',0),(124,2,'165117493771433','1000_00','NEU — Silicon Valley','200040878','9 Floor','Meduium Meeting Room','11','2022-05-01',0,2,'Xi Shen','shen.x@northeastern.edu','51',1,'2022-05-0109:00前','一层114窗口',100,'2022-04-30 15:30:00',0,'2022-04-28 19:42:17','2022-04-28 19:42:17',0);
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-28 13:12:20
