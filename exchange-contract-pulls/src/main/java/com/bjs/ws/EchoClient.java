package com.bjs.ws;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjs.GzipUtils;
import com.bjs.util.SpringUtils;
import com.google.common.collect.Maps;
import com.neovisionaries.ws.client.*;
import com.bjs.util.NaiveSSLContext;
import redis.clients.jedis.JedisCluster;

public class EchoClient {

    private String server = "wss://futuresws.gcex41.com/kline-api/ws";

    private int timeout = 5000;

    private WebSocketAdapter webSocketAdapter;

    private long pingWaitTime = TimeUnit.SECONDS.toMillis(9);


    /**
     * The entry point of this command line application.
     */

    public static EchoClient newClient(String server) {
        return new EchoClient().setServer(server);
    }


    public EchoClient setServer(String server) {
        this.server = server;
        return this;
    }

    public EchoClient setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public EchoClient setWebSocketAdapter(WebSocketAdapter webSocketAdapter) {
        this.webSocketAdapter = webSocketAdapter;
        return this;
    }

    /**
     * Connect to the server.
     */
    public WebSocket connect() throws IOException, WebSocketException {
        return new WebSocketFactory()
                .setSSLContext(NaiveSSLContext.getInstance("TLS"))
                .setConnectionTimeout(this.timeout)
                .setVerifyHostname(false)
                .createSocket(this.server)
                .addListener(this.webSocketAdapter)
//                .setPingInterval(pingWaitTime)
                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                .connect();
    }

    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {

        }

        return out.toByteArray();
    }


    public static WebSocketAdapter gcexWsAdapter() {
        return new EchoClient.GCEXwsAdapter();
    }

    static class GCEXwsAdapter extends WebSocketAdapter {
        @Override
        public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
            String msg = new String(uncompress(binary));
            System.out.println(msg);
//           if (msg.length()>0){
//               System.out.println("姐搜成功");
//           }
//            JedisCluster cluster = SpringUtils.getBean(JedisCluster.class);
            JSONObject json = JSON.parseObject(msg);
//            String channel = json.getString("channel");
//            Long publish = cluster.publish(channel, msg);
//            System.out.println("publish:::"+publish.toString());
//            System.out.println(publish + ": " + msg);
        }

//        @Override
//        public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
//            byte[] payload = frame.getPayload();
//            String msg = new String(uncompress(payload));
//            System.out.println(msg);
//            super.onBinaryFrame(websocket, frame);
//        }

        @Override
        public void onTextMessage(WebSocket websocket, byte[] data) throws Exception {

        }

        @Override
        public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            super.onPongFrame(websocket, frame);
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
            System.out.println("colse");
            super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
        }

        @Override
        public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
//            System.out.println("ping");
//            byte[] payload = frame.getPayload();
//            System.out.println(new String(payload));
//            String msg = new String(uncompress(payload));
//            System.out.println(msg);
//            HashMap<Object, Object> map = Maps.newHashMap();
//            map.put("pong",System.currentTimeMillis());
//            websocket.sendText(JSON.toJSONString(map));
//            super.onPingFrame(websocket, frame);
        }
    }

    public static void main(String[] args) throws Exception{
            String wsUrl = "ws://127.0.0.1:9090/ws?token=0";
//            String wsUrl = "wss://futuresws.gcex60.com/kline-api/ws";
//            String wsUrl = "wss://quizws.gcex60.com/quiz/ws";
//            String wsUrl = "wss://ws.bjs84.com/kline-api/ws";
            WebSocket ws = EchoClient.newClient(wsUrl).setWebSocketAdapter(EchoClient.gcexWsAdapter()).connect();
//        {"event":"sub","params":{"channel":"market_etcusdt_depth_step1","cb_id":"etcusdt"}}
//            String msg ="{\"event\":\"sub\",\"params\":{\"channel\":\"market_dogeusdt_depth_step0\",\"cb_id\":\"btcusdt\"}}";
//            String msg ="{\"event\":\"sub\",\"params\":{\"channel\":\"market_e_btcusdt_ticker\",\"cb_id\":\"s_btcusdt\"}}";
            String msg ="{\"event\":\"req\",\"params\":{\"channel\":\"review\"}}";
            String msg2 ="{\n" +
                    "    \"event\":\"req\",\n" +
                    "    \"params\":{\n" +
                    "        \"channel\":\"market_e_btcusdt_kline_1min\", \n" +
                    "        \"cb_id\":\"1\" \n" +
                    "    }\n" +
                    "}";
            String msg3 ="{\n" +
                    "    \"event\":\"sub\",\n" +
                    "    \"params\":{\n" +
                    "        \"channel\":\"market_e_btcusdt_kline_1min\", \n" +
                    "        \"cb_id\":\"1\" \n" +
                    "    }\n" +
                    "}";
//            ws.sendText(msg);
            ws.sendText(msg);
//            ws.sendText(msg3);

//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
//            System.out.println("pong");
//            HashMap<Object, Object> map = Maps.newHashMap();
//            map.put("pong",System.currentTimeMillis());
//            map.put("event","12");
//            ws.sendText(JSON.toJSONString(map));
//
//        }, 0, 10, TimeUnit.SECONDS);
            Thread.sleep(1000*600000);




    }

}