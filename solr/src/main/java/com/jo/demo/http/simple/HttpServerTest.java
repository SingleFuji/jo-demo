package com.jo.demo.http.simple;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jo.demo.po.RespObj;
import com.jo.demo.utils.JsonUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;   
public class HttpServerTest {   
    public static void main(String[] args) {  
        try {  
            //允许最大连接数  
            int backLog = 10;  
            InetSocketAddress inetSock = new InetSocketAddress(8086);  
            HttpServer httpServer = HttpServer.create(inetSock, backLog);  
            //直接返回Hello.....  
            httpServer.createContext("/", new HandlerTestA());  
            //显示已经处理的请求数，采用线程池  
            httpServer.createContext("/test",new HandlerTestB());  
            httpServer.setExecutor(null);  
            httpServer.start();  
            System.out.println("HttpServer Test Start!");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  
  
 
