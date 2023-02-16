package com.bjs.hedge.util;

import com.bjs.hedge.common.Constants;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

public class CommonUtil {

    private CommonUtil() {
    }

    public static String getSymbolByTable(String tableTrade) {
        if (tableTrade != null || tableTrade.contains("_")) {
            String[] arr = tableTrade.split("_");
            return arr[arr.length - 1].toUpperCase();
        }
        return tableTrade;
    }

    public static String getRedisLockKey(String tableName, Number tableUniqueId) {
        return String.format("%s_%s_%s", Constants.LOCK_HEDGE_MQ_MESSAGE_UNIQUE_KEY, tableName, tableUniqueId);
    }

    public static String getRedisHedgeOriginLockKey(String tableName, Number tableUniqueId) {
        return String.format("%s_%s_%s", Constants.LOCK_HEDGE_ORIGIN_UNIQUE_KEY_PREFIX, tableName, tableUniqueId);
    }


    public static Date getOrderTime(Long time) {
        if (time == null) {
            return new Date();
        }
        return new Date(time);
    }


    public static BigDecimal getBigDecimalDef(String str, String def) {
        if (StringUtils.isNotBlank(str)) {
            return new BigDecimal(str);
        }
        return StringUtils.isNotBlank(def) ? null : new BigDecimal(def);
    }
}
