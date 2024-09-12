package com.Ipaisa.Service;

import java.util.List;

import com.Ipaisa.dto.RusDto;
import com.Ipaisa.dto.UpUser;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.Ipaisa.Entitys.AddressDTO;
import com.Ipaisa.Entitys.BankDTO;
import com.Ipaisa.Entitys.Transaction;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Entitys.UsersDetail;
import com.Ipaisa.Entitys.WalletTransaction;

public interface IUserDao {
	ResponseEntity<?> saveUser(UsersDetail ud,String mobileno);

	User getDetailsById(String id);

	ResponseEntity<?> changeStatus(String id);

	List<User> listAllUsers();

	ResponseEntity<?> updateUsersDetails(UsersDetail user);

	long GetCount();

	RusDto reciverUnderSenderInfo(String userid, String mobileno);

	ResponseEntity<?> TransactionDetails(Transaction trans,String userid);

	ResponseEntity<?> saveBeneficiary(BankDTO bank,String userid);
	ResponseEntity<?> saveAddress(AddressDTO address,String userid);
	double getWalletBalance(String userid);
	Boolean isUserPresent(String mno);
	
	Boolean upUser(UpUser user,String mobileNo);
	public User getuser(String mobileNumber);
	

	ResponseEntity<?> TransactionDetailsNew(Transaction trans,String userid);
	ResponseEntity<?> getAllBankDetails(String mobileNo);
}
