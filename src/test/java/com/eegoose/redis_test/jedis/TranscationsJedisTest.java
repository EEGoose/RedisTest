package com.eegoose.redis_test.jedis;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TranscationsJedisTest extends BaseJedisTest {

    // 日志
    private static final Logger LOGGGER = LoggerFactory.getLogger(HashesJedisTest.class);

    @Ignore
    @Test
    public void discard() {
        Transaction transaction = jedis.multi();
        transaction.set("k1", "v1");
        transaction.set("k2", "v2");
        transaction.set("k3", "v3");
        try {
            int i = 1 / 0;
            transaction.exec();
        } catch (Exception e) {
            transaction.discard();
            LOGGGER.error("事务执行失败", e);
        }
    }

    @Ignore
    @Test
    public void exec() {
        Transaction transaction = jedis.multi();
        transaction.set("k1", "v1");
        transaction.incrBy("k1", 1);
        transaction.set("k1", "v2");
        transaction.set("k3", "v3");
        try {
            transaction.exec();
        } catch (Exception e) {
            transaction.discard();
            LOGGGER.error("事务执行失败", e);
        }
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.get("k3"));

        System.out.println(jedis.del("k1"));
        System.out.println(jedis.del("k1"));
    }

    /*
     * 乐观锁：watch需要搭配事务使用，否者不生效
     */
    @Test
    public void watch() {
        System.out.println(Thread.currentThread());

        System.out.println(jedis.set("k1", "v1"));
        System.out.println(jedis.watch("k1"));
        Transaction transaction = jedis.multi();
        transaction.set("k1", "v3");

        new Thread(new Runnable() {
            @Override
            public void run() {
                set();
            }
        }).start();
        try {
            Thread.sleep(100L);
        } catch (Exception e) {
            LOGGGER.error("线程休眠报错", e);
        }

        try{
            System.out.println("事务执行返回结果:"+transaction.exec());
        }catch(Exception e){
            LOGGGER.error("线程休眠报错", e);
        }
        System.out.println(jedis.unwatch());
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.del("k1"));
    }

    @Test
    public void set() {
        System.out.println(Thread.currentThread());

        Jedis newJedis = new Jedis("192.168.88.151", 6379);
        newJedis.auth("myredis");
        newJedis.select(15);
        System.out.println("修改成v2:" + newJedis.set("k1", "v2"));
        System.out.println("修改成v2结果为:" + newJedis.get("k1"));
        newJedis.close();
    }

}
