package com.Ipaisa.axis;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BillFetchService {
	

	private final String URL="https://axis-bbps-uat.transxt.in/bbps-cou/v3.0/npciservice/fetch";
	
	@Value("${axis.ai.id}")
	private String AIID;
	
	@Value("${axis.private.key}")
	private String PrivateKey;
	
	@Value("${axis.agent.id}")
	private String AgentId;
	
	@Value("${axis.public.key}")
	private String PublicKey;
	
	 private static final SecureRandom random = new SecureRandom();
	 private static final String INSTITUTIONNAME = "EDSOM FINTECH";
	 private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
	 private static final String UTF8 = "UTF-8";
	
	 
	 
	 public ResponseEntity<?> billfetch()
	 {
		 //agentId 
		 String agentId = generateAgentId(INSTITUTIONNAME);
		 
		 //Agent Id Encryption with public key for request header 
		 try {
			String ag_id_auth=encrypt(AgentId,PublicKey);
			String requestPayload=encrypt(AgentId,PublicKey);
			System.out.println("Agent Id Encryption with Public key-->> "+ag_id_auth);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		 
		 
		 System.out.println("Generated Agent ID: " + agentId);
                
         return ResponseEntity.ok(agentId);
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
	     
}
