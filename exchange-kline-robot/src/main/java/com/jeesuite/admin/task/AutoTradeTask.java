package com.jeesuite.admin.task;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.common.EnumConst;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.service.TaskTradeService;
import com.jeesuite.admin.service.TradeScheduleService;
import com.jeesuite.admin.util.Constants;
import com.jeesuite.admin.util.RobotBjs;
import com.jeesuite.admin.ws.client.ChannelVo;
import com.jeesuite.admin.ws.client.WebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AutoTradeTask {

    @Autowired
    TaskTradeService tradeService;
    //    @Autowired
//    TradeOrderRepository tradeOrderRepository;
    @Resource
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    TradeScheduleService tradeScheduleService;


    @Value("${robot.symbols}")
    private String robotSymbols;
    @Value("${robot.config}")
    private String isConfig;

    @Value("${PODNAME:pod-0}")
    private String podName;

    private Integer serverIndex = 10;

    @PostConstruct
    public void init() {
        String[] split = podName.split("-");
        this.serverIndex = Integer.valueOf(split[split.length - 1] );
    }

    //    @Scheduled(initialDelay = 1000, fixedDelay = 10000)
    @Scheduled(cron = "0/2 * * * * ?")
    public void checkSchedule() {
        if(destroyed ){
            return;
        }
        String existsCurrency = null;
        if(CollUtil.isNotEmpty(Constants.MAP_TRADESCHEDULE ) ) {
            existsCurrency = "'" + Constants.MAP_TRADESCHEDULE.keySet().stream().collect(Collectors.joining("','")) + "'";
        }
//        System.out.println("1234");
        if (StringUtils.isNotBlank(robotSymbols)) {
            List<TradeScheduleEntity> tradeSchedules = null;
            if ("true".equalsIgnoreCase(isConfig)) {
                tradeSchedules = tradeScheduleService.findListInCurrency(robotSymbols, existsCurrency,serverIndex);
            } else {
                tradeSchedules = tradeScheduleService.findListAll(existsCurrency,serverIndex);
            }

            //启动循环
            tradeSchedules.forEach(this::startTradeTask);
        }
    }

    private void startTradeTask(TradeScheduleEntity tradeSchedule) {
        if(destroyed ){
            return;
        }
        if (Constants.MAP_TRADESCHEDULE.containsKey(tradeSchedule.getCurrency())) {
            return;
        }

        RobotBjs rs = new RobotBjs(tradeSchedule.getServerUrl(), tradeSchedule.getAccessKey(), tradeSchedule.getSecurityKey());
        JSONObject pri = rs.getPricision(tradeSchedule.getCurrencyAlias());
        Constants.MAP_PRICISION.put(tradeSchedule.getCurrency(), pri);

        //这句代码放在前面,因为下面connect  方法出现问题的时候，这句放到后面就执行不到了，但是connect方法内部的 handler 又会有 reconnect 机制,
        //所以当connect出现问题的时候，定时任务也会不停的执行新的连接.
        Constants.MAP_TRADESCHEDULE.put(tradeSchedule.getCurrency(), tradeSchedule);

//        threadPoolTaskScheduler.scheduleWithFixedDelay(() -> {
//            tradeService.klineOrderSix(tradeSchedule);
//        }, 50);

//            webSocketClient.connect(config("wss://stream.binance.com:9443/ws/btcusdt@ticker", Collections.EMPTY_LIST),tradeSchedule);
//        webSocketClient.connect(config("wss://stream.binance.com/stream", new ArrayList<String>(){{add("{\"method\":\"SUBSCRIBE\",\"params\":[\"btcusdt@aggTrade\"],\"id\":2}");}}),tradeSchedule);


        if(EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.VALUE.equals(tradeSchedule.getFollowMarket()) ){

            new WebSocketClient().connect(config("wss://stream.binance.com/stream",
                    new ArrayList<String>() {{
                        add(String.format("{\"method\":\"SUBSCRIBE\",\"params\":[\"%s@aggTrade\"],\"id\":1}",
                                tradeSchedule.getCurrency().replace("_", "")));
                    }}), tradeSchedule);

            //做一个双保险，万一ws被屏蔽掉、或者连接异常。http还会执行
            ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.scheduleWithFixedDelay(() -> {
                tradeService.httpPrice(tradeSchedule);
            }, 1000);

            tradeSchedule.setScheduledFuture(scheduledFuture);
        } else if(EnumConst.ROBOT_FOLLOW_MARKET_MEXC.VALUE.equals(tradeSchedule.getFollowMarket()) ){
            new WebSocketClient().connect(config("wss://wbs.mexc.com/ws",
                    new ArrayList<String>() {{
                        add(String.format("{\"method\":\"SUBSCRIPTION\",\"params\":[\"spot@public.aggre.deals@%s\"],\"id\":3}",
                                tradeSchedule.getCurrency().toUpperCase()) );
                    }}), tradeSchedule);
        }

        //初始化 清除流动性订单
        tradeSchedule.initChedanV3();

//        threadPoolTaskScheduler.scheduleWithFixedDelay(() -> {
//            tradeService.holdHandicapSix(tradeSchedule);
//        }, 200);

    }


    private static ChannelVo config(String url, List<String> channel) {
        URI uri = null;
        String scheme = null;
        String host = null;
        int port = 0;
        try {
            uri = new URI(url);
            scheme = uri.getScheme() == null ? "ws" : uri.getScheme();
            host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
            port = 443;
            if (uri.getPort() == -1) {
                if ("ws".equalsIgnoreCase(scheme)) {
                    port = 80;
                } else if ("wss".equalsIgnoreCase(scheme)) {
                    port = 443;
                } else {
                    port = -1;
                }
            } else {
                port = uri.getPort();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new ChannelVo().setUri(uri).setHost(host)
                .setPort(port).setScheme(scheme).setMessage(channel);
    }

    private boolean destroyed = false;

    public void destroy(){
        log.error("开始destroy。。。。。。");
        destroyed = true;

        Iterator<String> iterator = Constants.MAP_TRADESCHEDULE.keySet().iterator();
        while (iterator.hasNext() ){
            String nextKey = iterator.next();
            Constants.MAP_TRADESCHEDULE.get(nextKey ).stop(false);
            iterator.remove();
        }

        log.error("结束destroy。。。。。。");
    }
}
