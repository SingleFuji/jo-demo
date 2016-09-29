package com.jo.demo.jetty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.jo.demo.po.RespObj;
import com.jo.demo.po.RespSuccess;
import com.jo.demo.utils.JsonUtils;

public class JettyHelloWorld extends AbstractHandler
{
	private static final String errorMsg = "ERROR";
	
    @Override
    public void handle( String target,
                        Request baseRequest,
                        HttpServletRequest request,
                        HttpServletResponse response ) throws IOException,
                                                      ServletException
    {
    	String rcvMsg = "";
    	int len = request.getContentLength();
    	String uri = request.getRequestURI();
    	request.getRequestURL();
    	System.err.println(uri);
//        if (len <= 0) {
//            writeResponse(response, errorMsg);
//        } else {
        	rcvMsg = readMsg(request);
//        }
    	System.err.println(rcvMsg);
        // Declare response encoding and types
        response.setContentType("text/html; charset=utf-8");
        
        // Declare response status code
        response.setStatus(HttpServletResponse.SC_OK);
        
        request.getHeader("token");
        Enumeration<String> headNames = request.getHeaderNames();
        
        while(headNames.hasMoreElements()){
        	System.out.println(headNames.nextElement());
        }
        request.getParameterMap();
        
        // Write back response
//        String responseString = JsonUtils.toJson(RespObj.success());
        String responseString = JsonUtils.toJson(RespSuccess.success());
        String now = System.currentTimeMillis()+"";
        String path = "/v1/agent/passive/create";
        String toSHA1 = formatSha1String(responseString, path, now);
        System.out.println(toSHA1);
        String token = DigestUtils.sha1Hex(toSHA1).toUpperCase();
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("token", token);
        headers.put("timestamp", now);
        addHeaders(headers, response);
        System.err.println(responseString);
        response.getWriter().println(responseString);

        // Inform jetty that this request has now been handled
        baseRequest.setHandled(true);
    }
    
    private String formatSha1String(String body, String path, String now){
		LinkedHashMap<String, String> toSHA1Map = new LinkedHashMap<String, String>();
		toSHA1Map.put("secretKey", "acde13c98cb84e42b9bdbdca5bd0db1550eb6652");
		toSHA1Map.put("timestamp", now);
		toSHA1Map.put("path", path);
		toSHA1Map.put("body", body);
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:toSHA1Map.entrySet()){
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return sb.substring(0, sb.length()-1).toString();
	}
    
    private String readMsg(HttpServletRequest request) throws IOException {
        String encocding = request.getCharacterEncoding();
        if (StringUtils.isEmpty(encocding)) {
            encocding = "UTF-8";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), encocding));
        StringBuilder msgBuilder = new StringBuilder();
        try {
            String readLine = null;
            while ((readLine = reader.readLine()) != null) {
                msgBuilder.append(readLine);
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                }
            }
        }
        return msgBuilder.toString();
    }
    
    private void writeResponse(HttpServletResponse resp, String respMsg) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println(respMsg);
        resp.getWriter().close();
    }
    
    private void addHeaders(Map<String, Object> headers, HttpServletResponse response) {
		for (Iterator<Entry<String, Object>> it = headers.entrySet().iterator(); it.hasNext(); ) {
			Entry<String, Object> entry = it.next();
			response.setHeader(entry.getKey(), entry.getValue().toString());
		}
	}

    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(8086);
        server.setHandler(new JettyHelloWorld());

        server.start();
        server.join();
    }
}
