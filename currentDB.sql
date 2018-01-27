-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: prototype
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `catalogproduct`
--

DROP TABLE IF EXISTS `catalogproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalogproduct` (
  `productID` int(11) NOT NULL,
  `salesPrice` float DEFAULT NULL,
  `Image` varchar(256) DEFAULT NULL,
  `StoreID` int(11) NOT NULL,
  PRIMARY KEY (`productID`,`StoreID`),
  KEY `storeid_idx` (`StoreID`),
  CONSTRAINT `pID` FOREIGN KEY (`productID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `storeid` FOREIGN KEY (`StoreID`) REFERENCES `store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogproduct`
--

LOCK TABLES `catalogproduct` WRITE;
/*!40000 ALTER TABLE `catalogproduct` DISABLE KEYS */;
INSERT INTO `catalogproduct` VALUES (4,100,'panter.jpg',0),(5,-1,'buttercup.jpg',0),(20,100,'SnowWhite.jpg',1),(21,120,'Cinderella.jpg',2),(22,-1,'Olive.jpg',1),(23,-1,'Spathiphyllum.jpg',3),(24,-1,'sunshine.jpg',0),(25,-1,'VioletWinter.jpg',1);
/*!40000 ALTER TABLE `catalogproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaintreport`
--

DROP TABLE IF EXISTS `complaintreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaintreport` (
  `Quarterly` varchar(45) NOT NULL,
  `Year` varchar(45) NOT NULL,
  `StoreID` int(11) NOT NULL,
  `FirstMonthHandledComplaintsAmount` int(11) DEFAULT NULL,
  `FirstMonthPendingComplaintsAmount` int(11) DEFAULT NULL,
  `SecondMonthHandledComplaintsAmount` int(11) DEFAULT NULL,
  `SecondMonthPendingComplaintsAmount` int(11) DEFAULT NULL,
  `ThirdMonthHandledComplaintsAmount` int(11) DEFAULT NULL,
  `ThirdMonthPendingComplaintsAmount` int(11) DEFAULT NULL,
  PRIMARY KEY (`Quarterly`,`Year`,`StoreID`),
  KEY `fk_ComplaintReport_1_idx` (`StoreID`),
  CONSTRAINT `fk_ComplaintReport_1` FOREIGN KEY (`StoreID`) REFERENCES `store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaintreport`
--

LOCK TABLES `complaintreport` WRITE;
/*!40000 ALTER TABLE `complaintreport` DISABLE KEYS */;
INSERT INTO `complaintreport` VALUES ('FIRST','2017',2,13,14,15,16,17,18),('THIRD','2017',2,20,10,51,46,23,31);
/*!40000 ALTER TABLE `complaintreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `personID` int(11) NOT NULL,
  `fullName` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `payMethod` varchar(45) DEFAULT NULL,
  `accountBalance` float DEFAULT NULL,
  `creditCardNumber` varchar(45) DEFAULT NULL,
  `AccountStatus` tinyint(4) DEFAULT NULL,
  `StoreID` int(11) NOT NULL,
  `ExpirationDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`personID`,`StoreID`),
  KEY `fk_Customers_1_idx` (`StoreID`),
  CONSTRAINT `fk_Customers_1` FOREIGN KEY (`StoreID`) REFERENCES `store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (101,'customer 1','1111111111','CREDIT_CARD',300,'1111-1111-1111-1111',1,1,''),(101,'customer 1','1111111111','YEARLY_SUBSCRIPTION',1086,'1111-1111-1111-1111',1,2,'15-01-2019'),(101,'customer 1','1111111111','CREDIT_CARD',-100,'1111-1111-1111-1111',0,3,NULL),(102,'customer 2','2222222222','MONTHLY_SUBSCRIPTION',410,'2222-2222-2222-2222',1,1,'15-01-2018');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customersatisfactionsurveyresults`
--

DROP TABLE IF EXISTS `customersatisfactionsurveyresults`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customersatisfactionsurveyresults` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `answer1` int(2) DEFAULT NULL,
  `answer2` int(2) DEFAULT NULL,
  `answer3` int(2) DEFAULT NULL,
  `answer4` int(2) DEFAULT NULL,
  `answer5` int(2) DEFAULT NULL,
  `answer6` int(2) DEFAULT NULL,
  `storeID` int(11) DEFAULT NULL,
  `analysis` varchar(500) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `fk_customersatisfactionsurveyresults_1_idx` (`storeID`),
  CONSTRAINT `fk_customersatisfactionsurveyresults_1` FOREIGN KEY (`storeID`) REFERENCES `store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customersatisfactionsurveyresults`
--

LOCK TABLES `customersatisfactionsurveyresults` WRITE;
/*!40000 ALTER TABLE `customersatisfactionsurveyresults` DISABLE KEYS */;
INSERT INTO `customersatisfactionsurveyresults` VALUES (1,'2018-01-26',7,3,9,10,10,10,3,''),(2,'2018-01-26',7,3,9,10,10,10,3,''),(3,'2018-01-26',7,3,9,10,10,10,3,''),(4,'2018-01-26',7,3,9,10,10,10,3,''),(5,'2018-01-26',7,3,9,10,10,10,3,''),(6,'2018-01-26',7,3,9,10,10,10,3,''),(7,'2018-01-26',7,3,9,10,10,10,3,''),(8,'2018-01-26',7,3,9,10,10,10,3,''),(9,'2018-01-26',7,3,9,10,10,10,3,''),(10,'2018-01-26',7,3,9,10,10,10,3,''),(11,'2017-07-06',7,10,6,4,10,4,2,'The customer thought we were the BEST flower shop ever!'),(12,'2017-07-23',1,8,4,7,2,2,3,'The customer thought we were the BEST flower shop ever!'),(13,'2017-07-02',5,7,6,6,5,10,1,'The customer thought we were the BEST flower shop ever!'),(14,'2017-07-07',2,7,9,3,10,5,3,'The customer thought we were the BEST flower shop ever!'),(15,'2017-07-04',9,2,9,5,1,10,1,'The customer thought we were the BEST flower shop ever!'),(16,'2017-07-10',9,3,5,9,7,9,3,'The customer thought we were the BEST flower shop ever!'),(17,'2017-07-19',7,8,3,3,9,10,1,'The customer thought we were the BEST flower shop ever!'),(18,'2017-07-09',2,5,9,4,7,3,3,'The customer thought we were the BEST flower shop ever!'),(19,'2017-07-05',10,10,4,6,4,4,2,'The customer thought we were the BEST flower shop ever!'),(20,'2017-07-25',9,9,8,7,1,10,2,'The customer thought we were the BEST flower shop ever!'),(21,'2017-09-06',10,3,2,3,4,8,2,'The customer thought we were the BEST flower shop ever!'),(22,'2017-09-26',6,10,6,3,7,6,3,'The customer thought we were the BEST flower shop ever!'),(23,'2017-09-14',3,1,1,3,3,5,1,'The customer thought we were the BEST flower shop ever!'),(24,'2017-09-24',9,3,1,6,6,7,3,'The customer thought we were the BEST flower shop ever!'),(25,'2017-09-18',2,4,8,5,8,7,1,'The customer thought we were the BEST flower shop ever!'),(26,'2017-09-17',1,4,2,5,9,1,2,'The customer thought we were the BEST flower shop ever!'),(27,'2017-09-27',4,6,1,9,10,7,3,'The customer thought we were the BEST flower shop ever!'),(28,'2017-09-20',10,9,2,4,5,1,3,'The customer thought we were the BEST flower shop ever!'),(29,'2017-09-04',1,10,8,9,2,10,3,'The customer thought we were the BEST flower shop ever!'),(30,'2017-09-15',7,2,1,5,1,7,2,'The customer thought we were the BEST flower shop ever!'),(31,'2017-10-11',5,1,1,5,6,3,1,'The customer thought we were the BEST flower shop ever!'),(32,'2017-10-05',9,9,3,10,9,8,1,'The customer thought we were the BEST flower shop ever!'),(33,'2017-10-08',8,9,2,10,6,6,3,'The customer thought we were the BEST flower shop ever!'),(34,'2017-10-07',7,6,3,10,10,9,1,'The customer thought we were the BEST flower shop ever!'),(35,'2017-10-03',3,4,3,3,4,8,3,'The customer thought we were the BEST flower shop ever!'),(36,'2017-10-18',10,9,8,3,6,8,3,'The customer thought we were the BEST flower shop ever!'),(37,'2017-10-21',3,1,9,3,1,9,1,'The customer thought we were the BEST flower shop ever!'),(38,'2017-10-10',5,8,8,5,2,5,1,'The customer thought we were the BEST flower shop ever!'),(39,'2017-10-06',1,9,8,10,4,4,3,'The customer thought we were the BEST flower shop ever!'),(40,'2017-10-04',9,9,7,8,10,6,1,'The customer thought we were the BEST flower shop ever!'),(41,'2017-11-03',5,9,1,1,1,9,1,'The customer thought we were the BEST flower shop ever!'),(42,'2017-11-08',5,1,5,10,7,8,2,'The customer thought we were the BEST flower shop ever!'),(43,'2017-11-24',3,5,4,4,10,8,2,'The customer thought we were the BEST flower shop ever!'),(44,'2017-11-26',1,3,3,8,10,5,1,'The customer thought we were the BEST flower shop ever!'),(45,'2017-11-07',2,3,1,7,9,1,1,'The customer thought we were the BEST flower shop ever!'),(46,'2017-11-24',8,1,3,6,10,9,2,'The customer thought we were the BEST flower shop ever!'),(47,'2017-11-11',7,4,4,9,9,7,1,'The customer thought we were the BEST flower shop ever!'),(48,'2017-11-25',10,9,6,6,3,1,1,'The customer thought we were the BEST flower shop ever!'),(49,'2017-11-20',10,5,1,9,8,8,1,'The customer thought we were the BEST flower shop ever!'),(50,'2017-11-26',2,9,4,6,6,3,3,'The customer thought we were the BEST flower shop ever!'),(51,'2017-12-28',7,4,4,1,6,5,3,'The customer thought we were the BEST flower shop ever!'),(52,'2017-12-24',4,6,7,1,1,9,3,'The customer thought we were the BEST flower shop ever!'),(53,'2017-12-11',4,7,10,10,6,9,1,'The customer thought we were the BEST flower shop ever!'),(54,'2017-12-27',6,7,3,3,10,6,1,'The customer thought we were the BEST flower shop ever!'),(55,'2017-12-10',10,8,9,8,7,1,3,'The customer thought we were the BEST flower shop ever!'),(56,'2017-12-04',4,7,6,1,9,9,1,'The customer thought we were the BEST flower shop ever!'),(57,'2017-12-04',7,4,10,1,4,5,2,'The customer thought we were the BEST flower shop ever!'),(58,'2017-12-11',7,1,10,4,10,10,2,'The customer thought we were the BEST flower shop ever!'),(59,'2017-12-23',1,2,5,5,5,4,1,'The customer thought we were the BEST flower shop ever!'),(60,'2017-12-16',5,8,4,1,9,2,2,'The customer thought we were the BEST flower shop ever!'),(61,'2018-01-06',6,4,2,1,3,7,2,''),(62,'2018-01-11',3,1,3,9,6,3,1,''),(63,'2018-01-18',2,7,9,10,5,10,2,''),(64,'2018-01-02',7,10,2,4,10,10,2,''),(65,'2018-01-06',10,6,3,9,1,3,1,''),(66,'2018-01-02',3,4,1,9,1,1,1,''),(67,'2018-01-27',8,6,4,6,5,2,2,''),(68,'2018-01-25',10,1,6,2,9,7,1,''),(69,'2018-01-24',3,3,9,8,2,1,2,''),(70,'2018-01-26',7,3,9,10,10,10,3,'');
/*!40000 ALTER TABLE `customersatisfactionsurveyresults` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customitem`
--

DROP TABLE IF EXISTS `customitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customitem` (
  `CustomItemID` int(11) NOT NULL AUTO_INCREMENT,
  `CustomItemType` varchar(45) DEFAULT NULL,
  `CustomItemPrice` float DEFAULT NULL,
  `CustomItemColor` varchar(45) DEFAULT NULL,
  `CustomItemGreetingCard` varchar(100) DEFAULT NULL,
  `CustomItemOrderID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CustomItemID`),
  KEY `fk_CustomItem_1_idx` (`CustomItemOrderID`),
  CONSTRAINT `fk_CustomItem_1` FOREIGN KEY (`CustomItemOrderID`) REFERENCES `order` (`OrderID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customitem`
--

LOCK TABLES `customitem` WRITE;
/*!40000 ALTER TABLE `customitem` DISABLE KEYS */;
INSERT INTO `customitem` VALUES (1,'BOUQUET',196,'Red','Happy birthday!',109),(2,'BRIDE_BOUQUET',142,'Red','',120),(3,'BRIDE_BOUQUET',96,'White','',178);
/*!40000 ALTER TABLE `customitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customitemproduct`
--

DROP TABLE IF EXISTS `customitemproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customitemproduct` (
  `CustomItemID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductID` int(11) NOT NULL,
  `Amount` int(11) NOT NULL,
  `Price` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CustomItemID`,`ProductID`),
  KEY `fk_CustomItemProduct_2_idx` (`CustomItemID`),
  KEY `fk_CustomItemProduct_2_idx1` (`ProductID`),
  CONSTRAINT `fk_CustomItemProduct_1` FOREIGN KEY (`CustomItemID`) REFERENCES `customitem` (`CustomItemID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_CustomItemProduct_2` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customitemproduct`
--

LOCK TABLES `customitemproduct` WRITE;
/*!40000 ALTER TABLE `customitemproduct` DISABLE KEYS */;
INSERT INTO `customitemproduct` VALUES (1,7,4,'36.0'),(1,8,3,'30.0'),(1,10,10,'120.0'),(2,7,2,'18.0'),(2,8,2,'20.0'),(2,10,7,'84.0'),(3,7,5,'45.0'),(3,8,2,'20.0'),(3,9,1,'11.0');
/*!40000 ALTER TABLE `customitemproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incomereport`
--

DROP TABLE IF EXISTS `incomereport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incomereport` (
  `Quarterly` varchar(45) NOT NULL,
  `Year` varchar(45) NOT NULL,
  `StoreID` int(11) NOT NULL,
  `IncomeAmount` float DEFAULT NULL,
  PRIMARY KEY (`Quarterly`,`Year`,`StoreID`),
  KEY `fk_IncomeReport_1_idx` (`StoreID`),
  CONSTRAINT `fk_IncomeReport_1` FOREIGN KEY (`StoreID`) REFERENCES `store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incomereport`
--

LOCK TABLES `incomereport` WRITE;
/*!40000 ALTER TABLE `incomereport` DISABLE KEYS */;
INSERT INTO `incomereport` VALUES ('FIRST','2017',1,3020.2),('SECOND','2017',2,1290);
/*!40000 ALTER TABLE `incomereport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `OrderID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderStatus` varchar(45) DEFAULT NULL,
  `OrderPrice` float DEFAULT NULL,
  `OrderCreationDateTime` varchar(45) DEFAULT NULL,
  `OrderRequiredDate` varchar(45) DEFAULT NULL,
  `OrderRequiredTime` varchar(45) DEFAULT NULL,
  `OrderShipmentAddress` varchar(45) DEFAULT NULL,
  `OrderReceiverName` varchar(45) DEFAULT NULL,
  `OrderReceiverPhoneNumber` varchar(45) DEFAULT NULL,
  `OrderPaymentMethod` varchar(45) DEFAULT NULL,
  `OrderOriginStore` int(11) DEFAULT NULL,
  `OrderCustomerID` int(11) DEFAULT NULL,
  `OrderRefund` float DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `fk_Order_1_idx` (`OrderCustomerID`),
  CONSTRAINT `fk_Order_1` FOREIGN KEY (`OrderCustomerID`) REFERENCES `customers` (`personID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (108,'NEW',178,'2017-09-27 23:06:24','2018-01-28 02:06:24','',NULL,NULL,NULL,'CASH',1,101,0),(109,'NEW',196,'2017-09-27 23:06:24','2018-01-28 02:13:14','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(110,'NEW',200,'2017-09-27 23:06:24','2018-01-28 02:14:01','',NULL,NULL,NULL,'CASH',1,101,0),(111,'NEW',129,'2017-08-27 23:06:24','2018-01-28 02:14:10','',NULL,NULL,NULL,'CASH',1,101,0),(112,'NEW',229,'2017-08-27 23:06:24','2018-01-28 02:14:25','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(113,'NEW',193,'2017-08-27 23:06:24','2018-01-29 02:14:00','','Yellow submarine','Queen','0555996552','CREDITCARD',1,101,0),(114,'NEW',183,'2017-07-27 23:06:24','2018-01-28 02:15:18','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(115,'NEW',141.75,'2017-07-27 23:06:24','2018-01-28 02:15:39','',NULL,NULL,NULL,'SUBSCRIPTION',2,101,0),(116,'NEW',145.5,'2017-07-27 23:06:24','2018-01-28 02:15:47','',NULL,NULL,NULL,'SUBSCRIPTION',2,101,0),(117,'NEW',70.5,'2017-07-27 23:06:24','2018-01-28 02:15:55','',NULL,NULL,NULL,'SUBSCRIPTION',2,101,0),(118,'NEW',326.5,'2017-07-27 23:06:24','2018-01-28 02:16:06','',NULL,NULL,NULL,'CASH',2,101,0),(119,'NEW',194,'2017-07-27 23:06:24','2018-01-28 02:16:17','',NULL,NULL,NULL,'STORE_ACCOUNT',2,101,0),(120,'NEW',142,'2017-07-27 23:06:24','2018-01-28 02:24:25','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(121,'NEW',218,'2017-07-27 23:06:24','2018-01-28 02:24:35','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(122,'NEW',318,'2017-06-27 23:06:24','2018-01-28 02:24:47','',NULL,NULL,NULL,'CASH',1,102,0),(123,'NEW',89,'2017-06-27 23:06:24','2018-01-28 02:24:56','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(124,'NEW',94,'2017-06-27 23:06:24','2018-01-28 02:25:05','',NULL,NULL,NULL,'CASH',1,102,0),(125,'NEW',188,'2017-06-27 23:06:24','2018-01-28 02:25:41','','Somewhere over the rainbow','Dardas','0547556556','CREDITCARD',1,102,0),(126,'NEW',178,'2017-10-27 23:06:24','2018-01-28 02:25:51','',NULL,NULL,NULL,'CASH',1,102,0),(127,'NEW',401,'2017-10-27 23:06:24','2018-01-28 02:26:02','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(128,'NEW',89,'2017-10-27 23:06:24','2018-01-28 02:26:08','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(129,'NEW',94,'2017-10-27 23:06:24','2018-01-28 02:26:12','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(130,'NEW',223,'2017-10-27 23:06:24','2018-01-28 02:26:16','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(131,'NEW',89,'2017-11-27 23:06:24','2018-01-28 02:26:33','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(132,'NEW',178,'2017-11-27 23:06:24','2018-01-28 02:26:59','',NULL,NULL,NULL,'CASH',1,102,0),(133,'NEW',178,'2017-11-27 23:06:24','2018-01-28 02:27:03','',NULL,NULL,NULL,'CASH',1,102,0),(134,'NEW',178,'2017-11-27 23:06:24','2018-02-02 02:26:00','',NULL,NULL,NULL,'CASH',1,102,0),(135,'NEW',178,'2017-11-27 23:06:24','2018-02-02 02:26:00','',NULL,NULL,NULL,'CASH',1,102,0),(136,'NEW',89,'2017-11-27 23:06:24','2018-01-28 02:28:46','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(137,'NEW',94,'2017-11-27 23:06:24','2018-01-28 02:28:51','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(138,'NEW',129,'2017-11-27 23:06:24','2018-01-28 02:28:54','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(139,'NEW',200,'2017-11-27 23:06:24','2018-01-28 02:28:59','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(140,'NEW',100,'2017-11-27 23:06:24','2018-01-28 02:29:02','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(141,'NEW',100,'2017-11-27 23:06:24','2018-01-28 02:29:05','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(142,'NEW',100,'2017-11-27 23:06:24','2018-01-28 02:29:08','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(143,'NEW',129,'2017-12-27 23:06:24','2018-01-28 02:29:11','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(144,'NEW',100,'2017-12-27 23:06:24','2018-01-28 02:29:14','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(145,'NEW',100,'2017-12-27 23:06:24','2018-01-28 02:29:18','',NULL,NULL,NULL,'CREDITCARD',1,102,0),(146,'NEW',100,'2017-12-27 23:06:24','2018-01-28 02:29:33','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(147,'NEW',100,'2017-12-27 23:06:24','2018-01-28 02:29:36','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(148,'NEW',100,'2017-12-27 23:06:24','2018-01-28 02:29:39','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(149,'NEW',129,'2017-12-27 23:06:24','2018-01-28 02:29:41','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(150,'NEW',89,'2017-06-27 23:06:24','2018-01-28 02:29:44','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(151,'NEW',183,'2017-06-27 23:06:24','2018-01-28 02:29:48','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(152,'NEW',189,'2017-06-27 23:06:24','2018-01-28 02:29:51','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(153,'NEW',229,'2017-06-27 23:06:24','2018-01-28 02:29:54','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(154,'NEW',229,'2017-06-27 23:06:24','2018-01-28 02:29:59','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(155,'NEW',100,'2017-06-27 23:06:24','2018-01-28 02:30:11','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(156,'NEW',66.75,'2017-07-27 23:06:24','2018-01-28 02:30:26','',NULL,NULL,NULL,'SUBSCRIPTION',2,101,0),(157,'NEW',90,'2017-09-27 23:06:24','2018-01-28 02:30:35','',NULL,NULL,NULL,'SUBSCRIPTION',2,101,0),(158,'NEW',75,'2017-09-27 23:06:24','2018-01-28 02:30:41','',NULL,NULL,NULL,'SUBSCRIPTION',2,101,0),(159,'NEW',100,'2017-09-27 23:06:24','2018-01-28 02:30:48','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(160,'NEW',145,'2017-09-27 23:06:24','2018-01-28 02:30:53','',NULL,NULL,NULL,'CASH',2,101,0),(161,'NEW',94,'2017-09-27 23:06:24','2018-01-28 02:30:56','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(162,'NEW',189,'2017-09-27 23:06:24','2018-01-28 02:30:59','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(163,'NEW',94,'2017-09-27 23:06:24','2018-01-28 02:31:45','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(164,'NEW',94,'2017-09-27 23:06:24','2018-01-28 02:34:02','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(165,'NEW',89,'2017-09-27 23:06:24','2018-01-28 02:34:05','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(166,'NEW',100,'2017-09-27 23:06:24','2018-01-28 02:34:07','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(167,'NEW',120,'2017-09-27 23:06:24','2018-01-28 02:34:10','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(168,'NEW',100,'2017-10-27 23:06:24','2018-01-28 02:34:12','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(169,'NEW',89,'2017-10-27 23:06:24','2018-01-28 02:34:16','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(170,'NEW',94,'2017-10-27 23:06:24','2018-01-28 02:34:18','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(171,'NEW',89,'2017-12-27 23:06:24','2018-01-28 02:34:21','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(172,'NEW',403,'2017-12-27 23:06:24','2018-01-28 02:34:25','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(173,'NEW',100,'2017-12-27 23:06:24','2018-01-28 02:34:28','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(174,'NEW',120,'2017-09-27 23:06:24','2018-01-28 02:34:30','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(175,'NEW',100,'2017-09-27 23:06:24','2018-01-28 02:34:32','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(176,'NEW',89,'2017-09-27 23:06:24','2018-01-28 02:34:35','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(177,'NEW',194,'2017-09-27 23:06:24','2018-01-28 02:34:39','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(178,'NEW',96,'2017-09-27 23:06:24','2018-01-28 02:35:05','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(179,'NEW',100,'2017-07-27 23:06:24','2018-01-28 02:35:53','',NULL,NULL,NULL,'CASH',1,101,0),(180,'NEW',129,'2017-07-27 23:06:24','2018-01-28 02:36:00','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(181,'NEW',89,'2017-07-27 23:06:24','2018-01-28 02:36:03','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(182,'NEW',100,'2017-07-27 23:06:24','2018-01-28 02:36:05','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(183,'NEW',129,'2017-07-27 23:06:24','2018-01-28 02:36:07','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(184,'NEW',100,'2017-06-27 23:06:24','2018-01-28 02:36:10','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(185,'NEW',200,'2017-06-27 23:06:24','2018-01-28 02:36:14','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(186,'NEW',89,'2017-06-27 23:06:24','2018-01-28 02:36:16','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(187,'NEW',89,'2017-06-27 23:06:24','2018-01-28 02:36:18','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(188,'NEW',129,'2017-06-27 23:06:24','2018-01-28 02:36:21','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(189,'NEW',89,'2017-07-27 23:06:24','2018-01-28 02:36:23','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(190,'NEW',129,'2017-07-27 23:06:24','2018-01-28 02:36:26','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(191,'NEW',100,'2017-08-27 23:06:24','2018-01-28 02:36:28','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(192,'NEW',129,'2017-08-27 23:06:24','2018-01-28 02:36:30','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(193,'NEW',129,'2017-08-27 23:06:24','2018-01-28 02:37:07','',NULL,NULL,NULL,'CASH',1,101,0),(194,'NEW',89,'2017-08-27 23:06:24','2018-01-28 02:37:10','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(195,'NEW',129,'2017-08-27 23:06:24','2018-01-28 02:37:12','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(196,'NEW',100,'2017-08-27 23:06:24','2018-01-28 02:37:15','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(197,'NEW',89,'2017-08-27 23:06:24','2018-01-28 02:37:17','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(198,'NEW',129,'2017-08-27 23:06:24','2018-01-28 02:37:19','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(199,'NEW',89,'2017-08-27 23:06:24','2018-01-28 02:37:21','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(200,'NEW',129,'2017-08-27 23:06:24','2018-01-28 02:37:25','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(201,'NEW',100,'2017-08-27 23:06:24','2018-01-28 02:37:27','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(202,'NEW',100,'2017-08-27 23:06:24','2018-01-28 02:37:30','',NULL,NULL,NULL,'CREDITCARD',1,101,0),(203,'NEW',89,'2017-09-27 23:06:24','2018-01-28 02:37:38','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(204,'NEW',89,'2017-10-27 23:06:24','2018-01-28 02:37:40','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(205,'NEW',183,'2017-10-27 23:06:24','2018-01-28 02:37:44','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(206,'NEW',94,'2017-10-27 23:06:24','2018-01-28 02:37:46','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(207,'NEW',89,'2017-10-27 23:06:24','2018-01-28 02:37:48','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(208,'NEW',100,'2017-10-27 23:06:24','2018-01-28 02:37:50','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(209,'NEW',94,'2017-10-27 23:06:24','2018-01-28 02:37:53','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(210,'NEW',94,'2017-12-27 23:06:24','2018-01-28 02:37:55','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(211,'NEW',94,'2017-12-27 23:06:24','2018-01-28 02:37:57','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(212,'NEW',94,'2017-12-27 23:06:24','2018-01-28 02:38:00','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(213,'NEW',94,'2017-12-27 23:06:24','2018-01-28 02:38:02','',NULL,NULL,NULL,'CREDITCARD',2,101,0),(214,'NEW',100,'2017-08-27 23:06:24','2018-01-28 02:41:17','',NULL,NULL,NULL,'CREDITCARD',1,102,0);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordercomplaint`
--

DROP TABLE IF EXISTS `ordercomplaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordercomplaint` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) DEFAULT NULL,
  `customerName` varchar(100) DEFAULT NULL,
  `customerPhoneNumber` varchar(100) DEFAULT NULL,
  `storeID` int(11) DEFAULT NULL,
  `complaintDescription` varchar(500) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `givenCompensationAmount` float DEFAULT NULL,
  `maxCompensationAmount` float DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `orderID` int(11) DEFAULT NULL,
  `addedBy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordercomplaint`
--

LOCK TABLES `ordercomplaint` WRITE;
/*!40000 ALTER TABLE `ordercomplaint` DISABLE KEYS */;
INSERT INTO `ordercomplaint` VALUES (23,101,'customer 1','1111111111',1,'Delivery way too slow!','2017-07-27','23:44',30,200,'NEW',185,'serviceWorker1'),(24,101,'customer 1','1111111111',1,'Delivery way too slow!','2017-07-27','23:44',30,89,'CLOSED',187,'serviceWorker1'),(25,101,'customer 1','1111111111',1,'Bad service!','2017-07-27','23:44',30,129,'NEW',188,'serviceWorker1'),(26,101,'customer 1','1111111111',1,'Delivery way too slow!','2017-08-27','23:44',30,129,'CLOSED',183,'serviceWorker1'),(27,101,'customer 1','1111111111',1,'Delivery way too slow!','2017-08-27','23:44',30,100,'NEW',201,'serviceWorker1'),(28,101,'customer 1','1111111111',1,'You suck!','2017-08-27','23:44',30,100,'NEW',202,'serviceWorker1'),(29,101,'customer 1','1111111111',1,'They smell bad!','2017-08-27','23:44',30,129,'CLOSED',200,'serviceWorker1'),(30,101,'customer 1','1111111111',1,'I hate this store!','2017-09-27','23:44',30,89,'CLOSED',197,'serviceWorker1'),(31,101,'customer 1','1111111111',1,'Delivery way too slow!','2017-09-27','23:45',30,100,'NEW',201,'serviceWorker1'),(32,101,'customer 1','1111111111',1,'Delivery way too slow!','2017-09-27','23:45',30,100,'CLOSED',202,'serviceWorker1'),(33,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-09-27','23:45',30,94,'NEW',211,'serviceWorker1'),(34,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-09-27','23:45',30,141.75,'CLOSED',115,'serviceWorker1'),(35,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-12-27','23:45',30,90,'CLOSED',157,'serviceWorker1'),(36,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-12-27','23:45',30,75,'NEW',158,'serviceWorker1'),(37,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-12-27','23:45',30,100,'CLOSED',173,'serviceWorker1'),(38,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-12-27','23:45',30,94,'NEW',211,'serviceWorker1'),(39,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-12-27','23:45',30,94,'CLOSED',211,'serviceWorker1'),(40,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-11-27','23:45',30,94,'CLOSED',213,'serviceWorker1'),(41,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-11-27','23:45',30,94,'NEW',211,'serviceWorker1'),(42,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-11-27','23:45',30,94,'CLOSED',210,'serviceWorker1'),(43,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-11-27','23:45',30,94,'CLOSED',209,'serviceWorker1'),(44,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-11-27','23:45',30,100,'NEW',208,'serviceWorker1'),(45,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-11-27','23:46',30,100,'NEW',175,'serviceWorker1'),(46,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-11-27','23:46',30,194,'CLOSED',177,'serviceWorker1'),(47,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-10-27','23:46',30,194,'NEW',177,'serviceWorker1'),(48,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-10-27','23:46',30,96,'CLOSED',178,'serviceWorker1'),(49,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-10-27','23:46',30,100,'CLOSED',208,'serviceWorker1'),(50,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-10-27','23:46',30,100,'CLOSED',208,'serviceWorker1'),(51,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-11-27','23:46',30,178,'CLOSED',126,'serviceWorker1'),(52,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-11-27','23:46',30,178,'NEW',126,'serviceWorker1'),(53,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-11-27','23:46',30,401,'NEW',127,'serviceWorker1'),(54,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-11-27','23:46',30,89,'CLOSED',128,'serviceWorker1'),(55,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-10-27','23:46',30,100,'CLOSED',142,'serviceWorker1'),(56,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-10-27','23:46',30,129,'NEW',143,'serviceWorker1'),(57,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-10-27','23:46',30,100,'CLOSED',145,'serviceWorker1'),(58,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-10-27','23:47',30,100,'NEW',214,'serviceWorker1'),(59,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-12-27','23:47',30,100,'NEW',141,'serviceWorker1'),(60,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-12-27','23:47',30,94,'CLOSED',129,'serviceWorker1'),(61,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-12-27','23:47',30,223,'NEW',130,'serviceWorker1'),(62,102,'customer 2','2222222222',1,'Delivery way too slow!','2017-12-27','23:47',30,89,'CLOSED',123,'serviceWorker1'),(63,101,'customer 1','1111111111',2,'Delivery way too slow!','2017-10-27','23:47',30,70.5,'NEW',117,'serviceWorker2'),(64,101,'customer 1','1111111111',2,'I did not like the service!','2017-09-27','23:48',30,90,'CLOSED',157,'serviceWorker2'),(65,101,'customer 1','1111111111',2,'I did not like the service!','2017-09-27','23:48',30,66.75,'NEW',156,'serviceWorker2'),(66,101,'customer 1','1111111111',2,'I did not like the service!','2017-09-27','23:48',30,89,'CLOSED',203,'serviceWorker2'),(67,101,'customer 1','1111111111',2,'I did not like the service!','2017-09-27','23:48',30,94,'NEW',211,'serviceWorker2'),(68,101,'customer 1','1111111111',2,'I did not like the service!','2017-09-27','23:48',30,94,'CLOSED',213,'serviceWorker2'),(69,101,'customer 1','1111111111',2,'I did not like the service!','2017-08-27','23:48',30,94,'NEW',212,'serviceWorker2'),(70,101,'customer 1','1111111111',2,'I did not like the service!','2017-08-27','23:48',30,100,'CLOSED',208,'serviceWorker2'),(71,101,'customer 1','1111111111',2,'I did not like the service!','2017-08-27','23:48',30,89,'NEW',207,'serviceWorker2'),(72,101,'customer 1','1111111111',2,'I did not like the service!','2017-08-27','23:48',30,403,'CLOSED',172,'serviceWorker2'),(73,101,'customer 1','1111111111',2,'I did not like the service!','2017-08-27','23:48',30,100,'NEW',173,'serviceWorker2'),(74,101,'customer 1','1111111111',2,'I did not like the service!','2017-08-27','23:48',30,120,'NEW',174,'serviceWorker2'),(75,101,'customer 1','1111111111',2,'I did not like the service!','2017-07-27','23:48',30,120,'CLOSED',174,'serviceWorker2'),(76,101,'customer 1','1111111111',2,'I did not like the service!','2017-07-27','23:48',30,100,'NEW',175,'serviceWorker2'),(77,101,'customer 1','1111111111',2,'I did not like the service!','2017-07-27','23:48',30,89,'CLOSED',176,'serviceWorker2'),(78,101,'customer 1','1111111111',2,'I did not like the service!','2017-07-27','23:48',30,94,'CLOSED',170,'serviceWorker2'),(79,101,'customer 1','1111111111',2,'I did not like the service!','2017-07-27','23:48',30,89,'CLOSED',171,'serviceWorker2'),(80,101,'customer 1','1111111111',2,'I did not like the service!','2017-07-27','23:49',30,100,'NEW',173,'serviceWorker2'),(81,101,'customer 1','1111111111',2,'You guys are so so :S','2018-01-28','00:08',-1,70.5,'NEW',117,'serviceWorker1'),(82,101,'customer 1','1111111111',2,'I am complaining!!','2018-01-28','00:12',-1,70.5,'NEW',117,'serviceWorker1');
/*!40000 ALTER TABLE `ordercomplaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderreport`
--

DROP TABLE IF EXISTS `orderreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderreport` (
  `Quarterly` varchar(45) NOT NULL,
  `Year` varchar(45) NOT NULL,
  `StoreID` int(11) NOT NULL,
  `TotalOrdersAmount` int(11) DEFAULT NULL,
  `BouquetAmount` int(11) DEFAULT NULL,
  `BrideBouquetAmount` int(11) DEFAULT NULL,
  `FlowerPotAmount` int(11) DEFAULT NULL,
  `CustomAmount` int(11) DEFAULT NULL,
  `FlowerClusterAmount` int(11) DEFAULT NULL,
  PRIMARY KEY (`Quarterly`,`Year`,`StoreID`),
  KEY `OrderReport_idx` (`StoreID`),
  CONSTRAINT `fk_OrderReport_1` FOREIGN KEY (`StoreID`) REFERENCES `store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderreport`
--

LOCK TABLES `orderreport` WRITE;
/*!40000 ALTER TABLE `orderreport` DISABLE KEYS */;
INSERT INTO `orderreport` VALUES ('FOURTH','2017',2,125,25,25,23,24,28),('THIRD','2017',2,265,51,52,53,54,55);
/*!40000 ALTER TABLE `orderreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `ProductID` int(11) NOT NULL,
  `ProductName` varchar(45) DEFAULT NULL,
  `ProductType` varchar(45) DEFAULT NULL,
  `ProductPrice` float DEFAULT NULL,
  `ProductAmount` int(11) DEFAULT NULL,
  `ProductColor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ProductID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'roses bouquet','Bouquet',90,15,'Red'),(2,'Chrysanthemums bouquet','Bouquet',20,20,'White'),(3,'Avalanche bouquet','Bouquet',15,10,'Purple'),(4,'Panter bouquet','Bouquet',151,5,'Pink'),(5,'Buttercup bouquet','Bouquet',94,10,'Red'),(6,'Anemone bouquet','Bouquet',123,15,'Red'),(7,'Chrysanthemums','FLOWER',9,30,'White'),(8,'Limonium','FLOWER',10,30,'Purple'),(9,'Sunflower','FLOWER',11,30,'Yellow'),(10,'roses','FLOWER',12,40,'Red'),(11,'Small Gozmania','PLANT',33,20,'Yellow'),(12,'Medium Gozmania','PLANT',69,30,'Yellow'),(13,'Big Gozmania','PLANT',99,25,'Yellow'),(14,'Small Anthurium','PLANT',29,15,'Red'),(15,'Medium Anthurium','PLANT',79,20,'Red'),(16,'Big Anthurium','PLANT',109,30,'Red'),(17,'Small Cocktail','PLANT',19,30,'Pink'),(18,'Medium Cocktail','PLANT',49,20,'Pink'),(19,'Big Cocktail','PLANT',89,10,'Pink'),(20,'SnowWhite','Bridal Bouquet',149,20,'White'),(21,'Cinderella','Bridal Bouquet',159,20,'White'),(22,'Olive','Flower Pot',89,20,'Green'),(23,'Spathiphyllum','Flower Pot',199,20,'Green'),(24,'sunshine','Flowers Cluster',89,20,'Yellow'),(25,'Violet Winter','Flowers Cluster',129,20,'Purple');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productinorder`
--

DROP TABLE IF EXISTS `productinorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productinorder` (
  `ProductID` int(11) NOT NULL,
  `OrderID` int(11) NOT NULL,
  `ProductGrettingCard` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ProductID`,`OrderID`),
  KEY `fk_ProductInOrder_2_idx` (`OrderID`),
  CONSTRAINT `fk_ProductInOrder_1` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ProductInOrder_2` FOREIGN KEY (`OrderID`) REFERENCES `order` (`OrderID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productinorder`
--

LOCK TABLES `productinorder` WRITE;
/*!40000 ALTER TABLE `productinorder` DISABLE KEYS */;
INSERT INTO `productinorder` VALUES (4,110,''),(4,112,'Granny!'),(4,115,''),(4,116,''),(4,119,''),(4,139,''),(4,141,''),(4,142,''),(4,145,''),(4,146,''),(4,148,''),(4,152,''),(4,154,''),(4,155,'Its dangerous to go alone, take this with you!'),(4,158,''),(4,159,''),(4,162,''),(4,166,''),(4,168,''),(4,172,''),(4,173,''),(4,175,''),(4,177,''),(4,182,''),(4,184,''),(4,185,''),(4,196,''),(4,202,''),(4,208,''),(5,113,''),(5,114,''),(5,116,''),(5,117,''),(5,118,''),(5,119,''),(5,124,''),(5,127,''),(5,129,''),(5,130,''),(5,137,''),(5,151,''),(5,161,''),(5,163,''),(5,164,''),(5,170,''),(5,172,''),(5,177,''),(5,205,''),(5,206,''),(5,209,''),(5,210,''),(5,211,''),(5,212,''),(5,213,''),(20,110,''),(20,122,''),(20,139,''),(20,140,''),(20,144,''),(20,147,''),(20,153,''),(20,179,''),(20,185,''),(20,191,''),(20,201,''),(21,118,''),(21,157,''),(21,160,''),(21,167,''),(21,172,''),(21,174,''),(22,108,'To granny.'),(22,114,''),(22,125,''),(22,126,''),(22,127,''),(22,135,''),(22,136,'Its dangerous to go alone, take this with you!'),(22,151,''),(24,108,'To my dear mom.'),(24,113,''),(24,115,''),(24,118,''),(24,121,''),(24,122,''),(24,123,''),(24,125,''),(24,126,''),(24,127,''),(24,128,''),(24,131,'Lalala happy happy new year'),(24,135,'Its dangerous to go alone, take this with you!'),(24,150,''),(24,152,''),(24,156,'Its dangerous to go alone, take this with you!'),(24,162,''),(24,165,''),(24,169,''),(24,171,''),(24,172,''),(24,176,''),(24,181,''),(24,186,''),(24,187,''),(24,189,''),(24,194,''),(24,197,''),(24,199,''),(24,203,''),(24,204,''),(24,205,''),(24,207,''),(25,111,''),(25,112,''),(25,121,''),(25,122,''),(25,127,''),(25,130,''),(25,138,''),(25,143,''),(25,149,''),(25,153,''),(25,154,''),(25,180,''),(25,183,''),(25,188,''),(25,190,''),(25,192,''),(25,193,''),(25,195,''),(25,198,''),(25,200,'');
/*!40000 ALTER TABLE `productinorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `StoreID` int(11) NOT NULL,
  `StoreAddress` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`StoreID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (0,'Base'),(1,'Haifa'),(2,'Karmiel'),(3,'Qiryat Biyalik');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeemployees`
--

DROP TABLE IF EXISTS `storeemployees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeemployees` (
  `username` varchar(45) NOT NULL,
  `storeID` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeemployees`
--

LOCK TABLES `storeemployees` WRITE;
/*!40000 ALTER TABLE `storeemployees` DISABLE KEYS */;
INSERT INTO `storeemployees` VALUES ('storeManager1',1,'STORE_MANAGER'),('storeManager2',2,'STORE_MANAGER'),('storeManager3',3,'STORE_MANAGER'),('storeWorker1',1,'STORE_WORKER'),('storeWorker2',2,'STORE_WORKER'),('storeWorker3',3,'STORE_WORKER');
/*!40000 ALTER TABLE `storeemployees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surveyreport`
--

DROP TABLE IF EXISTS `surveyreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `surveyreport` (
  `Quarterly` varchar(45) NOT NULL,
  `Year` varchar(45) NOT NULL,
  `StoreID` int(11) NOT NULL,
  `FirstSurveyAverageResult` int(11) DEFAULT NULL,
  `SecondSurveyAverageResult` int(11) DEFAULT NULL,
  `ThirdSurveyAverageResult` int(11) DEFAULT NULL,
  `FourthSurveyAverageResult` int(11) DEFAULT NULL,
  `FifthSurveyAverageResult` int(11) DEFAULT NULL,
  `SixthSurveyAverageResult` int(11) DEFAULT NULL,
  PRIMARY KEY (`Quarterly`,`Year`,`StoreID`),
  KEY `fk_SurveyReport_1_idx` (`StoreID`),
  CONSTRAINT `fk_SurveyReport_1` FOREIGN KEY (`StoreID`) REFERENCES `store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surveyreport`
--

LOCK TABLES `surveyreport` WRITE;
/*!40000 ALTER TABLE `surveyreport` DISABLE KEYS */;
INSERT INTO `surveyreport` VALUES ('FOURTH','2017',2,1,3,5,7,9,4),('SECOND','2017',1,4,7,6,9,10,2);
/*!40000 ALTER TABLE `surveyreport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `UserName` varchar(45) NOT NULL,
  `UserPassword` varchar(45) DEFAULT NULL,
  `UserPermission` varchar(45) DEFAULT NULL,
  `personID` int(11) DEFAULT NULL,
  `UserStatus` varchar(45) DEFAULT NULL,
  `unsuccessfulTries` int(11) DEFAULT NULL,
  PRIMARY KEY (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('admin','123456','SYSTEM_MANAGER',98,'REGULAR',0),('customer1','123456','CUSTOMER',101,'REGULAR',0),('customer2','123456','CUSTOMER',102,'REGULAR',0),('customerService','123456','CUSTOMER_SERVICE',105,'REGULAR',0),('networkManager','123456','NETWORK_MANAGER',103,'REGULAR',0),('networkWorker','123456','NETWORK_WORKER',100,'REGULAR',0),('service','123456','CUSTOMER_SERVICE',112,'REGULAR',0),('serviceExpert','123456','CUSTOMER_SERVICE_EXPERT',109,'REGULAR',0),('serviceWorker1','123456','CUSTOMER_SERVICE_WORKER',106,'REGULAR',0),('serviceWorker2','123456','CUSTOMER_SERVICE_WORKER',107,'REGULAR',0),('storeManager','123456','STORE_MANAGER',99,'REGULAR',0),('storeWorker1','123456','STORE_WORKER',104,'REGULAR',0),('storeWorker2','123456','STORE_WORKER',110,'REGULAR',0),('storeWorker3','123456','STORE_WORKER',111,'REGULAR',0),('systemManager1','123456','SYSTEM_MANAGER',108,'REGULAR',0),('systemManager2','123456','SYSTEM_MANAGER',112,'REGULAR',0),('systemManager3','123456','SYSTEM_MANAGER',113,'REGULAR',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-28  0:25:03
