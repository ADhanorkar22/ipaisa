package com.Ipaisa.axis;


import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncryptionService {

    private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
    private static final String UTF8 = "UTF-8";

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
        
   
    public String encryptAgentIdWithPublicKey(String agentId, String publicKey) throws Exception {
        return encrypt(agentId, publicKey);
    }
    
   
    public String encryptRequestBodyWithPrivateKey(String requestBody, String privateKey) throws Exception {
        return encrypt(requestBody, privateKey);
    }
    
    
}
