package com.Ipaisa.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.Ipaisa.Entitys.TicketRaise;
import com.Ipaisa.Entitys.TicketRaiseBankDetails;

public interface TicketRaiseService {
	 ResponseEntity<?> createTicket(TicketRaiseBankDetails details,MultipartFile file,String userid);
	    List<TicketRaise> getAllTickets();
	    Integer changeParentOfPartners(String oldParent,String newParent);
	    Optional<TicketRaiseBankDetails> getTicketById(Long id);
	  //  TicketRaiseBankDetails updateTicket(Long id, TicketRaiseBankDetails updatedDetails);
	    void deleteTicket(Long id);
	    List<TicketRaise> getAllTicketsOfUser(String parentid);
	    
	   List<TicketRaise> getAllTicketsOfPartner(String mobileno);
	  // List<TicketRaise> partnerAssignedTicket(String mobileno);
	   
	 // List<TicketRaise> partnerAssignedTicket(String mobileno);

}
