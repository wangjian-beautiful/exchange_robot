package com.bjs.hedge.common;

import com.bjs.hedge.crud.dao.exchange.CommonMapper;
import com.bjs.hedge.crud.model.ConfigSymbol;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConfigSymbolCache {

    @Autowired
    CommonMapper commonMapper;

    private static final ConcurrentHashMap<String, ConfigSymbol> cache = new ConcurrentHashMap();

    private static final ConcurrentHashMap<String, Long> cacheExpire = new ConcurrentHashMap();
    /**
     * 过期时间
     */
    public static final long EXPIRE_MILLS = 1000 * 60 * 5;

    public ConfigSymbol getConfigSymbol(String symbol) {
        if (StringUtils.isNotBlank(symbol)) {
            symbol = symbol.toLowerCase();
            if (cache.containsKey(symbol) && cacheExpire.containsKey(symbol)) {
                //是否过期
                if (System.currentTimeMillis() - cacheExpire.get(symbol) > EXPIRE_MILLS) {
                    ConfigSymbol configSymbol = commonMapper.getConfigSymbolBySymbol(symbol);
                    cache.put(symbol, configSymbol);
                    cacheExpire.put(symbol, System.currentTimeMillis());
                }
                return cache.get(symbol);
            } else {
                ConfigSymbol configSymbol = commonMapper.getConfigSymbolBySymbol(symbol);
                cache.put(symbol, configSymbol);
                cacheExpire.put(symbol, System.currentTimeMillis());
                return configSymbol;
            }
        }
        return null;
    }

}
