CREATE SCHEMA  IF NOT EXISTS `ved` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ved`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: ved
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_wugryet8mf6oi28n00x2eoc4` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `sutra`
--

DROP TABLE IF EXISTS `sutra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sutra` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chapter_name` varchar(255) DEFAULT NULL,
  `chapter_no` int(11) NOT NULL,
  `content` longtext NOT NULL,
  `lang_code` varchar(255) NOT NULL,
  `language` varchar(255) NOT NULL,
  `verse_no` int(11) NOT NULL,
  `book_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKem4eoslqnos07siqwvjl03qyp` (`book_id`),
  CONSTRAINT `FKem4eoslqnos07siqwvjl03qyp` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7712 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sutra`
--

--
-- Table structure for table `commentary`
--

DROP TABLE IF EXISTS `commentary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `commentary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `commentator` varchar(255) DEFAULT NULL,
  `content` longtext,
  `language` varchar(255) DEFAULT NULL,
  `sutra_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrphltddpbekidungp6j0mcnst` (`sutra_id`),
  CONSTRAINT `FKrphltddpbekidungp6j0mcnst` FOREIGN KEY (`sutra_id`) REFERENCES `sutra` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134010 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `commentary`
--