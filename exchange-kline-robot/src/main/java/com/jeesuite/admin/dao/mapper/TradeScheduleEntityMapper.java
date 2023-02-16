package com.jeesuite.admin.dao.mapper;

import com.jeesuite.admin.dao.CustomBaseMapper;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.dao.sql.TradeScheduleSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * description:
 * author:User
 * date:2019/5/4
 */
@Component
public interface TradeScheduleEntityMapper extends CustomBaseMapper<TradeScheduleEntity> {


    @SelectProvider(type = TradeScheduleSqlProvider.class, method = "findListAllSql")
    @Results(id = "result", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "access_key", property = "accessKey"),
            @Result(column = "security_key", property = "securityKey"),
            @Result(column = "currency", property = "currency"),
            @Result(column = "currency_alias", property = "currencyAlias"),
            @Result(column = "currency_trail", property = "currencyTrail"),
            @Result(column = "follow_type", property = "followType"),
            @Result(column = "follow_market", property = "followMarket"),
//            @Result(column = "price_trail_scale", property = "priceTrailScale"),
//            @Result(column = "count_trail_scale", property = "countTrailScale"),
            @Result(column = "fluctuation_ratio", property = "fluctuationRatio"),
            @Result(column = "duration", property = "duration"),
            @Result(column = "level1_price_gap", property = "level1PriceGap"),
            @Result(column = "active_wave_price", property = "activeWavePrice"),
            @Result(column = "active_duration", property = "activeDuration"),
            @Result(column = "start_time", property = "startTime"),
            @Result(column = "end_time", property = "endTime"),
//            @Result(column = "max_amount", property = "maxAmount"),
//            @Result(column = "min_amount", property = "minAmount"),
            @Result(column = "server_url", property = "serverUrl"),
            @Result(column = "status", property = "status"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "password", property = "password"),
            @Result(column = "type", property = "type"),
            @Result(column = "min_price", property = "minPrice"),
            @Result(column = "max_price", property = "maxPrice"),
            @Result(column = "query", property = "query"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "state", property = "state"),
            @Result(column = "fuid", property = "fuid"),
//            @Result(column = "price_max_sum", property = "priceMaxSum"),
//            @Result(column = "price_interval", property = "priceInterval"),
            @Result(column = "price_count", property = "priceCount"),
//            @Result(column = "traderids", property = "traderids"),
//            @Result(column = "threshold", property = "threshold"),
//            @Result(column = "handicap_count", property = "handicapCount"),
//            @Result(column = "handicap_type", property = "handicapType"),
            @Result(column = "handicap_random_fold", property = "handicapRandomFold"),
            @Result(column = "kline_random_fold", property = "klineRandomFold"),
            @Result(column = "handicap_price_gap", property = "handicapPriceGap"),
            @Result(column = "handicap_shape", property = "handicapShape"),
            @Result(column = "random_begin", property = "randomBegin"),
            @Result(column = "random_end", property = "randomEnd"),
            @Result(column = "refresh_time", property = "refreshTime"),
            @Result(column = "channel", property = "channel"),
            @Result(column = "server_index", property = "serverIndex")
    })
    List<TradeScheduleEntity> findListAll(String existsCurrency, Integer serverIndex);

    @SelectProvider(type = TradeScheduleSqlProvider.class, method = "findListInCurrencySql")
    @ResultMap("result")
    List<TradeScheduleEntity> findListInCurrency(String currency, String existsCurrency, Integer serverIndex);

    @Update("update trade_schedule set min_price=#{minPrice},max_price=#{maxPrice} where id=#{id}")
    int updatePrice(@Param("id") Long id, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);


}
