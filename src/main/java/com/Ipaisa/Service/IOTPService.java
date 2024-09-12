package com.Ipaisa.Service;

import org.springframework.http.ResponseEntity;

import com.Ipaisa.Entitys.MobileOtp;

public interface IOTPService {
	ResponseEntity<?> sendOtp(String mobilenumber,String otp);
	String genrateOtp(String mobileno);
	Boolean validateOtp(MobileOtp motp);
	

}
