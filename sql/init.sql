/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : kline_robot

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 11/08/2022 11:43:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE database if NOT EXISTS `kline_robot` default character set utf8mb4 collate utf8mb4_general_ci;
use `kline_robot`;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of contract_order
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of fo_rate
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
    `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of mac_config
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order_history
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sms_message
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for trade_order
-- ----------------------------
DROP TABLE IF EXISTS `trade_order`;
CREATE TABLE `trade_order` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of trade_order
-- ----------------------------
BEGIN;
COMMIT;

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
                                  `duration` bigint(20) DEFAULT NULL COMMENT '????????????????????????',
                                  `handicap_price_gap` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot????????????????????????????????????????????????1;',
                                  `level1_price_gap` decimal(20,8) DEFAULT NULL COMMENT '??????????????????????????????',
                                  `active_wave_price` decimal(20,8) DEFAULT NULL COMMENT '???????????????????????????',
                                  `active_duration` bigint(20) DEFAULT NULL COMMENT '??????????????????????????????????????????????????????',
                                  `end_time` bigint(20) DEFAULT NULL COMMENT '??????????????????',
                                  `password` varchar(255) DEFAULT NULL COMMENT '????????????',
                                  `server_url` varchar(255) DEFAULT NULL COMMENT '??????API??????',
                                  `start_time` bigint(20) DEFAULT NULL COMMENT '????????????',
                                  `update_time` datetime DEFAULT NULL COMMENT '????????????',
                                  `user_name` varchar(255) DEFAULT NULL COMMENT '?????????',
                                  `min_price` decimal(20,8) DEFAULT NULL COMMENT '????????????',
                                  `max_price` decimal(20,8) DEFAULT NULL COMMENT '????????????',
                                  `channel` varchar(255) DEFAULT NULL COMMENT '???????????????',
                                  `fuid` bigint(20) DEFAULT NULL COMMENT '???????????????ID-???????????????????????????ID <br/>trade????????????????????????',
                                  `price_count` int(11) DEFAULT NULL COMMENT '????????????????????????????????????20 <br/>trade????????????????????????',
                                  `create_time` datetime DEFAULT NULL COMMENT '????????????',
                                  `handicap_random_fold` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot??????????????????????????????????????????????????????????????????????????????1',
                                  `handicap_shape` int(11) DEFAULT '0' COMMENT 'robot?????????????????????0?????????????????????1????????????????????????0',
                                  `random_begin` decimal(20,8) DEFAULT '0.00000000' COMMENT '????????????????????????????????????????????????1???????????????',
                                  `random_end` decimal(20,8) DEFAULT '1.00000000' COMMENT '????????????????????????????????????????????????1???????????????',
                                  `kline_random_fold` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot??????????????????K????????????????????????????????????1',
                                  `count_trail_scale` double DEFAULT NULL,
                                  `max_amount` decimal(12,8) DEFAULT NULL,
                                  `min_amount` decimal(12,8) DEFAULT NULL,
                                  `price_interval` int(11) DEFAULT NULL,
                                  `price_max_sum` double DEFAULT NULL,
                                  `price_trail_scale` double DEFAULT NULL,
                                  `query` int(11) DEFAULT NULL,
                                  `refresh_time` int(11) DEFAULT NULL,
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of trade_schedule
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_account_info
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `name` varchar(16) DEFAULT NULL,
                         `password` varchar(32) DEFAULT NULL,
                         `mobile` char(11) DEFAULT NULL,
                         `email` varchar(32) DEFAULT NULL,
                         `type` smallint(6) DEFAULT NULL,
                         `gant_envs` varchar(64) DEFAULT NULL,
                         `status` smallint(6) DEFAULT NULL,
                         `created_at` datetime DEFAULT NULL,
                         `updated_at` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` (`id`, `name`, `password`, `mobile`, `email`, `type`, `gant_envs`, `status`, `created_at`, `updated_at`) VALUES (1, 'admin', '0128856bb61bf20f407a3513f457458d', NULL, NULL, 1, NULL, 1, NULL, NULL);
INSERT INTO `users` (`id`, `name`, `password`, `mobile`, `email`, `type`, `gant_envs`, `status`, `created_at`, `updated_at`) VALUES (3, 'cry', '25146dfde0871182ab43081b4c141624', NULL, NULL, 2, 'cry_usdt,soot_usdt', 0, '2019-05-17 22:37:10', '2019-05-19 20:49:28');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
