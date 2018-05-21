package com.mmall.common;


import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by yf
 */
public class RedisShardedPool {

    private static RedisShardedPool pool;//sharded jedis连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));//最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20"));//在jedispool中最大的idle状态(空闲的)的jedis实例个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","20"));//在jedispool中最小的idle状态(空闲的)的jedis实例个数

    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow一个jedis实例的时候,是否要进行验证操作,如果为true,则得到的jedis实例肯定是可以使用的
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));//在return一个jedis实例的时候,是否要进行验证操作,如果为true,则放回的jedispool实例肯定是可以使用的

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));

    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);//连接耗尽时,是否阻塞,false会抛出异常,true阻塞到超时.默认为true

//        pool = new JedisPool(config,redisIp,redisPort,1000*2);
    }

    static {
        initPool();
    }
//
//    public static Jedis getJedis(){
//        return pool.getResource();
//    }
//
//    public static void returnBrokenResource(Jedis jedis){
//        pool.returnBrokenResource(jedis);
//    }
//
//    public static void returnResource(Jedis jedis){
//        pool.returnResource(jedis);
//    }
//
//
//    public static void main(String[] args) {
//        Jedis jedis = pool.getResource();
//        jedis.set("yfkey","yfvalue");
//        returnResource(jedis);
//        pool.destroy();//临时调用,销毁池中所有链接
//
//        System.out.println("program is end");
//    }

}