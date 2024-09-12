
package com.Ipaisa.UserController;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.Entitys.TicketRaise;
import com.Ipaisa.Entitys.TicketRaiseBankDetails;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Service.TicketRaiseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
@RequestMapping("/auth")
public class TicketRaiseController {
	@Autowired
	private TicketRaiseService service;
	@Autowired
	private UserDetailsService udeatils;
    @Autowired
    private JwtUtils utils;
    
    @Autowired
	private UserRepositery uRepo;
	
	@Autowired
	 private ObjectMapper mapper;
	@PostMapping("/create")
	public ResponseEntity<?> createTicket(@RequestParam MultipartFile bankReceipt, @RequestParam String detail,@RequestHeader("Authorization") String token){
		TicketRaiseBankDetails details=null;
		   String t=null;
		   System.out.println(token);
		   if (token.startsWith("Bearer ")) {
		        t = token.substring(7);
		        System.out.println(t);
		   }          
		   String username = utils.getUserNameFromJwtToken(t);
	        UserDetails userDetails = udeatils.loadUserByUsername(username);
	        String userid=userDetails.getUsername();
		try {
			details = mapper.readValue(detail, TicketRaiseBankDetails.class);
		} catch (JsonProcessingException e) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ivalid Request");
		}
		
		return new ResponseEntity<>(service.createTicket(details ,bankReceipt,userid),HttpStatus.CREATED);
	}
//	@GetMapping("/alltickets")
//	public List<TicketRaise> getAllTickets() {
//		return service.getAllTickets();
//	}
	@GetMapping("/{id}")
	public Optional<TicketRaiseBankDetails> getTicketById(@PathVariable Long id) {
		return service.getTicketById(id);
	}
//	@PutMapping("/{id}")
//	public TicketRaiseBankDetails updateTicket(@PathVariable Long id, @RequestBody TicketRaiseBankDetails updatedDetails) {
//		return service.updateTicket(id, updatedDetails);
//	}
	@DeleteMapping("/{id}")
	public void deleteTicket(@PathVariable Long id) {
		service.deleteTicket(id);
	}
	@GetMapping("/getAllTicketsOfpartner")
	public ResponseEntity<?> getAllTicketsOfPartner(@RequestHeader("Authorization") String token) {
		String t = null;
		System.out.println(token);
		if (token.startsWith("Bearer ")) {
			t = token.substring(7);
			System.out.println("TOKEN ---->>>>>>>> " +t);
		}
		String mobileno = utils.getUserNameFromJwtToken(t);
//		System.out.println("");
//		System.out.println("TOKEN User Name  ---->>>>>>>> " +mobileno);
		User u=uRepo.findByMobileNumber(mobileno);
//		System.out.println("User Details --->>> "+u);
//		UserDetails userDetails = udeatils.loadUserByUsername(u.getUserid());
//		System.out.println("User Details --->>> "+userDetails);
//		String userid = userDetails.getUsername();
//			 this.service.getAllTicketsOfPartner(userDetails.getUsername());
		
//		return ResponseEntity.ok(getAllTicketsOfPartner(userid));
//		System.out.println("--------Inside the get ALl Ticket of Partner ----------");
		System.out.println("\t\t User Detailsss --->>>>>>"+u.getUserid());
		return ResponseEntity.ok(this.service.getAllTicketsOfPartner(u.getUserid()));
	}
	
//	@GetMapping("/partnerassignedticket")
//	public ResponseEntity<?> partnerAssignedTicket(@RequestHeader("Authorization") String token) {
//		   String t=null;
//		   System.out.println(token);
//		   if (token.startsWith("Bearer ")) {
//		        t = token.substring(7);
//		        System.out.println(t); 
//		        }
//		   String username = utils.getUserNameFromJwtToken(t);
//	        UserDetails userDetails = udeatils.loadUserByUsername(username);
//	        String mobileno=userDetails.getUsername();
//	        
//		return   ResponseEntity.ok(service.partnerAssignedTicket(mobileno));
//	}
	
	
	
	@GetMapping("/getbyuser")
    public ResponseEntity<?> getTicketsByUserId(@RequestHeader("Authorization") String token) {
	 String t = null;
		System.out.println(token);
		if (token.startsWith("Bearer ")) {
			t = token.substring(7);
			System.out.println("TOKEN ---->>>>>>>> " +t);
		}
		String mobileno = utils.getUserNameFromJwtToken(t);
		User u=uRepo.findByMobileNumber(mobileno);
		 List<TicketRaise> tickets = service.getAllTicketsOfUser(u.getUserid());
		    if (tickets.isEmpty()) {
		        ApiResponse<Object> response = new ApiResponse<Object>( "No tickets found for user ID: "+ mobileno,false);
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		    } else {
		        return new ResponseEntity<>(tickets, HttpStatus.OK);
		    }
		   
    }
	
	
	
	
	
	

}