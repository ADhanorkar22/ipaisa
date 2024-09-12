package com.Ipaisa.Service;

import org.springframework.http.ResponseEntity;

import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.SetCharges;

import jakarta.transaction.Transactional;

@Transactional
public interface SetChargesInterface {
	
	public ApiResponse setCharges(SetCharges setcharges);
	public Double getFinalCharge(Double amount,String transferMode);
	public ApiResponse getProviderList();

}
