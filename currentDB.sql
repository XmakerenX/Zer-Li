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
INSERT INTO `customers` VALUES (101,'customer 1','1111111111','CREDIT_CARD',0,'1111-1111-1111-1111',1,1,''),(101,'customer 1','1111111111','YEARLY_SUBSCRIPTION',200,'1111-1111-1111-1111',1,2,'15-01-2019'),(101,'customer 1','1111111111','CREDIT_CARD',-100,'1111-1111-1111-1111',0,3,NULL),(102,'customer 2','2222222222','MONTHLY_SUBSCRIPTION',50,'2222-2222-2222-2222',1,1,'15-01-2018');
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
INSERT INTO `customersatisfactionsurveyresults` VALUES (11,'2017-07-06',7,10,6,4,10,4,2,'The customer thought we were the BEST flower shop ever!'),(12,'2017-07-23',1,8,4,7,2,2,3,'The customer thought we were the BEST flower shop ever!'),(13,'2017-07-02',5,7,6,6,5,10,1,'The customer thought we were the BEST flower shop ever!'),(14,'2017-07-07',2,7,9,3,10,5,3,'The customer thought we were the BEST flower shop ever!'),(15,'2017-07-04',9,2,9,5,1,10,1,'The customer thought we were the BEST flower shop ever!'),(16,'2017-07-10',9,3,5,9,7,9,3,'The customer thought we were the BEST flower shop ever!'),(17,'2017-07-19',7,8,3,3,9,10,1,'The customer thought we were the BEST flower shop ever!'),(18,'2017-07-09',2,5,9,4,7,3,3,'The customer thought we were the BEST flower shop ever!'),(19,'2017-07-05',10,10,4,6,4,4,2,'The customer thought we were the BEST flower shop ever!'),(20,'2017-07-25',9,9,8,7,1,10,2,'The customer thought we were the BEST flower shop ever!'),(21,'2017-09-06',10,3,2,3,4,8,2,'The customer thought we were the BEST flower shop ever!'),(22,'2017-09-26',6,10,6,3,7,6,3,'The customer thought we were the BEST flower shop ever!'),(23,'2017-09-14',3,1,1,3,3,5,1,'The customer thought we were the BEST flower shop ever!'),(24,'2017-09-24',9,3,1,6,6,7,3,'The customer thought we were the BEST flower shop ever!'),(25,'2017-09-18',2,4,8,5,8,7,1,'The customer thought we were the BEST flower shop ever!'),(26,'2017-09-17',1,4,2,5,9,1,2,'The customer thought we were the BEST flower shop ever!'),(27,'2017-09-27',4,6,1,9,10,7,3,'The customer thought we were the BEST flower shop ever!'),(28,'2017-09-20',10,9,2,4,5,1,3,'The customer thought we were the BEST flower shop ever!'),(29,'2017-09-04',1,10,8,9,2,10,3,'The customer thought we were the BEST flower shop ever!'),(30,'2017-09-15',7,2,1,5,1,7,2,'The customer thought we were the BEST flower shop ever!'),(31,'2017-10-11',5,1,1,5,6,3,1,'The customer thought we were the BEST flower shop ever!'),(32,'2017-10-05',9,9,3,10,9,8,1,'The customer thought we were the BEST flower shop ever!'),(33,'2017-10-08',8,9,2,10,6,6,3,'The customer thought we were the BEST flower shop ever!'),(34,'2017-10-07',7,6,3,10,10,9,1,'The customer thought we were the BEST flower shop ever!'),(35,'2017-10-03',3,4,3,3,4,8,3,'The customer thought we were the BEST flower shop ever!'),(36,'2017-10-18',10,9,8,3,6,8,3,'The customer thought we were the BEST flower shop ever!'),(37,'2017-10-21',3,1,9,3,1,9,1,'The customer thought we were the BEST flower shop ever!'),(38,'2017-10-10',5,8,8,5,2,5,1,'The customer thought we were the BEST flower shop ever!'),(39,'2017-10-06',1,9,8,10,4,4,3,'The customer thought we were the BEST flower shop ever!'),(40,'2017-10-04',9,9,7,8,10,6,1,'The customer thought we were the BEST flower shop ever!'),(41,'2017-11-03',5,9,1,1,1,9,1,'The customer thought we were the BEST flower shop ever!'),(42,'2017-11-08',5,1,5,10,7,8,2,'The customer thought we were the BEST flower shop ever!'),(43,'2017-11-24',3,5,4,4,10,8,2,'The customer thought we were the BEST flower shop ever!'),(44,'2017-11-26',1,3,3,8,10,5,1,'The customer thought we were the BEST flower shop ever!'),(45,'2017-11-07',2,3,1,7,9,1,1,'The customer thought we were the BEST flower shop ever!'),(46,'2017-11-24',8,1,3,6,10,9,2,'The customer thought we were the BEST flower shop ever!'),(47,'2017-11-11',7,4,4,9,9,7,1,'The customer thought we were the BEST flower shop ever!'),(48,'2017-11-25',10,9,6,6,3,1,1,'The customer thought we were the BEST flower shop ever!'),(49,'2017-11-20',10,5,1,9,8,8,1,'The customer thought we were the BEST flower shop ever!'),(50,'2017-11-26',2,9,4,6,6,3,3,'The customer thought we were the BEST flower shop ever!'),(51,'2017-12-28',7,4,4,1,6,5,3,'The customer thought we were the BEST flower shop ever!'),(52,'2017-12-24',4,6,7,1,1,9,3,'The customer thought we were the BEST flower shop ever!'),(53,'2017-12-11',4,7,10,10,6,9,1,'The customer thought we were the BEST flower shop ever!'),(54,'2017-12-27',6,7,3,3,10,6,1,'The customer thought we were the BEST flower shop ever!'),(55,'2017-12-10',10,8,9,8,7,1,3,'The customer thought we were the BEST flower shop ever!'),(56,'2017-12-04',4,7,6,1,9,9,1,'The customer thought we were the BEST flower shop ever!'),(57,'2017-12-04',7,4,10,1,4,5,2,'The customer thought we were the BEST flower shop ever!'),(58,'2017-12-11',7,1,10,4,10,10,2,'The customer thought we were the BEST flower shop ever!'),(59,'2017-12-23',1,2,5,5,5,4,1,'The customer thought we were the BEST flower shop ever!'),(60,'2017-12-16',5,8,4,1,9,2,2,'The customer thought we were the BEST flower shop ever!'),(61,'2018-01-06',6,4,2,1,3,7,2,''),(62,'2018-01-11',3,1,3,9,6,3,1,''),(63,'2018-01-18',2,7,9,10,5,10,2,''),(64,'2018-01-02',7,10,2,4,10,10,2,''),(65,'2018-01-06',10,6,3,9,1,3,1,''),(66,'2018-01-02',3,4,1,9,1,1,1,''),(67,'2018-01-27',8,6,4,6,5,2,2,''),(68,'2018-01-25',10,1,6,2,9,7,1,''),(69,'2018-01-24',3,3,9,8,2,1,2,''),(70,'2018-01-26',7,3,9,10,10,10,3,'');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customitem`
--

LOCK TABLES `customitem` WRITE;
/*!40000 ALTER TABLE `customitem` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customitemproduct`
--

LOCK TABLES `customitemproduct` WRITE;
/*!40000 ALTER TABLE `customitemproduct` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (108,'NEW',178,'2018-01-27 23:06:24','2018-01-28 02:06:24','',NULL,NULL,NULL,'CASH',1,101,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordercomplaint`
--

LOCK TABLES `ordercomplaint` WRITE;
/*!40000 ALTER TABLE `ordercomplaint` DISABLE KEYS */;
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
INSERT INTO `productinorder` VALUES (22,108,'To granny.'),(24,108,'To my dear mom.');
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
INSERT INTO `user` VALUES ('admin','123456','SYSTEM_MANAGER',98,'REGULAR',0),('customer1','123456','CUSTOMER',101,'REGULAR',0),('customer2','123456','CUSTOMER',102,'REGULAR',0),('customerService','123456','CUSTOMER_SERVICE',105,'REGULAR',0),('networkManager','123456','NETWORK_MANAGER',103,'REGULAR',0),('networkWorker','123456','NETWORK_WORKER',100,'REGULAR',0),('service','123456','CUSTOMER_SERVICE',112,'REGULAR',0),('serviceExpert','123456','CUSTOMER_SERVICE_EXPERT',109,'REGULAR',0),('serviceWorker1','123456','CUSTOMER_SERVICE_WORKER',106,'REGULAR',4),('serviceWorker2','123456','CUSTOMER_SERVICE_WORKER',107,'REGULAR',0),('storeManager','123456','STORE_MANAGER',99,'REGULAR',0),('storeWorker1','123456','STORE_WORKER',104,'REGULAR',0),('storeWorker2','123456','STORE_WORKER',110,'REGULAR',0),('storeWorker3','123456','STORE_WORKER',111,'REGULAR',0),('systemManager1','123456','SYSTEM_MANAGER',108,'REGULAR',0),('systemManager2','123456','SYSTEM_MANAGER',112,'REGULAR',0),('systemManager3','123456','SYSTEM_MANAGER',113,'REGULAR',0);
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

-- Dump completed on 2018-01-27 23:09:55
