package com.jo.demo.thread.deadlock;

public class Main {

	public static void main(String[] args)
	{
	    DeadLock dl = new DeadLock();
	    ThreadA t0 = new ThreadA(dl);
	    ThreadB t1 = new ThreadB(dl);
	    t0.start();
	    t1.start();
	}
}
