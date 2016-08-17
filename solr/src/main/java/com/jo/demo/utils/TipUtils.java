package com.jo.demo.utils;

import java.util.concurrent.ThreadLocalRandom;

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
	
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(getRandomStr(6));
		}
	}
}
