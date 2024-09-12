package com.Ipaisa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ipaisa.Entitys.Providers;
import com.Ipaisa.Entitys.Services;
import com.Ipaisa.Entitys.ServicesByProvider;

import jakarta.transaction.Transactional;

@Transactional
public interface SetChargesRepositery extends JpaRepository<ServicesByProvider, Long> {
	List<ServicesByProvider> findByProvider(Providers p); 
	
	public ServicesByProvider findByProviderAndService(Providers p,Services s);
	

}
