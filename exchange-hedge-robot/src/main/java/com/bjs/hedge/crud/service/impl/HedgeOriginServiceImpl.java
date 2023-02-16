package com.bjs.hedge.crud.service.impl;

import com.bjs.hedge.crud.dao.robot.HedgeOriginMapper;
import com.bjs.hedge.crud.model.HedgeOrigin;
import com.bjs.hedge.crud.service.HedgeOriginService;
import com.bjs.hedge.crud.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * @author AUTHOR
 * @date  2022/10/19
 */
@Service
public class HedgeOriginServiceImpl extends AbstractService<HedgeOrigin> implements HedgeOriginService {
    @Resource
    private HedgeOriginMapper hedgeOriginMapper;


    @Override
    public BigDecimal sumBetting(String symbol) {
        return hedgeOriginMapper.sumBetting(symbol);
    }
}
