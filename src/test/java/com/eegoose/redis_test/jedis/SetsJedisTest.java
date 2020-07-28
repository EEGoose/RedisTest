package com.eegoose.redis_test.jedis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetsJedisTest extends BaseJedisTest {

    // 日志
    private static final Logger LOGGGER = LoggerFactory.getLogger(SetsJedisTest.class);

    @Test
    public void sadd() {
        System.out.println(jedis.del("set"));
        System.out.println(jedis.sadd("set", "a", "b", "c", "d", "e", "f"));
        System.out.println(jedis.sadd("set", "b", "c", "d"));
        // 返回6
        System.out.println(jedis.scard("set"));
        // 返回[c, f, e, b, d]
        System.out.println(jedis.smembers("set"));

        // 尝试删除已有元素: 返回1
        System.out.println(jedis.srem("set", "a"));
        // 尝试删除未有元素: 返回0
        System.out.println(jedis.srem("set", "x"));
        // 返回5
        System.out.println(jedis.scard("set"));
        // 返回[c, f, e, b, d]
        System.out.println(jedis.smembers("set"));
        System.out.println(jedis.del("set"));
    }

    /*
     * 随机弹出元素
     */
    @Test
    public void spop() {
        System.out.println(jedis.del("set"));
        System.out.println(jedis.sadd("set", "a", "b", "c", "d", "e", "f"));
        // 返回[b, d, a, c, f, e]
        System.out.println(jedis.smembers("set"));
        Long card = jedis.scard("set");
        if (card > 0) {
            // 依次弹出d f c e a b(每次运行都不一样，不确定是哪种随机方式)
            for (int i = 0; i < card; i++) {
                System.out.println(jedis.spop("set"));
            }
        }
        // 返回false，最后一个弹出时键已经不存在了
        System.out.println(jedis.exists("set"));
        // System.out.println(jedis.del("set"));

        // 尝试继续弹出，未报错，返回: null
        System.out.println(jedis.spop("set"));
    }

    @Test
    public void smove() {
        System.out.println(jedis.del("set"));
        System.out.println(jedis.sadd("set", "a", "b", "c", "d", "e", "f"));
        System.out.println(jedis.smembers("set"));
        // another可以未初始化，不存在
        System.out.println(jedis.smove("set", "another", "b"));

        // 返回: false
        System.out.println(jedis.sismember("set", "b"));
        // 返回: [a, d, c, f, e]
        System.out.println(jedis.smembers("set"));
        // 返回: true
        System.out.println(jedis.sismember("another", "b"));
        // 返回: [b]
        System.out.println(jedis.smembers("another"));

        System.out.println(jedis.del("set"));
        System.out.println(jedis.del("another"));
    }
}
