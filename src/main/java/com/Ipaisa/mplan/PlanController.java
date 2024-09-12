package com.Ipaisa.mplan;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plans")
public class PlanController {
	@Autowired
	 private RechargePlans rechargePlans;
	
//	@Autowired
//	private SimplePlansRecharge  planService;
	
	@GetMapping("/rechargepaln")
	public ResponseEntity<?> getPlans() throws IOException
	{
		return ResponseEntity.ok(rechargePlans.fetchPlanDetails());
	}
	}