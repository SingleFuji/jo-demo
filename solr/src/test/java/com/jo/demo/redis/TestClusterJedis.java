package com.jo.demo.redis;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestClusterJedis {

	public JedisCluster jedisCluster;
	
	public IRedisStringService redisStringService;
	
	private RedisTemplate<String, String> redisTemplate;
	
	@SuppressWarnings({ "unchecked", "resource" })
	@Before
	public void init(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-cluster.xml");
		redisStringService = (IRedisStringService) context.getBean("simpleStringService");
		redisTemplate = (RedisTemplate<String, String>) context.getBean("redisTemplate");
	}
	
	@Test
	public void testJedisCluster() throws IOException{
        HashSet<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("172.20.20.106", 7000));
        nodes.add(new HostAndPort("172.20.20.106", 7001));
        nodes.add(new HostAndPort("172.20.20.103", 7000));
        nodes.add(new HostAndPort("172.20.20.103", 7001));
        nodes.add(new HostAndPort("172.20.20.105", 7000));
        nodes.add(new HostAndPort("172.20.20.105", 7001));
        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("jedisClusterKey", "hello_world");
        String str = cluster.get("jedisClusterKey");
        System.out.println("---:"+str);
        cluster.close();
    }
	
	 /**
     * 结合spring的jedis集群版
	 * @throws IOException 
     */
    @Test
    public void testSpringJedisCluster() throws IOException{
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/applicationContext-*.xml");
//        JedisCluster jedisCluster = (JedisCluster)context.getBean(JedisCluster.class);
    	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-cluster.xml");
    	jedisCluster = (JedisCluster)context.getBean(JedisCluster.class);
        jedisCluster.set("key2", "3333");
        String str = jedisCluster.get("key2");
        System.out.println("--:"+str);
        jedisCluster.close();
    }
    
    @Test
    public void testGetString(){
    	String key = "jo_cluster_key";
    	String value = "jo_cluster_value";
    	String result = redisStringService.getString(key);
    	Assert.assertNotNull(result);
    	Assert.assertEquals(value, result);
    	System.out.println(result);
    }
    
    @Test
    public void testPutString(){
    	String key = "jo_cluster_key";
    	String value = "jo_cluster_value";
    	redisStringService.putString(key, value);
    }
    
    @Test
    public void testOpVal(){
    	String key = "jo_cluster_key";
    	String value = "jo_cluster_value";
//    	System.out.println(redisConnectionFactory.get(key));
    	redisTemplate.opsForValue().set(key, value);
    	String result = (String) redisTemplate.opsForValue().get(key);
    	Assert.assertNotNull(result);
    	Assert.assertEquals(value, result);
    }
}
