package com.jeesuite.admin.dao.sql;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

/**
 * description:机器人配置SQL构造器
 * author:User
 * date:2019/5/17
 */
public class TradeScheduleSqlProvider {

    /**
     * 获取对应币种配置列表
     *
     * @param currency
     * @return
     */
    public String findListInCurrencySql(String currency, String existsCurrency, Integer serverIndex) {
        SQL sql = new SQL();
        sql.SELECT(this.getColumns());
        sql.FROM("trade_schedule");
        if (StringUtils.isNoneBlank(currency)) {
            StringBuilder sb = new StringBuilder();
            String[] strs = currency.split(",");
            for (String str : strs) {
                sb.append(",").append("'").append(str).append("'");
            }
            sql.WHERE("currency IN (" + sb.substring(1) + ")");
        }
        if(StrUtil.isNotBlank(existsCurrency) ){
            sql.WHERE("currency NOT IN (" + existsCurrency + ")");
        }
        if(serverIndex != null ){
            sql.WHERE("server_index = " + serverIndex + "");
        }
        sql.ORDER_BY("create_time DESC");

        return sql.toString();
    }

    public String findListAllSql(String existsCurrency, Integer serverIndex){
        SQL sql = new SQL();
        sql.SELECT(this.getColumns());
        sql.FROM("trade_schedule");
        sql.WHERE("`status`", "1");
        if(StrUtil.isNotBlank(existsCurrency) ){
            sql.WHERE("currency NOT IN (" + existsCurrency + ")");
        }
        if(serverIndex != null ){
            sql.WHERE("server_index = " + serverIndex + "");
        }
        sql.ORDER_BY("create_time DESC");

        return sql.toString();
    }

    public String getColumns() {
//        StringBuilder columns = new StringBuilder();
//        columns.append("id,access_key,security_key,currency,currency_trail,status,type,follow_type,follow_market,");
//        columns.append("fluctuation_ratio,duration,end_time,password,server_url,start_time,update_time,user_name,min_price,max_price,query,");
//        columns.append("channel,fuid,price_count,create_time,refresh_time");
//
//        return columns.toString();

        return "*";
    }
}
