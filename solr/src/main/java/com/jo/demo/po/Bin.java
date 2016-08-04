package com.jo.demo.po;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Bin implements Serializable{

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 311770161221196245L;

	  // 银行卡卡BIN
	  private String cardBin;

	  // 银行卡号长度
	  private int cardLength;

	  // 银行卡类型代码
	  private String cardType;

	  // 银行卡品牌名
	  private String cardBrand;

	  // 发卡行机构代码
	  private String bankNo;
	  
	  // 银行卡类型描述
	  private String cardTypeDesc;

	  public String getCardTypeDesc() {
		return cardTypeDesc;
	}

	public void setCardTypeDesc(String cardTypeDesc) {
		this.cardTypeDesc = cardTypeDesc;
	}

	public String getCardBin() {
	    return cardBin;
	  }

	  public void setCardBin(String cardBin) {
	    this.cardBin = cardBin;
	  }

	  public int getCardLength() {
	    return cardLength;
	  }

	  public void setCardLength(int cardLength) {
	    this.cardLength = cardLength;
	  }

	  public String getCardType() {
	    return cardType;
	  }

	  public void setCardType(String cardType) {
	    this.cardType = cardType;
	  }

	  public String getCardBrand() {
	    return cardBrand;
	  }

	  public void setCardBrand(String cardBrand) {
	    this.cardBrand = cardBrand;
	  }

	  public String getBankNo() {
	    return bankNo;
	  }

	  public void setBankNo(String bankNo) {
	    this.bankNo = bankNo;
	  }

	  public int compareTo(Bin o) {

	    // if (o != null && !StringUtils.isBlank(cardBin) && !StringUtils.isBlank(o.getCardBin())) {
	    // return o.getCardBin().length() > cardBin.length() ? -1 : (o.getCardBin().length() == cardBin
	    // .length() ? 0 : 1);
	    // }
	    if (o != null) {
	      return o.getCardLength() > cardLength ? 1 : 0;
	    }
	    return -1;
	  }

	  public int compare(Bin o1, Bin o2) {
	    if (o1 != null && o2 != null) {
	      return o1.getCardLength() > o2.getCardLength() ? 1 : 0;
	    }
	    return 0;
	  }
	  
	  @Override
	  public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	  }
}
