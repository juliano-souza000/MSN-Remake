-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 15, 2021 at 09:55 AM
-- Server version: 10.3.23-MariaDB-1
-- PHP Version: 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `MSN`
--

-- --------------------------------------------------------

--
-- Table structure for table `Accounts`
--

CREATE TABLE `Accounts` (
  `UserID` int(11) NOT NULL,
  `UserName` text NOT NULL,
  `UserEmail` text NOT NULL,
  `UserPassword` text NOT NULL,
  `UserProfilePicturePath` text DEFAULT '/files/default_profile_picture.png',
  `UserAbout` text DEFAULT 'Hello! I am using Live Messenger.',
  `UserStatus` int(11) NOT NULL DEFAULT 3
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `MessageContent`
--

CREATE TABLE `MessageContent` (
  `MessageContentID` int(11) NOT NULL,
  `MessageContent` text COLLATE utf8mb4_bin NOT NULL,
  `MessageType` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `Messages`
--

CREATE TABLE `Messages` (
  `MessageID` int(11) NOT NULL,
  `UserIDFrom` int(11) NOT NULL,
  `UserIDTo` int(11) NOT NULL,
  `MessageContentID` int(11) NOT NULL,
  `MessageTimestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Accounts`
--
ALTER TABLE `Accounts`
  ADD PRIMARY KEY (`UserID`);

--
-- Indexes for table `MessageContent`
--
ALTER TABLE `MessageContent`
  ADD PRIMARY KEY (`MessageContentID`);

--
-- Indexes for table `Messages`
--
ALTER TABLE `Messages`
  ADD PRIMARY KEY (`MessageID`),
  ADD KEY `MessageContent` (`MessageContentID`),
  ADD KEY `UserIDFrom` (`UserIDFrom`),
  ADD KEY `UserIDTo` (`UserIDTo`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Accounts`
--
ALTER TABLE `Accounts`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `MessageContent`
--
ALTER TABLE `MessageContent`
  MODIFY `MessageContentID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Messages`
--
ALTER TABLE `Messages`
  MODIFY `MessageID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Messages`
--
ALTER TABLE `Messages`
  ADD CONSTRAINT `Messages_ibfk_1` FOREIGN KEY (`UserIDFrom`) REFERENCES `Accounts` (`UserID`),
  ADD CONSTRAINT `Messages_ibfk_2` FOREIGN KEY (`MessageContentID`) REFERENCES `MessageContent` (`MessageContentID`),
  ADD CONSTRAINT `Messages_ibfk_3` FOREIGN KEY (`UserIDTo`) REFERENCES `Accounts` (`UserID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
