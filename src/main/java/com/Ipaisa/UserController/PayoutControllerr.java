package com.Ipaisa.UserController;
import com.Ipaisa.EncryptionDecryption.EncryptionDecryption;
import com.Ipaisa.EncryptionDecryption.EncryptionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.RequestBody;
import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth/reta")
public class PayoutControllerr {
}


  //  private final OkHttpClient client = new OkHttpClient();
    //private final ObjectMapper objectMapper = new ObjectMapper();
 //   private static final Logger logger = LoggerFactory.getLogger(PayoutController.class);
//
//    @Value("${public.key.path}")
//    private String publicKeyPath;
//
//    @Value("${private.key.path}")
//    private String privateKeyPath;

//    @PostMapping("/initiate")
//    public Map<String, String> //initiatePayout(@org.springframework.web.bind.annotation.RequestBody Map<String, Object> requestData) {
//      
//}
//}
//   
//            // Load keys from the resources folder
        //    PrivateKey privateKey = EncryptionUtil.loadPrivateKey(privateKeyPath);
          //  PublicKey publicKey = EncryptionUtil.getPemPublicKey(publicKeyPath);

            // Generate AES key
//            SecretKey aesKey = EncryptionUtil.generateAESKey();
//
//            // Encrypt payload with AES key
//            String payload = objectMapper.writeValueAsString(requestData);
//            String encryptedPayload = EncryptionUtil.encryptAES(payload, aesKey);
//
//            // Encrypt AES key with RSA public key
//            String aesKeyEncoded = Base64.encodeBase64String(aesKey.getEncoded());
//            String encryptedKey = EncryptionUtil.encryptRSA(aesKeyEncoded, publicKey);

            // Prepare the headers JSON object
           // Map<String, String> headersMap = new HashMap<>();
            //headersMap.put("partnerId", "NlRJUE5OUk");
//            headersMap.put("client-id", "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==");
//            headersMap.put("key", encryptedKey);
//            String headersJson = objectMapper.writeValueAsString(headersMap);
//            
//            System.out.println();
//            System.out.println();
//            System.out.println("encryptedKey=====>"+encryptedKey);
//            System.out.println();
//            System.out.println();
//            System.out.println("headersJson=====>"+headersJson);
//            System.out.println();
//            System.out.println();
//            
//            // Construct the request body
//            Map<String, String> requestBodyMap = new HashMap<>();
//            requestBodyMap.put("payload", encryptedPayload);
//            requestBodyMap.put("headers", headersJson);
//            String jsonBody = objectMapper.writeValueAsString(requestBodyMap);
//            
//            System.out.println();
//            System.out.println();
//            System.out.println("jsonBody==========>"+jsonBody);
//            System.out.println();
//            System.out.println();
//
//            // Prepare the request body
//            MediaType mediaType = MediaType.parse("application/json");
//            RequestBody body = RequestBody.create(jsonBody, mediaType);
//            
//            System.out.println("body=========>"+body);
//
//            // Build the request
//            Request request = new Request.Builder()
//                    .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//                    .post(body)
//                    .addHeader("content-type", "application/json")
//                    .build();
//            
//            System.out.println("======>"+request);
//
//            // Execute the request
//            try (Response response = client.newCall(request).execute()) {
//                // Handle the response
//                if (response.isSuccessful()) {
//                    // Decrypt the response if needed
//                    String responseBody = response.body().string();
//                    System.out.println();
//                    System.out.println();
//                    System.out.println("responseBody======>"+responseBody);
//                    System.out.println();
//                    System.out.println();
//                    
//                    // Process and return the response as needed
//                    // Example: return objectMapper.readValue(responseBody, Map.class);
//                } else {
//                    logger.error("Failed to initiate payout: {}", response.message());
//                    throw new RuntimeException("Failed to initiate payout: " + response.message());
//                }
//            }
//
//            // Handle other exceptions
//        } catch (Exception e) {
//            logger.error("Error initiating payout", e);
//            throw new RuntimeException("Error initiating payout: " + e.getMessage());
//        }
//
//        return null; // Or handle success response here
//    }
//}