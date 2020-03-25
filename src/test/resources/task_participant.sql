-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: escape
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

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
-- Table structure for table `task_participant`
--

DROP TABLE IF EXISTS `task_participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_participant` (
  `task_id` bigint(20) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `easy` int(11) NOT NULL,
  `hard` int(11) NOT NULL,
  `medium` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`task_id`,`user_id`),
  KEY `FKmk8tg15sktpgx70tcq6taqeuc` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_participant`
--

LOCK TABLES `task_participant` WRITE;
/*!40000 ALTER TABLE `task_participant` DISABLE KEYS */;
INSERT INTO `task_participant` VALUES (2,'gspark',0,1,0,5),(2,'blue44rain',1,0,2,5),(2,'nokchax',2,1,0,7),(2,'minwoo33park',0,0,0,0),(2,'ryan22tube',0,1,0,5),(2,'kjs4395',0,1,0,5),(2,'seungkwon',3,0,1,5),(2,'integerous',5,0,0,5),(2,'gmlwjd9405',1,0,2,5),(2,'dlwogns0128',1,0,2,5),(3,'blue44rain',1,0,2,5),(3,'nokchax',0,1,0,5),(3,'minwoo33park',1,1,2,10),(3,'ryan22tube',0,1,0,5),(3,'kjs4395',0,1,0,5),(3,'seungkwon',3,0,1,5),(3,'integerous',5,0,0,5),(3,'gmlwjd9405',2,0,2,6),(3,'gspark',1,1,0,6),(3,'dlwogns0128',0,0,0,0),(3,'wonoh',4,0,1,6),(4,'blue44rain',0,1,0,5),(4,'nokchax',0,1,0,5),(4,'minwoo33park',2,0,4,10),(4,'ryan22tube',0,1,0,5),(4,'kjs4395',0,1,0,5),(4,'seungkwon',0,1,0,5),(4,'integerous',3,0,1,5),(4,'gmlwjd9405',0,1,0,5),(4,'gspark',0,1,0,5),(4,'wonoh',1,0,3,7),(4,'dlwogns0128',1,0,2,5),(5,'blue44rain',0,1,0,5),(5,'nokchax',0,1,0,5),(5,'minwoo33park',4,1,6,21),(5,'ryan22tube',0,1,0,5),(5,'kjs4395',0,1,0,5),(5,'seungkwon',0,1,0,5),(5,'integerous',3,0,1,5),(5,'gmlwjd9405',1,0,2,5),(5,'gspark',0,1,0,5),(5,'wonoh',1,0,3,7),(5,'dlwogns0128',0,1,0,5),(6,'blue44rain',1,0,2,5),(6,'nokchax',0,1,0,5),(6,'minwoo33park',2,2,4,20),(6,'ryan22tube',0,1,0,5),(6,'kjs4395',0,1,0,5),(6,'seungkwon',0,1,0,5),(6,'integerous',1,0,1,3),(6,'gmlwjd9405',0,1,0,5),(6,'gspark',0,1,1,5),(6,'wonoh',0,1,0,5),(6,'dlwogns0128',2,1,0,7),(7,'blue44rain',0,1,0,5),(7,'nokchax',0,1,0,5),(7,'minwoo33park',1,1,2,10),(7,'ryan22tube',0,1,0,5),(7,'kjs4395',0,1,0,5),(7,'seungkwon',5,0,0,5),(7,'integerous',1,0,2,5),(7,'gmlwjd9405',0,0,2,4),(7,'gspark',2,0,2,6),(7,'wonoh',5,0,0,5),(7,'dlwogns0128',0,1,0,5),(8,'blue44rain',1,0,2,5),(8,'nokchax',0,1,0,5),(8,'minwoo33park',2,0,4,10),(8,'ryan22tube',0,1,0,5),(8,'kjs4395',0,1,0,5),(8,'seungkwon',3,0,1,5),(8,'integerous',0,1,0,5),(8,'gmlwjd9405',1,1,0,6),(8,'gspark',1,0,2,5),(8,'wonoh',1,0,2,5),(8,'dlwogns0128',1,0,2,5),(9,'blue44rain',1,0,2,5),(9,'nokchax',1,1,0,6),(9,'minwoo33park',1,0,2,5),(9,'ryan22tube',0,1,0,5),(9,'kjs4395',0,1,0,5),(9,'seungkwon',0,1,0,5),(9,'integerous',0,1,0,5),(9,'gmlwjd9405',1,0,2,5),(9,'gspark',1,0,2,5),(9,'wonoh',1,0,2,5),(9,'dlwogns0128',1,0,2,5),(10,'blue44rain',1,0,2,5),(10,'nokchax',0,1,0,5),(10,'minwoo33park',1,1,0,6),(10,'ryan22tube',0,1,0,5),(10,'kjs4395',0,1,0,5),(10,'seungkwon',0,1,0,5),(10,'integerous',0,0,0,0),(10,'gmlwjd9405',0,1,0,5),(10,'gspark',0,1,0,5),(10,'wonoh',3,0,1,5),(10,'dlwogns0128',1,0,2,5),(11,'blue44rain',1,0,2,5),(11,'nokchax',0,1,0,5),(11,'minwoo33park',1,0,1,3),(11,'ryan22tube',0,1,0,5),(11,'kjs4395',0,1,0,5),(11,'seungkwon',0,0,0,0),(11,'integerous',1,0,2,5),(11,'gmlwjd9405',0,1,0,5),(11,'gspark',1,0,2,5),(11,'wonoh',1,0,2,5),(11,'dlwogns0128',1,0,2,5),(11,'sagsn0202',0,1,0,5),(12,'blue44rain',1,0,2,5),(12,'nokchax',0,1,0,5),(12,'minwoo33park',1,1,2,10),(12,'ryan22tube',0,1,0,5),(12,'kjs4395',0,1,0,5),(12,'seungkwon',0,1,0,5),(12,'integerous',0,1,0,5),(12,'gmlwjd9405',3,0,1,5),(12,'gspark',0,1,0,5),(12,'wonoh',1,0,2,5),(12,'sagsn0202',0,1,0,5),(12,'dlwogns0128',1,0,2,5),(13,'blue44rain',1,0,2,5),(13,'nokchax',0,1,0,5),(13,'minwoo33park',0,0,3,6),(13,'ryan22tube',0,1,0,5),(13,'kjs4395',0,1,0,5),(13,'seungkwon',0,0,0,0),(13,'integerous',0,0,0,0),(13,'gmlwjd9405',0,1,0,5),(13,'gspark',0,0,0,0),(13,'sagsn0202',0,1,0,5),(13,'dlwogns0128',1,0,2,5),(13,'songjein',0,0,3,6),(13,'rachelkim',1,0,2,5),(14,'blue44rain',1,0,2,5),(14,'nokchax',0,1,0,5),(14,'minwoo33park',0,0,1,2),(14,'ryan22tube',0,0,0,0),(14,'kjs4395',0,1,0,5),(14,'seungkwon',0,1,0,5),(14,'integerous',0,1,0,5),(14,'gmlwjd9405',0,1,0,5),(14,'gspark',0,1,0,5),(14,'songjein',1,0,2,5),(14,'sagsn0202',0,1,0,5),(14,'rachelkim',0,1,0,5),(14,'dlwogns0128',0,0,0,0),(15,'blue44rain',1,0,2,5),(15,'nokchax',0,1,0,5),(15,'minwoo33park',0,0,0,0),(15,'ryan22tube',0,1,0,5),(15,'kjs4395',0,0,0,0),(15,'seungkwon',0,1,0,5),(15,'integerous',0,0,0,0),(15,'gmlwjd9405',0,0,0,0),(15,'gspark',0,0,0,0),(15,'songjein',0,0,4,8),(15,'sagsn0202',0,1,0,5),(15,'rachelkim',0,1,0,5),(15,'dlwogns0128',1,0,2,5),(16,'blue44rain',1,0,2,5),(16,'nokchax',0,1,0,5),(16,'minwoo33park',0,1,0,5),(16,'ryan22tube',1,0,2,5),(16,'kjs4395',0,1,0,5),(16,'seungkwon',4,0,1,6),(16,'integerous',0,0,0,0),(16,'gmlwjd9405',0,1,0,5),(16,'gspark',3,0,2,7),(16,'songjein',0,0,4,8),(16,'sagsn0202',0,1,0,5),(16,'rachelkim',1,0,2,5),(16,'dlwogns0128',1,0,2,5),(17,'blue44rain',1,0,2,5),(17,'nokchax',0,1,0,5),(17,'minwoo33park',0,2,1,12),(17,'ryan22tube',0,0,0,0),(17,'kjs4395',0,0,0,0),(17,'seungkwon',0,1,0,5),(17,'integerous',3,0,1,5),(17,'gmlwjd9405',1,0,2,5),(17,'gspark',3,0,0,3),(17,'songjein',0,0,3,6),(17,'sagsn0202',0,1,0,5),(17,'rachelkim',1,0,2,5),(17,'pakseon25',6,0,1,8),(17,'dlwogns0128',0,1,0,5),(17,'hyunkyung',1,0,2,5),(18,'blue44rain',1,0,2,5),(18,'nokchax',0,1,0,5),(18,'minwoo33park',1,0,3,7),(18,'ryan22tube',0,1,0,5),(18,'kjs4395',0,1,0,5),(18,'seungkwon',0,1,0,5),(18,'integerous',1,1,0,6),(18,'gmlwjd9405',0,1,0,5),(18,'gspark',0,1,0,5),(18,'songjein',0,0,3,6),(18,'sagsn0202',0,1,0,5),(18,'rachelkim',3,0,1,5),(18,'pakseon25',4,0,1,6),(18,'hyunkyung',0,1,0,5),(18,'dlwogns0128',1,0,2,5),(19,'blue44rain',1,0,2,5),(19,'nokchax',0,1,0,5),(19,'minwoo33park',0,1,0,5),(19,'ryan22tube',0,1,0,5),(19,'kjs4395',0,1,0,5),(19,'seungkwon',0,1,0,5),(19,'integerous',3,0,1,5),(19,'gmlwjd9405',0,0,0,0),(19,'gspark',4,0,1,6),(19,'songjein',0,1,2,9),(19,'rachelkim',0,1,0,5),(19,'pakseon25',0,1,0,5),(19,'hyunkyung',1,0,2,5),(19,'dlwogns0128',1,0,2,5),(20,'blue44rain',1,0,2,5),(20,'nokchax',0,1,0,5),(20,'minwoo33park',1,0,3,7),(20,'ryan22tube',0,1,0,5),(20,'kjs4395',0,1,0,5),(20,'seungkwon',0,0,0,0),(20,'integerous',1,1,0,6),(20,'gmlwjd9405',1,0,2,5),(20,'gspark',3,0,1,5),(20,'songjein',0,0,3,6),(20,'rachelkim',0,1,0,5),(20,'pakseon25',0,0,0,0),(20,'hyunkyung',1,0,2,5),(20,'dlwogns0128',1,0,2,5),(21,'blue44rain',1,0,1,3),(21,'nokchax',0,1,0,5),(21,'minwoo33park',1,3,2,20),(21,'ryan22tube',0,1,0,5),(21,'kjs4395',0,1,0,5),(21,'seungkwon',0,1,0,5),(21,'integerous',0,1,0,5),(21,'gmlwjd9405',1,0,2,5),(21,'gspark',2,0,0,2),(21,'songjein',0,0,0,0),(21,'rachelkim',0,1,0,5),(21,'pakseon25',1,0,2,5),(21,'hyunkyung',2,0,0,2),(21,'dlwogns0128',0,0,0,0),(22,'blue44rain',2,0,1,4),(22,'nokchax',0,1,0,5),(22,'minwoo33park',1,1,3,12),(22,'ryan22tube',0,1,0,5),(22,'kjs4395',1,0,2,5),(22,'seungkwon',0,1,0,5),(22,'integerous',0,1,0,5),(22,'gmlwjd9405',0,0,0,0),(22,'gspark',4,0,1,6),(22,'songjein',0,1,2,9),(22,'rachelkim',1,0,2,5),(22,'pakseon25',0,1,0,5),(22,'hyunkyung',0,0,3,6),(22,'dlwogns0128',2,0,1,4),(23,'blue44rain',2,0,2,6),(23,'nokchax',0,1,0,5),(23,'minwoo33park',1,0,2,5),(23,'ryan22tube',0,1,0,5),(23,'kjs4395',0,1,0,5),(23,'seungkwon',0,0,0,0),(23,'integerous',2,0,0,2),(23,'gmlwjd9405',1,0,2,5),(23,'gspark',0,1,0,5),(23,'songjein',0,0,3,6),(23,'rachelkim',0,1,0,5),(23,'pakseon25',0,0,4,8),(23,'hyunkyung',1,0,2,5),(23,'dlwogns0128',2,0,2,6),(24,'blue44rain',1,0,2,5),(24,'nokchax',0,1,0,5),(24,'minwoo33park',1,0,2,5),(24,'ryan22tube',1,0,2,5),(24,'kjs4395',1,0,2,5),(24,'seungkwon',0,1,0,5),(24,'integerous',3,0,1,5),(24,'gmlwjd9405',0,1,0,5),(24,'gspark',1,0,1,3),(24,'songjein',0,0,2,4),(24,'rachelkim',1,0,2,5),(24,'pakseon25',0,0,3,6),(24,'hyunkyung',1,0,2,5),(24,'dlwogns0128',1,0,2,5),(25,'blue44rain',1,0,2,5),(25,'nokchax',0,1,0,5),(25,'minwoo33park',0,1,0,5),(25,'ryan22tube',1,0,2,5),(25,'kjs4395',1,0,2,5),(25,'seungkwon',5,0,2,9),(25,'integerous',0,1,0,5),(25,'gmlwjd9405',0,1,0,5),(25,'gspark',3,0,1,5),(25,'songjein',0,1,0,5),(25,'rachelkim',3,0,1,5),(25,'pakseon25',5,0,0,5),(25,'hyunkyung',3,0,1,5),(25,'dlwogns0128',1,0,2,5),(26,'blue44rain',1,0,2,5),(26,'nokchax',0,1,0,5),(26,'minwoo33park',0,1,0,5),(26,'ryan22tube',1,0,2,5),(26,'kjs4395',1,0,2,5),(26,'seungkwon',1,0,2,5),(26,'integerous',0,1,0,5),(26,'gmlwjd9405',0,0,0,0),(26,'gspark',0,0,0,0),(26,'songjein',0,0,0,0),(26,'rachelkim',0,1,0,5),(26,'pakseon25',6,0,0,6),(26,'hyunkyung',0,1,0,5),(26,'dlwogns0128',0,1,0,5),(27,'blue44rain',1,0,2,5),(27,'nokchax',0,1,0,5),(27,'minwoo33park',1,0,2,5),(27,'ryan22tube',1,0,2,5),(27,'kjs4395',1,0,1,3),(27,'seungkwon',0,1,0,5),(27,'integerous',0,1,0,5),(27,'gmlwjd9405',1,0,2,5),(27,'gspark',5,0,0,5),(27,'songjein',0,0,3,6),(27,'rachelkim',1,0,2,5),(27,'pakseon25',5,0,0,5),(27,'hyunkyung',0,1,0,5),(27,'dlwogns0128',0,0,0,0),(28,'blue44rain',1,0,2,5),(28,'nokchax',0,1,0,5),(28,'minwoo33park',0,1,0,5),(28,'ryan22tube',0,0,0,0),(28,'kjs4395',1,0,2,5),(28,'seungkwon',0,1,0,5),(28,'integerous',0,1,0,5),(28,'gmlwjd9405',0,1,0,5),(28,'gspark',1,0,2,5),(28,'songjein',0,0,3,6),(28,'rachelkim',0,1,0,5),(28,'pakseon25',5,0,0,5),(28,'hyunkyung',1,0,2,5),(28,'dlwogns0128',1,0,2,5),(29,'blue44rain',0,0,0,0),(29,'nokchax',0,1,0,5),(29,'minwoo33park',1,0,4,9),(29,'ryan22tube',0,0,2,4),(29,'kjs4395',0,0,0,0),(29,'seungkwon',0,0,0,0),(29,'integerous',0,0,0,0),(29,'gmlwjd9405',0,0,0,0),(29,'gspark',0,0,0,0),(29,'songjein',0,0,3,6),(29,'rachelkim',1,0,2,5),(29,'pakseon25',0,0,0,0),(29,'hyunkyung',0,0,0,0),(29,'dlwogns0128',0,0,0,0);
/*!40000 ALTER TABLE `task_participant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-25 19:24:38
