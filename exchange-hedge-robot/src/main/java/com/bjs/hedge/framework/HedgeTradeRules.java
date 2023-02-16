package com.bjs.hedge.framework;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjs.hedge.common.enums.HedgePlaceOrderTypes;
import com.bjs.hedge.common.enums.TradePlatform;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.MexcApiV3NoneAuthExample;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.ExchangeInfo;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.Symbol;
import com.bjs.hedge.util.MathUtils;
import com.bjs.hedge.util.http.HttpClientRequest;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author yolo
 * 对冲平台交易规则
 */
@Slf4j
public class HedgeTradeRules {

    private static Map<String, Symbol> mexcSymbolRules = null;
    private static Map<String, JSONArray> binanceSymbolRules = null;

    static class Binance {
        enum FilterType {
            PRICE_FILTER("价格过滤器") {
                @Override
                public boolean check(JSONObject jsonObject, ExHedgeOrder hedgeOrder) {
                    BigDecimal price = hedgeOrder.getHedgePrice().stripTrailingZeros();
                    String symbol = hedgeOrder.getHedgeOrderSymbol();
                    BigDecimal minPrice = jsonObject.getBigDecimal("minPrice");
                    BigDecimal maxPrice = jsonObject.getBigDecimal("maxPrice");
                    BigDecimal tickSize = jsonObject.getBigDecimal("tickSize");
                    if (compare(price, minPrice, CompareSide.LT)) {
                        log.error("币安 币对:{} 价格小于允许的最小值 {} < {} ", symbol, price, minPrice);
                        return true;
                    }
                    if (compare(price, maxPrice, CompareSide.GT)) {
                        log.error("币安 币对:{} 价格大于允许的最大值 {} > {} ", symbol, price, maxPrice);
                        return true;
                    }
                    //TODO 按照币安文档中描述的交易规则写的，感觉跟真实交易不符， 先不用
//                    if (price != null && tickSize != null && !(price.divideAndRemainder(tickSize)[1].compareTo(BigDecimal.ZERO) == 0)) {
//                        log.error("币安 币对:{} price必须等于minPrice+(tickSize的整数倍) price % tickSize == 0  price:{} ", symbol,price);
////                        int scale = tickSize.scale();
//                        return true;
//                    }
                    return false;
                }
            },
            LOT_SIZE("下单量过滤器") {
                @Override
                public boolean check(JSONObject jsonObject, ExHedgeOrder hedgeOrder) {
                    BigDecimal volume = hedgeOrder.getHedgeVolume().stripTrailingZeros();
                    BigDecimal minQty = jsonObject.getBigDecimal("minQty");
                    BigDecimal maxQty = jsonObject.getBigDecimal("maxQty");
                    BigDecimal stepSize = jsonObject.getBigDecimal("stepSize");
                    if (compare(volume, minQty, CompareSide.LT)) {
                        log.error("币安 下单量小于允许的最小值 {} < {} ", volume, minQty);
                        return true;
                    }
                    if (compare(volume, maxQty, CompareSide.GT)) {
                        log.error("币安 下单量大于允许的最大值 {} > {} ", volume, maxQty);
                        return true;
                    }

                    ////   (quantity-minQty) % stepSize == 0
                    if (volume != null && minQty != null && stepSize != null && !(volume.subtract(minQty).divideAndRemainder(stepSize)[1].compareTo(BigDecimal.ZERO) == 0)) {
                        log.error("币安 下单量不等于允许的步进值 (quantity-minQty) % stepSize == 0  quantity:{},minQty:{},stepSize:{}", volume, minQty, stepSize);
                       if(stepSize != null){
                           int scale = stepSize.stripTrailingZeros().scale();
                           log.info("等于允许的步进值{}",scale);
                           hedgeOrder.setHedgeVolume(MathUtils.toScaleNum(volume,scale));
                       }
//                        return true;
                    }
                    return false;
                }
            },
            MIN_NOTIONAL("最小名义价值过滤器") {
                @Override
                public boolean check(JSONObject jsonObject, ExHedgeOrder hedgeOrder) {
                    BigDecimal volume = hedgeOrder.getHedgeVolume().stripTrailingZeros();
                    BigDecimal price = hedgeOrder.getHedgePrice().stripTrailingZeros();
                    BigDecimal maxNotional = jsonObject.getBigDecimal("maxNotional");
                    BigDecimal minNotional = jsonObject.getBigDecimal("minNotional");
                    BigDecimal notional = volume.multiply(price);
                    //订单的名义价值 (单价 x 数量, price * quantity)
                    if (compare(notional, maxNotional, CompareSide.GT)) {
                        log.error("币安 下单金额大于允许的最大值 {} > {} ", notional, maxNotional);
                        return true;
                    }

                    if (compare(notional,minNotional, CompareSide.LT)) {
                        log.error("币安 下单金额小于于允许的最小值 {} < {} ", notional, minNotional);
                        return true;
                    }
                    return false;
                }
            };

            FilterType(String name) {
                this.name = name;
            }

            private String name;

            boolean check(JSONObject jsonObject, ExHedgeOrder hedgeOrder) {
                throw new RuntimeException("");
            }

            static FilterType getFilterType(String name) {
                for (FilterType value : FilterType.values()) {
                    if (value.name().equals(name)) {
                        return value;
                    }
                }
                return null;
            }
        }

        static JSONArray getFilters(String symbol) {
            if (binanceSymbolRules == null) {
                binanceSymbolRules = new HashMap<>();
            }
            if (binanceSymbolRules.containsKey(symbol)) {
                return binanceSymbolRules.get(symbol);
            }
            String url = "https://api.binance.com/api/v3/exchangeInfo?symbol=" + symbol.toUpperCase();
            try {
                String resp = HttpClientRequest.get(url, null, null);
                JSONArray symbols = JSONObject.parseObject(resp).getJSONArray("symbols");
                JSONArray filters = symbols.getJSONObject(0).getJSONArray("filters");
                binanceSymbolRules.put(symbol, filters);
                return filters;
            } catch (Exception e) {
                log.error("getFilters {} {}", url, symbol, e);
            }
            return null;
        }

        static boolean checkRules(ExHedgeOrder hedgeOrder) {
            String symbol = hedgeOrder.getHedgeOrderSymbol();
            JSONArray filters = getFilters(symbol);
            //可能由于网络原因未获取到币安交易规则，直接下单，不影响下单流程
            if (CollectionUtils.isEmpty(filters)) {
                return false;
            }
            for (Object filter : filters) {
                JSONObject filterJson = ((JSONObject) filter);
                FilterType filterType;
                if ((filterType = FilterType.getFilterType(filterJson.getString("filterType"))) != null) {
                    if (filterType.check(filterJson, hedgeOrder)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    static class Mexc {
        static {
            ExchangeInfo exchangeInfo = MexcApiV3NoneAuthExample.exchangeInfo(Maps.newHashMap(ImmutableMap.<String, String>builder().build()));
            mexcSymbolRules = exchangeInfo.getSymbols().stream().collect(Collectors.toMap(Symbol::getSymbol, Function.identity()));
        }
        static Boolean checkRules(ExHedgeOrder hedgeOrder, HedgeRobotConfig hedgeRobotConfig) {
            Symbol symbolRule = mexcSymbolRules.get(hedgeOrder.getHedgeOrderSymbol().toUpperCase());
            String originTable = hedgeOrder.getOpponentOrderTable();
            Long originId = hedgeOrder.getOpponentOrderTableUniqueId();
            String symbol = hedgeOrder.getHedgeOrderSymbol();
            BigDecimal volume = hedgeOrder.getHedgeVolume();
            BigDecimal price = hedgeOrder.getHedgePrice();
            //是否允许api现货交易
            if (!symbolRule.getIsSpotTradingAllowed()) {
                log.error("抹茶 币对：{} 不允许api现货交易 originTable:{} originId:{}", symbol, originTable, originId);
                return true;
            }
            //最大委托数量
            BigDecimal maxQuoteAmount = symbolRule.getMaxQuoteAmount();
            if (compare(volume, maxQuoteAmount, CompareSide.GT)) {
                log.error("抹茶 币对：{} 超过最大委托数量 {} >{} originTable:{} originId:{}", symbol, volume, maxQuoteAmount, originTable, originId);
                return true;
            }
            //最小下单金额
            BigDecimal quoteAmountPrecision = symbolRule.getQuoteAmountPrecision();
            switch (Objects.requireNonNull(HedgePlaceOrderTypes.getOrderType(hedgeRobotConfig.getBinanceOrderType()))) {
                case MARKET:
                    if (compare(price, quoteAmountPrecision, CompareSide.LT)) {
                        log.error("抹茶 币对：{} 小于最小下单金额 {} < {} originTable:{} originId:{}", symbol, price, quoteAmountPrecision, originTable, originId);
                        return true;
                    }
                    break;
                case LIMIT:
                    BigDecimal priceLimit = MathUtils.mulScale12(price, volume);
                    if (compare(priceLimit, quoteAmountPrecision, CompareSide.LT)) {
                        log.info("抹茶 币对：{} 小于最小下单金额 {} < {} originTable:{} originId:{}", symbol, priceLimit, quoteAmountPrecision, originTable, originId);
                        return true;
                    }
                    break;
                default:
                    break;
            }
            //最小下单数量
            BigDecimal baseSizePrecision = symbolRule.getBaseSizePrecision();
            if (compare(volume, baseSizePrecision, CompareSide.LT)) {
                log.error("抹茶 币对：{} 小于最小下单数量 {} <{} originTable:{} originId:{}", symbol, volume, baseSizePrecision, originTable, originId);
                return true;
            }
            return false;
        }
    }

    enum CompareSide {
        GT, LT
    }

    static boolean compare(BigDecimal bd1, BigDecimal bd2, CompareSide compareSide) {
        if (bd1 != null && bd2 != null) {
            switch (compareSide) {
                case GT:
                    return bd1.compareTo(bd2) > 0;
                case LT:
                    return bd1.compareTo(bd2) < 0;
            }
        }
        return false;
    }

    public static boolean checkRules(ExHedgeOrder hedgeOrder, HedgeRobotConfig robotConfig) {
        try {
            TradePlatform platform = TradePlatform.getPlatformByName(robotConfig.getTradePlatform());
            switch (Objects.requireNonNull(platform)) {
                case BINANCE:
                    return Binance.checkRules(hedgeOrder);
                case MEXC:
                    return Mexc.checkRules(hedgeOrder, robotConfig);
            }
        } catch (Exception e) {
            log.error("checkRules error", e);
        }
        return false;
    }
}
