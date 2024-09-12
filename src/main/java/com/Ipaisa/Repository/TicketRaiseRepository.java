package com.Ipaisa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Ipaisa.Entitys.TicketRaise;

import jakarta.transaction.Transactional;

@Repository
//@Modifying
@Transactional
public interface TicketRaiseRepository extends JpaRepository<TicketRaise, Long> {

	List<TicketRaise> findAllByParent(String parentid);
	
	 
	//Integer changeParentOfPartners(String oldParent, String newParent);

	//Integer (String oldParent, String newParent);

}




 




