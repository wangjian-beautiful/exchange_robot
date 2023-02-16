ALTER TABLE `kline_robot`.`trade_schedule`
    ADD COLUMN `server_index` int(11) NULL DEFAULT 0 COMMENT '执行的服务编号' AFTER `refresh_time`;