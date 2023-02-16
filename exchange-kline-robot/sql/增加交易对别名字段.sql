ALTER TABLE `kline_robot`.`trade_schedule`
    ADD COLUMN `currency_alias` varchar(255) NULL COMMENT '交易对别名（内部使用）' AFTER `currency`;

update `kline_robot`.`trade_schedule` set currency_alias = currency;

update `kline_robot`.`trade_schedule` set currency_alias = 'matic1_usdt' where currency = 'matic_usdt';
update `kline_robot`.`trade_schedule` set currency_alias = 'bsc_usdt' where currency = 'bnb_usdt';


