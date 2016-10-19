package com.jo.demo.test;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;

import com.alibaba.fastjson.JSONObject;

/**
 * 支付连接类
 * @author zhaoyz
 *
 */
public class DuolabaoTest {

    public static void main(String[] args) throws Exception {
        connect();
//        System.out.println(getSignStr2("04d2389a9a3747819f7aa9b006360b1edfa8a015","1234"));
    }

    // http://openapi.duolabao.cn/v1/agent/passive/create
    private static void connect() throws Exception{
//        final String path="/v1/customer/order/payurl/create";
        final String path = "/v1/agent/passive/create";
        final String secretKey="acde13c98cb84e42b9bdbdca5bd0db1550eb6652";
        final String accessKey="8bfa02b1270042beb2972a3963e31d1c20e4d16e";
//        final String timestamp=String.valueOf(Instant.now().toEpochMilli());
//        final String timestamp = String.valueOf(System.currentTimeMillis());
//        System.out.println("timestamp:"+ timestamp);
        final String timestamp = "1474343703348";
        URL url = new URL("http://openapi.duolabao.cn"+path);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false); 
        JSONObject json=new JSONObject();
        json.put("agentNum", "10001074290163389363344");
        json.put("customerNum", "10001114725322683300235");
        json.put("amount", "1");
//        json.put("callbackUrl", "www.duolabao.cn");
        json.put("authCode", "130422615755325068");
        json.put("shopNum", "10001214725330802375960");
        json.put("machineNum", "10011014725546802404656");
//        json.put("requestNum", "1010201608245610324");
        json.put("source", "API");
        
        System.out.println(timestamp);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("accessKey", accessKey);
        urlConnection.setRequestProperty("timestamp", timestamp);
        urlConnection.setRequestProperty("token",getSignStr(secretKey, timestamp, path, json.toJSONString()));
        System.out.println(json.toJSONString());
//        urlConnection.setRequestProperty("token", getSHA1("secretKey="+secretKey+"&timestamp="+timestamp+"&path="+path+"&body="+json.toJSONString()));
        System.out.println(urlConnection.getRequestProperty("token"));
        urlConnection.connect();
        urlConnection.getOutputStream().write(json.toJSONString().getBytes());
        urlConnection.getOutputStream().flush();
        urlConnection.getOutputStream().close();
        try{
            InputStream in=urlConnection.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int count = -1;
            while((count = in.read(data,0,1024)) != -1)
                out.write(data, 0, count);
            data = null;
            String msg=new String(out.toByteArray());
            System.out.println(msg);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * {生成签名1}
     * 
     * @param msg
     * @return
     * @author: zhaoyz
     */
  public static String getSHA1(String msg){
      MessageDigest md5 = null;
      try {
          System.out.println(msg);
          md5 = MessageDigest.getInstance("SHA-1");
          md5.update(msg.getBytes());
          byte[] buffer=md5.digest();
          StringBuffer sb=new StringBuffer(buffer.length*2);
          for(int i=0;i<buffer.length;i++){
              sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
              sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
          }
          return sb.toString().toUpperCase();
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }
    
    
    /**
     * 生成签名2
     * @param secretKey 密钥
     * @param timestamp 时间戳
     * @param path 路径
     * @param body 消息体 {can null}
     * @return sha1签名
     */
    private static String getSignStr(String secretKey, String timestamp, String path, String body) {
        StringBuilder signStr = new StringBuilder();
        signStr.append("secretKey=").append(secretKey)
            .append("&timestamp=").append(timestamp)
            .append("&path=").append(path);
        if(body!=null && !body.isEmpty())
            signStr.append("&body=").append(body);
        System.err.println(signStr.toString());
        return sha1(signStr.toString()).toUpperCase();
    }
    
    
    /**
     * SHA-1加密
     * @param sourceStr
     * @return
     */
    public static final String sha1(String sourceStr) {
        String signature = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sourceStr.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return signature;
    }
    
    private static final String byteToHex(byte[] bytes){
        StringBuffer sb=new StringBuffer(bytes.length*2);
        for(int i=0;i<bytes.length;i++){
            sb.append(Character.forDigit((bytes[i] & 0xf0) >> 4, 16));
            sb.append(Character.forDigit(bytes[i] & 0x0f, 16));
        }
        return sb.toString();
    }
    
    //重复通知时的token    最前，回调地址
    //http://cgt.org/mall/?dis=pay-dlb-new/callback?requestNum=10000001608161000573213135&orderNum=10021014713542994943253&status=SUCCESS&completeTime=2016-08-16 21:31:59
    // token  时间戳 放在postman中
    private static String getSignStr2(String secretKey, String timestamp) {
        StringBuilder signStr = new StringBuilder();
        signStr.append("secretKey=").append(secretKey)
            .append("&timestamp=").append(timestamp);
        return sha1(signStr.toString()).toUpperCase();
    }
    //10001011892335640051771
    //http://ydd.easyorder.cn/Weixin/Order/paycallback
    /**
     ........................?requestNum=201609081147290021560006&orderNum=10021014733064524682055&status=SUCCESS
&completeTime=2016-09-08 12:00:33
     */
    

}
