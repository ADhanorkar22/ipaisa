package com.Ipaisa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ipaisa.DeepLink.DeeplinkPayin;

@Repository
public interface DeeplinkRepository extends JpaRepository<DeeplinkPayin, Long> {

	DeeplinkPayin findByTxnid(String txnid);
	List<DeeplinkPayin> findByPhone(String phone);

	
}
