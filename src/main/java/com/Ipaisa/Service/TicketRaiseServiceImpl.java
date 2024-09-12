package com.Ipaisa.Service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Ipaisa.CustomExceptions.ResourceNotFoundException;
import com.Ipaisa.Entitys.TicketRaise;
import com.Ipaisa.Entitys.TicketRaiseBankDetails;
import com.Ipaisa.Entitys.TicketSettelement;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Repository.TicketRaiseRepository;
import com.Ipaisa.Repository.TicketSettelmentRepositery;
import com.Ipaisa.Repository.UserRepositery;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TicketRaiseServiceImpl implements TicketRaiseService {

	@Autowired
	private TicketRaiseRepository repository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepositery userRepo;
	
	@Autowired
	private IStorageService istorageservice;
	
	@Autowired
	private TicketSettelmentRepositery settelmentrepo;
	
	
	@Override
	public ResponseEntity<?> createTicket(TicketRaiseBankDetails dt ,MultipartFile file,String userid) {
		
		
		System.out.println(dt);
		//User userobj=userRepo.findByUserid(dt.getUser());
		User ticketuser=userRepo.findByMobileNumber(userid);   // user who raised ticket
		String parentid=ticketuser.getParentId();  //    parent id of user who raised ticket
		User parentobj=userRepo.findByUserid(parentid);  //parent entity
					System.out.println("parent");
					System.out.println(parentobj);
		//String userid=userobj.getUserid();
		
		String sendername=ticketuser.getFirstName()+" "+ticketuser.getMiddleName()+" "+ticketuser.getLastName(); 
		String parentname=parentobj.getFirstName()+" "+parentobj.getMiddleName()+" "+parentobj.getLastName();
		
	//	System.out.println(userobj);
		//System.out.println(parentobj);
		//System.out.println(dt.getUser())
			String type=dt.getIssueType();
		  
		if(dt.getIssueType().equals(type)) {
			
			TicketRaise tr=new TicketRaise(dt.getRemark(),dt.getIssueType(),dt.getSubject(),dt.getBankAccount(),dt.getPaymentMode(),dt.getUtr_ref_Number(), ticketuser,parentid, dt.getAmount(),sendername,parentname); 
			repository.save(tr);
			TicketRaise traise= istorageservice.uploadFile(file,tr);
			System.out.println("traise");
			System.out.println(traise);

			TicketSettelement ticketsettelement= new TicketSettelement(userid,sendername,parentid,parentname,dt.getAmount(),dt.getRemark(),traise);
			
			repository.save(traise);
			settelmentrepo.save(ticketsettelement);
		}else {
			TicketRaise tr=new TicketRaise(dt.getRemark(),dt.getIssueType(),dt.getSubject(), ticketuser,parentid); 
			
		}
		//public TicketRaise(String remark, String issueType, String subject,
		//User user, String parent) {
		
		
		return ResponseEntity.ok("Ticket Rised Successfully");
		
	}
		
		@Override
		public void deleteTicket(Long id) {
			repository.deleteById(id);
		}
		private TicketRaiseBankDetails convertToDto(TicketRaise ticket) {
			return modelMapper.map(ticket, TicketRaiseBankDetails.class);
		}
		private TicketRaise convertToEntity(TicketRaiseBankDetails details) {
			return modelMapper.map(details, TicketRaise.class);
		}
		@Override
		public List<TicketRaise> getAllTickets() {
			 return repository.findAll().stream().collect(Collectors.toList());
		}
		
		
		@Override
		public Optional<TicketRaiseBankDetails> getTicketById(Long id) {
			return repository.findById(id).map(this::convertToDto);
		}
		@Override
		public  List<TicketRaise> getAllTicketsOfPartner(String parentid) {
			List<TicketRaise> parenttickets;
			
				parenttickets = repository.findAllByParent(parentid);
			
				if(parenttickets !=null ) {
					return parenttickets;
				}
//				else	
//				new ResourceNotFoundException(parentid, "Tickets Not Found");
				//RuntimeException("no tickets for user");
				return (List<TicketRaise>) new com.Ipaisa.Entitys.ApiResponse("Not Found", false);
		
		}
		
		public List<TicketRaise> getAllTicketsOfUser(String userId) {
//			try {
				List<TicketRaise> tickets = this.repository.findAll().stream()
						.filter(e -> e.getUser().getUserid().equals(userId)).collect(Collectors.toList());
//				if (tickets.isEmpty()) {
////					return (List<TicketRaise>) new ResourceNotFoundException("TicketRaise", "userId", userId);
////					ApiResponse<T> response=new ApiResponse<>(false, userId);
////					throw new EntityNotFoundException("Not Found");
//				}
				return tickets;
//			} catch (Exception e) {
//				// Log the exception
//				System.err.println("Error fetching tickets for user ID " + userId + ": " + e.getMessage());
//				// Optionally, you could rethrow a custom exception
//				throw new ResourceNotFoundException("Error fetching tickets for user ID", userId, e.getMessage());
//			}
		}

		@Override
		public Integer changeParentOfPartners(String oldParent, String newParent) {
		return userRepo.changeParentOfPartners(oldParent,newParent);
		
		
			
		}
		
		
		
		
		
		
		

//		@Override
//		public List<TicketRaise> partnerAssignedTicket(String mobileno) {
//			String  u=userRepo.findByMobileNumber(mobileno).getUserid();
//			List<TicketRaise> parenttickets;
//			try {
//		 parenttickets=repository.findAllByParent(u);
//		}catch (Exception e) {
//			throw new RuntimeException("no tickets for user");
//		}
//			 return parenttickets;
//		}	
//		
		
	
		
		
		
/////////////////////////////////////////////////////////////////////////////////////////////		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		TicketRaise ticket = convertToEntity(details);
//		
////		 System.out.println(details.getUser().getUser_Id());
//		String uId=details.getUser().getUser_Id();
//		User userid=this.userRepo.findByUserid(uId);
//		
//		 TicketRaise fd=new TicketRaise();
//		//String parentId=details.getUser().getParentId();
//		//fd.setParent(parentId.getParentId());
//		// ticket.setParent(parentId.getParentId().valueOf(parentId));
//		 
//		ticket.setUser(userid);
//		 ticket.setParent(userid.getParentId());
//		TicketRaise savedTicket = repository.save(ticket);
//		return convertToDto(savedTicket);
	}
//	@Override
//	public List<FundsRequestDetails> getAllTickets() {
//		return repository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
//	}
	
//	@Override
//	public TicketRaiseBankDetails updateTicket(Long id, TicketRaiseBankDetails updatedDetails) {
//		return repository.findById(id).map(ticket -> {
//			ticket.setRemark(updatedDetails.getRemark());
//			ticket.setIssueType(updatedDetails.getIssueType());
//			ticket.setSubject(updatedDetails.getSubject());
//			ticket.setBankAccount(updatedDetails.getBankAccount());
//			ticket.setPaymentMode(updatedDetails.getPaymentMode());
//			ticket.setUtr_ref_Number(updatedDetails.getUtr_ref_Number());
//			//ticket.setReceiptPath(updatedDetails.getReceiptPath());
//			//ticket.setStatus(updatedDetails.getStatus());
//		//	ticket.setUser(updatedDetails.getUser());
////			ticket.setParent(updatedDetails.getParent());
//			TicketRaise updatedTicket = repository.save(ticket);
//			return convertToDto(updatedTicket);
//		}).orElseGet(() -> {
//			TicketRaise newTicket = convertToEntity(updatedDetails);
//			newTicket.setId(id);
//			TicketRaise savedTicket = repository.save(newTicket);
//			return convertToDto(savedTicket);
//		});
//	}

	
	
	


