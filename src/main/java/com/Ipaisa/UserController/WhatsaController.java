package com.Ipaisa.UserController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.Ipaisa.Entitys.WhatsappDto;
import com.Ipaisa.Service.OTPMessageService;
import com.Ipaisa.Service.WhatsappService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth/whatsapp")
public class WhatsaController {

	   @Autowired
	  private  WhatsappService whatsappservice;

		    private OTPMessageService otpMessageService;

		    @Autowired
		    public WhatsaController(OTPMessageService otpMessageService) {
		        this.otpMessageService = otpMessageService;
		    }
	   
	 
	@PostMapping("/msg")
	public ResponseEntity<?> getjsondata(@RequestBody WhatsappDto req) throws JsonProcessingException {
				
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		 Object Response;
	
	        
		  Map<String, Object> data = otpMessageService.constructWhatsaAppMessage(req);

        try {
         
            System.out.println(data);         
            Response = whatsappservice.encryptAndSendData(data);
            return ResponseEntity.ok(Response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

}
