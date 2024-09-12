package com.Ipaisa.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.Ipaisa.Entitys.Consent;
import com.Ipaisa.Entitys.InstantPayBody;
import com.Ipaisa.dto.AadhaarRequest;
import com.Ipaisa.fina.SprintNxtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class InstantAadhaarService implements LatitudeLongitude {
	

	 private static final Random RANDOM = new Random();
	 private static final AtomicLong COUNTER = new AtomicLong(1);
	
	
	  @Value("${instantpay.client_id}")
		private String Client_Id;
		
		@Value("${instantpay.client_secret}")
		private String Client_Secret;
		
		@Value("${instantpay.encryption_key}")
		private String Encryption_Key;
		  private final OkHttpClient client = new OkHttpClient();
		   private final ObjectMapper objectMapper = new ObjectMapper();
		
		 @Autowired
		    private HttpServletRequest request;
		 
		 @Autowired
		 private InstantPayBusinessLogic instatpayeny;
		 
		   String endpointIp = null;
		   private static final Logger LOGGER = Logger.getLogger(SprintNxtService.class.getName());
		
		
		 public String encryptAndSendData(AadhaarRequest payload) throws Exception {
			 
				
			 
			//System.out.println("encrypted aadhaar====>"+instatpayeny.encryptAadhaarNumber(payload.getAadhaarNumber(), Encryption_Key));
		        
		        System.out.println( Consent.valueOf("Y"));
		        
		        Map<String, Object> body = new HashMap<>();
		     
		        body.put("aadhaarNumber",instatpayeny.encryptAadhaarNumber(payload.getAadhaarNumber(), Encryption_Key) );
		        body.put("latitude", LatitudeLongitude.Latitde);
		        body.put("longitude", LatitudeLongitude.Longitude);
		      //  body.put("externalRef", payload.getExternalRef());
		        body.put("externalRef", generateTransactionId());
		        body.put("consent", Consent.valueOf("Y"));
		      
//		        
//		        Map<String, Object> finalbody = new HashMap<>();
//		        finalbody.put("data", body);
		        
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println("final body======>"+body.toString());
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        String data = objectMapper.writeValueAsString(body);
		        
		        return sendEncryptedData(data);
		    }
		  private String getClientIpAddress() {
		        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		        if (attributes != null) {
		            HttpServletRequest request = attributes.getRequest();
		            return request.getRemoteAddr();
		        }
		        return null;
		    }
		 
		 
		  private String sendEncryptedData(String data) throws Exception {
			  URL url = new URL("http://ifconfig.me/ip");
		    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
		    	con.setRequestMethod("GET");
		    	BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    	String endpointIp = in.readLine();
		    	in.close();
		    	System.out.println("Public IP Address: " + endpointIp);
			    System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println("final body======>"+data);
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
		        System.out.println();
			//  endpointIp= request.getRemoteAddr();
		     
		        MediaType mediaType = MediaType.parse("application/hal+json");
		        RequestBody body = RequestBody.create(mediaType, data);

		        Request request = new Request.Builder()
		                .url("https://api.instantpay.in/identity/verifyAadhaar")
		                .post(body)
		                .addHeader("accept", "application/json")
		                .addHeader("X-Ipay-Auth-Code", "1")
		                .addHeader("X-Ipay-Client-Id", Client_Id)
		                .addHeader("X-Ipay-Client-Secret", Client_Secret) // Add encrypted payload as key
		                .addHeader("X-Ipay-Endpoint-Ip",endpointIp)
		                .addHeader("content-type", "application/hal+json")
		                .build();

		        try (Response response = client.newCall(request).execute()) {
		            String responseContentType = response.header("Content-Type");
		    
		            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);

		            Headers headers = response.headers();
		            for (int i = 0, size = headers.size(); i < size; i++) {
		                System.out.println(headers.size());
		                LOGGER.log(Level.INFO, "{0}: {1} ashish key", new Object[]{headers.name(i), headers.value(i)});
		            }

		            String responseBody = response.body().string();
		            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);

		            // Decrypt the response body
		            return responseBody;
		        } catch (IOException e) {
		            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
		            return null;
		        }

}
		  
//		  private String getClientIpAddress(HttpServletRequest request) {
//		        String ipAddress = request.getHeader("X-Forwarded-For");
//		        
//		        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//		            ipAddress = request.getHeader("X-Real-IP");
//		        }
//		        
//		        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
//		            ipAddress = request.getRemoteAddr();
//		        }
//		        
//		        // If IP address is still not found, handle appropriately
//		        // (e.g., log warning, return default value)
//		        
//		        return ipAddress;
//		    }
		   public static String generateTransactionId() {
		        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		        int randomNum = 100000 + RANDOM.nextInt(900000); // 6-digit random number
		        long counterValue = COUNTER.getAndIncrement();
		        return timestamp + randomNum + counterValue;
		    }
}
