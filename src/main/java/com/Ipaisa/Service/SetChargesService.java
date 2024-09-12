package com.Ipaisa.Service;

import java.util.List;

import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.Providers;
import com.Ipaisa.Entitys.Services;
import com.Ipaisa.Entitys.ServicesByProvider;
import com.Ipaisa.Entitys.SetCharges;
import com.Ipaisa.Repository.ProvidersRepositery;
import com.Ipaisa.Repository.ServicesRepositery;
import com.Ipaisa.Repository.SetChargesRepositery;
import org.springframework.http.HttpStatus;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SetChargesService implements SetChargesInterface{
	
	@Autowired 
	private SetChargesRepositery setChargesRepositery;
	
	@Autowired
	private ProvidersRepositery providersRepositery;
	
	@Autowired
	private ServicesRepositery servicesRepositery;
	
	
	

	@Override
	public ApiResponse setCharges(SetCharges setCharges) {
	   
	    if (setCharges == null || setCharges.getProvider() == null || 
	        setCharges.getTransactionType() == null || 
	        setCharges.getTransactionSlab() == null || 
	        setCharges.getAmount() == null) {
	    	return new ApiResponse(HttpStatus.BAD_REQUEST, "Invalid input: All fields are required");
	    	

	    }

	   
	    Providers provider = providersRepositery.findByProviderName(setCharges.getProvider());
	    if (provider == null) {
	    	return new ApiResponse(HttpStatus.NOT_FOUND, "Provider not found");
	       
	    }

	    
	    Services service = servicesRepositery.findByTransactionTypeAndTransactionSlab(
	            setCharges.getTransactionType(), 
	            setCharges.getTransactionSlab()
	    );
	    if (service == null) {
	    	return new ApiResponse(HttpStatus.NOT_FOUND, "Service not found");
		       
	       
	    }
	    System.out.println(provider);
	    System.out.println(service);

	   
	    ServicesByProvider servicesByProvider =setChargesRepositery.findByProviderAndService(provider, service);
	    servicesByProvider.setBankChargesl(Double.valueOf(setCharges.getAmount()));
	    
	    try {
	    	setChargesRepositery.save(servicesByProvider);
	    } catch (Exception e) {
	       
	    	return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving charges: " + e.getMessage());
	    }
	    return new ApiResponse(HttpStatus.OK,"Charges set successfully");
	}

	
	public ResponseEntity<?> getproviders(String providerName){
		 Providers provider = providersRepositery.findByProviderName(providerName);
	        if (provider == null) {
	         
	            return new ResponseEntity<>("Provider not found", HttpStatus.NOT_FOUND);
	            
	           
	        }
	        List<ServicesByProvider> providerObject=setChargesRepositery.findByProvider(provider);
	        
	        if (providerObject.isEmpty()) {
	          
	            return new ResponseEntity<>("No provider available", HttpStatus.NO_CONTENT);
	        }
	        
	return new ResponseEntity<>(providerObject, HttpStatus.OK);
	}

	
	public Double getcharges(String providername,String transfertype,String transferslab) {
		Providers p=providersRepositery.findByProviderName(providername);
		System.out.println("provider=======>"+p);
		Services s=servicesRepositery.findByTransactionTypeAndTransactionSlab(transfertype, transferslab);
		System.out.println("service======>"+s);
		ServicesByProvider sp=setChargesRepositery.findByProviderAndService(p, s);
		System.out.println("ServicesByProvider============>"+sp);
		
		return sp.getBankChargesl();
		
	}
	
	
	
	@Override
	public Double getFinalCharge(Double amount,String transfermode) {
		//System.out.println("amount=============>"+amount);
		//System.out.println("transfermode============>"+transfermode);
		
		Double charge=0.0;
		  if(transfermode.equals("IMPS")) {
          	
          	Double amountDouble= amount;
          	if(amountDouble<=1000) {
          		//charge=getcharges("INSTANTPAY","Fund Transfer To Bank A/C (IMPS)","Upto 1000");
          		 charge=0.0;   	
          	}
          	else if(amountDouble>1001&&amountDouble<=25000) {
          		//charge=getcharges("INSTANTPAY","Fund Transfer To Bank A/C (IMPS)","Above 1001");
          		 charge=0.0;   
          	}
          	else {
          		//charge=getcharges("INSTANTPAY","Fund Transfer To Bank A/C (IMPS)","Above 25001");
          		 charge=0.0;   
          	}
          	}
          else if(transfermode.equals("NEFT")) {
          //	charge=getcharges("INSTANTPAY","Fund Transfer To Bank A/C (NEFT)","Any Amount");
        	  charge=0.0;   
          }
          else {
        	  charge=0.0;   
          }
		  System.out.println("charge=====>"+charge);
		  return charge;
	}


	@Override
	public ApiResponse getProviderList() {
		List<Providers> providerList=providersRepositery.findAll();
		if(!providerList.isEmpty()) {
			return new ApiResponse(providerList,HttpStatus.OK.toString());
		
		}
		
		return new ApiResponse("No Providers",HttpStatus.OK.toString());
	}
	

}
