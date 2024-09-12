package com.Ipaisa.UserController;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.BulkPaymentRequest;
import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Service.InstantPayService;
import com.Ipaisa.Service.SetChargesInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
@RequestMapping("/auth")
public class InstantPayController {
    @Autowired
    private InstantPayService instantpayservice ;
    @Autowired
	private UserDetailsService udeatils;
    @Autowired
    private JwtUtils utils;
    
    @Autowired
    private SetChargesInterface setChargesInterface;
    
    @Autowired
    private UserRepositery uRepo;
   
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    @PostMapping("instantpay/payout")
    public ResponseEntity<?> performPayout(@RequestHeader("Authorization") String token,@RequestBody InstantPayBody payload) {
        Object Response;
        Double ourCharge=0.0;
        
        try {
        	String t=null;
      	
      	if (token.startsWith("Bearer ")) {
  		        t = token.substring(7);
  		        System.out.println(t);
  		   }          
  		   String username = utils.getUserNameFromJwtToken(t);
  	        UserDetails userDetails = udeatils.loadUserByUsername(username);
  	        String mobileno=userDetails.getUsername();
        
            System.out.println(payload);

			User user = this.uRepo.findByMobileNumber(mobileno);
			String amountString = payload.getTransferAmount(); 
			double amount = Double.parseDouble(amountString);
//			double percentAmount=amount* 2.66 /100;
			ourCharge=setChargesInterface.getFinalCharge(amount,payload.getTransferMode());
		
			double finalAmount=amount+ourCharge;
			
			if(finalAmount>user.getWalletBalance())
			{
				  return new ResponseEntity(new ApiResponse("Insufficient balance for Transfer ", false), HttpStatus.PAYMENT_REQUIRED);
			}
			else {
            Response = instantpayservice.encryptAndSendData(payload,mobileno,ourCharge);
            return ResponseEntity.ok(Response);
			}
           
           

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    
//    @PostMapping("/bulk-payout")
//    public ResponseEntity<?> performBulkPayout(@RequestHeader("Authorization") String token, @RequestBody BulkPaymentRequest bulkRequest) {
//        try {
//            String t = null;
//            if (token.startsWith("Bearer ")) {
//                t = token.substring(7);
//                System.out.println(t);
//            }          
//            String username = utils.getUserNameFromJwtToken(t);
//            UserDetails userDetails = udeatils.loadUserByUsername(username);
//            String userid = userDetails.getUsername();
//            
//            String response = instantpayservice.processBulkPayments(bulkRequest, userid);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
//        }
//    } 
   
    
    
    
    
}