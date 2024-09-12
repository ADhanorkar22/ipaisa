package com.Ipaisa.UserController;

//import com.Ipaisa.CustomExceptions.ApiResponse;
//import com.Ipaisa.Entitys.*;
//import com.Ipaisa.Service.UserRoleServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.Ipaisa.Jwt_Utils.JwtUtils;
//import com.Ipaisa.Repository.UserRepositery;
//import com.Ipaisa.Service.AuthService;
//import com.Ipaisa.Service.AuthenticationService;
//import com.Ipaisa.Service.IUserDao;
//import com.Ipaisa.Service.UserPrincipal;
//import com.Ipaisa.dto.AuthRequest;
//import com.Ipaisa.dto.AuthResp;
//
//import jakarta.validation.Valid;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.*;
import com.Ipaisa.Service.UserRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Service.AuthService;
import com.Ipaisa.Service.AuthenticationService;
import com.Ipaisa.Service.IUserDao;
import com.Ipaisa.Service.UserPrincipal;
import com.Ipaisa.dto.AuthRequest;
import com.Ipaisa.dto.AuthResp;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = {"https://ipaisa.co.in","https://api.ipaisa.co.in"})
//@CrossOrigin(origins = {"https://ipaisa.co.in", "https://www.ipaisa.co.in", "https://api.ipaisa.co.in", "http://localhost:3000"})
public class AuthController {
	
    @Autowired
    private JwtUtils utils;

    @Autowired
    private AuthenticationManager manager;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private AuthService authservice;
    @Autowired
	private UserDetailsService udeatils;

	@Autowired
	private UserRoleServiceImpl urole;
	
	 @Autowired
	    private IUserDao userdao;
	 @Autowired
	private UserRepositery userRepo;
	 
	 

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @GetMapping("/home")
    public String home() {
    	return "hello";
    }


//	@PostMapping("/signin")
//	public ResponseEntity<?> validateUserCreateToken(@RequestBody AuthRequest request) {
//
//
//		System.out.println("signin controller");
//		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//				request.getMobileNumber(), request.getMpin());
//		System.out.println(request.getMobileNumber());
//		try {
//			Authentication authenticatedDetails = manager.authenticate(authToken);
//			String jwtToken = utils.generateJwtToken(authenticatedDetails); // Assuming jwtUtils is your JwtUtils instance
//			UserPrincipal userPrincipal = (UserPrincipal) authenticatedDetails.getPrincipal();
//			User user = userPrincipal.getUser();
//			UserRoleDto udto=this.urole.getRoleById(user.getRole().getId());
//
//
//			String j = utils.generateJwtToken(authenticatedDetails);
//			System.out.println("here");
//			//	System.out.println(utils.validateJwtToken(j));
//			AuthResp response = new AuthResp(
//					"Auth Successful",
//					utils.generateJwtToken(authenticatedDetails),
//					user.getUserid(),
//					user.getMobileNumber(),
//					user.getFirstName(),
//					user.getLastName(),
//					udto.getUserRole()
//
//			);
//			return ResponseEntity.ok(response);
//			//  return ResponseEntity.ok(new AuthResp("Auth Successful", jwtToken));
//		} catch (BadCredentialsException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Invalid Credentials", false));
//		}
//	}
    
    @PostMapping("/signin")
    public ResponseEntity<?> validateUserCreateToken(@RequestBody AuthRequest request) {
    		
        System.out.println("signin controller");
       
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getMobileNumber(), request.getMpin());
        System.out.println(request.getMobileNumber());
        try {
            Authentication authenticatedDetails = manager.authenticate(authToken);
            String jwtToken = utils.generateJwtToken(authenticatedDetails); // Assuming jwtUtils is your JwtUtils instance
            UserPrincipal userPrincipal = (UserPrincipal) authenticatedDetails.getPrincipal();
            User user = userPrincipal.getUser();
           
            
            if(user.getIsDeleted().equals(Deleted.TRUE)){
            	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Invalid Credentials", false));
            }
            
           // if()
            String isFirstLogin=user.getIsFirstLogin().toString();
            UserRoleDto udto = this.urole.getRoleById(user.getRole().getId());
            System.out.println("nsisxssnxjkns==========");

            AuthResp authResp = new AuthResp(
                    "Auth Successful",
                    jwtToken,
                    user.getUserid(),
                    user.getMobileNumber(),
                    user.getFirstName(),
                    user.getLastName(),
                    udto.getUserRole(),
                   user.getBulkPayout(),
                   user.getStatus().toString(),
                   isFirstLogin
                   
                   
            ); 
            if(user.getIsFirstLogin().toString().equals("TRUE"))
            {
           	 user.setIsFirstLogin(IsFirstLogin.FALSE);
           	 this.userRepo.save(user);
           	 };

           // ApiResponse<AuthResp> apiResponse = new ApiResponse<>("Login successful", true, authResp);

            return ResponseEntity.ok(authResp);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Invalid Credentials", false));
        }
    }

    
	@PostMapping("/register")
    public ResponseEntity<?> saveUsers(@RequestBody UsersDetail user,@RequestHeader("Authorization") String token) {
		 Optional<UserDetails> optionalUserDetails = authenticationService.authenticateUserByToken(token);

		 String createdpartner = user.getUtype();
		
		 
		 
		 
	        if (optionalUserDetails.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
	        }
	        UserDetails userDetails = optionalUserDetails.get();
	        String parentid = user.getParentId();
	        
	        System.out.println("====================================>"+parentid);
	        
	        
	        
	        
	        
	        
	        

		User chk=this.userRepo.findByMobileNumber(user.getMobileNumber());
    	User adhr=this.userRepo.findByAadharNumber(user.getAadharNumber());
    	
    	
    	 if(chk!=null && adhr!=null)
    	{
    		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
    				.body(new ApiResponse("AadharNumber & MobileNumber is Present", false));
    	}
    	 else if(chk!=null)
    	{
    		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ApiResponse("Mobile Number is Present", false));
    	}
    	else if(adhr!=null)																																																									
    	{
    		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ApiResponse("Aadhar Number is Present", false));
    	}
    		else {
    			
    			return new ResponseEntity<>(userdao.saveUser(user,parentid), HttpStatus.CREATED);
    		}
    }

	

	
//	@Autowired
//	private IUserDao userdao;
//	
//	  @PostMapping("/register")
//	    public ResponseEntity<?> saveUsers(@RequestBody UsersDetail user) {
//	        return new ResponseEntity<>(userdao.saveUser(user), HttpStatus.CREATED);
//	    }
    
    
//    @GetMapping("/getbymobileno/{mno}")
//    public ResponseEntity<?> getuser(String mno){
//    	return ResponseEntity.ok(authservice.getbyno(mno));
//    }



	@PostMapping("/validatemobilenumber")
	  public ResponseEntity<?> signin(@RequestBody MobileNumber mobileNumber) {
	        try {
	            System.out.println("Signin endpoint hit with mobile number: " + mobileNumber.getMobileno());
	            System.out.println("inside auth/validate");
	            String mobile = mobileNumber.getMobileno();
	            User u = authservice.signinn(mobile);
	          
	           
	            Map<String ,Boolean> response=new HashMap<>();
	            if(u!=null && (u.getMpin()!=null && !u.getMpin().isBlank() )    ) {
	            	System.out.println("here");
	            response.put("mobileValidate",true );
	            response.put("setMpin",true );
	            return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	            else if(u!=null &&( u.getMpin()==null||u.getMpin().isBlank())) {
		            response.put("mobileValidate",true );
		            response.put("setMpin",false );
		            return new ResponseEntity<>(response, HttpStatus.OK);
		            }
	            else {
	            	 response.put("mobileValidate",false );
		            response.put("setMpin",false );
	            	return new ResponseEntity<>(response, HttpStatus.OK);
	            }
	          
	        } catch (NumberFormatException e) {
	            return new ResponseEntity<>("Invalid mobile number format", HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error processing request", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	  
	  
	  @PostMapping("/setmpin")
	  public ResponseEntity<?> setmpin(@RequestBody Mpin mpin){
		  try {  
			  Boolean value=authservice.setmpin(mpin);
	            
	            if (value) {
	                return new ResponseEntity<>(new ApiResponse<>("Mpin Set Successfully", true), HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(new RuntimeException(), HttpStatus.NOT_FOUND);
	            }
	        } catch (NumberFormatException e) {
	            return new ResponseEntity<>("Invalid mobile number format", HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error processing request", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		  }
	  
	  @PostMapping("/forgetsetmpin")
	  public ResponseEntity<?> forgetsetmpin(@RequestBody Mpin mpin){
		  try {  
			  Boolean value=authservice.setmpin(mpin);
	            
	            if (value) {
	                return new ResponseEntity<>("mpin set successfully", HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>(new RuntimeException(), HttpStatus.NOT_FOUND);
	            }
	        } catch (NumberFormatException e) {
	            return new ResponseEntity<>("Invalid mobile number format", HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Error processing request", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		  }
	  
	  
	  
	  @GetMapping("/deletepartner/{id}")
	  public ResponseEntity<?> deletePartner(/*Authentication auth*/ @PathVariable String id){
		  try {
			     
		        return authservice.deletePartner(id);

		    } catch (Exception e) {
		      
		       
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("An error occurred while attempting to delete the partner: " + e.getMessage());
		    }
	  }
	  	
}
