package com.Ipaisa.EncryptionDecryption;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
public class EncryptionUtil {
	
	 private static final String PUBLIC_KEY_PATH = "public_key.pem";

	  
	  public EncryptedData encryptData(String data, String publicKeyPath) throws Exception {
	        SecretKey aesKey = generateAESKey();
	        byte[] encryptedData = encryptData(data.getBytes(), aesKey);
	        PublicKey publicKey = loadPublicKey();
	        byte[] encryptedAESKey = encryptAESKey(aesKey, publicKey);
	        return new EncryptedData(base64Encode(encryptedData), base64Encode(encryptedAESKey));
	    }
	  
	  

	    private SecretKey generateAESKey() throws Exception {
	        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	        keyGenerator.init(256);
	        return keyGenerator.generateKey();
	    }

	    private byte[] encryptData(byte[] data, SecretKey key) throws Exception {
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        return cipher.doFinal(data);
	    }

	    private byte[] encryptAESKey(SecretKey aesKey, PublicKey publicKey) throws Exception {
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        return cipher.doFinal(aesKey.getEncoded());
	    }

	    private String base64Encode(byte[] data) {
	        return Base64.getEncoder().encodeToString(data);
	    }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
//	 public static PrivateKey loadPrivateKey(String privateKeyPath) throws Exception {
//	        ClassPathResource resource = new ClassPathResource(privateKeyPath);
//	        InputStream inputStream = resource.getInputStream();
//	        byte[] keyBytes = inputStream.readAllBytes();
//	        inputStream.close();
//	        // Decode the key bytes
//	        String privateKeyContent = new String(keyBytes);
//	        privateKeyContent = privateKeyContent
//	                .replaceAll("\\n", "")
//	                .replace("-----BEGIN PRIVATE KEY-----", "")
//	                .replace("-----END PRIVATE KEY-----", "");
//	        byte[] decodedKey = Base64.decodeBase64(privateKeyContent);
//	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
//	        System.out.println(KeyFactory.getInstance("RSA").generatePrivate(keySpec));
//	        // Initialize PrivateKey
//	        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
//	 }
    // Load RSA private key from resource
//    public static PrivateKey loadPrivateKeyFromResource(String resourcePath) throws Exception {
//        try (InputStream inputStream = EncryptionUtil.class.getClassLoader().getResourceAsStream(resourcePath)) {
//            if (inputStream == null) {
//                throw new IllegalArgumentException("Resource not found: " + resourcePath);
//            }
//            byte[] keyBytes = inputStream.readAllBytes();
//            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePrivate(spec);
//        }
//    }
	
	 ////
//	 public static PublicKey getPemPublicKey(String filename) throws Exception {
//		    InputStream inputStream = EncryptionUtil.class.getClassLoader().getResourceAsStream(filename);
//		    if (inputStream == null) {
//		        throw new IllegalArgumentException("File not found: " + filename);
//		    }
//		    byte[] keyBytes = inputStream.readAllBytes();
//		    inputStream.close();
//		    // Decode the key bytes
//		    String publicKeyPEM = new String(keyBytes);
//		    publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "")
//		                               .replace("-----END PUBLIC KEY-----", "");
//		   
//		    byte[] decodedKey = Base64.decodeBase64(publicKeyPEM);
//		    X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
//		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		    return keyFactory.generatePublic(spec);
//		}
	 
	  public static PublicKey loadPublicKey() throws Exception {
	        ClassPathResource resource = new ClassPathResource(PUBLIC_KEY_PATH);
	        byte[] keyBytes = IOUtils.toByteArray(resource.getInputStream());
	        String publicKeyPEM = new String(keyBytes)
	                .replace("-----BEGIN PUBLIC KEY-----", "")
	                .replace("-----END PUBLIC KEY-----", "")
	                .replaceAll("\\s", "");
	        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
	        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePublic(spec);
	    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
//	      Base64 b64 = new Base64();
//	      byte [] decoded = b64.decode(publicKeyPEM);
//
//	      X509EncodedKeySpec spec =
//	            new X509EncodedKeySpec(decoded);
//	      KeyFactory kf = KeyFactory.getInstance(algorithm);
//	      return kf.generatePublic(spec);
	     
	
	 ///////
//	  public static PublicKey loadPublicKey(String publicKeyPath) throws Exception {
//	        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyPath));
//	        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
//	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//	        return keyFactory.generatePublic(spec);
//	    }
    // Generate AES key
//    public static SecretKey generateAESKey() throws Exception {
//        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//        keyGen.init(256); // AES-256
//        return keyGen.generateKey();
//    }

//    private SecretKey generateAESKey() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(256);
//        return keyGenerator.generateKey();
//    }
//
//    private IvParameterSpec generateIv() {
//        byte[] iv = new byte[16];
//        return new IvParameterSpec(iv);
//    }
    
    
    
    
    // Encrypt data using AES
//    public static String encryptAES(String data, SecretKey key) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] encrypted = cipher.doFinal(data.getBytes());
//        return Base64.encodeBase64String(encrypted);
//    }
    
    
    
    // Decrypt data using AES
//    public static String decryptAES(String encryptedData, SecretKey key) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] decrypted = cipher.doFinal(Base64.decodeBase64(encryptedData));
//        return new String(decrypted);
//    }
//    // Encrypt data using RSA
//    public static String encryptRSA(String data, PublicKey key) throws Exception {
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] encrypted = cipher.doFinal(data.getBytes());
//        return Base64.encodeBase64String(encrypted);
//    }
//    // Decrypt data using RSA
//    public static String decryptRSA(String encryptedData, PrivateKey key) throws Exception {
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] decrypted = cipher.doFinal(Base64.decodeBase64(encryptedData));
//        return new String(decrypted);
//    }
}
