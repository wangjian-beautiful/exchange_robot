package com.jeesuite.admin.service;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.dao.entity.ExOrder;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.util.BianceUtil;
import com.jeesuite.admin.util.Constants;
import com.jeesuite.admin.util.MathUtils;
import com.jeesuite.admin.util.RobotBjs;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;


/**
 * 交易服务接口
 **/
@Service
@Slf4j
public class TaskTradeService {

    @Autowired
    TradeScheduleService tradeScheduleService;
    @Autowired
    OrderStrategy orderStrategy;

    public void setAnchorPrice(TradeScheduleEntity tradeSchedule) {
        BigDecimal price = BianceUtil.getNewestPrice(getCurrency(tradeSchedule).replace("_", "").toUpperCase());
        RobotBjs rs = new RobotBjs(tradeSchedule.getServerUrl(), tradeSchedule.getAccessKey(), tradeSchedule.getSecurityKey());
        BigDecimal priceMy = rs.getLatestPriceSix(tradeSchedule.getCurrency());
        BigDecimal prs[] = new BigDecimal[2];
        prs[0] = priceMy;//jwx_usdt
        prs[1] = price;//btc_usdt
        Constants.MAP_TRADESCHEDULE_PRICE_ANCHOR.put(tradeSchedule.getCurrency(), prs);
        log.info("{} AnchorPrc: {}, {} AnchorPrc: {}", tradeSchedule.getCurrency(), priceMy, tradeSchedule.getCurrencyTrail(), price);
    }

    public void klineOrderSix(TradeScheduleEntity tradeSchedule) {
        tradeSchedule = isKlineOrderOk(tradeSchedule);
        if (tradeSchedule == null) {
            return;
        }
        TradePrice tradePrice = generatePrice(tradeSchedule);

        postKlineOrder(tradePrice, tradeSchedule);

    }

    public void postKlineOrder(TradePrice tradePrice, TradeScheduleEntity tradeSchedule) {
        RobotBjs rs = new RobotBjs(tradeSchedule.getServerUrl(), tradeSchedule.getAccessKey(), tradeSchedule.getSecurityKey());

        calcRatio(tradePrice.getRstPrice(), tradeSchedule);
        BigDecimal bdcnt = generateVolume(tradePrice.getRstPrice(), tradeSchedule);

        BigDecimal price = tradePrice.getPrice();
        if (isOkPrice(price) && isOkPrice(bdcnt)) {
            Random r = new Random();
            int i = r.nextInt(2);

//            List<TradeAddVo> tradeAddVoList = new ArrayList<>();
//
//            tradeAddVoList.add(TradeAddVo.builder().type(1).fake(0)
//                    .price(price.toPlainString())
//                    .num(bdcnt.toPlainString()).build());
//
//            tradeAddVoList.add(i, TradeAddVo.builder().type(0).fake(0)
//                    .price(price.toPlainString())
//                    .num(bdcnt.toPlainString()).build());

            rs.selfTrade(tradeSchedule.getCurrencyAlias(), i == 1 ? "BUY" : "SELL", price.toPlainString(), bdcnt.toPlainString());
//            .batchCreateOrderEx(tradeSchedule.getCurrency(), tradeAddVoList);
        } else {
            log.info("klineOrderSix currency->{}, tradePrice->{},bdcnt->{}", tradeSchedule.getCurrencyAlias(), tradePrice, bdcnt);
        }
    }

    public void postHoldHandicapSix(TradeScheduleEntity tradeSchedule) {


        //2.获得最新价，模拟出盘口价格和数量列表
        RobotBjs rs = new RobotBjs(tradeSchedule.getServerUrl(), tradeSchedule.getAccessKey(), tradeSchedule.getSecurityKey());
//        TradePrice tradePrice = generatePrice(tradeSchedule);
        BigDecimal curKlinePrice = tradeSchedule.getCurKlinePrice();
        if (curKlinePrice == null) {
            return;
        }
        if (curKlinePrice.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        TradePrice tradePrice = TradePrice.builder().rstPrice(curKlinePrice).price(curKlinePrice).build();

        calcRatio(tradePrice.getRstPrice(), tradeSchedule);

        BigDecimal curPrice = tradePrice.getPrice();
        if (curPrice == null || curPrice.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        BigDecimal priceGap = tradeSchedule.getHandicapPriceGap();
        int keyidx = priceToKey(curPrice, priceGap);
        JSONObject thdc = generateTargetHandicap(keyidx, tradeSchedule);
        JSONObject tsells = thdc.getJSONObject("sell");//卖盘价格和数量列表
        JSONObject tbuys = thdc.getJSONObject("buy");//买盘价格和数量列表
        //3.获得盘口价格数量快照
        JSONObject depth = rs.getDepthSix(tradeSchedule.getCurrency());
        JSONArray csells = depth.getJSONArray("sell");
        JSONArray cbuys = depth.getJSONArray("buy");
        //4.产生差异集合
        JSONObject cdsprice = new JSONObject();//撤单列表，在csells里面
        JSONObject cdbprice = new JSONObject();//撤单列表，在cbuys里面
        JSONObject bsell = new JSONObject();//补卖单列表
        JSONObject bbuy = new JSONObject();//补买单列表
        collectTail(tsells, cdsprice, bsell, keyidx, csells, priceGap);
        collectTail(tbuys, cdbprice, bbuy, keyidx, cbuys, priceGap);
        //5.撤单和补单

//        String bcode = tradeSchedule.getCurrency();
//
//        String ccode = tradeSchedule.getCurrency().replace("_","/").toUpperCase();
//
//        orderStrategy.newChedan(rs,curPrice, ccode, tradeSchedule);
//
//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        int scale = Constants.MAP_PRICISION.get(bcode).getIntValue("pricePrecision");
//        //补卖单
//        orderStrategy.budan(rs, bsell, bcode, priceGap ,scale, "0", tradeSchedule);
//        //补买单
//        orderStrategy.budan(rs, bbuy, bcode, priceGap ,scale, "1", tradeSchedule);

        String currency = tradeSchedule.getCurrency();
        int scale = Constants.MAP_PRICISION.get(currency).getIntValue("pricePrecision");
        orderStrategy.chebudan(rs, bsell, bbuy, priceGap, scale, tradeSchedule, curPrice);
    }

    public void postHoldHandicapSixV3(TradeScheduleEntity tradeSchedule) {


        //2.获得最新价，模拟出盘口价格和数量列表
        RobotBjs rs = new RobotBjs(tradeSchedule.getServerUrl(), tradeSchedule.getAccessKey(), tradeSchedule.getSecurityKey());
//        TradePrice tradePrice = generatePrice(tradeSchedule);
        BigDecimal curKlinePrice = tradeSchedule.getCurKlinePrice();
        if (curKlinePrice == null) {
            return;
        }
        if (curKlinePrice.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        TradePrice tradePrice = TradePrice.builder().rstPrice(curKlinePrice).price(curKlinePrice).build();

        calcRatio(tradePrice.getRstPrice(), tradeSchedule);

        BigDecimal curPrice = tradePrice.getPrice();
        if (curPrice == null || curPrice.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        BigDecimal priceGap = tradeSchedule.getHandicapPriceGap();
        int keyidx = priceToKey(curPrice, priceGap);
        JSONObject thdc = generateTargetHandicap(keyidx, tradeSchedule);
        JSONObject tsells = thdc.getJSONObject("sell");//卖盘价格和数量列表
        JSONObject tbuys = thdc.getJSONObject("buy");//买盘价格和数量列表
        //3.获得盘口价格数量快照
//        JSONObject depth = rs.getDepthSix(tradeSchedule.getCurrency());
//        JSONArray csells = depth.getJSONArray("sell");
//        JSONArray cbuys = depth.getJSONArray("buy");
        List<ExOrder> csells = new ArrayList<>();
        List<ExOrder> cbuys = new ArrayList<>();
        JSONArray orderIdsList = tradeSchedule.getLastOrderIds();
        if (orderIdsList != null) {
            for (int i = 0; i < orderIdsList.size(); i++) {
                ExOrder massCancelOrder = JSONObject.parseObject(orderIdsList.get(i).toString(), ExOrder.class);
                if ("SELL".equals(massCancelOrder.getSide())) {
                    csells.add(massCancelOrder);
                } else {
                    cbuys.add(massCancelOrder);
                }
            }
        }
        //4.产生差异集合
        List<ExOrder> cdsprice = new ArrayList<>();//撤单列表，在csells里面
        List<ExOrder> cdbprice = new ArrayList<>();//撤单列表，在cbuys里面
        JSONObject bsell = new JSONObject();//补卖单列表
        JSONObject bbuy = new JSONObject();//补买单列表
        collectTailV3(tsells, cdsprice, bsell, keyidx, csells, priceGap);
        collectTailV3(tbuys, cdbprice, bbuy, keyidx, cbuys, priceGap);
        //5.撤单和补单

//        String bcode = tradeSchedule.getCurrency();
//
//        String ccode = tradeSchedule.getCurrency().replace("_","/").toUpperCase();
//
//        orderStrategy.newChedan(rs,curPrice, ccode, tradeSchedule);
//
//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        int scale = Constants.MAP_PRICISION.get(bcode).getIntValue("pricePrecision");
//        //补卖单
//        orderStrategy.budan(rs, bsell, bcode, priceGap ,scale, "0", tradeSchedule);
//        //补买单
//        orderStrategy.budan(rs, bbuy, bcode, priceGap ,scale, "1", tradeSchedule);

        String currency = tradeSchedule.getCurrency();
        int scale = Constants.MAP_PRICISION.get(currency).getIntValue("pricePrecision");
        orderStrategy.chebudanV3(rs, bsell, bbuy, cdsprice, cdbprice, priceGap, scale, tradeSchedule, curPrice);
    }

    @Async
    public void chedan(TradeScheduleEntity tradeSchedule) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        RobotBjs rs = new RobotBjs(tradeSchedule.getServerUrl(), tradeSchedule.getAccessKey(), tradeSchedule.getSecurityKey());
        orderStrategy.chedan(rs, tradeSchedule);
    }

    @Async
    public void chedanV3(TradeScheduleEntity tradeSchedule) {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        RobotBjs rs = new RobotBjs(tradeSchedule.getServerUrl(), tradeSchedule.getAccessKey(), tradeSchedule.getSecurityKey());
        rs.cancelRestartOrder(tradeSchedule.getCurrencyAlias());
    }


    private BigDecimal generateCount(BigDecimal rstPrice, TradeScheduleEntity tradeSchedule) {
        BigDecimal klineRandomFold = tradeSchedule.getKlineRandomFold();
        BigDecimal count = new BigDecimal(1 * getFoldForLine(tradeSchedule));
        BigDecimal ratio = Constants.MAP_TRADESCHEDULE_TRAIL_RATIO.get(getCurrency(tradeSchedule))[1];
//        log.info("ratio->{},count->{}",ratio,count);
        if (ratio != null && ratio.compareTo(new BigDecimal(0.0001)) > 0) {
            BigDecimal countFold = count.multiply(new BigDecimal(10000)).multiply(klineRandomFold).multiply(ratio);
//            log.info("countFold->{}",countFold);
            if (countFold.compareTo(new BigDecimal(0)) > 0) {
                count = countFold;
            }
        }
        BigDecimal minTradeLimit = Constants.MAP_PRICISION.get(tradeSchedule.getCurrency()).getBigDecimal("minTradeLimit");
        if (count.compareTo(minTradeLimit) < 0) {//最小下单数量
            count = minTradeLimit;
        }
        int scale = Constants.MAP_PRICISION.get(tradeSchedule.getCurrency()).getIntValue("amountPrecision");
        count = count.setScale(scale, RoundingMode.DOWN);
        return count;
    }


    /**
     * 生成交易量,规则是简单的使用一个价格差的百分比 来作为一个交易量的倍数依据
     *
     * @return
     */
    private BigDecimal generateVolume(BigDecimal curPrice, TradeScheduleEntity tradeSchedule) {
        BigDecimal volume = tradeSchedule.getVolumeWithRateForDifferentPlatforms();
        if (volume.compareTo(BigDecimal.ZERO) == 0) {
            return generateCount(curPrice, tradeSchedule);
        }
        BigDecimal minTradeLimit = Constants.MAP_PRICISION.get(tradeSchedule.getCurrency()).getBigDecimal("minTradeLimit");
        if (volume.compareTo(minTradeLimit) < 0) {//最小下单数量
            volume = minTradeLimit;
        }
        int scale = Constants.MAP_PRICISION.get(tradeSchedule.getCurrency()).getIntValue("amountPrecision");
        volume = volume.setScale(scale, RoundingMode.DOWN);
        log.info("total volume:{}", volume.toPlainString());
        return volume;
    }


    private void calcRatio(BigDecimal rstPrice, TradeScheduleEntity tradeSchedule) {
        BigDecimal[] trailRatio = Constants.MAP_TRADESCHEDULE_TRAIL_RATIO.get(getCurrency(tradeSchedule));
        BigDecimal preTrailPrice = null;
        BigDecimal ratio = null;
        if (trailRatio != null) {
            preTrailPrice = trailRatio[0];
        }
        if (preTrailPrice != null && rstPrice != null) {
            ratio = rstPrice.subtract(preTrailPrice).abs().divide(preTrailPrice, 12, RoundingMode.DOWN);
//            log.info("ratio->{},preTrailPrice->{},nowTrailPrice->{}",ratio,preTrailPrice,rstPrice);
        }
        BigDecimal[] rt = {rstPrice, ratio};
        Constants.MAP_TRADESCHEDULE_TRAIL_RATIO.put(getCurrency(tradeSchedule), rt);
    }

    private String getCurrency(TradeScheduleEntity tradeSchedule) {
        String currency = tradeSchedule.getCurrency();
        String currencyTrail = tradeSchedule.getCurrencyTrail();
        if (currencyTrail != null && !currencyTrail.equals("")) {
            return currencyTrail;
        } else {
            return currency;
        }
    }

    public void httpPrice(TradeScheduleEntity tradeSchedule) {
        BigDecimal price = BianceUtil.getNewestPrice(getCurrency(tradeSchedule).replace("_", "").toUpperCase());

        tradeSchedule.changePrice(price);
    }

    @Data
    @Builder
    public static class TradePrice {
        //原始价格
        private BigDecimal rstPrice;
        //计算后的价格，有可能是价格跟随
        private BigDecimal price;
    }

    private TradePrice generatePrice(TradeScheduleEntity tradeSchedule) {
        String ft = tradeSchedule.getFollowType(); //1价格跟随 2波动率跟随
        BigDecimal rstPrice = null;
//        BigDecimal zbp = HbUtil.getNewestPrice(tradeSchedule.getCurrency().replace("_", ""));
//        BigDecimal hbp = ZbApiUtil.getNewestPrice(tradeSchedule.getCurrency());
//        log.info("zbp->{},hbp->{},bap-{}",zbp,hbp,bap);
        BigDecimal bap = BianceUtil.getNewestPrice(getCurrency(tradeSchedule).replace("_", "").toUpperCase());
        rstPrice = bap;

        int scale = Constants.MAP_PRICISION.get(tradeSchedule.getCurrency()).getIntValue("pricePrecision");
        if (ft.equals("1")) {
            return TradePrice.builder().price(rstPrice.setScale(scale, RoundingMode.DOWN))
                    .rstPrice(rstPrice).build();
        } else if (ft.equals("2")) {
            Double fr = MathUtils.getDouble(tradeSchedule.getFluctuationRatio(), 1d);//波动率倍数
            BigDecimal jwxAnchorPrc = null;
            BigDecimal trailAnchorPrc = null;
            Map<String, BigDecimal[]> priceAnchorMap = Constants.MAP_TRADESCHEDULE_PRICE_ANCHOR;//某时刻自动或手动设置的价格锚点
            BigDecimal[] anchorPrices = priceAnchorMap.get(tradeSchedule.getCurrency());
            if (anchorPrices == null) {
                setAnchorPrice(tradeSchedule);
                log.info("{}  3 锚点为空，设置锚点", tradeSchedule.getCurrency());
                return null;
            } else {
                jwxAnchorPrc = anchorPrices[0];
                trailAnchorPrc = anchorPrices[1];
                if ((jwxAnchorPrc == null || jwxAnchorPrc.compareTo(BigDecimal.ZERO) <= 0 || jwxAnchorPrc.compareTo(new BigDecimal(0.0000001)) <= 0) ||
                        (trailAnchorPrc == null || trailAnchorPrc.compareTo(BigDecimal.ZERO) <= 0 || trailAnchorPrc.compareTo(new BigDecimal(0.0000001)) <= 0)) {
                    setAnchorPrice(tradeSchedule);
                    log.info("{}  4 锚点为空，设置锚点", tradeSchedule.getCurrency());
                    return null;
                }
            }
            BigDecimal bdp = ((((rstPrice.subtract(trailAnchorPrc)).multiply(new BigDecimal(fr))).add(trailAnchorPrc)).multiply(jwxAnchorPrc)).divide(trailAnchorPrc, scale, BigDecimal.ROUND_DOWN);
            log.info("{}-- AnchorPrc: {} nowPrc: {} {}-- trailAnchorPrc: {} trailNowPrc: {}", tradeSchedule.getCurrency(), jwxAnchorPrc, bdp, tradeSchedule.getCurrencyTrail(), trailAnchorPrc, rstPrice);
            return TradePrice.builder().price(bdp)
                    .rstPrice(rstPrice).build();
        }
        return null;
    }

    private JSONObject generateTargetHandicap(int keyidx, TradeScheduleEntity tradeSchedule) {
        JSONObject sells = new JSONObject();//卖盘价格和数量列表
        JSONObject buys = new JSONObject();//买盘价格和数量列表
        int pci = MathUtils.getInteger(tradeSchedule.getPriceCount(), 15);
        for (int i = 1; i <= pci; i++) {
            sells.put(String.valueOf(keyidx + i), ithCount(i, tradeSchedule));//卖盘模拟档，卖一到卖N，价格由低到高
            if (keyidx > i) {
                buys.put(String.valueOf(keyidx - i), ithCount(i, tradeSchedule));//买盘模拟档，买一到买N，价格由高到低
            }
        }
        JSONObject rst = new JSONObject();
        rst.put("sell", sells);
        rst.put("buy", buys);
        return rst;
    }

    // tbuys--模拟买盘，cdprice--撤单的放在这里，bbuy--补单的放在这里，keyidx--中心id，cbuys--盘口快照，gap--间隔
    private void collectTail(JSONObject tbuys, JSONObject cdprice, JSONObject bbuy,
                             int keyidx, JSONArray cbuys, BigDecimal gap) {
//        JSONObject msells = getKeyidx2cnt(cbuys, gap);
//        JSONObject cdpriceidx = new JSONObject();//撤单keyidx
//        msells.forEach((k, v) -> {//收集尾单keyidx
//            if (!tbuys.containsKey(k)) {
//                cdpriceidx.put(k, v);
//            }
//        });
//        tbuys.forEach((k, v) -> {
//            BigDecimal cnt = msells.getBigDecimal(k);
//            if (notOkAmountSix(Integer.valueOf(k).intValue(), (BigDecimal) v, cnt, keyidx)) {
//                cdpriceidx.put(k, cnt);//不符合规则的单keyidx
//                bbuy.put(k, v);//需要补的买单
//            }
//        });
//        cbuys.forEach(item -> {//收集真实撤单价格
//            JSONArray it = (JSONArray) item;
//            BigDecimal price = it.getBigDecimal(0);
//            String priceKey = String.valueOf(priceToKey(price, gap));
//            if (cdpriceidx.containsKey(priceKey)) {
//                cdprice.put(price.toPlainString(), it.getBigDecimal(1));
//            }
//        });

        //2022年08月10日17:22:03 屏蔽所有盘口对比，因为币金所没有根据价格撤单的方法，所以只能全撤单，就不需要对比盘口了
        tbuys.forEach((k, v) -> {
            bbuy.put(k, v);//需要补的买单
        });
    }

    private void collectTailV3(JSONObject tbuys, List<ExOrder> cdprice, JSONObject bbuy,
                               int keyidx, List<ExOrder> cbuys, BigDecimal gap) {
        JSONObject msells = getKeyidx2cntV3(cbuys, gap);
        JSONObject cdpriceidx = new JSONObject();//撤单keyidx
        msells.forEach((k, v) -> {//收集尾单keyidx
            if (!tbuys.containsKey(k)) {
                cdpriceidx.put(k, v);
            }
        });
        tbuys.forEach((k, v) -> {
            BigDecimal cnt = msells.getBigDecimal(k);
            if (notOkAmountSix(Integer.valueOf(k).intValue(), (BigDecimal) v, cnt, keyidx)) {
                cdpriceidx.put(k, cnt);//不符合规则的单keyidx
                bbuy.put(k, v);//需要补的买单
            }
        });
        cbuys.forEach(item -> {//收集真实撤单价格
            BigDecimal price = item.getPrice();
            String priceKey = String.valueOf(priceToKey(price, gap));
            if (cdpriceidx.containsKey(priceKey)) {
                cdprice.add(item);
            }
        });

        //2022年08月10日17:22:03 屏蔽所有盘口对比，因为币金所没有根据价格撤单的方法，所以只能全撤单，就不需要对比盘口了
//        tbuys.forEach((k, v) -> {
//            bbuy.put(k, v);//需要补的买单
//        });
    }

    //间隔一定要大于等于buys本身的价格间隔
    private JSONObject getKeyidx2cnt(JSONArray buys, BigDecimal priceGap) {
        JSONObject idx2cnt = new JSONObject();
        buys.forEach((item) -> {
            JSONArray it = (JSONArray) item;
            BigDecimal price = it.getBigDecimal(0);
            BigDecimal count = it.getBigDecimal(1);
            String key = String.valueOf(priceToKey(price, priceGap));
            BigDecimal icnt = idx2cnt.getBigDecimal(key);
            if (icnt != null && icnt.compareTo(BigDecimal.ZERO) > 0) {
                idx2cnt.put(key, icnt.add(count));
            } else {
                idx2cnt.put(key, count);
            }
        });
        return idx2cnt;
    }

    //间隔一定要大于等于buys本身的价格间隔
    private JSONObject getKeyidx2cntV3(List<ExOrder> buys, BigDecimal priceGap) {
        JSONObject idx2cnt = new JSONObject();
        buys.forEach((item) -> {
            BigDecimal price = item.getPrice();
            BigDecimal count = item.getVolume();
            String key = String.valueOf(priceToKey(price, priceGap));
            BigDecimal icnt = idx2cnt.getBigDecimal(key);
            if (icnt != null && icnt.compareTo(BigDecimal.ZERO) > 0) {
                idx2cnt.put(key, icnt.add(count));
            } else {
                idx2cnt.put(key, count);
            }
        });
        return idx2cnt;
    }

    private int priceToKey(BigDecimal price, BigDecimal priceGap) {
        int keyidx = price.divide(priceGap, 12, RoundingMode.DOWN).setScale(0, BigDecimal.ROUND_FLOOR).intValue();//向下取整，舍弃小数部分
        return keyidx;
    }

    private BigDecimal ithCount(int i, TradeScheduleEntity tradeSchedule) {
        int pci = tradeSchedule.getPriceCount();
        double randomFold = tradeSchedule.getHandicapRandomFold().doubleValue();
        int shapeType = tradeSchedule.getHandicapShape();
        double cnt = getFoldForLine(tradeSchedule) * randomFold;
        if (shapeType == 0) {//2次曲线
            cnt = (double) (2 * i - 1) * getFoldForLine(tradeSchedule);
        } else if (shapeType == 1) {//直线
            //do nothing
        } else if (shapeType == 2) {//3档加量
            int sec = pci / 3;
            if (i > 0 && i < pci && (i % sec == 0)) {
                cnt = 8 * cnt;
            }
        } else if (shapeType == 3) {//5档加量
            int sec = pci / 5;
            if (i > 0 && i < pci && (i % sec == 0)) {
                cnt = 8 * cnt;
            }
        } else if (shapeType == 4) {//7档加量
            int sec = pci / 7;
            if (i > 0 && i < pci && (i % sec == 0)) {
                cnt = 8 * cnt;
            }
        } else if (shapeType == 5) {//9档加量
            int sec = pci / 9;
            if (i > 0 && i < pci && (i % sec == 0)) {
                cnt = 8 * cnt;
            }
        } else if (shapeType == 6) {//直线 随机
            int b = new Random().nextInt(2 * pci - 1);
            cnt = (double) (b) * getFoldForLine(tradeSchedule);
        }
        BigDecimal bdcnt = new BigDecimal(cnt);
        String curr = tradeSchedule.getCurrency();
        int pri = Constants.MAP_PRICISION.get(curr).getIntValue("amountPrecision");
        BigDecimal minLimit = Constants.MAP_PRICISION.get(curr).getBigDecimal("minTradeLimit");
        if (bdcnt.compareTo(minLimit) < 0) {
            bdcnt = minLimit;
        }
        return bdcnt.setScale(pri, BigDecimal.ROUND_DOWN);
    }

    private double getFoldForLine(TradeScheduleEntity tradeSchedule) {
        double rb = tradeSchedule.getRandomBegin().doubleValue();
        double re = tradeSchedule.getRandomEnd().doubleValue();
        return Math.random() * (re - rb) + rb;
    }

    private boolean notOkAmountSix(int k, BigDecimal v, BigDecimal cnt, int keyidx) {
        double freshold = 0.9;
        if (Math.abs(k - keyidx) < 10) {//靠近盘口的单
            freshold = 0.8;
        }
//        if(cnt!=null){
//            log.info("freshold->{},", v.subtract(cnt).abs().divide(v,3, RoundingMode.DOWN));
//        }
//        log.info("k->{},v->{},cnt->{},keyidx->{}",k,v,cnt,keyidx);
//        if (cnt==null ||
//                (isOkPrice(v) && v.subtract(cnt).abs().divide(v,3, RoundingMode.DOWN).compareTo(new BigDecimal(freshold))>0)) {
//            return true;
//        } else {
//            return false;
//        }
        return (cnt == null || (isOkPrice(v) && v.subtract(cnt).abs().divide(v, 3, RoundingMode.DOWN).compareTo(new BigDecimal(freshold)) > 0));
    }

    private boolean isOkPrice(BigDecimal price) {
//        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0 || price.compareTo(new BigDecimal(0.0000001)) <= 0) {
//            return false;
//        } else {
//            return true;
//        }
        return !(price == null || price.compareTo(BigDecimal.ZERO) <= 0 || price.compareTo(new BigDecimal(0.0000001)) <= 0);
    }

    private TradeScheduleEntity isKlineOrderOk(TradeScheduleEntity tradeSchedule) {
        if (tradeSchedule == null || !tradeSchedule.getStatus().equalsIgnoreCase("1")) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.getHour() < tradeSchedule.getStartTime() || localDateTime.getHour() > tradeSchedule.getEndTime()) {
            return null;
        }
        //间隔时间
        Long duration = tradeSchedule.getDuration();//交易间隔 毫秒-K线
        duration = (duration == null ? 10 : duration);//默认10秒
        if (getTimeGap(Constants.MAP_KLINE_SCHEDULE_DATE, tradeSchedule.getCurrency()) < duration) {
            return null;
        }
        Constants.MAP_KLINE_SCHEDULE_DATE.put(tradeSchedule.getCurrency(), new Date());
        return tradeSchedule;
    }

    private long getTimeGap(Map<String, Date> timeMap, String key) {
        Date curDate = new Date();
        Date lastDate = timeMap.get(key);//最后一次开始处理时间
        if (lastDate == null) {
            timeMap.put(key, curDate);
            return 0L;
        } else {
            return curDate.getTime() - lastDate.getTime();
        }
    }

}
