package com.jo.demo.http.simple;

import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

//处理请求的任务  
class RequestTasks implements Runnable{  

  static int processedNum = 0;  
  HttpExchange httpExchange;  
  RequestTasks(HttpExchange he){  
      httpExchange = he;  
      processedNum++;  
  }  
  public void run() {  
      // TODO Auto-generated method stub  
      System.out.println("ProcessedNum:" +processedNum);  
      String responseString = "ProcessedNum:" + processedNum + "\n";  
      try{  
      httpExchange.sendResponseHeaders(200, responseString.length());     
      OutputStream os = httpExchange.getResponseBody();     
      os.write(responseString.getBytes());     
      os.close();  
      //去掉注释，看看只能响应两个，有些问题  
      //while(true);  
      }catch (Exception e){  
          e.printStackTrace();  
      }  
  }  
    
}  
