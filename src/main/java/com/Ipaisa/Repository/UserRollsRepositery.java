package com.Ipaisa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Ipaisa.Entitys.UserRole;

public interface UserRollsRepositery extends JpaRepository<UserRole, Integer> {
		  UserRole findByUserrole(String userRole);
		//  Integer countByRoleId(Integer id);
		 

	}


