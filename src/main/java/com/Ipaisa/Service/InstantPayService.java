package com.Ipaisa.Service;

import java.io.IOException;
import java.util.logging.Level;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
//import jakarta.servlet.http.HttpServletRequest;
import okhttp3.*;
import okio.Buffer;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.Ipaisa.Entitys.BulkPaymentRequest;
import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.Entitys.InstantPayOut;
import com.Ipaisa.Entitys.User;
import com.Ipaisa.Repository.InstantPayoutRepository;
import com.Ipaisa.Repository.UserRepositery;
import com.Ipaisa.fina.SprintNxtService;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import okhttp3.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Logger;
//import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

//import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
@Transactional
public class InstantPayService implements LatitudeLongitude ,PayeeAccountNumber{

    @Value("${instantpay.client_id}")
    private String Client_Id;

    @Value("${instantpay.client_secret}")
    private String Client_Secret;

    @Autowired
    private HttpServletRequest request;
    @Autowired
   	private UserRepositery uRepo;
    
    @Autowired
    private InstantPayoutRepository instantPayoutService;
    
    @Autowired
    private SetChargesService setChargesService;
    
    
    

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(InstantPayService.class.getName());
    private static final Random RANDOM = new Random();
    private static final AtomicLong COUNTER = new AtomicLong(1);
    public static final String bankProfileId="35253668680";
    Double charge=0.0;
   
  

    public String encryptAndSendData(InstantPayBody payload ,String mobileno ,Double ourCharge) throws Exception {
        // Construct the request body
    	
        Map<String, Object> payer = new HashMap<>();
        payer.put("bankProfileId", bankProfileId);
        payer.put("accountNumber", PayeeAccountNumber.payerAccNumber);

        Map<String, Object> payee = new HashMap<>();
        payee.put("name", payload.getName());
        payee.put("accountNumber", payload.getPayeeaccountNumber());
        payee.put("bankIfsc", payload.getBankIfsc());

        Map<String, Object> body = new HashMap<>();
        body.put("payer", payer);
        body.put("payee", payee);
        body.put("transferMode", payload.getTransferMode());
        body.put("transferAmount", payload.getTransferAmount());
        body.put("externalRef", generateTransactionId());
        body.put("latitude", LatitudeLongitude.Latitde);
        body.put("longitude",LatitudeLongitude.Longitude);
        body.put("remarks", PayeeAccountNumber.remark);
        body.put("purpose", PayeeAccountNumber.purpose);

        String data = objectMapper.writeValueAsString(body);

        return sendEncryptedData(data,mobileno,payload.getTransferMode(),ourCharge);
    }

    private String sendEncryptedData(String data,String mobileno, String transfermode,Double ourCharge) throws Exception {
    	
    	 String responseBody=null;
    	URL url = new URL("http://ifconfig.me/ip");
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	con.setRequestMethod("GET");
    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    	String endpointIp = in.readLine();
    	in.close();
    	System.out.println("Public IP Address: " + endpointIp);
     
       
        // Build the request
        MediaType mediaType = MediaType.parse("application/hal+json");
        RequestBody body = RequestBody.create(mediaType, data);

        Request request = new Request.Builder()
                .url("https://api.instantpay.in/payments/payout")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("X-Ipay-Auth-Code", "1")
                .addHeader("X-Ipay-Client-Id", Client_Id)
                .addHeader("X-Ipay-Client-Secret", Client_Secret)
                .addHeader("X-Ipay-Endpoint-Ip", endpointIp) // Set the endpoint IP dynamically
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

            responseBody = response.body().string();
//            InstantPayOut instantPayoyt = objectMapper.readValue(responseBody, InstantPayOut.class);
//            instantPayoutService.save(instantPayoyt);
            
            
            
            
            
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
                Map<String, Object> data1 = (Map<String, Object>) jsonMap.get("data");

                // Extract values from JSON
                InstantPayOut instantPayoyt = new InstantPayOut();
                User user=uRepo.findByMobileNumber(mobileno);
                System.out.println("user======>"+user);
                instantPayoyt.setUser(user);
                instantPayoyt.setStatuscode((String) jsonMap.get("statuscode"));
                instantPayoyt.setActcode((String) jsonMap.get("actcode"));
                instantPayoyt.setStatus((String) jsonMap.get("status"));
                instantPayoyt.setExternalRef((String) data1.get("externalRef"));
                instantPayoyt.setPoolReferenceId((String) data1.get("poolReferenceId"));
                instantPayoyt.setTxnValue((String) data1.get("txnValue"));
                instantPayoyt.setTxnReferenceId((String) data1.get("txnReferenceId"));

                Map<String, Object> pool = (Map<String, Object>) data1.get("pool");
                if (pool != null) {
                    instantPayoyt.setPoolAccount((String) pool.get("account"));
                    instantPayoyt.setPoolOpeningBal((String) pool.get("openingBal"));
                    instantPayoyt.setPoolMode((String) pool.get("mode"));
                    instantPayoyt.setPoolAmount((String) pool.get("amount"));
                    instantPayoyt.setPoolClosingBal((String) pool.get("closingBal"));
                }

                Map<String, Object> payer = (Map<String, Object>) data1.get("payer");
                if (payer != null) {
                    instantPayoyt.setPayerAccount((String) payer.get("account"));
                    instantPayoyt.setPayerName((String) payer.get("name"));
                }

                Map<String, Object> payee = (Map<String, Object>) data1.get("payee");
                if (payee != null) {
                    instantPayoyt.setPayeeAccount((String) payee.get("account"));
                    instantPayoyt.setPayeeName((String) payee.get("name"));
                }
                String timestampString = (String) jsonMap.get("timestamp");
                if (timestampString != null && !timestampString.isEmpty()) {
                    try {
                        LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        instantPayoyt.setTimestamp(timestamp);
                    } catch (DateTimeParseException e) {
                        System.err.println("Error parsing timestamp: " + timestampString);
                        e.printStackTrace();
                    }
                }

                instantPayoyt.setIpayUuid((String) jsonMap.get("ipay_uuid"));
                instantPayoyt.setOrderid((String) jsonMap.get("orderid"));
                instantPayoyt.setEnvironment((String) jsonMap.get("environment"));

     ////////////////// Update Wallet Balance /////////////////////////////////
                instantPayoyt.setWalletOpeningBalance(user.getWalletBalance());
                String amt=(String) data1.get("txnValue");
                double amount=Double.parseDouble(amt);
//                double percentAmount=amount* 2.66 /100;
               
                double finalAmount=amount+ourCharge;
                
                String status=(String) jsonMap.get("status");
                if("Transaction Successful".equals(status))
                {
                	user.setWalletBalance(user.getWalletBalance()-finalAmount);
                	uRepo.save(user);
                }

                instantPayoyt.setWalletClosingBalance(user.getWalletBalance());
                instantPayoutService.save(instantPayoyt);
         
                
                
                         
                
                
            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);

            // Decrypt the response body
            return responseBody;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
            return responseBody;
        }
    }
      
    }


//    private String getClientIpAddress() {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attributes != null) {
//            HttpServletRequest request = attributes.getRequest();
//            return request.getRemoteAddr();
//        }
//        return null;
//    }

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
    
   
    public static String generateTransactionId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        int randomNum = 100000 + RANDOM.nextInt(900000); // 6-digit random number
        long counterValue = COUNTER.getAndIncrement();
        return timestamp + randomNum + counterValue;
    }
    
    
    
//    public String processBulkPayments(BulkPaymentRequest bulkRequest, String mobileno) throws Exception {
//        StringBuilder responseBuilder = new StringBuilder();
//        User user = uRepo.findByMobileNumber(mobileno);
//        double totalAmount = bulkRequest.getPayments().stream()
//                                       .mapToDouble(payment -> Double.parseDouble(payment.getTransferAmount()))
//                                       .sum();
//
//        if (user.getWalletBalance() < totalAmount) {
//            return "Insufficient balance for bulk payments";
//        }
//
//        for (InstantPayBody payment : bulkRequest.getPayments()) {
//            String response = encryptAndSendData(payment, mobileno);
//            responseBuilder.append(response).append("\n");
//        }
//
//        return responseBuilder.toString();
//    }
//    
    
}
