package com.bjs.hedge.config.redis;


import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCluster;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class RedissonConfig {
    @Autowired
    private JedisCluster jedisCluster;
    @Value("${redis_cluster_password}")
    private String password;

    public static final  String REDIS_ADDRESS_PREFIX = "redis://";

    @Bean
    public Redisson redisson() {
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        List<String> redisAddresses = jedisCluster.getClusterNodes().keySet().stream().map(key -> REDIS_ADDRESS_PREFIX.concat(key)).collect(Collectors.toList());
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.setCodec(new StringCodec()).useClusterServers().setCheckSlotsCoverage(false)
                .addNodeAddress(redisAddresses.toArray(new String[redisAddresses.size()]));
        clusterServersConfig.setPassword(password);//设置密码
        return (Redisson) Redisson.create(config);
    }
}