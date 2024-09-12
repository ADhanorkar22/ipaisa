package com.Ipaisa.Service;

import java.util.List;
import com.Ipaisa.Entitys.UserRole;
import com.Ipaisa.Entitys.UserRoleDto;

//import com.Ipaisa.dto.UserRoleDto;
public interface UserRoleServices {
	UserRoleDto addRole(UserRoleDto userrole);

	UserRoleDto updateRole(UserRoleDto userrole, Integer id);

	void deleteRole(Integer id);

	List<UserRoleDto> getAllRoles();

	UserRoleDto getRoleById(Integer id);

	// UserRoleDto getByRole(Integer role);
	UserRoleDto getByRole(String role);

	UserRoleDto updateCommission(String role, Double comission);

	UserRoleDto getCommissionByRole(String role);
}