-- MySQL dump 10.13  Distrib 5.5.61, for osx10.11 (x86_64)
--
-- Host: localhost    Database: twitter
-- ------------------------------------------------------
-- Server version	5.5.61

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
-- Table structure for table `followers`
--

DROP TABLE IF EXISTS `followers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `followers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `follower_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_id` (`user_id`,`follower_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followers`
--

LOCK TABLES `followers` WRITE;
/*!40000 ALTER TABLE `followers` DISABLE KEYS */;
INSERT INTO `followers` VALUES (15,'10','2'),(16,'13','2'),(8,'13','7'),(17,'14','2'),(9,'14','7'),(18,'15','2'),(6,'15','7'),(10,'5','2'),(11,'6','2'),(12,'7','2'),(19,'7','3'),(24,'7','8'),(22,'7','9'),(13,'8','2'),(20,'8','3'),(1,'8','7'),(23,'8','9'),(29,'9','10'),(14,'9','2'),(21,'9','3'),(30,'9','7'),(28,'9','8');
/*!40000 ALTER TABLE `followers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `messages` varchar(255) NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `time_created` int(13) NOT NULL,
  `replied_to` varchar(45) DEFAULT NULL,
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (5,'It varies from moment to moment','8',1536128212,NULL),(6,'He\'s fine! We drank, we fought - he made his ancestors proud!','7',1536128237,NULL),(7,'Loki, this is madness!','7',1536128250,NULL),(8,'Look at you. The mighty Thor! With all your strength! And what good does it do you now? Do you hear me, brother? There\'s nothing you can do!','8',1536128267,NULL),(9,'Loki, he\'s alive! Can you believe it? He\'s up there. Hey Loki! Look who it is!','7',1536128463,NULL),(10,'You\'re just using me to get to the Hulk. That\'s low. You\'re not my friend.','10',1536128556,NULL),(11,'Your ancestors called it magic and you call it science. Well, I come from a place where they’re one in the same thing.','7',1536128821,NULL),(12,'Finally, someone who speaks English.Finally, someone who speaks English.','15',1536128887,NULL),(13,'It\'s good to meet you, Dr. Banner. Your work on anti-electron collisions is unparalleled. And I\'m a huge fan of the way you lose control and turn into an enormous green rage monster.','15',1536128917,NULL),(14,'I don’t want to fight your sister. That’s a family issue.','10',1536128976,NULL),(15,'Hit her with a lightning blast.','8',1536194226,'0'),(16,' I just hit her with the biggest lightning blast in the history of lightning. It did nothing','7',1536194477,'15'),(17,'It won\'t end there. The longer Hela\'s on Asgard, the more powerful she grows. She\'ll hunt us down. We need to stop her here and now.','7',1536195062,'15'),(18,'I\'m not doing \'get help\'.','8',1536195101,'15'),(19,'I\'m Loki','8',1536196443,'0');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Robert','San Mateo'),(3,'Kristen','Santa Clara'),(4,'Kristy','Santa Clara'),(5,'Volcan','New York'),(6,'Starlord','New Jersey'),(7,'Thor','New York'),(8,'Loki','New York'),(9,'Hela','New York'),(10,'Bruce','New York'),(11,'Peter Pan','New York'),(12,'Captain Marvel','New York'),(13,'Captain America','New York'),(14,'Steven','Nepal'),(15,'Tony','New York'),(16,'Katnis','New Hampshire'),(17,'Peeta','New Hampshire'),(18,'Netasha','New York'),(19,'Jarvis','New York'),(20,'Nebula','New York'),(21,'Gamora','New York');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-06 23:17:55
