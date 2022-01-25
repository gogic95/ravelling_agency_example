/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.22 : Database - agency
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`agency` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `agency`;

/*Table structure for table `aranzman` */

DROP TABLE IF EXISTS `aranzman`;

CREATE TABLE `aranzman` (
  `destinacijaid` int NOT NULL,
  `rbaranzmana` int NOT NULL AUTO_INCREMENT,
  `nazivsmestaja` varchar(50) DEFAULT NULL,
  `cenapokrevetu` double DEFAULT NULL,
  `brojkreveta` int DEFAULT NULL,
  `podugovorom` tinyint DEFAULT NULL,
  `komercijalistaid` int DEFAULT NULL,
  PRIMARY KEY (`destinacijaid`,`rbaranzmana`),
  KEY `rbaranzmana` (`rbaranzmana`),
  KEY `komercijalistaid` (`komercijalistaid`),
  CONSTRAINT `aranzman_ibfk_1` FOREIGN KEY (`destinacijaid`) REFERENCES `destinacija` (`destinacijaid`),
  CONSTRAINT `aranzman_ibfk_2` FOREIGN KEY (`komercijalistaid`) REFERENCES `komercijalista` (`komercijalistaid`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `aranzman` */

insert  into `aranzman`(`destinacijaid`,`rbaranzmana`,`nazivsmestaja`,`cenapokrevetu`,`brojkreveta`,`podugovorom`,`komercijalistaid`) values 
(1,1,'Lefteris',150,3,1,1),
(1,2,'Nikki',189,4,0,1),
(1,21,'Apostolos',154.55,5,0,1),
(2,5,'Spiros',134,5,0,2),
(2,7,'test',300,3,0,2),
(2,26,'TEEEST',555,5,0,2),
(4,9,'Spiros',125,3,0,1),
(4,19,'Suvlaki apartments',199,2,0,1),
(4,22,'Giros Hotel',169,5,0,1),
(5,10,'Turtle Paradise',256,4,0,1),
(6,12,'Kebab Hotel',155,3,0,1),
(7,15,'Batko smestaj',89,5,0,1),
(8,14,'Luxor Hotel',399,4,0,1),
(9,11,'Cipros',150,5,0,1),
(9,30,'Banja Koviljaca Greek Hotel',329,2,0,1),
(10,13,'Casa De Papel',550,2,0,1),
(10,17,'Palma hotel',466,3,0,1),
(17,28,'Najnoviji smestaj ikada',999,3,0,1),
(18,29,'Za najnoviju najnoviji',900,1,0,1);

/*Table structure for table `destinacija` */

DROP TABLE IF EXISTS `destinacija`;

CREATE TABLE `destinacija` (
  `destinacijaid` int NOT NULL AUTO_INCREMENT,
  `mesto` varchar(50) DEFAULT NULL,
  `opis` varchar(255) DEFAULT NULL,
  `ukupankapacitet` int DEFAULT NULL,
  `popunjenkapacitet` int DEFAULT NULL,
  `komercijalistaid` int DEFAULT NULL,
  PRIMARY KEY (`destinacijaid`),
  KEY `komercijalistaid` (`komercijalistaid`),
  CONSTRAINT `destinacija_ibfk_1` FOREIGN KEY (`komercijalistaid`) REFERENCES `komercijalista` (`komercijalistaid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `destinacija` */

insert  into `destinacija`(`destinacijaid`,`mesto`,`opis`,`ukupankapacitet`,`popunjenkapacitet`,`komercijalistaid`) values 
(1,'Lefkada','Najlepse grcko ostrvo!',150,0,1),
(2,'Zakintos','Ostrvo za studentske zurke',95,0,2),
(4,'Kavos','Svakodnevne zurke!',0,0,1),
(5,'Kefalonija','Prelepe plaze sa kornjacama!',0,0,1),
(6,'Kusadasi','Najveca party destinacija u Turskoj',0,0,1),
(7,'Suncev Breg','Povoljno letovanje u Bugarskoj',0,0,1),
(8,'Hurgada','Letovaliste u Egiptu',0,0,1),
(9,'Evia','Banja u Grckoj',0,0,1),
(10,'Majorka','Luksuzno u Spaniji',0,0,1),
(11,'Test','Test',0,0,1),
(12,'TEST','TESTETEST',0,0,2),
(13,'TEEEST','teeest',0,0,1),
(14,'tetete','tetete',0,0,1),
(15,'rest','resttt',0,0,1),
(16,'aaaaaa','aaa',0,0,1),
(17,'Nova','novi test\n',0,0,1),
(18,'Najnovija ikada','nononononovovovovovov',0,0,1);

/*Table structure for table `komercijalista` */

DROP TABLE IF EXISTS `komercijalista`;

CREATE TABLE `komercijalista` (
  `komercijalistaid` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(50) DEFAULT NULL,
  `prezime` varchar(50) DEFAULT NULL,
  `brojsklopljenihugovora` int DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`komercijalistaid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `komercijalista` */

insert  into `komercijalista`(`komercijalistaid`,`ime`,`prezime`,`brojsklopljenihugovora`,`username`,`password`) values 
(1,'Filip','Gogic',0,'aaa','aaa'),
(2,'Ana','Anic',1,'ana','ana');

/*Table structure for table `stavkaugovora` */

DROP TABLE IF EXISTS `stavkaugovora`;

CREATE TABLE `stavkaugovora` (
  `brojugovora` int NOT NULL,
  `rbstavke` int NOT NULL,
  `imeprezimeklijenta` varchar(100) DEFAULT NULL,
  `jmbg` varchar(13) DEFAULT NULL,
  `cena` double DEFAULT NULL,
  PRIMARY KEY (`brojugovora`,`rbstavke`),
  KEY `rbstavke` (`rbstavke`),
  CONSTRAINT `stavkaugovora_ibfk_1` FOREIGN KEY (`brojugovora`) REFERENCES `ugovor` (`brojugovora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stavkaugovora` */

insert  into `stavkaugovora`(`brojugovora`,`rbstavke`,`imeprezimeklijenta`,`jmbg`,`cena`) values 
(5,1,'Prvi Prvi','1234567894657',255.35),
(5,2,'Drugi Drugi','7894561324567',255.35),
(6,1,'Filip Filipovic','1234567897894',149),
(6,2,'Bla bla','1234657894659',149),
(7,1,'Aaa aaaaa','4657981324568',900),
(8,1,'aa aa','1234567894658',300),
(9,1,'aa aaaa','4567891324567',333),
(10,1,'bbb bbb','7894651324658',99999),
(11,1,'aaa aaa','1234567896541',555),
(11,2,'aaa aaaaa','7897897897981',555),
(12,1,'aaa aaa','7894561234568',222),
(12,2,'bbb bbb','7897987894564',222),
(13,1,'aa aa','4567891234568',555),
(14,1,'aaaa aaa','4564564567988',455);

/*Table structure for table `ugovor` */

DROP TABLE IF EXISTS `ugovor`;

CREATE TABLE `ugovor` (
  `brojugovora` int NOT NULL AUTO_INCREMENT,
  `brojklijenata` int DEFAULT NULL,
  `ukupnacena` double DEFAULT NULL,
  `obradjen` tinyint DEFAULT NULL,
  `storniran` tinyint DEFAULT NULL,
  `destinacijaid` int DEFAULT NULL,
  `rbaranzmana` int DEFAULT NULL,
  `komercijalistaid` int DEFAULT NULL,
  PRIMARY KEY (`brojugovora`),
  KEY `rbaranzmana` (`rbaranzmana`),
  KEY `komercijalistaid` (`komercijalistaid`),
  KEY `destinacijaid` (`destinacijaid`),
  CONSTRAINT `ugovor_ibfk_2` FOREIGN KEY (`rbaranzmana`) REFERENCES `aranzman` (`rbaranzmana`),
  CONSTRAINT `ugovor_ibfk_3` FOREIGN KEY (`komercijalistaid`) REFERENCES `komercijalista` (`komercijalistaid`),
  CONSTRAINT `ugovor_ibfk_4` FOREIGN KEY (`destinacijaid`) REFERENCES `destinacija` (`destinacijaid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ugovor` */

insert  into `ugovor`(`brojugovora`,`brojklijenata`,`ukupnacena`,`obradjen`,`storniran`,`destinacijaid`,`rbaranzmana`,`komercijalistaid`) values 
(5,2,510.7,1,1,10,13,1),
(6,2,298,1,0,1,1,1),
(7,1,900,1,1,18,29,1),
(8,1,300,1,1,17,28,1),
(9,1,333,1,1,17,28,1),
(10,1,99999,1,1,17,28,1),
(11,2,1110,1,1,1,2,1),
(12,2,444,1,1,4,19,1),
(13,1,555,1,1,4,22,1),
(14,1,455,1,1,4,19,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
