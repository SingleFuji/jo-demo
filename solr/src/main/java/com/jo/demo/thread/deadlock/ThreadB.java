package com.jo.demo.thread.deadlock;

public class ThreadB extends Thread{

private DeadLock dl;
    
    public ThreadB(DeadLock dl)
    {
        this.dl = dl;
    }
    
    public void run()
    {
        try
        {
            dl.rightLeft();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
