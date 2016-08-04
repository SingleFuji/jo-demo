package com.jo.demo.solr;

public class TransactionThread implements Runnable{

    private String name;
    private Object prev;
    private Object self;
    
    public TransactionThread(String name, Object prev, Object self) {
        // TODO Auto-generated constructor stub
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int count = 10;
        while(count >0){
            synchronized(prev){
                synchronized (self) {
                    
                    System.out.println(name);
                    count--;
                    
                    self.notify();
                }
                try {
                    prev.wait();
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        TransactionThread tt1 = new TransactionThread("a", c, a);
        TransactionThread tt2 = new TransactionThread("b", a, b);
        TransactionThread tt3 = new TransactionThread("c", b, c);
        new Thread(tt1).start();
        Thread.sleep(1000);
        new Thread(tt2).start();
        Thread.sleep(1000);
        new Thread(tt3).start();
    }
}
