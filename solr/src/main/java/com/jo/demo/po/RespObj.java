package com.jo.demo.po;

public class RespObj {

	private String result;
	
	private Error error;
	
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}

	public Error getError() {
		return error;
	}


	public void setError(Error error) {
		this.error = error;
	}


	public static RespObj success(){
		RespObj resp = new RespObj();
		resp.setError(Error.errorInfo());
		resp.setResult("success");
		return resp;
	}
	
	public static class Error{
		private String errorCode;
		
		private String errorMsg;

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		
		public static Error errorInfo(){
			Error err = new Error();
//			err.setErrorCode("shopNotExist");
//			err.setErrorMsg("shop not exist");
			err.setErrorCode("ok");
			err.setErrorMsg("ok");
			return err;
		} 
		
	}
}
