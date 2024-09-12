package com.Ipaisa.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ipaisa.Entitys.UserRole;

public interface RollsRepositery extends JpaRepository<UserRole, Integer> {
	  UserRole findByUserrole(String userRole);

}
