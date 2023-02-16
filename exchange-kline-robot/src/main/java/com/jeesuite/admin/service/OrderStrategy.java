package com.jeesuite.admin.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.dao.entity.ExOrder;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.util.RobotBjs;
import com.jeesuite.admin.vo.TradeAddVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 下单策略服务
 */
@Service
@Slf4j
public class OrderStrategy {
    /**
     * 撤单
     *
     * @param rs            机器人服务
     * @param cdprice       撤单的价格表
     * @param ccode         币队符号
     * @param type          交易类型 买单-1 买单-0
     * @param tradeSchedule 机器人交易配置
     */
    /*public void chedan(RobotSix rs, JSONObject cdprice, String ccode, String type, TradeScheduleEntity tradeSchedule) {
        if (cdprice == null || cdprice.isEmpty()) {
            return;
        }
//        long average = tradeSchedule.getRefreshTime()*1000 / cdprice.size();
//        executorService.execute(()->{
//            cdprice.forEach((k,v)->{
//                try {
//                    Thread.sleep(randomTime(average) );
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                rs.cancelOrderByPrice(ccode,String.valueOf(k),type);
//            });
//        });

//        executorService.execute(()-> {
//        });
        String priceList = cdprice.keySet().stream().collect(Collectors.joining("`"));
        rs.cancelOrderByPriceList(ccode, priceList, type);

    }

    *//**
     * 根据平均休眠时间随机
     *
     * @param average 平均休眠时间
     * @return
     *//*
    private long randomTime(long average) {
        int max = 140;
        int min = 60;
        Random random = new Random();

        int s = random.nextInt(max) % (max - min + 1) + min;
        //生成一个90%-110%之间的休眠时间

        return average * s / 100;
    }

    //单次执行的数量
    private final static int BATCH = 5;

    *//**
     * 补单
     *
     * @param rs            机器人服务
     * @param order         订单价格表
     * @param bcode         币种符号
     * @param priceGap
     * @param scale         币种精度
     * @param type          交易类型 买单-1 买单-0
     * @param tradeSchedule 机器人交易配置
     *//*
    public void budan(RobotSix rs, JSONObject order, String bcode, BigDecimal priceGap, int scale, String type, TradeScheduleEntity tradeSchedule) {
        if (order == null || order.isEmpty()) {
            return;
        }

        List<TradeAddVo> tradeAddVos = makeTradeAddVos(order, type, priceGap, scale);

        if (tradeAddVos != null && !tradeAddVos.isEmpty()) {
            rs.batchCreateOrderEx(bcode, tradeAddVos);
        }
    }*/


    /**
     * @param order          订单价格表
     * @param type           交易类型 买单-1 买单-0
     * @param priceGap
     * @param scale          币种精度
     * @param level1PriceGap
     * @return
     * @Author Doctor
     * @Date 15:33 2022/5/27
     * @Description 生成交易集合
     **/
    private List<TradeAddVo> makeTradeAddVos(JSONObject order, String type, BigDecimal priceGap, int scale, BigDecimal level1PriceGap) {

        Iterator<String> keyIter = order.keySet().iterator();

        List<TradeAddVo> tradeAddVos = new ArrayList<>();

        while (keyIter.hasNext()) {
            String k = keyIter.next();
            Object v = order.get(k);
            BigDecimal price = keyToPrice(Integer.valueOf(k).intValue(), priceGap, scale, level1PriceGap, type);

            tradeAddVos.add(TradeAddVo.builder()
                    .type(Integer.valueOf(type)).fake(0)
                    .price(price.toPlainString())
                    .num(String.valueOf(v)).build());
        }

        return tradeAddVos;

    }

    private BigDecimal keyToPrice(int keyidx, BigDecimal priceGap, int scale, BigDecimal level1PriceGap, String type) {
        //先降低一档，买方 index +1 卖方 index - 1
        if("1".equals(type ) ){//买
            keyidx += 1;
        }else {
            keyidx -= 1;
        }
        BigDecimal downPrice = priceGap.multiply(new BigDecimal(keyidx));
        BigDecimal rstPrice = downPrice.add(priceGap.multiply(new BigDecimal(Math.random())));
        if("1".equals(type) ){//买 需要减去1挡价格间隔
            rstPrice = rstPrice.subtract(level1PriceGap ).setScale(scale, RoundingMode.DOWN);
        }else {
            rstPrice = rstPrice.add(level1PriceGap ).setScale(scale, RoundingMode.DOWN);
        }
        return rstPrice;
    }

    /**
     * @return
     * @Author Doctor
     * @Date 17:12 2022/3/24
     * @Description 新版撤单
     **/
    /*public void newChedan(RobotSix rs, BigDecimal curPrice, String ccode, TradeScheduleEntity tradeSchedule) {
        rs.cancelRobotOrder(ccode, curPrice.toPlainString());
    }*/

    /**
     * @param rs            机器人服务
     * @param bsell         卖方价格表
     * @param bbuy          买方价格表
     * @param priceGap
     * @param scale         币种精度
     * @param tradeSchedule 机器人交易配置
     * @param curPrice      当前行情价
     * @return
     * @Author Doctor
     * @Date 14:37 2022/5/27
     * @Description 撤补单一体方法 <br/>
     * 写成一体化的好处在于，可以让撮合那边一边撤单一边补单，不至于全部撤完,出现空盘口。
     **/
    public void chebudan(RobotBjs rs, JSONObject bsell, JSONObject bbuy, BigDecimal priceGap, int scale, TradeScheduleEntity tradeSchedule, BigDecimal curPrice) {


        List<TradeAddVo> sellTrades = makeTradeAddVos(bsell, "0", priceGap, scale, tradeSchedule.getLevel1PriceGap() );
//        TradeAddVo sellTradeFake = makeFakeTrade(sellTrades, "0", scale);

        List<TradeAddVo> buyTrades = makeTradeAddVos(bbuy, "1", priceGap, scale, tradeSchedule.getLevel1PriceGap() );
//        TradeAddVo buyTradeFake = makeFakeTrade(buyTrades, "1", scale);

//        TradeCheBuVo tradeCheBuVo = TradeCheBuVo.builder()
//                .sellTrades(sellTrades).sellTradeFake(sellTradeFake)
//                .buyTrades(buyTrades).buyTradeFake(buyTradeFake)
//                .curPrice(curPrice.toPlainString())
//                .build();

//        log.error("TradeCheBuVo: {} : {}", tradeSchedule.getCurrency(), tradeCheBuVo );

        sellTrades.addAll(buyTrades );
        JSONArray orderIds = rs.replaceOrderV2(tradeSchedule.getCurrencyAlias(), sellTrades, tradeSchedule.getLastOrderIds());

        tradeSchedule.setLastOrderIds(orderIds );
    }

    /**
     * @param rs            机器人服务
     * @param bsell         卖方价格表
     * @param bbuy          买方价格表
     * @param cdsprice
     * @param cdbprice
     * @param priceGap
     * @param scale         币种精度
     * @param tradeSchedule 机器人交易配置
     * @param curPrice      当前行情价
     * @return
     * @Author Doctor
     * @Date 14:37 2022/5/27
     * @Description 撤补单一体方法 <br/>
     * 写成一体化的好处在于，可以让撮合那边一边撤单一边补单，不至于全部撤完,出现空盘口。
     **/
    public void chebudanV3(RobotBjs rs, JSONObject bsell, JSONObject bbuy, List<ExOrder> cdsprice, List<ExOrder> cdbprice,
                           BigDecimal priceGap, int scale, TradeScheduleEntity tradeSchedule, BigDecimal curPrice) {


        List<TradeAddVo> sellTrades = makeTradeAddVos(bsell, "0", priceGap, scale, tradeSchedule.getLevel1PriceGap() );
//        TradeAddVo sellTradeFake = makeFakeTrade(sellTrades, "0", scale);

        List<TradeAddVo> buyTrades = makeTradeAddVos(bbuy, "1", priceGap, scale, tradeSchedule.getLevel1PriceGap() );
//        TradeAddVo buyTradeFake = makeFakeTrade(buyTrades, "1", scale);

//        TradeCheBuVo tradeCheBuVo = TradeCheBuVo.builder()
//                .sellTrades(sellTrades).sellTradeFake(sellTradeFake)
//                .buyTrades(buyTrades).buyTradeFake(buyTradeFake)
//                .curPrice(curPrice.toPlainString())
//                .build();

//        log.error("TradeCheBuVo: {} : {}", tradeSchedule.getCurrency(), tradeCheBuVo );

        cdsprice.addAll(cdbprice);
        sellTrades.addAll(buyTrades );
        JSONArray orderIds = rs.replaceOrderV3(tradeSchedule, sellTrades, JSONArray.parseArray(JSON.toJSONString(cdsprice)));
        if(orderIds !=null){
            tradeSchedule.setLastOrderIds(tradeSchedule.getLastOrderIds().fluentAddAll(orderIds));
        }

    }

    /**
     * @param rs            机器人服务
     * @param tradeSchedule 机器人交易配置
     * @return
     * @Author Doctor
     * @Date 14:37 2022/5/27
     * @Description 批量撤单方法 <br/>
     **/
    public void chedan(RobotBjs rs, TradeScheduleEntity tradeSchedule) {

        JSONArray orderIds = rs.replaceOrderV2(tradeSchedule.getCurrencyAlias(), new ArrayList<>(), tradeSchedule.getLastOrderIds());

        tradeSchedule.setLastOrderIds(orderIds );
    }

    /**
     * @param rs            机器人服务
     * @param tradeSchedule 机器人交易配置
     * @return
     * @Author Doctor
     * @Date 14:37 2022/5/27
     * @Description 批量撤单方法 <br/>
     **/
    public void chedanV3(RobotBjs rs, TradeScheduleEntity tradeSchedule) {

       final JSONArray orderIdsTem = tradeSchedule.getLastOrderIds();

        JSONArray orderIds = rs.replaceOrderV3(tradeSchedule, new ArrayList<>(), orderIdsTem);
        if(orderIds !=null){
            tradeSchedule.setLastOrderIds(tradeSchedule.getLastOrderIds().fluentAddAll(orderIds));
        }
    }



    /**
     * @param tradeAddVos 盘口订单集合
     * @param type        交易类型 买单-1 买单-0
     * @param scale
     * @return
     * @Author Doctor
     * @Date 15:39 2022/5/27
     * @Description 生成冰山单
     **/
    private TradeAddVo makeFakeTrade(List<TradeAddVo> tradeAddVos, String type, int scale) {
        BigDecimal fakePrice = type.equals("0") ? BigDecimal.ZERO : BigDecimal.valueOf(Long.MAX_VALUE);
        BigDecimal fakePriceRatio = type.equals("0") ? BigDecimal.valueOf(1.1) : BigDecimal.valueOf(0.9);
        TradeAddVo sideTrade = null;
        if (CollectionUtil.isEmpty(tradeAddVos)) {
            return null;
        }
        for (TradeAddVo tradeAddVo : tradeAddVos) {
            BigDecimal price = new BigDecimal(tradeAddVo.getPrice());

            if (isNewFake(price, fakePrice, type)) {
                fakePrice = price;
                sideTrade = tradeAddVo;
            }

        }

        TradeAddVo fakeTrade = BeanUtil.copyProperties(sideTrade, TradeAddVo.class);
        BigDecimal fakeNum = new BigDecimal(fakeTrade.getNum()).multiply(BigDecimal.valueOf(100_000));
        fakeTrade.setNum(fakeNum.toPlainString());
        fakeTrade.setPrice(NumberUtil.mul(fakePrice, fakePriceRatio).setScale(scale, BigDecimal.ROUND_DOWN).toPlainString());
        fakeTrade.setFake(1);

        return fakeTrade;
    }

    /**
     * @param price     现价格
     * @param fakePrice 旧冰山价
     * @param type      交易类型 买单-1 买单-0
     * @return
     * @Author Doctor
     * @Date 15:53 2022/5/27
     * @Description 判断价格是否为冰山价格
     **/
    private boolean isNewFake(BigDecimal price, BigDecimal fakePrice, String type) {
        if (type.equals("0")) {//卖方取最高卖价做冰山
            return NumberUtil.isGreater(price, fakePrice);
        }

        //买方取最低买价做冰山
        return NumberUtil.isLess(price, fakePrice);
    }
}
