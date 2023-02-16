package com.bjs.hedge.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;


public class RedisTemplateSerializerUtils {
    public static byte[] serializeKey(RedisTemplate redisTemplate, Object key){
        Assert.notNull(key, "non null key required");
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        return keySerializer == null && key instanceof byte[] ? ((byte[])key) : keySerializer.serialize(key);
    }
}
