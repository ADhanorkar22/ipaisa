//package com.Ipaisa.fina;
//
//import okhttp3.*;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//import com.Ipaisa.EncryptionDecryption.*;
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import com.Ipaisa.EncryptionDecryption.EncryptionUtil;
//import okhttp3.*;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//
//@Service
//public class SprintNxtService {
//	   @Value("${public.key.path}")
//	    private String publicKeyPath;
//	private final OkHttpClient client = new OkHttpClient();
//	
//	private final String partnerId = "NlRJUE5OUk";
//	private final String clientId = "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==";
//	// String filePat = EncryptionUtil.class.getClassLoader().getResource(publicKeyPath).getPath();
//	 private static final String PUBLIC_KEY_PATH = "public_key.pem";
//	
//	
//	  public static PublicKey loadPublicKey() throws Exception {
//	        ClassPathResource resource = new ClassPathResource(PUBLIC_KEY_PATH);
//	        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
//	        String publicKeyPEM = new String(keyBytes)
//	                .replace("-----BEGIN PUBLIC KEY-----", "")
//	                .replace("-----END PUBLIC KEY-----", "")
//	                .replaceAll("\\s", "");
//	        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
//	        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	        System.out.println(keyFactory.generatePublic(spec).toString());
//	        return keyFactory.generatePublic(spec);
//	    }
//
//	public String encryptAndSendData(String payload) throws Exception {
//	    String encryptedPayload = encryptPayload(payload);
//	    return sendEncryptedData(encryptedPayload);
//	}
//
//	private String encryptPayload(String payload) throws Exception {
//	    // Generate AES key
//	    SecretKey secretKey = generateAESKey();
//
//	    // Encrypt payload with AES
//	    byte[] ivBytes = generateIV();
//	    byte[] encryptedPayload = encryptAES(payload.getBytes(), secretKey, ivBytes);
//
//	    // Encrypt AES key with RSA public key
//	    String encryptedKey = encryptAESKey(secretKey);
//	    
//	    
//
//	    // Encode to Base64
//	    String encodedPayload = Base64.getEncoder().encodeToString(encryptedPayload);
//
//	    // Construct JSON with encrypted payload, key, partnerId, and clientId
//	    return "{\"body\":{\"payload\":\"" + encodedPayload + "\",\"key\":\"" + encryptedKey + "\",\"partnerId\":\"" + partnerId + "\",\"clientid\":\"" + encodeClientId(clientId) + "\"}}";
//	}
//
//	private String sendEncryptedData(String encryptedPayload) throws Exception {
//	    MediaType mediaType = MediaType.parse("application/json");
//	    RequestBody body = RequestBody.create(mediaType, encryptedPayload);
//
//	    String encryptedKey = encryptAESKey(generateAESKey());
//	    
//
//
//	    String key = encryptedKey + "\", \"partnerId\": \"NlRJUE5OUk\", \"clientid\": \"U1BSX05YVF91YXRfNTFjZGViNWE5ZWJiMjBmNQ==\"";
//
//	   System.out.println(key);
//	   
////	   byte[] encryptedPartnerId = rsaCipher.doFinal(partnerId.getBytes(StandardCharsets.UTF_8));
////       String encryptedPartnerIdBase64 = Base64.getEncoder().encodeToString(encryptedPartnerId);
//
//	    Request request = new Request.Builder()
//	            .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//	            .post(body)
//	            .addHeader("accept", "application/json")
//	            .addHeader("partnerId", partnerId)
//	            .addHeader("client-id", clientId)
//	            .addHeader("key", key)
//	            .addHeader("content-type", "application/json")
//	            .build();
//
//	    Response response = client.newCall(request).execute();
//	    return response.body().string();
//	}
//
//	private SecretKey generateAESKey() throws Exception {
//	    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//	    keyGen.init(256, new SecureRandom());
//	    return keyGen.generateKey();
//	}
//
//	private byte[] generateIV() {
//	    byte[] iv = new byte[16];
//	    new SecureRandom().nextBytes(iv);
//	    return iv;
//	}
//
//	private byte[] encryptAES(byte[] data, SecretKey secretKey, byte[] ivBytes) throws Exception {
//	    Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//	    aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
//	    return aesCipher.doFinal(data);
//	}
//
//	private String encryptAESKey(SecretKey secretKey) throws Exception {
//	    PublicKey publicKey = loadPublicKey();
//	    Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//	    rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//	    return Base64.getEncoder().encodeToString(rsaCipher.doFinal(secretKey.getEncoded()));
//	}
//
////	private PublicKey getPublicKey() throws Exception {
////	    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
////	    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
////	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
////	    return keyFactory.generatePublic(keySpec);
////	}
//
//	private String encryptPartnerId(String partnerId) throws Exception {
//	    PublicKey publicKey = loadPublicKey();
//	    Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//	    rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//	    return Base64.getEncoder().encodeToString(rsaCipher.doFinal(partnerId.getBytes()));
//	}
//
//	private String encodeClientId(String clientId) {
//	    return Base64.getEncoder().encodeToString(clientId.getBytes());
//	}
//}

package com.Ipaisa.fina;
//
//import okhttp3.*;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//import com.Ipaisa.EncryptionDecryption.*;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//
//@Service
//public class SprintNxtService {
//    @Value("${public.key.path}")
//    private String publicKeyPath;
//
//    private final OkHttpClient client = new OkHttpClient();
//    private final String partnerId = "NlRJUE5OUk";
//    private final String clientId = "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==";
//    private static final String PUBLIC_KEY_PATH = "public_key.pem";
//
//    public static PublicKey loadPublicKey() throws Exception {
//        ClassPathResource resource = new ClassPathResource(PUBLIC_KEY_PATH);
//        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
//        String publicKeyPEM = new String(keyBytes)
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s", "");
//        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(spec);
//    }
//
//    public String encryptAndSendData(String payload) throws Exception {
//        String encryptedPayload = encryptPayload(payload);
//        return sendEncryptedData(encryptedPayload);
//    }
//
//    private String encryptPayload(String payload) throws Exception {
//        // Generate AES key
//        SecretKey secretKey = generateAESKey();
//
//        // Encrypt payload with AES (No IV needed for ECB mode)
//        byte[] encryptedPayload = encryptAES(payload.getBytes(), secretKey);
//
//        // Encrypt AES key with RSA public key
//        String encryptedKey = encryptAESKey(secretKey);
//
//        // Encode to Base64
//        String encodedPayload = Base64.getEncoder().encodeToString(encryptedPayload);
//
//        // Construct JSON with encrypted payload, key, partnerId, and clientId
//        return "{\"body\":{\"payload\":\"" + encodedPayload + "\",\"key\":\"" + encryptedKey + "\",\"partnerId\":\"" + partnerId + "\",\"clientid\":\"" + encodeClientId(clientId) + "\"}}";
//    }
//
//    private String sendEncryptedData(String encryptedPayload) throws Exception {
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, encryptedPayload);
//
//        // Encrypt partnerId for the header
//        String encryptedPartnerId = encryptPartnerId(partnerId);
//
//        Request request = new Request.Builder()
//                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("partnerId", encryptedPartnerId)
//                .addHeader("client-id", clientId)
//                .addHeader("key", encryptedPayload) // Only the encrypted key should be added
//                .addHeader("content-type", "application/json")
//                .build();
//
//        Response response = client.newCall(request).execute();
//        return response.body().string();
//    }
//
//    private SecretKey generateAESKey() throws Exception {
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(256, new SecureRandom());
//        return keyGen.generateKey();
//    }
//
//    private byte[] encryptAES(byte[] data, SecretKey secretKey) throws Exception {
//        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Corrected mode
//        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        return aesCipher.doFinal(data);
//    }
//
//    private String encryptAESKey(SecretKey secretKey) throws Exception {
//        PublicKey publicKey = loadPublicKey();
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        return Base64.getEncoder().encodeToString(rsaCipher.doFinal(secretKey.getEncoded()));
//    }
//
//    private String encryptPartnerId(String partnerId) throws Exception {
//        PublicKey publicKey = loadPublicKey();
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        return Base64.getEncoder().encodeToString(rsaCipher.doFinal(partnerId.getBytes()));
//    }
//
//    private String encodeClientId(String clientId) {
//        return Base64.getEncoder().encodeToString(clientId.getBytes());
//    }
//}


//////////////////////////////////////2nd commit/////////////////////////////////////////
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.*;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import okio.Buffer;
//
//import java.io.IOException;
//
//@Service
//public class SprintNxtService {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Value("${public.key.path}")
//    private String publicKeyPath;
//
//    private final OkHttpClient client = new OkHttpClient();
//    private final String partnerId = "NlRJUE5OUk";
//    private final String clientId = "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==";
//
//    public SprintNxtService() throws Exception {
//    }
//
//
//
//    public  PublicKey loadPublicKey() throws Exception {
//        ClassPathResource resource = new ClassPathResource(publicKeyPath);
//        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
//        String publicKeyPEM = new String(keyBytes)
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s", "");
//        System.out.println(publicKeyPEM);
//        System.out.println();
//        System.out.println();
//        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(spec);
//    }
//    SecretKey aesKey = generateAESKey();
//
//    private SecretKey generateAESKey() throws Exception {
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(256, new SecureRandom());
//        return keyGen.generateKey();
//    }
//
//
//    String encryptedAESKeyBase64 = encryptAESKeyWithRSAPublicKey(aesKey, loadPublicKey());
//
//    public static String encryptAESKeyWithRSAPublicKey(SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
//        byte[] encryptedKey = rsaCipher.doFinal(aesKey.getEncoded());
//        return Base64.getEncoder().encodeToString(encryptedKey);
//    }
//
//    public String encryptAndSendData(String payload) throws Exception {
//        String encryptedPayload = encryptPayload(payload);
//        String encodedAESKey = Base64.getEncoder().encodeToString(aesKey.getEncoded());
//        System.out.println("AES Key (Base64 Encoded): " + encodedAESKey);
//        System.out.println("encryptedAESKeyBase64==>"+encryptedAESKeyBase64);
//
//        System.out.println("finally here");
//        return sendEncryptedData(encryptedPayload);
//    }
//
//
//
//    private String encryptPayload(String payload) throws Exception {
//
//        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
//        byte[] encryptedData = aesCipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));
//        String encodedData = Base64.getEncoder().encodeToString(encryptedData);
//        // Construct JSON with encrypted payload, key, partnerId, and clientId
//        System.out.println();
//        return "{\"body\":{\"payload\":\"" + encodedData + "\",\"key\":\"" + encryptedAESKeyBase64 + "\",\"partnerId\":\"" + partnerId + "\",\"clientid\":\"" + clientId + "\"}}";
//    }                                                                                                                                                  //encodeClientId(clientId)
//
//    private String sendEncryptedData(String encryptedPayload) throws Exception {
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, encryptedPayload);
//
//
//        Request request = new Request.Builder()
//                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("partnerId", partnerId)
//                .addHeader("client-id", clientId)
//                .addHeader("key", encryptedAESKeyBase64) // Only the encrypted key should be added
//                .addHeader("content-type", "application/json")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("Response Code: " + response.code());
//            StringBuilder requestDetails = new StringBuilder();
//            requestDetails.append("URL: ").append(request.url()).append("\n");
//            requestDetails.append("Method: ").append(request.method()).append("\n");
//            requestDetails.append("Headers: ").append("\n");
//
//            // Print all headers
//            Headers headers = request.headers();
//            System.out.println(headers.size());
//            for (int i = 0; i < headers.size(); i++) {
//
//                requestDetails.append(headers.name(i)).append(": ").append(headers.value(i)).append("\n");
//            }
//
//            // Print the body if present
//            RequestBody requestBody = request.body();
//            if (requestBody != null) {
//                Buffer buffer = new Buffer();
//                try {
//                    requestBody.writeTo(buffer);
//                    requestDetails.append("Body: ").append(buffer.readUtf8()).append("\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            System.out.println(requestDetails.toString());
//            String responseBody = response.body().string();
//            System.out.println("Response Body: " + responseBody);
//            return responseBody;
//        }
//    }

//////////////////////////////////////////////////////end.///////////////////////////////////

//    private byte[] encryptAES(byte[] data, SecretKey secretKey) throws Exception {
//        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Corrected mode
//        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        return aesCipher.doFinal(data);
//    }
//
//    private String encryptAESKey(SecretKey secretKey) throws Exception {
//        PublicKey publicKey = loadPublicKey();
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        return Base64.getEncoder().encodeToString(rsaCipher.doFinal(secretKey.getEncoded()));
//    }
//
//    private String encryptPartnerId(String partnerId) throws Exception {
//        PublicKey publicKey = loadPublicKey();
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        return Base64.getEncoder().encodeToString(rsaCipher.doFinal(partnerId.getBytes(StandardCharsets.UTF_8)));
//    }
//
//    private String encodeClientId(String clientId) {
//        return Base64.getEncoder().encodeToString(clientId.getBytes(StandardCharsets.UTF_8));
//    }



////////////////////////////////////////////////////////////////8-07-2024///////////////////////////////////////////////


//////////////////////////////////////////below 1st final//////////////////////////////////////////
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.*;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//     import com.fasterxml.jackson.databind.JsonNode;
//     import com.fasterxml.jackson.databind.ObjectMapper;
//
//import okio.Buffer;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//     import java.util.logging.Logger;
//
//@Service
//public class SprintNxtService {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Value("${public.key.path}")
//    private String publicKeyPath;
//    private static final Logger LOGGER = Logger.getLogger(SprintNxtService.class.getName());
//
//    private final OkHttpClient client = new OkHttpClient();
//    private final String partnerId = "NlRJUE5OUk";
//    private final String clientId = "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==";
//    String encryptedAESKeyBase64;
//
//    private PublicKey loadPublicKey() throws Exception {
//        ClassPathResource resource = new ClassPathResource(publicKeyPath);
//        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
//        String publicKeyPEM = new String(keyBytes)
//
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s", "");
//        System.out.println(publicKeyPEM);
//        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(spec);
//    }
//
//    private SecretKey generateAESKey() throws Exception {
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(256, new SecureRandom());
//        return keyGen.generateKey();
//    }
//
//    private String encryptAESKeyWithRSAPublicKey(SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
//        byte[] encryptedKey = rsaCipher.doFinal(aesKey.getEncoded());
//        return Base64.getEncoder().encodeToString(encryptedKey);
//    }
//
//    public String encryptAndSendData(String payload) throws Exception {
//        SecretKey aesKey = generateAESKey();
//        PublicKey publicKey = loadPublicKey();
//        encryptedAESKeyBase64 = encryptAESKeyWithRSAPublicKey(aesKey, publicKey);
//        String encryptedPayload = encryptPayload(payload, aesKey);
//
//        System.out.println("AES Key (Base64 Encoded): " + Base64.getEncoder().encodeToString(aesKey.getEncoded()));
//        System.out.println("Encrypted AES Key (Base64 Encoded): " + encryptedAESKeyBase64);
//        return sendEncryptedData(encryptedPayload);
//    }
//
//    private String encryptPayload(String payload, SecretKey aesKey) throws Exception {
//        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
//        byte[] encryptedData = aesCipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));
//        String encodedData = Base64.getEncoder().encodeToString(encryptedData);
//System.out.println("sjnkjsanjks======>"+encodedData);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("payload", encodedData );
//        body.put("key",encryptedAESKeyBase64 );
//        body.put("partnerId", partnerId);
//        body.put("clientid", clientId);
//
//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("body", body);
//        String json = objectMapper.writeValueAsString(jsonMap);
//        System.out.println(json);
//        return  json;//"{\"body\":{\"payload\":\"" + encodedData + "\",\"key\":\"" + encryptedAESKeyBase64 + "\",\"partnerId\":\"" + partnerId + "\",\"clientid\":\"" + clientId + "\"}}";
//    }
//
//    private String sendEncryptedData(String encryptedPayload) throws Exception {
//        //MediaType JSON = MediaType.get("application/json; charset=utf-8");
//        System.out.println(encryptedPayload);
//        MediaType mediaType = MediaType.parse("application/hal+json");
//       // RequestBody body = RequestBody.create(mediaType, encryptedPayload);
//        RequestBody body = RequestBody.create(mediaType,encryptedPayload);
////        String keya = String.format("{\"key\": \"%s\", \"partnerId\": \"%s\", \"clientid\": \"%s\"}",
////                encryptedAESKeyBase64, partnerId, clientId);
//        Request request = new Request.Builder()
//                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("partnerId", partnerId)
//                .addHeader("client-id", clientId)
//                .addHeader("key", encryptedAESKeyBase64) // Add encrypted payload as key
//                .addHeader("content-type", "application/hal+json")
//                .build();
//
//        //  try (Response response = client.newCall(request).execute()) {
//
//       // System.out.println(keya);
////
////    System.out.println(response);
////            String responseBodyString = response.body().string();
////            System.out.println("Response Code: " + response.code());
////            System.out.println("Response Body: " + responseBodyString);
////
////            logRequestAndResponseDetails(request, responseBodyString);
////
////            JsonNode jsonResponse = objectMapper.readTree(responseBodyString);
////
////            // Example: Accessing fields in JSON response
////            JsonNode statusNode = jsonResponse.get("status");
////            String status = (statusNode != null && !statusNode.isNull()) ? statusNode.asText() : "Unknown";
////            System.out.println("Status: " + status);
////
////            // Process further as needed
////
////            return responseBodyString; // Or return processed data
////        } catch (IOException e) {
////            // Handle connection or IO errors
////            e.printStackTrace();
////            throw e; // or handle as needed
////        }
//        try (Response response = client.newCall(request).execute()) {
//            // Log response content type
//
//            String responseContentType = response.header("Content-Type");
//            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);
//
//            // Log response body
//            String responseBody = response.body().string();
//            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);
//
//            return responseBody;
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
//            return null;
//        }
//    }
//}

/////////////////////////////////////////////above 1 final/..////////////////////////////////
//    private void logRequestAndResponseDetails(Request request, String responseBody) {
//        StringBuilder requestDetails = new StringBuilder();
//        requestDetails.append("URL: ").append(request.url()).append("\n");
//        requestDetails.append("Method: ").append(request.method()).append("\n");
//        requestDetails.append("Headers: ").append("\n");
//
//        Headers headers = request.headers();
//        for (int i = 0; i < headers.size(); i++) {
//            requestDetails.append(headers.name(i)).append(": ").append(headers.value(i)).append("\n");
//        }
//
//        RequestBody requestBody = request.body();
//        if (requestBody != null) {
//            try {
//                Buffer buffer = new Buffer();
//                requestBody.writeTo(buffer);
//                requestDetails.append("Body: ").append(buffer.readUtf8()).append("\n");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("Request Details:\n" + requestDetails.toString());
//        System.out.println("Response Body:\n" + responseBody);
//    }
/////////////////////////////////////////////////////////////////////////////End//////////////////////////////////////


///////////////////////////////////////////222222222222222
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.*;
//        import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import okio.Buffer;
//import java.io.IOException;
//
//@Service
//public class SprintNxtService {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Value("${public.key.path}")
//    private String publicKeyPath;
//    private static final Logger LOGGER = Logger.getLogger(SprintNxtService.class.getName());
//
//    private final OkHttpClient client = new OkHttpClient();
//    private final String partnerId = "NlRJUE5OUk";
//    private final String clientId = "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==";
//    private String encryptedAESKeyBase64;
//
//    private PublicKey loadPublicKey() throws Exception {
//        ClassPathResource resource = new ClassPathResource(publicKeyPath);
//        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
//        String publicKeyPEM = new String(keyBytes)
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s", "");
//        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(spec);
//    }
//
//    private SecretKey generateAESKey() throws Exception {
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(256, new SecureRandom());
//        return keyGen.generateKey();
//    }
//
//    private String encryptAESKeyWithRSAPublicKey(SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
//        byte[] encryptedKey = rsaCipher.doFinal(aesKey.getEncoded());
//        return Base64.getEncoder().encodeToString(encryptedKey);
//    }
//
//    private String encryptDataWithAES(String data, SecretKey aesKey, IvParameterSpec iv) throws Exception {
//        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, iv);
//        byte[] encryptedData = aesCipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
//        return Base64.getEncoder().encodeToString(encryptedData);
//    }
//
//    private String encryptPartnerIdWithRSAPublicKey(String partnerId, PublicKey rsaPublicKey) throws Exception {
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
//        byte[] encryptedPartnerId = rsaCipher.doFinal(partnerId.getBytes(StandardCharsets.UTF_8));
//        return Base64.getEncoder().encodeToString(encryptedPartnerId);
//    }
//
//    public String encryptAndSendData(String payload) throws Exception {
//        SecretKey aesKey = generateAESKey();
//        PublicKey publicKey = loadPublicKey();
//        encryptedAESKeyBase64 = encryptAESKeyWithRSAPublicKey(aesKey, publicKey);
//
//        IvParameterSpec iv = new IvParameterSpec(new byte[16]); // 32-byte IV
//        String encryptedPayload = encryptDataWithAES(payload, aesKey, iv);
//        String encryptedPartnerId = encryptPartnerIdWithRSAPublicKey(partnerId, publicKey);
//
//        return sendEncryptedData(encryptedPayload, encryptedAESKeyBase64);
//    }
//
//    private String sendEncryptedData(String encryptedPayload, String encryptedAESKeyBase64) throws Exception {
//        MediaType mediaType = MediaType.parse("application/json");
//        //String partnerIdHeader = encryptStringWithRSAPublicKey(partnerId, loadPublicKey());
//
//        // Construct the JSON payload to send
//        String jsonPayload = "{\"body\":{\"payload\":\"" + encryptedPayload + "\",\"key\":\"" + encryptedAESKeyBase64 + "\",\"partnerId\":\"" + partnerId + "\",\"clientid\":\"" + clientId + "\"}}";
//
//        RequestBody body = RequestBody.create(mediaType, jsonPayload);
//        String keyHeader = encryptedAESKeyBase64;
////        Request request;
//        String keya = String.format("{\"key\": \"%s\", \"partnerId\": \"%s\", \"clientid\": \"%s\"}",
//                keyHeader, partnerId, clientId);
//                System.out.println(keya);
//
//
//        Request  request = new Request.Builder()
//                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("partnerId", partnerId)
//                .addHeader("client-id", clientId)
//                .addHeader("key", keyHeader)
//                .addHeader("content-type", "application/hal+json") // Ensure content type is correct
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("Response Code: " + response.code());
//            StringBuilder requestDetails = new StringBuilder();
//            requestDetails.append("URL: ").append(request.url()).append("\n");
//            requestDetails.append("Method: ").append(request.method()).append("\n");
//            requestDetails.append("Headers: ").append("\n");
//
//            // Print all headers
//            Headers headers = request.headers();
//            System.out.println(headers.size());
//            for (int i = 0; i < headers.size(); i++) {
//
//                requestDetails.append(headers.name(i)).append(": ").append(headers.value(i)).append("\n");
//            }
//
//            // Print the body if present
//            RequestBody requestBody = request.body();
//            if (requestBody != null) {
//                Buffer buffer = new Buffer();
//                try {
//                    requestBody.writeTo(buffer);
//                    requestDetails.append("Body: ").append(buffer.readUtf8()).append("\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            System.out.println(requestDetails.toString());
//            String responseBody = response.body().string();
//            System.out.println("Response Body: " + responseBody);
//            String responseContentType = response.header("Content-Type");
//            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);
//
//            String responseBod = response.body().string();
//            LOGGER.log(Level.INFO, "Response Body: {0}", responseBod);
//
//            return responseBody;
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
//            return null;
//        }
//    }
//}
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.*;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.Cipher;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.io.IOExc
//eption;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Service
//public class SprintNxtService {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Value("${public.key.path}")
//    private String publicKeyPath;
//
//    private static final Logger LOGGER = Logger.getLogger(SprintNxtService.class.getName());
//    private final OkHttpClient client = new OkHttpClient();
//    private final String partnerId = "NlRJUE5OUk";
//    private final String clientId = "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==";
//
//    private PublicKey loadPublicKey() throws Exception {
//        ClassPathResource resource = new ClassPathResource(publicKeyPath);
//        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
//        String publicKeyPEM = new String(keyBytes)
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s", "");
//        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(spec);
//    }
//
//    private byte[] generateAESKey() throws Exception {
//        byte[] key = new byte[32]; // 32 bytes = 256 bits
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(key);
//        return key;
//    }
//
//    private String encryptAESKeyWithRSAPublicKey(byte[] aesKey, PublicKey rsaPublicKey) throws Exception {
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
//        byte[] encryptedKey = rsaCipher.doFinal(aesKey);
//        return Base64.getEncoder().encodeToString(encryptedKey);
//    }
//
//    public String encryptAndSendData(String payload) throws Exception {
//        byte[] aesKey = generateAESKey();
//        PublicKey publicKey = loadPublicKey();
//        String encryptedAESKeyBase64 = encryptAESKeyWithRSAPublicKey(aesKey, publicKey);
//        String encryptedPayload = encryptPayload(payload, aesKey, encryptedAESKeyBase64);
//
//        System.out.println("Encrypted AES Key (Base64 Encoded): " + encryptedAESKeyBase64);
//        return sendEncryptedData(encryptedPayload, encryptedAESKeyBase64);
//    }
//
//    private String encryptPayload(String payload, byte[] aesKey, String encryptedAESKeyBase64) throws Exception {
//        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        SecretKeySpec aesKeySpec = new SecretKeySpec(aesKey, "AES");
//        String aesKeyBase64 = Base64.getEncoder().encodeToString(aesKey);
//        System.out.println("AES Key (Base64): " + aesKeyBase64);
//        aesCipher.init(Cipher.ENCRYPT_MODE, aesKeySpec);
//        byte[] encryptedData = aesCipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));
//        String encodedData = Base64.getEncoder().encodeToString(encryptedData);
//        System.out.println(encodedData);
//
//        return "{\"body\":{\"payload\":\"" + encodedData + "\",\"key\":\"" + encryptedAESKeyBase64 + "\",\"partnerId\":\"" + partnerId + "\",\"clientid\":\"" + clientId + "\"}}";
//    }
//
//    private String sendEncryptedData(String encryptedPayload, String encryptedAESKeyBase64) throws Exception {
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, encryptedPayload);
//        Request request = new Request.Builder()
//                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("partnerId", partnerId)
//                .addHeader("client-id", clientId)
//                .addHeader("key", encryptedAESKeyBase64)
//                .addHeader("content-type", "application/hal+json")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            String responseContentType = response.header("Content-Type");
//            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);
//
//            String responseBody = response.body().string();
//            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);
//
//            return responseBody;
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
//            return null;
//        }
//    }
//}
//
//
//
////    private void logRequestAndResponseDetails(Request request, String responseBody) {
////        StringBuilder requestDetails = new StringBuilder();
////        requestDetails.append("URL: ").append(request.url()).append("\n");
////        requestDetails.append("Method: ").append(request.method()).append("\n");
////        requestDetails.append("Headers: ").append("\n");
////
////        Headers headers = request.headers();
////        for (int i = 0; i < headers.size(); i++) {
////            requestDetails.append(headers.name(i)).append(": ").append(headers.value(i)).append("\n");
////        }
////
////        RequestBody requestBody = request.body();
////        if (requestBody != null) {
////            try {
////                Buffer buffer = new Buffer();
////                requestBody.writeTo(buffer);
////                requestDetails.append("Body: ").append(buffer.readUtf8()).append("\n");
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
////
////        System.out.println("Request Details:\n" + requestDetails.toString());
////        System.out.println("Response Body:\n" + responseBody);
////    }
////}
//        }
///////////////////////////////////////////final belowwwww/////////////////////////////////////////







//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.*;
//import org.apache.commons.io.IOUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Service;
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.SecureRandom;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import com.fasterxml.jackson.databind.JsonNode;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
////import javax.crypto.Cipher;
////import javax.crypto.KeyGenerator;
////import javax.crypto.SecretKey;
//
//@Service
//public class SprintNxtService {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    @Value("${public.key.path}")
//    private String publicKeyPath;
//    @Value("${private.key.path}")
//    private String privateKeyPath;
//    private static final Logger LOGGER = Logger.getLogger(SprintNxtService.class.getName());
//
//    private final OkHttpClient client = new OkHttpClient();
//    private final String partnerId = "NlRJUE5OUk";
//    private final String clientId = "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==";
//    String encryptedAESKeyBase64;
//
//    private PublicKey loadPublicKey() throws Exception {
//        ClassPathResource resource = new ClassPathResource(publicKeyPath);
//        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
//        String publicKeyPEM = new String(keyBytes)
//                .replace("-----BEGIN PUBLIC KEY-----", "")
//                .replace("-----END PUBLIC KEY-----", "")
//                .replaceAll("\\s", "");
//        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePublic(spec);
//    }
//
//    private PrivateKey loadPrivateKey() throws Exception {
//        ClassPathResource resource = new ClassPathResource(privateKeyPath);
//        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
//        String privateKeyPEM = new String(keyBytes)
//                .replace("-----BEGIN PRIVATE KEY-----", "")
//                .replace("-----END PRIVATE KEY-----", "")
//                .replaceAll("\\s", "");
//        LOGGER.log(Level.INFO, "Private Key: {0}", privateKeyPEM);
//        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        return keyFactory.generatePrivate(spec);
//    }
//
//    private SecretKey generateAESKey() throws Exception {
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(256, new SecureRandom());
//        return keyGen.generateKey();
//    }
//
//    private String encryptAESKeyWithRSAPublicKey(SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
//        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
//        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
//        byte[] encryptedKey = rsaCipher.doFinal(aesKey.getEncoded());
//        return Base64.getEncoder().encodeToString(encryptedKey);
//    }
//
//    public String encryptAndSendData(String payload) throws Exception {
//        SecretKey aesKey = generateAESKey();
//        PublicKey publicKey = loadPublicKey();
//        encryptedAESKeyBase64 = encryptAESKeyWithRSAPublicKey(aesKey, publicKey);
//        String encryptedPayload = encryptPayload(payload, aesKey);
//
//        System.out.println("AES Key (Base64 Encoded): " + Base64.getEncoder().encodeToString(aesKey.getEncoded()));
//        System.out.println("Encrypted AES Key (Base64 Encoded): " + encryptedAESKeyBase64);
//        return sendEncryptedData(encryptedPayload);
//    }
//
//    private String encryptPayload(String payload, SecretKey aesKey) throws Exception {
//        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
//        byte[] encryptedData = aesCipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));
//        String encodedData = Base64.getEncoder().encodeToString(encryptedData);
//        System.out.println("sjnkjsanjks======>" + encodedData);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("payload", encodedData);
//        body.put("key", encryptedAESKeyBase64);
//        body.put("partnerId", partnerId);
//        body.put("clientid", clientId);
//
//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("body", body);
//        String json = objectMapper.writeValueAsString(jsonMap);
//        System.out.println(json);
//        return json;
//    }
//
//    private String sendEncryptedData(String encryptedPayload) throws Exception {
//        System.out.println(encryptedPayload);
//        MediaType mediaType = MediaType.parse("application/hal+json");
//        RequestBody body = RequestBody.create(mediaType, encryptedPayload);
//
//        Request request = new Request.Builder()
//                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("partnerId", partnerId)
//                .addHeader("client-id", clientId)
//                .addHeader("key", encryptedAESKeyBase64) // Add encrypted payload as key
//                .addHeader("content-type", "application/hal+json")
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            String responseContentType = response.header("Content-Type");
//            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);
//
//            String responseBody = response.body().string();
//            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);
//
//            // Decrypt the response body
//            return decryptResponseBody(responseBody);
//        } catch (IOException e) {
//            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
//            return null;
//        }
//    }
//
//    private String decryptResponseBody(String responseBody) throws Exception {
//        JsonNode jsonResponse = objectMapper.readTree(responseBody);
//        JsonNode bodyNode = jsonResponse.get("body");
//        if (bodyNode != null) {
//            String encryptedData = bodyNode.asText();
//            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
//
//            // Decrypt AES key with private key
//            PrivateKey privateKey = loadPrivateKey();
//
//            System.out.println(privateKey.getEncoded().toString());
//            byte[] encryptedAESKey = Base64.getDecoder().decode(encryptedAESKeyBase64);
//            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
//            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
//            byte[] decryptedAESKey = rsaCipher.doFinal(encryptedAESKey);
//
//            // Decrypt data with AES key
////            SecretKey aesKey = new SecretKeySpec(decryptedAESKey, 0, decryptedAESKey.length, "AES");
////            Cipher aesCipher = Cipher.getInstance("AES/ECB/NoPadding");
////            aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
////            byte[] decryptedData = aesCipher.doFinal(decodedData);
////
////            return new String(decryptedData, StandardCharsets.UTF_8);
//            Cipher aesCipher = Cipher.getInstance("AES/ECB/NoPadding");
//            SecretKeySpec secretKeySpec = new SecretKeySpec(decryptedAESKey, "AES");
//            aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
//            byte[] decryptedData = aesCipher.doFinal(decodedData);
//            return new String(decryptedData).trim();
//        }
//        return null;
//    }
//}


import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
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
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SprintNxtService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${public.key.path}")
    private String publicKeyPath;

    @Value("${private.key.path}")
    private String privateKeyPath;

    private static final Logger LOGGER = Logger.getLogger(SprintNxtService.class.getName());

    private final OkHttpClient client = new OkHttpClient();
    private final String partnerId = "NlRJUE5OUk";
    private final String clientId = "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==";
    private String encryptedAESKeyBase64;

    private PublicKey loadPublicKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(publicKeyPath);
        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
        String publicKeyPEM = new String(keyBytes)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        LOGGER.log(Level.INFO, "public Key: {0}", publicKeyPEM);
        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private PrivateKey loadPrivateKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(privateKeyPath);
        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
        String privateKeyPEM = new String(keyBytes)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        LOGGER.log(Level.INFO, "Private Key: {0}", privateKeyPEM);
        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    private SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        return keyGen.generateKey();
    }

    private String encryptAESKeyWithRSAPublicKey(SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] encryptedKey = rsaCipher.doFinal(aesKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedKey);
    }

    public String encryptAndSendData(String payload) throws Exception {
        SecretKey aesKey = generateAESKey();
        PublicKey publicKey = loadPublicKey();
        encryptedAESKeyBase64 = encryptAESKeyWithRSAPublicKey(aesKey, publicKey);
        String encryptedPayload = encryptPayload(payload, aesKey);

        System.out.println("AES Key (Base64 Encoded): " + Base64.getEncoder().encodeToString(aesKey.getEncoded()));
        System.out.println("Encrypted AES Key (Base64 Encoded): " + encryptedAESKeyBase64);
        return sendEncryptedData(encryptedPayload);
    }

    private String encryptPayload(String payload, SecretKey aesKey) throws Exception {
        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encryptedData = aesCipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        String encodedData = Base64.getEncoder().encodeToString(encryptedData);
        System.out.println("sjnkjsanjks======>" + encodedData);

        Map<String, Object> body = new HashMap<>();
        body.put("payload", encodedData);
        body.put("key", encryptedAESKeyBase64);
        body.put("partnerId", partnerId);
        body.put("clientid", clientId);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("body", body);
        String json = objectMapper.writeValueAsString(jsonMap);
        System.out.println(json);
        return json;
    }

    private String sendEncryptedData(String encryptedPayload) throws Exception {
        System.out.println(encryptedPayload);
        MediaType mediaType = MediaType.parse("application/hal+json");
        RequestBody body = RequestBody.create(mediaType, encryptedPayload);

        Request request = new Request.Builder()
                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("partnerId", partnerId)
                .addHeader("client-id", clientId)
                .addHeader("key", encryptedAESKeyBase64) // Add encrypted payload as key
                .addHeader("content-type", "application/hal+json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseContentType = response.header("Content-Type");
          //  response.headers().forEach(e->System.out.println(e.getFirst()));
            //LOGGER.log(Level.INFO, "header key Content-Type: {0}", response.headers().forEach(e->e.getFirst().toString()));
            LOGGER.log(Level.INFO, "Response Content-Type: {0}", responseContentType);

            Headers headers = response.headers();
            for (int i = 0, size = headers.size(); i < size; i++) {
                System.out.println(headers.size());
                LOGGER.log(Level.INFO, "{0}: {1} ashish key", new Object[]{headers.name(i), headers.value(i)});
            }

            String responseBody = response.body().string();
            LOGGER.log(Level.INFO, "Response Body: {0}", responseBody);

            // Decrypt the response body
            return decryptResponseBody(responseBody,response.header("key"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error during HTTP request", e);
            return null;
        }
    }

    private String decryptResponseBody(String responseBody ,String k) throws Exception {
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        JsonNode bodyNode = jsonResponse.get("body");
        byte[] decodedData=null;
        if (bodyNode != null) {
            String encryptedData = bodyNode.asText();
            decodedData = Base64.getDecoder().decode(encryptedData);
        }


        byte[] encryptedAESKey = Base64.getDecoder().decode(k);

        // Decrypt AES key with private key
        PrivateKey privateKey = loadPrivateKey();
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedAESKey = rsaCipher.doFinal(encryptedAESKey);

        // Decrypt data with AES key
        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(decryptedAESKey, "AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedData = aesCipher.doFinal(decodedData);
        return new String(decryptedData, StandardCharsets.UTF_8).trim();
    }

}

////////////////////////////////////////////////////////////////////////////////////////////////