package com.Ipaisa.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.Ipaisa.dto.RusDto;
import com.Ipaisa.dto.UpUser;
import com.Ipaisa.dto.UserBankDetailsDTO;
import com.Ipaisa.dto.UserBankDetailsDTO.UserDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.Ipaisa.CustomExceptions.ResourceNotFoundException;
import com.Ipaisa.Entitys.*;
import com.Ipaisa.Repository.RollsRepositery;
import com.Ipaisa.Repository.UserBankDetailsRepositery;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Repository.TransactionRepositery;
import com.amazonaws.services.xray.model.Http;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Ipaisa.Repository.UserKycRepositery;
import com.Ipaisa.Repository.AddressRepositery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDaoImpl implements IUserDao {
    @Autowired
    private UserRepositery userRepo;

    @Autowired
    private UserKycRepositery kycRepo;

    @Autowired
    private AddressRepositery addRepo;

    @Autowired
    private EntityManager em;

    @Autowired
    private RollsRepositery rollrepo;
    
    @Autowired
    private TransactionRepositery transactionRepo;
    
   @Autowired 
   private UserBankDetailsRepositery userbankrepo;
    
    BusinessLogic bl=new BusinessLogic();
    
    @Autowired
    private ModelMapper usermapper;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private TransactionService transactionService;

    @Override
    public ResponseEntity<?> saveUser(UsersDetail ud,String parentid) {
    	
    	//User uu=this.userRepo.findByMobileNumber(mobileno);
    	
    	
    	
    	User u=bl.getUserInstance(ud ,rollrepo,userRepo,addRepo,parentid);
    	return ResponseEntity.ok("User saved successfully");
    	
    //	UsersAddress ad=bl.getUserAddressInstance(ud,u ,addRepo);
    	
       // User setuser = userRepo.save(u);
        //addRepo.save(ad);
        
       // UserKycDetail userKyc = BusinessLogic.getUserUserKycDetails(ud);
        //userKyc.setUser(setuser);
        //kycRepo.save(userKyc);


//        UsersAddress userAdd = BusinessLogic.getUserAddressInstance(ud);
//        userAdd.setUser(setuser);
//        addRepo.save(userAdd);

    }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
//    @Override
//   	public ResponseEntity<String> changeStatus(String id) {
//    	
//    	try {
//       	String active="active";
//       	String inactive="inactive";
//   	  // User u=userRepo.findByUserid(id);
//       //	System.out.println("here");
//   	    User u=userRepo.findByUserid(id);
//   	   System.out.println("here "+u);
//   	  // System.out.println()u.get;
//   	   String s=u.getStatus().toString();
//   	  
//   	 
//   	  
//   	   if(u!=null) {
//   		u.setStatus(s.equals("ACTIVE") ? Status.valueOf("INACTIVE") : Status.valueOf("ACTIVE"));
////   		   if(s=="ACTIVE") {
////   			  
////   			   System.out.println("active");
////   			u.setStatus(u.getStatus().valueOf(inactive.toUpperCase()));
////   		   }
////   		
////   		   else {
////   		u.setStatus(u.getStatus().valueOf(active.toUpperCase()));
////   		   }
////   		
//   		userRepo.save(u);
//   	   }
//   		
//   		
//    	  return ResponseEntity.ok("Status updated successfully.");
//    } catch (Exception e) {
//        e.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
//    }
//   	}
    
    public ResponseEntity<?> changeStatus(String id) {
        try {
            User u = userRepo.findByUserid(id);
            if (u == null) {
//             return new  ResponseEntity(new com.Ipaisa.CustomExceptions.ApiResponse<>("No User Found - "+id, false),HttpStatus.FORBIDDEN);
            	return new  ResponseEntity(new ApiResponse("User Not Found ", "false","Id -"+id),HttpStatus.FORBIDDEN);//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            String currentStatus = u.getStatus().toString();
            Status newStatus = currentStatus.equals("ACTIVE") ? Status.INACTIVE : Status.ACTIVE;
            u.setStatus(newStatus);
            userRepo.save(u);
            return new ResponseEntity<>( new ApiResponse("Status updated successfully to - "+newStatus, "true",newStatus),HttpStatus.OK);
  //          return new ResponseEntity(new ApiResponse("Status updated successfully to " + newStatus.toString(), true), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update status: " + e.getMessage());
        }
    }
    
	@Override
	public Boolean isUserPresent(String mno) {
		User u=userRepo.findByMobileNumber(mno);
		if(u!=null) {
			return true;
		}
		return false;
	}

   	@Override
   	public List<User> listAllUsers() {
   		List<User> list = userRepo.findAll();
   		list.forEach(user -> {
          if (user.getRole() != null) {
              UserRole role = user.getRole();
              String userRole = role.getUserrole();
              user.setUtype(userRole);
          }
      });
   		
   		
   	System.out.println(userRepo.findAll().size());
   		
   		return list;
   	}
   	@Override
   	public User getDetailsById(String id) {
   		  return userRepo.findById(id)
   	                .orElseThrow(() -> new RuntimeException("User Not Found!!! " + id));
   	    }

   	@Override
   	public ResponseEntity<?> updateUsersDetails(UsersDetail user) {
   		
   	//	return userRepo.save(user);
   		return ResponseEntity.ok(null);
   	}

	@Override
	public long GetCount() {
		return userRepo.count();
		
	}

	@Override
	public RusDto reciverUnderSenderInfo(String childmobile, String mobileno) {
		//	User u= userRepo.reciverUnderSenderInfo(userid,parentid);
		User loggedinuser=userRepo.findByMobileNumber(mobileno);
		String parentuser=loggedinuser.getUserid();
		System.out.println(parentuser);
		User child=userRepo.findByMobileNumber(childmobile);
		String childuserid=child.getUserid();
		System.out.println(childuserid);
		User u= userRepo.reciverUnderSenderInfo(childuserid,parentuser);
		//System.out.println("uuuuuuuuuuuuuuuuuu"+);
		String Role=u.getRole().getUserRole();
		String firstname =u.getFirstName();
		return  new RusDto (firstname,Role);
	}

//	@Override
//	public ResponseEntity<?> TransactionDetails(Transaction transaction,String user_loggedin_mobileno) {
//		User sid=userRepo.findByMobileNumber(user_loggedin_mobileno);
//		User rid=userRepo.findByMobileNumber(transaction.getReceiverId());
//
//		if (!rid.getParentId().equals(sid.getUserid())){
//			return new ResponseEntity<>(new com.Ipaisa.CustomExceptions.ApiResponse<Object>("User is not under sender",false), HttpStatus.NOT_ACCEPTABLE);	
//		}
//		
//		Double balance=transaction.getAmount();
//		if (sid.getWalletBalance()>balance) {
//		
//		Double comm=rid.getRole().getCommission();
//		Double amount=  balance+((comm/100)*balance);
//		
//		sid.setWalletBalance(transaction.getAmount()-sid.getWalletBalance());
//		
//		rid.setWalletBalance(amount);
//		}else {
//			   return new ResponseEntity<>(new com.Ipaisa.CustomExceptions.ApiResponse<Object>("Balance is not sufficient",false), HttpStatus.NOT_ACCEPTABLE);
//		}
//		
//		WalletTransaction wt=new WalletTransaction(sid,rid,transaction.getAmount(),transaction.getRemark());
//		System.out.println(wt.getUtype().valueOf(transaction.getReceiverType()));
//		wt.setUtype(wt.getUtype().valueOf(transaction.getReceiverType()));
//		
//		userRepo.save(sid);
//		userRepo.save(rid);
//		transactionRepo.save(wt);
//		return ResponseEntity.status(HttpStatus.OK).body("Amount transfer successfully");
//			
//	}

	@Override
	public ResponseEntity<?> TransactionDetails(Transaction transaction, String user_loggedin_mobileno) {
	    try {
	        // Find sender and receiver users
	        User sender = userRepo.findByMobileNumber(user_loggedin_mobileno);
	        System.out.println("Sender Mobile -> "+sender.getMobileNumber());
	        User receiver = userRepo.findByMobileNumber(transaction.getReceiverId());
	        System.out.println("rcv Mobile -> "+receiver.getMobileNumber());
	        Double amount = transaction.getAmount();
	        Double commission = receiver.getRole().getCommission();
	        
	        Double finalAmount=0.0;
	        Double comm=0.0;
	        // Check if receiver is under sender
	        System.out.println("Reci ----"+receiver.getParentId());
	        System.out.println("Send ----"+sender.getUserid());
	        Set<User> list=new HashSet<>();
	        String parent_id= receiver.getParentId();
	        
	        
	        while (parent_id != null && !parent_id.equals("AD0001")) {
	            User user = userRepo.findByUserid(parent_id);
	            
	            if (user != null ) {
	                list.add(user);
	                parent_id = user.getParentId();
	               
	            } else {
	                break; 
	            }
	            System.out.println(parent_id);
	        }

	        
	        for (User user : list) {
	        	comm=comm+((user.getRole().getCommission()/100)*amount);
	        	user.setWalletBalance(user.getWalletBalance()+ ( user.getRole().getCommission()/ 100) * amount);
	        	
	        	System.out.println("user list====>"+user);
			}
	        
	        finalAmount=amount+comm+((receiver.getRole().getCommission()/100)*100);
	        
	        
//	        
//	        if (!receiver.getParentId().matches(sender.getUserid())) {
//	            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//	                    .body(new ApiResponse("User is not under sender", false));
//	        }
	        
	        

	        // Check if sender has sufficient balance
//	        Double amount = transaction.getAmount();
//	       
//	        if (sender.getWalletBalance() < amount) {
//	            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//	                    .body(new ApiResponse("Balance is not sufficient", false));
//	        }
//
//	        // Calculate commission based on receiver's role
//	        Double commission = receiver.getRole().getCommission();
//	        Double finalAmount;
//	        
//	        if (sender.getCategory().equals("FIXED")) {
//	            finalAmount = amount + ((commission / 100) * amount);
//	        } else {
//	            finalAmount = amount;
//	        }
//  
//	        // Update wallet balances
	        sender.setWalletBalance(sender.getWalletBalance() - finalAmount);
	        receiver.setWalletBalance(receiver.getWalletBalance() +((receiver.getRole().getCommission()/100)*100)+amount );
//
//	        // Create and save wallet transaction
//	        WalletTransaction walletTransaction = new WalletTransaction(sender, receiver, transaction.getAmount(), transaction.getRemark());
//	        walletTransaction.setUtype(Utype.valueOf(transaction.getReceiverType())); // Assuming UserType is an enum
//	        transactionRepo.save(walletTransaction);
//
//	        // Save updated sender and receiver
//	        userRepo.save(sender);
//	        userRepo.save(receiver);

	        return ResponseEntity.status(HttpStatus.OK).body("Amount transfer successfully");
	    } catch (Exception e) {
	        // Handle any unexpected exceptions
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse("Error processing transaction: " + e.getMessage(), false));
	    }
	}

	
//	@Override
//	public ResponseEntity<?> saveBeneficiary(  BankDTO bank,String userid) {
//		System.out.println(bank);
//		UserBankDetails ub=usermapper.map(bank, UserBankDetails.class);
//		ub.setAcccountNumber(Long.parseUnsignedLong(bank.getAccountNumber()));
//		 // Manually set a unique ID
//	    ub.setId(UUID.randomUUID().toString());
//		ub.setName(bank.getName());
//		//UserBankDetails ub=new UserBankDetails(bank.getName(),bank.getBankName(),bank.getBranch(),Long.parseUnsignedLong(bank.getAccountNumber()),bank.getIfsc());
//		User u=userRepo.findByMobileNumber(userid);
//		ub.setUser(u);
//		userbankrepo.save(ub);
//		
//		 return ResponseEntity.ok("Beneficiary added successfully");
//	}
	@Override
	public ResponseEntity<?> saveBeneficiary(  BankDTO bank,String userid) {
		System.out.println(bank);
		UserBankDetails ub=null;
		
		System.out.println(bank);
		System.out.println(bank.getAccountNumber());
			User u=this.userRepo.findByMobileNumber(userid);
			List<UserBankDetails> list=this.userbankrepo.findByUser(u);
			
//		UserBankDetails foundBank=this.userbankrepo.findByAccountNumber(Long.parseLong(bank.getAccountNumber()));
			Optional<UserBankDetails> detail = list.stream()
				    .filter(e -> e.getAccountNumber().equals(Long.parseLong(bank.getAccountNumber())))
				    .findFirst();
			System.out.println(detail);
	//	System.out.println(foundBank);
		
		if((detail.isEmpty()))
		{
			ub=new UserBankDetails();
			ub.setAcccountNumber(Long.parseUnsignedLong(bank.getAccountNumber()));
			 // Manually set a unique ID
		    ub.setId(UUID.randomUUID().toString());
			ub.setName(bank.getName());
			ub.setBankName(bank.getBankName());
			ub.setIfsc(bank.getIfsc());
			ub.setBranch(bank.getBranch());	//UserBankDetails ub=new UserBankDetails(bank.getName(),bank.getBankName(),bank.getBranch(),Long.parseUnsignedLong(bank.getAccountNumber()),bank.getIfsc());
			User u1=userRepo.findByMobileNumber(userid);
			ub.setUser(u1);
			userbankrepo.save(ub);
			}
			else  {
				return new ResponseEntity(new com.Ipaisa.CustomExceptions.ApiResponse("Account Details is Present", false),HttpStatus.NOT_ACCEPTABLE);
					
		}
		
		
		
		 return ResponseEntity.ok("Beneficiary added successfully");
	}

	@Override
	public ResponseEntity<?> saveAddress(AddressDTO address, String userid) {
		
		  UsersAddress ua=usermapper.map(address, UsersAddress.class);
		
		User u=userRepo.findByUserid(userid);
		ua.setUser(u);
		addRepo.save(ua);
		 return ResponseEntity.ok("Address added successfully");
		
	}

	@Override
	public double getWalletBalance(String userid) {
		User u=userRepo.findByUserid(userid);
		Double walletBalance;
		if(u!=null) {
			walletBalance=u.getWalletBalance();
	
		}else {
			throw new RuntimeException("user not exist");
		}
		return walletBalance;
		
	}

	@Override
	public Boolean upUser(UpUser upUser, String mobileNo) {
        try {
        	
        	System.out.println("mobileNo====>"+mobileNo);
            User user = userRepo.findByMobileNumber(mobileNo);
            System.out.println("user====>"+mobileNo);
            if (user == null) {
                throw new ResourceNotFoundException("User not found with mobile number: " + mobileNo,"");
            }
            // Update user details
            user.setBussinessName(upUser.getBusinessName());
            user.setEmail(upUser.getEmail());
            user.setDob(LocalDate.parse(upUser.getDob()));
            user.setFirstName(upUser.getFirstName());
            user.setLastName(upUser.getLastName());
            user.setMiddleName(upUser.getMiddleName());
            // Update user address
            UsersAddress userAddress = addRepo.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User address not found for user ID: " + user.getUserid(),""));
            userAddress.setAddress(upUser.getAddress());
            addRepo.save(userAddress);
            userRepo.save(user);
            return true; // Return true indicating success
        } catch (ResourceNotFoundException e) {
            // Log the exception (you can use a logger instead of printStackTrace)
            e.printStackTrace();
            return false; // Return false indicating failure
        } catch (Exception e) {
            // Log the exception (you can use a logger instead of printStackTrace)
            e.printStackTrace();
            return false; // Return false indicating failure
        }
    }	
	
	///////////////////////////////////////////////////////////////////////////////////
	//////////////////////// New Updates in Transaction
	/////////////////////////////////
	
	
	@Override
	public ResponseEntity<?> TransactionDetailsNew(Transaction transaction, String user_loggedin_mobileno) {
	    try {
	      
	        User sender = userRepo.findByMobileNumber(user_loggedin_mobileno);
	        System.out.println("abhjbahjbsahjbsaj");
	        System.out.println("sender"+sender.getWalletBalance());
	        System.out.println("Sender Mobile -> "+sender.getMobileNumber());
	        User receiver = userRepo.findByMobileNumber(transaction.getReceiverId());
	        System.out.println("rcv Mobile -> "+receiver.getMobileNumber());
	     
	        System.out.println("Reci ----"+receiver.getParentId());
	        System.out.println("Send ----"+sender.getUserid());
	       
	      //  System.out.println(sender.getWalletBalance());
	       
	        Double amount = transaction.getAmount();
	        if (sender.getWalletBalance() < amount) {
	            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
	                    .body(new ApiResponse("Balance is not sufficient", false));
	        }
	        Double commission = receiver.getPercentage();
	        Double finalAmountdeduct=0.0;
	        Double finalAmountdeductcheck=0.0;
	        Double finalreceiveamount=0.0;
	        Double comm=0.0;
	        Double checkcomm=0.0;
	       
	       
	        if(sender.getRole().getUserrole().equals("ADMIN")&&receiver.getCategory().toString().equals("FIXED")&&!(receiver.getParentId().equals(sender.getUserid()))) {
	        	System.out.println("1");
	        	 Set<User> list=transactionService.getFinalAmount(receiver,sender);
	        	if(receiver.getComm_sur_type().equals("COMMISSION")) {
	        		
	        		 for (User user : list) {
	        			 checkcomm=checkcomm+(user.getPercentage()/100)*amount;
	        		 }
	        		 finalAmountdeductcheck=amount+checkcomm+((receiver.getPercentage()/100)*amount);
	        		
	        		  if (sender.getWalletBalance() > finalAmountdeductcheck) {
	
	   	        for (User user : list) {
	   	        	comm=comm+((user.getPercentage()/100)*amount);
	   	        	user.setWalletBalance(user.getWalletBalance()+ (user.getPercentage()/ 100) * amount);
	   	        	String mobile =user.getMobileNumber();
	   	        	System.out.println("user list====>"+user);
	   	        	WalletTransaction creditTransaction = new WalletTransaction(sender, user, comm, transaction.getRemark(), TransactionType.CREDIT);
	   	        	creditTransaction.setUtype(Utype.valueOf(user.getRole().getUserrole()));
	   	        	transactionRepo.save(creditTransaction);
	   			}
	        		  }else {
	        			  return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
	      	                    .body(new ApiResponse("Balance is not sufficient", false));
	        		  }
	   	  //   finalAmountdeduct=amount+comm+((receiver.getPercentage()/100)*100);
	   	     finalAmountdeduct=amount+comm+((receiver.getPercentage()/100)*amount);
	   	  finalreceiveamount=((receiver.getPercentage()/100)*100)+amount;
	        	}else {
	        	    finalAmountdeduct=amount-((receiver.getPercentage()/100)*amount);
	      	   	  finalreceiveamount=finalAmountdeduct;
	        		
	        	}
	        	}
	        
	        else if (sender.getRole().getUserrole().equals("ADMIN") && receiver.getCategory().toString().equals("FIXED")&&(receiver.getParentId().equals(sender.getUserid()))) {
	        	System.out.println("3");
	        	if(receiver.getComm_sur_type().equals("COMMISSION")) {
	        	finalAmountdeduct = amount + ((commission / 100) * amount);
	        	finalreceiveamount=finalAmountdeduct;
	        	}else {
	        		finalAmountdeduct = amount - ((commission / 100) * amount);
		        	finalreceiveamount=finalAmountdeduct;
	        	}
	        
	        }
	        
	      
	        else if (sender.getRole().getUserrole().equals("ADMIN")&& !receiver.getCategory().toString().equals("FIXED")) {
	        	
	        	System.out.println("2");
	        	finalAmountdeduct=finalreceiveamount = amount;
	        	
				}
  else if (!sender.getRole().getUserrole().equals("ADMIN")&& !receiver.getCategory().toString().equals("FIXED")) {
		System.out.println("2");
	  if (!receiver.getParentId().matches(sender.getUserid())) {
          return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                  .body(new ApiResponse("User is not under sender", false));
	  }
	       
	        	finalAmountdeduct=finalreceiveamount = amount;
	        	
				}
//	        	
	        else if (!sender.getRole().getUserrole().equals("ADMIN") && receiver.getCategory().toString().equals("FIXED")) {
	        	System.out.println("3");
		        if (!receiver.getParentId().matches(sender.getUserid())) {
	            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
	                    .body(new ApiResponse("User is not under sender", false));
	        }else {
	        	
	        	if(receiver.getComm_sur_type().equals("COMMISSION")) {
	        	finalAmountdeduct = amount + ((commission / 100) * amount);
	        	finalreceiveamount=finalAmountdeduct;
	        	}else {
	        		finalAmountdeduct = amount - ((commission / 100) * amount);
		        	finalreceiveamount=finalAmountdeduct;
	        	}
	        	
	        	
	        	
//	        	 WalletTransaction debitTransaction = new WalletTransaction(sender, receiver, finalAmountdeduct, transaction.getRemark(),TransactionType.DEBIT);
//	 	        
//	 	        debitTransaction.setUtype(Utype.valueOf(sender.getRole().getUserrole())); // Assuming UserType is an enum
//	 	        WalletTransaction creditTransaction = new WalletTransaction(sender, receiver, finalreceiveamount, transaction.getRemark(), TransactionType.CREDIT);
//	 	        creditTransaction.setUtype(Utype.valueOf(transaction.getReceiverType()));
	 	       
	        	
	        	
	        }
	        }
//	       
	        if (sender.getWalletBalance() < finalreceiveamount) {
	            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
	                    .body(new ApiResponse("Balance is not sufficient", false));
	        }
	   
	     
	        sender.setWalletBalance(sender.getWalletBalance() - finalAmountdeduct);
	        receiver.setWalletBalance(receiver.getWalletBalance() +finalreceiveamount );
	     
	        WalletTransaction debitTransaction = new WalletTransaction(sender, receiver, finalAmountdeduct, transaction.getRemark(),TransactionType.DEBIT);
	        
	        debitTransaction.setUtype(Utype.valueOf(sender.getRole().getUserrole())); // Assuming UserType is an enum
	        WalletTransaction creditTransaction = new WalletTransaction(sender, receiver, finalreceiveamount, transaction.getRemark(), TransactionType.CREDIT);
	        creditTransaction.setUtype(Utype.valueOf(transaction.getReceiverType()));
	        transactionRepo.save(debitTransaction);
	        transactionRepo.save(creditTransaction);
	        
	        userRepo.save(sender);
	        userRepo.save(receiver);
	        return ResponseEntity.status(HttpStatus.OK).body("Amount transfer successfully");
	    } catch (Exception e) {
	      
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse("Error processing transaction: " + e.getMessage(), false));
	    }
	}

//	@Override
//	public ResponseEntity<?> getAllBankDetails(String mobileNo) {
//		
//		User u=this.userRepo.findByMobileNumber(mobileNo);
//		if(u==null)
//		{
//			return new ResponseEntity(new com.Ipaisa.CustomExceptions.ApiResponse<>("User Not Found"+mobileNo, false),HttpStatus.NO_CONTENT);
//		}
//		
//		List<UserBankDetails> banks=this.userbankrepo.findByUser(u);
//		if(banks.isEmpty()|| banks==null)
//		{
//			
//			return new ResponseEntity(new com.Ipaisa.CustomExceptions.ApiResponse<>("Bank Details Not Found", false),HttpStatus.NO_CONTENT);
//		}
//		
//		
//		return ResponseEntity.ok(banks) ;
//	}
	
	@Override
	public ResponseEntity<?> getAllBankDetails(String mobileNo) {
	    User u = this.userRepo.findByMobileNumber(mobileNo);
	    if (u == null) {
	        return new ResponseEntity(new ApiResponse("User Not Found: " + mobileNo, false), HttpStatus.NO_CONTENT);
	    }

	    List<UserBankDetails> banks = this.userbankrepo.findByUser(u);
	    if (banks.isEmpty()) {
	        return new ResponseEntity(new ApiResponse("Bank Details Not Found", false), HttpStatus.NO_CONTENT);
	    }

	    // Convert UserBankDetails to UserBankDetailsDTO
	    List<UserBankDetailsDTO> bankDetailsDTOs = banks.stream()
	            .map(bank -> new UserBankDetailsDTO(
	                bank.getId(),
	                bank.getBankName(),
	                bank.getBranch(),
	                bank.getAccountNumber(),
	                bank.getIfsc(),
	                bank.getName(),
	                new UserDTO(
	                    bank.getUser().getUserid(),
	                    bank.getUser().getMobileNumber(),
	                    bank.getUser().getFirstName().concat(" "+bank.getUser().getMiddleName()+" "+bank.getUser().getLastName())
	                )
	            ))
	            .collect(Collectors.toList());
	   

	    return ResponseEntity.ok(bankDetailsDTOs);
	}
	
	
	
	public User getuser(String mobileNumber) {
		return userRepo.findByMobileNumber(mobileNumber);
	}

	
	
}
    




