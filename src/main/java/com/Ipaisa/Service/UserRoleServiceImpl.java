package com.Ipaisa.Service;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ipaisa.CustomExceptions.ResourceNotFoundException;
import com.Ipaisa.Entitys.*;
import com.Ipaisa.Repository.RollsRepositery;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleServices {
	@Autowired
	private RollsRepositery roleRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public UserRoleDto addRole(UserRoleDto userroledto) {
		UserRole user = this.modelMapper.map(userroledto, UserRole.class);
		UserRole saveuser = this.roleRepo.save(user);
		return this.modelMapper.map(saveuser, UserRoleDto.class);
	}
	@Override
	public UserRoleDto updateRole(UserRoleDto userrole, Integer id) {
		UserRole userRole = this.roleRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UserRole", "User id", id));
		userRole.setUserRole(userrole.getUserRole());
	
		UserRole updatedRole = this.roleRepo.save(userRole);
		return this.modelMapper.map(updatedRole, UserRoleDto.class);
	}
	@Override
	public void deleteRole(Integer id) {
		UserRole userRole = this.roleRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UserRole", "User id", id));
		this.roleRepo.delete(userRole);
	}
	public List<UserRoleDto> getAllRoles() {
		List<UserRole> userRoles = roleRepo.findAll();
		List<UserRoleDto> userRoleDtos = new ArrayList<>();
		for (UserRole userRole : userRoles) {
			userRoleDtos.add(userToDto(userRole));
		}
		return userRoleDtos;
	}
	@Override
	public UserRoleDto getRoleById(Integer id) {
		UserRole userRole = this.roleRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UserRole", "id", id));
		return this.userToDto(userRole);
	}
	private UserRole dtoToUser(UserRoleDto userdto) {
		UserRole userRole = this.modelMapper.map(userdto, UserRole.class);
		return userRole;
	}
	private UserRoleDto userToDto(UserRole user) {
		UserRoleDto dto = this.modelMapper.map(user, UserRoleDto.class);
		return dto;
	}
	@Override
	public UserRoleDto getByRole(String role) {
		UserRole userRole1 = this.roleRepo.findByUserrole(role);
		return this.userToDto(userRole1);
	}
	@Override
	public UserRoleDto getCommissionByRole(String role) {
		UserRole userRole = roleRepo.findByUserrole(role);
		if (userRole != null) {
			return userToDto(userRole);
		} else {
			throw new EntityNotFoundException("Not Found");
		}
	}
	public UserRoleDto updateCommission(String role, Double commission) {
		UserRole userRole = roleRepo.findByUserrole(role);
		if (userRole != null) {
			userRole.setCommission(commission);
			UserRole updatedUserRole = roleRepo.save(userRole);
			return userToDto(updatedUserRole);
		} else {
			throw new EntityNotFoundException("UserRole with role " + role + " not found");
		}
	}
	}




































//	public UserRoleDto userToDto2(UserRole userRole) {
//	    // Conversion logic here
//	    return new UserRoleDto(userRole.getId(), userRole.getUserRole(), userRole.getCommission());
//	}
//		@Override
//		public UserRoleDto updateCommission(Integer id, Double comission) {
//			 Optional<UserRole> optionalUserRole = roleRepo.findById(id);
//			    if (optionalUserRole.isPresent()) {
//			        UserRole userRole = optionalUserRole.get();
//			        userRole.setCommission(comission);
//			        UserRole updatedUserRole = roleRepo.save(userRole);
//			        return userToDto(updatedUserRole);
//			    } else {
//			      
//			        throw new ResourceNotFoundException("UserRole", "id", id);
//			    }
//		}
//		@Override
//		public List<UserRoleDto> getAllRoles() {
//			List<UserRole> userRole = this.roleRepo.findAll();
//			List<UserRoleDto> usersDto = userRole.stream().map((user -> this.userToDto(user))).collect(Collectors.toList());
	//
//			return usersDto;
//		}