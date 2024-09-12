package com.Ipaisa.UserController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Ipaisa.Service.UserRoleServices;
import com.Ipaisa.CustomExceptions.*;
import com.Ipaisa.CustomExceptions.ApiResponse;
import com.Ipaisa.*;
import com.Ipaisa.CustomExceptions.*;
import com.Ipaisa.Entitys.*;
@RestController
@RequestMapping("/ipaisa/roles/")
public class UserRoleControllers {
	@Autowired
	private UserRoleServices roleServ;
	@PostMapping("/")
	public ResponseEntity<UserRoleDto> createRole(@RequestBody UserRoleDto role) {
		com.Ipaisa.Entitys.UserRoleDto userrole = this.roleServ.addRole(role);
		return new ResponseEntity<UserRoleDto>(userrole, HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserRoleDto> getRoleById(@PathVariable Integer id) {
		UserRoleDto dto = this.roleServ.getRoleById(id);
		return ResponseEntity.ok(dto);
	}
	@GetMapping("/")
	public ResponseEntity<List<UserRoleDto>> getAllRole() {
		return ResponseEntity.ok(this.roleServ.getAllRoles());
	}
	@PutMapping("/{id}")
	public ResponseEntity<UserRoleDto> updateRole(@PathVariable Integer id, @RequestBody UserRoleDto urdto) {
		UserRoleDto userdto = this.roleServ.updateRole(urdto, id);
		return ResponseEntity.ok(userdto);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
		this.roleServ.deleteRole(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user Deleted Successfully", true), HttpStatus.OK);
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
	@PutMapping("/upcommission/{role}")
	public ResponseEntity<?> updateCommission(@PathVariable String role, @RequestBody UserRoleDto ud) {
		try {
			UserRoleDto u = roleServ.getByRole(role);
			if (u.getStatus().matches("Active")) {
				Double commission = ud.getCommission();
				UserRoleDto userRoleDto = roleServ.updateCommission(role, commission);
				return new ResponseEntity<>(userRoleDto, HttpStatus.OK);
			} else {
				ApiResponse errorResponse = new ApiResponse("Status is not Active", false);
				return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
			}
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/byrole/{role}")
	public ResponseEntity<UserRoleDto> getRoleByRole(@PathVariable String role) {
		UserRoleDto dto = this.roleServ.getByRole(role);
		return ResponseEntity.ok(dto);
	}
}