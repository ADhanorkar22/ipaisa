package com.Ipaisa.UserController;

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
@RequestMapping("/auth")
@CrossOrigin(origins = {"https://ipaisa.co.in", "https://www.ipaisa.co.in", "https://api.ipaisa.co.in"})
public class PaymentController {

	@Value("${easebuzz.api.key}")
	private String key;

	@Value("${easebuzz.api.salt}")
	private String salt;

	@Autowired
	private EasebuzzConfig easebuzzConfig;

	@Autowired
	private EaseBuzzService easebuzz;

	@Autowired
	private UserDetailsService udeatils;
	@Autowired
	private JwtUtils utils;
	@Autowired
	private UserRepositery uRepo;

	private final OkHttpClient client = new OkHttpClient();
	private final ObjectMapper objectmapper = new ObjectMapper();

//    @CrossOrigin(origins = "http://127.0.0.1:5500")
	@PostMapping(value = "/easebuzz/initiate-payment", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> initiatePayment(@RequestHeader("Authorization") String token,
			@RequestBody PaymentRequest paymentRequest) {
		try {
			String t = null;
			Object Response;
			if (token.startsWith("Bearer ")) {
				t = token.substring(7);
				System.out.println(t); 
			}
			String username = utils.getUserNameFromJwtToken(t);
			UserDetails userDetails = udeatils.loadUserByUsername(username);
			String mobileno = userDetails.getUsername();

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
				return easebuzz.initiatePayment(paymentRequest);		

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		}

	}

	@CrossOrigin(origins = { "https://ipaisa.co.in", "https://api.ipaisa.co.in" })
	@PostMapping("/transaction-api-v2")
	public ResponseEntity<?> initiateTransaction(@RequestHeader("Authorization") String token,
			@RequestBody Map<String, String> requestBody) {
		try {
			String t = null;
			Object Response;
			if (token.startsWith("Bearer ")) {
				t = token.substring(7);
				System.out.println(t);
			}
			String username = utils.getUserNameFromJwtToken(t);
			UserDetails userDetails = udeatils.loadUserByUsername(username);
			String userid = userDetails.getUsername();

			System.out.println(requestBody);

			return easebuzz.getResponse(requestBody, userid);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		}
	}

}
