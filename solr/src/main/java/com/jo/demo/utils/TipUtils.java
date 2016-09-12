package com.jo.demo.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.util.CharArrayBuffer;

public class TipUtils {

//	private static Map
	
	/**
	 * 生成制定位数的随机数
	 * 
	 * @param len
	 * @return
	 */
	public static String getRandomStr(int len){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			int num = ThreadLocalRandom.current().nextInt(10);
			sb.append(num);
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T StringToClass(String str, Class<T> clazz) {
		if(clazz.equals(Byte.class)) {
			return (T)Byte.valueOf(str.trim());
		} else if(clazz.equals(Short.class)) {
			return (T)Short.valueOf(str.trim());
		} else if(clazz.equals(Integer.class)) {
			return (T)Integer.valueOf(str.trim());
		} else if(clazz.equals(Long.class)) {
			return (T)Long.valueOf(str.trim());
		} else if(clazz.equals(Float.class)) {
			return (T)Float.valueOf(str.trim());
		} else if(clazz.equals(Double.class)) {
			return (T)Double.valueOf(str.trim());
		} else if(clazz.equals(Character.class)) {
			return (T)Character.valueOf(str.trim().toCharArray()[0]);
		} else if(clazz.equals(String.class)) {
			return (T)str.trim();
		}
		return null;
	}
	
	public static void testNumFormat(){
		System.out.println(NumberFormat.getInstance().format(321.24f));//321.24
		System.out.println(NumberFormat.getInstance(Locale.GERMANY).format(4032.3f));//4.032,3
		System.out.println(NumberFormat.getCurrencyInstance().format(40324.31f));
		System.out.println(NumberFormat.getCurrencyInstance(new Locale("nl")).format(40324.31f));

	}
	
	public static void testUTF8(){
		String str = "成";
		byte[] bArr = str.getBytes();
//		InputStream instream = new Inputst
//		final Reader reader = new InputStreamReader(instream, Charset.forName("UTF-8"));
//        final CharArrayBuffer buffer = new CharArrayBuffer(i);
//        final char[] tmp = new char[1024];
//        int l;
//        while((l = reader.read(tmp)) != -1) {
//            buffer.append(tmp, 0, l);
//        }
//        buffer.toString();
	}
	
	public static void main(String[] args) {
		testUTF8();
	}
}
