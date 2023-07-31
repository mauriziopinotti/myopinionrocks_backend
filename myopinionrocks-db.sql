-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Creato il: Lug 31, 2023 alle 08:34
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

-- --------------------------------------------------------

--
-- Struttura della tabella `DATABASECHANGELOG`
--

DROP TABLE IF EXISTS `DATABASECHANGELOG`;
CREATE TABLE IF NOT EXISTS `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `DATABASECHANGELOG`
--

INSERT INTO `DATABASECHANGELOG` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2023-07-29 15:38:06', 1, 'EXECUTED', '8:e9bd258eb7b0cf4dd734ed1b89463d5d', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', '', NULL, '4.20.0', NULL, NULL, '0637886047'),
('20230729091007-1', 'jhipster', 'config/liquibase/changelog/20230729091007_added_entity_Survey.xml', '2023-07-31 08:31:21', 2, 'EXECUTED', '8:2b61edd03ab73979749a0929e8b86f9f', 'createTable tableName=survey', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091007-1-relations', 'jhipster', 'config/liquibase/changelog/20230729091007_added_entity_Survey.xml', '2023-07-31 08:31:21', 3, 'EXECUTED', '8:e06c84ee7b35e334dd55764daf617c1e', 'createTable tableName=rel_survey__survey_question; addPrimaryKey tableName=rel_survey__survey_question', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091008-1', 'jhipster', 'config/liquibase/changelog/20230729091008_added_entity_SurveyQuestion.xml', '2023-07-31 08:31:21', 4, 'EXECUTED', '8:69a03d1ee1db00b182358cbbc342197d', 'createTable tableName=survey_question', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091008-1-relations', 'jhipster', 'config/liquibase/changelog/20230729091008_added_entity_SurveyQuestion.xml', '2023-07-31 08:31:22', 5, 'EXECUTED', '8:bcdd61c070a2f0b04fb10bd27b6d643d', 'createTable tableName=rel_survey_question__survey_answer; addPrimaryKey tableName=rel_survey_question__survey_answer', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091009-1', 'jhipster', 'config/liquibase/changelog/20230729091009_added_entity_SurveyAnswer.xml', '2023-07-31 08:31:22', 6, 'EXECUTED', '8:35a40e488c3e646af559a12a6f40039d', 'createTable tableName=survey_answer', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091006-1', 'jhipster', 'config/liquibase/changelog/20230729091006_added_entity_SurveyResult.xml', '2023-07-31 08:31:22', 7, 'EXECUTED', '8:e9c0c827f9ad3ba45f71849f501d1b9b', 'createTable tableName=survey_result; dropDefaultValue columnName=datetime, tableName=survey_result', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091006-1-relations', 'jhipster', 'config/liquibase/changelog/20230729091006_added_entity_SurveyResult.xml', '2023-07-31 08:31:22', 8, 'EXECUTED', '8:12b22ca0e0aa68ae1951aa0a9e48dd0f', 'createTable tableName=rel_survey_result__survey_question_answer; addPrimaryKey tableName=rel_survey_result__survey_question_answer', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091007-2', 'jhipster', 'config/liquibase/changelog/20230729091007_added_entity_constraints_Survey.xml', '2023-07-31 08:31:22', 9, 'EXECUTED', '8:a2388a5e7ffc7b1ca8b1700f548b6ffd', 'addForeignKeyConstraint baseTableName=rel_survey__survey_question, constraintName=fk_rel_survey__survey_question__survey_id, referencedTableName=survey; addForeignKeyConstraint baseTableName=rel_survey__survey_question, constraintName=fk_rel_surve...', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091008-2', 'jhipster', 'config/liquibase/changelog/20230729091008_added_entity_constraints_SurveyQuestion.xml', '2023-07-31 08:31:22', 10, 'EXECUTED', '8:844e8f83909628fb9e1005bea4574bf4', 'addForeignKeyConstraint baseTableName=rel_survey_question__survey_answer, constraintName=fk_rel_survey_question__survey_answer__survey_question_id, referencedTableName=survey_question; addForeignKeyConstraint baseTableName=rel_survey_question__sur...', '', NULL, '4.20.0', NULL, NULL, '0785081920'),
('20230729091006-2', 'jhipster', 'config/liquibase/changelog/20230729091006_added_entity_constraints_SurveyResult.xml', '2023-07-31 08:31:22', 11, 'EXECUTED', '8:787f2767b7b085710d5c170a412bfb01', 'addForeignKeyConstraint baseTableName=survey_result, constraintName=fk_survey_result__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=survey_result, constraintName=fk_survey_result__survey_id, referencedTableName=surve...', '', NULL, '4.20.0', NULL, NULL, '0785081920');

-- --------------------------------------------------------

--
-- Struttura della tabella `DATABASECHANGELOGLOCK`
--

DROP TABLE IF EXISTS `DATABASECHANGELOGLOCK`;
CREATE TABLE IF NOT EXISTS `DATABASECHANGELOGLOCK` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `DATABASECHANGELOGLOCK`
--

INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
CREATE TABLE IF NOT EXISTS `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `jhi_authority`
--

INSERT INTO `jhi_authority` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- --------------------------------------------------------

--
-- Struttura della tabella `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
CREATE TABLE IF NOT EXISTS `jhi_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `jhi_user`
--

INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', b'1', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
(2, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', b'1', 'en', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
(3, 'm+test1@gmail.com', '$2a$10$yyAy6uJooj1rN81YrQdSLuNgAo7r2s1BCCIK3MlVzoimxE/rxDzgq', 'Test', 'One', 'm+test1@gmail.com', NULL, b'1', 'en', NULL, NULL, 'anonymousUser', '2023-07-29 12:13:52', NULL, 'anonymousUser', '2023-07-29 12:13:52'),
(4, 'm+test2@gmail.com', '$2a$10$89Q4MdEunR858ji4bGJtXOf4pwCietVUaTvPz8Ye7bCoGhR7OxGs2', 'Test', 'Two', 'm+test2@gmail.com', NULL, b'1', 'en', NULL, NULL, 'anonymousUser', '2023-07-29 12:14:08', NULL, 'anonymousUser', '2023-07-29 12:14:08'),
(5, 'm+test3@gmail.com', '$2a$10$ojIsPBkHCrteqS33TMzk9OqjXT/6zwWPTPWHqfJJZbxKXiDzUlR2i', 'Test', 'Three', 'm+test3@gmail.com', NULL, b'1', 'en', NULL, NULL, 'anonymousUser', '2023-07-29 12:14:17', NULL, 'anonymousUser', '2023-07-29 12:14:17');

-- --------------------------------------------------------

--
-- Struttura della tabella `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
CREATE TABLE IF NOT EXISTS `jhi_user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `jhi_user_authority`
--

INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER'),
(5, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Struttura della tabella `rel_survey_question__survey_answer`
--

DROP TABLE IF EXISTS `rel_survey_question__survey_answer`;
CREATE TABLE IF NOT EXISTS `rel_survey_question__survey_answer` (
  `survey_question_id` bigint NOT NULL,
  `survey_answer_id` bigint NOT NULL,
  PRIMARY KEY (`survey_question_id`,`survey_answer_id`),
  KEY `fk_rel_survey_question__survey_answer__survey_answer_id` (`survey_answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

-- --------------------------------------------------------

--
-- Struttura della tabella `rel_survey_result__survey_question_answer`
--

DROP TABLE IF EXISTS `rel_survey_result__survey_question_answer`;
CREATE TABLE IF NOT EXISTS `rel_survey_result__survey_question_answer` (
  `survey_result_id` bigint NOT NULL,
  `survey_question_id` bigint NOT NULL,
  `survey_answer_id` bigint NOT NULL,
  PRIMARY KEY (`survey_result_id`,`survey_question_id`,`survey_answer_id`),
  KEY `fk_rel_survey_result__survey_question__survey_question_id` (`survey_question_id`),
  KEY `fk_rel_survey_result__survey_answer__survey_answer_id` (`survey_answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `rel_survey_result__survey_question_answer`
--

INSERT INTO `rel_survey_result__survey_question_answer` (`survey_result_id`, `survey_question_id`, `survey_answer_id`) VALUES
(3, 1, 2),
(5, 1, 2),
(6, 1, 2),
(7, 1, 2),
(8, 1, 2),
(9, 1, 2),
(10, 1, 2),
(11, 1, 2),
(12, 1, 2),
(13, 1, 2),
(14, 1, 2),
(15, 1, 2),
(16, 1, 2),
(17, 1, 2),
(18, 1, 4),
(19, 1, 4),
(20, 1, 4),
(21, 1, 4),
(22, 1, 4),
(23, 1, 4),
(3, 2, 3),
(5, 2, 2),
(6, 2, 2),
(7, 2, 3),
(8, 2, 2),
(9, 2, 2),
(10, 2, 3),
(11, 2, 3),
(12, 2, 3),
(13, 2, 3),
(14, 2, 3),
(15, 2, 3),
(16, 2, 3),
(17, 2, 3),
(18, 2, 3),
(19, 2, 3),
(20, 2, 3),
(21, 2, 3),
(22, 2, 3),
(23, 2, 3),
(3, 3, 7),
(5, 3, 8),
(6, 3, 8),
(7, 3, 7),
(8, 3, 8),
(9, 3, 8),
(10, 3, 7),
(11, 3, 7),
(12, 3, 7),
(13, 3, 7),
(14, 3, 7),
(15, 3, 7),
(16, 3, 7),
(17, 3, 7),
(18, 3, 8),
(19, 3, 8),
(20, 3, 8),
(21, 3, 8),
(22, 3, 8),
(23, 3, 8),
(4, 4, 10),
(4, 5, 3),
(4, 6, 12);

-- --------------------------------------------------------

--
-- Struttura della tabella `rel_survey__survey_question`
--

DROP TABLE IF EXISTS `rel_survey__survey_question`;
CREATE TABLE IF NOT EXISTS `rel_survey__survey_question` (
  `survey_id` bigint NOT NULL,
  `survey_question_id` bigint NOT NULL,
  PRIMARY KEY (`survey_id`,`survey_question_id`),
  KEY `fk_rel_survey__survey_question__survey_question_id` (`survey_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `rel_survey__survey_question`
--

INSERT INTO `rel_survey__survey_question` (`survey_id`, `survey_question_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(2, 6);

-- --------------------------------------------------------

--
-- Struttura della tabella `survey`
--

DROP TABLE IF EXISTS `survey`;
CREATE TABLE IF NOT EXISTS `survey` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `survey`
--

INSERT INTO `survey` (`id`, `title`) VALUES
(1, 'DID DONALD TRUMP ATTEMPT TO OVERTURN THE RESULTS OF THE 2020 ELECTION?'),
(2, 'WHAT COLOR DO YOU THINK A TENNIS BALL IS?');

-- --------------------------------------------------------

--
-- Struttura della tabella `survey_answer`
--

DROP TABLE IF EXISTS `survey_answer`;
CREATE TABLE IF NOT EXISTS `survey_answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `survey_answer`
--

INSERT INTO `survey_answer` (`id`, `title`) VALUES
(1, 'Not sure'),
(2, 'Yes'),
(3, 'No'),
(4, 'No, but it didn\'t affect the outcome'),
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

-- --------------------------------------------------------

--
-- Struttura della tabella `survey_question`
--

DROP TABLE IF EXISTS `survey_question`;
CREATE TABLE IF NOT EXISTS `survey_question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

-- --------------------------------------------------------

--
-- Struttura della tabella `survey_result`
--

DROP TABLE IF EXISTS `survey_result`;
CREATE TABLE IF NOT EXISTS `survey_result` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `survey_id` bigint DEFAULT NULL,
  `datetime` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_survey_result__user_id` (`user_id`),
  KEY `fk_survey_result__survey_id` (`survey_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dump dei dati per la tabella `survey_result`
--

INSERT INTO `survey_result` (`id`, `user_id`, `survey_id`, `datetime`) VALUES
(3, NULL, 1, '2023-07-30 09:47:55.189033'),
(4, NULL, 2, '2023-07-30 21:09:15.626661'),
(5, NULL, 1, '2023-07-30 21:09:31.529729'),
(6, NULL, 1, '2023-07-30 21:10:22.321405'),
(7, NULL, 1, '2023-07-30 21:11:02.552944'),
(8, NULL, 1, '2023-07-30 21:12:41.995869'),
(9, NULL, 1, '2023-07-30 21:12:50.422566'),
(10, NULL, 1, '2023-07-30 21:13:09.677216'),
(11, NULL, 1, '2023-07-30 21:16:26.441877'),
(12, NULL, 1, '2023-07-30 21:16:37.740019'),
(13, NULL, 1, '2023-07-30 21:17:35.459916'),
(14, NULL, 1, '2023-07-30 21:17:44.127831'),
(15, NULL, 1, '2023-07-30 21:18:06.777201'),
(16, NULL, 1, '2023-07-30 21:18:21.033401'),
(17, NULL, 1, '2023-07-30 21:18:32.638278'),
(18, NULL, 1, '2023-07-30 21:19:31.065044'),
(19, NULL, 1, '2023-07-30 21:21:11.183046'),
(20, NULL, 1, '2023-07-30 21:22:45.276523'),
(21, NULL, 1, '2023-07-30 21:23:21.308860'),
(22, NULL, 1, '2023-07-30 21:23:29.006068'),
(23, NULL, 1, '2023-07-30 21:23:42.512991');

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Limiti per la tabella `rel_survey_question__survey_answer`
--
ALTER TABLE `rel_survey_question__survey_answer`
  ADD CONSTRAINT `fk_rel_survey_question__survey_answer__survey_answer_id` FOREIGN KEY (`survey_answer_id`) REFERENCES `survey_answer` (`id`),
  ADD CONSTRAINT `fk_rel_survey_question__survey_answer__survey_question_id` FOREIGN KEY (`survey_question_id`) REFERENCES `survey_question` (`id`);

--
-- Limiti per la tabella `rel_survey_result__survey_question_answer`
--
ALTER TABLE `rel_survey_result__survey_question_answer`
  ADD CONSTRAINT `fk_rel_survey_result__survey_answer__survey_answer_id` FOREIGN KEY (`survey_answer_id`) REFERENCES `survey_answer` (`id`),
  ADD CONSTRAINT `fk_rel_survey_result__survey_question__survey_question_id` FOREIGN KEY (`survey_question_id`) REFERENCES `survey_question` (`id`),
  ADD CONSTRAINT `fk_rel_survey_result__survey_question__survey_result_id` FOREIGN KEY (`survey_result_id`) REFERENCES `survey_result` (`id`);

--
-- Limiti per la tabella `rel_survey__survey_question`
--
ALTER TABLE `rel_survey__survey_question`
  ADD CONSTRAINT `fk_rel_survey__survey_question__survey_id` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`),
  ADD CONSTRAINT `fk_rel_survey__survey_question__survey_question_id` FOREIGN KEY (`survey_question_id`) REFERENCES `survey_question` (`id`);

--
-- Limiti per la tabella `survey_result`
--
ALTER TABLE `survey_result`
  ADD CONSTRAINT `fk_survey_result__survey_id` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`id`),
  ADD CONSTRAINT `fk_survey_result__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
