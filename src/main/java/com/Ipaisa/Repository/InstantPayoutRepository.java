package com.Ipaisa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ipaisa.Entitys.InstantPayOut;
import com.Ipaisa.Entitys.User;

import jakarta.transaction.Transactional;
import java.util.List;


@Transactional
@Repository
public interface InstantPayoutRepository extends JpaRepository<InstantPayOut, Long> {
	
	List<InstantPayOut> findByUser(User user);

}
