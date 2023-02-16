package com.bjs.hedge.framework.platform;


import com.alibaba.fastjson.JSON;
import com.bjs.hedge.common.Constants;
import com.bjs.hedge.common.enums.TradePlatform;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.crud.service.HedgeRobotConfigService;
import com.bjs.hedge.framework.platform.binance.BinanceTradeService;
import com.bjs.hedge.framework.platform.mexc.MexcTradeService;
import com.bjs.hedge.util.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 下单接口
 */
@Component
@Slf4j
public abstract class TradeService {

    @Autowired
    HedgeRobotConfigService hedgeRobotConfigService;

    public static TradeService useTradePlatform(TradePlatform tradePlatform){
        TradeService tradePlatService =null;
        if (tradePlatform == null){
            HedgeRobotConfig hedgeRobotConfig = SpringUtils.getBean(HedgeRobotConfigService.class).selectConfigBySymbol(Constants.SYMBOL_UNIFIED_DEFAULT_NAME);
            tradePlatform = TradePlatform.getPlatformByName(hedgeRobotConfig.getTradePlatform());
        }
        switch (tradePlatform){
            case BINANCE:
                tradePlatService = SpringUtils.getBean(BinanceTradeService.class);
                break;
            case MEXC:
                tradePlatService = SpringUtils.getBean(MexcTradeService.class);
                break;
        }
        return tradePlatService;
    }


    /**
     * 现货下单
     *
     * @param exHedgeOrder
     * @return
     */
    public  abstract JSON newOrder(ExHedgeOrder exHedgeOrder, HedgeRobotConfig hedgeRobotConfig);

    /**
     * 获取历史成交订单
     *
     * @param exHedgeOrder
     * @return
     */
    public abstract JSON myTrades(ExHedgeOrder exHedgeOrder);

    /**
     * 查询当前订单
     *
     * @param timestamp
     * @return
     */
    public abstract JSON getOrder(ExHedgeOrder order, Long timestamp) ;

    /**
     * 查询所有订单
     *
     * @param timestamp
     * @return
     */
    public abstract JSON getOrders(ExHedgeOrder order, Long timestamp);

    /**
     * 获取账号信息
     *
     * @return
     */
    public abstract JSON account();

}
