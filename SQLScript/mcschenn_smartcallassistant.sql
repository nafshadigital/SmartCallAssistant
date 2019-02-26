-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 26, 2019 at 04:35 PM
-- Server version: 5.5.62-cll-lve
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mcschenn_smartcallassistant`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_admin_email_ids`
--

CREATE TABLE `tbl_admin_email_ids` (
  `id` int(11) NOT NULL,
  `admin_email_ids` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_admin_email_ids`
--

INSERT INTO `tbl_admin_email_ids` (`id`, `admin_email_ids`) VALUES
(1, 'traja.malaris@gmail.com, kandasamy.malaris@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_country`
--

CREATE TABLE `tbl_country` (
  `id` int(11) NOT NULL,
  `country_code` varchar(4) DEFAULT NULL,
  `country` varchar(75) DEFAULT NULL,
  `is_active` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_country`
--

INSERT INTO `tbl_country` (`id`, `country_code`, `country`, `is_active`) VALUES
(1, '+91', 'India', 1),
(2, '+1', 'United States', 1),
(3, '+971', 'United Arab Emirates', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_notifications`
--

CREATE TABLE `tbl_notifications` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `to_user_id` int(11) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_notifications`
--

INSERT INTO `tbl_notifications` (`id`, `user_id`, `to_user_id`, `message`, `created_date`) VALUES
(14, 171, 169, 'Driving. Please call me after 14 minutes.', '2019-02-23 10:43:24'),
(15, 171, 169, 'Driving. Please call me after 10 minutes.', '2019-02-23 10:47:42'),
(16, 171, 169, 'Driving. Please call me after 03 minutes.', '2019-02-23 10:54:44'),
(17, 171, 169, 'Driving. Please call me after 11 minutes.', '2019-02-23 10:59:17'),
(19, 169, 171, 'Driving. Please call me after 15 minutes.', '2019-02-23 11:13:51'),
(20, 169, 171, 'Driving. Please call me after 15 minutes.', '2019-02-23 11:13:51'),
(21, 169, 171, 'Driving. Please call me after 15 minutes.', '2019-02-23 11:13:51'),
(22, 169, 171, 'Driving. Please call me after 15 minutes.', '2019-02-23 11:13:51'),
(23, 169, 171, 'Driving. Please call me after 15 minutes.', '2019-02-23 11:13:51'),
(24, 169, 171, 'Driving. Please call me after 15 minutes.', '2019-02-23 11:13:51'),
(25, 169, 171, 'Driving. Please call me after 15 minutes.', '2019-02-23 11:13:51'),
(26, 169, 166, 'Driving. Please call me after 15 minutes.', '2019-02-23 11:13:51'),
(27, 22, 23, 'Driving. Please call me after 02 minutes.', '2019-02-23 12:49:53'),
(28, 23, 22, 'Driving. Please call me after 01 minutes.', '2019-02-23 12:55:07'),
(29, 23, 22, 'In a meeting. Please call me after 03 minutes.', '2019-02-23 13:01:48'),
(30, 23, 22, 'Taking rest. Please call me after 03 minutes.', '2019-02-23 14:36:50'),
(31, 177, 174, 'Driving. Please call me after 54 minutes.', '2019-02-24 15:59:51'),
(32, 177, 174, 'Driving. Please call me after 51 minutes.', '2019-02-24 16:02:10'),
(33, 177, 174, 'Driving. Please call me after 49 minutes.', '2019-02-24 16:04:02'),
(34, 22, 23, 'In a meeting. Please call me after 06 minutes.', '2019-02-24 16:07:45'),
(35, 22, 174, 'In a meeting. Please call me after 03 minutes.', '2019-02-24 16:10:01'),
(36, 178, 174, 'Driving. Please call me after 58 minutes.', '2019-02-24 16:11:33'),
(37, 178, 174, 'Driving. Please call me after 54 minutes.', '2019-02-24 16:15:27'),
(38, 179, 174, 'in the movie theater call me later. Please call me after 58 minutes.', '2019-02-24 16:29:19'),
(39, 179, 174, 'in the movie theater call me later. Please call me after 57 minutes.', '2019-02-24 16:30:39'),
(40, 174, 179, 'Driving. Please call me after 01 hours and 06 minutes.', '2019-02-24 16:33:11'),
(41, 22, 23, 'Taking rest. Please call me after 03 minutes.', '2019-02-24 19:41:43'),
(42, 23, 22, 'Taking rest. Please call me after 08 minutes.', '2019-02-24 19:43:44'),
(43, 22, 23, 'Taking rest. Please call me after .', '2019-02-24 19:44:26'),
(44, 22, 23, 'Taking rest. Please call me after .', '2019-02-24 19:44:57');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_support`
--

CREATE TABLE `tbl_support` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `message` text,
  `msg_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_support`
--

INSERT INTO `tbl_support` (`id`, `user_id`, `message`, `msg_date`, `status`) VALUES
(1, 17, 'this is test for small call rest handler', '2018-11-16 11:18:23', 0),
(2, 17, 'this is test for small call rest handler', '2018-11-16 11:19:05', 0),
(3, 17, '1this is test for small call rest handler1', '2018-11-16 11:19:27', 0),
(4, 16, '1this is test for small call rest handler1', '2018-11-16 11:19:36', 0),
(5, 16, '', '2018-11-16 11:20:54', 0),
(6, 21, 'This is test content', '2018-11-16 12:37:36', 0),
(7, 21, 'This is test content1', '2018-11-16 12:38:34', 0),
(8, 21, 'This is test of the support', '2018-11-17 10:48:50', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `id` int(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `android_id` varchar(300) NOT NULL,
  `device_id` varchar(300) NOT NULL,
  `country_code` varchar(4) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `wallet_balance` decimal(11,2) DEFAULT '0.00',
  `is_active` int(11) DEFAULT '1',
  `last_seen` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`id`, `name`, `email`, `android_id`, `device_id`, `country_code`, `mobile`, `dob`, `created_date`, `modified_date`, `wallet_balance`, `is_active`, `last_seen`) VALUES
(1, 'raja', 'raja@gmail.com', '', '', NULL, NULL, '1993-03-19', '2018-09-26 08:54:45', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(2, 'ganesh', 'ganesh@gmail.com', '', '', NULL, NULL, '1994-05-13', '2018-09-28 04:52:28', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(3, 'imman', 'imman@gmail.com', '', '', NULL, NULL, '1992-12-17', '2018-09-28 04:53:29', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(4, 'kumar', 'kumar@gmail.com', '', '', NULL, NULL, '1995-08-12', '2018-09-28 04:54:30', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(10, 'suresh', 'suresh@gmail.com', '', '', NULL, NULL, '1993-02-15', '2018-09-29 09:26:26', NULL, '20.00', 1, '0000-00-00 00:00:00'),
(11, 'uty', 'ut', '', '', NULL, NULL, '0000-00-00', '2018-09-29 10:01:24', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(12, 'gdfg', 'gdfg', '', '', NULL, NULL, '0000-00-00', '2018-09-29 11:59:07', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(13, 'kandasamy', 'kandasamy.malaris@gmail.com', '', '', NULL, NULL, '1994-05-13', '2018-10-11 15:09:42', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(14, NULL, NULL, '', '', '+81', '9632587410', NULL, '2018-11-16 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(15, NULL, NULL, '', '', '+81', '9632587410', NULL, '2018-11-16 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(16, NULL, NULL, '', '', '+81', '9632587410', NULL, '2018-11-16 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(17, 'kannan', 'kannan@gmail.com', '', '', '+81', '9632587410', NULL, '2018-11-16 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(18, 'ganesh', 'ganesh@gmail.com', '', '', '+81', '9632587410', NULL, '2018-11-16 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(19, NULL, NULL, '', '', '', '9632587410', NULL, '2018-11-16 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(21, 'karthick', 'karthick@gmail.com', '', '', '+91', '1234567890', NULL, '2018-11-16 00:00:00', NULL, '0.00', 1, '2018-11-21 05:10:25'),
(22, 'karthick', 'karthickmce@gmail.com', '', '', '+91', '9787249698', NULL, '2018-12-13 00:00:00', NULL, '0.00', 1, '2019-02-24 19:44:05'),
(23, 'Sindhuja', 'Sindhuja.malaris@gmail.com ', '', '', '+91', '9787328698', NULL, '2018-12-13 00:00:00', NULL, '0.00', 1, '2019-02-24 19:45:22'),
(25, NULL, NULL, '', '', '+91', '9677554476', NULL, '2018-12-25 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(26, 'Mohamed Akbar', 'mohamed Akbar.k@gmail.com', '', '', '+971', '0502584526', NULL, '2018-12-27 00:00:00', NULL, '0.00', 1, '2019-02-24 04:42:12'),
(27, NULL, NULL, '', '', '+91', '9876543210', NULL, '2019-01-04 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(28, 'malar', 'malar@gmail.com', '532350bac7ddba70', '', NULL, NULL, NULL, '2019-01-05 00:00:00', NULL, '0.00', 1, '2019-01-05 13:46:13'),
(30, 'Raja', 'raja@gmail.com', '80f415927a20e023', '353380074853366', NULL, NULL, NULL, '2019-01-07 00:00:00', NULL, '0.00', 1, '2019-01-23 13:08:51'),
(31, NULL, NULL, 'b15523a790f2d180', '355266041232537', NULL, NULL, NULL, '2019-01-07 00:00:00', NULL, '0.00', 1, '2019-01-07 13:27:42'),
(33, NULL, NULL, 'd3a67e4cb77533da', '357537080715253', NULL, NULL, NULL, '2019-01-07 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(34, NULL, NULL, '478a5c02c288a6a7', '354267096712325', NULL, NULL, NULL, '2019-01-07 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(35, NULL, NULL, '4366461f92513537', '', NULL, NULL, NULL, '2019-01-07 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(36, NULL, NULL, 'dea572ebabc390be', '12345678902468', NULL, NULL, NULL, '2019-01-07 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(37, NULL, NULL, 'fc8d0bd37a9a04e', '357019080330227', NULL, NULL, NULL, '2019-01-07 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(38, 'mohamed Rahmathullah', 'nafsha.mohamed@gmail.com', 'f9f01dd7f1fb46b4', '867274023103776', NULL, NULL, NULL, '2019-01-10 00:00:00', NULL, '0.00', 1, '2019-02-20 04:18:31'),
(39, NULL, NULL, 'a55918aff366aa19', '355266047661010', NULL, NULL, NULL, '2019-01-11 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(40, NULL, NULL, '3605f7eb119b21f0', '355266042076974', NULL, NULL, NULL, '2019-01-11 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(41, 'Kiante Burton-Solomon', 'kianteburtonsolomon@gmail.com', 'f53c0fdc78d11544', '355418091712971', NULL, NULL, NULL, '2019-01-16 00:00:00', NULL, '0.00', 1, '2019-01-16 21:40:32'),
(42, 'null', 'null', 'f42d4a0775b71fb4', '355266040231365', NULL, NULL, NULL, '2019-01-17 00:00:00', NULL, '0.00', 1, '2019-01-17 15:20:05'),
(43, 'null', 'null', '7bfae665d455e36f', '355266044087060', NULL, NULL, NULL, '2019-01-17 00:00:00', NULL, '0.00', 1, '2019-01-17 15:20:54'),
(44, NULL, NULL, '1112fd25450d3617', '354527081822508', NULL, NULL, NULL, '2019-01-17 00:00:00', NULL, '0.00', 1, '2019-02-24 14:27:38'),
(45, 'karthick ', 'karthick.malaris@gmail.com', 'a4fac081f92a7ae', '358094058486524', NULL, NULL, NULL, '2019-01-17 00:00:00', NULL, '0.00', 1, '2019-02-18 12:54:38'),
(48, 'raja', 'raja@gmail.com', '532350bac7ddba70', '358240051111110', NULL, NULL, NULL, '2019-01-18 00:00:00', NULL, '0.00', 1, '2019-01-23 12:46:45'),
(49, 'Sindhuja', 'Karthickmce@gmail.com', '8246d0e7b3cba2d4', '862512032621493', NULL, NULL, NULL, '2019-01-18 00:00:00', NULL, '0.00', 1, '2019-02-12 12:08:21'),
(50, 'null', 'null', '46a1e5b9edd275cc', '354699069348135', NULL, NULL, NULL, '2019-01-19 00:00:00', NULL, '0.00', 1, '2019-01-19 07:08:04'),
(51, 'null', 'null', '3a7f6e335304ac66', '353234090599737', NULL, NULL, NULL, '2019-01-19 00:00:00', NULL, '0.00', 1, '2019-01-19 15:41:41'),
(52, NULL, NULL, 'bbf1028b76cd2107', '355266041807619', NULL, NULL, NULL, '2019-01-19 00:00:00', NULL, '0.00', 1, '2019-01-19 08:47:04'),
(53, NULL, NULL, 'b7a0dce1f99852e4', '355266042816221', NULL, NULL, NULL, '2019-01-19 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(54, NULL, NULL, '170fb52ec67a3a80', '355266046033609', NULL, NULL, NULL, '2019-01-19 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(55, 'Mohamed Akbar', 'softakbar@gmail.com ', '19d33edbb8f975d0', '352326100602252', NULL, NULL, NULL, '2019-01-20 00:00:00', NULL, '0.00', 1, '2019-02-12 18:45:20'),
(56, NULL, NULL, '18af3bd47424db61', '865879033466146', NULL, NULL, NULL, '2019-01-21 00:00:00', NULL, '0.00', 1, '2019-01-21 08:17:42'),
(57, 'asanbasha', '7667414018', 'b1df42d6ae94324e', '356150057146766', NULL, NULL, NULL, '2019-01-21 00:00:00', NULL, '0.00', 1, '2019-01-21 11:36:28'),
(58, NULL, NULL, 'c02b479c1179750b', '358792087313805', NULL, NULL, NULL, '2019-01-21 00:00:00', NULL, '0.00', 1, '2019-01-21 11:55:44'),
(59, NULL, NULL, 'c62f47f97ca4f0c4', '358502080182747', NULL, NULL, NULL, '2019-01-23 00:00:00', NULL, '0.00', 1, '2019-01-23 03:01:27'),
(60, NULL, NULL, 'd4daf6a5fcbf5940', '355266046564017', NULL, NULL, NULL, '2019-01-23 00:00:00', NULL, '0.00', 1, '2019-01-23 03:00:49'),
(61, NULL, NULL, 'e9b66e792adadc77', '866278029380156', NULL, NULL, NULL, '2019-01-23 00:00:00', NULL, '0.00', 1, '2019-01-23 07:19:02'),
(62, NULL, NULL, 'f1eac56068cd50de', '355266044634671', NULL, NULL, NULL, '2019-01-23 00:00:00', NULL, '0.00', 1, '2019-01-23 13:08:04'),
(63, NULL, NULL, '4149b09e618855f7', '355266044853396', NULL, NULL, NULL, '2019-01-23 00:00:00', NULL, '0.00', 1, '2019-01-23 13:08:04'),
(64, NULL, NULL, 'be373ff9aa094a9a', '351562090190731', NULL, NULL, NULL, '2019-01-24 00:00:00', NULL, '0.00', 1, '2019-01-24 01:08:31'),
(65, NULL, NULL, 'f90f8948468fd242', '868150034258495', NULL, NULL, NULL, '2019-01-25 00:00:00', NULL, '0.00', 1, '2019-01-25 02:45:07'),
(66, NULL, NULL, '660819de014e40ed', '352711091708780', NULL, NULL, NULL, '2019-01-26 00:00:00', NULL, '0.00', 1, '2019-01-26 08:21:11'),
(67, NULL, NULL, 'f1beded6a7fee12d', '357736089725244', NULL, NULL, NULL, '2019-01-26 00:00:00', NULL, '0.00', 1, '2019-01-26 12:08:20'),
(68, 'amal', 'amalnkhili@gmail.com', '60dfcd961719c033', '357414070733441', NULL, NULL, NULL, '2019-01-27 00:00:00', NULL, '0.00', 1, '2019-01-27 03:05:50'),
(69, 'kamlesh', 'kamleshchauhan564@com', '787b7316b7958099', '863661038171737', NULL, NULL, NULL, '2019-01-27 00:00:00', NULL, '0.00', 1, '2019-01-27 04:36:26'),
(70, NULL, NULL, '1ef97c51dbd154fa', '356420079943940', NULL, NULL, NULL, '2019-01-27 00:00:00', NULL, '0.00', 1, '2019-01-27 21:40:30'),
(71, NULL, NULL, '9201a47a6f27c51b', '355266043668845', NULL, NULL, NULL, '2019-01-28 00:00:00', NULL, '0.00', 1, '2019-01-28 02:00:27'),
(72, NULL, NULL, 'cbf7ee2192ce7869', '353380079627948', NULL, NULL, NULL, '2019-01-29 00:00:00', NULL, '0.00', 1, '2019-01-29 03:19:17'),
(73, 'Anna', 'Annafoxus', 'b30e2ab4092a1054', '864875030047831', NULL, NULL, NULL, '2019-01-29 00:00:00', NULL, '0.00', 1, '2019-01-29 04:22:22'),
(74, NULL, NULL, 'ed379070e6ee51ff', '867598031477180', NULL, NULL, NULL, '2019-01-29 00:00:00', NULL, '0.00', 1, '2019-01-29 09:35:08'),
(75, 'Nicholas Elias', 'chibayanicholus@gmail.com', '14b67ea05403dd69', '351815100734462', NULL, NULL, NULL, '2019-01-30 00:00:00', NULL, '0.00', 1, '2019-01-30 13:09:36'),
(76, NULL, NULL, '710f7887dc1ea8c3', '351592106266225', NULL, NULL, NULL, '2019-01-30 00:00:00', NULL, '0.00', 1, '2019-01-30 15:23:49'),
(77, 'Eman', 'hffjgfkhff@gmail.com', 'e67d5a57a366b5f6', '864963040611190', NULL, NULL, NULL, '2019-01-31 00:00:00', NULL, '0.00', 1, '2019-02-02 02:34:10'),
(78, NULL, NULL, '2ec44b2bba146215', '355266044959789', NULL, NULL, NULL, '2019-01-31 00:00:00', NULL, '0.00', 1, '2019-01-31 14:48:40'),
(79, NULL, NULL, '39e2ed954344b4bf', '355266046519888', NULL, NULL, NULL, '2019-01-31 00:00:00', NULL, '0.00', 1, '2019-01-31 14:51:03'),
(80, 'Aadan', '634448742', '722b2b937fbfa4cf', '357656089597635', NULL, NULL, NULL, '2019-02-01 00:00:00', NULL, '0.00', 1, '2019-02-02 12:18:27'),
(81, NULL, NULL, '08a33a8cadfa69b6', '865720040677459', NULL, NULL, NULL, '2019-02-02 00:00:00', NULL, '0.00', 1, '2019-02-07 04:14:01'),
(82, NULL, NULL, '2cbc52046f95ffa6', '351905092289938', NULL, NULL, NULL, '2019-02-02 00:00:00', NULL, '0.00', 1, '2019-02-02 13:10:46'),
(83, NULL, NULL, 'b89d12ca4de051af', '352093093008471', NULL, NULL, NULL, '2019-02-03 00:00:00', NULL, '0.00', 1, '2019-02-03 02:08:35'),
(84, 'TRILOCHAN MAHATO', 'trilochanmahckp @gmail.com', '5b0a9b4418d4c2c7', '911565659745681', NULL, NULL, NULL, '2019-02-03 00:00:00', NULL, '0.00', 1, '2019-02-03 08:43:34'),
(85, NULL, NULL, 'd1333f46f0adccd5', '864625033928358', NULL, NULL, NULL, '2019-02-03 00:00:00', NULL, '0.00', 1, '2019-02-03 17:25:51'),
(86, 'Normasist', 'normadelaguilarios@outlook.com', 'df812622ea54efce', '863450034277601', NULL, NULL, NULL, '2019-02-04 00:00:00', NULL, '0.00', 1, '2019-02-04 01:49:01'),
(87, NULL, NULL, 'dd2cc374f8ed9eb2', '353327061309530', NULL, NULL, NULL, '2019-02-04 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(88, NULL, NULL, '47c1476e15cbb79c', '357392067472795', NULL, NULL, NULL, '2019-02-04 00:00:00', NULL, '0.00', 1, '2019-02-16 10:27:44'),
(89, NULL, NULL, 'a18d244aa956dc9c', '356133081448291', NULL, NULL, NULL, '2019-02-04 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(90, 'Monika', 'monikapal264@gmail.com', 'a0b8d30c403d5846', '911602252489704', NULL, NULL, NULL, '2019-02-05 00:00:00', NULL, '0.00', 1, '2019-02-05 01:14:49'),
(91, 'kingsley', 'papendiame@gmail.com', '64c7cbd5ced373e0', '358615172224894', NULL, NULL, NULL, '2019-02-05 00:00:00', NULL, '0.00', 1, '2019-02-05 20:01:44'),
(92, NULL, NULL, 'ba90dba719995568', '352762068064210', NULL, NULL, NULL, '2019-02-05 00:00:00', NULL, '0.00', 1, '2019-02-05 22:54:32'),
(93, NULL, NULL, '4d30c042808c2740', '863676039295319', NULL, NULL, NULL, '2019-02-07 00:00:00', NULL, '0.00', 1, '2019-02-07 08:45:26'),
(94, NULL, NULL, 'd4cdd7ff1249ddac', '866345047336807', NULL, NULL, NULL, '2019-02-07 00:00:00', NULL, '0.00', 1, '2019-02-07 12:32:22'),
(95, NULL, NULL, '6c19d97857c1b5a3', '868495039305670', NULL, NULL, NULL, '2019-02-07 00:00:00', NULL, '0.00', 1, '2019-02-07 15:46:24'),
(96, NULL, NULL, 'f122e68d5bca23ae', '358240051111110', NULL, NULL, NULL, '2019-02-08 00:00:00', NULL, '0.00', 1, '2019-02-18 06:58:01'),
(97, 'Curtis Romans', 'curtisr861@gmail.com', '35e6726251ea9436', '352346100225144', NULL, NULL, NULL, '2019-02-08 00:00:00', NULL, '0.00', 1, '2019-02-09 04:34:32'),
(98, NULL, NULL, '082b424fe4e1ec0c', '911643402051465', NULL, NULL, NULL, '2019-02-09 00:00:00', NULL, '0.00', 1, '2019-02-09 02:01:39'),
(99, 'silvia', 'silvia.bicciato@gmail.com ', '86894633a85e7f02', '353467105852648', NULL, NULL, NULL, '2019-02-09 00:00:00', NULL, '0.00', 1, '2019-02-09 02:14:44'),
(100, NULL, NULL, 'dac51e7914ef99a2', '353380074853366', NULL, NULL, NULL, '2019-02-09 00:00:00', NULL, '0.00', 1, '2019-02-09 06:19:45'),
(101, NULL, NULL, '323b401f6e8ae767', '864708038311265', NULL, NULL, NULL, '2019-02-10 00:00:00', NULL, '0.00', 1, '2019-02-10 00:03:00'),
(102, NULL, NULL, 'b4a7db52eb3aedc6', '359948070327934', NULL, NULL, NULL, '2019-02-10 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(103, NULL, NULL, '6cf6e1c595ccaec6', '351625071282619', NULL, NULL, NULL, '2019-02-10 00:00:00', NULL, '0.00', 1, '2019-02-10 14:09:18'),
(104, NULL, NULL, '55c79ae5d54fae6a', '352093095405691', NULL, NULL, NULL, '2019-02-11 00:00:00', NULL, '0.00', 1, '2019-02-11 08:43:11'),
(105, NULL, NULL, '1cf09d08ee34c203', '355266049090168', NULL, NULL, NULL, '2019-02-11 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(106, NULL, NULL, '42ba63ba17256041', '355266041852375', NULL, NULL, NULL, '2019-02-11 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(107, 'afifa', 'afifarshaikh', '181c35f9be6ba3a1', '359608082624435', NULL, NULL, NULL, '2019-02-11 00:00:00', NULL, '0.00', 1, '2019-02-11 18:53:41'),
(108, 'ilma', 'yllmafreitas1@gmail. com', 'dab9c82f8fa47f09', '354604087254281', NULL, NULL, NULL, '2019-02-11 00:00:00', NULL, '0.00', 1, '2019-02-11 23:03:57'),
(109, NULL, NULL, '85fb641d202f02fe', '358312061491798', NULL, NULL, NULL, '2019-02-12 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(110, NULL, NULL, 'da7cd12c823d4773', '355266042056075', NULL, NULL, NULL, '2019-02-12 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(111, NULL, NULL, 'a36bbe57cc880a9d', '990007010163902', NULL, NULL, NULL, '2019-02-12 00:00:00', NULL, '0.00', 1, '2019-02-12 17:05:06'),
(112, NULL, NULL, 'fe317479663a2a87', '357443086221695', NULL, NULL, NULL, '2019-02-12 00:00:00', NULL, '0.00', 1, '2019-02-12 21:52:09'),
(113, 'Rohit kumar', 'rohitkumar8287967923@gmail.com', 'cf3034fb409c2473', '911536250206779', NULL, NULL, NULL, '2019-02-13 00:00:00', NULL, '0.00', 1, '2019-02-13 09:29:12'),
(114, 'ramratansuthar770 ', 'ramratansuthar770@gmail. com', '4b6fe757c15d85c8', '355261073667627', NULL, NULL, NULL, '2019-02-13 00:00:00', NULL, '0.00', 1, '2019-02-13 12:47:51'),
(115, 'amrutha varshan ', 'amrutha.varshan.9@gmail.com ', 'd5adb85fd7c49441', '865449034984453', NULL, NULL, NULL, '2019-02-13 00:00:00', NULL, '0.00', 1, '2019-02-13 14:04:06'),
(116, NULL, NULL, '2a08079ede32f8c9', '862597038196533', NULL, NULL, NULL, '2019-02-14 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(117, 'mukul', 'md.jp.Nazmul @gmail.com', '3148ef211f3f983b', '357832073378965', NULL, NULL, NULL, '2019-02-14 00:00:00', NULL, '0.00', 1, '2019-02-14 15:00:29'),
(118, 'Travah UG', 'travah8@gmail.com', '7b3bb88830d993a3', '350789909567780', NULL, NULL, NULL, '2019-02-14 00:00:00', NULL, '0.00', 1, '2019-02-14 15:05:42'),
(119, 'sanjay', 'sanjaypanara11@gmail.com', '7c6cfd297dbb94f4', '358223074825833', NULL, NULL, NULL, '2019-02-15 00:00:00', NULL, '0.00', 1, '2019-02-15 05:46:38'),
(120, NULL, NULL, '5c154069d6e39fc7', '353328090070200', NULL, NULL, NULL, '2019-02-15 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(121, NULL, NULL, '66e18c0fb935fb6e', '356817077829965', NULL, NULL, NULL, '2019-02-15 00:00:00', NULL, '0.00', 1, '2019-02-15 10:49:25'),
(122, NULL, NULL, 'c4aabf2344360204', '868920021097720', NULL, NULL, NULL, '2019-02-15 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(123, NULL, NULL, 'f4a43b92cf4cbcc5', '357926069457179', NULL, NULL, NULL, '2019-02-15 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(124, NULL, NULL, 'de1fd5043ff69222', '355259076457459', NULL, NULL, NULL, '2019-02-15 00:00:00', NULL, '0.00', 1, '2019-02-15 15:21:07'),
(125, 'reza', 'rezaalinezxad90@gmail.com', '4ac77deabd0e318a', '357656087069181', NULL, NULL, NULL, '2019-02-15 00:00:00', NULL, '0.00', 1, '2019-02-15 20:46:49'),
(126, NULL, NULL, 'ed46bcbc2f3f88a9', '352024091088900', NULL, NULL, NULL, '2019-02-16 00:00:00', NULL, '0.00', 1, '2019-02-16 06:41:32'),
(127, 'Manasi Bhattacharya', 'manasibhattacharya2018@gmail.com', '2257d4ef25afd0b1', '869306031617195', NULL, NULL, NULL, '2019-02-16 00:00:00', NULL, '0.00', 1, '2019-02-16 08:02:08'),
(128, NULL, NULL, 'c3fed94852b6ae99', '865550039493117', NULL, NULL, NULL, '2019-02-16 00:00:00', NULL, '0.00', 1, '2019-02-16 11:24:33'),
(129, NULL, NULL, '079fa14eaabbe9b5', '357220070873347', NULL, NULL, NULL, '2019-02-16 00:00:00', NULL, '0.00', 1, '2019-02-16 13:39:39'),
(130, NULL, NULL, '73d3cdfb0954f74c', '356513088801297', NULL, NULL, NULL, '2019-02-16 00:00:00', NULL, '0.00', 1, '2019-02-16 17:07:24'),
(131, NULL, NULL, 'a881a407854ab1e8', '359767071010665', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 00:34:11'),
(132, 'Biswas Alok', 'alokbiswas378@gmail.com', '9e6f28cf9f1e2648', '868565030612207', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 06:04:04'),
(133, NULL, NULL, '2727adc3247be052', '354912070937162', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(134, NULL, NULL, '8dbf4a46cde08a78', '357344070315958', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 09:20:31'),
(135, 'mahdi ', 'mahdijavid380@gmail.com', 'b0b33874c0aac958', '357002076790331', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 12:59:58'),
(136, 'Ram', 'Ram7623@gmail.com', '95448c0753edf298', '867344036983694', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 15:20:15'),
(137, 'Sudarsh', 'sudarshrsingh@gmail.com', 'ac6aaf82d7df920a', '865570030054254', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 17:22:37'),
(138, NULL, NULL, '587667297709370d', '356020086588843', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 17:36:48'),
(139, NULL, NULL, '0d3168c6be5f1333', '355726096192241', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 17:53:27'),
(140, 'Ameyaw K.Junior', 'ameyaw.k.junior.2017@gmail.com', 'af7f609839af6001', '358078090376785', NULL, NULL, NULL, '2019-02-17 00:00:00', NULL, '0.00', 1, '2019-02-17 22:38:19'),
(141, NULL, NULL, 'b2801261ebbdae92', '355266048792251', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '2019-02-18 07:11:04'),
(142, NULL, NULL, 'cdf10403da7c56ff', '12345678902468', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(143, NULL, NULL, 'f238a95e9aea93cf', '355266045174982', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(144, NULL, NULL, 'ae86aa0136e55559', '355266042248805', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '2019-02-18 08:00:28'),
(145, NULL, NULL, '4695f5a7e2f2ffaa', '354267096909384', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(146, NULL, NULL, '3da22fd850965f77', '355266044520706', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(147, NULL, NULL, 'd3a67e4cb77533da', '35753708076935', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(148, NULL, NULL, '34c63bc20c9c15b2', '358354082022384', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(149, NULL, NULL, '4366461f92513537', '356446050824346', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(150, NULL, NULL, 'bfd896c60a77f919', '12345678902468', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(151, NULL, NULL, 'f37978df01d105a0', '867981020311091', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '2019-02-18 08:33:02'),
(152, NULL, NULL, 'a88e17fcf15d71d4', '12345678902468', NULL, NULL, NULL, '2019-02-18 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(153, 'sanjay chauhan', 'sanjuhotboy@gmail.com\n', 'cecb66994ed631dd', '865639030984677', NULL, NULL, NULL, '2019-02-19 00:00:00', NULL, '0.00', 1, '2019-02-19 07:14:20'),
(154, NULL, NULL, 'c35ddbd45f8ab8c0', '353117063117266', NULL, NULL, NULL, '2019-02-19 00:00:00', NULL, '0.00', 1, '2019-02-19 09:33:25'),
(155, 'sethupathi', 'kpsethupathi786@gmail.com', 'd0cc8f28647ce52e', '861736037328785', NULL, NULL, NULL, '2019-02-19 00:00:00', NULL, '0.00', 1, '2019-02-23 16:28:26'),
(156, 'Jaylen ', 'jaylensimpsonjs@gmail.com ', '8a05781986ce9821', '359500092464052', NULL, NULL, NULL, '2019-02-19 00:00:00', NULL, '0.00', 1, '2019-02-19 22:59:13'),
(157, NULL, NULL, 'a0e5147fff30aa33', '355266048546327', NULL, NULL, NULL, '2019-02-19 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(158, NULL, NULL, 'c2ea99ace832f9d6', '355266047305196', NULL, NULL, NULL, '2019-02-20 00:00:00', NULL, '0.00', 1, '2019-02-20 06:23:55'),
(159, 'Winston john Gaoiran', 'winstonjohngaoiran10@gmail.com', 'e27ce2b645dfccf1', '861933049925050', NULL, NULL, NULL, '2019-02-20 00:00:00', NULL, '0.00', 1, '2019-02-20 10:47:56'),
(160, NULL, NULL, '657c5c1347e3388d', '861957032571035', NULL, NULL, NULL, '2019-02-20 00:00:00', NULL, '0.00', 1, '2019-02-20 18:35:26'),
(161, 'rajatest', 'rajatest@gmail.com', '', '', '+91', '8521475212', NULL, '2019-02-21 00:00:00', NULL, '0.00', 1, '2019-02-21 06:12:25'),
(163, NULL, NULL, '', '', '+91', '7511452589', NULL, '2019-02-21 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(166, 'RajaMobile ', 'raja@gmail.com', '', '', '+91', '8122405019', NULL, '2019-02-21 00:00:00', NULL, '0.00', 1, '2019-02-23 13:01:23'),
(167, NULL, NULL, '', '', '+91', '9600220705', NULL, '2019-02-21 00:00:00', NULL, '0.00', 1, '2019-02-21 12:45:12'),
(168, NULL, NULL, '', '', '+91', '9565421052', NULL, '2019-02-21 00:00:00', NULL, '0.00', 1, '2019-02-21 13:20:50'),
(169, 'Raja', NULL, '', '', '+91', '15555215556', NULL, '2019-02-21 00:00:00', NULL, '0.00', 1, '2019-02-21 13:20:51'),
(170, NULL, NULL, '', '', '', '', NULL, '2019-02-21 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(171, 'Karthick', NULL, '', '', '+91', '15555215554', NULL, '2019-02-22 00:00:00', NULL, '0.00', 1, '2019-02-23 12:17:59'),
(172, NULL, NULL, '', '', '+91', '9856251452', NULL, '2019-02-22 00:00:00', NULL, '0.00', 1, '2019-02-23 10:47:20'),
(173, NULL, NULL, '', '', '+91', '7010037451', NULL, '2019-02-23 00:00:00', NULL, '0.00', 1, '2019-02-24 03:35:22'),
(174, 'Mohamed Rahmathullah', 'nafsha.mohamed@gmail.com', '', '', '+971', '559887887', NULL, '2019-02-24 00:00:00', NULL, '0.00', 1, '2019-02-24 16:35:53'),
(175, NULL, NULL, '', '', '+1', '8327161286', NULL, '2019-02-24 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(176, NULL, NULL, '', '', '+91', '9894695840', NULL, '2019-02-24 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(178, 'waheed', 'waheed.rahuman@gmail.com\n', '', '', '+971', '508150115', NULL, '2019-02-24 00:00:00', NULL, '0.00', 1, '2019-02-24 16:10:39'),
(179, 'Lavazza', 'test@test.com', '', '', '+971', '508109372', NULL, '2019-02-24 00:00:00', NULL, '0.00', 1, '2019-02-24 18:47:16'),
(180, NULL, NULL, '', '', '+91', '7373739837', NULL, '2019-02-24 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00'),
(181, NULL, NULL, '', '', '+971', '0566673708', NULL, '2019-02-24 00:00:00', NULL, '0.00', 1, '2019-02-24 17:54:09'),
(182, 'ggh', 'tyyy', '', '', '+971', '0508109372', NULL, '2019-02-24 00:00:00', NULL, '0.00', 1, '2019-02-24 19:12:26'),
(183, NULL, NULL, '', '', '+971', '963258074', NULL, '2019-02-26 00:00:00', NULL, '0.00', 1, '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_verification_code`
--

CREATE TABLE `tbl_verification_code` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT '0',
  `verification_code` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_verification_code`
--

INSERT INTO `tbl_verification_code` (`id`, `user_id`, `verification_code`, `created_date`, `status`) VALUES
(1, 0, 102910, NULL, 1),
(2, 13, 101441, NULL, 0),
(3, 14, 100615, '2019-02-26 06:24:20', 1),
(4, 15, 108091, '2018-11-16 09:54:06', 0),
(5, 16, 105906, '2018-11-16 09:55:13', 0),
(6, 17, 106956, '2018-11-16 09:55:23', 1),
(7, 18, 103850, '2018-11-16 11:13:10', 1),
(8, 19, 101513, '2018-11-16 11:26:19', 1),
(9, 20, 101534, '2018-11-16 11:27:30', 1),
(10, 21, 102022, '2018-11-17 10:38:09', 0),
(11, 22, 100329, '2019-02-23 14:35:35', 0),
(12, 23, 103486, '2019-02-23 14:34:33', 0),
(13, 24, 107846, '2018-12-26 07:32:56', 1),
(14, 25, 101683, '2018-12-25 17:40:45', 1),
(15, 26, 100427, '2019-02-24 04:26:52', 0),
(16, 27, 100544, '2019-01-04 07:46:36', 1),
(17, 161, 101432, '2019-02-21 05:16:20', 0),
(18, 162, 108600, '2019-02-21 06:35:17', 0),
(19, 163, 105997, '2019-02-21 06:36:26', 1),
(20, 164, 103168, '2019-02-21 06:37:40', 1),
(21, 165, 102321, '2019-02-21 06:38:26', 0),
(22, 166, 100386, '2019-02-23 12:57:28', 0),
(23, 167, 108766, '2019-02-21 12:44:42', 0),
(24, 168, 105981, '2019-02-21 13:16:34', 0),
(25, 169, 100429, '2019-02-21 13:19:21', 0),
(26, 170, 104098, '2019-02-23 20:14:38', 1),
(27, 171, 105637, '2019-02-22 05:39:48', 0),
(28, 172, 102930, '2019-02-22 05:41:10', 0),
(29, 173, 105301, '2019-02-24 03:34:56', 0),
(30, 174, 102615, '2019-02-24 15:19:35', 0),
(31, 175, 106539, '2019-02-24 15:44:27', 1),
(32, 176, 107926, '2019-02-24 15:45:12', 1),
(33, 177, 103228, '2019-02-24 15:51:10', 0),
(34, 178, 108653, '2019-02-24 16:08:59', 0),
(35, 179, 106267, '2019-02-24 16:26:35', 0),
(36, 180, 102253, '2019-02-24 17:30:33', 1),
(37, 181, 108279, '2019-02-24 17:33:21', 0),
(38, 182, 106012, '2019-02-24 18:49:12', 0),
(39, 183, 101230, '2019-02-26 06:25:41', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_admin_email_ids`
--
ALTER TABLE `tbl_admin_email_ids`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_country`
--
ALTER TABLE `tbl_country`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_notifications`
--
ALTER TABLE `tbl_notifications`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_support`
--
ALTER TABLE `tbl_support`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_verification_code`
--
ALTER TABLE `tbl_verification_code`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_admin_email_ids`
--
ALTER TABLE `tbl_admin_email_ids`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_country`
--
ALTER TABLE `tbl_country`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_notifications`
--
ALTER TABLE `tbl_notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `tbl_support`
--
ALTER TABLE `tbl_support`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=184;

--
-- AUTO_INCREMENT for table `tbl_verification_code`
--
ALTER TABLE `tbl_verification_code`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
