package com.jo.demo.redis;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestBase {

	public IRedisStringService redisStringService;
	
	public IRedisListService redisListService;
	
	public RedisBaseService redisBaseService;
	
	protected final String BIN_KEY = "MPAY:CARD:BIN";

	@SuppressWarnings({ "resource" })
	@Before
	public void init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		redisStringService = (IRedisStringService) context.getBean("redisStringService");
		redisListService = (IRedisListService) context.getBean("redisListService");
		redisBaseService =  (RedisBaseService) context.getBean("redisBaseService");
	}
}
