package com.Ipaisa.DeepLink;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.EaseBuzzUtil.HashUtil;
import com.Ipaisa.Entitys.EasebuzzPayin;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Jwt_Utils.JwtUtils;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.Service.EaseBuzzService;
import com.Ipaisa.config.EasebuzzConfig;
import com.Ipaisa.dto.PaymentRequest;
import com.Ipaisa.dto.PaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/ezpg")
@CrossOrigin(origins = { "https://ipaisa.co.in", "https://www.ipaisa.co.in", "https://api.ipaisa.co.in",
		"http://localhost:3000", "http://localhost:3001" })
public class EzController {

	@Value("${easebuzz.api.key}")
	private String key;

	@Value("${easebuzz.api.salt}")
	private String salt;

	@Autowired
	private EasebuzzConfig easebuzzConfig;

	@Autowired
	private EzService easebuzz;

	@Autowired
	private UserDetailsService udeatils;
	@Autowired
	private JwtUtils utils;
	@Autowired
	private UserRepositery uRepo;

	private final OkHttpClient client = new OkHttpClient();
	private final ObjectMapper objectmapper = new ObjectMapper();

//   
	@PostMapping(value = "/easebuzz/initiate-payment", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> initiatePayment(@RequestBody PaymentRequest paymentRequest) {
		try {
//			String t = null;
//			Object Response;
//			if (token.startsWith("Bearer ")) {
//				t = token.substring(7);
//				System.out.println(t); 
//			}
//			String username = utils.getUserNameFromJwtToken(t);
//			UserDetails userDetails = udeatils.loadUserByUsername(username);
//			String mobileno = userDetails.getUsername();

//			User user = this.uRepo.findByMobileNumber(mobileno);
//			user.getWalletBalance();
//			String amountString = paymentRequest.getAmount(); 
//			double amount = Double.parseDouble(amountString);
//			double percentAmount=amount* 2.66 /100;
//			double finalAmount=amount+percentAmount;
//			
//			if(finalAmount>amount)
//			{
//				  return new ResponseEntity<>("Insufficient balance", HttpStatus.PAYMENT_REQUIRED);
//			}
//			else

//			private String phone;
//			private double amount;
//			private String orderId;
//			private String redirectUrl;
//			
//			
//			PaymentRequest request=new PaymentRequest();
//			request.setAmount(String.valueOf(req.getAmount()));
//			request.setEmail("");
//			request.setFirstname();
//			request.setPhone(req.getPhone());
//			request.setFurl();
//			request.setSurl(req.getRedirectUrl());
//			request.setProductinfo();
//			request.setTxnid(req.getOrderId());

//					easebuzz.initiatePayment(paymentRequest);	
			return ResponseEntity.ok(easebuzz.initiatePayment(paymentRequest));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		}

	}

//	@CrossOrigin(origins = { "https://ipaisa.co.in", "https://api.ipaisa.co.in" })
//	@PostMapping("/get-response")
	@PostMapping("/transaction-api-v2")
	public ResponseEntity<?> initiateTransaction(@RequestBody Map<String, String> requestBody) {
		try {
//			String t = null;
//			Object Response;
//			if (token.startsWith("Bearer ")) {
//				t = token.substring(7);
//				System.out.println(t);
//			}
//			String username = utils.getUserNameFromJwtToken(t);
//			UserDetails userDetails = udeatils.loadUserByUsername(username);
//			String userid = userDetails.getUsername();
//
//			System.out.println(requestBody);

//		String userid="7798552844";

			return easebuzz.getResponse(requestBody);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		}
	}

}
