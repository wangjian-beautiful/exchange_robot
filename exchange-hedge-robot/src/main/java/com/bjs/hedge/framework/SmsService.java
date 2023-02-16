package com.bjs.hedge.framework;

import com.bjs.hedge.common.Constants;
import com.bjs.hedge.crud.service.HedgeRobotConfigService;
import com.bjs.hedge.util.ValidateCodeUtil;
import com.bjs.hedge.util.http.HttpClientRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@Slf4j
public class SmsService {

    @Value("${sms.openapi.url}")
    private String smsUrl;

    @Value("${sms.openapi.token}")
    private String token;


    @Autowired
    HedgeRobotConfigService hedgeRobotConfigService;

    public void sendSMs(String symbol, String content) {
        String mobiles = null;
        try {
            mobiles = hedgeRobotConfigService.selectConfigBySymbol(symbol).getMobile();
            if (StringUtils.isBlank(mobiles)) {
                mobiles = hedgeRobotConfigService.selectConfigBySymbol(Constants.SYMBOL_UNIFIED_DEFAULT_NAME).getMobile();
            }
            if (StringUtils.isNotBlank(mobiles)) {
                String[] mobile = mobiles.split(",");
                for (String mobileCountryCode : mobile) {
                    String[] mobileAndCountryCode = mobileCountryCode.split("\\s+");
                    if (mobileAndCountryCode.length == 2) {
                        log.info("send sms phone:{}\tmessage:{}", StringUtils.join(mobileAndCountryCode, " "), content);
//                        ValidateCodeUtil.sendSmsBySmsbao(mobileAndCountryCode[0], mobileAndCountryCode[1], content);
                        //切换接口方式
                        sendSMsOpenApi(mobileAndCountryCode[0], mobileAndCountryCode[1], content);
                    }
                }
            }
        } catch (Exception e) {
            log.error("发送短信错误{}\t{}", mobiles, e);
        }
    }

    Boolean sendSMsOpenApi(String countryCode, String mobile, String msg) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("api_key", token);
        params.put("country", countryCode);
        params.put("mobile", mobile);
        params.put("content", msg);
        try {
            String s = HttpClientRequest.post(smsUrl, params, null);
            log.info("sendSMsOpenApi result{}", s);
            return true;
        } catch (Exception e) {
            log.error("sendSMsOpenApi error ", e);
        }
        return false;
    }



}
