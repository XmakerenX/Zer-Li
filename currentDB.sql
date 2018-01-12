-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: prototype
-- ------------------------------------------------------
-- Server version	5.7.20

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
-- Table structure for table `CatalogProduct`
--

DROP TABLE IF EXISTS `CatalogProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CatalogProduct` (
  `productID` int(11) NOT NULL,
  `salesPrice` float DEFAULT NULL,
  `Image` varchar(256) DEFAULT NULL,
  `StoreID` int(11) NOT NULL,
  PRIMARY KEY (`productID`,`StoreID`),
  KEY `storeid_idx` (`StoreID`),
  CONSTRAINT `pID` FOREIGN KEY (`productID`) REFERENCES `Product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `storeid` FOREIGN KEY (`StoreID`) REFERENCES `Store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CatalogProduct`
--

LOCK TABLES `CatalogProduct` WRITE;
/*!40000 ALTER TABLE `CatalogProduct` DISABLE KEYS */;
INSERT INTO `CatalogProduct` VALUES (2,-1,NULL,2),(3,-1,NULL,1),(4,100,'panter.jpg',0),(5,-1,'buttercup.jpg',0),(6,50,NULL,0),(6,10,NULL,3);
/*!40000 ALTER TABLE `CatalogProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Customers`
--

DROP TABLE IF EXISTS `Customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Customers` (
  `personID` int(11) NOT NULL,
  `fullName` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `payMethod` varchar(45) DEFAULT NULL,
  `accountBalance` float DEFAULT NULL,
  `creditCardNumber` varchar(45) DEFAULT NULL,
  `AccountStatus` tinyint(4) DEFAULT NULL,
  `StoreID` int(11) NOT NULL,
  PRIMARY KEY (`personID`,`StoreID`),
  KEY `fk_Customers_1_idx` (`StoreID`),
  CONSTRAINT `fk_Customers_1` FOREIGN KEY (`StoreID`) REFERENCES `Store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customers`
--

LOCK TABLES `Customers` WRITE;
/*!40000 ALTER TABLE `Customers` DISABLE KEYS */;
INSERT INTO `Customers` VALUES (1234,'mk','452','CREDIT_CARD',0,'3423',0,2),(12345,'Dzon Levi','101','CREDIT_CARD',0,'4356768',NULL,1),(305022949,'matan k','0507788765','CREDIT_CARD',0,'1111-2222-3333-4444',NULL,1),(305022949,'matan k','0507788765','CREDIT_CARD',0,'1111-2222-3333-4444',0,3);
/*!40000 ALTER TABLE `Customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order`
--

DROP TABLE IF EXISTS `Order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Order` (
  `OrderID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderStatus` varchar(45) DEFAULT NULL,
  `OrderPrice` float DEFAULT NULL,
  `OrderRequiredDate` date DEFAULT NULL,
  `OrderRequiredTime` varchar(45) DEFAULT NULL,
  `OrderShipmentAddress` varchar(45) DEFAULT NULL,
  `OrderReceiverName` varchar(45) DEFAULT NULL,
  `OrderReceiverPhoneNumber` varchar(45) DEFAULT NULL,
  `OrderPaymentMethod` varchar(45) DEFAULT NULL,
  `OrderOriginStore` int(11) DEFAULT NULL,
  `OrderCustomerID` int(11) DEFAULT NULL,
  PRIMARY KEY (`OrderID`),
  KEY `fk_Order_1_idx` (`OrderCustomerID`),
  CONSTRAINT `fk_Order_1` FOREIGN KEY (`OrderCustomerID`) REFERENCES `Customers` (`personID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order`
--

LOCK TABLES `Order` WRITE;
/*!40000 ALTER TABLE `Order` DISABLE KEYS */;
INSERT INTO `Order` VALUES (32,'NEW',194,'2018-01-12','12:12',NULL,NULL,NULL,'CREDITCARD',1,305022949),(33,'NEW',124,'2018-01-25','12:12','23','32','45','CREDITCARD',2,1234);
/*!40000 ALTER TABLE `Order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Product` (
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
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
INSERT INTO `Product` VALUES (2,'Chrysanthemums','bouquet',20,20,'White'),(3,'Avalanche','bouquet',15,10,'Purple'),(4,'Panter','bouquet',151,5,'Purple'),(5,'Buttercup','bouquet',94,10,'Red'),(6,'Anemone','bouquet',123,15,'Red');
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProductInOrder`
--

DROP TABLE IF EXISTS `ProductInOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProductInOrder` (
  `ProductID` int(11) NOT NULL,
  `OrderID` int(11) NOT NULL,
  `ProductGrettingCard` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ProductID`,`OrderID`),
  KEY `fk_ProductInOrder_2_idx` (`OrderID`),
  CONSTRAINT `fk_ProductInOrder_1` FOREIGN KEY (`ProductID`) REFERENCES `Product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ProductInOrder_2` FOREIGN KEY (`OrderID`) REFERENCES `Order` (`OrderID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProductInOrder`
--

LOCK TABLES `ProductInOrder` WRITE;
/*!40000 ALTER TABLE `ProductInOrder` DISABLE KEYS */;
INSERT INTO `ProductInOrder` VALUES (2,33,'234'),(4,32,'2342'),(5,32,'423'),(5,33,'234');
/*!40000 ALTER TABLE `ProductInOrder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Store`
--

DROP TABLE IF EXISTS `Store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Store` (
  `StoreID` int(11) NOT NULL,
  `StoreAddress` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`StoreID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Store`
--

LOCK TABLES `Store` WRITE;
/*!40000 ALTER TABLE `Store` DISABLE KEYS */;
INSERT INTO `Store` VALUES (0,'Base'),(1,'Haifa'),(2,'Karmiel'),(3,'Qiryat Biyalik');
/*!40000 ALTER TABLE `Store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
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
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('admin','123456','SYSTEM_MANAGER',0,'REGULAR',0),('Ariel','123456','NETWORK_WORKER',12,'REGULAR',0),('daniel','123456','CUSTOMER_SERVICE',123,'REGULAR',0),('jenia','123456','CUSTOMER_SERVICE_EXPERT',1111,'REGULAR',0),('matan','qwerty','STORE_WORKER',1,'REGULAR',0),('matan2','123456','CUSTOMER',305022949,'REGULAR',0),('matan3','123456','CUSTOMER',1234,'LOGGED_IN',0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customersatisfactionsurveyresults`
--

DROP TABLE IF EXISTS `customersatisfactionsurveyresults`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customersatisfactionsurveyresults` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `surveyName` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `answer1` int(2) DEFAULT NULL,
  `answer2` int(2) DEFAULT NULL,
  `answer3` int(2) DEFAULT NULL,
  `answer4` int(2) DEFAULT NULL,
  `answer5` int(2) DEFAULT NULL,
  `answer6` int(2) DEFAULT NULL,
  `storeID` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customersatisfactionsurveyresults_1_idx` (`storeID`),
  CONSTRAINT `fk_customersatisfactionsurveyresults_1` FOREIGN KEY (`storeID`) REFERENCES `Store` (`StoreID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customersatisfactionsurveyresults`
--

LOCK TABLES `customersatisfactionsurveyresults` WRITE;
/*!40000 ALTER TABLE `customersatisfactionsurveyresults` DISABLE KEYS */;
INSERT INTO `customersatisfactionsurveyresults` VALUES (1,'12','2018-01-03',2,2,2,2,2,2,NULL),(2,'123','2018-01-03',5,5,5,5,5,55,NULL),(3,'new survey','2018-01-03',5,5,5,5,5,5,NULL);
/*!40000 ALTER TABLE `customersatisfactionsurveyresults` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeEmployees`
--

DROP TABLE IF EXISTS `storeEmployees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeEmployees` (
  `username` varchar(45) NOT NULL,
  `storeID` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeEmployees`
--

LOCK TABLES `storeEmployees` WRITE;
/*!40000 ALTER TABLE `storeEmployees` DISABLE KEYS */;
INSERT INTO `storeEmployees` VALUES ('matan',1,'STORE_WORKER');
/*!40000 ALTER TABLE `storeEmployees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `surveys`
--

DROP TABLE IF EXISTS `surveys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `surveys` (
  `surveyName` varchar(200) NOT NULL,
  `question1` varchar(200) DEFAULT NULL,
  `question2` varchar(200) DEFAULT NULL,
  `question3` varchar(200) DEFAULT NULL,
  `question4` varchar(200) DEFAULT NULL,
  `question5` varchar(200) DEFAULT NULL,
  `question6` varchar(200) DEFAULT NULL,
  `analysis` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`surveyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `surveys`
--

LOCK TABLES `surveys` WRITE;
/*!40000 ALTER TABLE `surveys` DISABLE KEYS */;
INSERT INTO `surveys` VALUES ('12','1','1','1','1','1','1','1'),('123','Question','Question','Question','Question','Question','Question','Bla bla'),('12355','1','1','1','1','1','1',NULL),('new survey','1','1','1','1','1','1',NULL);
/*!40000 ALTER TABLE `surveys` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-11 12:14:55
