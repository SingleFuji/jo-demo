package com.jo.demo.redis;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class TestSingleJedis {

	@Test
	public void test() {
		boolean flag = true;
		String msg = "good";
		Assert.assertTrue(flag);
		Assert.assertEquals(msg, "good");
	}

}
