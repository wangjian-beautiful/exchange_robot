package com.jeesuite.admin.conf;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: Doctor
 * @Date: 2022/9/8 10:38
 * @Description:
 */
@Slf4j
@Configuration
public class RedisConfig {
    @Value("${redis.model:single}")
    private String model;
    @Value("${redis.hostName:127.0.0.1}")
    private String hostName;
    @Value("${redis.port:6379}")
    private Integer port;
    @Value("${redis.timeout:20}")
    private Integer timeout;
    @Value("${redis.cluster}")
    private String clusterNodes;

    @Value("${redis.password}")
    private String password;

    //最大空闲数
    @Value("${redis.maxIdle:20}")
    private Integer maxIdle;
    //连接池的最大数据库连接数
    @Value("${redis.maxTotal:30}")
    private Integer maxTotal;
    //最大建立连接等待时间
    @Value("${redis.maxWaitMillis:20000}")
    private Long maxWaitMillis;
    //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
    @Value("${redis.minEvictableIdleTimeMillis:1800000}")
    private Long minEvictableIdleTimeMillis;
    //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
    @Value("${redis.numTestsPerEvictionRun:3}")
    private Integer numTestsPerEvictionRun;
    //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
    @Value("${redis.timeBetweenEvictionRunsMillis:-1}")
    private Long timeBetweenEvictionRunsMillis;
    //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
    @Value("${redis.testOnBorrow:false}")
    private Boolean testOnBorrow;
    //在空闲时检查有效性, 默认false
    @Value("${redis.testWhileIdle:false}")
    private Boolean testWhileIdle;



    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        if(StringUtils.isEmpty(clusterNodes) ){
            return redisClusterConfiguration;
        }

        String[] serverArray = clusterNodes.split(",");
        Set<RedisNode> nodes = new HashSet<RedisNode>();
        for (String ipPort : serverArray) {
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
        }
        redisClusterConfiguration.setClusterNodes(nodes);

        if(StrUtil.isNotBlank(password)) {
            redisClusterConfiguration.setPassword(password);
        }

        return redisClusterConfiguration;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPoolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);

        return jedisPoolConfig;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory factory;
        if("cluster".equalsIgnoreCase(model) ){
            factory = new JedisConnectionFactory(redisClusterConfiguration(), jedisPoolConfig() );
        } else {
            factory = new JedisConnectionFactory(jedisPoolConfig() );
            factory.setHostName(hostName);
            if(! StringUtils.isEmpty(password )) {
                factory.setPassword(password);
            }
            factory.setPort(port);
            factory.setTimeout(timeout );
        }

        factory.setUsePool(true);

        factory.afterPropertiesSet();

        return factory;

    }

    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory() );

        RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
