package com.bjs.hedge.crud.dao.robot;

import com.bjs.hedge.crud.Mapper;
import com.bjs.hedge.crud.model.HedgeOrigin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

public interface HedgeOriginMapper extends Mapper<HedgeOrigin> {

    @Select("SELECT SUM(betting) FROM hedge_origin WHERE `status` = 'NOT_HANDLE' and symbol = #{symbol}")
    BigDecimal sumBetting(@Param("symbol")String symbol);
}