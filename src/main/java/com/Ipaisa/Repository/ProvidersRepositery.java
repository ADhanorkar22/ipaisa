package com.Ipaisa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ipaisa.Entitys.Providers;

import jakarta.transaction.Transactional;


@Transactional
public interface ProvidersRepositery extends JpaRepository<Providers, Long> {
	Providers findByProviderName(String providerName);

}
