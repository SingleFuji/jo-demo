package com.jo.demo.redis;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jo.demo.TestBase;
import com.jo.demo.po.Bin;

public class TestRedisListService extends TestBase {

	@Test
	public void testListSize() {
		long size = redisListService.listSize(BIN_KEY);
		System.out.println(size);
	}
	
	@Test
	public void testListSizePure() {
		long size = redisListService.listSize(BIN_KEY);
		System.out.println(size);
	}
	
	@Test
	public void testlistRange(){
		List<byte[]> list = redisListService.listRange(BIN_KEY, 5, 6);
		Assert.assertNotNull(list);
		int size = list.size();
		System.out.println(size);
		List<Bin> binList = TransRedisObjUtils.trans(list, Bin.class);
		Assert.assertNotNull(binList);
		System.out.println(binList.toString());
		System.out.println(binList.toString());
	}
	
	@Test
	public void testlistAll(){
		List<byte[]> list = redisListService.listRangeAll(BIN_KEY);
		Assert.assertNotNull(list);
		int size = list.size();
		System.out.println(size);
		
		List<Bin> binList = TransRedisObjUtils.trans(list, Bin.class);
		Assert.assertNotNull(binList);
		System.out.println(binList.toString());
	}
	
}
