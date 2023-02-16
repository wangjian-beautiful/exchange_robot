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
                               `access_key` varchar(255) DEFAULT NULL COMMENT '用户access_key',
                               `amount` decimal(24,8) DEFAULT NULL COMMENT '交易金额',
                               `count` decimal(24,8) DEFAULT NULL COMMENT '交易数量',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `currency_code` varchar(255) DEFAULT NULL COMMENT '交易对，例如btc_usdt，usdt是主币',
                               `description` varchar(255) DEFAULT NULL COMMENT '交易描述',
                               `fee` decimal(24,8) DEFAULT NULL COMMENT '手续费',
                               `limit_type` varchar(255) DEFAULT NULL COMMENT '报价类型：市价 限价',
                               `order_no` varchar(255) DEFAULT NULL COMMENT '订单号',
                               `order_type` bit(1) NOT NULL COMMENT '订单类型',
                               `price` decimal(24,8) DEFAULT NULL COMMENT '价格',
                               `schedule_id` bigint(20) DEFAULT NULL COMMENT '交易机器人ID-外键',
                               `security_key` varchar(255) DEFAULT NULL COMMENT '用户security_key',
                               `server_url` varchar(255) DEFAULT NULL COMMENT '交易API地址',
                               `status` varchar(255) DEFAULT NULL COMMENT '状态0初始订单 未成交未进入盘口 1新订单，未成交进入盘口 2完全成交 3部分成交 4已撤单 5待撤单 6异常订单',
                               `success_amount` decimal(12,8) DEFAULT NULL COMMENT '成功交易金额',
                               `success_count` decimal(12,8) DEFAULT NULL COMMENT '成功交易数量',
                               `update_time` datetime DEFAULT NULL COMMENT '修改日期',
                               `channel` varchar(255) DEFAULT NULL COMMENT '交易所备注',
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
                                  `access_key` varchar(255) DEFAULT NULL COMMENT '用户access_key',
                                  `security_key` varchar(255) DEFAULT NULL COMMENT '用户security_key',
                                  `currency` varchar(255) DEFAULT NULL COMMENT '交易对',
                                  `currency_trail` varchar(255) DEFAULT NULL COMMENT '跟随的交易对-可单一交易对，也可多个交易对乘法计算',
                                  `status` varchar(255) DEFAULT NULL COMMENT '状态0不下单 1下单',
                                  `type` varchar(255) DEFAULT NULL COMMENT '类型 kline K线机器人，trade刷量机器人',
                                  `follow_type` varchar(255) DEFAULT NULL COMMENT '跟随类型 1价格跟随 2波动率跟随【火币】',
                                  `follow_market` varchar(255) DEFAULT NULL COMMENT '跟随交易所类型1火币 2ZB 3组合计算',
                                  `fluctuation_ratio` double DEFAULT NULL COMMENT '波动率-波动率跟随有效',
                                  `duration` bigint(20) DEFAULT NULL COMMENT '交易间隔（毫秒）',
                                  `handicap_price_gap` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot新盘口配置，盘口价格间隔，默认为1;',
                                  `level1_price_gap` decimal(20,8) DEFAULT NULL COMMENT '买卖一档与行情价间隔',
                                  `active_wave_price` decimal(20,8) DEFAULT NULL COMMENT '价格波动，主动下单',
                                  `active_duration` bigint(20) DEFAULT NULL COMMENT '主动下单的时间间隔，避免下单速率太快',
                                  `end_time` bigint(20) DEFAULT NULL COMMENT '交易持续时间',
                                  `password` varchar(255) DEFAULT NULL COMMENT '密码配置',
                                  `server_url` varchar(255) DEFAULT NULL COMMENT '交易API地址',
                                  `start_time` bigint(20) DEFAULT NULL COMMENT '开始时间',
                                  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
                                  `min_price` decimal(20,8) DEFAULT NULL COMMENT '最低价格',
                                  `max_price` decimal(20,8) DEFAULT NULL COMMENT '最高价格',
                                  `channel` varchar(255) DEFAULT NULL COMMENT '交易所备注',
                                  `fuid` bigint(20) DEFAULT NULL COMMENT '机器人用户ID-在业务系统中的用户ID <br/>trade刷量机器人时配置',
                                  `price_count` int(11) DEFAULT NULL COMMENT '交易对盘口纵深数量，例如20 <br/>trade刷量机器人时配置',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `handicap_random_fold` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot新盘口配置，价格档位下单数量随机数乘以的倍数，默认为1',
                                  `handicap_shape` int(11) DEFAULT '0' COMMENT 'robot盘口配置类型，0表示抛物线形，1表示线性，默认为0',
                                  `random_begin` decimal(20,8) DEFAULT '0.00000000' COMMENT '随机数区间的开始值，深度图形状为1线性时有效',
                                  `random_end` decimal(20,8) DEFAULT '1.00000000' COMMENT '随机数区间的结束值，深度图形状为1线性时有效',
                                  `kline_random_fold` decimal(20,8) DEFAULT '1.00000000' COMMENT 'robot新盘口配置，K线下单随机数倍数，默认为1',
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
