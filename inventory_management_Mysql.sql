/*
Navicat MySQL Data Transfer

Source Server         : 192.168.241.130
Source Server Version : 80020
Source Host           : 192.168.241.130:3306
Source Database       : inventory_management

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2021-08-30 01:52:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cashier
-- ----------------------------
DROP TABLE IF EXISTS `cashier`;
CREATE TABLE `cashier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `terminal_id` int NOT NULL,
  `token` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `terminal_id` (`terminal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of cashier
-- ----------------------------
INSERT INTO `cashier` VALUES ('1', '101', 'token101', '2021-08-25 17:32:23', '2021-08-25 17:32:27');
INSERT INTO `cashier` VALUES ('2', '102', 'token102', '2021-08-25 17:32:29', '2021-08-25 17:32:31');
INSERT INTO `cashier` VALUES ('3', '103', 'token103', '2021-08-25 17:32:34', '2021-08-25 17:32:40');
INSERT INTO `cashier` VALUES ('4', '104', 'token104', null, null);
INSERT INTO `cashier` VALUES ('5', '105', 'token105', null, null);
INSERT INTO `cashier` VALUES ('6', '106', 'token106', null, null);
INSERT INTO `cashier` VALUES ('7', '107', 'token107', '2021-08-25 17:27:43', '2021-08-25 17:27:43');
INSERT INTO `cashier` VALUES ('8', '108', 'token108', '2021-08-27 08:45:55', '2021-08-27 08:45:55');
INSERT INTO `cashier` VALUES ('9', '109', 'token109', '2021-08-27 15:28:10', '2021-08-27 15:28:10');
INSERT INTO `cashier` VALUES ('10', '110', 'token110', '2021-08-29 09:38:37', '2021-08-29 09:38:37');
INSERT INTO `cashier` VALUES ('11', '111', 'token111', '2021-08-30 00:31:18', '2021-08-30 00:31:18');

-- ----------------------------
-- Table structure for discount_products
-- ----------------------------
DROP TABLE IF EXISTS `discount_products`;
CREATE TABLE `discount_products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `upc` varchar(50) NOT NULL,
  `regular_price` decimal(10,2) DEFAULT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `discount_percentage` double DEFAULT NULL,
  `quantity_requirement` int DEFAULT NULL,
  `with_product_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(50) DEFAULT NULL,
  `active` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `upc` (`upc`),
  CONSTRAINT `discount_products_ibfk_1` FOREIGN KEY (`upc`) REFERENCES `product` (`upc`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of discount_products
-- ----------------------------
INSERT INTO `discount_products` VALUES ('1', '101', 'SP101', '1.50', '2021-08-24', '2021-08-27', '0.15', '2', null, '2021-08-24 17:50:56', null, 'Tony', '0');
INSERT INTO `discount_products` VALUES ('2', '103', 'SP103', '5.20', '2021-08-25', '2021-09-30', '0.2', '2', null, '2021-08-25 17:51:25', null, 'Tony', '1');
INSERT INTO `discount_products` VALUES ('3', '105', 'SP105', '20.10', '2021-08-26', '2021-09-30', '0.2', null, '106', '2021-08-26 17:51:30', null, 'Tony', '1');
INSERT INTO `discount_products` VALUES ('4', '104', 'SP104', '2.90', '2021-08-27', '2021-09-30', '0.3', null, '102', '2021-08-27 17:54:30', null, 'Tony', '1');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(50) NOT NULL,
  `order_id` varchar(50) NOT NULL,
  `product_id` int NOT NULL,
  `product_name` varchar(50) NOT NULL,
  `upc` varchar(50) NOT NULL,
  `price` double DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`detail_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_head` (`order_id`),
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('09d2ca826edb4d121a8c6592c279c81f', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 10:39:43', '2021-08-27 10:39:43');
INSERT INTO `order_detail` VALUES ('1849908cdec78442bfe3156732da9872', '8c81e09be5e39158dae3147caaa907b2', '104', 'product104', 'SP104', '2.9', '10', '2021-08-27 11:01:23', '2021-08-27 11:01:23');
INSERT INTO `order_detail` VALUES ('1bbf6b2efce6dcd725dd131e2911a08b', 'cef40fc5e54045e12337f7d9348bb383', '104', 'product104', 'SP104', '2.9', '5', '2021-08-25 23:58:41', '2021-08-25 23:58:41');
INSERT INTO `order_detail` VALUES ('1ff18eec9c553181b617fab1621c3bf4', '8c81e09be5e39158dae3147caaa907b2', '101', 'product101', 'SP101', '1.5', '5', '2021-08-27 09:54:48', '2021-08-27 09:54:48');
INSERT INTO `order_detail` VALUES ('24f2f79a8148829ee6e52f0db2ca85dd', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 09:57:43', '2021-08-27 09:57:43');
INSERT INTO `order_detail` VALUES ('26184d6ba19a126ddf34ea28f46d2c00', 'e5fb7d4c064b1418af607babfe19a790', '102', 'product102', 'SP102', '3.2', '3', '2021-08-25 22:45:02', '2021-08-25 22:45:02');
INSERT INTO `order_detail` VALUES ('289b5a8629dadd21bd969e7ee86800c6', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 10:43:45', '2021-08-27 10:43:45');
INSERT INTO `order_detail` VALUES ('4223184b5db83e2ea9fb4323da95cdd7', 'cef40fc5e54045e12337f7d9348bb383', '104', 'product104', 'SP104', '2.9', '5', '2021-08-25 22:47:02', '2021-08-25 22:47:02');
INSERT INTO `order_detail` VALUES ('42d3b8d8c0e396bb518977bf9dc7e812', 'e9c8cecab86d4bfd9c316cb66afe1d4d', '102', 'product102', 'SP102', '3.2', '3', '2021-08-25 22:43:11', '2021-08-25 22:43:11');
INSERT INTO `order_detail` VALUES ('4401de594b85126beca575e6fbc8e20a', '8c81e09be5e39158dae3147caaa907b2', '104', 'product104', 'SP104', '2.9', '10', '2021-08-27 10:55:32', '2021-08-27 10:55:32');
INSERT INTO `order_detail` VALUES ('4f0e48881e479fdb4c3c7197f7352acf', 'd2b7dbc169915c4ac195e5615e93be2b', '102', 'product102', 'SP102', '3.2', '5', '2021-08-26 13:36:05', '2021-08-26 13:36:05');
INSERT INTO `order_detail` VALUES ('51512c2bcccde81e8d779ea9e1986b3b', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 10:34:41', '2021-08-27 10:34:41');
INSERT INTO `order_detail` VALUES ('53119c99336cb1603acfef3b5d59d58a', '35393480350143495b116172d3713dcf', '102', 'product102', 'SP102', '3.2', '5', '2021-08-30 00:18:20', '2021-08-30 00:18:20');
INSERT INTO `order_detail` VALUES ('59180ecc529f1b00044ae4845825e81c', '8c81e09be5e39158dae3147caaa907b2', '101', 'product101', 'SP101', '1.5', '5', '2021-08-27 09:50:15', '2021-08-27 09:50:15');
INSERT INTO `order_detail` VALUES ('5c8e2872e84d10d3e07f1b74b4da7b6a', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 10:54:27', '2021-08-27 10:54:27');
INSERT INTO `order_detail` VALUES ('5f298ebc9e139a3a718a9ef4516b9781', '8dce1b6f8db937d27bc402124b19a288', '102', 'product102', 'SP102', '3.2', '10', '2021-08-27 15:07:44', '2021-08-27 15:07:44');
INSERT INTO `order_detail` VALUES ('626a0e6240b9f78738e7b5107739acdb', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 10:44:54', '2021-08-27 10:44:54');
INSERT INTO `order_detail` VALUES ('62fbad7ab2f0628c0bb256f6f42fef10', '8c81e09be5e39158dae3147caaa907b2', '101', 'product101', 'SP101', '1.5', '5', '2021-08-27 09:52:01', '2021-08-27 09:52:01');
INSERT INTO `order_detail` VALUES ('6598d57bae09a905bb2359121c1f25ec', 'd1e0e14b29fcdddfa56a16caef010505', '102', 'product102', 'SP102', '3.2', '10', '2021-08-27 15:21:07', '2021-08-27 15:21:07');
INSERT INTO `order_detail` VALUES ('6b8bef9501e633bfad2e7755f580f736', '6b2750cde06c91dea536984ca1a099e4', '103', 'product103', 'SP103', '5.2', '10', '2021-08-29 21:36:21', '2021-08-29 21:36:21');
INSERT INTO `order_detail` VALUES ('712b8101f70432b36e96b51130eaba07', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 10:39:03', '2021-08-27 10:39:03');
INSERT INTO `order_detail` VALUES ('93260d91d089078f896bb52ca608a5d6', '8c81e09be5e39158dae3147caaa907b2', '104', 'product104', 'SP104', '2.9', '10', '2021-08-30 00:34:43', '2021-08-30 00:34:43');
INSERT INTO `order_detail` VALUES ('9463db5361eeccbe9b56a2baff0efcab', '8c81e09be5e39158dae3147caaa907b2', '101', 'product101', 'SP101', '1.5', '5', '2021-08-27 09:49:24', '2021-08-27 09:49:24');
INSERT INTO `order_detail` VALUES ('96b8e240f9cefd22b47cf0c6d3d2408e', '35393480350143495b116172d3713dcf', '103', 'product103', 'SP103', '5.2', '10', '2021-08-30 00:18:20', '2021-08-30 00:18:20');
INSERT INTO `order_detail` VALUES ('9808fcd4426b39110c1565a07cdfa26d', '8c81e09be5e39158dae3147caaa907b2', '105', 'product105', 'SP105', '20.1', '5', '2021-08-26 22:43:11', '2021-08-26 22:43:11');
INSERT INTO `order_detail` VALUES ('9cf8a62e0ab535e01f7fc9ed779a9bd9', '8c81e09be5e39158dae3147caaa907b2', '106', 'product106', 'SP106', '11.5', '10', '2021-08-26 22:43:14', '2021-08-26 22:43:14');
INSERT INTO `order_detail` VALUES ('b604e77b01980f9c7b8ed6a8b3be9007', '8dce1b6f8db937d27bc402124b19a288', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 15:07:42', '2021-08-27 15:07:42');
INSERT INTO `order_detail` VALUES ('b63661c44e8f3938aae9bd16106a46da', 'd1e0e14b29fcdddfa56a16caef010505', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 15:21:05', '2021-08-27 15:21:05');
INSERT INTO `order_detail` VALUES ('c133e4e7cbbaed4223cf4e38f2b4c473', '6b50af9223b6472217dadd7f60b25192', '105', 'product105', 'SP105', '20.1', '5', '2021-08-27 11:14:25', '2021-08-27 11:14:25');
INSERT INTO `order_detail` VALUES ('cf3389578d6e19f67c79af99a35c04e6', '024b585af40cec2399bb0ae428d8c512', '104', 'product104', 'SP104', '2.9', '5', '2021-08-26 07:36:32', '2021-08-26 07:36:32');
INSERT INTO `order_detail` VALUES ('d1f08e839919cb8af34e5d4867a37b3f', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 11:01:20', '2021-08-27 11:01:20');
INSERT INTO `order_detail` VALUES ('d5640e1d3858cf0a7f7a8548b6b7e50a', 'e5fb7d4c064b1418af607babfe19a790', '103', 'product103', 'SP103', '5.2', '4', '2021-08-25 22:45:16', '2021-08-25 22:45:16');
INSERT INTO `order_detail` VALUES ('d6b1de5f695ebb67aea588aab513c387', '6b2750cde06c91dea536984ca1a099e4', '102', 'product102', 'SP102', '3.2', '5', '2021-08-29 21:36:21', '2021-08-29 21:36:21');
INSERT INTO `order_detail` VALUES ('d8b3a1f1c2cc1576d5e0c9ba43f21cd2', '024b585af40cec2399bb0ae428d8c512', '101', 'product101', 'SP101', '1.5', '5', '2021-08-26 07:36:29', '2021-08-26 07:36:29');
INSERT INTO `order_detail` VALUES ('db310054ac86b3af1541e5842b47fbeb', '50d332706c0c3cdcc22b8d2e951d71fb', '102', 'product102', 'SP102', '3.2', '5', '2021-08-26 13:44:04', '2021-08-26 13:44:04');
INSERT INTO `order_detail` VALUES ('df724ac8987f5aa1b8bcf6a3ced45a6d', 'cef40fc5e54045e12337f7d9348bb383', '101', 'product101', 'SP101', '1.5', '5', '2021-08-25 22:46:59', '2021-08-25 22:46:59');
INSERT INTO `order_detail` VALUES ('ea8a404d577be2cca1575ac63ae2a284', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-30 00:34:43', '2021-08-30 00:34:43');
INSERT INTO `order_detail` VALUES ('eadf02aa0992e2fbb221689fde875966', '6e88b7bea2fa32d12c8e79e557744a92', '106', 'product106', 'SP106', '11.5', '10', '2021-08-27 11:16:02', '2021-08-27 11:16:02');
INSERT INTO `order_detail` VALUES ('f4097d9fe79fe2f4be210b45b848a6c1', '6e88b7bea2fa32d12c8e79e557744a92', '105', 'product105', 'SP105', '20.1', '5', '2021-08-27 11:15:59', '2021-08-27 11:15:59');
INSERT INTO `order_detail` VALUES ('fa64143f4595a989d1b5a8bf0d9c2e9f', '8c81e09be5e39158dae3147caaa907b2', '103', 'product103', 'SP103', '5.2', '5', '2021-08-27 10:55:28', '2021-08-27 10:55:28');

-- ----------------------------
-- Table structure for order_head
-- ----------------------------
DROP TABLE IF EXISTS `order_head`;
CREATE TABLE `order_head` (
  `order_id` varchar(50) NOT NULL,
  `customer_name` varchar(50) DEFAULT NULL,
  `terminal_id` int NOT NULL,
  `order_amount` double NOT NULL,
  `order_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `payment_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pay_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `terminal_id` (`terminal_id`),
  CONSTRAINT `order_head_ibfk_1` FOREIGN KEY (`terminal_id`) REFERENCES `cashier` (`terminal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of order_head
-- ----------------------------
INSERT INTO `order_head` VALUES ('024b585af40cec2399bb0ae428d8c512', 'YY', '101', '22', 'new', 'credit card', 'pending', '2021-08-26 07:36:26', '2021-08-26 07:36:26');
INSERT INTO `order_head` VALUES ('35393480350143495b116172d3713dcf', 'Steve', '103', '65.088', 'pending', 'credit card', 'pending', '2021-08-30 00:18:20', '2021-08-30 00:18:20');
INSERT INTO `order_head` VALUES ('50d332706c0c3cdcc22b8d2e951d71fb', 'ZHOU', '101', '18.08', 'new', 'credit card', 'pending', '2021-08-26 13:43:57', '2021-08-26 13:43:57');
INSERT INTO `order_head` VALUES ('6b2750cde06c91dea536984ca1a099e4', 'Steve', '101', '76.83999999999999', 'pending', 'credit card', 'pending', '2021-08-29 21:36:21', '2021-08-29 21:36:21');
INSERT INTO `order_head` VALUES ('6b50af9223b6472217dadd7f60b25192', 'Michael', '101', '243.515', 'completed', 'credit card', 'paid', '2021-08-27 06:14:21', '2021-08-30 00:36:43');
INSERT INTO `order_head` VALUES ('6e88b7bea2fa32d12c8e79e557744a92', 'Michael', '101', '243.515', 'pending', 'credit card', 'paid', '2021-08-27 07:15:53', '2021-08-30 00:39:20');
INSERT INTO `order_head` VALUES ('7e019d5c49a7a6aca72468cdb2d92bb9', 'Test', '101', '159.32999999999998', 'pending', 'credit card', 'pending', '2021-08-28 21:07:49', '2021-08-28 21:07:49');
INSERT INTO `order_head` VALUES ('8c81e09be5e39158dae3147caaa907b2', 'WANG', '101', '1150.3399999999997', 'pending', 'credit card', 'completed', '2021-08-26 05:42:23', '2021-08-30 00:34:43');
INSERT INTO `order_head` VALUES ('8dce1b6f8db937d27bc402124b19a288', 'Ben', '101', '65.53999999999999', 'pending', 'credit card', 'pending', '2021-08-27 15:07:35', '2021-08-27 15:07:35');
INSERT INTO `order_head` VALUES ('99fbfb9174328b641dda1614547775ed', 'test', '101', '243.515', 'pending', 'credit card', 'pending', '2021-08-28 13:16:13', '2021-08-28 13:16:13');
INSERT INTO `order_head` VALUES ('a639cf155d4383224a489a3670c9091f', 'last', '101', '243.515', 'pending', 'credit card', 'pending', '2021-08-28 21:44:47', '2021-08-28 21:44:47');
INSERT INTO `order_head` VALUES ('ab3043b89b61b08be4f982dd8cb6abe5', 'Ben', '101', '65.53999999999999', 'pending', 'credit card', 'pending', '2021-08-27 15:01:47', '2021-08-27 15:01:47');
INSERT INTO `order_head` VALUES ('c3aa237722864f6845191b7ff5cf5b38', 'Ben', '101', '65.53999999999999', 'pending', 'credit card', 'pending', '2021-08-27 14:59:31', '2021-08-27 14:59:31');
INSERT INTO `order_head` VALUES ('cef40fc5e54045e12337f7d9348bb383', 'tony', '101', '51', 'new', 'credit card', 'pending', '2021-08-25 20:46:58', '2021-08-25 23:57:23');
INSERT INTO `order_head` VALUES ('d1e0e14b29fcdddfa56a16caef010505', 'stephen', '101', '65.53999999999999', 'pending', 'credit card', 'pending', '2021-08-27 15:21:01', '2021-08-27 15:21:01');
INSERT INTO `order_head` VALUES ('d2b7dbc169915c4ac195e5615e93be2b', 'ZHOU', '101', '18.08', 'new', 'credit card', 'pending', '2021-08-26 13:36:02', '2021-08-26 13:36:02');
INSERT INTO `order_head` VALUES ('dc520fcf2ff9807e0a38f78dfd84ffaa', 'Test', '101', '159.32999999999998', 'pending', 'credit card', 'pending', '2021-08-28 21:05:31', '2021-08-28 21:05:31');
INSERT INTO `order_head` VALUES ('e5fb7d4c064b1418af607babfe19a790', 'tony', '101', '30.4', 'new', 'credit card', 'pending', '2021-08-25 22:44:39', '2021-08-25 22:44:39');
INSERT INTO `order_head` VALUES ('e9c8cecab86d4bfd9c316cb66afe1d4d', 'tony', '101', '30.4', 'new', 'credit card', 'pending', '2021-08-25 22:43:11', '2021-08-25 22:43:11');
INSERT INTO `order_head` VALUES ('f7ce810b25ec2a0d9bcc98a5839db8d9', 'Ben', '101', '65.53999999999999', 'pending', 'credit card', 'pending', '2021-08-27 15:04:47', '2021-08-27 15:04:47');

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(50) NOT NULL,
  `order_amount` double NOT NULL,
  `payment_method` varchar(50) NOT NULL,
  `payment_status` varchar(50) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order_head` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of payment
-- ----------------------------
INSERT INTO `payment` VALUES ('1', '8c81e09be5e39158dae3147caaa907b2', '243.515', 'credit card', 'paid', '2021-08-26 20:42:28', '2021-08-26 23:11:43');
INSERT INTO `payment` VALUES ('2', '6b50af9223b6472217dadd7f60b25192', '243.515', 'credit card', 'paid', '2021-08-27 11:14:23', '2021-08-27 11:14:23');
INSERT INTO `payment` VALUES ('3', '6e88b7bea2fa32d12c8e79e557744a92', '243.515', 'credit card', 'paid', '2021-08-27 07:15:56', '2021-08-30 00:39:20');
INSERT INTO `payment` VALUES ('4', 'c3aa237722864f6845191b7ff5cf5b38', '65.53999999999999', 'credit card', 'pending', '2021-08-27 14:59:31', '2021-08-27 14:59:31');
INSERT INTO `payment` VALUES ('5', 'ab3043b89b61b08be4f982dd8cb6abe5', '65.53999999999999', 'credit card', 'pending', '2021-08-27 15:01:55', '2021-08-27 15:01:55');
INSERT INTO `payment` VALUES ('6', 'f7ce810b25ec2a0d9bcc98a5839db8d9', '65.53999999999999', 'credit card', 'pending', '2021-08-27 15:04:49', '2021-08-27 15:04:49');
INSERT INTO `payment` VALUES ('7', '8dce1b6f8db937d27bc402124b19a288', '65.53999999999999', 'credit card', 'pending', '2021-08-27 15:07:38', '2021-08-27 15:07:38');
INSERT INTO `payment` VALUES ('8', 'd1e0e14b29fcdddfa56a16caef010505', '65.53999999999999', 'credit card', 'pending', '2021-08-27 15:21:03', '2021-08-27 15:21:03');
INSERT INTO `payment` VALUES ('10', '99fbfb9174328b641dda1614547775ed', '243.515', 'credit card', 'pending', '2021-08-28 13:18:26', '2021-08-28 13:18:26');
INSERT INTO `payment` VALUES ('17', 'dc520fcf2ff9807e0a38f78dfd84ffaa', '159.32999999999998', 'credit card', 'pending', '2021-08-28 21:05:31', '2021-08-28 21:05:31');
INSERT INTO `payment` VALUES ('18', '7e019d5c49a7a6aca72468cdb2d92bb9', '159.32999999999998', 'credit card', 'pending', '2021-08-28 21:07:53', '2021-08-28 21:07:53');
INSERT INTO `payment` VALUES ('19', 'a639cf155d4383224a489a3670c9091f', '243.515', 'credit card', 'pending', '2021-08-28 21:44:47', '2021-08-28 21:44:47');
INSERT INTO `payment` VALUES ('20', '6b2750cde06c91dea536984ca1a099e4', '76.83999999999999', 'credit card', 'pending', '2021-08-29 21:36:21', '2021-08-29 21:36:21');
INSERT INTO `payment` VALUES ('21', '35393480350143495b116172d3713dcf', '65.088', 'credit card', 'pending', '2021-08-30 00:18:20', '2021-08-30 00:18:20');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) NOT NULL,
  `product_id` int NOT NULL,
  `upc` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `inventory` int NOT NULL,
  `stock_alert_threshold` int DEFAULT NULL,
  `from_date` date DEFAULT NULL COMMENT 'promotion starts',
  `to_date` date DEFAULT NULL COMMENT 'promotion ends',
  `discount_percentage` double DEFAULT NULL COMMENT 'discount off percentage',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `upc` (`upc`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', 'product101', '101', 'SP101', '1.50', '2000', '10', null, null, null, '2021-07-23 23:17:19', '2021-08-29 10:35:42');
INSERT INTO `product` VALUES ('2', 'product102', '102', 'SP102', '2.20', '1478', '10', '2018-08-18', '2018-09-20', '0.15', '2021-04-10 08:18:03', '2021-08-30 00:28:02');
INSERT INTO `product` VALUES ('3', 'product103', '103', 'SP103', '5.20', '1995', '10', null, null, null, '2021-08-03 08:18:03', '2021-08-30 00:34:43');
INSERT INTO `product` VALUES ('4', 'product104', '104', 'SP104', '2.90', '1990', '10', null, null, null, '2021-08-04 06:10:39', '2021-08-30 00:34:43');
INSERT INTO `product` VALUES ('5', 'product105', '105', 'SP105', '20.10', '2000', '10', null, null, null, '2021-08-06 00:31:18', '2021-08-29 10:38:55');
INSERT INTO `product` VALUES ('6', 'product106', '106', 'SP106', '11.50', '2000', '5', null, null, null, '2021-08-05 16:32:37', '2021-08-29 10:37:44');

-- ----------------------------
-- Table structure for sales_report
-- ----------------------------
DROP TABLE IF EXISTS `sales_report`;
CREATE TABLE `sales_report` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `report_detail` longtext,
  `report_past_days` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sales_report
-- ----------------------------
INSERT INTO `sales_report` VALUES ('1', '[SalesStatisticVo(upc=SP102, productId=102, productName=product102, price=3.2, totalSales=16), SalesStatisticVo(upc=SP104, productId=104, productName=product104, price=2.9, totalSales=15), SalesStatisticVo(upc=SP101, productId=101, productName=product101, price=1.5, totalSales=10), SalesStatisticVo(upc=SP103, productId=103, productName=product103, price=5.2, totalSales=4)]', '7', '2021-08-26 20:58:30', '2021-08-26 20:58:30');
INSERT INTO `sales_report` VALUES ('2', '[SalesStatisticVo(upc=SP103, productId=103, productName=product103, price=5.2, totalSales=49), SalesStatisticVo(upc=SP104, productId=104, productName=product104, price=2.9, totalSales=35), SalesStatisticVo(upc=SP101, productId=101, productName=product101, price=1.5, totalSales=30), SalesStatisticVo(upc=SP106, productId=106, productName=product106, price=11.5, totalSales=20), SalesStatisticVo(upc=SP102, productId=102, productName=product102, price=3.2, totalSales=16), SalesStatisticVo(upc=SP105, productId=105, productName=product105, price=20.1, totalSales=15)]', '7', '2021-08-27 11:33:44', '2021-08-27 11:33:44');
INSERT INTO `sales_report` VALUES ('3', '[SalesStatisticVo(upc=SP103, productId=103, productName=product103, price=5.2, totalSales=49), SalesStatisticVo(upc=SP104, productId=104, productName=product104, price=2.9, totalSales=35), SalesStatisticVo(upc=SP101, productId=101, productName=product101, price=1.5, totalSales=30), SalesStatisticVo(upc=SP106, productId=106, productName=product106, price=11.5, totalSales=20), SalesStatisticVo(upc=SP102, productId=102, productName=product102, price=3.2, totalSales=16), SalesStatisticVo(upc=SP105, productId=105, productName=product105, price=20.1, totalSales=15)]', '7', '2021-08-27 14:58:35', '2021-08-27 14:58:35');
INSERT INTO `sales_report` VALUES ('4', '[SalesStatisticVo(upc=SP103, productId=103, productName=product103, price=5.2, totalSales=59), SalesStatisticVo(upc=SP102, productId=102, productName=product102, price=3.2, totalSales=36), SalesStatisticVo(upc=SP104, productId=104, productName=product104, price=2.9, totalSales=35), SalesStatisticVo(upc=SP101, productId=101, productName=product101, price=1.5, totalSales=30), SalesStatisticVo(upc=SP106, productId=106, productName=product106, price=11.5, totalSales=20), SalesStatisticVo(upc=SP105, productId=105, productName=product105, price=20.1, totalSales=15)]', '7', '2021-08-28 21:43:14', '2021-08-28 21:43:14');
INSERT INTO `sales_report` VALUES ('5', '[SalesStatisticVo(upc=SP103, productId=103, productName=product103, price=5.2, totalSales=59), SalesStatisticVo(upc=SP102, productId=102, productName=product102, price=3.2, totalSales=36), SalesStatisticVo(upc=SP104, productId=104, productName=product104, price=2.9, totalSales=35), SalesStatisticVo(upc=SP101, productId=101, productName=product101, price=1.5, totalSales=30), SalesStatisticVo(upc=SP106, productId=106, productName=product106, price=11.5, totalSales=20), SalesStatisticVo(upc=SP105, productId=105, productName=product105, price=20.1, totalSales=15)]', '7', '2021-08-29 09:51:05', '2021-08-29 09:51:05');
INSERT INTO `sales_report` VALUES ('6', '[SalesStatisticVo(upc=SP103, productId=103, productName=product103, price=5.2, totalSales=84), SalesStatisticVo(upc=SP102, productId=102, productName=product102, price=3.2, totalSales=46), SalesStatisticVo(upc=SP104, productId=104, productName=product104, price=2.9, totalSales=45), SalesStatisticVo(upc=SP101, productId=101, productName=product101, price=1.5, totalSales=30), SalesStatisticVo(upc=SP106, productId=106, productName=product106, price=11.5, totalSales=20), SalesStatisticVo(upc=SP105, productId=105, productName=product105, price=20.1, totalSales=15)]', '7', '2021-08-30 00:37:15', '2021-08-30 00:37:15');

-- ----------------------------
-- Table structure for stock_alert
-- ----------------------------
DROP TABLE IF EXISTS `stock_alert`;
CREATE TABLE `stock_alert` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alert_product` varchar(50) DEFAULT NULL,
  `alert_product_upc` varchar(50) DEFAULT NULL,
  `alert_current_stock` int DEFAULT NULL,
  `alert_content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alert_product_upc` (`alert_product_upc`),
  CONSTRAINT `stock_alert_ibfk_1` FOREIGN KEY (`alert_product_upc`) REFERENCES `product` (`upc`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of stock_alert
-- ----------------------------
INSERT INTO `stock_alert` VALUES ('1', 'product101', 'SP101', '9', 'product inventory lower than the alert Threshold, UPC is :SP101', null, null);
INSERT INTO `stock_alert` VALUES ('2', 'product101', 'SP101', '9', 'product inventory lower than the alert Threshold, UPC is :SP101', null, null);
INSERT INTO `stock_alert` VALUES ('3', 'product101', 'SP101', '9', 'product inventory lower than the alert Threshold, UPC is :SP101', '2021-08-26 15:40:40', '2021-08-26 15:40:46');
INSERT INTO `stock_alert` VALUES ('4', 'product101', 'SP101', '9', 'product inventory lower than the alert Threshold, UPC is :SP101', '2021-08-28 14:22:26', '2021-08-28 14:22:26');
INSERT INTO `stock_alert` VALUES ('5', 'product102', 'SP102', '10', 'product inventory lower than the alert Threshold, UPC is :SP102', '2021-08-29 10:25:08', '2021-08-29 10:25:08');
INSERT INTO `stock_alert` VALUES ('6', 'product102', 'SP102', '8', 'product inventory lower than the alert Threshold, UPC is :SP102', '2021-08-29 10:25:08', '2021-08-29 10:25:08');
INSERT INTO `stock_alert` VALUES ('7', 'product102', 'SP102', '8', 'product inventory lower than the alert Threshold, UPC is :SP102', '2021-08-29 10:25:08', '2021-08-29 10:25:08');
INSERT INTO `stock_alert` VALUES ('8', 'product102', 'SP102', '6', 'product inventory lower than the alert Threshold, UPC is :SP102', '2021-08-29 10:25:08', '2021-08-29 10:25:08');
INSERT INTO `stock_alert` VALUES ('9', 'product102', 'SP102', '4', 'product inventory lower than the alert Threshold, UPC is :SP102', '2021-08-29 10:25:08', '2021-08-29 10:25:08');
INSERT INTO `stock_alert` VALUES ('10', 'product102', 'SP102', '2', 'product inventory lower than the alert Threshold, UPC is :SP102', '2021-08-29 10:25:08', '2021-08-29 10:25:08');
INSERT INTO `stock_alert` VALUES ('11', 'product102', 'SP102', '0', 'product inventory lower than the alert Threshold, UPC is :SP102', '2021-08-29 10:25:08', '2021-08-29 10:25:08');
INSERT INTO `stock_alert` VALUES ('12', 'product101', 'SP101', '7', 'product inventory lower than the alert Threshold, UPC is :SP101', '2021-08-29 10:35:42', '2021-08-29 10:35:42');
INSERT INTO `stock_alert` VALUES ('13', 'product101', 'SP101', '1', 'product inventory lower than the alert Threshold, UPC is :SP101', '2021-08-29 10:35:42', '2021-08-29 10:35:42');
