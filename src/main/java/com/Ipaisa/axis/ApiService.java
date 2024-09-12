package com.Ipaisa.axis;

import okhttp3.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.security.SecureRandom;

@Service
public class ApiService {
	
	@Value("${axis.ai.id}")
	private String AIID;
	
	@Value("${axis.private.key}")
	private String PrivateKey;
	
	@Value("${axis.agent.id}")
	private String AgentId;
	
	@Value("${axis.public.key}")
	private String PublicKey;
	
	@Autowired
	private EncryptionService encryptDecrypt;
	
	 private static final SecureRandom random = new SecureRandom();
	 private static final String INSTITUTIONNAME = "EDSOM FINTECH";
	 private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
	 private static final String UTF8 = "UTF-8";

    private final OkHttpClient client = new OkHttpClient();

    public void sendRequest() {
        // Define the fields
        Integer chId = 1;
        Boolean isRealTimeFetch = true;
        String mobileNo = "9004398093";
        String email = "mk.chekuri@gmail.com";
//        String agentId = "AM01AM11BNK519046222";
        String agentId = generateAgentId(INSTITUTIONNAME);;
        
        String initiatingChannel = "BSC";
        String agentMobile = "7878787123";
        String geocode = "28.6139,78.5555";
        String postalCode = "600001";
        String terminalId = "3451234560";
        Integer billerId = 500000;
        String consumerMobileNo = "9865778954";
        String uid = "8596547893214111";
        String accountNo = "258931";

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
        
        String payloadwithEncrypt=null;
        //Encrypt PayLoad With private Key
        try {
        	payloadwithEncrypt=encryptDecrypt.encrypt(jsonString, PrivateKey);
			String pbody=payloadwithEncrypt.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Create RequestBody
        RequestBody body = RequestBody.create(payloadwithEncrypt, MediaType.get("application/json; charset=utf-8"));

        // Create Request
        Request request = new Request.Builder()
                .url("https://your.api.endpoint/here")
                .post(body)
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
                    System.out.println("Request failed: " + response.code());
                }
            }
        });
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
}
