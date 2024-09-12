package com.Ipaisa.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Service.SetChargesService;

@RestController
@RequestMapping("/admin")
public class SetCharges {
	
	@Autowired
	private SetChargesService setChargesService;
	
	
	@PutMapping("/setcharge")
	public ApiResponse setCharges(@RequestBody com.Ipaisa.Entitys.SetCharges setcharges){
	System.out.println("here  ");
		
		return setChargesService.setCharges(setcharges);
		
	}
	
	
	@GetMapping("/getprovider/{providerName}")
	public ResponseEntity<?>getProviders(@PathVariable String providerName){
		
		return setChargesService.getproviders(providerName);
	}
	
	
	@GetMapping("/getproviderlist")
	public ApiResponse getProviderList(){
		System.out.println("heree");
		return setChargesService.getProviderList();
	}

}
