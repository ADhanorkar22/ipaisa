package com.Ipaisa.Service;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.Ipaisa.Entitys.RazorPay;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.RazorpayClient;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okhttp3.*;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class RazorPayOutService {
	
	
	 private final OkHttpClient client = new OkHttpClient();
	    private final ObjectMapper objectMapper = new ObjectMapper();
	    private static final Logger LOGGER = Logger.getLogger(InstantPayService.class.getName());

	    public String encryptAndSendData(RazorPay razorpay) throws Exception {

	    	JSONObject payoutrequest=getjsonbody(razorpay);
	        return sendEncryptedData(payoutrequest);
	    }

	    private String sendEncryptedData(JSONObject data) throws Exception {
	    	  RazorpayClient razorpay = new RazorpayClient("rzp_test_rXEGx7MqXiTukn", "qrtvDb80SWFMAVe06V2r2MMF");
	    	  String idempotencyKey = UUID.randomUUID().toString();
	    	  String keyId = "rzp_test_rXEGx7MqXiTukn";
	            String keySecret = "qrtvDb80SWFMAVe06V2r2MMF";

	          
	            String credentials = keyId + ":" + keySecret;

	           
	            String base64EncodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
     
	        MediaType mediaType = MediaType.parse("application/hal+json");
	        RequestBody body = RequestBody.create(mediaType, data.toString());
	        Request request = new Request.Builder()
	                .url("https://api.razorpay.com/v1/payouts")
	                .post(body)
	                .addHeader("accept", "application/json")
	                .header("Idempotency-Key", idempotencyKey)
	                .header("Authorization", "Basic " + base64EncodedCredentials)
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
	
	
	public JSONObject getjsonbody(RazorPay razorpay) {
		 JSONObject payoutRequest = new JSONObject();
         payoutRequest.put("account_number", razorpay.getAccount_number());
         payoutRequest.put("fund_account_id", razorpay.getFund_account_id());
         payoutRequest.put("amount", razorpay.getAmount());
         payoutRequest.put("currency", razorpay.getCurrency());
         payoutRequest.put("mode", razorpay.getMode()); // UPI, IMPS, NEFT, RTGS
         payoutRequest.put("purpose", razorpay.getPurpose());
         payoutRequest.put("queue_if_low_balance", true);
         payoutRequest.put("reference_id", razorpay.getReference_id());
         payoutRequest.put("narration", "Payout for services");
         payoutRequest.put("notes", new JSONObject().put("note_key", "Note Value"));
        return  payoutRequest;
	}
	    
	    
	    

}
