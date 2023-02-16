package com.bjs.hedge.crud.service.impl;

import com.bjs.hedge.crud.dao.robot.ExHedgeOrderMapper;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.service.ExHedgeOrderService;
import com.bjs.hedge.crud.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author AUTHOR
 * @date 2022/09/07
 */
@Service
public class ExHedgeOrderServiceImpl extends AbstractService<ExHedgeOrder> implements ExHedgeOrderService {
    @Resource
    private ExHedgeOrderMapper exHedgeOrderMapper;

    @Override
    public ExHedgeOrder getExHedgeOrderByUniqueKey(String tableName, Long tableUniqueId) {
        Condition condition = new Condition(ExHedgeOrder.class);
        condition.createCriteria()
                .andEqualTo("opponentOrderTable", tableName)
                .andEqualTo("opponentOrderTableUniqueId", tableUniqueId);
        List<ExHedgeOrder> byCondition = exHedgeOrderMapper.selectByCondition(condition);
        if (!CollectionUtils.isEmpty(byCondition)) {
            return byCondition.get(0);
        }
        return null;
    }
}
