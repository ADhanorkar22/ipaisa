package com.Ipaisa.EncryptionDecryption;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionDecryption {
	
	  // Method to encrypt data
    public static EncData encryptData(String data, String publicKeyPath) throws Exception {
        // Generate a random AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();

        // Encrypt the data with AES
        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = aesCipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Encrypt the AES key with the public key
        PublicKey publicKey = getPublicKey(publicKeyPath);
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey = rsaCipher.doFinal(secretKey.getEncoded());

        // Encode the encrypted data and key as Base64
        String encodedData = Base64.getEncoder().encodeToString(encryptedData);
        String encodedKey = Base64.getEncoder().encodeToString(encryptedKey);

        return new EncData(encodedData, encodedKey);
    }

    // Method to decrypt data
    public static String decryptData(String base64EncryptedKey, String base64EncryptedData, String privateKeyPath) throws Exception {
        // Decode the encrypted AES key and data from Base64
        byte[] encryptedKey = Base64.getDecoder().decode(base64EncryptedKey);
        byte[] encryptedData = Base64.getDecoder().decode(base64EncryptedData);

        // Decrypt the AES key with the private key
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedKey = rsaCipher.doFinal(encryptedKey);

        // Decrypt the data with the AES key
        SecretKeySpec secretKeySpec = new SecretKeySpec(decryptedKey, "AES");
        Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedData = aesCipher.doFinal(encryptedData);

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    // Helper method to load a public key from a file
    private static PublicKey getPublicKey(String filename) throws Exception {
        byte[] keyBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filename));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    // Helper method to load a private key from a file
    private static PrivateKey getPrivateKey(String filename) throws Exception {
        byte[] keyBytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filename));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }
    public static String de(String encryptedData, String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);

        return new String(decryptedData);
    }

}
