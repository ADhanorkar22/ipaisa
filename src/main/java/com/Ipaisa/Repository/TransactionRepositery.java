package com.Ipaisa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ipaisa.Entitys.User;
import com.Ipaisa.Entitys.WalletTransaction;


public interface TransactionRepositery extends JpaRepository<WalletTransaction, Integer> {
	
	List<WalletTransaction> findByReceiverid(User receiverid);
	List<WalletTransaction> findBySenderid(User senderid);

}
