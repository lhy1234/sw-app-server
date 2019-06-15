/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50605
Source Host           : localhost:3306
Source Database       : db_sw

Target Server Type    : MYSQL
Target Server Version : 50605
File Encoding         : 65001

Date: 2019-05-29 17:57:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sw_app_user
-- ----------------------------
DROP TABLE IF EXISTS `sw_app_user`;
CREATE TABLE `sw_app_user` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `token` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT '',
  `sex` tinyint(1) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `head_img_url` varchar(255) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL COMMENT '所在省',
  `city` varchar(30) DEFAULT NULL COMMENT '所在市',
  `area` varchar(30) DEFAULT NULL COMMENT '所在县/区',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_update_pwd_time` datetime DEFAULT NULL COMMENT '上次修改密码时间',
  `is_blacklist` tinyint(1) DEFAULT '0' COMMENT '是否黑名单 0-否 1-是',
  `is_locked` tinyint(1) DEFAULT '0' COMMENT '是否锁定  0-否 1-是',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '是否删除 0-否 1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sw_app_user
-- ----------------------------
INSERT INTO `sw_app_user` VALUES ('123', 'lhy', '123', null, '13298457669', null, null, null, null, null, null, null, null, null, null, null, null, null);
