/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50605
Source Host           : localhost:3306
Source Database       : db_sw

Target Server Type    : MYSQL
Target Server Version : 50605
File Encoding         : 65001

Date: 2019-05-29 17:58:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sw_demo
-- ----------------------------
DROP TABLE IF EXISTS `sw_demo`;
CREATE TABLE `sw_demo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL,
  `age` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sw_demo
-- ----------------------------
INSERT INTO `sw_demo` VALUES ('1', '张三', '1', '18', '2019-05-20 18:08:59');
