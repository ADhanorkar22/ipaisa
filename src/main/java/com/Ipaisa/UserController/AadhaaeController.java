package com.Ipaisa.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import com.Ipaisa.Entitys.AadhaarRequest;
import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Service.InstantAadhaarService;
import com.Ipaisa.Service.InstantPayService;
import com.Ipaisa.dto.AadhaarRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/auth/instantpay")

public class AadhaaeController {
	  @Autowired
	    private InstantAadhaarService instantadharservice ;
	    private final ObjectMapper objectMapper = new ObjectMapper();
	    
	    @PostMapping("/aadhaar")
	    public ResponseEntity<?> performPayout(@RequestBody AadhaarRequest payload) {
	        Object Response;
	       
	        try {
	           
	            System.out.println(payload);
	           
	            Response = instantadharservice.encryptAndSendData(payload);
	           
	            return ResponseEntity.ok(Response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	        }
	    }


}
