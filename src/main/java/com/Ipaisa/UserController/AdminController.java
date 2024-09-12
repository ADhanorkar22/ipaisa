package com.Ipaisa.UserController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.CustomExceptions.ResourceNotFoundException;
import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.Providers;
import com.Ipaisa.Entitys.TicketRaise;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Entitys.UserRoleDto;
import com.Ipaisa.Entitys.UsersDetail;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Service.IUserDao;
import com.Ipaisa.Service.ProviderService;
import com.Ipaisa.Service.RevMoneyService;
import com.Ipaisa.Service.TicketRaiseService;
import com.Ipaisa.Service.UserRoleServices;
import com.Ipaisa.dto.ReverseMoneyDto;
import com.Ipaisa.dto.RoleCountDto;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private TicketRaiseService service;

	@Autowired
	private UserDetailsService udeatils;

	@Autowired
	private UserRoleServices roleServ;

	@Autowired
	private IUserDao userdao;

	@Autowired
	private UserRepositery userRepo;

	@Autowired
	private JwtUtils utils;
	
	
	
	@Autowired
	private RevMoneyService revMoneyService;

	
	@PutMapping("/changeStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable String id, @RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : null;
            if (jwtToken == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token format.");
            }
            String username = utils.getUserNameFromJwtToken(jwtToken);
            UserDetails userDetails = udeatils.loadUserByUsername(username);
            String userid = userDetails.getUsername();
            return userdao.changeStatus(id);
        } catch (Exception e) {
        	
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to change status: " + e.getMessage());
        }
    }
	
	

	@PostMapping("/register")
	public ResponseEntity<?> saveUsers(@RequestBody UsersDetail user, @RequestHeader("Authorization") String token) {
		String t = null;
		System.out.println(token);

		if (token.startsWith("Bearer ")) {
			t = token.substring(7);
			System.out.println(t);
		}

		String username = utils.getUserNameFromJwtToken(t);
		UserDetails userDetails = udeatils.loadUserByUsername(username);
		String mobileno = userDetails.getUsername();
		// User u = uRepo.findByMobileNumber(userid);

		return new ResponseEntity<>(userdao.saveUser(user, mobileno), HttpStatus.CREATED);
	}

	@GetMapping("/list")
	public ResponseEntity<?> listAllUsers(@RequestHeader("Authorization") String token) {
		List<User> list = userdao.listAllUsers();
		if (list.isEmpty())
			return new ResponseEntity<>("Empty Emp List!!", HttpStatus.OK);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

//		    @GetMapping("/alltickets")
//			public ResponseEntity<List<TicketRaise>> getAllTickets(@RequestHeader("Authorization") String token) {
//				List<TicketRaise> tickets = service.getAllTickets();
//				return ResponseEntity.ok(tickets);
//			}

//		    @GetMapping("/getAllCount")
//			public ResponseEntity<RoleCountDto> getAllCountByRole(@RequestHeader("Authorization") String token) {
//			System.out.println("Inside Get Count>>>>>>>>>>>>");
//				RoleCountDto roleCountDto = new RoleCountDto();
//				UserRoleDto userRole = this.roleServ.getByRole("CHANNALPARTNER");
//				System.out.println(userRole);
//				Integer cid = Integer.parseInt(userRole.getId());
//				UserRoleDto userRole1 = this.roleServ.getByRole("SUPERDESTRIBUTER");
//				System.out.println(userRole1);
//				Integer mid = Integer.parseInt(userRole1.getId());
//				UserRoleDto userRole2 = this.roleServ.getByRole("MASTERDESTRIBUTER");
//				System.out.println(userRole2);
//				Integer sid = Integer.parseInt(userRole2.getId());
//				UserRoleDto userRole3 = this.roleServ.getByRole("DESRIBUTER");
//				Integer aid = Integer.parseInt(userRole3.getId());
//				UserRoleDto userRole4 = this.roleServ.getByRole("RETAILER");
//				Integer Rid = Integer.parseInt(userRole4.getId());
//
//				roleCountDto.setSuperDistributer(userRepo.countByRoleId(sid));
//				System.out.println(userRepo.countByRoleId(cid));
//				roleCountDto.setMasterDistributer(userRepo.countByRoleId(mid));
//				System.out.println(userRepo.countByRoleId(mid));
//				roleCountDto.setAreaDistributer(userRepo.countByRoleId(aid));
//				System.out.println(userRepo.countByRoleId(aid));
//				roleCountDto.setRetailer(userRepo.countByRoleId(Rid));
//				System.out.println(userRepo.countByRoleId(Rid));
//				return ResponseEntity.ok(roleCountDto);
//			}

	@GetMapping("/getAllCount")
	public ResponseEntity<RoleCountDto> getAllCountByRole(@RequestHeader("Authorization") String token) {
		System.out.println("Inside Get Count>>>>>>>>>>>>");
		RoleCountDto roleCountDto = new RoleCountDto();
		try {
			UserRoleDto userRole = this.roleServ.getByRole("CHANNELPARTNER");
			System.out.println(userRole);
			Integer cid = Integer.parseInt(userRole.getId());
			roleCountDto.setChannelPartner(userRepo.countByRoleId(cid));
		} catch (Exception e) {
			System.out.println("CHANNALPARTNER role not found");
			roleCountDto.setChannelPartner(0);
		}
		try {
			UserRoleDto userRole1 = this.roleServ.getByRole("SUPERDISTRIBUTOR");
			System.out.println(userRole1);
			Integer mid = Integer.parseInt(userRole1.getId());
			roleCountDto.setSuperDistributer(userRepo.countByRoleId(mid));
		} catch (Exception e) {
			System.out.println("SUPERDESTRIBUTER role not found");
			roleCountDto.setSuperDistributer(0);
		}
		try {
			UserRoleDto userRole2 = this.roleServ.getByRole("MASTERDISTRIBUTOR");
			System.out.println(userRole2);
			Integer sid = Integer.parseInt(userRole2.getId());
			roleCountDto.setMasterDistributer(userRepo.countByRoleId(sid));
		} catch (Exception e) {
			System.out.println("MASTERDESTRIBUTER role not found");
			roleCountDto.setMasterDistributer(0);
		}
		try {
			UserRoleDto userRole3 = this.roleServ.getByRole("AREADISTRIBUTOR");
			System.out.println(userRole3);
			Integer aid = Integer.parseInt(userRole3.getId());
			roleCountDto.setAreaDistributer(userRepo.countByRoleId(aid));
		} catch (Exception e) {
			System.out.println("DESRIBUTER role not found");
			roleCountDto.setAreaDistributer(0);
		}
		try {
			UserRoleDto userRole4 = this.roleServ.getByRole("RETAILER");
			System.out.println(userRole4);
			Integer rid = Integer.parseInt(userRole4.getId());
			roleCountDto.setRetailer(userRepo.countByRoleId(rid));
		} catch (Exception e) {
			System.out.println("RETAILER role not found");
			roleCountDto.setRetailer(0);
		}
		return ResponseEntity.ok(roleCountDto);
	}

	@PutMapping("/addcommission")
	public ResponseEntity<?> updateCommission(@RequestBody UserRoleDto ud,
			@RequestHeader("Authorization") String token) {
		try {
			UserRoleDto u = roleServ.getByRole(ud.getUserRole());
			Double commission = ud.getCommission();
			UserRoleDto userRoleDto = roleServ.updateCommission(u.getUserRole(), commission);
			return new ResponseEntity<>(userRoleDto, HttpStatus.OK);
//					} else {
//						ApiResponse errorResponse = new ApiResponse("Status is not Active", false);
//						return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAllTickets")
	public ResponseEntity<?> getAllTicketsOfPartner(@RequestHeader("Authorization") String token) {
		String t = null;

		System.out.println(token);
		if (token.startsWith("Bearer ")) {
			t = token.substring(7);
//					System.out.println(t);
		}
		String username = utils.getUserNameFromJwtToken(t);
		UserDetails userDetails = udeatils.loadUserByUsername(username);
		String userid = userDetails.getUsername();
		// return ResponseEntity.ok(getAllTicketsOfPartner(userid));
		return ResponseEntity.ok(this.service.getAllTickets());
	}

	@GetMapping("/commission/{role}")
	public ResponseEntity<UserRoleDto> getCommissionByRole(@PathVariable String role) {
		try {
			UserRoleDto userRoleDto = roleServ.getCommissionByRole(role);
			return new ResponseEntity<>(userRoleDto, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/changeParentOfPartners")
	public ResponseEntity<?> changeParentOfPartners(@RequestHeader("Authorization") String token,
			@RequestBody Map<String, String> obj) {
		System.out.println("changeParentOfPartners");
		System.out.println(obj.get("oldParentId") + " " + obj.get("newParentId"));
		Integer updatedCount = service.changeParentOfPartners(obj.get("oldParentId"), obj.get("newParentId"));
		System.out.println(obj.size());
		obj.remove("oldParentId");
		obj.remove("newParentId");
		System.out.println(obj.size());
		return new ResponseEntity<>(
				new ApiResponse("success", "this many of Users Parentid changed " + updatedCount, true), HttpStatus.OK);

	}

	@GetMapping("/isUserPresent")
	public ResponseEntity<?> userIsPresent(@RequestHeader("Authorization") String token,
			@RequestBody String mobileNumber) {
		int start = mobileNumber.indexOf(":\"") + 2;
		int end = mobileNumber.indexOf("\"", start);
		String mno = mobileNumber.substring(start, end);
		Boolean u = userdao.isUserPresent(mno);
		if (u) {
			return ResponseEntity.ok(new ApiResponse("User is Present", "true"));
		}
		return ResponseEntity.ok(new ApiResponse("User is Not Present", "false"));
	}

	@PostMapping("/reversebalance")
	public ResponseEntity<?> reverseMoney(@RequestHeader("Authorization") String token,
			@RequestBody ReverseMoneyDto revMoney) {
		String t = null;
		System.out.println(token);
		if (token.startsWith("Bearer ")) {
			t = token.substring(7);
			System.out.println(t);
		}
		String mobileno = utils.getUserNameFromJwtToken(t);
		User admin = this.userRepo.findByMobileNumber(mobileno);

		try {
			boolean isSuccess = this.revMoneyService.revMoney(admin.getUserid(), revMoney);
			if (isSuccess) {
				return new ResponseEntity<>(new com.Ipaisa.CustomExceptions.ApiResponse<Object>(
						"Transaction completed successfully", isSuccess), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
						new com.Ipaisa.CustomExceptions.ApiResponse<Object>("Transaction failed", isSuccess),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Transaction failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}



	
	
	

	
	



