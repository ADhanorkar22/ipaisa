package com.Ipaisa.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.ReverseMoney;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Repository.RevMoneyRepo;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.dto.ReverseMoneyDto;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class RevMoneyServiceImpl implements RevMoneyService {
	@Autowired
	private UserRepositery userRepo;
	@Autowired
	private RevMoneyRepo revRepo;
//	@Override
//	public ResponseEntity<ApiResponse<?>> revMoney(String adminId, ReverseMoneyDto revDto) {
//		User admin = this.userRepo.findByUserid(adminId);
//		System.out.println("\t\t Admin  --->>>>> "+admin);
//		User user = this.userRepo.findByUserid(revDto.getReceiverId());
//		admin.setWalletBalance(admin.getWalletBalance() + revDto.getAmount());
//		user.setWalletBalance(user.getWalletBalance() - revDto.getAmount());
//
////	public ReverseMoney(String receiverId, String receiverName, String utype, Double amount, String remark) {
//
//		ReverseMoney rev = new ReverseMoney(revDto.getReceiverId(), revDto.getReceiverName(),admin.getUserid(), revDto.getUserType(),
//				revDto.getAmount(), revDto.getRemark());
//		userRepo.save(admin);
//		userRepo.save(user);
//
//		revRepo.save(rev);
//		return new ResponseEntity<>(new ApiResponse<>(true, "updated"),HttpStatus.OK);
//	}
//
//}
	
	@Override
	public boolean revMoney(String adminId, ReverseMoneyDto revDto) {
	    try {
	        User admin = this.userRepo.findByUserid(adminId);
	        System.out.println("\t\t Admin  --->>>>> " + admin);
	        User user = this.userRepo.findByUserid(revDto.getReceiverId());
	       
	       
	        // Adjust wallet balances
	        if(user.getWalletBalance()>0 && revDto.getAmount()<user.getWalletBalance())
	        {
	        admin.setWalletBalance(admin.getWalletBalance() + revDto.getAmount());
	        user.setWalletBalance(user.getWalletBalance() - revDto.getAmount());
	       
	        // Create ReverseMoney entity
	        ReverseMoney rev = new ReverseMoney(revDto.getReceiverId(), revDto.getReceiverName(), admin.getUserid(), revDto.getUserType(),
	                                            revDto.getAmount(), revDto.getRemark());
	       
	        // Save changes to database
	        userRepo.save(admin);
	        userRepo.save(user);
	        revRepo.save(rev);
	        return true;  // Indicate the transaction was successful
	        }
	        else
	        {
	        	return false;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;  // Indicate the transaction failed
	    }
	
	}
	
}









