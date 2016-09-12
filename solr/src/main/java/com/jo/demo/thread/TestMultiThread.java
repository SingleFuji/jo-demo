package com.jo.demo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMultiThread {

	private static final int INDEX_THREAD_NUM = 200;
	
	private static final int POOL_SIZE = 20;
	
	public static void testThread(){
		CountThread2 t = new CountThread2();
		Thread t1 = new Thread(t, "No1");
		Thread t2 = new Thread(t, "No2");
		Thread t3 = new Thread(t, "No3");
		t1.start();
		t2.start();
		t3.start();
	}
	
	public static void testRunnable(){
		CountThread t = new CountThread();
		Thread t1 = new Thread(t, "R1");
		Thread t2 = new Thread(t, "R2");
		Thread t3 = new Thread(t, "R3");
		t1.start();
		t2.start();
		t3.start();
	}
	
	public static void main(String[] args) {
//		testThread();
//		testRunnable();
//		ExecutorService executorService = Executors.newCachedThreadPool();
		
//		int cpuNums = Runtime.getRuntime().availableProcessors();
//        //获取当前系统的CPU 数目
//		ExecutorService executorService =Executors.newFixedThreadPool(cpuNums * POOL_SIZE);
//        //ExecutorService通常根据系统资源情况灵活定义线程池大小
//		executorService.execute(new CountThread2());
		
		testJoin();
	}
	
	public static void testJoin(){
		CountThread he = new CountThread();
         Thread demo = new Thread(he,"线程");
         demo.start();
         for(int i=0;i<50;++i){
             if(i>10){
                 try{
//                     demo.join();  //强制执行demo
                 }catch (Exception e) {
                     e.printStackTrace();
                 }
             }
             System.out.println("main 线程执行-->"+i);
         }
         System.out.println(Thread.holdsLock(he));
	}
}
