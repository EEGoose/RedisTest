package com.eegoose.redis_test.jedis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringJedisTest extends BaseJedisTest{
    // 日志
    private static final Logger LOGGGER = LoggerFactory.getLogger(StringJedisTest.class);

    @Test
    public void ping() {
        System.out.println(jedis.ping());
    }

    @Test
    public void set() {
        System.out.println(jedis.set("k1","v1"));
        System.out.println(jedis.set("k2","v2"));
        System.out.println(jedis.set("k3","v3"));
    }

    @Test
    public void get() {
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.get("k2"));
        System.out.println(jedis.get("k3"));
    }

    @Test
    public void incr() {
        System.out.println(jedis.set("count","1"));
        System.out.println(jedis.incr("count"));
        System.out.println(jedis.get("count"));
    }

    @Test
    public void decr() {
        System.out.println(jedis.decr("count"));
        System.out.println(jedis.get("count"));
    }

    @Test
    public void mset_mget() {
        System.out.println(jedis.mset("kk1","vv1","kk2","vv2","kk3","vv1"));
        System.out.println(jedis.mget("kk1","kk2","kk3"));
    }

    @Test
    public void del() {
        System.out.println(jedis.mset("dd1","vv1","dd2","vv2"));
        System.out.println(jedis.del("dd1","dd2"));
        System.out.println(jedis.mget("dd1","dd2"));
    }

    /*
     * set if not exist
     */
    @Test
    public void setnx() {
        System.out.println(jedis.set("kkk1","v1"));
        System.out.println(jedis.setnx("kkk1","v2"));
        System.out.println(jedis.get("kkk1"));
        System.out.println(jedis.setnx("kkk2","v2"));
        System.out.println(jedis.get("kkk2"));
    }

    /*
     * set expire time after now
     */
    @Test
    public void setex() {
        System.out.println(jedis.setex("clock", 5, "awake"));
        System.out.println(jedis.get("clock"));
        System.out.println(jedis.exists("clock"));
        try{
            Thread.sleep(5000L);
        }catch (Exception e){
            LOGGGER.error("线程中断", e);
        }
        System.out.println(jedis.get("clock"));
        System.out.println(jedis.exists("clock"));
    }

    /*
     * 截取字符串
     */
    @Test
    public void getrange() {
        System.out.println(jedis.set("range", "0123456789"));
        System.out.println(jedis.get("range"));
        System.out.println(jedis.getrange("range", 1, 5));
    }
}
