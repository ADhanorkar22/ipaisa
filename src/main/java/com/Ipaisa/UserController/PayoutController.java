//package com.Ipaisa.UserController;
//
//import com.Ipaisa.EncryptionDecryption.EncryptionDecryption;
//import com.Ipaisa.EncryptionDecryption.EncryptionUtil;
//import com.Ipaisa.EncryptionDecryptionUtilityClass.EncryptionUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import org.apache.commons.codec.binary.Base64;
//import org.hibernate.internal.build.AllowSysOut;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import javax.crypto.SecretKey;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("auth/retailer")
//public class PayoutController {
//
//    private final OkHttpClient client = new OkHttpClient();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Value("${public.key.path}")
//    private String publicKeyPath;
//    @Value("${private.key.path}")
//    private String privateKeyPath;
//    @PostMapping("/initiate")
//   public Map<String, String> initiatePayout( ){
//	return null;
//    	}
//    }
//		   //@org.springframework.web.bind.annotation.RequestBody Map<String, Object> requestData) throws Exception {
//

    

    	//        // Load keys from the resources folder
//
//        PrivateKey privateKey = EncryptionUtil.loadPrivateKey(privateKeyPath);
//        System.out.println("here "+privateKey.toString());
//        String filePath = EncryptionUtil.class.getClassLoader().getResource(privateKeyPath).getPath();
//        String filePat = EncryptionUtil.class.getClassLoader().getResource(publicKeyPath).getPath();
//       // System.out.println("File path: " + filePath);
//        //System.out.println(filePat);
//        PublicKey publicKey = EncryptionUtil.getPemPublicKey(publicKeyPath);
//        System.out.println("here===> "+publicKey.getEncoded());
//        System.out.println("private===>	"+privateKey.getEncoded());
//        // Generate AES key
//        SecretKey aesKey = EncryptionUtils.generateAESKey();
//        System.out.println("aesKey==========>"+aesKey.toString());
//        // Encrypt payload with AES key
//        String payload = objectMapper.writeValueAsString(requestData);
//        String encryptedPayload = EncryptionUtil.encryptAES(payload, aesKey);
//        // Encrypt AES key with RSA public key
//        String aesKeyEncoded = Base64.encodeBase64String(aesKey.getEncoded());
//        String encryptedKey = EncryptionUtil.encryptRSA(aesKeyEncoded, publicKey);
//        System.out.println(encryptedKey);
////
//        String partnerId = "\"partnerId\":\"NlRJUE5OUk\"";
//        String clientId = "\"clientid\":\"U1BSX05YVF91YXRfNTFjZGViNWE5ZWJiMjBmNQ==\"";
//        String concatenatedString = encryptedKey + "," + partnerId + "," + clientId;
//     //  encryptedKey","partnerId": "NlRJUE5OUk",         "clientid": "U1BSX05YVF91YXRfNTFjZGViNWE5ZWJiMjBmNQ==
//       // System.out.println(concatenatedString);
//        // Construct the request body
//        Map<String, String> encryptedData = new HashMap<>();
//        encryptedData.put("payload", encryptedPayload);
//        encryptedData.put("key", encryptedKey);
//        encryptedData.put("partnerId", "NlRJUE5OUk");
//        encryptedData.put("clientid", "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==");
//        System.out.println();
//        System.out.println();
//        System.out.println(encryptedData.toString());
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        // Prepare the request body
//        MediaType mediaType = MediaType.parse("application/json");
//        String jsonBody = objectMapper.writeValueAsString(encryptedData);
//        RequestBody body = RequestBody.create(jsonBody, mediaType);
//        System.out.println();
//        System.out.println();
//        System.out.println(jsonBody);
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println(body.toString());
//        // Build the request
//        Request request = new Request.Builder()
//                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("partnerId", "NlRJUE5OUk")
//                .addHeader("client-id", "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==")
//                .addHeader("key", concatenatedString)
//                .addHeader("content-type", "application/json")
//                .build();
//        System.out.println("headers===>"+request.toString());
//        System.out.println();
//        System.out.println();
//        // Execute the request
//        Response response = client.newCall(request).execute();
//        System.out.println();
//        System.out.println();
//        System.out.println("jndsbhjbdskj  ====> "+response);
//        // Handle the response
//        if (response.isSuccessful()) {
//            // Decrypt the response
//            String responseBody = response.body().string();
//            System.out.println("String response=============> "+responseBody);
//          //  String decryptedResponse =EncryptionUtils.decryptAES(responseBody, aesKey);
//            String decryptedResponse =EncryptionUtils.decryptAES(responseBody, aesKey);
//            Map<String, String> result = new HashMap<>();
//            result.put("status", "success");
//            result.put("data", responseBody);
//            return result;
//        } else {
//            throw new RuntimeException("Failed to initiate payout: " + response.message());
//        }
//    }


