package com.jo.demo.http.simple;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

//线程池还不会用，简略的使用了下，意思有点差距，后面在分析  
class HandlerTestB implements HttpHandler{  
  private static int requestNum = 0;   
  ThreadPoolExecutor threadPoolExecutor;  
  HandlerTestB(){  
      //两个常在线程，最大3个  
       threadPoolExecutor = new  ThreadPoolExecutor(2,3, 30,   
               TimeUnit.SECONDS,   
               new ArrayBlockingQueue<Runnable>(2),    
               new ThreadPoolExecutor.CallerRunsPolicy()  
               );  
  }  
  public void handle(HttpExchange he) throws IOException {  
      // TODO Auto-generated method stub  
      if((getQueueSize(threadPoolExecutor.getQueue()))<2){  
          RequestTasks rqt = new RequestTasks(he);   
          threadPoolExecutor.execute(rqt);  
      }  
      else System.out.println("Please Wait!");  
  }  
  private synchronized int getQueueSize(Queue queue)    
  {    
      return queue.size();    
  }   
    
}  