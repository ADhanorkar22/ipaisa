//package com.Ipaisa.UserController;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.Ipaisa.Entitys.ApiResponse;
//import com.Ipaisa.Entitys.MobileNumber;
//import com.Ipaisa.Entitys.MobileOtp;
//import com.Ipaisa.Entitys.Mpin;
//import com.Ipaisa.Entitys.User;
//import com.Ipaisa.Jwt_Utils.JwtUtils;
//import com.Ipaisa.Repository.MobileRepositery;
//import com.Ipaisa.Service.AuthService;
//import com.Ipaisa.Service.IOTPService;
//import com.Ipaisa.Service.UserPrincipal;
//import com.Ipaisa.dto.AuthRequest;
//import com.Ipaisa.dto.AuthResp;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/auth/mobile")
//public class SMSController {
//	
//	@Autowired
//	private IOTPService smsservice; 
//	
//	
//    @Autowired
//    private JwtUtils utils;
//
//    @Autowired
//    private AuthenticationManager manager;
//    
//    @Autowired
//    private AuthService authservice;
//  
//
//	
//	
//    @PostMapping("/signin")
//    public ResponseEntity<?> validateUserCreateToken(@RequestBody @Valid AuthRequest request) {
//    	
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                request.getMobileNumber(), request.getMpin());
//        try {
//            Authentication authenticatedDetails = manager.authenticate(authToken);
//            String jwtToken = utils.generateJwtToken(authenticatedDetails); // Assuming jwtUtils is your JwtUtils instance
//
//       	 UserPrincipal userPrincipal = (UserPrincipal) authenticatedDetails.getPrincipal();
//   	        User user = userPrincipal.getUser();
//   	
//   		String j = utils.generateJwtToken(authenticatedDetails);
//   		System.out.println(utils.validateJwtToken(j));
//   		 AuthResp response = new AuthResp(
//   		            "Auth Successful",
//   		            utils.generateJwtToken(authenticatedDetails),
//   		            user.getUserid(),
//   		            user.getMobileNumber(),
//   		            user.getFirstName(),
//   		            user.getLastName(),
//				 user.getUtype(),
//				 user.getBulkPayout()
//   		        );
//   		  return ResponseEntity.ok(response);
//          //  return ResponseEntity.ok(new AuthResp("Auth Successful", jwtToken));
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }
//	
//	
//	
//	
//	@PostMapping("/sendotp")
//
//	public ResponseEntity<?> sendsms(@RequestBody  MobileNumber mobileno){
//		String otp= smsservice.genrateOtp(mobileno.getMobileno());
//		return ResponseEntity.ok(smsservice.sendOtp(mobileno.getMobileno(), otp));
//
//		
//		
//	}
//	
//	@PostMapping("/validateotp")
//	public ResponseEntity<?> validateOtp(@RequestBody MobileOtp motp ) {
//	     Boolean isValid= smsservice.validateOtp(motp);
//	     
//	     
//	     if (isValid) {
//	    	authservice.setMobileNo(motp.getMobileno());
//	    	  return new ResponseEntity<>(new ApiResponse("success", "OTP is valid.", true), HttpStatus.OK);
//	    	 
//           //  return ResponseEntity.ok("OTP is valid.");
//         } else {
//            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
//             return new ResponseEntity<>(new ApiResponse("success", "OTP is valid.", false), HttpStatus.NOT_ACCEPTABLE);
//         }
//	     
//	     
//	}
//	
//	 
//	  @PostMapping("/setmpin")
//	  public ResponseEntity<?> setmpin(@RequestBody Mpin mpin){
//		  try {  
//			  Boolean value=authservice.setmobilempin(mpin);
//			
//	            
//	            if (value) {
//	            	 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//	                         mpin.getMobileno(), mpin.getMpin());
//	                 try {
//	                     Authentication authenticatedDetails = manager.authenticate(authToken);
//	                     String jwtToken = utils.generateJwtToken(authenticatedDetails); // Assuming jwtUtils is your JwtUtils instance
//	                     UserPrincipal userPrincipal = (UserPrincipal) authenticatedDetails.getPrincipal();
//	            	        User user = userPrincipal.getUser();
//	            	
//	            		String j = utils.generateJwtToken(authenticatedDetails);
//	            		System.out.println(utils.validateJwtToken(j));
//	            		 AuthResp response = new AuthResp(
//	            		            "Auth Successful",
//	            		            utils.generateJwtToken(authenticatedDetails),
//	            		            user.getUserid(),
//	            		            user.getMobileNumber(),
//	            		            user.getFirstName(),
//	            		            user.getLastName(),
//								 user.getUtype(),
//								 user.getBulkPayout()
//	            		        );
//	            		  return ResponseEntity.ok(response);
//	                    // return ResponseEntity.ok(new AuthResp("Auth Successful", jwtToken));
//	                 } catch (BadCredentialsException e) {
//	                     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//	                 }
//	       
//	               // return new ResponseEntity<>("mpin set successfully", HttpStatus.OK);
//	            } else {
//	                return new ResponseEntity<>(new RuntimeException(), HttpStatus.NOT_FOUND);
//	            }
//	        } catch (NumberFormatException e) {
//	            return new ResponseEntity<>("Invalid mobile number format", HttpStatus.BAD_REQUEST);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>("Error processing request", HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//		  }
//	  
//	  
//	  @PostMapping("/validatemobilenumber")
//	  public ResponseEntity<?> signin(@RequestBody MobileNumber mobileNumber) {
//	        try {
//	            System.out.println("Signin endpoint hit with mobile number: " + mobileNumber.getMobileno());
//	            
//	            String mobile = mobileNumber.getMobileno();
//
//	            Boolean value = authservice.getByMobileNo(mobile);
//	            
//	            if (value) {
//	                return new ResponseEntity<>(new ApiResponse("success", "Mobile number is valid", true), HttpStatus.OK);
//	            } else {
//	                return new ResponseEntity<>(new ApiResponse("error", "Mobile number not found", false), HttpStatus.NOT_FOUND);
//	            }
//	        } catch (NumberFormatException e) {
//	            return new ResponseEntity<>(new ApiResponse("error", "Invalid mobile number format"), HttpStatus.BAD_REQUEST);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>(new ApiResponse("error", "Error processing request"), HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }
//	   
//    
//		
//		
//	
//
//}
