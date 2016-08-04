package com.jo.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;

/**
 * 集群不用jedisCluster.close();  否则下次不能连接
 * @author Administrator
 *
 */
public class JedisClientCluster implements JedisClient{

    @Autowired
    private JedisCluster jedisCluster;
    @Override
    public String get(String key) {
        String str = jedisCluster.get(key);
        return str;
    }

    @Override
    public String set(String key, String value) {
        String str = jedisCluster.set(key, value);
        return str;
    }

    @Override
    public String hget(String hkey, String key) {
        return jedisCluster.hget(hkey, key);
    }

    @Override
    public Long hset(String hkey, String key, String value) {
        return jedisCluster.hset(hkey, key, value);
    }

    @Override
    public long incr(String key) {
        return jedisCluster.incr(key);
    }

    @Override
    public long expire(String key, int seconds) {
        return jedisCluster.expire(key, seconds);
    }

    @Override
    public long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public long del(String key) {
        return jedisCluster.del(key);
    }

    @Override
    public long hdel(String hkey, String key) {
        return  jedisCluster.hdel(hkey, key);
    }
}