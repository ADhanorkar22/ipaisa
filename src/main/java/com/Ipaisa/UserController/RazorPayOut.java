package com.Ipaisa.UserController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.RazorPay;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Service.RazorPayOutService;

@RestController
@RequestMapping("/auth")
public class RazorPayOut {
	
	@Autowired
	private RazorPayOutService razorpayservice;

	@PostMapping("/razorpayout")
	public ResponseEntity<?>razorPayOut(@RequestBody RazorPay razorpay){
		
		 Object Response=null;
			
		
			  
			try {
	           
	            Response = razorpayservice.encryptAndSendData(razorpay);
	           

	            return ResponseEntity.ok(Response);
			 
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	        }
		
	
	
}
}
