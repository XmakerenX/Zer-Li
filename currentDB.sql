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
INSERT INTO `CatalogProduct` VALUES (4,100,'panter.jpg',0),(5,-1,'buttercup.jpg',0),(24,-1,'sunshine.jpg',0);
/*!40000 ALTER TABLE `CatalogProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ComplaintReport`
--

DROP TABLE IF EXISTS `ComplaintReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ComplaintReport` (
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
-- Dumping data for table `ComplaintReport`
--

LOCK TABLES `ComplaintReport` WRITE;
/*!40000 ALTER TABLE `ComplaintReport` DISABLE KEYS */;
INSERT INTO `ComplaintReport` VALUES ('FIRST','2017',2,13,14,15,16,17,18),('THIRD','2017',2,20,10,51,46,23,31);
/*!40000 ALTER TABLE `ComplaintReport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CustomItem`
--

DROP TABLE IF EXISTS `CustomItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CustomItem` (
  `CustomItemID` int(11) NOT NULL AUTO_INCREMENT,
  `CustomItemType` varchar(45) DEFAULT NULL,
  `CustomItemPrice` float DEFAULT NULL,
  `CustomItemColor` varchar(45) DEFAULT NULL,
  `CustomItemGreetingCard` varchar(100) DEFAULT NULL,
  `CustomItemOrderID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CustomItemID`),
  KEY `fk_CustomItem_1_idx` (`CustomItemOrderID`),
  CONSTRAINT `fk_CustomItem_1` FOREIGN KEY (`CustomItemOrderID`) REFERENCES `Order` (`OrderID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CustomItem`
--

LOCK TABLES `CustomItem` WRITE;
/*!40000 ALTER TABLE `CustomItem` DISABLE KEYS */;
/*!40000 ALTER TABLE `CustomItem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CustomItemProduct`
--

DROP TABLE IF EXISTS `CustomItemProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CustomItemProduct` (
  `CustomItemID` int(11) NOT NULL AUTO_INCREMENT,
  `ProductID` int(11) NOT NULL,
  `Amount` int(11) NOT NULL,
  `Price` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CustomItemID`,`ProductID`),
  KEY `fk_CustomItemProduct_2_idx` (`CustomItemID`),
  KEY `fk_CustomItemProduct_2_idx1` (`ProductID`),
  CONSTRAINT `fk_CustomItemProduct_1` FOREIGN KEY (`CustomItemID`) REFERENCES `CustomItem` (`CustomItemID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_CustomItemProduct_2` FOREIGN KEY (`ProductID`) REFERENCES `Product` (`ProductID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CustomItemProduct`
--

LOCK TABLES `CustomItemProduct` WRITE;
/*!40000 ALTER TABLE `CustomItemProduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `CustomItemProduct` ENABLE KEYS */;
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
  `ExpirationDate` varchar(45) DEFAULT NULL,
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
  CONSTRAINT `fk_Order_1` FOREIGN KEY (`OrderCustomerID`) REFERENCES `Customers` (`personID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order`
--

LOCK TABLES `Order` WRITE;
/*!40000 ALTER TABLE `Order` DISABLE KEYS */;
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
INSERT INTO `Product` VALUES (1,'roses bouquet','bouquet',90,15,'Red'),(2,'Chrysanthemums bouquet','bouquet',20,20,'White'),(3,'Avalanche bouquet','bouquet',15,10,'Purple'),(4,'Panter bouquet','bouquet',151,5,'Pink'),(5,'Buttercup bouquet','bouquet',94,10,'Red'),(6,'Anemone bouquet','bouquet',123,15,'Red'),(7,'Chrysanthemums','FLOWER',9,30,'White'),(8,'Limonium','FLOWER',10,30,'Purple'),(9,'Sunflower','FLOWER',11,30,'Yellow'),(10,'roses','FLOWER',12,40,'Red'),(11,'Small Gozmania','PLANT',33,20,'Yellow'),(12,'Medium Gozmania','PLANT',69,30,'Yellow'),(13,'Big Gozmania','PLANT',99,25,'Yellow'),(14,'Small Anthurium','PLANT',29,15,'Red'),(15,'Medium Anthurium','PLANT',79,20,'Red'),(16,'Big Anthurium','PLANT',109,30,'Red'),(17,'Small Cocktail','PLANT',19,30,'Pink'),(18,'Medium Cocktail','PLANT',49,20,'Pink'),(19,'Big Cocktail','PLANT',89,10,'Pink'),(20,'SnowWhite','Bridal Bouquet',149,20,'White'),(21,'Cinderella','Bridal Bouquet',159,20,'White'),(22,'Olive','Flower Pot',89,20,'Green'),(23,'Spathiphyllum','Flower Pot',199,20,'Green'),(24,'sunshine','Flowers Cluster',89,20,'Yellow'),(25,'Violet Winter','Flowers Cluster',129,20,'Purple');
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
  `ProductGrettingCard` varchar(100) DEFAULT NULL,
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
INSERT INTO `User` VALUES ('admin','123456','SYSTEM_MANAGER',98,'REGULAR',0),('customer1','123456','CUSTOMER',101,'REGULAR',0),('customer2','123456','CUSTOMER',102,'REGULAR',0),('customerService','123456','CUSTOMER_SERVICE',105,'REGULAR',0),('networkManager','123456','NETWORK_MANAGER',103,'REGULAR',0),('networkWorker','123456','NETWORK_WORKER',100,'REGULAR',0),('serviceExpert','123456','CUSTOMER_SERVICE_EXPERT',109,'REGULAR',0),('serviceWorker1','123456','CUSTOMER_SERVICE_WORKER',106,'REGULAR',0),('serviceWorker2','123456','CUSTOMER_SERVICE_WORKER',107,'REGULAR',0),('storeManager','123456','STORE_MANAGER',99,'REGULAR',0),('storeWorker','123456','STORE_WORKER',104,'REGULAR',0),('systemManager','123456','SYSTEM_MANAGER',108,'REGULAR',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customersatisfactionsurveyresults`
--

LOCK TABLES `customersatisfactionsurveyresults` WRITE;
/*!40000 ALTER TABLE `customersatisfactionsurveyresults` DISABLE KEYS */;
/*!40000 ALTER TABLE `customersatisfactionsurveyresults` ENABLE KEYS */;
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
INSERT INTO `storeEmployees` VALUES ('storeManager',2,'STORE_MANAGER'),('storeWorker',1,'STORE_WORKER');
/*!40000 ALTER TABLE `storeEmployees` ENABLE KEYS */;
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

-- Dump completed on 2018-01-27 22:31:04
