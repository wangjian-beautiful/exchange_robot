package com.bjs.hedge.crud.service.impl;

import com.bjs.hedge.common.Constants;
import com.bjs.hedge.crud.dao.robot.HedgeRobotConfigMapper;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.crud.service.HedgeRobotConfigService;
import com.bjs.hedge.crud.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author AUTHOR
 * @date  2022/09/20
 */
@Service
public class HedgeRobotConfigServiceImpl extends AbstractService<HedgeRobotConfig> implements HedgeRobotConfigService {
    @Resource
    private HedgeRobotConfigMapper hedgeRobotConfigMapper;

    @Override
    public HedgeRobotConfig selectConfigBySymbol(String symbol) {
        HedgeRobotConfig params = new HedgeRobotConfig();
        params.setSymbol(symbol);
        return hedgeRobotConfigMapper.selectOne(params);

    }


    @Override
    public HedgeRobotConfig selectConfigBySymbolBjs(String symbolBjs) {
        HedgeRobotConfig params = new HedgeRobotConfig();
        params.setSymbolBjs(symbolBjs);
        return hedgeRobotConfigMapper.selectOne(params);

    }

    public HedgeRobotConfig selectConfigBySymbolIfNullDefault(String symbol) {
        HedgeRobotConfig params = new HedgeRobotConfig();
        params.setSymbol(symbol);
        HedgeRobotConfig hedgeRobotConfig = hedgeRobotConfigMapper.selectOne(params);
        if(hedgeRobotConfig == null || hedgeRobotConfig.getBinanceOrderType() == null || hedgeRobotConfig.getTimeInForceStatus() == null){
            params.setSymbol(Constants.SYMBOL_UNIFIED_DEFAULT_NAME);
            return hedgeRobotConfigMapper.selectOne(params);
        }
        return hedgeRobotConfig;
    }
}
