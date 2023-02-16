package test;


import com.bjs.hedge.HedgeRobotApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;


/**
 * {@link SpringBootTest}：配置文件属性的读取。读取classes标志的启动类的配置文件和运行环境，并加载。
 *
 */
@SpringBootTest(classes = HedgeRobotApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisClusterTest {

    @Resource
    private JedisCluster jedisCluster;

    @Test
    public void set() {
        String test = jedisCluster.set("test", "123");
        System.err.println(test);
    }

    @Test
    public void get() {
        String test = jedisCluster.get("test");
        System.err.println(test);
    }
}

