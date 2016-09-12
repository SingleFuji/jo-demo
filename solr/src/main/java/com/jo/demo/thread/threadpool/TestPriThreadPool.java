package com.jo.demo.thread.threadpool;

import com.jo.demo.thread.CountThread;
import com.jo.demo.thread.threadpool.factory.ThreadPoolFactory;

public class TestPriThreadPool {

	
	  private static final int INDEX_THREAD_NUM = 10;

	  protected static ThreadPool indexThreadPool =
	      ThreadPoolFactory.getFixedThreadPool("tradeIndex", INDEX_THREAD_NUM, ThreadPool.KEEP_ALIVE_TIME);
	 
	  
	public static void main(String[] args) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i= 0; i<50;i++){
			indexThreadPool.submit(new CountThread());
		}
		
	}
}
