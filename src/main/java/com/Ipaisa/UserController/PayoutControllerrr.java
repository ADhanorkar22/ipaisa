package com.Ipaisa.UserController;
import okhttp3.RequestBody;
//import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

//@RestController("auth/a")
//@RequestMapping("auth/retailer")
public class PayoutControllerrr {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${public.key.path}")
    private String publicKeyPath;

    @Value("${private.key.path}")
    private String privateKeyPath;
    

    @PostMapping("/initiate")
    public Map<String, String> initiatePayout(@org.springframework.web.bind.annotation.RequestBody Map<String, Object> requestData) throws Exception {
        // Load keys from the resources folder
        PublicKey publicKey = loadPublicKey(publicKeyPath);
        PrivateKey privateKey = loadPrivateKey(privateKeyPath);

        // Generate AES key
        SecretKey aesKey = generateAESKey();

        // Encrypt payload with AES key using AES-256-ECB
        String payload = objectMapper.writeValueAsString(requestData);
        String encryptedPayload = encryptAES(payload, aesKey);

        // Encrypt AES key with RSA public key
        String aesKeyEncoded = Base64.getEncoder().encodeToString(aesKey.getEncoded());
        String encryptedKey = encryptRSA(aesKeyEncoded, publicKey);

        // Construct the request body
        Map<String, String> encryptedData = new HashMap<>();
        encryptedData.put("payload", encryptedPayload);
        encryptedData.put("key", encryptedKey);
        encryptedData.put("partnerId", "NlRJUE5OUk");
        encryptedData.put("clientid", "U1BSX05YVF91YXRfNTFjZGViNWE5ZWJiMjBmNQ==");

        // Prepare the request body
        MediaType mediaType = MediaType.parse("application/json");
        String jsonBody = objectMapper.writeValueAsString(encryptedData);
        RequestBody body = RequestBody.create(mediaType, jsonBody);
        

        // Build the request
        Request request = new Request.Builder()
                .url("https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT")
                .post((okhttp3.RequestBody) body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("partnerId", "NlRJUE5OUk")
                .addHeader("client-id", "U1BSX05YVF91YXRfOTc3YThmYmJiY2VmNjU4Nw==")
                .addHeader("key", encryptedKey)
                .build();

        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Parse response
            return objectMapper.readValue(response.body().string(), Map.class);
        }
    }

    private PublicKey loadPublicKey(String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private PrivateKey loadPrivateKey(String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    private String encryptAES(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        System.out.println("jsondataaa=====>"+Base64.getEncoder().encodeToString(encrypted));
        System.out.println(key.getEncoded());
        System.out.println(key.toString());
        System.out.println(key.getFormat());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private String encryptRSA(String data, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
