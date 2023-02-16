package com.bjs.hedge.crud.service;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.crud.Service;



/**
 * @author AUTHOR
 * @date  2022/09/20
 */
public interface HedgeRobotConfigService extends Service<HedgeRobotConfig> {

    HedgeRobotConfig selectConfigBySymbol(String symbol);
    HedgeRobotConfig selectConfigBySymbolIfNullDefault(String symbol);
    HedgeRobotConfig selectConfigBySymbolBjs(String symbol);
}
