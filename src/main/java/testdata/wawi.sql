-- MySQL dump 10.15  Distrib 10.0.17-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: wawi
-- ------------------------------------------------------
-- Server version	10.0.17-MariaDB-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ARTICLE`
--

DROP TABLE IF EXISTS `ARTICLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ARTICLE` (
  `SKU` varchar(255) NOT NULL,
  `TYPE` varchar(20) DEFAULT NULL,
  `BRAND` varchar(255) DEFAULT NULL,
  `LONGDESCRIPTION` varchar(255) DEFAULT NULL,
  `MISC` varchar(255) DEFAULT NULL,
  `MODEL` varchar(255) DEFAULT NULL,
  `NUMBEROFPICTURES` int(11) DEFAULT NULL,
  `PARENTARTICLENAME` varchar(255) DEFAULT NULL,
  `SHORTDESCRIPTION` varchar(255) DEFAULT NULL,
  `SPECIALPRODUCT` tinyint(1) DEFAULT '0',
  `TAXCLASS` float DEFAULT NULL,
  `TOPPRODUCT` tinyint(1) DEFAULT '0',
  `TOPPRODUCTMOBILE` tinyint(1) DEFAULT '0',
  `ATTRIBUTE_ID` bigint(20) DEFAULT NULL,
  `CATEGORY_ID` bigint(20) DEFAULT NULL,
  `COLOR_ID` bigint(20) DEFAULT NULL,
  `AMAZONPRICE` decimal(38,0) DEFAULT NULL,
  `ASIN` bigint(20) DEFAULT NULL,
  `DIMENSIONS` varchar(255) DEFAULT NULL,
  `EAN` bigint(20) DEFAULT NULL,
  `EBAYPRICE` decimal(38,0) DEFAULT NULL,
  `MANUFACTURERSKU` varchar(255) DEFAULT NULL,
  `PURCHASEPRICE` decimal(38,0) DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `SHOPPRICE` decimal(38,0) DEFAULT NULL,
  `SPECIALPRICE` decimal(38,0) DEFAULT NULL,
  `SUGGESTEDRETAILPRICE` decimal(38,0) DEFAULT NULL,
  `WEIGHT` decimal(38,0) DEFAULT NULL,
  `SIZE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`SKU`),
  KEY `FK_ARTICLE_SIZE_ID` (`SIZE_ID`),
  KEY `FK_ARTICLE_ATTRIBUTE_ID` (`ATTRIBUTE_ID`),
  KEY `FK_ARTICLE_CATEGORY_ID` (`CATEGORY_ID`),
  KEY `FK_ARTICLE_COLOR_ID` (`COLOR_ID`),
  CONSTRAINT `FK_ARTICLE_ATTRIBUTE_ID` FOREIGN KEY (`ATTRIBUTE_ID`) REFERENCES `ATTRIBUTE` (`ID`),
  CONSTRAINT `FK_ARTICLE_CATEGORY_ID` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `CATEGORY` (`ID`),
  CONSTRAINT `FK_ARTICLE_COLOR_ID` FOREIGN KEY (`COLOR_ID`) REFERENCES `COLOR` (`ID`),
  CONSTRAINT `FK_ARTICLE_SIZE_ID` FOREIGN KEY (`SIZE_ID`) REFERENCES `SIZES` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARTICLE`
--

LOCK TABLES `ARTICLE` WRITE;
/*!40000 ALTER TABLE `ARTICLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ARTICLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ARTICLE_GENDER`
--

DROP TABLE IF EXISTS `ARTICLE_GENDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ARTICLE_GENDER` (
  `gender_ID` bigint(20) NOT NULL,
  `ParentArticle_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`gender_ID`,`ParentArticle_ID`),
  KEY `FK_ARTICLE_GENDER_ParentArticle_ID` (`ParentArticle_ID`),
  CONSTRAINT `FK_ARTICLE_GENDER_ParentArticle_ID` FOREIGN KEY (`ParentArticle_ID`) REFERENCES `ARTICLE` (`SKU`),
  CONSTRAINT `FK_ARTICLE_GENDER_gender_ID` FOREIGN KEY (`gender_ID`) REFERENCES `GENDER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARTICLE_GENDER`
--

LOCK TABLES `ARTICLE_GENDER` WRITE;
/*!40000 ALTER TABLE `ARTICLE_GENDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `ARTICLE_GENDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ARTICLE_MATERIAL`
--

DROP TABLE IF EXISTS `ARTICLE_MATERIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ARTICLE_MATERIAL` (
  `materials_ID` bigint(20) NOT NULL,
  `ParentArticle_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`materials_ID`,`ParentArticle_ID`),
  KEY `FK_ARTICLE_MATERIAL_ParentArticle_ID` (`ParentArticle_ID`),
  CONSTRAINT `FK_ARTICLE_MATERIAL_ParentArticle_ID` FOREIGN KEY (`ParentArticle_ID`) REFERENCES `ARTICLE` (`SKU`),
  CONSTRAINT `FK_ARTICLE_MATERIAL_materials_ID` FOREIGN KEY (`materials_ID`) REFERENCES `MATERIAL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARTICLE_MATERIAL`
--

LOCK TABLES `ARTICLE_MATERIAL` WRITE;
/*!40000 ALTER TABLE `ARTICLE_MATERIAL` DISABLE KEYS */;
/*!40000 ALTER TABLE `ARTICLE_MATERIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ARTICLE_PRODUCTTYPE`
--

DROP TABLE IF EXISTS `ARTICLE_PRODUCTTYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ARTICLE_PRODUCTTYPE` (
  `ParentArticle_SKU` varchar(255) NOT NULL,
  `productTypes_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ParentArticle_SKU`,`productTypes_ID`),
  KEY `FK_ARTICLE_PRODUCTTYPE_productTypes_ID` (`productTypes_ID`),
  CONSTRAINT `FK_ARTICLE_PRODUCTTYPE_ParentArticle_SKU` FOREIGN KEY (`ParentArticle_SKU`) REFERENCES `ARTICLE` (`SKU`),
  CONSTRAINT `FK_ARTICLE_PRODUCTTYPE_productTypes_ID` FOREIGN KEY (`productTypes_ID`) REFERENCES `PRODUCTTYPE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARTICLE_PRODUCTTYPE`
--

LOCK TABLES `ARTICLE_PRODUCTTYPE` WRITE;
/*!40000 ALTER TABLE `ARTICLE_PRODUCTTYPE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ARTICLE_PRODUCTTYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ATTRIBUTE`
--

DROP TABLE IF EXISTS `ATTRIBUTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ATTRIBUTE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ATTRIBUTE`
--

LOCK TABLES `ATTRIBUTE` WRITE;
/*!40000 ALTER TABLE `ATTRIBUTE` DISABLE KEYS */;
INSERT INTO `ATTRIBUTE` VALUES (1,'Default'),(2,'Jeans'),(3,'International');
/*!40000 ALTER TABLE `ATTRIBUTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CATEGORY`
--

DROP TABLE IF EXISTS `CATEGORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CATEGORY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CATEGORY`
--

LOCK TABLES `CATEGORY` WRITE;
/*!40000 ALTER TABLE `CATEGORY` DISABLE KEYS */;
INSERT INTO `CATEGORY` VALUES (1,'Kategorie A'),(2,'Kategorie B'),(3,'Kategorie C');
/*!40000 ALTER TABLE `CATEGORY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COLOR`
--

DROP TABLE IF EXISTS `COLOR`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COLOR` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COLOR`
--

LOCK TABLES `COLOR` WRITE;
/*!40000 ALTER TABLE `COLOR` DISABLE KEYS */;
INSERT INTO `COLOR` VALUES (1,'Grün'),(2,'Blau'),(3,'Schwarz');
/*!40000 ALTER TABLE `COLOR` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GENDER`
--

DROP TABLE IF EXISTS `GENDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GENDER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GENDER`
--

LOCK TABLES `GENDER` WRITE;
/*!40000 ALTER TABLE `GENDER` DISABLE KEYS */;
INSERT INTO `GENDER` VALUES (1,'Men\'s'),(2,'Women\'s'),(3,'Unisex');
/*!40000 ALTER TABLE `GENDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MATERIAL`
--

DROP TABLE IF EXISTS `MATERIAL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MATERIAL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MATERIAL`
--

LOCK TABLES `MATERIAL` WRITE;
/*!40000 ALTER TABLE `MATERIAL` DISABLE KEYS */;
INSERT INTO `MATERIAL` VALUES (1,'Jeans'),(2,'Echtfell'),(3,'Leder'),(4,'Kunstleder');
/*!40000 ALTER TABLE `MATERIAL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PREFIXNUMBER`
--

DROP TABLE IF EXISTS `PREFIXNUMBER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PREFIXNUMBER` (
  `NUMBER` bigint(20) NOT NULL,
  PRIMARY KEY (`NUMBER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PREFIXNUMBER`
--

LOCK TABLES `PREFIXNUMBER` WRITE;
/*!40000 ALTER TABLE `PREFIXNUMBER` DISABLE KEYS */;
INSERT INTO `PREFIXNUMBER` VALUES (1000000);
/*!40000 ALTER TABLE `PREFIXNUMBER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PRODUCTTYPE`
--

DROP TABLE IF EXISTS `PRODUCTTYPE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUCTTYPE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCTTYPE`
--

LOCK TABLES `PRODUCTTYPE` WRITE;
/*!40000 ALTER TABLE `PRODUCTTYPE` DISABLE KEYS */;
INSERT INTO `PRODUCTTYPE` VALUES (1,'Produkt-Typ A'),(2,'Produkt-Typ B'),(3,'Produkt-Typ C');
/*!40000 ALTER TABLE `PRODUCTTYPE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SIZES`
--

DROP TABLE IF EXISTS `SIZES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SIZES` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SIZE_TYPE` varchar(31) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SIZES`
--

LOCK TABLES `SIZES` WRITE;
/*!40000 ALTER TABLE `SIZES` DISABLE KEYS */;
INSERT INTO `SIZES` VALUES (1,'D','32'),(2,'D','34'),(3,'D','36'),(4,'D','37'),(5,'D','38'),(6,'D','39,5'),(7,'D','40'),(8,'D','41'),(9,'D','42'),(10,'D','43'),(11,'D','44'),(12,'D','44,5'),(13,'D','46'),(14,'D','47'),(15,'D','48'),(16,'D','50'),(17,'D','52'),(18,'D','54'),(19,'D','56'),(20,'D','58'),(21,'D','60'),(22,'I','XXS'),(23,'I','XS'),(24,'I','S'),(25,'I','M'),(26,'I','L'),(27,'I','XL'),(28,'I','XXL'),(29,'I','3XL'),(30,'I','4XL'),(31,'I','5XL'),(32,'J','W24/L30'),(33,'J','W24/L32'),(34,'J','W24/L34'),(35,'J','W24/L36'),(36,'J','W24/L38'),(37,'J','W24/L40'),(38,'J','W24/L42'),(39,'J','W25/L30'),(40,'J','W25/L32'),(41,'J','W25/L34'),(42,'J','W25/L36'),(43,'J','W25/L38'),(44,'J','W25/L40'),(45,'J','W25/L42'),(46,'J','W26/L30'),(47,'J','W26/L32'),(48,'J','W26/L34'),(49,'J','W26/L36'),(50,'J','W26/L38'),(51,'J','W26/L40'),(52,'J','W26/L42'),(53,'J','W27/L30'),(54,'J','W27/L32'),(55,'J','W27/L34'),(56,'J','W27/L36'),(57,'J','W27/L38'),(58,'J','W27/L40'),(59,'J','W27/L42'),(60,'J','W28/L30'),(61,'J','W28/L32'),(62,'J','W28/L34'),(63,'J','W28/L36'),(64,'J','W28/L38'),(65,'J','W28/L40'),(66,'J','W28/L42'),(67,'J','W29/L30'),(68,'J','W29/L32'),(69,'J','W29/L34'),(70,'J','W29/L36'),(71,'J','W29/L38'),(72,'J','W29/L40'),(73,'J','W29/L42'),(74,'J','W30/L30'),(75,'J','W30/L32'),(76,'J','W30/L34'),(77,'J','W30/L36'),(78,'J','W30/L38'),(79,'J','W30/L40'),(80,'J','W30/L42'),(81,'J','W31/L30'),(82,'J','W31/L32'),(83,'J','W31/L34'),(84,'J','W31/L36'),(85,'J','W31/L38'),(86,'J','W31/L40'),(87,'J','W31/L42'),(88,'J','W32/L30'),(89,'J','W32/L32'),(90,'J','W32/L34'),(91,'J','W32/L36'),(92,'J','W32/L38'),(93,'J','W32/L40'),(94,'J','W32/L42'),(95,'J','W33/L30'),(96,'J','W33/L32'),(97,'J','W33/L34'),(98,'J','W33/L36'),(99,'J','W33/L38'),(100,'J','W33/L40'),(101,'J','W33/L42'),(102,'J','W34/L30'),(103,'J','W34/L32'),(104,'J','W34/L34'),(105,'J','W34/L36'),(106,'J','W34/L38'),(107,'J','W34/L40'),(108,'J','W34/L42'),(109,'J','W35/L30'),(110,'J','W35/L32'),(111,'J','W35/L34'),(112,'J','W35/L36'),(113,'J','W35/L38'),(114,'J','W35/L40'),(115,'J','W35/L42'),(116,'J','W36/L30'),(117,'J','W36/L32'),(118,'J','W36/L34'),(119,'J','W36/L36'),(120,'J','W36/L38'),(121,'J','W36/L40'),(122,'J','W36/L42'),(123,'J','W37/L30'),(124,'J','W37/L32'),(125,'J','W37/L34'),(126,'J','W37/L36'),(127,'J','W37/L38'),(128,'J','W37/L40'),(129,'J','W37/L42'),(130,'J','W38/L30'),(131,'J','W38/L32'),(132,'J','W38/L34'),(133,'J','W38/L36'),(134,'J','W38/L38'),(135,'J','W38/L40'),(136,'J','W38/L42'),(137,'J','W39/L30'),(138,'J','W39/L32'),(139,'J','W39/L34'),(140,'J','W39/L36'),(141,'J','W39/L38'),(142,'J','W39/L40'),(143,'J','W39/L42'),(144,'J','W40/L30'),(145,'J','W40/L32'),(146,'J','W40/L34'),(147,'J','W40/L36'),(148,'J','W40/L38'),(149,'J','W40/L40'),(150,'J','W40/L42'),(151,'J','W41/L30'),(152,'J','W41/L32'),(153,'J','W41/L34'),(154,'J','W41/L36'),(155,'J','W41/L38'),(156,'J','W41/L40'),(157,'J','W41/L42'),(158,'J','W42/L30'),(159,'J','W42/L32'),(160,'J','W42/L34'),(161,'J','W42/L36'),(162,'J','W42/L38'),(163,'J','W42/L40'),(164,'J','W42/L42'),(165,'J','W43/L30'),(166,'J','W43/L32'),(167,'J','W43/L34'),(168,'J','W43/L36'),(169,'J','W43/L38'),(170,'J','W43/L40'),(171,'J','W43/L42'),(172,'J','W44/L30'),(173,'J','W44/L32'),(174,'J','W44/L34'),(175,'J','W44/L36'),(176,'J','W44/L38'),(177,'J','W44/L40'),(178,'J','W44/L42'),(179,'J','W45/L30'),(180,'J','W45/L32'),(181,'J','W45/L34'),(182,'J','W45/L36'),(183,'J','W45/L38'),(184,'J','W45/L40'),(185,'J','W45/L42'),(186,'J','W46/L30'),(187,'J','W46/L32'),(188,'J','W46/L34'),(189,'J','W46/L36'),(190,'J','W46/L38'),(191,'J','W46/L40'),(192,'J','W46/L42'),(193,'J','W47/L30'),(194,'J','W47/L32'),(195,'J','W47/L34'),(196,'J','W47/L36'),(197,'J','W47/L38'),(198,'J','W47/L40'),(199,'J','W47/L42'),(200,'J','W48/L30'),(201,'J','W48/L32'),(202,'J','W48/L34'),(203,'J','W48/L36'),(204,'J','W48/L38'),(205,'J','W48/L40'),(206,'J','W48/L42'),(207,'J','W49/L30'),(208,'J','W49/L32'),(209,'J','W49/L34'),(210,'J','W49/L36'),(211,'J','W49/L38'),(212,'J','W49/L40'),(213,'J','W49/L42'),(214,'J','W50/L30'),(215,'J','W50/L32'),(216,'J','W50/L34'),(217,'J','W50/L36'),(218,'J','W50/L38'),(219,'J','W50/L40'),(220,'J','W50/L42'),(221,'J','W51/L30'),(222,'J','W51/L32'),(223,'J','W51/L34'),(224,'J','W51/L36'),(225,'J','W51/L38'),(226,'J','W51/L40'),(227,'J','W51/L42'),(228,'J','W52/L30'),(229,'J','W52/L32'),(230,'J','W52/L34'),(231,'J','W52/L36'),(232,'J','W52/L38'),(233,'J','W52/L40'),(234,'J','W52/L42'),(235,'J','W53/L30'),(236,'J','W53/L32'),(237,'J','W53/L34'),(238,'J','W53/L36'),(239,'J','W53/L38'),(240,'J','W53/L40'),(241,'J','W53/L42'),(242,'J','W54/L30'),(243,'J','W54/L32'),(244,'J','W54/L34'),(245,'J','W54/L36'),(246,'J','W54/L38'),(247,'J','W54/L40'),(248,'J','W54/L42'),(249,'J','W55/L30'),(250,'J','W55/L32'),(251,'J','W55/L34'),(252,'J','W55/L36'),(253,'J','W55/L38'),(254,'J','W55/L40'),(255,'J','W55/L42'),(256,'J','W56/L30'),(257,'J','W56/L32'),(258,'J','W56/L34'),(259,'J','W56/L36'),(260,'J','W56/L38'),(261,'J','W56/L40'),(262,'J','W56/L42'),(263,'J','W57/L30'),(264,'J','W57/L32'),(265,'J','W57/L34'),(266,'J','W57/L36'),(267,'J','W57/L38'),(268,'J','W57/L40'),(269,'J','W57/L42'),(270,'J','W58/L30'),(271,'J','W58/L32'),(272,'J','W58/L34'),(273,'J','W58/L36'),(274,'J','W58/L38'),(275,'J','W58/L40'),(276,'J','W58/L42'),(277,'J','W59/L30'),(278,'J','W59/L32'),(279,'J','W59/L34'),(280,'J','W59/L36'),(281,'J','W59/L38'),(282,'J','W59/L40'),(283,'J','W59/L42'),(284,'J','W60/L30'),(285,'J','W60/L32'),(286,'J','W60/L34'),(287,'J','W60/L36'),(288,'J','W60/L38'),(289,'J','W60/L40'),(290,'J','W60/L42');
/*!40000 ALTER TABLE `SIZES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-03 14:32:50
