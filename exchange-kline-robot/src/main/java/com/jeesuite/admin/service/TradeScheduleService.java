package com.jeesuite.admin.service;

import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.dao.mapper.TradeScheduleEntityMapper;
import com.jeesuite.admin.util.DateUtils;
import com.jeesuite.common.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * description:
 * author:User
 * date:2019/5/4
 */
@Service
public class TradeScheduleService {

    @Autowired
    private TradeScheduleEntityMapper tradeScheduleEntityMapper;

    public List<TradeScheduleEntity> findListAll(String existsCurrency, Integer serverIndex) {
        return this.tradeScheduleEntityMapper.findListAll(existsCurrency,serverIndex);
    }

    public List<TradeScheduleEntity> findListInCurrency(String currency, String existsCurrency, Integer serverIndex) {
        return this.tradeScheduleEntityMapper.findListInCurrency(currency,existsCurrency,serverIndex);
    }



    public TradeScheduleEntity selectByPrimaryKey(Long id) {
        return this.tradeScheduleEntityMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int addInfo(TradeScheduleEntity entity) {
        entity.setCreateTime(DateUtils.getNow());
        return this.tradeScheduleEntityMapper.insertSelective(entity);
    }

    @Transactional
    public int updateInfo(TradeScheduleEntity entity) {
        TradeScheduleEntity target = BeanCopyUtils.copy(entity, TradeScheduleEntity.class);
        target.setUpdateTime(DateUtils.getNow());

        return this.tradeScheduleEntityMapper.updateByPrimaryKey(target);
    }

    public int deleteInfo(Integer id) {

        return this.tradeScheduleEntityMapper.deleteByPrimaryKey(Long.parseLong(id.toString()));
    }

    public int updatePrice(Long id, BigDecimal minPrice, BigDecimal maxPrice) {
        int count = this.tradeScheduleEntityMapper.updatePrice(id, minPrice, maxPrice);
        return count;
    }
}
