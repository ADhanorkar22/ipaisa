package com.Ipaisa.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Ipaisa.EaseBuzzUtil.HashUtil;
import com.Ipaisa.Entitys.EasebuzzPayin;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Repository.EaseBuzzRepositery;
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

@Service
@Transactional
public class EaseBuzzService {
	
	@Value("${easebuzz.api.key}")
    private String key;

    @Value("${easebuzz.api.salt}")
    private String salt;
	
    @Autowired
    private EasebuzzConfig easebuzzConfig;
    
   
    
    @Autowired
    private EaseBuzzRepositery easerepo;
    @Autowired
   	private UserRepositery uRepo;
	
	
	  private final OkHttpClient client = new OkHttpClient();
	    private final ObjectMapper  objectmapper= new ObjectMapper();
	    
	    
	    
	    
	    public ResponseEntity<?> initiatePayment(PaymentRequest paymentRequest) {
	    	  try {
	              String hash = HashUtil.generateHash(
	                      easebuzzConfig.getApiKey(),
	                      paymentRequest.getTxnid(),
	                      paymentRequest.getAmount(),
	                      paymentRequest.getProductinfo(),
	                      paymentRequest.getFirstname(),
	                      paymentRequest.getEmail(),
	                      easebuzzConfig.getApiSalt()
	              );

	              String params = String.format(
	                      "key=%s&txnid=%s&amount=%s&productinfo=%s&firstname=%s&phone=%s&email=%s&surl=%s&furl=%s&hash=%s",
	                      easebuzzConfig.getApiKey(),
	                      paymentRequest.getTxnid(),
	                      paymentRequest.getAmount(),
	                      paymentRequest.getProductinfo(),
	                      paymentRequest.getFirstname(),
	                      paymentRequest.getPhone(),
	                      paymentRequest.getEmail(),
	                      paymentRequest.getSurl(),
	                      paymentRequest.getFurl(),
	                      hash
	              );

	              try (CloseableHttpClient client = HttpClients.createDefault()) {
	                  HttpPost httpPost = new HttpPost(easebuzzConfig.getApiUrl());
	                  httpPost.setEntity(new StringEntity(params));
	                  httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
	                  httpPost.setHeader("Accept", "application/json");

	                  String responseString = EntityUtils.toString(client.execute(httpPost).getEntity());
	                
	                  return ResponseEntity.ok(responseString);
	              }
	          } catch (Exception e) {
	              return ResponseEntity.status(500).body(new PaymentResponse(0, "Error initiating payment"));
	          }
	    }
	
	
	public  ResponseEntity<?> getResponse(Map<String, String> requestBody,String mobileno) {
	    try {
	    	String txnid =null;
	    	txnid= requestBody.get("txnid");
	    	
	    	User user=uRepo.findByMobileNumber(mobileno);
	    	
	    	  EasebuzzPayin easebuzz=new EasebuzzPayin();
              easebuzz.setTxnid(txnid);
              easebuzz.setUser(user);
              easerepo.save(easebuzz);
            
            String inputString = key + "|" + txnid + "|" + salt;
            String hash = generateSHA512Hash(inputString);

            FormBody formBody = new FormBody.Builder()
                    .add("txnid", txnid)
                    .add("key", key)
                    .add("hash", hash)
                    .build();

            Request request = new Request.Builder()
                    .url("https://dashboard.easebuzz.in/transaction/v2/retrieve")
                    .post(formBody)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
                String json=response.body().string();
                
                System.out.println("json=====>"+json);
                
                ObjectMapper objectMapper = new ObjectMapper();
//                Map<String, Object> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
//
//               Map<String, Object> msgMap = (Map<String, Object>) jsonMap.get("msg");
              //  EasebuzzPayin easebuzztixn = easerepo.findByTxnid(txnid);

                EasebuzzPayin easebuzztixn=easerepo.findByTxnid(txnid);
                System.out.println("easebuzztixn=======>"+easebuzztixn.toString());
//                easebuzztixn.setFirstname((String)msgMap.get("firstname"));
//                easebuzztixn.setEmail((String) msgMap.get("email"));
//                easebuzztixn.setPhone((String) msgMap.get("phone"));
//                easebuzztixn.setKey( (String) msgMap.get("key"));
//                easebuzztixn.setMode((String) msgMap.get("mode"));
//                easebuzztixn.setUnmappedstatus( (String) msgMap.get("unmappedstatus"));
//                easebuzztixn.setCardCategory((String) msgMap.get("cardCategory"));
//                easebuzztixn.setAddedon((LocalDateTime.parse((String)msgMap.get("addedon"))));
//                easebuzztixn.setPaymentSource( (String) msgMap.get("payment_source"));
//                easebuzztixn.setPgType( (String) msgMap.get("PG_TYPE"));
//                easebuzztixn.setBankRefNum((String) msgMap.get("bank_ref_num"));
//                easebuzztixn.setBankcode((String) msgMap.get("bankcode"));
//                easebuzztixn.setError((String) msgMap.get("error"));
//                easebuzztixn.setErrorMessage((String) msgMap.get("error_Message"));
//                easebuzztixn.setNameOnCard((String) msgMap.get("name_on_card"));
//                easebuzztixn.setUpiVa((String) msgMap.get("upi_va"));
//                easebuzztixn.setCardnum((String) msgMap.get("cardnum"));
//                easebuzztixn.setIssuingBank((String) msgMap.get("issuing_bank"));
//                easebuzztixn.setEasepayid( (String) msgMap.get("easepayid"));
//                easebuzztixn.setAmount((String) msgMap.get("amount"));
//                easebuzztixn.setNetAmountDebit((String) msgMap.get("net_amount_debit"));
//                easebuzztixn.setCashBackPercentage((String) msgMap.get("cash_back_percentage"));
//                easebuzztixn.setDeductionPercentage((String) msgMap.get("deduction_percentage"));
//              
//                easebuzztixn.setProductinfo((String) msgMap.get("productinfo"));
//                easebuzztixn.setCardType((String) msgMap.get("card_type"));
//            
//                easebuzztixn.setStatus((String) msgMap.get("status"));
//                easebuzztixn.setBankName((String) msgMap.get("bank_name"));
//                easebuzztixn.setAuthCode((String) msgMap.get("auth_code"));
                
                Map<String, Object> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});

                // Extract 'msg' object from JSON
                Map<String, Object> msgMap = (Map<String, Object>) jsonMap.get("msg");
                
                System.out.println("msgMap=======>"+msgMap);

                // Retrieve 'txnid' from 'msg' object
            
              
                // Update entity fields from 'msg' map
                easebuzztixn.setFirstname((String) msgMap.get("firstname"));
                easebuzztixn.setEmail((String) msgMap.get("email"));
                easebuzztixn.setPhone((String) msgMap.get("phone"));
                easebuzztixn.setKeyId((String) msgMap.get("key")); // Corrected to setKeyId
                easebuzztixn.setMode((String) msgMap.get("mode"));
                easebuzztixn.setUnmappedstatus((String) msgMap.get("unmappedstatus"));
                easebuzztixn.setCardCategory((String) msgMap.get("cardCategory"));

                // Parse 'addedon' string to LocalDateTime
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime addedon = LocalDateTime.parse((String) msgMap.get("addedon"), formatter);
                easebuzztixn.setAddedon(addedon);

//                easebuzztixn.setPaymentSource((String) msgMap.get("payment_source"));
                easebuzztixn.setPgType((String) msgMap.get("PG_TYPE"));
                easebuzztixn.setBankRefNum((String) msgMap.get("bank_ref_num"));
                easebuzztixn.setBankcode((String) msgMap.get("bankcode"));
                easebuzztixn.setError((String) msgMap.get("error"));
                easebuzztixn.setErrorMessage((String) msgMap.get("error_Message"));
                easebuzztixn.setNameOnCard((String) msgMap.get("name_on_card"));
                easebuzztixn.setUpiVa((String) msgMap.get("upi_va"));
                easebuzztixn.setCardnum((String) msgMap.get("cardnum"));
                easebuzztixn.setIssuingBank((String) msgMap.get("issuing_bank"));
                easebuzztixn.setEasepayid((String) msgMap.get("easepayid"));
                easebuzztixn.setAmount((String) msgMap.get("amount"));
                easebuzztixn.setNetAmountDebit((String) msgMap.get("net_amount_debit"));
                easebuzztixn.setCashBackPercentage((String) msgMap.get("cash_back_percentage"));
                easebuzztixn.setDeductionPercentage((String) msgMap.get("deduction_percentage"));
                easebuzztixn.setProductinfo((String) msgMap.get("productinfo"));
                easebuzztixn.setCardType((String) msgMap.get("card_type"));
                easebuzztixn.setStatus((String) msgMap.get("status"));
                easebuzztixn.setBankName((String) msgMap.get("bank_name"));
                easebuzztixn.setAuthCode((String) msgMap.get("auth_code"));
                
                /////////////////////////// Wallet Calculation /////////////////////////////////////////
                String Status=(String) msgMap.get("status");
                if(Status.matches("success"))
                {
                	String amount=(String) msgMap.get("amount");
                	double famount=Double.parseDouble(amount);
                	user.setWalletBalance(user.getWalletBalance()+famount);
                	uRepo.save(user);
                }
                
                
                easerepo.save(easebuzztixn);
                
                
                
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", 0, "message", "Error initiating payment"));
        }
	}
	  private String generateSHA512Hash(String input) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        for (byte b : bytes) {
	            sb.append(String.format("%02x", b));
	        }
	        return sb.toString();
	    }

}
