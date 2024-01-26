CREATE DATABASE `addressbook` ;
USE `addressbook`;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `Contacts` (
  `cid` int(11) NOT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `Contacts` (`cid`, `firstName`, `lastname`, `location`, `phone`) VALUES
(307, 'Aman', 'Raj', 'Patna', '7878787878');

ALTER TABLE `Contacts`
  ADD PRIMARY KEY (`cid`);


ALTER TABLE `Contacts`
  MODIFY `cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=308;
COMMIT;

