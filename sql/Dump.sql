-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: promis_authorization_server
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client_access_token`
--

DROP TABLE IF EXISTS `client_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_access_token` (
  `access_token` varchar(255) DEFAULT NULL,
  `patientPIN` varchar(5) DEFAULT NULL,
  `createdAt` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_access_token`
--

LOCK TABLES `client_access_token` WRITE;
/*!40000 ALTER TABLE `client_access_token` DISABLE KEYS */;
INSERT INTO `client_access_token` VALUES ('f3a79617-3158-4abe-9b64-49d39db9bd1c','2001','11-30-17 20:36:29 GMT'),('b835a995-9186-44df-b061-5c0f7fc5bd80','2001','11-30-17 20:39:21 GMT'),('49e4009c-4eac-421c-b228-451470e14a1f','2001','11-30-17 23:10:13 GMT'),('ec308047-1fb9-4fc6-b82c-c1931d0f5e6a','2001','12-01-17 05:40:49 GMT'),('ce6ff836-ec38-4bf0-a9e0-fa985ba59e45','2001','12-01-17 08:55:02 GMT');
/*!40000 ALTER TABLE `client_access_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientinfo`
--

DROP TABLE IF EXISTS `clientinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientinfo` (
  `apptype` varchar(20) DEFAULT NULL,
  `appversion` varchar(10) DEFAULT NULL,
  `clientid` varchar(36) DEFAULT NULL,
  `clientsecret` varchar(65) DEFAULT NULL,
  `issueAt` varchar(25) DEFAULT NULL,
  `expiresAt` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientinfo`
--

LOCK TABLES `clientinfo` WRITE;
/*!40000 ALTER TABLE `clientinfo` DISABLE KEYS */;
INSERT INTO `clientinfo` VALUES ('promis','321','24e84938-277e-48d3-8892-3d9e4264afe6','c5f3d1c7-62fb-4400-86d0-90ce07b01b04','2017-09-22',NULL),('promis','321','1cb68c8c-adbd-47d9-b404-66f973de064e','eb62f56c-9ac8-4072-8dff-dc971f6b8144','2017-09-22',NULL),('promis','321','40667ade-db66-4881-948b-b3aea766bf6d','c2a0c379-6a52-409d-9a85-a317e29d32b5','2017-09-22',NULL),('promis','321','40229482-faec-4eb6-928b-21230e4340a9','350de8d3-dfed-4857-aa07-1e49b72098cb','2017-09-22',NULL),('promis','321','51867a6e-7b8c-4f71-94a6-b8da8e5dc6ec','da5a334f-11c6-4a61-a6a0-424ffa98c62d','2017-09-22',NULL),('promis','321','3385b474-592e-44da-a028-844ea3c185cf','46800431-c18d-4751-a082-439715b74671','2017-09-22',NULL),('promis','123','5a29cab6-b3f5-462d-b2af-b307f4a84efa','$2a$12$.6a76ebCDP1WUlIt9t8Kqu7t0/5VbD9IbxJNMb.rcKmcyCdfTfH12','2017-09-26',NULL),('promis','123','86eea8a9-cadc-4a15-acfa-daf4530aa6e6','$2a$12$wZGTp1Y0e.6mTeh1Xzo4MOJyEyRDaxr4qXhO1QA0YHJvclylY6V/.','2017-09-26',NULL),('promis','123','d2bb8073-b8f2-4157-aa8c-c92c6bd367e2','$2a$12$ZQ3bgAiMCCet0B..Qvp9M.cNrf4qGqhgTbxpA5VzEyB.SbhNFVPOK','2017-09-26',NULL),('promis','123','7abdb761-7643-47fd-93e4-25a927799ffb','$2a$12$z2dP.08yyLFeV9JzdVKplukE4/6twQRPVyQEPRrrH59KGdgBG3V0e','2017-09-26',NULL),('promis','123','54096c6e-ed7d-4e26-b0e8-ce09cfd40781','$2a$12$iOGXzkAELrIeIg6ERSRy5OorfGOCvZ9mMZQL5XFEPiDagq9qJCR1W','09-27-17 01:28:14 GMT',NULL),('promis','123','bb079a65-6399-47da-b9ef-f6a3eeda2113','$2a$12$dQAT.ubT0Vw7CNuQ2fJaeeS5gEJJsh5nZR48KUQAABGngCIEQdqJy','09-27-17 01:31:41 GMT',NULL),('promis','123','bd2290e0-64d4-46de-99bf-d9c7089bc841','$2a$12$1.3EhlX7xl.ZizK30UiZmeSLryn/9OqS2PcNUJq8wom8C.dLwWiJS','10-13-17 04:14:12 GMT',NULL),('promis','1234','6c38bfb5-7ae8-469f-acca-81346d6a423d','$2a$12$RgqVs34XKruUkzVfvhBix.7Ec5d5rAk.k1MfLRHbOwBeViBd3aVZu','10-13-17 14:15:02 GMT',NULL),('promis','1234','cc20ce4c-719e-4d14-a411-58789c2b028f','$2a$12$mDhbNqaMkMdEoVPF96/OvuhttyihAYqNG/RNoxVZXY7Qu.d9BPwn6','10-13-17 14:18:07 GMT',NULL),('promis','1234','00f69071-9c61-43d4-9a67-31133f847a4f','$2a$12$TPWsH4dbfOCzJtHEHg6HkORfiP40ILRPsxRRNWgcpYo5p8IBfsDEO','10-13-17 15:32:35 GMT',NULL),('promis','1234','37dca09b-868e-4d08-acb8-2d4d53469965','$2a$12$/eG448HETUbc8OvnJN55du30d6V.OC6DeAfEbDy16hXi9uvvxUIPG','10-13-17 21:09:18 GMT',NULL),('promis','1234','9da03a01-0de0-4bc5-8c6f-c51a3bb5ef7a','$2a$12$LwqNcqyYAy6cgtn3Fy7jc.hDU0UwiMCJCtxEjJCgXob/d//dzzMkm','11-11-17 00:19:28 GMT',NULL),('promis','1234','1a04417b-352e-4a4e-8c4c-139944a07d01','$2a$12$7NXF86xGp4fsaphCxIVxTebS0aPfUWCfrGmGptlta2zae59AzvtiS','11-17-17 17:17:36 GMT',NULL),('promis','1234','6aaa3c53-73a9-4cbe-a89c-b44cca21a723','$2a$12$IXhX6aaZzvgXZW/Gtbc7ueHgUs73lhhoJzdpBY8Vlz5gJXWu9vMUG','11-17-17 17:20:42 GMT',NULL),('promis','1234','c107e3f7-e4f3-40c3-997e-0699fe1bf798','$2a$12$9Ti7m22Q9jtpLUv85LxJbe7o7.R4WkplHZZgRQCdTMZ1QcomfKBbO','11-17-17 17:21:36 GMT',NULL),('promis','1234','ff03a70b-df24-4c23-b19a-3de169a2d5ae','$2a$12$WSZqiByzv9YgBuUZH3GiQuIgPGA0SFSo.E8tCEDCRz8atiEK8.0iK','11-17-17 18:06:37 GMT',NULL),('promis','1234','ef744144-fd7b-4142-af2f-4f676c05adfd','$2a$12$rdUQF5ca3yZQ.ZMQ4AQEVO/DDAFlR2cHP/MLMJHKJ88QsifxJsW7O','11-30-17 04:38:14 GMT',NULL),('promis','1234','c000c387-3a9a-4910-be6c-0cfff6943205','$2a$12$bkT4k/Ci7H5XDJF2Q.QL.uS2L1iOw/EDR70.gCbhvZSgmXDOZSTOe','11-30-17 17:53:00 GMT',NULL);
/*!40000 ALTER TABLE `clientinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-11  0:05:47
