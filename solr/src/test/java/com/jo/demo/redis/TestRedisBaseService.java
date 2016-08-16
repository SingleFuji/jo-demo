package com.jo.demo.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestRedisBaseService extends TestBase {

	private static final String LRBIND_KEY = "CRM:LRMAP*";
	
	@Test
	public void testKeys() {
		long now = System.currentTimeMillis();
		List<byte[]> list = redisBaseService.keys(LRBIND_KEY);
		long afterRedis = System.currentTimeMillis();
		List<String> jsonList = new ArrayList<String>();
		System.out.println();
		for(byte[] val:list){
			String tmp = new String(val);
			System.out.println(tmp);
			jsonList.add(tmp);
		}
		long afterJsonStr = System.currentTimeMillis();
		long reidsTime = afterRedis - now;
		long transTime = afterJsonStr - afterRedis;
		System.out.println("Redis 耗时："+reidsTime);
		System.out.println("转换对象耗时："+transTime);
		System.out.println(list.size());
	}
	
}
