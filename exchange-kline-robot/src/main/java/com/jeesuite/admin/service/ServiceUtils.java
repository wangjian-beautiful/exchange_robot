package com.jeesuite.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.model.OrderBean;
import com.jeesuite.admin.util.ApiUtil;
import com.jeesuite.admin.util.MathUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
public class ServiceUtils {
    /**
     * 获取盘口500个订单
     *
     * @param apiUtil
     * @param tradeSchedule
     * @return
     */
    public static List<OrderBean> getOrdersList(ApiUtil apiUtil, TradeScheduleEntity tradeSchedule) {
        String currency = tradeSchedule.getCurrency();
        JSONObject jsonObject = apiUtil.getOrders(currency, 500);
        String code = jsonObject.get("code").toString();
        if (code.equalsIgnoreCase("0000") == false) {
            return null;
        }
        List<Object> list = (List<Object>) JSONObject.parseObject(jsonObject.get("data").toString(), List.class);
        if (list == null || list.size() == 0) {
            return null;
        }
        List<OrderBean> beanList = new ArrayList<>();
        for (Object obj : list) {
            OrderBean bean = JSONObject.parseObject(obj.toString(), OrderBean.class);
            beanList.add(bean);
        }
        return beanList;
    }

    /**
     * 获取最后价格
     *
     * @param apiUtil
     * @param tradeSchedule
     * @return
     */
    public static BigDecimal getTickerPrice(ApiUtil apiUtil, TradeScheduleEntity tradeSchedule) {
        try {
            //使用最新价
            JSONObject ticker = apiUtil.getTicker(tradeSchedule.getCurrency());
            if (ticker != null && ticker.getBigDecimal("last").compareTo(BigDecimal.ZERO) > 0) {
                return ticker.getBigDecimal("last");
            }
        } catch (Exception e) {
            log.error("【价格】无法获取自有价格 ->{}", tradeSchedule.getCurrency());
            return null;
        }
        return null;
    }

    /**
     * 机器人是否可以执行
     * 2.判断机器人是否禁用
     * 3.判断机器人是否在时间段
     *
     */

//    public static TradeScheduleEntity isKlineSchedule(TradeScheduleService tradeScheduleService, TradeScheduleEntity tradeSchedule){
//        tradeSchedule = tradeScheduleService.selectByPrimaryKey(tradeSchedule.getId());
//        if (tradeSchedule == null || !tradeSchedule.getStatus().equalsIgnoreCase("1")) {
//            return null;
//        }
//        LocalDateTime localDateTime = LocalDateTime.now();
//        if (localDateTime.getHour() < tradeSchedule.getStartTime() || localDateTime.getHour() > tradeSchedule.getEndTime()) {
//            return null;
//        }
//        //间隔时间
//        Long duration = tradeSchedule.getDuration();//交易间隔 秒-K线
//        duration = (duration == null ? 10 : duration);//默认10秒
//        if(getTimeGap(Constants.MAP_KLINE_SCHEDULE_DATE, tradeSchedule.getCurrency()) < duration.intValue() * 1000){
//            return null;
//        }
//        Constants.MAP_KLINE_SCHEDULE_DATE.put(tradeSchedule.getCurrency(),new Date());
//        return tradeSchedule;
//    }
//
//    public static TradeScheduleEntity isHandicapSchedule(TradeScheduleService tradeScheduleService, TradeScheduleEntity tradeSchedule) {
//        tradeSchedule = tradeScheduleService.selectByPrimaryKey(tradeSchedule.getId());
//        if (tradeSchedule == null || !tradeSchedule.getStatus().equalsIgnoreCase("1")) {
//            return null;
//        }
//        LocalDateTime localDateTime = LocalDateTime.now();
//        if (localDateTime.getHour() < tradeSchedule.getStartTime() || localDateTime.getHour() > tradeSchedule.getEndTime()) {
//            return null;
//        }
//        //间隔时间
//        Integer refreshTime = tradeSchedule.getRefreshTime();//盘口整理时间 秒-盘口
//        refreshTime = (refreshTime == null ? 60 : refreshTime);//默认20秒
//        if(getTimeGap(Constants.MAP_HANDICAP_SCHEDULE_DATE, tradeSchedule.getCurrency()) < refreshTime.intValue() * 1000){
//            return null;
//        }
//        Constants.MAP_HANDICAP_SCHEDULE_DATE.put(tradeSchedule.getCurrency(),new Date());
//        return tradeSchedule;
//    }

//    private static long getTimeGap(Map<String, Date> timeMap, String key){
//        Date curDate = new Date();
//        Date lastDate = timeMap.get(key);//最后一次开始处理时间
//        if(lastDate==null){
//            timeMap.put(key, curDate);
//            return 0L;
//        }else{
//            return curDate.getTime()-lastDate.getTime();
//        }
//    }

    /**
     * 获取一个降序Map
     *
     * @return
     */
//    public static Map<String, BigDecimal> getMap() {
//        Map<String, BigDecimal> map = new TreeMap<String, BigDecimal>(
//                new Comparator<String>() {
//                    public int compare(String obj1, String obj2) {
//                        // KEY 降序排序
//                        return obj2.compareTo(obj1);
//                    }
//                });
//        return map;
//    }

    public static Map<String, BigDecimal> getMapEx() {
        Map<String, BigDecimal> map = new TreeMap<String, BigDecimal>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // KEY 降序排序
                        Integer key1 = Integer.valueOf(obj1);
                        Integer key2 = Integer.valueOf(obj2);
                        if(key2>key1){
                            return 1;
                        }else if(key2<key1){
                            return -1;
                        }else {
                            return 0;
                        }
                    }
                });
        return map;
    }

    private static String priceToKey(BigDecimal price, BigDecimal priceGap){
        int keyidx = price.divide(priceGap,12,RoundingMode.DOWN).setScale(0,BigDecimal.ROUND_FLOOR).intValue();//向下取整，舍弃小数部分
        return String.valueOf(keyidx);
    }

    public static List<Object> getOrderPriceEx(List<OrderBean> list, TradeScheduleEntity tradeSchedule) {
        Integer userId = tradeSchedule.getFuid();//机器人用户ID
        BigDecimal priceGap = tradeSchedule.getHandicapPriceGap();
        Map<String, BigDecimal> buyMap = ServiceUtils.getMapEx();//买盘价格和数量-降序
        Map<String, BigDecimal> sellMap = ServiceUtils.getMapEx();//卖盘价格和数量-降序
        Map<String, List<OrderBean>> buyRobotListMap = new HashMap<>();//某价格的 机器人挂的委托买单
        Map<String, List<OrderBean>> sellRobotListMap = new HashMap<>();//某价格的 机器人挂的委托卖单
        Map<String, List<OrderBean>> buyAllListMap = new HashMap<>();//某价格的 所有人挂的委托买单
        Map<String, List<OrderBean>> sellAllListMap = new HashMap<>();//某价格的 所有人挂的委托卖单
        if (list != null && list.size() > 0) {
            for (OrderBean bean : list) {
                Integer fuid = bean.getFuid();
                BigDecimal price = bean.getPrice(); // 单价
                Integer status = bean.getStatus();// 委单状态:1未成交；2部分成交；3完全成交；4撤单处理中；5已撤销
                if (status != null && (status.intValue() == 3 || status.intValue() == 4 || status.intValue() == 5)) {
                    continue;
                }
                BigDecimal total_amount = bean.getTotal_amount();//挂单总数量
                BigDecimal trade_amount = bean.getTrade_amount(); //已成交数量
                Integer type = bean.getType();//挂单类型 0 / 1[buy / sell]
                BigDecimal other_amount = MathUtils.sub(total_amount, trade_amount);//剩余委托中的数量
                bean.setOther_amount(other_amount);
                if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }
                String priceKey = ServiceUtils.priceToKey(price,priceGap);
                if (type.intValue() == 0) {
                    BigDecimal amount = buyMap.get(priceKey);
                    if (amount == null) {
                        amount = new BigDecimal(0);
                    }
                    buyMap.put(priceKey, MathUtils.add(amount, other_amount, 12));
                    //价位的所有订单
                    List<OrderBean> mapAllList = buyAllListMap.get(priceKey);
                    if (mapAllList == null)
                        mapAllList = new ArrayList<>();
                    mapAllList.add(bean);
                    buyAllListMap.put(priceKey, mapAllList);
                    //如果是机器人的订单，则收集起来
                    if (fuid != null && userId != null && fuid.intValue() == userId.intValue()) {
                        List<OrderBean> mapList = buyRobotListMap.get(priceKey);
                        if (mapList == null)
                            mapList = new ArrayList<>();
                        mapList.add(bean);
                        buyRobotListMap.put(priceKey, mapList);
                    }
                } else {
                    BigDecimal amount = sellMap.get(priceKey);

                    if (amount == null) {
                        amount = new BigDecimal(0);
                    }
                    sellMap.put(priceKey, MathUtils.add(amount, other_amount, 12));
                    //价位的所有订单
                    List<OrderBean> mapAllList = sellAllListMap.get(priceKey);
                    if (mapAllList == null)
                        mapAllList = new ArrayList<>();
                    mapAllList.add(bean);
                    sellAllListMap.put(priceKey, mapAllList);
                    //如果是机器人的订单，则收集起来
                    if (fuid != null && userId != null & fuid.intValue() == userId.intValue()) {
                        List<OrderBean> mapList = sellRobotListMap.get(priceKey);
                        if (mapList == null)
                            mapList = new ArrayList<>();
                        mapList.add(bean);
                        sellRobotListMap.put(priceKey, mapList);
                    }
                }
            }
        }
        List<Object> listObj = new ArrayList<>();
        listObj.add(buyMap);
        listObj.add(sellMap);
        listObj.add(buyRobotListMap);
        listObj.add(sellRobotListMap);
        listObj.add(buyAllListMap);
        listObj.add(sellAllListMap);
        return listObj;
    }



}
