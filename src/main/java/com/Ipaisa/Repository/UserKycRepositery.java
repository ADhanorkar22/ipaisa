package com.Ipaisa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ipaisa.Entitys.UserKycDetail;

public interface UserKycRepositery extends JpaRepository<UserKycDetail, String> {

	UserKycDetail findByUid(String id);
}
