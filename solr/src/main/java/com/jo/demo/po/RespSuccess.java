package com.jo.demo.po;

public class RespSuccess {

	private String result;
	
	private Data data;
	
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}

	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}


	public static RespSuccess success(){
		RespSuccess resp = new RespSuccess();
		resp.setData(Data.successInfo());
		resp.setResult("success");
		return resp;
	}
	
	public static class Data{
		private String message;
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}



		public static Data successInfo(){
			Data data = new Data();
			data.setMessage("pay proccessing,please check the order status");
			return data;
		} 
		
	}
}
