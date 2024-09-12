package com.Ipaisa.DeepLink;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.Ipaisa.Entitys.ApiResponse;
import com.Ipaisa.Repository.DeeplinkRepository;
import com.Ipaisa.dto.DecryptPayloadWithKey;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
public class EncryptDecryptData {

	private static final String SECRET_KEY = "EDSOM";
	private static final String UTF8 = "UTF-8";
	private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";

	public ResponseEntity<?> GenrateKey(KeyGenrateDto dto) {
		try {
			// validate Number
			if (dto.getPhone() == null || dto.getPhone().isEmpty())
			{
				return new ResponseEntity<>(new ApiResponse("false", "Phone number is required."), HttpStatus.BAD_REQUEST);
			}
			 if (!dto.getPhone().matches("\\d{10}")) {
		            return new ResponseEntity<>( new ApiResponse("false", "Phone number must be numeric and 10 digits long.","Accept Only Numeric"), HttpStatus.BAD_REQUEST);
		        }
			if (dto.getAmount() <= 0) 
			{
				return new ResponseEntity<>("Amount must be greater than zero.", HttpStatus.BAD_REQUEST);
			}
			if (dto.getEmail() == null || dto.getEmail().isEmpty() && !dto.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) 
			{
				return new ResponseEntity<>(new ApiResponse("false", "Enter the Valid Email "), HttpStatus.BAD_REQUEST);
			}
			 if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
		            return new ResponseEntity<>(new ApiResponse("false", "Invalid email format.","Not Match"), HttpStatus.BAD_REQUEST);
		        }


			if (dto.getSurl() == null || dto.getSurl().isEmpty()) {
				return new ResponseEntity<>("Success URL (surl) is required.", HttpStatus.BAD_REQUEST);
			}
			if (dto.getTxnid() == null || dto.getTxnid().isEmpty()) {
				return new ResponseEntity<>(new ApiResponse("false", "Transaction ID (txnid) is required.") , HttpStatus.BAD_REQUEST);
			}

			
			
			
			// Construct the data string to be encrypted
			String dataToEncrypt = "phone=" + dto.getPhone() + "&" + "amount=" + dto.getAmount() + "&" + "email="
					+ dto.getEmail() + "&" + "furl=" + dto.getSurl() + "&" + "txnid=" + dto.getTxnid();

			// Encrypt the constructed data string
//		        String key = SECRET_KEY;
			String encryptedData = encrypt(dataToEncrypt);
			// Print the encrypted data to console (for debugging purposes)
			System.out.println("Encrypted Data: " + encryptedData);

			// Return the encrypted data
			return new ResponseEntity(new GenratedKey(true, encryptedData), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse("false", "An error occurred while generating the key.","Something Worng"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	public Map<String, String> decryptToJSON(String encryptedMessage) throws Exception {
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
