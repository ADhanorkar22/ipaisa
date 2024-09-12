package com.Ipaisa.DeepLink;



import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Entitys.EasebuzzPayin;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Repository.DeeplinkRepository;
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
public class EzService {
	
	@Value("${easebuzz.api.key}")
    private String key;

    @Value("${easebuzz.api.salt}")
    private String salt;
	
    @Autowired
    private EasebuzzConfig easebuzzConfig;
    
   
    
    @Autowired
    private DeeplinkRepository deeprepo;
//    @Autowired
//   	private UserRepositery uRepo;
	
	
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
	                System.out.println("Response ---->>> "+responseString);
	              
	                  return  ResponseEntity.ok(responseString);
	              }
	          } catch (Exception e) {
	              return ResponseEntity.status(500).body(new PaymentResponse(0, "Error initiating payment"));
	          }
	    }
	
	
	public  ResponseEntity<?> getResponse(Map<String, String> requestBody) {
	    try {
	    	String txnid =null;
	    	txnid= requestBody.get("txnid");
	    	String phone=null;
	    	phone=requestBody.get("phone");
	    	
//	    	User user=uRepo.findByMobileNumber("7798552844");
	    	
	    	DeeplinkPayin deeplinkk=new DeeplinkPayin();
	    	deeplinkk.setTxnid(txnid);
	    	deeplinkk.setPhone(phone);
              deeprepo.save(deeplinkk);
            
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
              //  EasebuzzPayin deeplink = easerepo.findByTxnid(txnid);

                DeeplinkPayin deeplink=deeprepo.findByTxnid(txnid);
                System.out.println("deeplink=======>"+deeplink.toString());
//                deeplink.setFirstname((String)msgMap.get("firstname"));
//                deeplink.setEmail((String) msgMap.get("email"));
//                deeplink.setPhone((String) msgMap.get("phone"));
//                deeplink.setKey( (String) msgMap.get("key"));
//                deeplink.setMode((String) msgMap.get("mode"));
//                deeplink.setUnmappedstatus( (String) msgMap.get("unmappedstatus"));
//                deeplink.setCardCategory((String) msgMap.get("cardCategory"));
//                deeplink.setAddedon((LocalDateTime.parse((String)msgMap.get("addedon"))));
//                deeplink.setPaymentSource( (String) msgMap.get("payment_source"));
//                deeplink.setPgType( (String) msgMap.get("PG_TYPE"));
//                deeplink.setBankRefNum((String) msgMap.get("bank_ref_num"));
//                deeplink.setBankcode((String) msgMap.get("bankcode"));
//                deeplink.setError((String) msgMap.get("error"));
//                deeplink.setErrorMessage((String) msgMap.get("error_Message"));
//                deeplink.setNameOnCard((String) msgMap.get("name_on_card"));
//                deeplink.setUpiVa((String) msgMap.get("upi_va"));
//                deeplink.setCardnum((String) msgMap.get("cardnum"));
//                deeplink.setIssuingBank((String) msgMap.get("issuing_bank"));
//                deeplink.setEasepayid( (String) msgMap.get("easepayid"));
//                deeplink.setAmount((String) msgMap.get("amount"));
//                deeplink.setNetAmountDebit((String) msgMap.get("net_amount_debit"));
//                deeplink.setCashBackPercentage((String) msgMap.get("cash_back_percentage"));
//                deeplink.setDeductionPercentage((String) msgMap.get("deduction_percentage"));
//              
//                deeplink.setProductinfo((String) msgMap.get("productinfo"));
//                deeplink.setCardType((String) msgMap.get("card_type"));
//            
//                deeplink.setStatus((String) msgMap.get("status"));
//                deeplink.setBankName((String) msgMap.get("bank_name"));
//                deeplink.setAuthCode((String) msgMap.get("auth_code"));
                
                Map<String, Object> jsonMap = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});

                // Extract 'msg' object from JSON
                Map<String, Object> msgMap = (Map<String, Object>) jsonMap.get("msg");
                
                System.out.println("msgMap=======>"+msgMap);

                // Retrieve 'txnid' from 'msg' object
            
              
                // Update entity fields from 'msg' map
                deeplink.setFirstname((String) msgMap.get("firstname"));
                deeplink.setEmail((String) msgMap.get("email"));
                deeplink.setPhone((String) msgMap.get("phone"));
                deeplink.setKeyId((String) msgMap.get("key")); // Corrected to setKeyId
                deeplink.setMode((String) msgMap.get("mode"));
                deeplink.setUnmappedstatus((String) msgMap.get("unmappedstatus"));
                deeplink.setCardCategory((String) msgMap.get("cardCategory"));

                // Parse 'addedon' string to LocalDateTime
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime addedon = LocalDateTime.parse((String) msgMap.get("addedon"), formatter);
                deeplink.setAddedon(addedon);

//                deeplink.setPaymentSource((String) msgMap.get("payment_source"));
                deeplink.setPgType((String) msgMap.get("PG_TYPE"));
                deeplink.setBankRefNum((String) msgMap.get("bank_ref_num"));
                deeplink.setBankcode((String) msgMap.get("bankcode"));
                deeplink.setError((String) msgMap.get("error"));
                deeplink.setErrorMessage((String) msgMap.get("error_Message"));
                deeplink.setNameOnCard((String) msgMap.get("name_on_card"));
                deeplink.setUpiVa((String) msgMap.get("upi_va"));
                deeplink.setCardnum((String) msgMap.get("cardnum"));
                deeplink.setIssuingBank((String) msgMap.get("issuing_bank"));
                deeplink.setEasepayid((String) msgMap.get("easepayid"));
                deeplink.setAmount((String) msgMap.get("amount"));
                deeplink.setNetAmountDebit((String) msgMap.get("net_amount_debit"));
                deeplink.setCashBackPercentage((String) msgMap.get("cash_back_percentage"));
                deeplink.setDeductionPercentage((String) msgMap.get("deduction_percentage"));
                deeplink.setProductinfo((String) msgMap.get("productinfo"));
                deeplink.setCardType((String) msgMap.get("card_type"));
                deeplink.setStatus((String) msgMap.get("status"));
                deeplink.setBankName((String) msgMap.get("bank_name"));
                deeplink.setAuthCode((String) msgMap.get("auth_code"));
                
                /////////////////////////// Wallet Calculation /////////////////////////////////////////
//                String Status=(String) msgMap.get("status");
//                if(Status.matches("success"))
//                {
//                	String amount=(String) msgMap.get("amount");
//                	double famount=Double.parseDouble(amount);
//                	user.setWalletBalance(user.getWalletBalance()+famount);
//                	uRepo.save(user);
//                }
                
                
                deeprepo.save(deeplink);
                
                
                String amount=(String) msgMap.get("amount");
                String status=(String) msgMap.get("status");
                String mode=(String) msgMap.get("mode");
                String phone2=(String) msgMap.get("phone");
                String txnid1= requestBody.get("txnid");
                if(response.isSuccessful())
                {
                Map<String ,String> allresp=new HashMap<>();
                allresp.put("message ", "Transaction Successfull");
                allresp.put("amount", amount);
                allresp.put("status", status);
                allresp.put("txnmode", mode);
                allresp.put("phone", phone2);
                allresp.put("txnid",txnid1 );
                allresp.put("date", addedon.toString());
                return ResponseEntity.ok(allresp);
                }
                
                
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
