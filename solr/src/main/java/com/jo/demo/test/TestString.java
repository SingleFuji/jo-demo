package com.jo.demo.test;

import java.math.BigDecimal;

public class TestString {

	public String appendStr(String src){
		src += "ooo";
		return src;
	}
	
	public static void main(String[] args) {
		String val = "0.83";
		double tmp = 0.0;
		tmp = Double.valueOf(val.trim())*10;
		System.out.println(tmp);
		BigDecimal bd = new BigDecimal(val);
		BigDecimal ten = new BigDecimal(10);
		tmp = bd.multiply(ten).doubleValue();
		System.out.println(tmp);
	}
}
