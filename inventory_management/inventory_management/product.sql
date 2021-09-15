/*
Navicat MySQL Data Transfer

Source Server         : 192.168.241.130
Source Server Version : 80020
Source Host           : 192.168.241.130:3306
Source Database       : inventory_management

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2021-09-01 07:16:35
*/

SET FOREIGN_KEY_CHECKS=0;

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
INSERT INTO `product` VALUES ('2', 'product102', '102', 'SP102', '3.20', '1978', '10', '2018-08-15', '2018-09-17', '0.15', '2021-04-10 05:18:03', '2021-08-30 09:59:43');
INSERT INTO `product` VALUES ('3', 'product103', '103', 'SP103', '5.20', '1995', '10', null, null, null, '2021-08-03 08:18:03', '2021-08-30 00:34:43');
INSERT INTO `product` VALUES ('4', 'product104', '104', 'SP104', '2.90', '1990', '10', null, null, null, '2021-08-04 06:10:39', '2021-08-30 00:34:43');
INSERT INTO `product` VALUES ('5', 'product105', '105', 'SP105', '20.10', '2000', '10', null, null, null, '2021-08-06 00:31:18', '2021-08-29 10:38:55');
INSERT INTO `product` VALUES ('6', 'product106', '106', 'SP106', '11.50', '1989', '5', null, null, null, '2021-08-05 14:32:37', '2021-08-31 05:46:11');
