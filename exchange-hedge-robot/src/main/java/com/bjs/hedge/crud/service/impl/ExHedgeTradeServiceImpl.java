package com.bjs.hedge.crud.service.impl;

import com.bjs.hedge.crud.dao.robot.ExHedgeTradeMapper;
import com.bjs.hedge.crud.model.ExHedgeTrade;
import com.bjs.hedge.crud.service.ExHedgeTradeService;
import com.bjs.hedge.crud.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author AUTHOR
 * @date  2022/10/12
 */
@Service
public class ExHedgeTradeServiceImpl extends AbstractService<ExHedgeTrade> implements ExHedgeTradeService {
    @Resource
    private ExHedgeTradeMapper exHedgeTradeMapper;

}
