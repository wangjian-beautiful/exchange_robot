package com.jeesuite.admin.dao.repository;

import com.jeesuite.admin.dao.entity.TradeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 自动交易策略领域
 *
 * @author kew
 * @create 2018-08-19 下午5:15
 **/

public interface TradeOrderRepository extends PagingAndSortingRepository<TradeOrder, Long> {


    @Modifying
    @Transactional
    @Query("update TradeOrder set status=:status where id=:id")
    Integer editStatusById(@Param("id") Long id, @Param("status") String status);


    @Modifying
    @Transactional
    @Query("select t from TradeOrder t where status='1' and currency_code=:currency ")
    List<TradeOrder> countWaitMatchOrder(@Param("currency") String currency);

    Page<TradeOrder> findByCreateTimeLessThanEqual(LocalDateTime createTime, Pageable pageable);

    @Modifying
    @Transactional
    void deleteByIdIn(@Param("ids") List ids);
}
