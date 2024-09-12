package com.Ipaisa.Service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.Ipaisa.utils.OkHttpClientSingleton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class AxisService {
    @Value("${axis.ai.id}")
    private String AIID;
    @Value("${axis.private.key}")
    private String PrivateKey;
    @Value("${axis.agent.id}")
    private String AgentId;
    @Value("${axis.public.key}")
    private String PublicKey;
    private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
    private static final String UTF8 = "UTF-8";
    private static final SecureRandom random = new SecureRandom();
    private static final String INSTITUTIONNAME = "EDSOM FINTECH";
    private final OkHttpClient client;
    private static final Random RANDOM = new Random();
    private static final AtomicLong COUNTER = new AtomicLong(1);
    private final String URL = "https://axis-bbps-uat.transxt.in/bbps-cou/v3.0/npciservice/fetch";
    private final String payURL = "https://axis-bbps-uat.transxt.in/bbps-cou/v3.0/npciservice/pay";
	    private final ObjectMapper objectMapper = new ObjectMapper();
	    private static final Logger LOGGER = Logger.getLogger(InstantPayService.class.getName());
	    
	    public AxisService() {
	    	client=OkHttpClientSingleton.getInstance();
		}
	    
	    public String encryptAndSendData() throws Exception {
	    	

	        //String data = objectMapper.writeValueAsString(getConstructedBody());
	        
	        //String jsonString = payload.toString();
           // System.out.println("PayLoad ----->>>>  " + jsonString);
    
            // Encrypt agentId using AES
            String encryptedAgentId = encrypt(AgentId, PublicKey);
            String authHeader = encryptedAgentId;
            // Encrypt JSON string using AES
            String encryptedJson = encrypt(getConstructedBody(), PrivateKey);
           
                        JSONObject encryptedPayload = new JSONObject();
            
            encryptedPayload.put("request", encryptedJson);

	        return sendEncryptedData(encryptedPayload.toString(),authHeader,URL);	    	    
	    }

	    ///////////////////////////////pay//////////////////////////////////////////////
    public String encryptAndSendData2(String reffid) throws Exception {
	    	

	        //String data = objectMapper.writeValueAsString(getConstructedBody());
	        
	        //String jsonString = payload.toString();
           // System.out.println("PayLoad ----->>>>  " + jsonString);
    
            // Encrypt agentId using AES
            String encryptedAgentId = encrypt(AgentId, PublicKey);
            String authHeader = encryptedAgentId;
            // Encrypt JSON string using AES
            String encryptedJson = encrypt(getpaybody(reffid), PrivateKey);
           
                        JSONObject encryptedPayload = new JSONObject();
            
            encryptedPayload.put("request", encryptedJson);
         
            	 return sendEncryptedData(encryptedPayload.toString(),authHeader,payURL);
            
             
	        
	    }
	    
	    
	    
	    
	    
	    
	    private String sendEncryptedData(String data,String auth ,String url) throws Exception {
	    	
	       
	       // MediaType mediaType = MediaType.parse("application/hal+json");
	        MediaType mediaType = MediaType.parse("application/json");
	        RequestBody body = RequestBody.create(mediaType,data);

	        Request request = new Request.Builder()
	                .url(url)
	                .post(body)
	                .addHeader("accept", "application/json")
	                .addHeader("AUTH",auth)
	                .addHeader("content-type", "application/hal+json")
	                
	                .build();
	        // Execute the request
	        try (Response response = client.newCall(request).execute()) {
	            logRequest(request);
	            String responseContentType = response.header("Content-Type");
	            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);

	            Headers headers = response.headers();
	            for (int i = 0, size = headers.size(); i < size; i++) {
	                String headerName = headers.name(i);
	                String headerValue = headers.value(i);
	                LOGGER.log(Level.INFO, "{0}: {1}", new Object[]{headerName, headerValue});
	            }

	            String responseBody = response.body().string();
	            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);

	            // Decrypt the response body
	            return responseBody;
	        } catch (Exception e) {
	            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
	            return null;
	        }
	    }
//


	    private void logRequest(Request request) {
	        LOGGER.log(Level.INFO, "-- Request --");
	        LOGGER.log(Level.INFO, "URL: {0}", request.url());
	        LOGGER.log(Level.INFO, "Method: {0}", request.method());

	        Headers headers = request.headers();
	        for (String name : headers.names()) {
	            LOGGER.log(Level.INFO, "Header: {0} - {1}", new Object[]{name, headers.get(name)});
	        }

	        RequestBody requestBody = request.body();
	        if (requestBody != null) {
	            try {
	                LOGGER.log(Level.INFO, "Body: {0}", requestBodyToString(requestBody));
	            } catch (IOException e) {
	                LOGGER.log(Level.SEVERE, "Error reading request body", e);
	            }
	        }
	    }

	    

	    private String requestBodyToString(RequestBody requestBody) throws IOException {
	        Buffer buffer = new Buffer();
	        requestBody.writeTo(buffer);
	        return buffer.readUtf8();
	    }
	    
	      
	        public String encrypt(String message, String secretKey) throws Exception {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] digestOfPassword = md.digest(secretKey.getBytes(UTF8));
	            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
	            byte[] iv = Arrays.copyOf(digestOfPassword, 16);
	            SecretKey key = new SecretKeySpec(keyBytes, "AES");
	            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
	            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
	            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
	            byte[] plainTextBytes = message.getBytes(UTF8);
	            byte[] buf = cipher.doFinal(plainTextBytes);
	            byte[] base64Bytes = Base64.getEncoder().encode(buf);
	            return new String(base64Bytes, UTF8);
	        }
	        public String decrypt(String encryptedMessage, String secretKey) throws Exception {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] digestOfPassword = md.digest(secretKey.getBytes(UTF8));
	            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
	            byte[] iv = Arrays.copyOf(digestOfPassword, 16);
	            SecretKey key = new SecretKeySpec(keyBytes, "AES");
	            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
	            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
	            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
	            byte[] base64Bytes = Base64.getDecoder().decode(encryptedMessage);
	            byte[] decryptedBytes = cipher.doFinal(base64Bytes);
	            return new String(decryptedBytes, UTF8);
	        }
	        public static String generateAgentId(String institutionName) {
	            if (institutionName == null || institutionName.length() < 4) {
	                throw new IllegalArgumentException("Institution name must be at least 4 characters long.");
	            }
	            String ouCode = "OU01";
	            String institutionCode = institutionName.substring(0, 4).toUpperCase();
	            String randomNumber = String.format("%012d", random.nextInt(1000000000));
	            return ouCode + institutionCode + randomNumber;
	        }
//	        private String generateBillerId(String billerName, String subBiller, String region) {
//	            String billerNamePart = billerName.length() >= 4 ? billerName.substring(0, 4) : String.format("%-4s", billerName).replace(' ', '0');
//	            String randomPart = String.format("%02d", new SecureRandom().nextInt(100));
//	            return billerNamePart + subBiller + region + randomPart;
//	        }

    public String getConstructedBody() {
        Map<String, Object> data = new HashMap<>();

        data.put("chId", 1);
        data.put("isRealTimeFetch", true);

        Map<String, Object> custDetails = new HashMap<String,Object>();
        custDetails.put("mobileNo","7021398101");

        List<Map<String, String>> insideCustomerTags = new ArrayList<>();
        insideCustomerTags.add(getKeyValue("EMAIL", "mk.chekuri@gmail.com"));
        custDetails.put("customerTags", insideCustomerTags);

        data.put("custDetails", custDetails);

        	
        Map<String, Object> agentDetails = new HashMap<>();
        agentDetails.put("agentId", "AM01YKS067INTU000001");

        List<Map<String, String>> deviceTags = new ArrayList<>();
       deviceTags.add(getKeyValue("INITIATING_CHANNEL", "INT"));
//        deviceTags.add(getKeyValue("MOBILE", "9422194999"));
//        deviceTags.add(getKeyValue("GEOCODE", "19.9052,77.5755"));
//        deviceTags.add(getKeyValue("POSTAL_CODE", "445204"));
//        deviceTags.add(getKeyValue("TERMINAL_ID", "3451234560"));
//        deviceTags.add(getKeyValue("INITIATING_CHANNEL", "INT"));
        deviceTags.add(getKeyValue("IP", "124.170.23.28"));
        deviceTags.add(getKeyValue("MAC", "00-14-22-01-23-45"));

        agentDetails.put("deviceTags", deviceTags);
        data.put("agentDetails", agentDetails);

        Map<String, Object> billDetails = new HashMap<>();
        billDetails.put("billerId", "MAHA00000MAH01");

        List<Map<String, String>> customerParams = new ArrayList<>();
     
        customerParams.add(getKeyValue("Consumer No","842157026212"));
        customerParams.add(getKeyValue("BU", "1234"));
        
      //  customerParams.add(getKeyValue("UID", "618707875320"));
        
    //    customerParams.add(getKeyValue("Account No", "120664"));

        billDetails.put("customerParams", customerParams);
        data.put("billDetails", billDetails);
        
        ObjectMapper objectMapper = new ObjectMapper();
        try {
			System.out.println(objectMapper.writeValueAsString(data));
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    
    }
    
    public String getpaybody(String reffid) {
    	System.out.println("in pay");
    	Map<String ,Object> data=new HashMap<String, Object>();
    	   data.put("chId", 1);
    	 Map<String, Object> agentDetails = new HashMap<>();
         agentDetails.put("agentId", "AM01YKS067INTU000001");
         List<Map<String, String>> deviceTags = new ArrayList<>();
         deviceTags.add(getKeyValue("INITIATING_CHANNEL", "INT"));
       deviceTags.add(getKeyValue("IP", "124.170.23.28"));
       deviceTags.add(getKeyValue("MAC", "00-14-22-01-23-45"));
       agentDetails.put("deviceTags", deviceTags);
       data.put("agentDetails", agentDetails);
       
       Map<String,Object> amountDetails=new HashMap<String, Object>();
       amountDetails.put("amount","10");
       amountDetails.put("currency","356");
       amountDetails.put("custConvFee","0");
       amountDetails.put("couCustConvFee","");
       data.put("amountDetails", amountDetails);
       Map<String, Object> billDetails = new HashMap<>();
       billDetails.put("billerId", "MAHA00000MAH01");

       List<Map<String, String>> customerParams = new ArrayList<>();
    
       customerParams.add(getKeyValue("Consumer No","842157026212"));
       customerParams.add(getKeyValue("BU", "1234"));
     //  customerParams.add(getKeyValue("Id", "ASDU4595"));
       billDetails.put("customerParams", customerParams);
       
      List<Map<String,Object>> reponseParams=new ArrayList<Map<String,Object>>();
      Map<String,Object> reponseParamsobj=new HashMap<String, Object>();
      List<Map<String,String>> amtBreakupList=new ArrayList<Map<String,String>>();
//      amtBreakupList.add(getKeyValue("Early Payment Amount"," "));
//      amtBreakupList.add(getKeyValue("Late Payment Amount"," "));
      reponseParamsobj.put("amtBreakupList", amtBreakupList);
      reponseParams.add(reponseParamsobj);
      data.put("reponseParams", reponseParams);
      
       data.put("billDetails", billDetails);
       Map<String, Object> custDetails = new HashMap<String,Object>();
       custDetails.put("mobileNo","7021398101");

       List<Map<String, String>> insideCustomerTags = new ArrayList<>();
       insideCustomerTags.add(getKeyValue("EMAIL", "mk.chekuri@gmail.com"));
       custDetails.put("customerTags", insideCustomerTags);

       data.put("custDetails", custDetails);
       
       Map<String,Object> paymentDetails=new HashMap<String, Object>();
       paymentDetails.put("paymentMode", "Credit Card");
       paymentDetails.put("quickPay", "Yes");
       paymentDetails.put("splitPay", "Yes");
       paymentDetails.put("offusPay", "No");
       List<Map<String,String>> paymentDetailslist=new ArrayList<Map<String,String>>();
//       paymentDetailslist.add(getKeyValue("name","CardNum|AuthCode"));
       paymentDetailslist.add(getKeyValue("CardNum|AuthCode","1234567890123456|123456"));
       paymentDetails.put("paymentInfo", paymentDetailslist);
       data.put("paymentDetails", paymentDetails);
      data.put("refId", reffid);
       data.put("clientRequestId", generateTransactionId());       
       data.put("paymentDetails", paymentDetails);	
       
       System.out.println(data.toString());
       
       
       ObjectMapper objectMapper = new ObjectMapper();
       try {
			System.out.println(objectMapper.writeValueAsString(data));
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
       try {
           return objectMapper.writeValueAsString(data);
       } catch (JsonProcessingException e) {
           e.printStackTrace();
           return null;
       }
    }

    private Map<String, String> getKeyValue(String key, String value) {
        Map<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("name", key);
        keyValueMap.put("value", value);
        return keyValueMap;
    }

	public ResponseEntity<?> getAllBillers() throws Exception {
		
		  String encryptedAgentId = encrypt(AgentId, PublicKey);
		
		 Request request = new Request.Builder()
	                .url("https://axis-bbps-uat.transxt.in/bbps-cou/v3.0/billerservice/downloadMDM/billercategory/Electricity")
	                .get()
	              //  .addHeader("accept", "application/json")
	                .addHeader("AUTH",encryptedAgentId)
	               // .addHeader("content-type", "application/json")
	                
	                .build();
	        // Execute the request
	        try (Response response = client.newCall(request).execute()) {
	            logRequest(request);
	            String responseContentType = response.header("Content-Type");
	            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);

	            Headers headers = response.headers();
	            for (int i = 0, size = headers.size(); i < size; i++) {
	                String headerName = headers.name(i);
	                String headerValue = headers.value(i);
	                LOGGER.log(Level.INFO, "{0}: {1}", new Object[]{headerName, headerValue});
	            }

	            String responseBody = null;
				try {
					responseBody = response.body().string();
					System.out.println(responseBody);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);

	            // Decrypt the response body
	            return ResponseEntity.ok(responseBody);
	}
	
	}
	
	
	
	   public static String generateTransactionId() {
	        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
	        int randomNum = 100000 + RANDOM.nextInt(900000)+RANDOM.hashCode(); // 6-digit random number
	        long counterValue = COUNTER.getAndIncrement();
	        return timestamp + randomNum + counterValue;
	    }
	    
	    

}

	        
	        
	        
	           
	        
	    
	
	
	


