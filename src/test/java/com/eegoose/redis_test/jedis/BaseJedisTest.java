package com.eegoose.redis_test.jedis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eegoose.redis_test.RedisTestApplication;

import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisTestApplication.class)
public class BaseJedisTest {
    // 日志
    private static final Logger LOGGGER = LoggerFactory.getLogger(BaseJedisTest.class);

    private String host = "192.168.88.151";

    private int port = 6379;

    private String password = "myredis";

    protected Jedis jedis;

    @Before
    public void init(){
        LOGGGER.info("建立redis连接");
        jedis = new Jedis(host, port);
        jedis.auth(password);
        jedis.select(15);
    }

    @Test
    public void ping(){
        System.out.println(jedis.ping());
    }

    @After
    public void close(){
        jedis.close();
        LOGGGER.info("关闭redis连接");
    }
}