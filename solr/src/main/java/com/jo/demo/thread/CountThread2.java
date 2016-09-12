package com.jo.demo.thread;

public class CountThread2 extends Thread{

	int ticket = 5;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++){
			if(ticket > 0){  
                System.out.println(CountThread2.currentThread().getName()+"ticket = " + ticket--);  
            }  
		}
	}

}
