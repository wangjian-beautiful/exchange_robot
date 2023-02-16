package com.jeesuite.admin.dao.entity;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.jeesuite.admin.common.EnumConst;
import com.jeesuite.admin.dao.BaseEntity;
import com.jeesuite.admin.service.TaskTradeService;
import com.jeesuite.admin.util.Constants;
import com.jeesuite.admin.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
//import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * description:机器人交易配置表
 * author:User
 * date:2019/5/4
 */
//@Slf4j
@Data
@Table(name = "trade_schedule")
@Slf4j
public class TradeScheduleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户access_key
     */
    @Column(name = "access_key")
    private String accessKey;
    /**
     * 用户security_key
     */
    @Column(name = "security_key")
    private String securityKey;
    /**
     * 类型 kline K线机器人，trade刷量机器人
     * 这个属性即将弃用
     */
    @Column(name = "type")
    private String type;
    /**
     * 是否查询订单 0不查询订单 1查询订单
     */
//    @Column(name = "query")
//    private Integer query;
    /**
     * 交易对
     */
    @Column(name = "currency")
    private String currency;

    /**
     * 交易对别名（内部使用）
     */
    @Column(name = "currency_alias")
    private String currencyAlias;


    public String getCurrencyAlias() {

        if (StrUtil.isNotBlank(currencyAlias)) {
            return currencyAlias;
        }

        return currency;
    }

    /**
     * 跟随的交易对-可单一交易对，也可多个交易对乘法计算
     */
    @Column(name = "currency_trail")
    private String currencyTrail;
    /**
     * 跟随类型 1价格跟随 2波动率跟随【火币】
     */
    @Column(name = "follow_type")
    private String followType;
    /**
     * 跟随交易所类型1火币 2ZB 3组合计算
     */
    @Column(name = "follow_market")
    private String followMarket;
    /**
     * 跟随的交易对 价格比例（%）- 价格跟随和非组合计算生效
     */
//    private Double priceTrailScale;
    /**
     * 跟随的交易对 数量比例（%）- 价格跟随和非组合计算生效
     */
//    private Double countTrailScale;
    /**
     * 波动率-波动率跟随有效
     */
    @Column(name = "fluctuation_ratio")
    private Double fluctuationRatio;
    /**
     * 交易间隔
     */
    @Column(name = "duration")
    private Long duration;
    /**
     * 买卖一档与行情价间隔
     */
    @Column(name = "level1_price_gap")
    private BigDecimal level1PriceGap;
    /**
     * 价格波动，主动下单
     */
    @Column(name = "active_wave_price")
    private BigDecimal activeWavePrice;
    /**
     * 主动下单的时间间隔，避免下单速率太快
     */
    @Column(name = "active_duration")
    private Long activeDuration;
    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Long startTime;
    /**
     * 交易持续时间
     */
    @Column(name = "end_time")
    private Long endTime;
    /**
     * 交易最大量
     */
//    private BigDecimal maxAmount;
    /**
     * 交易最小量
     */
//    private BigDecimal minAmount;
    /**
     * 交易API地址
     */
    @Column(name = "server_url")
    private String serverUrl;
    /**
     * 下单状态0不下单 1下单
     */
    @Column(name = "status")
    private String status;
    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 密码配置
     */
    @Column(name = "password")
    private String password;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 最低价格
     */
    @Column(name = "min_price")
    private BigDecimal minPrice;
    /**
     * 最高价格
     */
    @Column(name = "max_price")
    private BigDecimal maxPrice;
    /**
     * 交易所备注
     */
    @Column(name = "channel")
    private String channel;
    /**
     * 机器人用户ID-在业务系统中的用户ID
     * trade刷量机器人时配置
     */
    @Column(name = "fuid")
    private Integer fuid;
    /**
     * 交易对单一盘口最大量，例如100.00
     * trade刷量机器人时配置
     */
//    private Double priceMaxSum;
    /**
     * 盘口K线机器人下单倍率 下单量=maxAmount*klineFold
     * 默认 2
     */
//    private Integer klineFold;
    /**
     * 交易对盘口密度间隔，例如0.001
     * trade刷量机器人时配置
     */
//    private Integer priceInterval;
    /**
     * 交易对盘口纵深数量，例如20
     * trade刷量机器人时配置
     */
    @Column(name = "price_count")
    private Integer priceCount;
    //盘口整理时间
//    @Column(name = "refresh_time")
//    private Integer refreshTime;

    /**
     * robot关注的交易员的ids，前几档价格，交易员未成交挂单总额阀值；
     */
//    @Column(name = "traderids")
//    private String traderids;

    //交易员未成交挂单总额的阀值
//    @Column(name = "threshold")
//    private BigDecimal threshold;

    //盘口几档
//    @Column(name = "handicap_count")
//    private Integer handicapCount;
    //    //robot盘口配置类型，0表示旧盘口配置参数生效，1表示新盘口配置参数生效
//    private Integer handicapType;
    //robot新盘口配置，盘口档位下单数量随机数乘以的倍数，默认为1，可以为0.001，0.003，5，0.5等
    @Column(name = "handicap_random_fold")
    private BigDecimal handicapRandomFold;
    //robot新盘口配置，盘口价格间隔，默认为1;
    @Column(name = "handicap_price_gap")
    private BigDecimal handicapPriceGap;
    //robot新盘口配置，盘口深度形状，0表示抛物线形，1表示线性，默认为0，
    @Column(name = "handicap_shape")
    private Integer handicapShape;

    //robot新盘口配置，盘口形状是1直线型时有效的属性，随机数区间的开始值
    @Column(name = "random_begin")
    private BigDecimal randomBegin;
    //robot新盘口配置，盘口形状是1直线型时有效的属性，随机数区间的结束值
    @Column(name = "random_end")
    private BigDecimal randomEnd;

    //robot新盘口配置，K线下单数量随机数乘以的倍数，默认为1，可以为0.001，0.003，5，0.5等
    @Column(name = "kline_random_fold")
    private BigDecimal klineRandomFold;

    /**
     * 用户access_key
     */
    @Column(name = "server_index")
    private String serverIndex;


    @Transient
    private String followTypeString;
    @Transient
    private String followMarketString;
    @Transient
    private String statusString;

    public String getFollowTypeString() {
        if (EnumConst.ROBOT_FOLLOW_TYPE_PRICE.VALUE.equals(this.followType)) {
            return EnumConst.ROBOT_FOLLOW_TYPE_PRICE.NAME;
        } else if (EnumConst.ROBOT_FOLLOW_TYPE_WAVE.VALUE.equals(this.followType)) {
            return EnumConst.ROBOT_FOLLOW_TYPE_WAVE.NAME;
        }
        return followTypeString;
    }

    public String getFollowMarketString() {
        if (EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.VALUE.equals(this.followMarket)) {
            return EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.NAME;
        } else if (EnumConst.ROBOT_FOLLOW_MARKET_MEXC.VALUE.equals(this.followMarket)) {
            return EnumConst.ROBOT_FOLLOW_MARKET_MEXC.NAME;
        }
//        else if (EnumConst.ROBOT_FOLLOW_MARKET_COMBINATION.VALUE.equals(this.followMarket)) {
//            return EnumConst.ROBOT_FOLLOW_MARKET_COMBINATION.NAME;
//        }
        return followMarketString;
    }

    public String getStatusString() {
        if (EnumConst.ROBOT_SATATUS_NO_ORDER.VALUE.equals(this.status)) {
            return EnumConst.ROBOT_SATATUS_NO_ORDER.NAME;
        } else if (EnumConst.ROBOT_SATATUS_ORDER.VALUE.equals(this.status)) {
            return EnumConst.ROBOT_SATATUS_ORDER.NAME;
        }
        return statusString;
    }

    private static TaskTradeService tradeService;

    public static TaskTradeService getTradeService() {
        if (tradeService == null) {
            tradeService = SpringUtil.getBean(TaskTradeService.class);
        }
        return tradeService;
    }

    @Transient
    private AtomicReference<BigDecimal> lastKlinePrice = new AtomicReference<>(BigDecimal.ZERO);
    @Transient
    private AtomicReference<BigDecimal> accumulateVolume = new AtomicReference<>(BigDecimal.ZERO);

    @Transient
    private BigDecimal curKlinePrice = BigDecimal.ZERO;

    @Transient
    private AtomicLong lastKlineTime = new AtomicLong(0L);

    @Transient
    private boolean stopped = false;

    @Transient
    private EventLoopGroup oldLoopGroup = null;

    @Transient
    private Channel ch = null;

    /**
     * @Author Doctor
     * @Date 14:24 2022/8/6
     * @Description 上次批量下单的订单id集合
     * @return
     **/
    @Transient
    private JSONArray lastOrderIds = new JSONArray();

    /**
     * @Author Doctor
     * @Date 17:09 2022/5/30
     * @Description quartz定时任务，用于获取httpPrice的
     **/
    @Transient
    private ScheduledFuture<?> scheduledFuture;

    @Transient
    private ExecutorService executorService;

    public boolean stop() {
        return stop(true);
    }

    /**
     * @return
     * @Author Doctor
     * @Date 14:30 2022/4/11
     * @Description 停止机器人
     **/
    public boolean stop(boolean delMap) {
        this.stopped = true;
        if (this.ch != null && this.ch.isOpen()) {
            this.ch.close();
        }
        if (this.oldLoopGroup != null) {
            this.oldLoopGroup.shutdownGracefully();
            this.oldLoopGroup = null;
        }
        if (delMap) {
            if (Constants.MAP_TRADESCHEDULE.containsKey(this.getCurrency())) {
                Constants.MAP_TRADESCHEDULE.remove(this.getCurrency());
            }
        }

        //关闭httpPrice
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
//        try {
//            getTradeService().chedanV3(this);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }

        return true;
    }

    public void initChedanV3(){
        try {
            getTradeService().chedanV3(this);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void changePrice(BigDecimal price) {
        if (this.stopped) {
            return;
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        long now = System.currentTimeMillis();

        if (!canExec(now, lastKlineTime, price, lastKlinePrice)) {
            return;
        }

        execPrice(price, now);
    }

    private synchronized void execPrice(BigDecimal price, long now) {

        System.out.println(String.format("exec %s,price:%s", this.currency, price.toPlainString()));
        System.out.println(String.format("duration:%d,now:%d,last:%d", this.duration, now, lastKlineTime.get()));

        curKlinePrice = price;

//        if(lastKlinePrice.compareTo(price ) == 0 && now - lastKlineTime  < 600){
//            return;

//        }

//        if(lastKlinePrice.compareTo(BigDecimal.ZERO) != 0){
//            return;
//        }
//        lastKlinePrice = new BigDecimal(41760);
//
//        getTradeService().postHoldHandicapSix(this );


        lastKlineTime.set(now);

        lastKlinePrice.set(curKlinePrice);

        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor();
        }
        // 盘口
        executorService.execute(() -> {
            log.info("postKlineOrder --------------");
            try {
                // k线价格
                TaskTradeService.TradePrice tradePrice = TaskTradeService.TradePrice.builder().rstPrice(curKlinePrice)
                        .price(curKlinePrice).build();
                getTradeService().postKlineOrder(tradePrice, this);
            }catch (Exception e){
                log.error("postKlineOrder error,{}", e);
            }

            log.info("postHoldHandicapSixV3 --------------");
            try {
                getTradeService().postHoldHandicapSixV3(this);
            } catch (Exception e) {
                log.error("postHoldHandicapSixV3 error,{}", e);
            }


            log.info("end --------------");
        });

//        executorService.execute(() -> {
//        });
    }

    private boolean canExec(long now, AtomicLong lastKlineTime, BigDecimal price, AtomicReference<BigDecimal> lastKlinePrice) {
        //价格优先, 价格波动大于主动下单价格,需要执行
        if (now - lastKlineTime.get() > this.getActiveDuration()) {
            if (price.subtract(lastKlinePrice.get()).abs().compareTo(this.activeWavePrice) >= 0) {
                return true;
            }
        }
        if (now - lastKlineTime.get() < this.duration) {
            return false;
        }

        return true;
    }

    public void randomPrice() {
        int random = getRandom();

        if (this.currency.contains("btc")) {
            changePrice(new BigDecimal(40_000 + random));
        } else if (this.currency.contains("eth")) {
            changePrice(new BigDecimal(2_000 + random));
        }

    }

    private int getRandom() {
        int max = 30;
        int min = 10;
        Random random = new Random();

        int s = random.nextInt(max) % (max - min + 1) + min;

        return s;
    }


    public void addAccumulateVolume(BigDecimal volume) {
        while (true) {
            BigDecimal prev = accumulateVolume.get();
            BigDecimal next = prev.add(volume);
            if (accumulateVolume.compareAndSet(prev, next)) {
                break;
            } else {
                log.info("Optimistic lock competition");
            }
        }
    }

    public BigDecimal getAccumulateVolumeAndReset() {
        while (true) {
            BigDecimal prev = accumulateVolume.get();
            BigDecimal next = BigDecimal.ZERO;
            if (accumulateVolume.compareAndSet(prev, next)) {
                return prev;
            } else {
                log.info("Optimistic lock competition");
            }
        }
    }

    public BigDecimal getVolumeWithRateForDifferentPlatforms() {
        BigDecimal volume = getAccumulateVolumeAndReset();
        if (EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.VALUE.equals(getFollowMarket())) {
            return NumberUtil.div(volume, EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.getVolumeMultiple());

        } else if (EnumConst.ROBOT_FOLLOW_MARKET_MEXC.VALUE.equals(getFollowMarket())) {
            return NumberUtil.div(volume, EnumConst.ROBOT_FOLLOW_MARKET_MEXC.getVolumeMultiple());
        }
        return volume;
    }
}
