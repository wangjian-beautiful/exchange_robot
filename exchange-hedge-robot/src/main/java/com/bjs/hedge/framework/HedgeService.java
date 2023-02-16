package com.bjs.hedge.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjs.hedge.common.ConfigSymbolCache;
import com.bjs.hedge.common.enums.HedgePlaceOrderStatus;
import com.bjs.hedge.common.enums.HedgeSideTypes;
import com.bjs.hedge.common.enums.HedgeHandlerResp;
import com.bjs.hedge.common.enums.TradePlatform;
import com.bjs.hedge.crud.dao.exchange.CommonMapper;
import com.bjs.hedge.crud.model.ConfigSymbol;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.model.HedgeOrigin;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.crud.service.ExHedgeOrderService;
import com.bjs.hedge.framework.platform.TradeService;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.Order;
import com.bjs.hedge.util.CommonUtil;
import com.bjs.hedge.util.MathUtils;
import com.bjs.hedge.vo.BinanceOrderRespVO;
import com.bjs.hedge.vo.ExTrade;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.bjs.hedge.util.CommonUtil.*;

import static com.bjs.hedge.common.Constants.REDIS_TRY_LOCK_LEASE_SECONDS;
import static com.bjs.hedge.common.Constants.REDIS_TRY_LOCK_WAIT_SECONDS;

/**
 * 对冲处理
 */
@Slf4j
@Component
public class HedgeService {

    @Autowired
    CommonMapper commonMapper;


    @Autowired
    ExHedgeOrderService exHedgeOrderService;
    @Autowired
    HedgeTradeService hedgeTradeService;

    @Autowired
    Redisson redisson;

    @Autowired
    ConfigSymbolCache configSymbolCache;

    public HedgeHandlerResp hedgeHandler(HedgeOrigin hedgeOrigin, HedgeRobotConfig hedgeRobotConfig) throws Exception {
        String tableName = hedgeOrigin.getOriginTable();
        Long tableUniqueId = hedgeOrigin.getOriginId();
        RLock lock = redisson.getLock(getRedisLockKey(tableName, tableUniqueId));
        try {
            if (!lock.tryLock(REDIS_TRY_LOCK_WAIT_SECONDS, REDIS_TRY_LOCK_LEASE_SECONDS, TimeUnit.SECONDS)) {
                return HedgeHandlerResp.NOT_LOCK;
            }
            //是否已经下过单
            if (exHedgeOrderService.getExHedgeOrderByUniqueKey(tableName, tableUniqueId) != null) {
                return HedgeHandlerResp.HANDLED;
            }
            ExHedgeOrder hedgeOrder = generatorExHedgeOrder(hedgeOrigin, hedgeRobotConfig);
            //检查是否符合交易规则
            if (HedgeTradeRules.checkRules(hedgeOrder, hedgeRobotConfig)) {
                return HedgeHandlerResp.FILTER_RULES;
            }
            //下单
            TradePlatform tradePlatform = TradePlatform.getPlatformByName(hedgeRobotConfig.getTradePlatform());
            JSON resp = TradeService.useTradePlatform(tradePlatform).newOrder(hedgeOrder, hedgeRobotConfig);
            //检查下单
            return checkPlaceOrderRespAndSaveHedgeOrder(resp, hedgeOrder);
        } catch (Exception e) {
            throw e;
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }


    public HedgeHandlerResp checkPlaceOrderRespAndSaveHedgeOrder(JSON json, ExHedgeOrder hedgeOrder) {
        if (json == null || !(json instanceof JSONObject)) {
            return HedgeHandlerResp.FAIL;
        }
        if (TradePlatform.BINANCE.name.equals(hedgeOrder.getHedgePlatform())) {
            BinanceOrderRespVO binanceOrder = JSONObject.parseObject(json.toJSONString(), BinanceOrderRespVO.class);
            String orderId = binanceOrder.getOrderId();
            if (orderId != null) {
                //补充订单信息
                hedgeOrder.setHedgeOrderId(orderId);
                hedgeOrder.setHedgeDealMoney(binanceOrder.getCummulativeQuoteQty());
                hedgeOrder.setHedgeDealVolume(binanceOrder.getExecutedQty());
                hedgeOrder.setHedgeCreateTime(CommonUtil.getOrderTime(binanceOrder.getTime()));
                hedgeOrder.setHedgeStatus(binanceOrder.getStatus());
                hedgeOrder.setHedgeOrderDetail(binanceOrder.getFills());
                if (exHedgeOrderService.save(hedgeOrder)) {
                    hedgeTradeService.hedgeHandlerTrade(hedgeOrder);
                    log.info("hedge handler success  OpponentOrderTable:{} OpponentOrderTableUniqueId:{} HedgeOrderId:{} ",
                            hedgeOrder.getOpponentOrderTable(),
                            hedgeOrder.getOpponentOrderTableUniqueId(),
                            hedgeOrder.getHedgeOrderId());
                    return HedgeHandlerResp.SUCCESS;
                }
                log.error("HedgeService hedgeHandler hedge newOrder success but save mysql order fail: {}", JSON.toJSONString(hedgeOrder));
                return HedgeHandlerResp.NEW_ORDER_SUCCESS_SAVE_FAIL;

            }
        }
        if (TradePlatform.MEXC.name.equals(hedgeOrder.getHedgePlatform())) {
            Order order = JSON.parseObject(json.toJSONString(), Order.class);
            if (order != null && order.getOrderId() != null) {
                //补充订单信息
                hedgeOrder.setHedgeOrderId(order.getOrderId());
                hedgeOrder.setHedgeUpdateTime(new Date());
                hedgeOrder.setHedgeCreateTime(CommonUtil.getOrderTime(order.getTime()));
                hedgeOrder.setHedgeStatus(HedgePlaceOrderStatus.Mexc.NEW.name());
                hedgeOrder.setHedgeOrderSymbol(order.getSymbol());
                if (exHedgeOrderService.save(hedgeOrder)) {
                    hedgeTradeService.hedgeHandlerTrade(hedgeOrder);
                    log.info("hedge handler success  OpponentOrderTable:{} OpponentOrderTableUniqueId:{} HedgeOrderId:{} ",
                            hedgeOrder.getOpponentOrderTable(),
                            hedgeOrder.getOpponentOrderTableUniqueId(),
                            hedgeOrder.getHedgeOrderId());
                    return HedgeHandlerResp.SUCCESS;
                }
                log.error("HedgeService hedgeHandler hedge newOrder success but save mysql order fail: {}", JSON.toJSONString(hedgeOrder));
                return HedgeHandlerResp.NEW_ORDER_SUCCESS_SAVE_FAIL;
            }
        }
        return HedgeHandlerResp.FAIL;
    }


    public ExHedgeOrder generatorExHedgeOrder(HedgeOrigin hedgeOrigin, HedgeRobotConfig hedgeRobotConfig) {
        String tableName = hedgeOrigin.getOriginTable();
        Long tableUniqueId = hedgeOrigin.getOriginId();
        ExTrade exTrade = commonMapper.getExTrade(tableName, tableUniqueId);
        ExHedgeOrder hedgeOrder = new ExHedgeOrder();
        //下单信息
        hedgeOrder.setHedgeSide(RobotListCache.getUserTrendSide(exTrade));
        hedgeOrder.setHedgeOrderSymbol(hedgeRobotConfig.getSymbol());
        BigDecimal price = exTrade.getPrice();
        BigDecimal volume = exTrade.getVolume();

        ConfigSymbol configSymbol = configSymbolCache.getConfigSymbol(hedgeOrder.getHedgeOrderSymbol());
        if(null == configSymbol){
            hedgeOrder.setHedgePrice(price != null ? price.stripTrailingZeros() : null);
            hedgeOrder.setHedgeVolume(volume != null ? volume.stripTrailingZeros(): null);
        }else{
            if (price != null && configSymbol.getPricePre() != null){

                hedgeOrder.setHedgePrice(price.setScale(configSymbol.getPricePre(),BigDecimal.ROUND_DOWN));
            }
            if (volume != null && configSymbol.getVolumePre() != null){
                hedgeOrder.setHedgeVolume(volume.setScale(configSymbol.getVolumePre(),BigDecimal.ROUND_DOWN));
            }
        }

        hedgeOrder.setHedgePlatform(hedgeRobotConfig.getTradePlatform());
        //对手信息
        hedgeOrder.setOpponentCreateTime(exTrade.getCtime());
        hedgeOrder.setOpponentOrderSymbol(hedgeRobotConfig.getSymbolBjs());
        hedgeOrder.setOpponentOrderTable(tableName);
        hedgeOrder.setOpponentOrderTableUniqueId(tableUniqueId);
        hedgeOrder.setOpponentDealMoney(MathUtils.mulScale12(exTrade.getPrice(), exTrade.getVolume()));
        hedgeOrder.setOpponentDealVolume(exTrade.getVolume());
        hedgeOrder.setOpponentSide(RobotListCache.getUserTrendSide(exTrade));
        hedgeOrder.setOpponentFee(HedgeSideTypes.BUY.name().equals(exTrade.getTrendSide()) ? exTrade.getBuyFee() : exTrade.getSellFee());
        return hedgeOrder;
    }


}
