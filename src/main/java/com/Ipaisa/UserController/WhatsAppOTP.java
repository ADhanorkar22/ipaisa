package com.Ipaisa.UserController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Service.IUserDao;
import com.Ipaisa.Service.OTPMessageService;
import com.Ipaisa.Service.WhatsappService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class WhatsAppOTP {
	
	  @Autowired
	  private  WhatsappService whatsappservice;
		
	  @Autowired
	  private IUserDao Userservice;

	    private OTPMessageService otpMessageService;

	    @Autowired
	    public WhatsAppOTP(OTPMessageService otpMessageService) {
	        this.otpMessageService = otpMessageService;
	    }
	
	
	   
	
	@PostMapping("/otpmsg")
	public ResponseEntity<?> SendOTP(@RequestBody com.Ipaisa.dto.WhatsAppOTP req) throws JsonProcessingException {
		 Object Response=null;
		
		  try {
			  User u=Userservice.getuser(req.getMobileNumber());
			  
			  if(u!=null) {
			  
			  Map<String, Object> data = otpMessageService.constructOTPMessage(req);
		  
	           
	            System.out.println(data);
	           
	           
	            Response = whatsappservice.encryptAndSendData(data);
	           

	            return ResponseEntity.ok(Response);
			  }else {
				  return new  ResponseEntity( new ApiResponse("Invalid User", false),HttpStatus.NOT_FOUND);
			  }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	        }
		  
		  
		  
		
	     
	
	

}
	
}
