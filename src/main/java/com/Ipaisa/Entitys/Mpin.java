package com.Ipaisa.Entitys;

public class Mpin {
		private String mpin;
		private String mobileno;
		private String type;
	public Mpin(String mpin, String mobileno,String type) {
		super();
		this.mpin = mpin;
		this.mobileno = mobileno;
		this.type=type;
		
	}
	public Mpin(String mpin, String mobileno) {
		super();
		this.mpin = mpin;
		this.mobileno = mobileno;
		
	}
	public Mpin() {
		
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
