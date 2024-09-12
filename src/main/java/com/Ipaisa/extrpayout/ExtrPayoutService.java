package com.Ipaisa.extrpayout;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Ipaisa.CustomExceptions.KeyResponse;
import com.Ipaisa.DeepLink.KeyGenrateDto;
import com.Ipaisa.Entitys.InstantPayBody;

@Service
public class ExtrPayoutService {

	private static final String SECRET_KEY = "EDSOM";
	private static final String UTF8 = "UTF-8";
	private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";

	public ResponseEntity<?> GenrateKey(InstantPayBody payload) {

		String dataToEncrypt = "name=" + payload.getName() + "&"

				+ "payeeaccountNumber=" + payload.getPayeeaccountNumber() + "&" + "bankIfsc=" + payload.getBankIfsc()
				+ "&" + "transferMode=" + payload.getTransferMode() + "&" + "transferAmount="
				+ payload.getTransferAmount();
		String Key = null;
		try {
			Key = encrypt(dataToEncrypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity(new KeyResponse(Key, true), HttpStatus.OK);
	}

	public ResponseEntity<?> decryptKey(String key) {
		 
		System.out.println("KEY-------"+key);
		String decryptedData=null;
		try {
			decryptedData = decrypt(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (ResponseEntity<?>) convertToJSON(decryptedData);
	}

	
	public Map<String, String> decryptToJSON(String encryptedMessage) throws Exception {
		System.out.println("000000000000  ---"+encryptedMessage);
		
		String decryptedData = decrypt(encryptedMessage);
		return convertToJSON(decryptedData);
	}

	public String encrypt(String message) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digestOfPassword = md.digest(SECRET_KEY.getBytes(UTF8));
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

	public String decrypt(String encryptedMessage) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digestOfPassword = md.digest(SECRET_KEY.getBytes(UTF8));
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

	public Map<String, String> convertToJSON(String decryptedData) {
		// Split the data into key-value pairs
		String[] pairs = decryptedData.split("&");
		Map<String, String> dataMap = new HashMap<>();

		for (String pair : pairs) {
			// Split each pair into key and value
			String[] keyValue = pair.split("=");
			if (keyValue.length == 2) {
				// Add the key-value pair to the map
				dataMap.put(keyValue[0], keyValue[1]);
			}
		}

		// Convert the map to a JSON object
		JSONObject jsonObject = new JSONObject(dataMap);

		// Return the JSON object as a string
		return dataMap;
	}

}
