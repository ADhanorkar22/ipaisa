package com.Ipaisa.Service;

import java.util.HashSet;
import java.util.List;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.EasebuzzPayin;
import com.Ipaisa.Entitys.InstantPayOut;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Entitys.WalletTransaction;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.dto.WalletTransactionDTO;
import com.Ipaisa.Repository.EaseBuzzRepositery;
import com.Ipaisa.Repository.InstantPayoutRepository;
import com.Ipaisa.Repository.TransactionRepositery;



@Service
public class TransactionService {

	
	
	@Autowired
	private UserRepositery userRepo;
	
	@Autowired
	private TransactionRepositery trepo;
	
	@Autowired
	private EaseBuzzRepositery eseRepo;
	
	@Autowired
	private InstantPayoutRepository payOutRepo;
	
	
	
	
	public Set<User> getFinalAmount(User receiver,User sender) {   //   1  0
		  Set<User> list=null;
		  list=new HashSet<>();
 	        String parent_id= receiver.getParentId();
 	      
 	        while (parent_id != null) {
 	            User user = userRepo.findByUserid(parent_id);
// 	          System.out.println("uuuu---======"+user);
 	            if (user != null ) {
 	                list.add(user);
 	                parent_id = user.getParentId();
 	                if(userRepo.findByUserid(parent_id).getParentId()==null||userRepo.findByUserid(parent_id).getParentId().isEmpty()) {break;}
 	             
 	            } else {
 	                break;
 	            }
 	            System.out.println(parent_id);
 	        }
			
		
		
		return list;
	}
	
	
//	public ResponseEntity<?> getListOfWalletTrasDeb(User u) {
//	    String userId = u.getUserid();
//
//	    java.util.List<WalletTransaction> wtra = trepo.findByReceiverid(u);
//	    if (wtra == null || wtra.isEmpty()) {
//	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("No data found", false));
//	    } else {
//	        return ResponseEntity.ok(wtra);
//	    }
//	}
	
	  public List<WalletTransactionDTO> getTransactionsByReceiver(User sender) {
	        List<WalletTransaction> transactions = this.trepo.findBySenderid(sender);
	        return transactions.stream()
	            .filter(transaction -> "DEBIT".equals(transaction.getTransactionType().name()))
	            .map(transaction -> new WalletTransactionDTO(
	                transaction.getReceiverid().getFirstName() + " " + transaction.getReceiverid().getLastName(),
	                transaction.getReceiverid().getUserid(),
	                transaction.getTransactionType().name(),
	                transaction.getAmount(),
	                transaction.getDate(),
	                transaction.getRemark(),
	                transaction.getReceiverid().getMobileNumber(),
	                transaction.getTransactionId()
	            ))
	            .collect(Collectors.toList());
	    }
	
	  //////////////////////////////////
	  ///credit
	
	  
	  public List<WalletTransactionDTO> getTransactionsByReceiver1(User receiver) {
	        List<WalletTransaction> transactions = this.trepo.findByReceiverid(receiver);
	        return transactions.stream()
	            .filter(transaction -> "CREDIT".equals(transaction.getTransactionType().name()))
	            .map(transaction -> new WalletTransactionDTO(
	                transaction.getSenderid().getFirstName() + " " + transaction.getSenderid().getLastName(),
	                transaction.getSenderid().getUserid(),
	                transaction.getTransactionType().name(),
	                transaction.getAmount(),
	                transaction.getDate(),
	                transaction.getRemark(),
	                transaction.getSenderid().getMobileNumber(),
	                transaction.getTransactionId()
	            ))
	            .collect(Collectors.toList());
	    }
	  
	  
	  public ResponseEntity<?> getAllTxn(User u)
	  {
		  List<EasebuzzPayin> list=this.eseRepo.findByUser(u);
		  if(list.isEmpty())
			  return new ResponseEntity(new ApiResponse("No Content",  false),HttpStatus.NO_CONTENT );
					  
		  return ResponseEntity.ok(list) ;
	  }
	  
	  
	  public ResponseEntity<?> getAllTxnPayOut(User u)
	  {
		  List<InstantPayOut> list=this.payOutRepo.findByUser(u);
	
		  if(list.isEmpty())
			  return new ResponseEntity(new ApiResponse("No Content",  false),HttpStatus.NO_CONTENT );
					  
		  return ResponseEntity.ok(list) ;
	  }
	  
	  
}
