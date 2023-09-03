/*
Navicat MySQL Data Transfer

Source Server         : 192.168.241.130
Source Server Version : 80020
Source Host           : 192.168.241.130:3306
Source Database       : inventory_management

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2021-09-01 07:17:11
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
