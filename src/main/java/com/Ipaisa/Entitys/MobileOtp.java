package com.Ipaisa.Entitys;

public class MobileOtp {
	
	private String mobileno;
	private String otp;
	
	public MobileOtp() {
		
	}
	
	public MobileOtp(String mobileno, String otp) {
		super();
		this.mobileno = mobileno;
		this.otp = otp;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	
}
