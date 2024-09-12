package com.Ipaisa.dto;

//import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
	
	
	private String mobileNumber;
	
	
	private String mpin;

	public AuthRequest() {
		
	}
	
	
public AuthRequest(String mobileNumber, String mpin) {
		super();
		this.mobileNumber = mobileNumber;
		this.mpin = mpin;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}


	
	
	

	
	
}
