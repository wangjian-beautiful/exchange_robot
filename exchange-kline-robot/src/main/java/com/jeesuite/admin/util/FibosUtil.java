package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @author kew
 * @create 2018-09-01 下午2:37
 **/

public class FibosUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    private static BigDecimal getRate() {
        return JSONObject.parseObject(restTemplate.getForObject("https://fibos.io/1.0/app/getExchangeInfo",String.class)).getBigDecimal("price");
    }

    public static void main(String[] args) {
        System.out.println(FibosUtil.getRate());
    }

}
