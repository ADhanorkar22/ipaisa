package com.Ipaisa.day_19;

import okhttp3.*;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PayoutService {

    private static final String UAT_URL = "https://uatnxtgen.sprintnxt.in/api/v1/payout/PAYOUT";

    @Autowired
    private EncryptionUtils encryptionUtils;

    @Value("${partner.id}")
    private String partnerId;

    @Value("${client.id}")
    private String clientId;

    public String sendPayoutRequest(PayoutRequest request) throws Exception {
        // Generate a symmetric key for AES encryption
        String symmetricKey = encryptionUtils.generateSymmetricKey();

        // Encrypt the payload using AES
        String encryptedPayload = encryptionUtils.encryptAES(request.getPayload(), symmetricKey);

        // Encrypt the symmetric key using RSA with the SprintNxt public key
        PublicKey sprintNxtPublicKey = encryptionUtils.getPublicKey("sprintnxt_public_key.cer");
        String encryptedKey = encryptionUtils.encryptRSA(symmetricKey, sprintNxtPublicKey);

        // Encrypt partnerId using RSA with the bank's public key
        PublicKey bankPublicKey = encryptionUtils.getPublicKey("bank_public_key.cer");
        String encryptedPartnerId = encryptionUtils.encryptRSA(partnerId, bankPublicKey);

        // Encode client ID
        String encodedClientId = Base64.getEncoder().encodeToString(clientId.getBytes(StandardCharsets.UTF_8));

        // Prepare the JSON body
        String requestBodyJson = String.format("{ \"body\": { \"payload\": \"%s\", \"key\": \"%s\", \"partnerId\": \"%s\", \"clientid\": \"%s\" } }",
                                               encryptedPayload, encryptedKey, encryptedPartnerId, encodedClientId);

        // Prepare the request body
        RequestBody requestBody = RequestBody.create(
            requestBodyJson, MediaType.get("application/json; charset=utf-8")
        );

        // Prepare the HTTP request with encrypted headers
        Request httpRequest = new Request.Builder()
                .url(UAT_URL)
                .post(requestBody)
                .addHeader("key", encryptedKey)
                .addHeader("partnerId", encryptedPartnerId)
                .addHeader("client-id", encodedClientId)
                .build();

        // Make the request using OkHttpClient
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(httpRequest).execute();

        // Return the response body as a string
        return response.body().string();
    }
}
