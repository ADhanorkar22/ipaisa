package com.Ipaisa.axis;
//
//import okhttp3.*;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.SecureRandom;
//import java.util.Arrays;
//import java.util.Base64;
//
//@Service
//public class ApiService1 {
//	
//	
//	@Value("${axis.ai.id}")
//	private String AIID;
//	
//	@Value("${axis.private.key}")
//	private String PrivateKey;
//	
//	@Value("${axis.agent.id}")
//	private String AgentId;
//	
//	@Value("${axis.public.key}")
//	private String PublicKey;
//	
//
//    private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
//    private static final String UTF8 = "UTF-8";
//    private static final SecureRandom random = new SecureRandom();
//	 private static final String INSTITUTIONNAME = "EDSOM FINTECH";
//    private final OkHttpClient client = new OkHttpClient();
//
//	private final String URL="https://axis-bbps-uat.transxt.in/bbps-cou/v3.0/npciservice/fetch";
//
//    public void sendRequest() throws Exception {
//        // Define the fields
//        Integer chId = 1;
//        Boolean isRealTimeFetch = true;
//        String mobileNo = "9004398093";
//        String email = "mk.chekuri@gmail.com";
//        String agentId = generateAgentId(INSTITUTIONNAME);
//        String initiatingChannel = "BSC";
//        String agentMobile = "7878787123";
//        String geocode = "28.6139,78.5555";
//        String postalCode = "600001";
//        String terminalId = "3451234560";
////        Integer billerId = 500000;
//        
//        String consumerMobileNo = "9865778954";
//        String uid = "8596547893214111";
//        String accountNo = "258931";
////        String secretKey = "your-secret-key";  // Define your secret key
//
//     // Generate compliant billerId
//        String billerName = "OUA";
//        String subBiller = "500000";
//        String region = "PUN";
//        String billerId = generateBillerId(billerName, subBiller, region);
//        
//        
//        
//        // Create JSON objects
//        JSONObject payload = new JSONObject();
//        JSONObject custDetails = new JSONObject();
//        JSONArray customerTags = new JSONArray();
//        JSONObject emailTag = new JSONObject();
//        JSONObject agentDetails = new JSONObject();
//        JSONArray deviceTags = new JSONArray();
//        JSONObject initiatingChannelTag = new JSONObject();
//        JSONObject agentMobileTag = new JSONObject();
//        JSONObject geocodeTag = new JSONObject();
//        JSONObject postalCodeTag = new JSONObject();
//        JSONObject terminalIdTag = new JSONObject();
//        JSONObject billDetails = new JSONObject();
//        JSONArray customerParams = new JSONArray();
//        JSONObject consumerMobileNoParam = new JSONObject();
//        JSONObject uidParam = new JSONObject();
//        JSONObject accountNoParam = new JSONObject();
//
//        // Populate customerTags
//        emailTag.put("name", "EMAIL");
//        emailTag.put("value", email);
//        customerTags.put(emailTag);
//
//        // Populate custDetails
//        custDetails.put("mobileNo", mobileNo);
//        custDetails.put("customerTags", customerTags);
//
//        // Populate deviceTags
//        initiatingChannelTag.put("name", "INITIATING_CHANNEL");
//        initiatingChannelTag.put("value", initiatingChannel);
//        deviceTags.put(initiatingChannelTag);
//
//        agentMobileTag.put("name", "MOBILE");
//        agentMobileTag.put("value", agentMobile);
//        deviceTags.put(agentMobileTag);
//
//        geocodeTag.put("name", "GEOCODE");
//        geocodeTag.put("value", geocode);
//        deviceTags.put(geocodeTag);
//
//        postalCodeTag.put("name", "POSTAL_CODE");
//        postalCodeTag.put("value", postalCode);
//        deviceTags.put(postalCodeTag);
//
//        terminalIdTag.put("name", "TERMINAL_ID");
//        terminalIdTag.put("value", terminalId);
//        deviceTags.put(terminalIdTag);
//
//        // Populate agentDetails
//        agentDetails.put("agentId", agentId);
//        agentDetails.put("deviceTags", deviceTags);
//
//        // Populate customerParams
//        consumerMobileNoParam.put("name", "Consumer Mobile No");
//        consumerMobileNoParam.put("value", consumerMobileNo);
//        customerParams.put(consumerMobileNoParam);
//
//        uidParam.put("name", "UID");
//        uidParam.put("value", uid);
//        customerParams.put(uidParam);
//
//        accountNoParam.put("name", "Account No");
//        accountNoParam.put("value", accountNo);
//        customerParams.put(accountNoParam);
//
//        // Populate billDetails
//        billDetails.put("billerId", billerId);
//        billDetails.put("customerParams", customerParams);
//
//        // Populate payload
//        payload.put("chId", chId);
//        payload.put("isRealTimeFetch", isRealTimeFetch);
//        payload.put("custDetails", custDetails);
//        payload.put("agentDetails", agentDetails);
//        payload.put("billDetails", billDetails);
//
//        // Convert JSON object to string
//        String jsonString = payload.toString();
//        System.out.println("PayLoad ----->>>>  "+jsonString);
//
//        // Encrypt agentId using AES
//        String encryptedAgentId = encrypt(agentId, PublicKey);
//        String authHeader =  encryptedAgentId;
//
//        // Encrypt JSON string using AES
//        String encryptedJson = encrypt(jsonString, PrivateKey);
//
//        // Create encrypted request payload
//        JSONObject encryptedPayload = new JSONObject();
//        encryptedPayload.put("request", encryptedJson);
//
//        // Create RequestBody
//        RequestBody body = RequestBody.create(encryptedPayload.toString(), MediaType.get("application/json; charset=utf-8"));
//
//        // Create Request
//        Request request = new Request.Builder()
//                .url(URL)
//                .post(body)
//                .addHeader("Auth", authHeader)
//                .build();
//
//        // Send Request
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    System.out.println(response.body().string());
//                } else {
//                    System.out.println("Request failed: " + response.code());
//                }
//            }
//        });
//    }
//
//    public String encrypt(String message, String secretKey) throws Exception {
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        byte[] digestOfPassword = md.digest(secretKey.getBytes(UTF8));
//        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
//        byte[] iv = Arrays.copyOf(digestOfPassword, 16);
//        SecretKey key = new SecretKeySpec(keyBytes, "AES");
//        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
//        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
//        byte[] plainTextBytes = message.getBytes(UTF8);
//        byte[] buf = cipher.doFinal(plainTextBytes);
//        byte[] base64Bytes = Base64.getEncoder().encode(buf);
//        return new String(base64Bytes, UTF8);
//    }
//
//    public String decrypt(String encryptedMessage, String secretKey) throws Exception {
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        byte[] digestOfPassword = md.digest(secretKey.getBytes(UTF8));
//        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
//        byte[] iv = Arrays.copyOf(digestOfPassword, 16);
//        SecretKey key = new SecretKeySpec(keyBytes, "AES");
//        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
//        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
//        byte[] base64Bytes = Base64.getDecoder().decode(encryptedMessage);
//        byte[] decryptedBytes = cipher.doFinal(base64Bytes);
//        return new String(decryptedBytes, UTF8);
//    }
//    
//    public static String generateAgentId(String institutionName) {
//        if (institutionName == null || institutionName.length() < 4) {
//            throw new IllegalArgumentException("Institution name must be at least 4 characters long.");
//        }
//
//        String ouCode = "OU01";
//        String institutionCode = institutionName.substring(0, 4).toUpperCase();
//        String randomNumber = String.format("%012d", random.nextInt(1000000000));
//
//        return ouCode + institutionCode + randomNumber;
//    }
//    
//    private String generateBillerId(String billerName, String subBiller, String region) {
//        String billerNamePart = billerName.length() >= 4 ? billerName.substring(0, 4) : String.format("%-4s", billerName).replace(' ', '0');
//        String randomPart = String.format("%02d", new SecureRandom().nextInt(100));
//        return billerNamePart + subBiller + region + randomPart;
//    }
//}
//



import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class ApiService1 {

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
    private final OkHttpClient client = new OkHttpClient();

    private final String URL = "https://axis-bbps-uat.transxt.in/bbps-cou/v3.0/npciservice/fetch";

    public void sendRequest() throws Exception {
        // Define the fields
        Integer chId = 1;
        Boolean isRealTimeFetch = true;
        String mobileNo = "9004398093";
        String email = "mk.chekuri@gmail.com";
        String agentId = "AM01AM11BNK519046222";
        String initiatingChannel = "BSC";
        String agentMobile = "7878787123";
        String geocode = "28.6139,78.5555";
        String postalCode = "600001";
        String terminalId = "3451234560";
        String consumerMobileNo = "9865778954";
        String uid = "8596547893214111";
        String accountNo = "258931";

        // Generate compliant billerId
        String billerName = "OUA";
        String subBiller = "500000";
        String region = "PUN";
//        String billerId = generateBillerId(billerName, subBiller, region);
        String billerId ="OUA500000PUN01";

        // Create JSON objects
        JSONObject payload = new JSONObject();
        JSONObject custDetails = new JSONObject();
        JSONArray customerTags = new JSONArray();
        JSONObject emailTag = new JSONObject();
        JSONObject agentDetails = new JSONObject();
        JSONArray deviceTags = new JSONArray();
        JSONObject initiatingChannelTag = new JSONObject();
        JSONObject agentMobileTag = new JSONObject();
        JSONObject geocodeTag = new JSONObject();
        JSONObject postalCodeTag = new JSONObject();
        JSONObject terminalIdTag = new JSONObject();
        JSONObject billDetails = new JSONObject();
        JSONArray customerParams = new JSONArray();
        JSONObject consumerMobileNoParam = new JSONObject();
        JSONObject uidParam = new JSONObject();
        JSONObject accountNoParam = new JSONObject();

        // Populate customerTags
        emailTag.put("name", "EMAIL");
        emailTag.put("value", email);
        customerTags.put(emailTag);

        // Populate custDetails
        custDetails.put("mobileNo", mobileNo);
        custDetails.put("customerTags", customerTags);

        // Populate deviceTags
        initiatingChannelTag.put("name", "INITIATING_CHANNEL");
        initiatingChannelTag.put("value", initiatingChannel);
        deviceTags.put(initiatingChannelTag);

        agentMobileTag.put("name", "MOBILE");
        agentMobileTag.put("value", agentMobile);
        deviceTags.put(agentMobileTag);

        geocodeTag.put("name", "GEOCODE");
        geocodeTag.put("value", geocode);
        deviceTags.put(geocodeTag);

        postalCodeTag.put("name", "POSTAL_CODE");
        postalCodeTag.put("value", postalCode);
        deviceTags.put(postalCodeTag);

        terminalIdTag.put("name", "TERMINAL_ID");
        terminalIdTag.put("value", terminalId);
        deviceTags.put(terminalIdTag);

        // Populate agentDetails
        agentDetails.put("agentId", agentId);
        agentDetails.put("deviceTags", deviceTags);

        // Populate customerParams
        consumerMobileNoParam.put("name", "Consumer Mobile No");
        consumerMobileNoParam.put("value", consumerMobileNo);
        customerParams.put(consumerMobileNoParam);

        uidParam.put("name", "UID");
        uidParam.put("value", uid);
        customerParams.put(uidParam);

        accountNoParam.put("name", "Account No");
        accountNoParam.put("value", accountNo);
        customerParams.put(accountNoParam);

        // Populate billDetails
        billDetails.put("billerId", billerId);
        billDetails.put("customerParams", customerParams);

        // Populate payload
        payload.put("chId", chId);
        payload.put("isRealTimeFetch", isRealTimeFetch);
        payload.put("custDetails", custDetails);
        payload.put("agentDetails", agentDetails);
        payload.put("billDetails", billDetails);

        // Convert JSON object to string
        String jsonString = payload.toString();
        System.out.println("PayLoad ----->>>>  " + jsonString);

        // Encrypt agentId using AES
        String encryptedAgentId = encrypt(agentId, PublicKey);
        String authHeader = encryptedAgentId;

        // Encrypt JSON string using AES
        String encryptedJson = encrypt(jsonString, PrivateKey);

        // Create encrypted request payload
        JSONObject encryptedPayload = new JSONObject();
        encryptedPayload.put("request", encryptedJson);
        
        System.out.println("enc pyload----->>> "+encryptedPayload.toString());

        // Create RequestBody
        RequestBody body = RequestBody.create(encryptedPayload.toString(), MediaType.get("application/json; charset=utf-8"));

        // Create Request
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Auth", encryptedAgentId)
                .build();

        // Send Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println(response.body().string());
                } else {
                	 System.out.println(response.body().string());
                    System.out.println("Request failed: " + response.code());
                }
            }
        });
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

    private String generateBillerId(String billerName, String subBiller, String region) {
        String billerNamePart = billerName.length() >= 4 ? billerName.substring(0, 4) : String.format("%-4s", billerName).replace(' ', '0');
        String randomPart = String.format("%02d", new SecureRandom().nextInt(100));
        return billerNamePart + subBiller + region + randomPart;
    }
}

