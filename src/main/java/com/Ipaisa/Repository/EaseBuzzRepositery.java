package com.Ipaisa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ipaisa.Entitys.EasebuzzPayin;
import com.Ipaisa.Entitys.User;

import java.util.List;


@Repository
 public interface EaseBuzzRepositery extends JpaRepository<EasebuzzPayin, Long> {

	EasebuzzPayin findByTxnid(String txnid);
	List<EasebuzzPayin> findByUser(User user);
	
}
