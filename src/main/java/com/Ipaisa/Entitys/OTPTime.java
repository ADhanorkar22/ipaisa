package com.Ipaisa.Entitys;

import java.time.LocalDateTime;

public class OTPTime {
	private String otp;
	private LocalDateTime time;
	public OTPTime() {
		
	}
	public OTPTime(String otp, LocalDateTime time) {
		super();
		this.otp = otp;
		this.time = time;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "OTPTime [otp=" + otp + ", time=" + time + "]";
	}
	

}
