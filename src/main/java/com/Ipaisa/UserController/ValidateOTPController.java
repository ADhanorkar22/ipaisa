package com.Ipaisa.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.MobileOtp;
import com.Ipaisa.Service.OTPMessageService;

@RestController
@RequestMapping("/auth")
public class ValidateOTPController {
	
    private OTPMessageService otpMessageService;

    @Autowired
    public ValidateOTPController(OTPMessageService otpMessageService) {
        this.otpMessageService = otpMessageService;
    }
	
	
	@PostMapping("/validateotp")
	public ResponseEntity<?> validateOtp(@RequestBody MobileOtp motp ) {
	     Boolean isValid= otpMessageService.validateOtp(motp);
	     if (isValid) {
	    	  return new ResponseEntity<>(new ApiResponse("success", "OTP is valid.", true), HttpStatus.OK);
         } else {
             return new ResponseEntity<>(new ApiResponse("fail", "OTP is invalid.", false), HttpStatus.NOT_ACCEPTABLE);
         }
	     
	     
	}

}
