package com.Ipaisa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ipaisa.Entitys.UsersAddress;

public interface AddressRepositery extends JpaRepository<UsersAddress, String> {

}
