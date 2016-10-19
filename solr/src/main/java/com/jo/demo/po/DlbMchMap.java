package com.jo.demo.po;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DlbMchMap {

	private String jlMchNo;

	private String jlTermNo;

	private String dlbMchNo;

	private String dlbTermNo;

	private String dlbShopNo;

	public String getJlMchNo() {
		return jlMchNo;
	}

	public void setJlMchNo(String jlMchNo) {
		this.jlMchNo = jlMchNo;
	}

	public String getJlTermNo() {
		return jlTermNo;
	}

	public void setJlTermNo(String jlTermNo) {
		this.jlTermNo = jlTermNo;
	}

	public String getDlbMchNo() {
		return dlbMchNo;
	}

	public void setDlbMchNo(String dlbMchNo) {
		this.dlbMchNo = dlbMchNo;
	}

	public String getDlbTermNo() {
		return dlbTermNo;
	}

	public void setDlbTermNo(String dlbTermNo) {
		this.dlbTermNo = dlbTermNo;
	}

	public String getDlbShopNo() {
		return dlbShopNo;
	}

	public void setDlbShopNo(String dlbShopNo) {
		this.dlbShopNo = dlbShopNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
