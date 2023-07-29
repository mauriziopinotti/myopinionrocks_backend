-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Creato il: Lug 29, 2023 alle 15:35
-- Versione del server: 8.0.33-0ubuntu0.22.04.4
-- Versione PHP: 8.1.2-1ubuntu2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `myopinionrocks`
--

--
-- Dump dei dati per la tabella `survey`
--

INSERT INTO `survey` (`id`, `title`) VALUES
(1, 'DID DONALD TRUMP ATTEMPT TO OVERTURN THE RESULTS OF THE 2020 ELECTION'),
(2, 'WHAT COLOR DO YOU THINK A TENNIS BALL IS?');

--
-- Dump dei dati per la tabella `survey_question`
--

INSERT INTO `survey_question` (`id`, `survey_id`, `title`) VALUES
(1, 1, 'Do you think that the 2020 presidential election was held fairly?'),
(2, 1, 'Was Donald Trump involved in an attempt to overturn the results of the 2020 election prior to the official certification of electoral votes?'),
(3, 1, 'How much responsibility does Donald Trump have for the takeover of the Capitol on January 6th, 2021?'),
(4, 2, 'What color is a tennis ball?'),
(5, 2, 'Have you ever played pickleball?'),
(6, 2, 'If a new pickleball court were built in close proximity to your home, would you...?');

--
-- Dump dei dati per la tabella `survey_answer`
--

INSERT INTO `survey_answer` (`id`, `question_id`, `title`) VALUES
(1, 1, 'Yes'),
(2, 1, 'No, but it didn\'t affect the outcome'),
(3, 1, 'No, and it affected the outcome'),
(4, 1, 'Not sure'),
(5, 2, 'Yes'),
(6, 2, 'No'),
(7, 2, 'Not sure'),
(8, 3, 'A lot'),
(9, 3, 'Some'),
(10, 3, 'A little'),
(11, 3, 'None'),
(12, 3, 'Not sure'),
(13, 4, 'Yellow'),
(14, 4, 'Green'),
(15, 4, 'Not sure'),
(16, 5, 'Yes'),
(17, 5, 'No'),
(18, 5, 'Not sure'),
(19, 6, 'Love it'),
(20, 6, 'Like it'),
(21, 6, 'Feel neutral'),
(22, 6, 'Dislike it'),
(23, 6, 'Hate it'),
(24, 6, 'Not sure');

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
