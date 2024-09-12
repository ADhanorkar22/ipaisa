package com.Ipaisa.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.*;
import com.Ipaisa.Jwt_Utils.JwtUtils;

import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Responses.UserListResponse;
import com.Ipaisa.dto.AuthRequest;
import com.Ipaisa.dto.AuthResp;
import com.amazonaws.services.alexaforbusiness.model.NotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

	@Autowired
	private JwtUtils utils;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private UserRepositery userrepo;

	@Autowired
	private JwtUtils jutil;
	
	@Autowired
	private PasswordEncoder encoder;
	
//	@Autowired
//	private MobileRepo mobilerepo;
//	
//	@Autowired
//	private MobileRepositery mobielRepo;

	@Override
	public ResponseEntity<?> Signin(AuthRequest request) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getMobileNumber(),
				request.getMpin());
		try {
			Authentication authenticatedDetails = manager.authenticate(authToken);
			 UserPrincipal userPrincipal = (UserPrincipal) authenticatedDetails.getPrincipal();
		        User user = userPrincipal.getUser();
		
			String j = utils.generateJwtToken(authenticatedDetails);
			System.out.println(jutil.validateJwtToken(j));
			 AuthResp response = new AuthResp(
			            "Auth Successful",
			            utils.generateJwtToken(authenticatedDetails),
			            user.getUserid(),
			            user.getMobileNumber(),
			            user.getFirstName(),
			            user.getLastName(),
						user.getUtype(),
						user.getBulkPayout(),
						user.getStatus().toString(),
						user.getIsFirstLogin().toString()
						
			        );
			  return ResponseEntity.ok(response);
			
			//return ResponseEntity.ok(new AuthResp("Auth Successfull", utils.generateJwtToken(authenticatedDetails)));
		} catch (BadCredentialsException e) {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you are not auathrized to access this resource");
		}

	}

	@Override
	public User signinn(String mno) {

		return userrepo.findByMobileNumber(mno);
	}
	
	
//	@Override
//	public Boolean getByMobileNo(String mno) {
//
//		return mobielRepo.findByMobileNumber(mno) != null;
//	}

	@Override
	public Boolean setmpin(Mpin mpin) {
	
		try {
			if(mpin.getType()==null) {
            User user = userrepo.findByMobileNumber(mpin.getMobileno());
            if (user == null) {
                throw new NotFoundException ("User not found for mobile number: " + mpin.getMobileno());
            }
            
            user.setMpin(encoder.encode(mpin.getMpin()));
            userrepo.save(user);
			}
            return true;
			
        } catch (Exception e) {
           
            e.printStackTrace();
            return false;
        }
	}

	@Override
	public ResponseEntity<?> deletePartner(String uid) {
	    try {
	      
	        List<User> userlist = userrepo.findHierarchicalUsers(uid);
	        userlist.forEach(u -> {
	            if (u.getRole() != null) {
	                UserRole role = u.getRole();
	                String userRole = role.getUserrole();
	                u.setUtype(userRole); 
	            }
	        });
	        if (userlist.isEmpty()) {
	            User deletePartner = userrepo.findById(uid).orElse(null);
	            
	            if (deletePartner != null) {
	                deletePartner.setIsDeleted(Deleted.TRUE); 
	                userrepo.save(deletePartner); 
	              
	                return ResponseEntity.status(HttpStatus.OK)
		                    .body(new ApiResponse<>("User deleted successfully!", true));
		            
		          
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
	            }
	        } else {
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(new ApiResponse<>("Cannot delete the partner as associated users are present.", false));
	            
	          
	        }
	    } catch (Exception e) {
	       
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while attempting to delete the partner: " + e.getMessage());
	    }
	}


	
	
	
	
	
//	@Override
//	public void setMobileNo(String mobileno) {
//	
//			MobileUser muser=new MobileUser();
//		//	muser.setMobileno(Long.parseUnsignedLong(mobileno));
//			muser.setMobileno(mobileno);
//			mobilerepo.save(muser);
//	}
//	@Override
//	public ResponseEntity<?> getbyno(String mno) {
//		
//		return ResponseEntity.status(HttpStatus.OK).body(userrepo.findByMobileNumber(mno));
//	}
//	@Override
//	public Boolean setmobilempin(Mpin mpin) {
//		try {
//			
//            MobileUser user = mobielRepo.findByMobileNumber(mpin.getMobileno());
//            if (user == null) {
//                throw new NotFoundException ("User not found for mobile number: " + mpin.getMobileno());
//            }
//                else {
//            user.setMpin(encoder.encode(mpin.getMpin()));
//         
//            mobielRepo.save(user);
//			}
//            return true;
//            
//			
//        } catch (Exception e) {
//           
//            e.printStackTrace();
//          
//        }
//		  return false;
//	}
//	

}
