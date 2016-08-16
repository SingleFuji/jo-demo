package com.jo.demo.quartz;

import org.junit.Test;

import com.jo.demo.TestBase;

public class SpringQuartzTest extends TestBase {

	@Test
	public void testHelloJob() {
		int i= 100;
		while (i>0) {
			try {
				Thread.currentThread().sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			i--;
		}
	}

}
