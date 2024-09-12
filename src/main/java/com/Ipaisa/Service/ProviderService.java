package com.Ipaisa.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Ipaisa.Entitys.Providers;
import com.Ipaisa.Repository.ProvidersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProviderService {
	
	@Autowired
	private ProvidersRepository  providersrepo;

	public Providers addProviders(Providers provider) {
	
		return providersrepo.save(provider);
	}
	
	
	
	
	

}
