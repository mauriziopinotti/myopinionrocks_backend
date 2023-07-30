-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Creato il: Lug 30, 2023 alle 10:27
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
-- Dump dei dati per la tabella `rel_survey_question__survey_answer`
--

INSERT INTO `rel_survey_question__survey_answer` (`survey_question_id`, `survey_answer_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(1, 2),
(2, 2),
(5, 2),
(2, 3),
(5, 3),
(1, 4),
(1, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(4, 10),
(4, 11),
(6, 12),
(6, 13),
(6, 14),
(6, 15),
(6, 16);

--
-- Dump dei dati per la tabella `rel_survey__survey_question`
--

INSERT INTO `rel_survey__survey_question` (`survey_question_id`, `survey_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2),
(6, 2);

--
-- Dump dei dati per la tabella `survey`
--

INSERT INTO `survey` (`id`, `title`) VALUES
(1, 'DID DONALD TRUMP ATTEMPT TO OVERTURN THE RESULTS OF THE 2020 ELECTION?'),
(2, 'WHAT COLOR DO YOU THINK A TENNIS BALL IS?');

--
-- Dump dei dati per la tabella `survey_answer`
--

INSERT INTO `survey_answer` (`id`, `title`) VALUES
(1, 'Not sure'),
(2, 'Yes'),
(3, 'No'),
(4, 'No, but it didn\\\'t affect the outcome'),
(5, 'No, and it affected the outcome'),
(6, 'A lot'),
(7, 'Some'),
(8, 'A little'),
(9, 'None'),
(10, 'Yellow'),
(11, 'Green'),
(12, 'Love it'),
(13, 'Like it'),
(14, 'Feel neutral'),
(15, 'Dislike it'),
(16, 'Hate it');

--
-- Dump dei dati per la tabella `survey_question`
--

INSERT INTO `survey_question` (`id`, `title`) VALUES
(1, 'Do you think that the 2020 presidential election was held fairly?'),
(2, 'Was Donald Trump involved in an attempt to overturn the results of the 2020 election prior to the official certification of electoral votes?'),
(3, 'How much responsibility does Donald Trump have for the takeover of the Capitol on January 6th, 2021?'),
(4, 'What color is a tennis ball?'),
(5, 'Have you ever played pickleball?'),
(6, 'If a new pickleball court were built in close proximity to your home, would you...?');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
