package com.jo.demo.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class ScanTest {

    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        List<String> retList = new ArrayList<String>();
        Jedis jedis = new Jedis("172.20.21.125", 7000, 400000);
        String scanRet = "0";
        do {
        	ScanParams params = new ScanParams();
        	params = params.match("CRM:R:MCH*");
            ScanResult ret = jedis.scan(scanRet, params);
            scanRet = ret.getStringCursor();
            retList.addAll(ret.getResult());
        } while (!scanRet.equals("0"));
        System.out.println(retList.size());
        for(String tmp : retList){
        	System.out.println("="+tmp+"=");
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("using time is:"+(endTime - startTime));
        jedis.close();
    }
    
    private static void testScan(){
    }
}
