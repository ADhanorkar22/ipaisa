
package com.Ipaisa.UserController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.BankDTO;
import com.Ipaisa.Entitys.Deleted;
import com.Ipaisa.Entitys.Providers;
import com.Ipaisa.Entitys.Transaction;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Entitys.UserRole;
import com.Ipaisa.Entitys.UsersDetail;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Responses.UserListResponse;
import com.Ipaisa.Service.IUserDao;
import com.Ipaisa.Service.ProviderService;
import com.Ipaisa.Service.TransactionService;
import com.Ipaisa.Service.UserRoleServices;
import com.Ipaisa.dto.UpUser;
import com.Ipaisa.dto.WalletTransactionDTO;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/auth")
public class UsersController {
	@Autowired
	private UserDetailsService udeatils;

	@Autowired
	private JwtUtils utils;
	
	@Autowired
	private ProviderService providersService;

	@Autowired
	private UserRoleServices roleServ;
	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private UserRepositery uRepo;

	@Autowired
	private IUserDao userdao;

	@Autowired
	private TransactionService tserv;
	
//	
//	  @PostMapping("/User_Register")
//	    public ResponseEntity<?> saveUsers(@RequestBody UsersDetail user) {
//	        return new ResponseEntity<>(userdao.saveUser(user), HttpStatus.CREATED);
//	    }
//	  

	@PostMapping("/addBeneficiary")
	public ResponseEntity<?> saveBeneficiary(@RequestHeader("Authorization") String token,@RequestBody BankDTO bank) {
		String t = null;
		System.out.println(token);
		if (token.startsWith("Bearer ")) {
			t = token.substring(7);
			System.out.println(t);
		}
		String username = utils.getUserNameFromJwtToken(t);
		UserDetails userDetails = udeatils.loadUserByUsername(username);
		String userid = userDetails.getUsername();
		System.out.println("__________________===="+userid);
		System.out.println(bank.getName());
		return new ResponseEntity<>(userdao.saveBeneficiary(bank, userid), HttpStatus.CREATED);

	}
//	  @PostMapping("/User_Address")
//	  public ResponseEntity<?> saveAddress(AddressDTO address,@RequestHeader("Authorization") String token){
//		  if (token.startsWith("Bearer ")&& utils.validateJwtToken(token)) {
//		        token = token.substring(7);
//		        }
//		    String userid = utils.getUserNameFromJwtToken(token);
//		  
//		  return new ResponseEntity<>(userdao.saveAddress(address,userid), HttpStatus.CREATED);
//		  
//	  }

	@GetMapping("/getDetails")
	public ResponseEntity<?> getDetailsById(@RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ") && utils.validateJwtToken(token)) {
			token = token.substring(7);
		}
		String userid = utils.getUserNameFromJwtToken(token);
		return ResponseEntity.ok(userdao.getDetailsById(userid));
	}

//	    @GetMapping("/list")
//	    public ResponseEntity<?> listAllUsers() {
//	        List<User> list = userdao.listAllUsers();
//	        if (list.isEmpty())
//	            return new ResponseEntity<>("Empty Emp List!!", HttpStatus.OK);
//	        return new ResponseEntity<>(list, HttpStatus.OK);
//	    }

	@PutMapping("update/{id}")
	public ResponseEntity<?> updateUserDetails(@RequestBody UsersDetail user) {
		return ResponseEntity.ok(userdao.updateUsersDetails(user));
	}

	@GetMapping("/count")
	public ResponseEntity<?> GetCount(@RequestHeader("Authorization") String token) {
		try {
			if (token == null || !token.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header");
			}

			String jwtToken = token.substring(7);
			String username = utils.getUserNameFromJwtToken(jwtToken);
			UserDetails userDetails = udeatils.loadUserByUsername(username);

			if (userDetails == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
			}

			String mobileno = userDetails.getUsername();
			User user = uRepo.findByMobileNumber(mobileno);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new UserListResponse(HttpStatus.UNAUTHORIZED.value(), "User not found", null));
			}
			String userid = user.getUserid();
			System.out.println(userid);

			List<User> list = uRepo.findHierarchicalUsers(userid);
			list.forEach(u -> {
				if (u.getRole() != null) {
					UserRole role = u.getRole();
					String userRole = role.getUserrole();
					u.setUtype(userRole);
				}
			});
			
			Map<String, Long> roleCounts = new HashMap<>();

			list.forEach(u -> {
			    if (u.getRole() != null) {
			        String role =u.getRole().getUserRole();
			        
			        roleCounts.put(role, roleCounts.getOrDefault(role, 0L) + 1);
			    }
			});
			
			
			if (list.isEmpty()) {
				return ResponseEntity.ok(UserListResponse.notFound("Empty User List!!"));
			}

			return new ResponseEntity<>(new com.Ipaisa.Entitys.ApiResponse("Success", "Count is",roleCounts), HttpStatus.OK) ;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(UserListResponse.notFound("Error occurred: " + e.getMessage()));
		
		}

	}

//	@GetMapping("/reciverUnderSender/{id}")
//	public ResponseEntity<?> reciverUnderSenderInfo(@RequestHeader("Authorization") String token,@PathVariable String id) {
//		String t=null;
//		System.out.println(token);
//		if (token.startsWith("Bearer ")) {
//			t = token.substring(7);
//			System.out.println(t);
//		}
//		String username = utils.getUserNameFromJwtToken(t);
//		UserDetails userDetails = udeatils.loadUserByUsername(username);
//		String mobileno=userDetails.getUsername();
//
//		return ResponseEntity.status(HttpStatus.OK).body(userdao.reciverUnderSenderInfo(id,mobileno));
//	}

//	    @PostMapping("/transaction")
//	    public ResponseEntity<?> TransactionDetails(@RequestBody @Valid Transaction  transaction,@RequestHeader("Authorization") String token) {
//	    	System.out.println(transaction.toString());
//	    	  String t=null;
//	   		   System.out.println(token);
//	   		   if (token.startsWith("Bearer ")) {
//	   		        t = token.substring(7);
//	   		        System.out.println(t);
//	   		        }
//	   		   String username = utils.getUserNameFromJwtToken(t);
//	   	        UserDetails userDetails = udeatils.loadUserByUsername(username);
//	   	        String logeedin_user_mobileno=userDetails.getUsername();
//	    	
//	    return  userdao.TransactionDetails(transaction,logeedin_user_mobileno);
//	    }

//	@GetMapping("/getwalletbalance")
//	public ResponseEntity<?> getWalletBalance(@RequestHeader("Authorization") String token) {
//		String t = null;
//		System.out.println(token);
//
//		if (token.startsWith("Bearer ")) {
//			t = token.substring(7);
//			System.out.println(t);
//		}
//
//		String username = utils.getUserNameFromJwtToken(t);
//		UserDetails userDetails = udeatils.loadUserByUsername(username);
//		String userid = userDetails.getUsername();
//		User u = uRepo.findByMobileNumber(userid);
//		Map<String, Object> response = new HashMap<>();
//		response.put("mobileNumber", u.getMobileNumber());
//		response.put("walletBalance", u.getWalletBalance());
//		return ResponseEntity.status(HttpStatus.OK).body(response);
//	}

//	    @PostMapping
//	    public ResponseEntity<?> forgetMpin(@RequestParam String mobile){
//	    	
//	    }	
	//// 1) Get the json object from FrontEnd
	/// 2) Create the Dto for this
	@PutMapping("/updateUser")
	public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token,@RequestBody UpUser upUser) {
		String t = null;
		System.out.println(token);
		if (token.startsWith("Bearer ")) {
			t = token.substring(7);
			System.out.println(t);
		}
		String username = utils.getUserNameFromJwtToken(t);
		UserDetails userDetails = udeatils.loadUserByUsername(username);
		String userid = userDetails.getUsername();
		System.out.println("userid====>"+userid);
		User logUser = uRepo.findByMobileNumber(userid);
		User upUserf=uRepo.findByMobileNumber(upUser.getMobileNumber());
		
		Boolean response=false;
		System.out.println("cek --------------"+logUser.getRole().getUserrole());
		if(logUser.getRole().getUserrole().matches("ADMIN") || logUser.getUserid()== upUserf.getUserid())
		{
			if(logUser.getRole().getUserrole().matches("ADMIN")&& logUser.getUserid().matches(upUserf.getUserid()))
			{
				response=this.userdao.upUser(upUser, userid);
			}
			response=this.userdao.upUser(upUser, upUserf.getMobileNumber());
			
		}else
		{
			return new ResponseEntity(new ApiResponse("You dont have Access to Update ", false),HttpStatus.BAD_REQUEST);
		}
		
		if(response)
		{			 
		  return ResponseEntity.ok(new ApiResponse<>("User Updated Succesfully", true));
		}
		  return ResponseEntity.ok(new ApiResponse<>("User Not Updated ", false));
		
	}
	@GetMapping("/getwalletbalance")
	public ResponseEntity<?> getWalletBalance(@RequestHeader("Authorization") String token) {
		try {
			if (token == null || !token.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header");
			}

			String jwtToken = token.substring(7);
			String username = utils.getUserNameFromJwtToken(jwtToken);
			UserDetails userDetails = udeatils.loadUserByUsername(username);

			if (userDetails == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
			}

			String userid = userDetails.getUsername();
			User user = uRepo.findByMobileNumber(userid);

			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}

			Map<String, Object> response = new HashMap<>();
			response.put("mobileNumber", user.getMobileNumber());
			response.put("walletBalance", user.getWalletBalance());

			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	@PostMapping("/transaction")
	public ResponseEntity<?> TransactionDetails(@RequestBody @Valid Transaction transaction,
			@RequestHeader("Authorization") String token) {
		try {
			System.out.println(transaction.toString());

			if (token == null || !token.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header");
			}

			String jwtToken = token.substring(7);
			String username = utils.getUserNameFromJwtToken(jwtToken);
			UserDetails userDetails = udeatils.loadUserByUsername(username);

			if (userDetails == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
			}

			String loggedinUserMobileNo = userDetails.getUsername();
			Object response = userdao.TransactionDetailsNew(transaction, loggedinUserMobileNo);

			if (response == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transaction failed");
			}

			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	@GetMapping("/reciverUnderSender/{id}")
	public ResponseEntity<?> reciverUnderSenderInfo(@RequestHeader("Authorization") String token,
			@PathVariable String id) {
		try {
			if (token == null || !token.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header");
			}

			String jwtToken = token.substring(7);
			String username = utils.getUserNameFromJwtToken(jwtToken);
			UserDetails userDetails = udeatils.loadUserByUsername(username);

			if (userDetails == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
			}

			String mobileno = userDetails.getUsername();
			System.out.println("id--------------->>>>>>>>"+id);
//			Object response = userdao.reciverUnderSenderInfo(id, mobileno);
			User u=this.uRepo.findByMobileNumber(id);
			
			
			if (u == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
			}
			else {
				
				Map<String, String> response=new HashMap<>();
				response.put("firstName", u.getFirstName());
				response.put("middleName", u.getMiddleName());
				response.put("lastName", u.getLastName());
				response.put("uType", u.getRole().getUserrole());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			


		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> listAllUsers(@RequestHeader("Authorization") String token) {
		try {
			if (token == null || !token.startsWith("Bearer ")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header");
			}

			String jwtToken = token.substring(7);
			String username = utils.getUserNameFromJwtToken(jwtToken);
			UserDetails userDetails = udeatils.loadUserByUsername(username);

			if (userDetails == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
			}

			String mobileno = userDetails.getUsername();
			User user = uRepo.findByMobileNumber(mobileno);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new UserListResponse(HttpStatus.UNAUTHORIZED.value(), "User not found", null));
			}
			String userid = user.getUserid();
			System.out.println("====>"+userid);

			List<User> list = uRepo.findHierarchicalUsers(userid);
			list.forEach(u -> {
				if (u.getRole() != null) {
					UserRole role = u.getRole();
					String userRole = role.getUserrole();
					u.setUtype(userRole);
				}
			});
			
			System.out.println(list.toString());
			
			if (list.isEmpty()||list==null) {
				return ResponseEntity.ok(UserListResponse.notFound("Empty User List!!"));
			}

			List<User> finallist=list
								.stream()
								.filter(e->e.getIsDeleted().toString().equals(Deleted.FALSE))
								
								.collect(Collectors.toList());
			
			System.out.println(finallist);
			
//			List<User> finallist = list.stream()
//				    .filter(u -> {
//				        if (u.getRole() != null) {
//				            UserRole role = u.getRole();
//				            String userRole = role.getUserrole();
//				            u.setUtype(userRole);
//				           
//				        }
//				        System.out.println(u.getUserid()+"  "+ u.getIsDeleted().equals(Deleted.FALSE));
//				        return u.getIsDeleted().equals(Deleted.FALSE);
//				    })
//				    .collect(Collectors.toList());
			
			
			
			
			return ResponseEntity.ok(UserListResponse.success("Users fetched successfully", finallist));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(UserListResponse.notFound("Error occurred: " + e.getMessage()));
		}

	}
	
	
	
	
	
	


//	@GetMapping("/wtransReportDeb")
//	public ResponseEntity<?> getwTransDetails(@RequestHeader("Authorization") String token) {
//	    if (token == null || !token.startsWith("Bearer ")) {
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid Authorization header", false));
//	    }
//
//	    String jwtToken = token.substring(7);
//	    String username;
//	    try {
//	        username = utils.getUserNameFromJwtToken(jwtToken);
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid JWT token", false));
//	    }
//
//	    UserDetails userDetails;
//	    try {
//	        userDetails = udeatils.loadUserByUsername(username);
//	    } catch (UsernameNotFoundException e) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found", false));
//	    }
//
//	    String mobileno = userDetails.getUsername();
//
//	    User u = uRepo.findByMobileNumber(mobileno);
//	    if (u == null) {
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found in the database", false));
//	    }
//
//	    ResponseEntity<?> response = tserv.getListOfWalletTrasDeb(u);
//	    return response;
//	}

	 @GetMapping("/wtransReportCred")
	    public ResponseEntity<?> getwTransDetailsCred(@RequestHeader("Authorization") String token) {
	        if (token == null || !token.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid Authorization header", false));
	        }

	        String jwtToken = token.substring(7);
	        String username;
	        try {
	            username = utils.getUserNameFromJwtToken(jwtToken);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid JWT token", false));
	        }

	        UserDetails userDetails;
	        try {
	            userDetails = udeatils.loadUserByUsername(username);
	        } catch (UsernameNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found", false));
	        }

	        String mobileno = userDetails.getUsername();

	        User u = uRepo.findByMobileNumber(mobileno);
	        if (u == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found in the database", false));
	        }

	        List<WalletTransactionDTO> transactions = this.tserv.getTransactionsByReceiver1(u);
	        if (transactions.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("No data found", false));
	        }

	        return ResponseEntity.ok(transactions);
	    }
	 
	 
	 @GetMapping("/wtransReportDebt")
	    public ResponseEntity<?> getwTransDetailsDebt(@RequestHeader("Authorization") String token) {
	        if (token == null || !token.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid Authorization header", false));
	        }

	        String jwtToken = token.substring(7);
	        String username;
	        try {
	            username = utils.getUserNameFromJwtToken(jwtToken);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid JWT token", false));
	        }

	        UserDetails userDetails;
	        try {
	            userDetails = udeatils.loadUserByUsername(username);
	        } catch (UsernameNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found", false));
	        }

	        String mobileno = userDetails.getUsername();

	        User u = uRepo.findByMobileNumber(mobileno);
	        if (u == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found in the database", false));
	        }

	        List<WalletTransactionDTO> transactions = this.tserv.getTransactionsByReceiver(u);
	        if (transactions.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("No data found", false));
	        }

	        return ResponseEntity.ok(transactions);
	    }
	 
	 
//	 @GetMapping("/getBankDetail")
//	 public ResponseEntity<?> getBankDetails(@RequestHeader("Authorization") String token){
//		 if (token == null || !token.startsWith("Bearer ")) {
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid Authorization header", false));
//	        }
//
//	        String jwtToken = token.substring(7);
//	        String username;
//	        try {
//	            username = utils.getUserNameFromJwtToken(jwtToken);
//	        } catch (Exception e) {
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid JWT token", false));
//	        }
//
//	        UserDetails userDetails;
//	        try {
//	            userDetails = udeatils.loadUserByUsername(username);
//	        } catch (UsernameNotFoundException e) {
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found", false));
//	        }
//	        String mobileno = userDetails.getUsername();
//	        
//	      
//	        
//	        
//		 
//		 return ResponseEntity.ok(  this.userdao.getAllBankDetails(mobileno));
//	 }
	 
	 @GetMapping("/getBankDetail")
	 public ResponseEntity<?> getBankDetails(@RequestHeader("Authorization") String token) {
	     if (token == null || !token.startsWith("Bearer ")) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid Authorization header", false));
	     }

	     String jwtToken = token.substring(7);
	     String username;
	     try {
	         username = utils.getUserNameFromJwtToken(jwtToken);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid JWT token", false));
	     }

	     UserDetails userDetails;
	     try {
	         userDetails = udeatils.loadUserByUsername(username);
	     } catch (UsernameNotFoundException e) {
	         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found", false));
	     }
	     String mobileNo = userDetails.getUsername();

	     // Call service to get bank details
	     return ResponseEntity.ok(this.userdao.getAllBankDetails(mobileNo));
	 }

	 @GetMapping("/easTxnDetails")
	 public ResponseEntity<?> getEaseBuzTxnDetail(@RequestHeader("Authorization") String token)
	 {
		  if (token == null || !token.startsWith("Bearer ")) {
		         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid Authorization header", false));
		     }

		     String jwtToken = token.substring(7);
		     String username;
		     try {
		         username = utils.getUserNameFromJwtToken(jwtToken);
		     } catch (Exception e) {
		         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid JWT token", false));
		     }

		     UserDetails userDetails;
		     try {
		         userDetails = udeatils.loadUserByUsername(username);
		     } catch (UsernameNotFoundException e) {
		         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found", false));
		     }
		     String mobileNo = userDetails.getUsername();
		     
		     User u=this.uRepo.findByMobileNumber(mobileNo);
		     
		 
		 return ResponseEntity.ok(this.tserv.getAllTxn(u));
	 }
	 
	 
	 @GetMapping("/pOuttTxnDetails")
	 public ResponseEntity<?> getInstPoutTxnDetail(@RequestHeader("Authorization") String token)
	 {
		  if (token == null || !token.startsWith("Bearer ")) {
		         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid Authorization header", false));
		     }

		     String jwtToken = token.substring(7);
		     String username;
		     try {
		         username = utils.getUserNameFromJwtToken(jwtToken);
		     } catch (Exception e) {
		         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Invalid JWT token", false));
		     }

		     UserDetails userDetails;
		     try {
		         userDetails = udeatils.loadUserByUsername(username);
		     } catch (UsernameNotFoundException e) {
		         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("User not found", false));
		     }
		     String mobileNo = userDetails.getUsername();
		     
		     User u=this.uRepo.findByMobileNumber(mobileNo);
		     
		 
		 return ResponseEntity.ok(this.tserv.getAllTxnPayOut(u));
	 }
	 
	 @PostMapping("/addproviders")
		public ResponseEntity<?> addProviders(@RequestBody Providers provider ){
			Providers p=providersService.addProviders(provider);
			
			if(p!=null) {
				return ResponseEntity.ok("provider added");
			}
			
			return (ResponseEntity<?>) ResponseEntity.internalServerError();
			
		}
	 	 
	 
}
