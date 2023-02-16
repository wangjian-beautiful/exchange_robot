package com.jeesuite.admin.ws.client;

import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.proxy.ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.resolver.NoopAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class WebSocketClient {
//    public static Map<String, EventLoopGroup> URI_CONNECT_MAP = new ConcurrentHashMap<>();


    public void connect(ChannelVo vo, TradeScheduleEntity tradeScheduleEntity) {
        String uriKey = getUriKey(vo);

        if(tradeScheduleEntity.getOldLoopGroup() != null){
            EventLoopGroup oldGroup = tradeScheduleEntity.getOldLoopGroup();
            if(oldGroup != null ) {
                oldGroup.shutdownGracefully();
            }
            tradeScheduleEntity.setOldLoopGroup(null);
        }

        EventLoopGroup group = null;
        boolean flag = false; // 出现异常时设置为true,为true时，清空EventLoopGroup
        while (true){
            try {
                System.out.println("---------开始连接：" + uriKey);
                flag = false;
                group = new NioEventLoopGroup();
                if (!"ws".equalsIgnoreCase(vo.getScheme()) && !"wss".equalsIgnoreCase(vo.getScheme())) {
                    System.err.println("Only WS(S) is supported.");
                    return;
                }

                tradeScheduleEntity.setOldLoopGroup(group );

                final boolean ssl = "wss".equalsIgnoreCase(vo.getScheme());
                final SslContext sslCtx;
                if (ssl) {
                    sslCtx = SslContextBuilder.forClient()
                            .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                } else {
                    sslCtx = null;
                }

                WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                        vo.getUri(), WebSocketVersion.V13, null, false, new DefaultHttpHeaders());
                final WebSocketClientHandler handler =
                        new WebSocketClientHandler(vo,
                                handshaker, vo.getBeginReconnectTime(), tradeScheduleEntity);

                Bootstrap b = new Bootstrap();

                ProxyHandler proxy = getProxy();
                if(proxy != null ) {
                    b.resolver(NoopAddressResolverGroup.INSTANCE);
                }
                b.group(group)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                ChannelPipeline p = ch.pipeline();
                                if(proxy != null ) {
                                    p.addFirst("proxy", proxy );
                                }
                                if (sslCtx != null) {
                                    p.addLast(sslCtx.newHandler(ch.alloc(), vo.getHost(), vo.getPort()));
                                }
                                p.addLast(new ReadTimeoutHandler(600));
                                p.addLast(
                                        new HttpClientCodec(),
                                        new HttpObjectAggregator(8192),
//CompresstionHandler 继承了ExtensionHandler  会识别不了permessage-deflate; server_no_context_takeover; client_max_window_bits=15 握手header
//										WebSocketClientCompressionHandler.INSTANCE,
                                        handler);
                            }
                        });

                Channel ch = b.connect(vo.getHost(), vo.getPort()).sync().channel();
                tradeScheduleEntity.setCh(ch );
                //订阅消息
//				executorService.execute(()->{
//					try {
//						subChannels(vo, ch);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				});

                handler.handshakeFuture().sync();
//				handshaker.handshake(ch).syncUninterruptibly();

                subChannels(vo, ch);
                break;

            } catch (Exception e) {
                flag = true;
                log.error("WebSocketClient", e);
            }finally {
                if ( flag && null != group) {
                    try {
                        //释放连接之前，先休眠。避免有一条无法释放的channel
                        Thread.sleep(3000);
                        group.shutdownGracefully().sync();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    group = null;
                }
            }
        }
    }

    private String getUriKey(ChannelVo vo) {
        String uri = vo.getUri().toString();

        Iterator<String> iterator = vo.getMessage().iterator();
        while (iterator.hasNext() ){
            String m = iterator.next();
            uri += " -> " + m;
        }

        return uri;
    }

    private ProxyHandler getProxy() {
        String socksProxyHost = System.getProperties().getProperty("cusSocksProxyHost");
        if(StringUtils.isBlank(socksProxyHost )){
            return null;
        }
        String socksProxyPort = System.getProperties().getProperty("cusSocksProxyPort", "1080");
        return new Socks5ProxyHandler(new InetSocketAddress(socksProxyHost, Integer.valueOf(socksProxyPort )) );
    }

    private void subChannels(ChannelVo vo, Channel ch) {
        List<String> msgs = new ArrayList<String>();
        if (vo.getMessage() != null && vo.getMessage().size() > 0) {
            msgs.addAll(vo.getMessage());
        }

        for (int i = 0; i < msgs.size(); i++) {
            WebSocketFrame frame = new TextWebSocketFrame(msgs.get(i));
            ch.writeAndFlush(frame);
        }
    }
}

