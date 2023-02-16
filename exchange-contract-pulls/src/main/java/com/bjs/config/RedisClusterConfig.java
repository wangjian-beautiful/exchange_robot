package com.bjs.config;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author lili
 * @date 2020/2/21 22:22
 * @description
 */
@Configuration
public class RedisClusterConfig {

    @Value("${redis_MAX_TOTAL}")
    private int redisMaxTotal;
    @Value("${redis_MAX_IDLE}")
    private int redisMaxIdle;

    @Value("${redis_cluster_cluster_1_host}")
    private String node1host;
    @Value("${redis_cluster_cluster_1_port}")
    private int node1port;

    @Value("${redis_cluster_cluster_2_host}")
    private String node2host;
    @Value("${redis_cluster_cluster_2_port}")
    private int node2port;

    @Value("${redis_cluster_cluster_3_host}")
    private String node3host;
    @Value("${redis_cluster_cluster_3_port}")
    private int node3port;

    @Value("${redis_cluster_cluster_4_host}")
    private String node4host;
    @Value("${redis_cluster_cluster_4_port}")
    private int node4port;

    @Value("${redis_cluster_cluster_5_host}")
    private String node5host;
    @Value("${redis_cluster_cluster_5_port}")
    private int node5port;

    @Value("${redis_cluster_cluster_6_host}")
    private String node6host;
    @Value("${redis_cluster_cluster_6_port}")
    private int node6port;

    @Value("${redis_cluster_password}")
    private String password;

    @Value("${redis_SSSION_TIMEOUT}")
    private int redisSessionTimeout;

    @Value("${base_cookie}")
    private String baseCookie;

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisMaxTotal);
        config.setMaxIdle(redisMaxIdle);
        return config;
    }

    @Bean(name = "jedisCluster")
    public JedisCluster getJedisCluster() {
        Set<HostAndPort> hostAndPorts = Sets.newHashSet();
        HostAndPort node1 = new HostAndPort(node1host, node1port);
        HostAndPort node2 = new HostAndPort(node2host, node2port);
        HostAndPort node3 = new HostAndPort(node3host, node3port);
        HostAndPort node4 = new HostAndPort(node4host, node4port);
        HostAndPort node5 = new HostAndPort(node5host, node5port);
        HostAndPort node6 = new HostAndPort(node6host, node6port);
        hostAndPorts.add(node1);
        hostAndPorts.add(node2);
        hostAndPorts.add(node3);
        hostAndPorts.add(node4);
        hostAndPorts.add(node5);
        hostAndPorts.add(node6);
        JedisCluster cluster = new JedisCluster(hostAndPorts,
                30000,
                30000,
                15,
                password,
                getJedisPoolConfig());
//        log.info("RedisClusterConfig.init.jedisCluster："+ cluster.getClusterNodes().keySet());

        return cluster;
    }






    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory getJedisConnFactory(@Qualifier("jedisPoolConfig") JedisPoolConfig poolConfig) {
        RedisClusterConfiguration configuration = new RedisClusterConfiguration();
        configuration.setMaxRedirects(5);
        Set<RedisNode> redisNodes = Sets.newHashSet();
        redisNodes.add(new RedisNode(node1host, node1port));
        redisNodes.add(new RedisNode(node2host, node2port));
        redisNodes.add(new RedisNode(node3host, node3port));
        redisNodes.add(new RedisNode(node4host, node4port));
        redisNodes.add(new RedisNode(node5host, node5port));
        redisNodes.add(new RedisNode(node6host, node6port));
        configuration.setClusterNodes(redisNodes);
        JedisConnectionFactory factory = new JedisConnectionFactory(configuration, poolConfig);
        factory.setPassword(password);
        return factory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }
}

