package com.jo.demo.po;

public class RespObj {

	private String retCode;
	
	private String retDesc;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetDesc() {
		return retDesc;
	}

	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}
	
	public static RespObj success(){
		RespObj resp = new RespObj();
		resp.setRetCode("00");
		resp.setRetDesc("成功");
		return resp;
	}
}
