package com.Ipaisa.UserController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Service.AxisService;
import com.fasterxml.jackson.core.JsonProcessingException;


@RestController
@RequestMapping("/auth")
public class AxisController {
	
	@Autowired
	private AxisService axisService;

	
	
	@GetMapping("/axisobj")
	public ResponseEntity<?> SendOTP() throws JsonProcessingException {
		 Object Response=null;
		
		  try {
	           
	           
	       //     Response = axisService.getConstructedBody();
	           

			  Response= ResponseEntity.ok(axisService.encryptAndSendData());
			  return (ResponseEntity<?>) Response;
			 
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	        }
	}
	
	
	@GetMapping("/axixpay/{reffid}")
	public ResponseEntity<?> aixpay(@PathVariable String reffid) throws JsonProcessingException {
		 Object Response=null;
		
		  try {
			  System.out.println("here");
	           
	           
	       //     Response = axisService.getConstructedBody();
	           

			  return ResponseEntity.ok(axisService.encryptAndSendData2(reffid));
			  //return (ResponseEntity<?>) Response;
			 
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	        }
	}
	
	
	@GetMapping("/getallbillers")
	public ResponseEntity<?> allBillers(){
		
		
		
		try {
			return axisService.getAllBillers();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
		
	}
	
	
}
