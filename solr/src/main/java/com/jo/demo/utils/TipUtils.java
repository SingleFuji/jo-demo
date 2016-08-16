package com.jo.demo.utils;

import java.util.concurrent.ThreadLocalRandom;

public class TipUtils {

//	private static Map
	
	public static String getRandomStr(int len){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			int num = ThreadLocalRandom.current().nextInt(10);
			sb.append(num);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(getRandomStr(6));
		}
	}
}
