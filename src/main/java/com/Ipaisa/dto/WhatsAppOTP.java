package com.Ipaisa.dto;

public class WhatsAppOTP {
	
	private String mobileNumber;
	
	
	public WhatsAppOTP() {
		// TODO Auto-generated constructor stub
	}


	public WhatsAppOTP(String mobileNumber) {
		super();
		this.mobileNumber = mobileNumber;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	@Override
	public String toString() {
		return "WhatsAppOTP [mobileNumber=" + mobileNumber + "]";
	}
	
	

}
