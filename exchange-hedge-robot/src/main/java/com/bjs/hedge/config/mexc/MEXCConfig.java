package com.bjs.hedge.config.mexc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 币安配置类
 *
 */
@Component
@ConfigurationProperties(prefix = "mexc")
@Data
public class MEXCConfig
{
    public String apiKey;
    public String secretKey;
    public String baseUrl;
    public String testnetApiKey;
    public String testnetSecretKey;
    public String testUrl;
}
