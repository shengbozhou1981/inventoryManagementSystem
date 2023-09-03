/*
Navicat MySQL Data Transfer

Source Server         : 192.168.241.130
Source Server Version : 80020
Source Host           : 192.168.241.130:3306
Source Database       : inventory_management

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2021-09-01 07:16:16
*/

SET FOREIGN_KEY_CHECKS=0;

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
