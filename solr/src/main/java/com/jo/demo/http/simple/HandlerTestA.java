package com.jo.demo.http.simple;

import java.io.IOException;
import java.io.OutputStream;

import com.jo.demo.po.RespObj;
import com.jo.demo.utils.JsonUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

//直接处理请求  
class HandlerTestA implements HttpHandler{  

  public void handle(HttpExchange httpExchange) throws IOException {  
      // TODO Auto-generated method stub  
      //针对请求的处理部分     
      //返回请求响应时，遵循HTTP协议  
//      String responseString = "<font color='#ff0000'>Hello! This a HttpServer!</font>";
  	
      String responseString = JsonUtils.toJson(RespObj.success());  

      //设置响应头  
      httpExchange.sendResponseHeaders(200, responseString.length());     
      OutputStream os = httpExchange.getResponseBody();     
      os.write(responseString.getBytes());     
      os.close();   
  }  
    
} 
