package com.Ipaisa.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.random.RandomGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.MobileOtp;
import com.Ipaisa.Entitys.OTPTime;
import com.amazonaws.services.cognitoidp.model.HttpHeader;

import io.jsonwebtoken.Header;
import jakarta.transaction.Transactional;

import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class OTPerviceImpl implements IOTPService {
	
	@Value("${hspsm.api.url}")
	private String url;
	
	@Value("${hspsm.api.username}")
	private String username;
	
	@Value("${hspsm.api.key}")
	private String key;
	
	@Value("${hspsm.api.sendername}")
	private String sendername;
	
	@Value("${hspsm.api.type}")
	private String type;
	
	
	  @Autowired
	    private RestTemplate restTemplate;
	  
	  Map<String ,OTPTime > mobilemap=new HashMap<String, OTPTime>();
	  public static final int VALIDITY_MINUTES=5;


	@Override
	public ResponseEntity<?> sendOtp(String mobilenumber,String otp) throws RestClientException {
		RestTemplate rt=new RestTemplate();
		
		
		
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	        
	       String message = "Your one-time password (OTP) to proceed on iPaisa is " + otp + ". This OTP is valid for 5 minutes. Remember, never share your OTP with anyone for security reasons. Thank you, Team iPaisa - Edsom Fintech.";
	       // String message = "otp is";
	        String u = url
	                + "?username=ramkumar.ramdhani9@gmail.com"
	                + "&message=" + message
	                + "&sendername=IPESSA"
	                + "&smstype=TRANS"
	                + "&numbers=" + mobilenumber
	                + "&apikey=503c34a4-c484-4d6e-8f55-bf7102c71242";
	       

	        HttpEntity<String> requestEntity = new HttpEntity<>(url, headers);

	        ResponseEntity<String> response = restTemplate.exchange(
	                u,
	                HttpMethod.POST,
	                requestEntity,
	                String.class);

	        if (response.getStatusCode() == HttpStatus.OK) 
	       //   return  ResponseEntity.ok("otp send sccessfully");
	        return new ResponseEntity<>(new ApiResponse("success", "otp send sccessfully"), HttpStatus.OK);
	        
	         //   return  ResponseEntity.ok("Failed to send SMS. Status code: " + response.getStatusCode());
	            return new ResponseEntity<>(new ApiResponse("Failed", "Failed to send SMS."), HttpStatus.NOT_ACCEPTABLE);
	           
	    }
	

	@Override
	public String genrateOtp(String mobileno) {
		Random random = new Random();
		String otp = String.valueOf(random.nextInt(9000) + 100000);
		mobilemap.put(mobileno, new OTPTime(otp,LocalDateTime.now()));
			return otp; 
		
		
	}


	@Override
	public Boolean validateOtp(MobileOtp motp) {
		System.out.println("validateOtp=====>"+mobilemap.size() );
		String mobileno=motp.getMobileno();
		String otp=motp.getOtp();
		  OTPTime otpData = mobilemap.get(mobileno);
		  System.out.println( mobilemap.get(mobileno));
		  System.out.println(otpData);
		
	        if (otpData != null && mobilemap.containsKey(mobileno)&&otpData.getOtp().equals(motp.getOtp())) {
	            LocalDateTime now = LocalDateTime.now();
	            LocalDateTime timestamp = otpData.getTime();
	            long minutesElapsed = timestamp.until(now, ChronoUnit.MINUTES);
	            mobilemap.remove(mobileno);
	            return minutesElapsed <= VALIDITY_MINUTES;
	        }
			mobilemap.remove(mobileno);
			return false;
	
		
	}
	
	

}
