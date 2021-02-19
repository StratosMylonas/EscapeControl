-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 18, 2020 at 08:10 AM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `escapecontrol`
--

-- --------------------------------------------------------

--
-- Table structure for table `atlantis`
--

DROP TABLE IF EXISTS `atlantis`;
CREATE TABLE IF NOT EXISTS `atlantis` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tritons_key` int(1) NOT NULL,
  `room_door_1_2` int(1) NOT NULL,
  `holy_molly` int(1) NOT NULL,
  `poseidons_chest` int(1) NOT NULL,
  `hexagon_cabinets` int(1) NOT NULL,
  `waterfall_btn` int(1) NOT NULL,
  `tritons_key_btn` int(1) NOT NULL,
  `column1_btn` int(1) NOT NULL,
  `column2_btn` int(1) NOT NULL,
  `column3_btn` int(1) NOT NULL,
  `room_door_1_2_btn` int(1) NOT NULL,
  `holy_molly_btn` int(1) NOT NULL,
  `poseidon_btn` int(1) NOT NULL,
  `hexagon_pattern_btn` int(1) NOT NULL,
  `hexagon_cabinets_btn` int(1) NOT NULL,
  `trident_unlock_btn` int(1) NOT NULL,
  `exit_btn` int(1) NOT NULL,
  `reset_btn` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `atlantis`
--

INSERT INTO `atlantis` (`id`, `tritons_key`, `room_door_1_2`, `holy_molly`, `poseidons_chest`, `hexagon_cabinets`, `waterfall_btn`, `tritons_key_btn`, `column1_btn`, `column2_btn`, `column3_btn`, `room_door_1_2_btn`, `holy_molly_btn`, `poseidon_btn`, `hexagon_pattern_btn`, `hexagon_cabinets_btn`, `trident_unlock_btn`, `exit_btn`, `reset_btn`) VALUES
(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `haunted_mansion`
--

DROP TABLE IF EXISTS `haunted_mansion`;
CREATE TABLE IF NOT EXISTS `haunted_mansion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mirror_drawer` int(1) NOT NULL,
  `mirror_cabinet` int(1) NOT NULL,
  `living_room_door` int(1) NOT NULL,
  `kids_room_door` int(1) NOT NULL,
  `passage` int(1) NOT NULL,
  `window_doors` int(1) NOT NULL,
  `tarrot_combination_1_btn` int(1) NOT NULL,
  `tarrot_combination_2_btn` int(1) NOT NULL,
  `tarrot_combination_3_btn` int(1) NOT NULL,
  `radio_on_btn` int(1) NOT NULL,
  `doll_btn` int(1) NOT NULL,
  `shelf_1_btn` int(1) NOT NULL,
  `shelf_2_btn` int(1) NOT NULL,
  `window_doors_btn` int(1) NOT NULL,
  `exit_btn` int(1) NOT NULL,
  `reset_btn` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `haunted_mansion`
--

INSERT INTO `haunted_mansion` (`id`, `mirror_drawer`, `mirror_cabinet`, `living_room_door`, `kids_room_door`, `passage`, `window_doors`, `tarrot_combination_1_btn`, `tarrot_combination_2_btn`, `tarrot_combination_3_btn`, `radio_on_btn`, `doll_btn`, `shelf_1_btn`, `shelf_2_btn`, `window_doors_btn`, `exit_btn`, `reset_btn`) VALUES
(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `mission`
--

DROP TABLE IF EXISTS `mission`;
CREATE TABLE IF NOT EXISTS `mission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lasers_btn` int(1) NOT NULL,
  `mobile_phone_btn` int(1) NOT NULL,
  `office_door_btn` int(1) NOT NULL,
  `desk_cabinet_btn` int(1) NOT NULL,
  `bookcage_cabinet_btn` int(1) NOT NULL,
  `bansky_painting_btn` int(1) NOT NULL,
  `frame_rfid_btn` int(1) NOT NULL,
  `control_room_door_btn` int(1) NOT NULL,
  `vault_door_btn` int(1) NOT NULL,
  `ventilation_btn` int(1) NOT NULL,
  `money_drop_btn` int(1) NOT NULL,
  `panel_numbers_btn` int(1) NOT NULL,
  `console_large_buttons_btn` int(1) NOT NULL,
  `console_metal_buttons_btn` int(1) NOT NULL,
  `passage_btn` int(1) NOT NULL,
  `exit_btn` int(1) NOT NULL,
  `reset_btn` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mission`
--

INSERT INTO `mission` (`id`, `lasers_btn`, `mobile_phone_btn`, `office_door_btn`, `desk_cabinet_btn`, `bookcage_cabinet_btn`, `bansky_painting_btn`, `frame_rfid_btn`, `control_room_door_btn`, `vault_door_btn`, `ventilation_btn`, `money_drop_btn`, `panel_numbers_btn`, `console_large_buttons_btn`, `console_metal_buttons_btn`, `passage_btn`, `exit_btn`, `reset_btn`) VALUES
(1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `philosopher`
--

DROP TABLE IF EXISTS `philosopher`;
CREATE TABLE IF NOT EXISTS `philosopher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coins_prop_btn` int(1) NOT NULL,
  `harp_btn` int(1) NOT NULL,
  `passage_btn` int(1) NOT NULL,
  `knight_video_btn` int(1) NOT NULL,
  `holding_keys_door_btn` int(1) NOT NULL,
  `books_open_btn` int(1) NOT NULL,
  `books_close_btn` int(1) NOT NULL,
  `bookcase_door_btn` int(11) NOT NULL,
  `mirror_btn` int(1) NOT NULL,
  `exit_btn` int(1) NOT NULL,
  `reset_btn` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `philosopher`
--

INSERT INTO `philosopher` (`id`, `coins_prop_btn`, `harp_btn`, `passage_btn`, `knight_video_btn`, `holding_keys_door_btn`, `books_open_btn`, `books_close_btn`, `bookcase_door_btn`, `mirror_btn`, `exit_btn`, `reset_btn`) VALUES
(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
