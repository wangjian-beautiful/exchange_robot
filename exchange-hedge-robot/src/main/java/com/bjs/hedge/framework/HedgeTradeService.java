package com.bjs.hedge.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bjs.hedge.common.enums.HedgePlaceOrderStatus;
import com.bjs.hedge.common.enums.HedgeSideTypes;
import com.bjs.hedge.common.enums.TradePlatform;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.model.ExHedgeTrade;
import com.bjs.hedge.crud.service.ExHedgeOrderService;
import com.bjs.hedge.crud.service.ExHedgeTradeService;
import com.bjs.hedge.framework.platform.TradeService;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.MyTrades;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.Order;
import com.bjs.hedge.util.CommonUtil;
import com.bjs.hedge.util.MathUtils;
import com.bjs.hedge.vo.BinanceOrderRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

import static com.bjs.hedge.common.Constants.*;

/**
 * 异步获取订单信息并入库
 */
@Slf4j
@Component
public class HedgeTradeService {
    @Autowired
    ExHedgeOrderService exHedgeOrderService;
    @Autowired
    ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    SmsService smsService;
    @Autowired
    ExHedgeTradeService exHedgeTradeService;

    private ConcurrentHashMap<String, ScheduledFuture> cacheSchedulers = new ConcurrentHashMap();
    private ConcurrentHashMap<String, Integer> cacheTaskCount = new ConcurrentHashMap();

    private ConcurrentHashMap<String, Integer> runErrorTryNumberMap = new ConcurrentHashMap();

    private static final int max_try_run_error = 3;

    public boolean hedgeHandlerTrade(ExHedgeOrder hedgeOrder) {
        PeriodicTrigger trigger = new PeriodicTrigger(HEDGE_ORDER_TRADE_OUT_MILLISECONDS);
        trigger.setInitialDelay(HEDGE_ORDER_TRADE_OUT_MILLISECONDS);
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(new HandlerHedgeTradeTask(hedgeOrder), trigger);
        cacheSchedulers.put(hedgeOrder.getHedgeOrderId(), scheduledFuture);
        return true;
    }


    public boolean cancelScheduler(String hedgeOrderId) {
        if (cacheSchedulers.containsKey(hedgeOrderId)) {
            boolean isCancel = cacheSchedulers.get(hedgeOrderId).cancel(false);
            cacheSchedulers.remove(hedgeOrderId);
            cacheTaskCount.remove(hedgeOrderId);
            runErrorTryNumberMap.remove(hedgeOrderId);
            return isCancel;
        }
        return false;
    }

    class HandlerHedgeTradeTask implements Runnable {
        private ExHedgeOrder hedgeOrderVO;

        HandlerHedgeTradeTask(ExHedgeOrder exhedgeOrder) {
            this.hedgeOrderVO = exhedgeOrder;
        }

        @Override
        public void run() {
            log.info("HandlerHedgeTradeTask run {}", hedgeOrderVO);
            String hedgeOrderId = hedgeOrderVO.getHedgeOrderId();
            try {
                if (!checkTryNumberAndAdd(hedgeOrderId)) {
                    return;
                }
                ExHedgeOrder hedgeOrder = exHedgeOrderService.findById(hedgeOrderId);
                TradePlatform tradePlatform = TradePlatform.getPlatformByName(hedgeOrder.getHedgePlatform());
                JSON resp = TradeService.useTradePlatform(tradePlatform).getOrder(hedgeOrder, null);
                switch (Objects.requireNonNull(tradePlatform)) {
                    case BINANCE:
                        binanceCheckOrderStatusAndUpdateHedgeOrder(resp, hedgeOrder);
                        break;
                    case MEXC:
                        mexcCheckOrderStatusAndUpdateHedgeOrder(resp, hedgeOrder);
                        break;
                    default:
                        break;
                }
                cancelScheduler(hedgeOrderId);
            } catch (Exception e) {
                log.error("HandlerHedgeTradeTask fail", e);
                handleRunError(hedgeOrderVO);
            }
        }

        /**
         * 币安 更新 对冲订单 成交记录
         *
         * @param json
         * @param hedgeOrder
         */
        public void binanceCheckOrderStatusAndUpdateHedgeOrder(JSON json, ExHedgeOrder hedgeOrder) {
            if (json != null) {
                BinanceOrderRespVO binanceOrder = JSON.parseObject(json.toString(), BinanceOrderRespVO.class);
                HedgePlaceOrderStatus.Binance binanceOrderStatus = HedgePlaceOrderStatus.getBinanceOrderStatus(binanceOrder.getStatus());
                switch (binanceOrderStatus) {
                    case NEW:
                    case REJECTED:
                    case EXPIRED:
                    case PARTIALLY_FILLED://部分成交
                        smsService.sendSMs(hedgeOrderVO.getHedgeOrderSymbol(), binanceOrder.generaSmsContext());
                        break;
                    default:
                        break;
                }
                //更新订单信息
                hedgeOrder.setHedgeStatus(binanceOrder.getStatus());
                hedgeOrder.setHedgeDealMoney(binanceOrder.getCummulativeQuoteQty());
                hedgeOrder.setHedgeDealVolume(binanceOrder.getExecutedQty());
                hedgeOrder.setHedgeUpdateTime(CommonUtil.getOrderTime(binanceOrder.getUpdateTime()));
//                if (StringUtils.isNotBlank(binanceOrder.getFills())) {
//                    hedgeOrder.setHedgeOrderDetail(binanceOrder.getFills());
//                }
                JSON tradesJSON = TradeService.useTradePlatform(TradePlatform.BINANCE).myTrades(hedgeOrder);
                saveHedgeTrade(tradesJSON, hedgeOrder);
                exHedgeOrderService.update(hedgeOrder);

            }
        }

        /**
         * 抹茶 更新 对冲订单 成交记录
         *
         * @param json
         * @param hedgeOrder
         */
        public void mexcCheckOrderStatusAndUpdateHedgeOrder(JSON json, ExHedgeOrder hedgeOrder) {
            if (json != null) {
                Order order = JSON.parseObject(json.toString(), Order.class);
                if (null == order || StringUtils.isBlank(order.getStatus())) {
                    return;
                }
                HedgePlaceOrderStatus.Mexc mexcPlaceOrderStatus = HedgePlaceOrderStatus.getMexcOrderStatus(order.getStatus());
                switch (mexcPlaceOrderStatus) {
                    case NEW:
                    case CANCELED:
                    case PARTIALLY_CANCELED:
                    case PARTIALLY_FILLED://部分成交
                        smsService.sendSMs(hedgeOrderVO.getHedgeOrderSymbol(), order.generaSmsContext());
                        break;
                    case FILLED:
                        log.info("订单已完全成交：{}", order.getOrderId());
                        break;
                    default:
                        break;
                }
                //更新订单信息
                hedgeOrder.setHedgeStatus(order.getStatus());
                hedgeOrder.setHedgeDealMoney(CommonUtil.getBigDecimalDef(order.getCummulativeQuoteQty(), null));
                hedgeOrder.setHedgeDealVolume(CommonUtil.getBigDecimalDef(order.getExecutedQty(), null));
                hedgeOrder.setHedgeCreateTime(CommonUtil.getOrderTime(order.getTime()));
                hedgeOrder.setHedgeUpdateTime(CommonUtil.getOrderTime(order.getUpdateTime()));
                JSON tradesJSON = TradeService.useTradePlatform(TradePlatform.MEXC).myTrades(hedgeOrder);
                saveHedgeTrade(tradesJSON, hedgeOrder);
                exHedgeOrderService.update(hedgeOrder);
            }
        }

        boolean checkTryNumberAndAdd(String hedgeOrderId) {
            if (cacheTaskCount.containsKey(hedgeOrderId) && cacheTaskCount.get(hedgeOrderId) >= 3) {
                cancelScheduler(hedgeOrderId);
                return false;
            } else {
                if (cacheTaskCount.containsKey(hedgeOrderId)) {
                    cacheTaskCount.put(hedgeOrderId, cacheTaskCount.get(hedgeOrderId) + 1);
                } else {
                    cacheTaskCount.put(hedgeOrderId, 1);
                }
            }
            return true;
        }
    }

    private void saveHedgeTrade(JSON tradesJSON, ExHedgeOrder hedgeOrder) {
        if (tradesJSON != null && tradesJSON instanceof JSONArray) {
//            hedgeOrder.setHedgeOrderDetail(tradesJSON.toJSONString());
            //更新成交明细
            List<ExHedgeTrade> exHedgeTrades = new ArrayList<>();
            List<MyTrades> myTrades = JSONArray.parseArray(tradesJSON.toJSONString(), MyTrades.class);
            BigDecimal quoteQtySum = BigDecimal.ZERO;// 成交金额
            BigDecimal qtySum = BigDecimal.ZERO;//成交量
            myTrades.forEach(myTrade -> {
                ExHedgeTrade exHedgeTrade = new ExHedgeTrade();
                exHedgeTrade.setOrderId(myTrade.getOrderId());
                exHedgeTrade.setTradeId(myTrade.getId());
                exHedgeTrade.setSymbol(myTrade.getSymbol());
                exHedgeTrade.setPrice(new BigDecimal(myTrade.getPrice()));
                exHedgeTrade.setQty(new BigDecimal(myTrade.getQty()));
                exHedgeTrade.setQuoteqty(new BigDecimal(myTrade.getQuoteQty()));
                exHedgeTrade.setTime(CommonUtil.getOrderTime(myTrade.getTime()));
                exHedgeTrade.setCommission(new BigDecimal(myTrade.getCommission()));
                exHedgeTrade.setPlatform(hedgeOrder.getHedgePlatform());
                exHedgeTrade.setSide(myTrade.getIsBuyer() ? HedgeSideTypes.BUY.name() : HedgeSideTypes.SELL.name());
                exHedgeTrades.add(exHedgeTrade);
                quoteQtySum.add(exHedgeTrade.getQuoteqty());
                qtySum.add(exHedgeTrade.getQty());

            });
            hedgeOrder.setHedgeDealVolume(qtySum);
            hedgeOrder.setHedgeDealMoney(quoteQtySum);
            //手续费
            BigDecimal commission = myTrades.stream().map(m -> new BigDecimal(m.getCommission())).reduce(BigDecimal.ZERO, MathUtils::addScale8);
            BigDecimal hedgePrice = hedgeOrder.getHedgePrice();
            BigDecimal opponentFee = hedgeOrder.getOpponentFee() == null ? BigDecimal.ZERO : hedgeOrder.getOpponentFee();
            BigDecimal profit;
            //计算盈利
            if (hedgeOrder.getHedgeSide().equals(HedgeSideTypes.BUY.name())) {
                //买方向  (我方成交总额 - 对冲成交总额) + (我方手续费 - 对方手续费)
                profit = hedgePrice.multiply(qtySum).subtract(quoteQtySum).add(opponentFee.subtract(commission));
            } else {
                //卖方向  (对冲成交总额 - 我方成交总额) + (我方手续费 - 对方手续费)
                profit = quoteQtySum.subtract(hedgePrice.multiply(qtySum)).add(opponentFee.subtract(commission));
            }
            hedgeOrder.setProfit(profit);
            hedgeOrder.setHedgeFee(commission);
            hedgeOrder.setHedgeUpdateTime(new Date());
            exHedgeTradeService.save(exHedgeTrades);
        }
    }

    private void handleRunError(ExHedgeOrder hedgeOrderVO) {
        String hedgeOrderId = hedgeOrderVO.getHedgeOrderId();
        if (runErrorTryNumberMap.containsKey(hedgeOrderId)) {
            if (runErrorTryNumberMap.get(hedgeOrderId) >= max_try_run_error) {
                cancelScheduler(hedgeOrderId);
                runErrorTryNumberMap.remove(hedgeOrderId);
            } else {
                runErrorTryNumberMap.put(hedgeOrderId, runErrorTryNumberMap.get(hedgeOrderId) + 1);
            }
        } else {
            runErrorTryNumberMap.put(hedgeOrderId, 1);
        }
    }
}
