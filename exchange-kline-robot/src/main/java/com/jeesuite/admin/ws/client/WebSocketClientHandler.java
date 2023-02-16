package com.jeesuite.admin.ws.client;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.common.EnumConst;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.util.SpringUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@Slf4j
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {
    private static WebSocketClient webSocketClient;

    public static WebSocketClient getWebSocketClient() {
        if (webSocketClient != null) {
            return webSocketClient;
        }
        webSocketClient = SpringUtil.getBean(WebSocketClient.class);
        return webSocketClient;
    }

    private final WebSocketClientHandshaker handshaker;
    /**
     * 开始重连的时间，用于检测重连超时问题
     */
    private Date beginReconnectTime;
    private ChannelPromise handshakeFuture;

    private ChannelVo channelVo;

    private TradeScheduleEntity tradeScheduleEntity;

    public WebSocketClientHandler(ChannelVo vo, WebSocketClientHandshaker handshaker, Date beginReconnectTime, TradeScheduleEntity tradeScheduleEntity) {
        this.channelVo = vo;
        this.handshaker = handshaker;
        this.beginReconnectTime = beginReconnectTime;
        this.tradeScheduleEntity = tradeScheduleEntity;
    }

    private static String uncompress(byte[] bytes) {

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        byte[] buffer = new byte[1024];
        try (
                final Deflate64CompressorInputStream zin = new Deflate64CompressorInputStream(in)) {

            int offset;
            while (-1 != (offset = zin.read(buffer))) {
                out.write(buffer, 0, offset);
            }
            buffer = null;
            return out.toString();
        } catch (final IOException e) {
            log.error("uncompress", e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e) {
                log.error("close Err:", e);
            }
        }
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("channelInactive WebSocket Client disconnected!");
        Channel channel = ctx.channel();
        if (null != channel) {
            channel.close();
            channel.eventLoop().parent().shutdownGracefully();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }

        reconnect(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        String result = "";
        try {
            Channel ch = ctx.channel();

            if (!handshaker.isHandshakeComplete()) {
                try {
                    handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                    log.info("WebSocket Client connected!");
                    handshakeFuture.setSuccess();
                    //握手成功，把重连时间干掉
                    clearBeginReconnectTime(ch);
                } catch (WebSocketHandshakeException e) {
                    log.info("WebSocket Client failed to connect");
                    handshakeFuture.setFailure(e);
                }
                return;
            }

            if (msg instanceof FullHttpResponse) {
                FullHttpResponse response = (FullHttpResponse) msg;
//                throw new IllegalStateException(
//                        "Unexpected FullHttpResponse (getStatus=" + response.status() +
//                                ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
            }

            WebSocketFrame frame = (WebSocketFrame) msg;
            if (frame instanceof CloseWebSocketFrame) {
                log.info("WebSocket Client received closing");
                ch.close();
                //noun 20191219新增 发生错误重连
                //  reconnect(ctx);


            }
//            else if (frame instanceof BinaryWebSocketFrame) {
//                BinaryWebSocketFrame binaryFrame = (BinaryWebSocketFrame) frame;
//                byte[] temp = new byte[binaryFrame.content().readableBytes()];
//                binaryFrame.content().readBytes(temp);
//                String str = "";
//                if (channelVo.getUri().toString().contains("okex")) {
//                    str = uncompress(temp);
//                } else {
//                    temp = GZipUtils.decompress(temp);
//                    str = new String(temp, "UTF-8");
//                }
//
//                if (str.contains("ping")) {
//                    ch.writeAndFlush(new TextWebSocketFrame(str.replace("ping", "pong")));
//                }
//                temp = null;
//                result = str;
//
//            }
            else if (frame instanceof TextWebSocketFrame) {
                TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
                result = textFrame.text();
            }
        } catch (Exception e) {
            log.error("channelRead0", e);
        }


        handleResult(result);
//        ServiceLifeCheckUtils.prolong();

    }

    private void handleResult(String result) {
//        log.info("binance:"+result);
        try {
            JSONObject tick = JSON.parseObject(result);

            BigDecimal price = pickPrice(tick);
            BigDecimal volume = totalVolume(tick);
            if (volume != null) {
                this.tradeScheduleEntity.addAccumulateVolume(volume);
            }
            if (price != null) {
                this.tradeScheduleEntity.changePrice(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BigDecimal pickPrice(JSONObject tick) {


        if (tick == null) {
            return null;
        }

        if (EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.VALUE.equals(tradeScheduleEntity.getFollowMarket())) {

            JSONObject data = tick.getJSONObject("data");
            if (data == null) {
                return null;
            }

            BigDecimal price = data.getBigDecimal("p");

            return price;
        } else if (EnumConst.ROBOT_FOLLOW_MARKET_MEXC.VALUE.equals(tradeScheduleEntity.getFollowMarket())) {

            JSONObject data = tick.getJSONObject("d");
            if (data == null) {
                return null;
            }

            JSONArray deals = data.getJSONArray("deals");
            if (CollUtil.isEmpty(deals)) {
                return null;
            }

            return deals.getJSONObject(0).getBigDecimal("p");
        }

        return null;
    }


    private BigDecimal totalVolume(JSONObject tick) {
        if (tick == null) {
            return null;
        }
        if (EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.VALUE.equals(tradeScheduleEntity.getFollowMarket())) {
            JSONObject data = tick.getJSONObject("data");
            if (data == null) {
                return null;
            }
            BigDecimal volume = data.getBigDecimal("q");
            return volume;
        } else if (EnumConst.ROBOT_FOLLOW_MARKET_MEXC.VALUE.equals(tradeScheduleEntity.getFollowMarket())) {
            JSONObject data = tick.getJSONObject("d");
            if (data == null) {
                return null;
            }
            JSONArray deals = data.getJSONArray("deals");
            if (CollUtil.isEmpty(deals)) {
                return null;
            }
            BigDecimal volume = BigDecimal.ZERO;
            for (int i = 0; i < deals.size(); i++) {
                volume = volume.add(deals.getJSONObject(0).getBigDecimal("q"));
            }
            return volume;
        }
        return null;
    }

    /**
     * 清除ctx里的开始重连时间
     *
     * @param ch
     */
    private void clearBeginReconnectTime(Channel ch) {
//		AttributeKey<Date> key = AttributeKey.valueOf("begin_reconnect_time");
//		if(! ch.hasAttr(key) ){
//			return;
//		}
//		ch.attr(key).set(null);
        this.beginReconnectTime = null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();

        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }

    private void reconnect(ChannelHandlerContext ctx) {
        if (tradeScheduleEntity.isStopped()) {
            log.error("主动中断连接！{}", tradeScheduleEntity.getCurrency());
            return;
        }
        channelVo.setBeginReconnectTime(beginReconnectTime == null ? new Date() : beginReconnectTime);
        getWebSocketClient().connect(channelVo, tradeScheduleEntity);
    }

}
