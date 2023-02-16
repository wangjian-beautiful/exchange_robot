package com.jeesuite.admin.ws.client;

import lombok.Data;
import lombok.experimental.Accessors;

import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * @author baixiaozheng
 * @desc ${DESCRIPTION}
 * @date 2018/8/29 下午7:13
 */
@Data
@Accessors(chain = true)
public class ChannelVo {

    private URI uri;

    private String host;

    private String scheme;

    private int port;

    private List<String> message;

    /**
     * 开始重连的时间，用于检测重连超时问题
     */
    private Date beginReconnectTime;
}
