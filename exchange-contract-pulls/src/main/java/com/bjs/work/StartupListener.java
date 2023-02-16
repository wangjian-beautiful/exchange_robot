package com.bjs.work;

import com.bjs.ws.EchoClient;
import com.neovisionaries.ws.client.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private JedisCluster jedisCluster;
    @Value("${symbols}")
    private String symbols;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /**
         *系统存在两个容器，一个是root application context,另一个是我们自己的projectName application context
         * (root application context 的子容器)，这种情况会造成onApplicationEvent执行两次。为了避免执行两次的情况，
         * 我们可以只在root application context初始化完成后调用此逻辑代码，其他容器初始化完成不做任何处理。
         */
        if (event.getApplicationContext().getParent() == null) {
            try {
                starterGcex();
            } catch (Exception e) {

            }
        }

    }


    private void starterGcex() throws Exception {
        String wsUrl = "wss://futuresws.gcex41.com/kline-api/ws";
        WebSocket ws = EchoClient.newClient(wsUrl).setWebSocketAdapter(EchoClient.gcexWsAdapter()).connect();
        String msg ="{\"event\":\"sub\",\"params\":{\"channel\":\"market_e_%s_ticker\",\"cb_id\":\"e_%s\"}}";
        for (String s : symbols.split(",")) {
            String format = String.format(msg, s, s);
            System.out.println(format);
            ws.sendText(String.format(msg,s,s));
        }

    }

}
