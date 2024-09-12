package com.Ipaisa.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.Service.InstantAadhaarService;
import com.Ipaisa.Service.InstantBankVerifyService;
import com.Ipaisa.dto.AadhaarRequest;
import com.Ipaisa.dto.BankVerifyRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth/instantpay")
public class VerifyBankAccount {
	
	 @Autowired
	    private InstantBankVerifyService instantBankVerifyService ;
	    private final ObjectMapper objectMapper = new ObjectMapper();
	    
	    @PostMapping("/bankverify")
	    public ResponseEntity<?> performPayout(@RequestBody BankVerifyRequest payload) {
	        Object Response;
	       
	        try {
	          
	            System.out.println(payload);
	          
	            Response = instantBankVerifyService.encryptAndSendData(payload);
	           

	            return ResponseEntity.ok(Response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	        }
	    }

}
