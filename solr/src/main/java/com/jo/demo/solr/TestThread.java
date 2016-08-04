package com.jo.demo.solr;

public class TestThread implements Runnable{

    String c;
    
    public TestThread(String c) {
        this.c = c;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        for(int i= 0 ;i<10;i++){
            System.out.println(c);
        }
    }
    
    public static void main(String[] args) {
        TestThread tt1 = new TestThread("a");
        TestThread tt2 = new TestThread("b");
        TestThread tt3 = new TestThread("c");
        Thread t1 = new Thread(tt1);
        Thread t2 = new Thread(tt2);
        Thread t3 = new Thread(tt3);
        t1.start();
        t2.start();
        t3.start();
    }

    
}
