-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3308
-- Généré le :  Dim 14 mai 2023 à 20:22
-- Version du serveur :  8.0.18
-- Version de PHP :  7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `foodapp`
--

-- --------------------------------------------------------

--
-- Structure de la table `cammande`
--

DROP TABLE IF EXISTS `cammande`;
CREATE TABLE IF NOT EXISTS `cammande` (
  `idCmd` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `idfood` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `Specialinst` varchar(255) NOT NULL,
  PRIMARY KEY (`idCmd`),
  UNIQUE KEY `idCmd` (`idCmd`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `food`
--

DROP TABLE IF EXISTS `food`;
CREATE TABLE IF NOT EXISTS `food` (
  `FoodId` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `prixUnitaire` double NOT NULL,
  `image` varchar(255) NOT NULL,
  `restauId` int(11) NOT NULL,
  `rate` int(11) NOT NULL,
  `review` int(11) NOT NULL,
  PRIMARY KEY (`FoodId`),
  UNIQUE KEY `FoodId` (`FoodId`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `food`
--

INSERT INTO `food` (`FoodId`, `Name`, `Description`, `prixUnitaire`, `image`, `restauId`, `rate`, `review`) VALUES
(1, 'Pizza', 'Pizza delicious', 30, 'https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcQcHbxCjB7FY6Rttw1VZFdh0gIZmm4MLLjfmD0dhA11saxBKG_D49VVkmlvz3sE71-b', 5, 4, 5),
(2, 'Frite Omelette', 'Frite Omelette delicious', 120, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUVFBcVFRUYGBcYGRocGhoaGh0dGh0gIRogHBwcGRoaICwjICAoHRwcJDUkKC0vMjIyGiI4PTgxPCwxMi8BCwsLDw4PHRERHTEoIykxMzExNDozMTE0MTExMTExMTExMTExMTEzMTExMTExMTExMTExMTExMTExMTExMTExMf/AABEIANAA8gMBIgACEQEDEQH/', 2, 4, 5),
(3, 'Rechta', 'Rechta delicious', 500, 'https://upload.wikimedia.org/wikipedia/commons/d/d4/Rechtaalgeroise.jpg', 6, 3, 8);

-- --------------------------------------------------------

--
-- Structure de la table `restau`
--

DROP TABLE IF EXISTS `restau`;
CREATE TABLE IF NOT EXISTS `restau` (
  `RestauId` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  `image` longtext NOT NULL,
  `MapAddress` varchar(100) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `category` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Numphone` varchar(50) NOT NULL,
  `fblink` varchar(50) NOT NULL,
  `instalink` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `stars` int(11) NOT NULL,
  `viewers` int(11) NOT NULL,
  PRIMARY KEY (`RestauId`),
  UNIQUE KEY `RestauId` (`RestauId`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `restau`
--

INSERT INTO `restau` (`RestauId`, `Name`, `image`, `MapAddress`, `Address`, `category`, `Email`, `Numphone`, `fblink`, `instalink`, `stars`, `viewers`) VALUES
(1, 'Délice', 'https://img.freepik.com/vecteurs-libre/conception-du-menu-cru-restaurant-gratuit_23-2147491098.jpg?w=740&t=st=1684024844~exp=1684025444~hmac=836238c4270737de404f1b65e39c9219728bb9824863ef58a3a9211c21c78152', 'https://www.google.com/maps/place/Restaurant+d%C3%A9lice/data=!4m7!3m6!1s0x128fb32d0e6ac6d3:0x138235', 'El Biar', 'Fast Food', 'js_laouadi@esi.dz', '0542 55 65 24', 'https://www.facebook.com/bouhaik35', 'https://www.instagram.com/restaurant_delice35/?igs', 4, 8),
(2, 'La salsa', 'https://img.freepik.com/vecteurs-libre/conception-du-menu-cru-restaurant-gratuit_23-2147491098.jpg?w=740&t=st=1684024844~exp=1684025444~hmac=836238c4270737de404f1b65e39c9219728bb9824863ef58a3a9211c21c78152', 'https://www.google.com/maps/place/Restaurant+la+salsa/@36.7379178,3.0317486,17z/data=!3m1!4b1!4m6!3m', 'Bir Mourad Raïs', 'Fast Food', 'js_laouadi@esi.dz', ' 0552 18 24 48', 'https://www.facebook.com/bouhaik35', 'https://www.instagram.com/restaurant_delice35/?igs', 4, 104),
(3, 'ChikChik', 'https://img.freepik.com/vecteurs-libre/collection-logo-restaurant-retro-dore_23-2148388943.jpg?w=740&t=st=1684024872~exp=1684025472~hmac=09e85ccb0a667a3fe2c71cf57966cca1909d3fcfb8aa88121cdca50fd21c2173', 'https://www.google.com/maps/place/Restaurant+d%C3%A9lice/data=!4m7!3m6!1s0x128fb32d0e6ac6d3:0x138235', 'El Biar', 'Fast Food', 'js_laouadi@esi.dz', '0542 55 65 24', 'https://www.facebook.com/bouhaik35', 'https://www.instagram.com/restaurant_delice35/?igs', 4, 50),
(4, 'BigBen', 'https://img.freepik.com/vecteurs-libre/collection-logo-restaurant-retro-dore_23-2148388943.jpg?w=740&t=st=1684024872~exp=1684025472~hmac=09e85ccb0a667a3fe2c71cf57966cca1909d3fcfb8aa88121cdca50fd21c2173', 'https://www.google.com/maps/place/Restaurant+la+salsa/@36.7379178,3.0317486,17z/data=!3m1!4b1!4m6!3m', 'Bir Mourad Raïs', 'Fast Food', 'js_laouadi@esi.dz', ' 0552 18 24 48', 'https://www.facebook.com/bouhaik35', 'https://www.instagram.com/restaurant_delice35/?igs', 3, 74),
(5, 'Pizza Carre ', 'https://img.freepik.com/vecteurs-libre/creation-logo-nourriture-savoureuse-restaurant_460848-10307.jpg?w=740&t=st=1684024672~exp=1684025272~hmac=72803e1ff65460d5c736984d68e803a1ffdb696272c91c3915daa2187abb97f6', 'https://www.google.com/maps/place//@36.7362048,3.0375936,12z/data=!3m1!4b1', 'tipaza', 'pizaa ', 'jk@esiiil.gmail.com', '00 11 22 33 44', 'fb', 'insta', 5, 10),
(6, 'Traditional Algerian', 'https://i0.wp.com/dinepartner.com/blog/wp-content/uploads/2019/10/j.png?resize=696%2C396&ssl=1', 'https://www.google.com/maps/place/Traditional+Algerian+Restaurant/@36.7723458,3.0577245,15z/data=!4m', ' tayeb , Ex, 04 Rue Ferhat, Rue Ballay, El Djazair 16000', 'Traditional', 'js_laouadi@esi.dz', '0542 55 65 24', 'https://www.facebook.com/bouhaik35', 'https://www.instagram.com/restaurant_delice35/?igs', 3, 50);

-- --------------------------------------------------------

--
-- Structure de la table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
CREATE TABLE IF NOT EXISTS `reviews` (
  `idReview` int(11) NOT NULL,
  `idRestau` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `comment` longtext NOT NULL,
  PRIMARY KEY (`idReview`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `email` varchar(20) NOT NULL,
  `phoneNumber` varchar(50) NOT NULL,
  `address` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profilePicture` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `FirstName`, `LastName`, `email`, `phoneNumber`, `address`, `password`, `profilePicture`) VALUES
(1, 'Sara', 'Laouadi', 'js_laouadi@esi.dz', '0524875120', 'Kh-El-Kh boumerdes', 'sarah123', ''),
(2, 'Kaouthar', 'Essaheli', 'jk_essaheli@esi.dz', '0524875120', 'Tipaza', 'kaouthar123', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
