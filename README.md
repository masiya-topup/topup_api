# Topup Project

## Database setup
* MySQL server with credentials (root/admin)
```sh
$ yum update
$ rpm -Uvh https://mirror.webtatic.com/yum/el6/latest.rpm
$ yum install mysql55w mysql55w-server
$ chkconfig --levels 235 mysqld on
$ service mysqld start
$ mysql -h localhost -u root
mysql> SET PASSWORD FOR root@localhost = PASSWORD('admin');
$ mysql -h localhost -u root -padmin
```
SQL Setup
```sql
--
-- Database: `topup`
--

-- --------------------------------------------------------

--
-- Table structure for table `Category`
--

CREATE TABLE IF NOT EXISTS `Category` (
  `CategoryId_int` int(11) unsigned NOT NULL,
  `CompanyId_int` int(11) unsigned NOT NULL,
  `CountryId_int` int(11) unsigned NOT NULL,
  `CategoryName_vchr` varchar(45) NOT NULL,
  `CategoryDesc_vchr` varchar(255) DEFAULT NULL,
  `CategoryDate_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Company`
--

CREATE TABLE IF NOT EXISTS `Company` (
  `CompanyId_int` int(11) unsigned NOT NULL,
  `CountryId_int` int(11) unsigned NOT NULL,
  `CompanyName_vchr` varchar(45) NOT NULL,
  `CompanyDesc_vchr` varchar(255) DEFAULT NULL,
  `CompanyDate_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Country`
--

CREATE TABLE IF NOT EXISTS `Country` (
  `CountryId_int` int(11) NOT NULL,
  `CountryCode_chr` char(2) NOT NULL DEFAULT '',
  `CountryName_vchr` varchar(45) NOT NULL DEFAULT '',
  `CurrencyCode_chr` char(3) DEFAULT NULL,
  `Capital_vchr` varchar(30) DEFAULT NULL,
  `Continent_chr` char(2) DEFAULT NULL,
  `Area_vchr` varchar(20) DEFAULT NULL,
  `Languages_vchr` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `EventLog`
--

CREATE TABLE IF NOT EXISTS `EventLog` (
  `EventLogId_int` int(11) unsigned NOT NULL,
  `UserId_int` int(11) unsigned DEFAULT NULL,
  `OwnerId_int` int(11) unsigned DEFAULT NULL COMMENT 'User Id of Action performer',
  `EventLogType_enm` enum('api','customercare','user') NOT NULL,
  `EventLogTitle_vchr` varchar(45) NOT NULL,
  `EventLogDetails_vchr` mediumtext NOT NULL,
  `EventLogDate_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `PasswordReset`
--

CREATE TABLE IF NOT EXISTS `PasswordReset` (
  `PasswordResetID_int` int(11) unsigned NOT NULL,
  `UserID_int` int(11) unsigned NOT NULL,
  `PasswordResetHash_vchr` varchar(80) NOT NULL DEFAULT '1',
  `PasswordResetDate_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Service`
--

CREATE TABLE IF NOT EXISTS `Service` (
  `ServiceId_int` int(11) unsigned NOT NULL,
  `CompanyId_int` int(11) unsigned NOT NULL,
  `CategoryId_int` int(11) unsigned NOT NULL,
  `ServiceName_vchr` varchar(45) NOT NULL,
  `ServiceDesc_vchr` varchar(255) DEFAULT NULL,
  `APIURL_vchr` varchar(255) DEFAULT NULL,
  `ServiceDate_dt` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Ticket`
--

CREATE TABLE IF NOT EXISTS `Ticket` (
  `TicketId_int` int(11) unsigned NOT NULL,
  `UserId_int` int(11) unsigned NOT NULL,
  `OwnerId_int` int(11) unsigned NOT NULL,
  `TicketTitle_vchr` varchar(45) NOT NULL,
  `TicketDesc_vchr` varchar(255) NOT NULL,
  `TicketOpenDate_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TicketCloseDate_dt` timestamp NULL DEFAULT NULL,
  `TicketStatus_enm` enum('open','closed','new','in-progress','resolved','un-resolved') NOT NULL DEFAULT 'new'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `TicketHistory`
--

CREATE TABLE IF NOT EXISTS `TicketHistory` (
  `TicketHistoryId_int` int(11) unsigned NOT NULL,
  `TicketId_int` int(11) unsigned NOT NULL,
  `UserId_int` int(11) unsigned NOT NULL,
  `OwnerId_int` int(11) unsigned NOT NULL,
  `ModifierId_int` int(11) unsigned NOT NULL,
  `TicketTitle_vchr` varchar(45) NOT NULL,
  `TicketDesc_vchr` varchar(255) NOT NULL,
  `TicketOpenDate_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TicketCloseDate_dt` timestamp NULL DEFAULT NULL,
  `TicketStatus_enm` enum('open','close','new','in-progress','resolved','un-resolved') NOT NULL DEFAULT 'new'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Transaction`
--

CREATE TABLE IF NOT EXISTS `Transaction` (
  `TransactionId_int` int(11) unsigned NOT NULL,
  `CompanyId_int` int(11) unsigned NOT NULL,
  `CategoryId_int` int(11) unsigned NOT NULL,
  `ServiceId_int` int(11) unsigned NOT NULL,
  `UserId_int` int(11) unsigned DEFAULT NULL,
  `UserPhoneId_int` int(11) unsigned DEFAULT NULL,
  `TransactionType_enm` enum('creditcard','cash') NOT NULL DEFAULT 'creditcard',
  `TransactionSystem_enm` enum('VISA','MasterCard','KNET') DEFAULT 'KNET',
  `TransactionAmount_dbl` double NOT NULL DEFAULT '0',
  `TransactionRefId_vchr` varchar(150) DEFAULT NULL,
  `TransactionTrackId_vchr` varchar(150) DEFAULT NULL,
  `TransactionPaymentId_vchr` varchar(45) DEFAULT NULL,
  `TransactionDate_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TransactionStatus_enm` enum('transit','success','failed') NOT NULL DEFAULT 'transit',
  `UserEMail_vchr` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `UserId_int` int(11) unsigned NOT NULL,
  `CountryId_int` int(11) unsigned NOT NULL,
  `UserLogin_vchr` varchar(45) NOT NULL,
  `UserPassword_vchr` varchar(45) NOT NULL DEFAULT 'changeme',
  `UserPasswordSHA256_vchr` varchar(100) NOT NULL DEFAULT 'SHA2(''changeme'', 256)',
  `UserFirstName_vchr` varchar(45) DEFAULT NULL,
  `UserLastName_vchr` varchar(45) DEFAULT NULL,
  `UserEMail_vchr` varchar(100) NOT NULL,
  `UserGender_enm` enum('male','female') DEFAULT 'male',
  `UserRole_enm` enum('user','admin_all','admin_customer','admin_finance') NOT NULL DEFAULT 'user',
  `UserBirthDate_dt` date NOT NULL,
  `UserRegistrationDate_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UserAddress_vchr` varchar(255) DEFAULT NULL,
  `UserStatus_bit` bit(1) DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `UserAuth`
--

CREATE TABLE IF NOT EXISTS `UserAuth` (
  `UserAuthId_int` int(11) unsigned NOT NULL,
  `UserId_int` int(11) unsigned NOT NULL,
  `UserAuthDate_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UserAuthToken_vchr` varchar(45) NOT NULL,
  `UserAuthStatus_bit` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `UserPhone`
--

CREATE TABLE IF NOT EXISTS `UserPhone` (
  `UserPhoneId_int` int(11) unsigned NOT NULL,
  `UserId_int` int(11) unsigned DEFAULT NULL,
  `UserPhoneNo_vchr` varchar(32) NOT NULL,
  `UserPhoneType_enm` enum('prepaid','postpaid') DEFAULT 'prepaid',
  `UserPhoneDate_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Category`
--
ALTER TABLE `Category`
  ADD PRIMARY KEY (`CategoryId_int`),
  ADD KEY `fk_country_id_idx` (`CountryId_int`),
  ADD KEY `fk_cat_company_id_idx` (`CompanyId_int`);

--
-- Indexes for table `Company`
--
ALTER TABLE `Company`
  ADD PRIMARY KEY (`CompanyId_int`);

--
-- Indexes for table `Country`
--
ALTER TABLE `Country`
  ADD PRIMARY KEY (`CountryId_int`);

--
-- Indexes for table `EventLog`
--
ALTER TABLE `EventLog`
  ADD PRIMARY KEY (`EventLogId_int`);

--
-- Indexes for table `PasswordReset`
--
ALTER TABLE `PasswordReset`
  ADD PRIMARY KEY (`PasswordResetID_int`);

--
-- Indexes for table `Service`
--
ALTER TABLE `Service`
  ADD PRIMARY KEY (`ServiceId_int`),
  ADD KEY `fk_company_id_idx` (`CompanyId_int`),
  ADD KEY `fk_svc_company_id_idx` (`CompanyId_int`),
  ADD KEY `fk_svc_category_id_idx` (`CategoryId_int`);

--
-- Indexes for table `Ticket`
--
ALTER TABLE `Ticket`
  ADD PRIMARY KEY (`TicketId_int`);

--
-- Indexes for table `TicketHistory`
--
ALTER TABLE `TicketHistory`
  ADD PRIMARY KEY (`TicketHistoryId_int`);

--
-- Indexes for table `Transaction`
--
ALTER TABLE `Transaction`
  ADD PRIMARY KEY (`TransactionId_int`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`UserId_int`),
  ADD UNIQUE KEY `user_login_UNIQUE` (`UserLogin_vchr`),
  ADD UNIQUE KEY `UserEMail_vchr_UNIQUE` (`UserEMail_vchr`);

--
-- Indexes for table `UserAuth`
--
ALTER TABLE `UserAuth`
  ADD PRIMARY KEY (`UserAuthId_int`),
  ADD UNIQUE KEY `user_token_UNIQUE` (`UserAuthToken_vchr`),
  ADD KEY `fk_auth_user_idx` (`UserId_int`);

--
-- Indexes for table `UserPhone`
--
ALTER TABLE `UserPhone`
  ADD PRIMARY KEY (`UserPhoneId_int`),
  ADD UNIQUE KEY `UserPhoneNo_vchr_UNIQUE` (`UserPhoneNo_vchr`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Category`
--
ALTER TABLE `Category`
  MODIFY `CategoryId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Company`
--
ALTER TABLE `Company`
  MODIFY `CompanyId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Country`
--
ALTER TABLE `Country`
  MODIFY `CountryId_int` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `EventLog`
--
ALTER TABLE `EventLog`
  MODIFY `EventLogId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `PasswordReset`
--
ALTER TABLE `PasswordReset`
  MODIFY `PasswordResetID_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Service`
--
ALTER TABLE `Service`
  MODIFY `ServiceId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Ticket`
--
ALTER TABLE `Ticket`
  MODIFY `TicketId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `TicketHistory`
--
ALTER TABLE `TicketHistory`
  MODIFY `TicketHistoryId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Transaction`
--
ALTER TABLE `Transaction`
  MODIFY `TransactionId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
  MODIFY `UserId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `UserAuth`
--
ALTER TABLE `UserAuth`
  MODIFY `UserAuthId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `UserPhone`
--
ALTER TABLE `UserPhone`
  MODIFY `UserPhoneId_int` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Category`
--
ALTER TABLE `Category`
  ADD CONSTRAINT `fk_cat_company_id` FOREIGN KEY (`CompanyId_int`) REFERENCES `Company` (`CompanyId_int`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `Service`
--
ALTER TABLE `Service`
  ADD CONSTRAINT `fk_svc_category_id` FOREIGN KEY (`CategoryId_int`) REFERENCES `Category` (`CategoryId_int`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_svc_company_id` FOREIGN KEY (`CompanyId_int`) REFERENCES `Company` (`CompanyId_int`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `UserAuth`
--
ALTER TABLE `UserAuth`
  ADD CONSTRAINT `fk_auth_user` FOREIGN KEY (`UserId_int`) REFERENCES `User` (`UserId_int`) ON DELETE CASCADE ON UPDATE NO ACTION;

INSERT INTO `Country` (`CountryId_int`, `CountryCode_chr`, `CountryName_vchr`, `CurrencyCode_chr`, `Capital_vchr`, `Continent_chr`, `Area_vchr`, `Languages_vchr`) VALUES
(1, 'AD', 'Andorra', 'EUR', 'Andorra la Vella', 'EU', '468,0', 'ca'),
(2, 'AE', 'United Arab Emirates', 'AED', 'Abu Dhabi', 'AS', '82880,0', 'ar-AE,fa,en,hi,ur'),
(3, 'AF', 'Afghanistan', 'AFN', 'Kabul', 'AS', '647500,0', 'fa-AF,ps,uz-AF,tk'),
(4, 'AG', 'Antigua and Barbuda', 'XCD', 'St. John''s', 'NA', '443,0', 'en-AG'),
(5, 'AI', 'Anguilla', 'XCD', 'The Valley', 'NA', '102,0', 'en-AI'),
(6, 'AL', 'Albania', 'ALL', 'Tirana', 'EU', '28748,0', 'sq,el'),
(7, 'AM', 'Armenia', 'AMD', 'Yerevan', 'AS', '29800,0', 'hy'),
(8, 'AO', 'Angola', 'AOA', 'Luanda', 'AF', '1246700,0', 'pt-AO'),
(9, 'AQ', 'Antarctica', '', '', 'AN', '1,4E7', ''),
(10, 'AR', 'Argentina', 'ARS', 'Buenos Aires', 'SA', '2766890,0', 'es-AR,en,it,de,fr,gn'),
(11, 'AS', 'American Samoa', 'USD', 'Pago Pago', 'OC', '199,0', 'en-AS,sm,to'),
(12, 'AT', 'Austria', 'EUR', 'Vienna', 'EU', '83858,0', 'de-AT,hr,hu,sl'),
(13, 'AU', 'Australia', 'AUD', 'Canberra', 'OC', '7686850,0', 'en-AU'),
(14, 'AW', 'Aruba', 'AWG', 'Oranjestad', 'NA', '193,0', 'nl-AW,es,en'),
(15, 'AX', 'Åland', 'EUR', 'Mariehamn', 'EU', '1580,0', 'sv-AX'),
(16, 'AZ', 'Azerbaijan', 'AZN', 'Baku', 'AS', '86600,0', 'az,ru,hy'),
(17, 'BA', 'Bosnia and Herzegovina', 'BAM', 'Sarajevo', 'EU', '51129,0', 'bs,hr-BA,sr-BA'),
(18, 'BB', 'Barbados', 'BBD', 'Bridgetown', 'NA', '431,0', 'en-BB'),
(19, 'BD', 'Bangladesh', 'BDT', 'Dhaka', 'AS', '144000,0', 'bn-BD,en'),
(20, 'BE', 'Belgium', 'EUR', 'Brussels', 'EU', '30510,0', 'nl-BE,fr-BE,de-BE'),
(21, 'BF', 'Burkina Faso', 'XOF', 'Ouagadougou', 'AF', '274200,0', 'fr-BF'),
(22, 'BG', 'Bulgaria', 'BGN', 'Sofia', 'EU', '110910,0', 'bg,tr-BG,rom'),
(23, 'BH', 'Bahrain', 'BHD', 'Manama', 'AS', '665,0', 'ar-BH,en,fa,ur'),
(24, 'BI', 'Burundi', 'BIF', 'Bujumbura', 'AF', '27830,0', 'fr-BI,rn'),
(25, 'BJ', 'Benin', 'XOF', 'Porto-Novo', 'AF', '112620,0', 'fr-BJ'),
(26, 'BL', 'Saint Barthélemy', 'EUR', 'Gustavia', 'NA', '21,0', 'fr'),
(27, 'BM', 'Bermuda', 'BMD', 'Hamilton', 'NA', '53,0', 'en-BM,pt'),
(28, 'BN', 'Brunei', 'BND', 'Bandar Seri Begawan', 'AS', '5770,0', 'ms-BN,en-BN'),
(29, 'BO', 'Bolivia', 'BOB', 'Sucre', 'SA', '1098580,0', 'es-BO,qu,ay'),
(30, 'BQ', 'Bonaire', 'USD', '', 'NA', '328,0', 'nl,pap,en'),
(31, 'BR', 'Brazil', 'BRL', 'Brasília', 'SA', '8511965,0', 'pt-BR,es,en,fr'),
(32, 'BS', 'Bahamas', 'BSD', 'Nassau', 'NA', '13940,0', 'en-BS'),
(33, 'BT', 'Bhutan', 'BTN', 'Thimphu', 'AS', '47000,0', 'dz'),
(34, 'BV', 'Bouvet Island', 'NOK', '', 'AN', '49,0', ''),
(35, 'BW', 'Botswana', 'BWP', 'Gaborone', 'AF', '600370,0', 'en-BW,tn-BW'),
(36, 'BY', 'Belarus', 'BYR', 'Minsk', 'EU', '207600,0', 'be,ru'),
(37, 'BZ', 'Belize', 'BZD', 'Belmopan', 'NA', '22966,0', 'en-BZ,es'),
(38, 'CA', 'Canada', 'CAD', 'Ottawa', 'NA', '9984670,0', 'en-CA,fr-CA,iu'),
(39, 'CC', 'Cocos [Keeling] Islands', 'AUD', 'West Island', 'AS', '14,0', 'ms-CC,en'),
(40, 'CD', 'Democratic Republic of the Congo', 'CDF', 'Kinshasa', 'AF', '2345410,0', 'fr-CD,ln,kg'),
(41, 'CF', 'Central African Republic', 'XAF', 'Bangui', 'AF', '622984,0', 'fr-CF,sg,ln,kg'),
(42, 'CG', 'Republic of the Congo', 'XAF', 'Brazzaville', 'AF', '342000,0', 'fr-CG,kg,ln-CG'),
(43, 'CH', 'Switzerland', 'CHF', 'Berne', 'EU', '41290,0', 'de-CH,fr-CH,it-CH,rm'),
(44, 'CI', 'Ivory Coast', 'XOF', 'Yamoussoukro', 'AF', '322460,0', 'fr-CI'),
(45, 'CK', 'Cook Islands', 'NZD', 'Avarua', 'OC', '240,0', 'en-CK,mi'),
(46, 'CL', 'Chile', 'CLP', 'Santiago', 'SA', '756950,0', 'es-CL'),
(47, 'CM', 'Cameroon', 'XAF', 'Yaoundé', 'AF', '475440,0', 'en-CM,fr-CM'),
(48, 'CN', 'China', 'CNY', 'Beijing', 'AS', '9596960,0', 'zh-CN,yue,wuu,dta,ug,za'),
(49, 'CO', 'Colombia', 'COP', 'Bogotá', 'SA', '1138910,0', 'es-CO'),
(50, 'CR', 'Costa Rica', 'CRC', 'San José', 'NA', '51100,0', 'es-CR,en'),
(51, 'CU', 'Cuba', 'CUP', 'Havana', 'NA', '110860,0', 'es-CU'),
(52, 'CV', 'Cape Verde', 'CVE', 'Praia', 'AF', '4033,0', 'pt-CV'),
(53, 'CW', 'Curacao', 'ANG', 'Willemstad', 'NA', '444,0', 'nl,pap'),
(54, 'CX', 'Christmas Island', 'AUD', 'The Settlement', 'AS', '135,0', 'en,zh,ms-CC'),
(55, 'CY', 'Cyprus', 'EUR', 'Nicosia', 'EU', '9250,0', 'el-CY,tr-CY,en'),
(56, 'CZ', 'Czech Republic', 'CZK', 'Prague', 'EU', '78866,0', 'cs,sk'),
(57, 'DE', 'Germany', 'EUR', 'Berlin', 'EU', '357021,0', 'de'),
(58, 'DJ', 'Djibouti', 'DJF', 'Djibouti', 'AF', '23000,0', 'fr-DJ,ar,so-DJ,aa'),
(59, 'DK', 'Denmark', 'DKK', 'Copenhagen', 'EU', '43094,0', 'da-DK,en,fo,de-DK'),
(60, 'DM', 'Dominica', 'XCD', 'Roseau', 'NA', '754,0', 'en-DM'),
(61, 'DO', 'Dominican Republic', 'DOP', 'Santo Domingo', 'NA', '48730,0', 'es-DO'),
(62, 'DZ', 'Algeria', 'DZD', 'Algiers', 'AF', '2381740,0', 'ar-DZ'),
(63, 'EC', 'Ecuador', 'USD', 'Quito', 'SA', '283560,0', 'es-EC'),
(64, 'EE', 'Estonia', 'EUR', 'Tallinn', 'EU', '45226,0', 'et,ru'),
(65, 'EG', 'Egypt', 'EGP', 'Cairo', 'AF', '1001450,0', 'ar-EG,en,fr'),
(66, 'EH', 'Western Sahara', 'MAD', 'El Aaiún', 'AF', '266000,0', 'ar,mey'),
(67, 'ER', 'Eritrea', 'ERN', 'Asmara', 'AF', '121320,0', 'aa-ER,ar,tig,kun,ti-ER'),
(68, 'ES', 'Spain', 'EUR', 'Madrid', 'EU', '504782,0', 'es-ES,ca,gl,eu,oc'),
(69, 'ET', 'Ethiopia', 'ETB', 'Addis Ababa', 'AF', '1127127,0', 'am,en-ET,om-ET,ti-ET,so-ET,sid'),
(70, 'FI', 'Finland', 'EUR', 'Helsinki', 'EU', '337030,0', 'fi-FI,sv-FI,smn'),
(71, 'FJ', 'Fiji', 'FJD', 'Suva', 'OC', '18270,0', 'en-FJ,fj'),
(72, 'FK', 'Falkland Islands', 'FKP', 'Stanley', 'SA', '12173,0', 'en-FK'),
(73, 'FM', 'Micronesia', 'USD', 'Palikir', 'OC', '702,0', 'en-FM,chk,pon,yap,kos,uli,woe,nkr,kpg'),
(74, 'FO', 'Faroe Islands', 'DKK', 'Tórshavn', 'EU', '1399,0', 'fo,da-FO'),
(75, 'FR', 'France', 'EUR', 'Paris', 'EU', '547030,0', 'fr-FR,frp,br,co,ca,eu,oc'),
(76, 'GA', 'Gabon', 'XAF', 'Libreville', 'AF', '267667,0', 'fr-GA'),
(77, 'GB', 'United Kingdom', 'GBP', 'London', 'EU', '244820,0', 'en-GB,cy-GB,gd'),
(78, 'GD', 'Grenada', 'XCD', 'St. George''s', 'NA', '344,0', 'en-GD'),
(79, 'GE', 'Georgia', 'GEL', 'Tbilisi', 'AS', '69700,0', 'ka,ru,hy,az'),
(80, 'GF', 'French Guiana', 'EUR', 'Cayenne', 'SA', '91000,0', 'fr-GF'),
(81, 'GG', 'Guernsey', 'GBP', 'St Peter Port', 'EU', '78,0', 'en,fr'),
(82, 'GH', 'Ghana', 'GHS', 'Accra', 'AF', '239460,0', 'en-GH,ak,ee,tw'),
(83, 'GI', 'Gibraltar', 'GIP', 'Gibraltar', 'EU', '6,5', 'en-GI,es,it,pt'),
(84, 'GL', 'Greenland', 'DKK', 'Nuuk', 'NA', '2166086,0', 'kl,da-GL,en'),
(85, 'GM', 'Gambia', 'GMD', 'Banjul', 'AF', '11300,0', 'en-GM,mnk,wof,wo,ff'),
(86, 'GN', 'Guinea', 'GNF', 'Conakry', 'AF', '245857,0', 'fr-GN'),
(87, 'GP', 'Guadeloupe', 'EUR', 'Basse-Terre', 'NA', '1780,0', 'fr-GP'),
(88, 'GQ', 'Equatorial Guinea', 'XAF', 'Malabo', 'AF', '28051,0', 'es-GQ,fr'),
(89, 'GR', 'Greece', 'EUR', 'Athens', 'EU', '131940,0', 'el-GR,en,fr'),
(90, 'GS', 'South Georgia and the South Sandwich Islands', 'GBP', 'Grytviken', 'AN', '3903,0', 'en'),
(91, 'GT', 'Guatemala', 'GTQ', 'Guatemala City', 'NA', '108890,0', 'es-GT'),
(92, 'GU', 'Guam', 'USD', 'Hagåtña', 'OC', '549,0', 'en-GU,ch-GU'),
(93, 'GW', 'Guinea-Bissau', 'XOF', 'Bissau', 'AF', '36120,0', 'pt-GW,pov'),
(94, 'GY', 'Guyana', 'GYD', 'Georgetown', 'SA', '214970,0', 'en-GY'),
(95, 'HK', 'Hong Kong', 'HKD', 'Hong Kong', 'AS', '1092,0', 'zh-HK,yue,zh,en'),
(96, 'HM', 'Heard Island and McDonald Islands', 'AUD', '', 'AN', '412,0', ''),
(97, 'HN', 'Honduras', 'HNL', 'Tegucigalpa', 'NA', '112090,0', 'es-HN'),
(98, 'HR', 'Croatia', 'HRK', 'Zagreb', 'EU', '56542,0', 'hr-HR,sr'),
(99, 'HT', 'Haiti', 'HTG', 'Port-au-Prince', 'NA', '27750,0', 'ht,fr-HT'),
(100, 'HU', 'Hungary', 'HUF', 'Budapest', 'EU', '93030,0', 'hu-HU'),
(101, 'ID', 'Indonesia', 'IDR', 'Jakarta', 'AS', '1919440,0', 'id,en,nl,jv'),
(102, 'IE', 'Ireland', 'EUR', 'Dublin', 'EU', '70280,0', 'en-IE,ga-IE'),
(103, 'IL', 'Israel', 'ILS', '', 'AS', '20770,0', 'he,ar-IL,en-IL,'),
(104, 'IM', 'Isle of Man', 'GBP', 'Douglas', 'EU', '572,0', 'en,gv'),
(105, 'IN', 'India', 'INR', 'New Delhi', 'AS', '3287590,0', 'en-IN,hi,bn,te,mr,ta,ur,gu,kn,ml,or,pa,as,bh,sat,ks,ne,sd,kok,doi,mni,sit,sa,fr,lus,inc'),
(106, 'IO', 'British Indian Ocean Territory', 'USD', '', 'AS', '60,0', 'en-IO'),
(107, 'IQ', 'Iraq', 'IQD', 'Baghdad', 'AS', '437072,0', 'ar-IQ,ku,hy'),
(108, 'IR', 'Iran', 'IRR', 'Tehran', 'AS', '1648000,0', 'fa-IR,ku'),
(109, 'IS', 'Iceland', 'ISK', 'Reykjavik', 'EU', '103000,0', 'is,en,de,da,sv,no'),
(110, 'IT', 'Italy', 'EUR', 'Rome', 'EU', '301230,0', 'it-IT,de-IT,fr-IT,sc,ca,co,sl'),
(111, 'JE', 'Jersey', 'GBP', 'Saint Helier', 'EU', '116,0', 'en,pt'),
(112, 'JM', 'Jamaica', 'JMD', 'Kingston', 'NA', '10991,0', 'en-JM'),
(113, 'JO', 'Jordan', 'JOD', 'Amman', 'AS', '92300,0', 'ar-JO,en'),
(114, 'JP', 'Japan', 'JPY', 'Tokyo', 'AS', '377835,0', 'ja'),
(115, 'KE', 'Kenya', 'KES', 'Nairobi', 'AF', '582650,0', 'en-KE,sw-KE'),
(116, 'KG', 'Kyrgyzstan', 'KGS', 'Bishkek', 'AS', '198500,0', 'ky,uz,ru'),
(117, 'KH', 'Cambodia', 'KHR', 'Phnom Penh', 'AS', '181040,0', 'km,fr,en'),
(118, 'KI', 'Kiribati', 'AUD', 'Tarawa', 'OC', '811,0', 'en-KI,gil'),
(119, 'KM', 'Comoros', 'KMF', 'Moroni', 'AF', '2170,0', 'ar,fr-KM'),
(120, 'KN', 'Saint Kitts and Nevis', 'XCD', 'Basseterre', 'NA', '261,0', 'en-KN'),
(121, 'KP', 'North Korea', 'KPW', 'Pyongyang', 'AS', '120540,0', 'ko-KP'),
(122, 'KR', 'South Korea', 'KRW', 'Seoul', 'AS', '98480,0', 'ko-KR,en'),
(123, 'KW', 'Kuwait', 'KWD', 'Kuwait City', 'AS', '17820,0', 'ar-KW,en'),
(124, 'KY', 'Cayman Islands', 'KYD', 'George Town', 'NA', '262,0', 'en-KY'),
(125, 'KZ', 'Kazakhstan', 'KZT', 'Astana', 'AS', '2717300,0', 'kk,ru'),
(126, 'LA', 'Laos', 'LAK', 'Vientiane', 'AS', '236800,0', 'lo,fr,en'),
(127, 'LB', 'Lebanon', 'LBP', 'Beirut', 'AS', '10400,0', 'ar-LB,fr-LB,en,hy'),
(128, 'LC', 'Saint Lucia', 'XCD', 'Castries', 'NA', '616,0', 'en-LC'),
(129, 'LI', 'Liechtenstein', 'CHF', 'Vaduz', 'EU', '160,0', 'de-LI'),
(130, 'LK', 'Sri Lanka', 'LKR', 'Colombo', 'AS', '65610,0', 'si,ta,en'),
(131, 'LR', 'Liberia', 'LRD', 'Monrovia', 'AF', '111370,0', 'en-LR'),
(132, 'LS', 'Lesotho', 'LSL', 'Maseru', 'AF', '30355,0', 'en-LS,st,zu,xh'),
(133, 'LT', 'Lithuania', 'LTL', 'Vilnius', 'EU', '65200,0', 'lt,ru,pl'),
(134, 'LU', 'Luxembourg', 'EUR', 'Luxembourg', 'EU', '2586,0', 'lb,de-LU,fr-LU'),
(135, 'LV', 'Latvia', 'EUR', 'Riga', 'EU', '64589,0', 'lv,ru,lt'),
(136, 'LY', 'Libya', 'LYD', 'Tripoli', 'AF', '1759540,0', 'ar-LY,it,en'),
(137, 'MA', 'Morocco', 'MAD', 'Rabat', 'AF', '446550,0', 'ar-MA,fr'),
(138, 'MC', 'Monaco', 'EUR', 'Monaco', 'EU', '1,95', 'fr-MC,en,it'),
(139, 'MD', 'Moldova', 'MDL', 'Chişinău', 'EU', '33843,0', 'ro,ru,gag,tr'),
(140, 'ME', 'Montenegro', 'EUR', 'Podgorica', 'EU', '14026,0', 'sr,hu,bs,sq,hr,rom'),
(141, 'MF', 'Saint Martin', 'EUR', 'Marigot', 'NA', '53,0', 'fr'),
(142, 'MG', 'Madagascar', 'MGA', 'Antananarivo', 'AF', '587040,0', 'fr-MG,mg'),
(143, 'MH', 'Marshall Islands', 'USD', 'Majuro', 'OC', '181,3', 'mh,en-MH'),
(144, 'MK', 'Macedonia', 'MKD', 'Skopje', 'EU', '25333,0', 'mk,sq,tr,rmm,sr'),
(145, 'ML', 'Mali', 'XOF', 'Bamako', 'AF', '1240000,0', 'fr-ML,bm'),
(146, 'MM', 'Myanmar [Burma]', 'MMK', 'Nay Pyi Taw', 'AS', '678500,0', 'my'),
(147, 'MN', 'Mongolia', 'MNT', 'Ulan Bator', 'AS', '1565000,0', 'mn,ru'),
(148, 'MO', 'Macao', 'MOP', 'Macao', 'AS', '254,0', 'zh,zh-MO,pt'),
(149, 'MP', 'Northern Mariana Islands', 'USD', 'Saipan', 'OC', '477,0', 'fil,tl,zh,ch-MP,en-MP'),
(150, 'MQ', 'Martinique', 'EUR', 'Fort-de-France', 'NA', '1100,0', 'fr-MQ'),
(151, 'MR', 'Mauritania', 'MRO', 'Nouakchott', 'AF', '1030700,0', 'ar-MR,fuc,snk,fr,mey,wo'),
(152, 'MS', 'Montserrat', 'XCD', 'Plymouth', 'NA', '102,0', 'en-MS'),
(153, 'MT', 'Malta', 'EUR', 'Valletta', 'EU', '316,0', 'mt,en-MT'),
(154, 'MU', 'Mauritius', 'MUR', 'Port Louis', 'AF', '2040,0', 'en-MU,bho,fr'),
(155, 'MV', 'Maldives', 'MVR', 'Malé', 'AS', '300,0', 'dv,en'),
(156, 'MW', 'Malawi', 'MWK', 'Lilongwe', 'AF', '118480,0', 'ny,yao,tum,swk'),
(157, 'MX', 'Mexico', 'MXN', 'Mexico City', 'NA', '1972550,0', 'es-MX'),
(158, 'MY', 'Malaysia', 'MYR', 'Kuala Lumpur', 'AS', '329750,0', 'ms-MY,en,zh,ta,te,ml,pa,th'),
(159, 'MZ', 'Mozambique', 'MZN', 'Maputo', 'AF', '801590,0', 'pt-MZ,vmw'),
(160, 'NA', 'Namibia', 'NAD', 'Windhoek', 'AF', '825418,0', 'en-NA,af,de,hz,naq'),
(161, 'NC', 'New Caledonia', 'XPF', 'Noumea', 'OC', '19060,0', 'fr-NC'),
(162, 'NE', 'Niger', 'XOF', 'Niamey', 'AF', '1267000,0', 'fr-NE,ha,kr,dje'),
(163, 'NF', 'Norfolk Island', 'AUD', 'Kingston', 'OC', '34,6', 'en-NF'),
(164, 'NG', 'Nigeria', 'NGN', 'Abuja', 'AF', '923768,0', 'en-NG,ha,yo,ig,ff'),
(165, 'NI', 'Nicaragua', 'NIO', 'Managua', 'NA', '129494,0', 'es-NI,en'),
(166, 'NL', 'Netherlands', 'EUR', 'Amsterdam', 'EU', '41526,0', 'nl-NL,fy-NL'),
(167, 'NO', 'Norway', 'NOK', 'Oslo', 'EU', '324220,0', 'no,nb,nn,se,fi'),
(168, 'NP', 'Nepal', 'NPR', 'Kathmandu', 'AS', '140800,0', 'ne,en'),
(169, 'NR', 'Nauru', 'AUD', '', 'OC', '21,0', 'na,en-NR'),
(170, 'NU', 'Niue', 'NZD', 'Alofi', 'OC', '260,0', 'niu,en-NU'),
(171, 'NZ', 'New Zealand', 'NZD', 'Wellington', 'OC', '268680,0', 'en-NZ,mi'),
(172, 'OM', 'Oman', 'OMR', 'Muscat', 'AS', '212460,0', 'ar-OM,en,bal,ur'),
(173, 'PA', 'Panama', 'PAB', 'Panama City', 'NA', '78200,0', 'es-PA,en'),
(174, 'PE', 'Peru', 'PEN', 'Lima', 'SA', '1285220,0', 'es-PE,qu,ay'),
(175, 'PF', 'French Polynesia', 'XPF', 'Papeete', 'OC', '4167,0', 'fr-PF,ty'),
(176, 'PG', 'Papua New Guinea', 'PGK', 'Port Moresby', 'OC', '462840,0', 'en-PG,ho,meu,tpi'),
(177, 'PH', 'Philippines', 'PHP', 'Manila', 'AS', '300000,0', 'tl,en-PH,fil'),
(178, 'PK', 'Pakistan', 'PKR', 'Islamabad', 'AS', '803940,0', 'ur-PK,en-PK,pa,sd,ps,brh'),
(179, 'PL', 'Poland', 'PLN', 'Warsaw', 'EU', '312685,0', 'pl'),
(180, 'PM', 'Saint Pierre and Miquelon', 'EUR', 'Saint-Pierre', 'NA', '242,0', 'fr-PM'),
(181, 'PN', 'Pitcairn Islands', 'NZD', 'Adamstown', 'OC', '47,0', 'en-PN'),
(182, 'PR', 'Puerto Rico', 'USD', 'San Juan', 'NA', '9104,0', 'en-PR,es-PR'),
(183, 'PS', 'Palestine', 'ILS', '', 'AS', '5970,0', 'ar-PS'),
(184, 'PT', 'Portugal', 'EUR', 'Lisbon', 'EU', '92391,0', 'pt-PT,mwl'),
(185, 'PW', 'Palau', 'USD', 'Melekeok - Palau State Capital', 'OC', '458,0', 'pau,sov,en-PW,tox,ja,fil,zh'),
(186, 'PY', 'Paraguay', 'PYG', 'Asunción', 'SA', '406750,0', 'es-PY,gn'),
(187, 'QA', 'Qatar', 'QAR', 'Doha', 'AS', '11437,0', 'ar-QA,es'),
(188, 'RE', 'Réunion', 'EUR', 'Saint-Denis', 'AF', '2517,0', 'fr-RE'),
(189, 'RO', 'Romania', 'RON', 'Bucharest', 'EU', '237500,0', 'ro,hu,rom'),
(190, 'RS', 'Serbia', 'RSD', 'Belgrade', 'EU', '88361,0', 'sr,hu,bs,rom'),
(191, 'RU', 'Russia', 'RUB', 'Moscow', 'EU', '1,71E7', 'ru,tt,xal,cau,ady,kv,ce,tyv,cv,udm,tut,mns,bua,myv,mdf,chm,ba,inh,tut,kbd,krc,ava,sah,nog'),
(192, 'RW', 'Rwanda', 'RWF', 'Kigali', 'AF', '26338,0', 'rw,en-RW,fr-RW,sw'),
(193, 'SA', 'Saudi Arabia', 'SAR', 'Riyadh', 'AS', '1960582,0', 'ar-SA'),
(194, 'SB', 'Solomon Islands', 'SBD', 'Honiara', 'OC', '28450,0', 'en-SB,tpi'),
(195, 'SC', 'Seychelles', 'SCR', 'Victoria', 'AF', '455,0', 'en-SC,fr-SC'),
(196, 'SD', 'Sudan', 'SDG', 'Khartoum', 'AF', '1861484,0', 'ar-SD,en,fia'),
(197, 'SE', 'Sweden', 'SEK', 'Stockholm', 'EU', '449964,0', 'sv-SE,se,sma,fi-SE'),
(198, 'SG', 'Singapore', 'SGD', 'Singapore', 'AS', '692,7', 'cmn,en-SG,ms-SG,ta-SG,zh-SG'),
(199, 'SH', 'Saint Helena', 'SHP', 'Jamestown', 'AF', '410,0', 'en-SH'),
(200, 'SI', 'Slovenia', 'EUR', 'Ljubljana', 'EU', '20273,0', 'sl,sh'),
(201, 'SJ', 'Svalbard and Jan Mayen', 'NOK', 'Longyearbyen', 'EU', '62049,0', 'no,ru'),
(202, 'SK', 'Slovakia', 'EUR', 'Bratislava', 'EU', '48845,0', 'sk,hu'),
(203, 'SL', 'Sierra Leone', 'SLL', 'Freetown', 'AF', '71740,0', 'en-SL,men,tem'),
(204, 'SM', 'San Marino', 'EUR', 'San Marino', 'EU', '61,2', 'it-SM'),
(205, 'SN', 'Senegal', 'XOF', 'Dakar', 'AF', '196190,0', 'fr-SN,wo,fuc,mnk'),
(206, 'SO', 'Somalia', 'SOS', 'Mogadishu', 'AF', '637657,0', 'so-SO,ar-SO,it,en-SO'),
(207, 'SR', 'Suriname', 'SRD', 'Paramaribo', 'SA', '163270,0', 'nl-SR,en,srn,hns,jv'),
(208, 'SS', 'South Sudan', 'SSP', 'Juba', 'AF', '644329,0', 'en'),
(209, 'ST', 'São Tomé and Príncipe', 'STD', 'São Tomé', 'AF', '1001,0', 'pt-ST'),
(210, 'SV', 'El Salvador', 'USD', 'San Salvador', 'NA', '21040,0', 'es-SV'),
(211, 'SX', 'Sint Maarten', 'ANG', 'Philipsburg', 'NA', '21,0', 'nl,en'),
(212, 'SY', 'Syria', 'SYP', 'Damascus', 'AS', '185180,0', 'ar-SY,ku,hy,arc,fr,en'),
(213, 'SZ', 'Swaziland', 'SZL', 'Mbabane', 'AF', '17363,0', 'en-SZ,ss-SZ'),
(214, 'TC', 'Turks and Caicos Islands', 'USD', 'Cockburn Town', 'NA', '430,0', 'en-TC'),
(215, 'TD', 'Chad', 'XAF', 'N''Djamena', 'AF', '1284000,0', 'fr-TD,ar-TD,sre'),
(216, 'TF', 'French Southern Territories', 'EUR', 'Port-aux-Français', 'AN', '7829,0', 'fr'),
(217, 'TG', 'Togo', 'XOF', 'Lomé', 'AF', '56785,0', 'fr-TG,ee,hna,kbp,dag,ha'),
(218, 'TH', 'Thailand', 'THB', 'Bangkok', 'AS', '514000,0', 'th,en'),
(219, 'TJ', 'Tajikistan', 'TJS', 'Dushanbe', 'AS', '143100,0', 'tg,ru'),
(220, 'TK', 'Tokelau', 'NZD', '', 'OC', '10,0', 'tkl,en-TK'),
(221, 'TL', 'East Timor', 'USD', 'Dili', 'OC', '15007,0', 'tet,pt-TL,id,en'),
(222, 'TM', 'Turkmenistan', 'TMT', 'Ashgabat', 'AS', '488100,0', 'tk,ru,uz'),
(223, 'TN', 'Tunisia', 'TND', 'Tunis', 'AF', '163610,0', 'ar-TN,fr'),
(224, 'TO', 'Tonga', 'TOP', 'Nuku''alofa', 'OC', '748,0', 'to,en-TO'),
(225, 'TR', 'Turkey', 'TRY', 'Ankara', 'AS', '780580,0', 'tr-TR,ku,diq,az,av'),
(226, 'TT', 'Trinidad and Tobago', 'TTD', 'Port of Spain', 'NA', '5128,0', 'en-TT,hns,fr,es,zh'),
(227, 'TV', 'Tuvalu', 'AUD', 'Funafuti', 'OC', '26,0', 'tvl,en,sm,gil'),
(228, 'TW', 'Taiwan', 'TWD', 'Taipei', 'AS', '35980,0', 'zh-TW,zh,nan,hak'),
(229, 'TZ', 'Tanzania', 'TZS', 'Dodoma', 'AF', '945087,0', 'sw-TZ,en,ar'),
(230, 'UA', 'Ukraine', 'UAH', 'Kyiv', 'EU', '603700,0', 'uk,ru-UA,rom,pl,hu'),
(231, 'UG', 'Uganda', 'UGX', 'Kampala', 'AF', '236040,0', 'en-UG,lg,sw,ar'),
(232, 'UM', 'U.S. Minor Outlying Islands', 'USD', '', 'OC', '0,0', 'en-UM'),
(233, 'US', 'United States', 'USD', 'Washington', 'NA', '9629091,0', 'en-US,es-US,haw,fr'),
(234, 'UY', 'Uruguay', 'UYU', 'Montevideo', 'SA', '176220,0', 'es-UY'),
(235, 'UZ', 'Uzbekistan', 'UZS', 'Tashkent', 'AS', '447400,0', 'uz,ru,tg'),
(236, 'VA', 'Vatican City', 'EUR', 'Vatican', 'EU', '0,44', 'la,it,fr'),
(237, 'VC', 'Saint Vincent and the Grenadines', 'XCD', 'Kingstown', 'NA', '389,0', 'en-VC,fr'),
(238, 'VE', 'Venezuela', 'VEF', 'Caracas', 'SA', '912050,0', 'es-VE'),
(239, 'VG', 'British Virgin Islands', 'USD', 'Road Town', 'NA', '153,0', 'en-VG'),
(240, 'VI', 'U.S. Virgin Islands', 'USD', 'Charlotte Amalie', 'NA', '352,0', 'en-VI'),
(241, 'VN', 'Vietnam', 'VND', 'Hanoi', 'AS', '329560,0', 'vi,en,fr,zh,km'),
(242, 'VU', 'Vanuatu', 'VUV', 'Port Vila', 'OC', '12200,0', 'bi,en-VU,fr-VU'),
(243, 'WF', 'Wallis and Futuna', 'XPF', 'Mata-Utu', 'OC', '274,0', 'wls,fud,fr-WF'),
(244, 'WS', 'Samoa', 'WST', 'Apia', 'OC', '2944,0', 'sm,en-WS'),
(245, 'XK', 'Kosovo', 'EUR', 'Pristina', 'EU', '10908,0', 'sq,sr'),
(246, 'YE', 'Yemen', 'YER', 'Sanaa', 'AS', '527970,0', 'ar-YE'),
(247, 'YT', 'Mayotte', 'EUR', 'Mamoutzou', 'AF', '374,0', 'fr-YT'),
(248, 'ZA', 'South Africa', 'ZAR', 'Pretoria', 'AF', '1219912,0', 'zu,xh,af,nso,en-ZA,tn,st,ts,ss,ve,nr'),
(249, 'ZM', 'Zambia', 'ZMW', 'Lusaka', 'AF', '752614,0', 'en-ZM,bem,loz,lun,lue,ny,toi'),
(250, 'ZW', 'Zimbabwe', 'ZWL', 'Harare', 'AF', '390580,0', 'en-ZW,sn,nr,nd');

```

## Server setup
* Git, Maven, Java & Tomcat setup
```sh
$ yum install git
$ wget http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.rpm?AuthParam=1437243125_07c1ad264df66e4c8b11d6698cc64f0d
$ mv jdk-7u79-linux-x64.rpm\?AuthParam\=1437243125_07c1ad264df66e4c8b11d6698cc64f0d jdk-7u79-linux-x64.rpm
$ rpm -Uvh jdk-7u79-linux-x64.rpm
$ nano /etc/profile.d/jdk.sh
$ chmod +x /etc/profile.d/jdk.sh
$ source /etc/profile.d/jdk.sh
$ wget http://www.interior-dsgn.com/apache/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz
$ mv apache-maven-3.3.3 /usr/maven
$ ln -s /usr/maven/bin/mvn /usr/bin/mvn
$ nano /etc/profile.d/maven.sh
$ chmod +x /etc/profile.d/maven.sh
$ source /etc/profile.d/maven.sh
$ wget http://mirror.olnevhost.net/pub/apache/tomcat/tomcat-7/v7.0.63/bin/apache-tomcat-7.0.63.tar.gz
$ tar -xzf apache-tomcat-7.0.63.tar.gz
$ mv apache-tomcat-7.0.63 /usr/local/tomcat7
$ nano /etc/profile.d/tomcat.sh
$ chmod +x /etc/profile.d/tomcat.sh
$ source /etc/profile.d/tomcat.sh
$ chmod +x $CATALINA_HOME/bin/startup.sh
$ chmod +x $CATALINA_HOME/bin/shutdown.sh
$ chmod +x $CATALINA_HOME/bin/catalina.sh
$ nano /etc/init.d/tomcat
$ chmod a+x /etc/init.d/tomcat
$ chkconfig --add tomcat
$ service tomcat start
$ chkconfig tomcat on
```

## Eclipse setup
* Import Maven Project
* Window -> Show View -> Other -> General -> Navigator
  * .settings -> org.eclipse.wst.common.project.facet.core.xml (java = 1.7, jst.web = 3.0)
* Preferences -> Java -> Installed JREs -> 1.7
* Project -> Properties -> Java Build Path -> Libraries (Remove JRE 1.5 or 1.6)
* Project -> Properties -> Targeted Runtimes -> Apache Tomcat 7.0
* Project -> Properties -> Java Compiler -> Compiler Compliance Level 1.7
* Project -> Properties -> Project Facets -> Java (1.7)
* POM.xml should have maven build plugin source & target to 1.7
* Project -> Maven -> Update Project...

## Eclipse run/debug configuration
* Project -> Run As... -> Run Configurations -> Maven (RMB) -> New...
  * Main Tab
    * Name: mvnTopup
    * Base Directory: ${workspace_loc:/topup}
    * Goals: tomcat:run
  * Source Tab
    * Add -> Workspace

## Command line
* `$ mvn clean install`
* `$ mvn package` 
* `$ mvn tomcat:run` or `$ mvnDebug tomcat:run`
