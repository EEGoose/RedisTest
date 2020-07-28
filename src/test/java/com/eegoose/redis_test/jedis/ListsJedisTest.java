package com.eegoose.redis_test.jedis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.SortingParams;

public class ListsJedisTest extends BaseJedisTest {

    // 日志
    private static final Logger LOGGGER = LoggerFactory.getLogger(ListsJedisTest.class);

    /*
     * redis中list的实质为linkedList,即双头可操作性列表
     */
    @Test
    public void lpush() {
        System.out.println(jedis.del("list"));
        System.out.println(jedis.lpush("list", "a", "b", "c", "d", "e", "f"));
        System.out.println(jedis.llen("list"));
        System.out.println(jedis.lrange("list", 0, -1));
    }

    @Test
    public void rpop() {
        // jedis list没有的值长度返回“0”
        System.out.println(jedis.llen("list2"));
        Long length = jedis.llen("list");
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                System.out.println(jedis.rpop("list"));
            }
        }
        System.out.println(jedis.llen("list"));
        // jedis list为空返回“[]”
        System.out.println(jedis.lrange("list", 0, -1));
    }

    @Test
    public void trim() {
        System.out.println(jedis.del("trim"));
        System.out.println(jedis.rpush("trim", "0", "1", "2", "3", "4", "5"));
        // list仅支持ltrim,   xtrim给stream提供的
        System.out.println(jedis.ltrim("trim", 1, 3));
        System.out.println(jedis.llen("trim"));
        System.out.println(jedis.lrange("trim", 0, -1));
    }

    @Test
    public void lindex() {
        System.out.println(jedis.del("index"));
        System.out.println(jedis.rpush("index", "0", "1", "2", "3", "4", "5"));
        System.out.println(jedis.lindex("index", 2));
        System.out.println(jedis.lrange("index", 0, -1));
    }

    /*
    * 关于sort初步测试，sort存在大量后置参数可供调整
    */
    @Test
    public void sort() {
        System.out.println(jedis.del("sort"));
        System.out.println(jedis.rpush("sort", "789", "321", "4892", "23", "4", "35"));
        // 默认升序
        System.out.println(jedis.sort("sort"));

        SortingParams sortingParams = new SortingParams();
        // 降序
        System.out.println(jedis.sort("sort", sortingParams.desc()));
        // 字典排序
        System.out.println(jedis.sort("sort", sortingParams.alpha()));

        //尝试加入非数字字符串
        System.out.println(jedis.rpush("sort", "awsl"));
        // 升序: 报错
        // System.out.println(jedis.sort("sort"));
        // 字典排序: 正常处理
        System.out.println(jedis.sort("sort", sortingParams.alpha()));

        // 排序并不影响原有集合数据存储状态，输出与为排序时无异
        System.out.println(jedis.lrange("sort", 0, -1));
    }
}
