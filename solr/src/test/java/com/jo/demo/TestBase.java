package com.jo.demo;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jo.demo.redis.IRedisListService;
import com.jo.demo.redis.IRedisStringService;
import com.jo.demo.redis.RedisBaseService;
import com.jo.demo.utils.ConfigUtils;
import com.jo.demo.zookeeper.TaskLockUtils;

public class TestBase {

	public IRedisStringService redisStringService;
	
	public IRedisListService redisListService;
	
	public RedisBaseService redisBaseService;
	
	protected final String BIN_KEY = "MPAY:CARD:BIN";

	@SuppressWarnings({ "resource" })
	@Before
	public void init() {
//		TaskLockUtils.init();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		redisStringService = (IRedisStringService) context.getBean("redisStringService");
		redisListService = (IRedisListService) context.getBean("redisListService");
		redisBaseService =  (RedisBaseService) context.getBean("redisBaseService");
	}
}
