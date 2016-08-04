package com.jo.demo.redis;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class TestBase {

	public IRedisStringService redisStringService;
	
	public IRedisListService redisListService;

	private RedisTemplate<String, String> redisTemplate;
	
	protected final String BIN_KEY = "MPAY:CARD:BIN";

	@SuppressWarnings({ "unchecked", "resource" })
	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-cluster.xml");
		redisStringService = (IRedisStringService) context.getBean("redisStringService");
		redisListService = (IRedisListService) context.getBean("redisListService");
		redisTemplate = (RedisTemplate<String, String>) context.getBean("redisTemplate");
	}
}
