-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: bookthevibedb
-- ------------------------------------------------------
-- Server version	8.4.9

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
-- Table structure for table `attivita`
--

DROP TABLE IF EXISTS `attivita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attivita` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titolo` varchar(100) NOT NULL,
  `provider` varchar(100) NOT NULL,
  `descrizione` text,
  `categoria` varchar(50) NOT NULL,
  `durata` varchar(50) DEFAULT NULL,
  `capacita_massima` int NOT NULL,
  `stato` tinyint(1) NOT NULL DEFAULT '1',
  `citta` varchar(50) NOT NULL,
  `prezzo_unitario` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `codice_sconto`
--

DROP TABLE IF EXISTS `codice_sconto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `codice_sconto` (
  `code_id` varchar(20) NOT NULL,
  `percentuale` decimal(5,2) NOT NULL,
  `stato` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disponibilita`
--

DROP TABLE IF EXISTS `disponibilita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disponibilita` (
  `attivita_id` int NOT NULL,
  `data_evento` date NOT NULL,
  `posti_rimanenti` int NOT NULL,
  PRIMARY KEY (`attivita_id`,`data_evento`),
  CONSTRAINT `disponibilita_ibfk_1` FOREIGN KEY (`attivita_id`) REFERENCES `attivita` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `immagine`
--

DROP TABLE IF EXISTS `immagine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `immagine` (
  `id_img` int NOT NULL AUTO_INCREMENT,
  `attivita_id` int NOT NULL,
  `formato` varchar(50) NOT NULL,
  `dati_immagine` longblob NOT NULL,
  `testo_alternativo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_img`),
  KEY `attivita_id` (`attivita_id`),
  CONSTRAINT `immagine_ibfk_1` FOREIGN KEY (`attivita_id`) REFERENCES `attivita` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `prenotazione`
--

DROP TABLE IF EXISTS `prenotazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prenotazione` (
  `id` int NOT NULL AUTO_INCREMENT,
  `utente_id` int NOT NULL,
  `codice_sconto_id` varchar(20) DEFAULT NULL,
  `attivita_id` int NOT NULL,
  `data_evento` date NOT NULL,
  `data_prenotazione` date NOT NULL,
  `prezzo_tot` decimal(10,2) NOT NULL,
  `stato_pagamento` varchar(50) NOT NULL,
  `num_prenotati` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `utente_id` (`utente_id`),
  KEY `codice_sconto_id` (`codice_sconto_id`),
  KEY `attivita_id` (`attivita_id`,`data_evento`),
  CONSTRAINT `prenotazione_ibfk_1` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`) ON DELETE CASCADE,
  CONSTRAINT `prenotazione_ibfk_2` FOREIGN KEY (`codice_sconto_id`) REFERENCES `codice_sconto` (`code_id`) ON DELETE SET NULL,
  CONSTRAINT `prenotazione_ibfk_3` FOREIGN KEY (`attivita_id`, `data_evento`) REFERENCES `disponibilita` (`attivita_id`, `data_evento`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recensione`
--

DROP TABLE IF EXISTS `recensione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recensione` (
  `attivita_id` int NOT NULL,
  `utente_id` int NOT NULL,
  `punteggio` int NOT NULL,
  `testo` text,
  `data_recensione` date NOT NULL,
  PRIMARY KEY (`attivita_id`,`utente_id`),
  KEY `utente_id` (`utente_id`),
  CONSTRAINT `recensione_ibfk_1` FOREIGN KEY (`attivita_id`) REFERENCES `attivita` (`id`) ON DELETE CASCADE,
  CONSTRAINT `recensione_ibfk_2` FOREIGN KEY (`utente_id`) REFERENCES `utente` (`id`) ON DELETE CASCADE,
  CONSTRAINT `recensione_chk_1` CHECK (((`punteggio` >= 1) and (`punteggio` <= 5)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `ruolo` varchar(20) NOT NULL DEFAULT 'USER',
  `password` varchar(255) NOT NULL,
  `data_nascita` date DEFAULT NULL,
  `cellulare` varchar(20) DEFAULT NULL,
  `via` varchar(100) DEFAULT NULL,
  `civico` varchar(10) DEFAULT NULL,
  `cap` varchar(10) DEFAULT NULL,
  `nazione` varchar(50) DEFAULT NULL,
  `citta` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-21 15:06:18
