-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: nustudy_user
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
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `certificates_type` varchar(3) DEFAULT NULL,
  `certificates_no` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL COMMENT 'email',
  `state_code` varchar(20) DEFAULT NULL,
  `city_code` varchar(20) DEFAULT NULL,
  `district_code` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '1: deleted. 0: not deleted',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3 COMMENT='student list';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,1,'Ying Tuo','1','1234567890','tuo.y@northeastern.edu','123','123','123','4 N 2nd St Suite 150, San Jose, CA 95113','2022-04-25 08:50:42','2022-04-28 19:39:10',0),(2,2,'Xi Shen',NULL,'1234567890','shen.x@northeastern.edu',NULL,NULL,NULL,'4 N 2nd St Suite 150, San Jose, CA 95113','2022-04-28 19:26:38','2022-04-28 19:39:10',0),(3,3,'Huixin Huang',NULL,'1234567890','huang.huix@northeastern.edu',NULL,NULL,NULL,'4 N 2nd St Suite 150, San Jose, CA 95113','2022-04-28 19:26:38','2022-04-28 19:39:10',0),(4,4,'Yuqin Luo',NULL,'1234567890','luo.yuqin@northeastern.edu',NULL,NULL,NULL,'4 N 2nd St Suite 150, San Jose, CA 95113','2022-04-28 19:26:38','2022-04-28 19:39:10',0);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nick_name` varchar(20) DEFAULT NULL COMMENT 'nick name',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT 'phone number',
  `name` varchar(20) DEFAULT NULL COMMENT 'name',
  `certificates_type` varchar(3) DEFAULT NULL COMMENT 'certificate type',
  `certificates_no` varchar(30) DEFAULT NULL COMMENT 'certificate number',
  `certificates_url` varchar(200) DEFAULT NULL COMMENT 'certificate path',
  `auth_status` tinyint NOT NULL DEFAULT '0' COMMENT 'authorization status',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT 'status',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT 'deletion logic',
  PRIMARY KEY (`id`),
  KEY `uk_mobile` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb3 COMMENT='student list';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'Ying','tuo.y@northeastern.edu','Ying Tuo','1','1234567890',NULL,0,1,'2022-04-25 01:52:52','2022-04-28 19:39:34',0),(2,'Xi','shen.x@northeastern.edu','Xi Shen','1','1234567890',NULL,0,1,'2022-04-28 19:19:51','2022-04-28 19:39:47',0),(3,'Huixin','huang.huix@northeastern.edu','Huixin Huang','1','1234567890',NULL,0,1,'2022-04-28 19:25:27','2022-04-28 19:39:47',0),(4,'Yuqian','luo.yuqin@northeastern.edu','Yuqin Luo','1','1234567890',NULL,0,1,'2022-04-28 19:25:27','2022-04-28 19:39:47',0);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-28 13:12:32
