package com.jeesuite.admin.dao.repository;

import com.jeesuite.admin.dao.entity.TradeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * 自动交易策略领域
 *
 * @author kew
 * @create 2018-08-19 下午5:15
 **/

public interface TradeScheduleRepository extends JpaRepository<TradeSchedule, Long> {
    @Modifying
    @Transactional
    @Query("update TradeSchedule set min_price=:minPrice,max_price=:maxPrice where id=:id")
    Integer editMinPriceMaxPriceById(@Param("id") Long id, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
}
