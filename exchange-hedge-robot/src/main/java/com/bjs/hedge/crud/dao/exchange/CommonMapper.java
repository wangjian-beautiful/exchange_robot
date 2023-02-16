package com.bjs.hedge.crud.dao.exchange;

import com.bjs.hedge.crud.model.ConfigSymbol;
import com.bjs.hedge.vo.ExOrder;
import com.bjs.hedge.vo.ExTrade;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CommonMapper {

    @Select("select * from ${tableName} where id = #{id}")
    ExTrade  getExTrade(@Param("tableName") String tableName, @Param("id") Number id );

    @Select("select * from ${tableName} where id =#{id}")
    ExOrder getExOrder(@Param("tableName") String tableName, @Param("id") Number id );

    @Select("select * from ${tableName}")
    List<ExTrade> getAllExTrade(@Param("tableName") String tableName );

    @Select( "select symbol from config_symbol")
    List<String> getConfigSymbol();

    @Select( "select price_pre,volume_pre from config_symbol where symbol=#{symbol} ")
    ConfigSymbol getConfigSymbolBySymbol(@Param("symbol")String symbol);
}
