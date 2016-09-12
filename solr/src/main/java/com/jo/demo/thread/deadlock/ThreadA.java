package com.jo.demo.thread.deadlock;

public class ThreadA extends Thread {

private DeadLock dl;
    
    public ThreadA(DeadLock dl)
    {
        this.dl = dl;
    }
    
    public void run()
    {
        try
        {
            dl.leftRight();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
