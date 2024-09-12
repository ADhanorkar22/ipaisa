package com.Ipaisa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ipaisa.Entitys.Services;

import jakarta.transaction.Transactional;

@Transactional
public interface ServicesRepositery extends JpaRepository<Services, Long> {

	  Services findByTransactionTypeAndTransactionSlab(String transactionType, String transactionSlab);
	  
	}