/*
Navicat MySQL Data Transfer

Source Server         : kline_robot
Source Server Version : 50719
Source Host           : 172.33.1.14:3306
Source Database       : kline_robot

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2019-11-02 20:58:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for contract_order
-- ----------------------------
DROP TABLE IF EXISTS `contract_order`;
CREATE TABLE `contract_order` (
  `id` bigint(20) NOT NULL,
  `access_key` varchar(255) DEFAULT NULL,
  `amount` decimal(12,8) DEFAULT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `contract_order_type` int(11) DEFAULT NULL,
  `contract_type` int(11) DEFAULT NULL,
  `count` decimal(12,8) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `currency_code` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fee` decimal(12,8) DEFAULT NULL,
  `match_price` bit(1) NOT NULL DEFAULT b'0',
  `order_no` varchar(255) DEFAULT NULL,
  `price` decimal(24,8) DEFAULT NULL,
  `schedule_id` bigint(20) DEFAULT NULL,
  `security_key` varchar(255) DEFAULT NULL,
  `server_url` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `success_amount` decimal(12,8) DEFAULT NULL,
  `success_count` decimal(12,8) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `leverage` decimal(19,2) DEFAULT NULL,
  `orde_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of contract_order
-- ----------------------------

-- ----------------------------
-- Table structure for fo_rate
-- ----------------------------
DROP TABLE IF EXISTS `fo_rate`;
CREATE TABLE `fo_rate` (
  `id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `rate` decimal(19,2) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of fo_rate
-- ----------------------------

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ROW_FORMAT=FIXED;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------

-- ----------------------------
-- Table structure for mac_config
-- ----------------------------
DROP TABLE IF EXISTS `mac_config`;
CREATE TABLE `mac_config` (
  `id` bigint(20) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `mac` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of mac_config
-- ----------------------------

-- ----------------------------
-- Table structure for order_history
-- ----------------------------
DROP TABLE IF EXISTS `order_history`;
CREATE TABLE `order_history` (
  `id` bigint(20) NOT NULL,
  `access_key` varchar(255) NOT NULL,
  `last_time` datetime DEFAULT NULL,
  `security_key` varchar(255) NOT NULL,
  `simple_cum_qty` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of order_history
-- ----------------------------

-- ----------------------------
-- Table structure for sms_message
-- ----------------------------
DROP TABLE IF EXISTS `sms_message`;
CREATE TABLE `sms_message` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `context` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sms_message
-- ----------------------------

-- ----------------------------
-- Table structure for trade_order
-- ----------------------------
DROP TABLE IF EXISTS `trade_order`;
CREATE TABLE `trade_order` (
  `id` bigint(24) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `access_key` varchar(255) DEFAULT NULL COMMENT '??????access_key',
  `amount` decimal(24,8) DEFAULT NULL COMMENT '????????????',
  `count` decimal(24,8) DEFAULT NULL COMMENT '????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `currency_code` varchar(255) DEFAULT NULL COMMENT '??????????????????btc_usdt???usdt?????????',
  `description` varchar(255) DEFAULT NULL COMMENT '????????????',
  `fee` decimal(24,8) DEFAULT NULL COMMENT '?????????',
  `limit_type` varchar(255) DEFAULT NULL COMMENT '????????????????????? ??????',
  `order_no` varchar(255) DEFAULT NULL COMMENT '?????????',
  `order_type` bit(1) NOT NULL COMMENT '????????????',
  `price` decimal(24,8) DEFAULT NULL COMMENT '??????',
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '???????????????ID-??????',
  `security_key` varchar(255) DEFAULT NULL COMMENT '??????security_key',
  `server_url` varchar(255) DEFAULT NULL COMMENT '??????API??????',
  `status` varchar(255) DEFAULT NULL COMMENT '??????0???????????? ???????????????????????? 1????????????????????????????????? 2???????????? 3???????????? 4????????? 5????????? 6????????????',
  `success_amount` decimal(12,8) DEFAULT NULL COMMENT '??????????????????',
  `success_count` decimal(12,8) DEFAULT NULL COMMENT '??????????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `channel` varchar(255) DEFAULT NULL COMMENT '???????????????',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `find_trade_order` (`currency_code`,`order_no`) USING BTREE,
  KEY `index_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of trade_order
-- ----------------------------

-- ----------------------------
-- Table structure for trade_schedule
-- ----------------------------
DROP TABLE IF EXISTS `trade_schedule`;
CREATE TABLE `trade_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_key` varchar(255) DEFAULT NULL COMMENT '??????access_key',
  `security_key` varchar(255) DEFAULT NULL COMMENT '??????security_key',
  `currency` varchar(255) DEFAULT NULL COMMENT '?????????',
  `currency_trail` varchar(255) DEFAULT NULL COMMENT '??????????????????-??????????????????????????????????????????????????????',
  `status` varchar(255) DEFAULT NULL COMMENT '??????0????????? 1??????',
  `type` varchar(255) DEFAULT NULL COMMENT '?????? kline K???????????????trade???????????????',
  `follow_type` varchar(255) DEFAULT NULL COMMENT '???????????? 1???????????? 2???????????????????????????',
  `follow_market` varchar(255) DEFAULT NULL COMMENT '?????????????????????1?????? 2ZB 3????????????',
  `fluctuation_ratio` double DEFAULT NULL COMMENT '?????????-?????????????????????',
  `duration` bigint(20) DEFAULT NULL COMMENT '????????????',
  `end_time` bigint(20) DEFAULT NULL COMMENT '??????????????????',
  `password` varchar(255) DEFAULT NULL COMMENT '????????????',
  `server_url` varchar(255) DEFAULT NULL COMMENT '??????API??????',
  `start_time` bigint(20) DEFAULT NULL COMMENT '????????????',
  `update_time` datetime DEFAULT NULL COMMENT '????????????',
  `user_name` varchar(255) DEFAULT NULL COMMENT '?????????',
  `min_price` decimal(20,8) DEFAULT NULL COMMENT '????????????',
  `max_price` decimal(20,8) DEFAULT NULL COMMENT '????????????',
  `query` int(20) DEFAULT NULL COMMENT '?????????????????? 0??????????????? 1????????????',
  `channel` varchar(255) DEFAULT NULL COMMENT '???????????????',
  `fuid` bigint(20) DEFAULT NULL COMMENT '???????????????ID-???????????????????????????ID <br/>trade????????????????????????',
  `price_count` int(10) DEFAULT NULL COMMENT '????????????????????????????????????20 <br/>trade????????????????????????',
  `create_time` datetime DEFAULT NULL COMMENT '????????????',
  `refresh_time` int(10) DEFAULT NULL COMMENT '????????????????????????????????????<br/>trade????????????????????????',
  `traderids` varchar(255) DEFAULT NULL COMMENT 'robot?????????trader???ids,??????????????????',
  `threshold` decimal(20,8) DEFAULT NULL COMMENT 'robot????????????????????????????????????',
  `handicap_count` int(10) DEFAULT NULL COMMENT 'robot???????????????????????????????????????????????????????????????',
  `handicap_random_fold` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot??????????????????????????????????????????????????????????????????????????????1',
  `handicap_price_gap` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot????????????????????????????????????????????????1;',
  `handicap_shape` int(10) DEFAULT '0' COMMENT 'robot?????????????????????0?????????????????????1????????????????????????0',
  `random_begin` decimal(20,8) DEFAULT '0.00000000' COMMENT '????????????????????????????????????????????????1???????????????',
  `random_end` decimal(20,8) DEFAULT '1.00000000' COMMENT '????????????????????????????????????????????????1???????????????',
  `kline_random_fold` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot??????????????????K????????????????????????????????????1',
  `count_trail_scale` double DEFAULT NULL,
  `max_amount` decimal(12,8) DEFAULT NULL,
  `min_amount` decimal(12,8) DEFAULT NULL,
  `price_interval` int(11) DEFAULT NULL,
  `price_max_sum` double DEFAULT NULL,
  `price_trail_scale` double DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of trade_schedule
-- ----------------------------
INSERT INTO `trade_schedule` VALUES ('50', 'cfca82b4bf9bd1078940ff0f572865dc', 'd59cd340-8506-471c-94e0-41910c6312a6', 'btc_usdt', '', '1', 'trade', '1', '2', '1', '1', '24', 'exchange', 'http://api.bitcoin360.io/', '0', '2019-10-26 12:45:23', 'exchange', '1.00000000', '20000.00000000', null, 'flycoin', '1', '30', '2019-09-25 16:15:51', '2', '2,3,4', '4000.00000000', '10', '1.00000000', '1.00000000', '0', '0.00500000', '0.10000000', '3.00000000', null, null, null, null, null, null);
INSERT INTO `trade_schedule` VALUES ('51', 'cfca82b4bf9bd1078940ff0f572865dc', 'd59cd340-8506-471c-94e0-41910c6312a6', 'eth_usdt', '', '0', 'trade', '1', '2', '1', '1', '24', 'exchange', 'http://api.bitcoin360.io/', '0', '2019-10-26 13:18:34', 'exchange', '1.00000000', '20000.00000000', null, 'flycoin', '1', '30', '2019-10-24 17:40:53', '2', '2,3,4', '4000.00000000', '10', '1.00000000', '0.50000000', '0', '0.05000000', '0.50000000', '3.00000000', null, null, null, null, null, null);
INSERT INTO `trade_schedule` VALUES ('52', 'cfca82b4bf9bd1078940ff0f572865dc', 'd59cd340-8506-471c-94e0-41910c6312a6', 'eos_usdt', '', '0', 'trade', '1', '2', '1', '1', '24', 'exchange', 'http://api.bitcoin360.io/', '0', '2019-10-26 13:18:58', 'exchange', '1.00000000', '20000.00000000', null, 'flycoin', '1', '30', '2019-10-24 17:47:00', '2', '2,3,4', '4000.00000000', '10', '1.00000000', '0.04000000', '0', '3.50000000', '100.00000000', '3.00000000', null, null, null, null, null, null);
INSERT INTO `trade_schedule` VALUES ('53', 'cfca82b4bf9bd1078940ff0f572865dc', 'd59cd340-8506-471c-94e0-41910c6312a6', 'ltc_usdt', '', '1', 'trade', '1', '2', '1', '1', '24', 'exchange', 'http://api.bitcoin360.io/', '0', '2019-10-26 12:46:07', 'exchange', '1.00000000', '20000.00000000', null, 'flycoin', '1', '30', '2019-10-24 18:01:44', '2', '2,3,4', '4000.00000000', '10', '1.00000000', '0.50000000', '0', '4.00000000', '50.00000000', '3.00000000', null, null, null, null, null, null);
INSERT INTO `trade_schedule` VALUES ('54', 'cfca82b4bf9bd1078940ff0f572865dc', 'd59cd340-8506-471c-94e0-41910c6312a6', 'etc_usdt', '', '1', 'trade', '1', '2', '1', '1', '24', 'exchange', 'http://api.bitcoin360.io/', '0', '2019-10-26 12:46:21', 'exchange', '1.00000000', '20000.00000000', null, 'flycoin', '1', '30', '2019-10-24 18:05:15', '2', '2,3,4', '4000.00000000', '10', '1.00000000', '0.00500000', '0', '4.00000000', '50.00000000', '3.00000000', null, null, null, null, null, null);
INSERT INTO `trade_schedule` VALUES ('55', 'cfca82b4bf9bd1078940ff0f572865dc', 'd59cd340-8506-471c-94e0-41910c6312a6', 'eth_btc', '', '1', 'trade', '1', '2', '1', '1', '24', 'exchange', 'http://api.bitcoin360.io/', '0', '2019-10-24 23:06:55', 'exchange', '0.00000100', '20000.00000000', null, 'flycoin', '1', '30', '2019-10-24 18:10:56', '2', '2,3,4', '4000.00000000', '10', '1.00000000', '0.00100000', '0', '0.05000000', '0.10000000', '3.00000000', null, null, null, null, null, null);
INSERT INTO `trade_schedule` VALUES ('56', 'cfca82b4bf9bd1078940ff0f572865dc', 'd59cd340-8506-471c-94e0-41910c6312a6', 'eos_btc', '', '1', 'trade', '1', '2', '1', '1', '24', 'exchange', 'http://api.bitcoin360.io/', '0', '2019-10-25 16:47:27', 'exchange', '0.00000001', '20000.00000000', null, 'flycoin', '1', '30', '2019-10-24 18:20:08', '2', '2,3,4', '4000.00000000', '10', '1.00000000', '0.00000400', '0', '2.00000000', '100.00000000', '3.00000000', null, null, null, null, null, null);
INSERT INTO `trade_schedule` VALUES ('57', 'cfca82b4bf9bd1078940ff0f572865dc', 'd59cd340-8506-471c-94e0-41910c6312a6', 'aaa_usdt', 'btc_usdt', '1', 'trade', '2', '2', '1', '1', '24', 'exchange', 'http://api.bitcoin360.io/', '0', '2019-10-26 12:46:53', 'exchange', '0.00100000', '20000.00000000', null, 'flycoin', '1', '30', '2019-10-25 16:47:01', '2', '2,3,4', '4000.00000000', '10', '1.00000000', '0.01000000', '0', '10.00000000', '100.00000000', '5.00000000', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `mobile` char(11) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `type` smallint(1) DEFAULT NULL,
  `gant_envs` varchar(64) DEFAULT NULL,
  `status` smallint(1) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', 'e8ba4dcdc46417ffcb7b3c22ee11e82a', null, null, '1', null, '1', null, null);
INSERT INTO `users` VALUES ('3', 'cry', '25146dfde0871182ab43081b4c141624', null, null, '2', 'cry_usdt,soot_usdt', '0', '2019-05-17 22:37:10', '2019-05-19 20:49:28');

-- ----------------------------
-- Table structure for user_account_info
-- ----------------------------
DROP TABLE IF EXISTS `user_account_info`;
CREATE TABLE `user_account_info` (
  `id` bigint(20) NOT NULL,
  `access_key` varchar(255) DEFAULT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `security_key` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_account_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `google_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_info
-- ----------------------------
