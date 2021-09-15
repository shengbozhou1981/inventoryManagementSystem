/*
Navicat MySQL Data Transfer

Source Server         : 192.168.241.130
Source Server Version : 80020
Source Host           : 192.168.241.130:3306
Source Database       : inventory_management

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2021-09-01 07:17:29
*/

SET FOREIGN_KEY_CHECKS=0;

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
