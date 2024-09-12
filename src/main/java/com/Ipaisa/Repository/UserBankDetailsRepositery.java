package com.Ipaisa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ipaisa.Entitys.User;
import com.Ipaisa.Entitys.UserBankDetails;
import java.util.List;


public interface UserBankDetailsRepositery extends JpaRepository<UserBankDetails, String> {
	
	List<UserBankDetails> findByUser(User user);
	UserBankDetails findByAccountNumber(Long accountNumber);

}
