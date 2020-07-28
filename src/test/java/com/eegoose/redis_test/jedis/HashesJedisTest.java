package com.eegoose.redis_test.jedis;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eegoose.redis_test.common.Constant;

public class HashesJedisTest extends BaseJedisTest {
    // 日志
    private static final Logger LOGGGER = LoggerFactory.getLogger(HashesJedisTest.class);

    @Test
    public void hmset() {
        System.out.println(jedis.del("map"));
        Map<String, String> map = new HashMap<>(Constant.DEFAULT_INITIAL_CAPACITY);
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.put("k4", "v4");
        System.out.println(jedis.hmset("map", map));
        // 乱序
        System.out.println(jedis.hkeys("map"));
        // 乱序，且不跟上面一一对应
        System.out.println(jedis.hvals("map"));
        // 乱序
        System.out.println(jedis.hgetAll("map"));
        System.out.println(jedis.hlen("map"));

        System.out.println(jedis.hset("map", "k5", "v5"));
        System.out.println(jedis.hkeys("map"));
        System.out.println(jedis.hgetAll("map"));
        System.out.println(jedis.hlen("map"));

        System.out.println(jedis.hget("map", "k1"));
        // 返回: false
        System.out.println(jedis.hexists("map", "k6"));
        // 尝试取未有元素，返回: null
        System.out.println(jedis.hget("map", "k6"));

        // 返回: [v1, v2]
        System.out.println(jedis.hmget("map", "k1", "k2"));
        // 返回: [v1, null]
        System.out.println(jedis.hmget("map", "k1", "k6"));
    }

}
