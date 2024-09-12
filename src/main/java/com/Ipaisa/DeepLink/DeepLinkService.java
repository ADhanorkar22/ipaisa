package com.Ipaisa.DeepLink;

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
@Transactional
public class DeepLinkService {

	@Autowired
	private DeeplinkRepository drepo;

	private static final String secretKey = "EDSOM";
	private static final String AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
	private static final String UTF8 = "UTF-8";
	private static final String UTF_8 = "UTF-8";
	
	 private static final String AES = "AES";
	    private static final String PASS_PHRASE = "EDSOM";
	    private static final int KEY_LENGTH = 16; // 128-bit key

//    private static final String SECRET_KEY = "1234567890123456";

//    public String buildGatewayUrl(KeyGenrateDto dto) throws Exception {
//    	String URL="https://ipaisa.co.in/deeplinkpage";
//        StringBuilder gatewayUrl = new StringBuilder("https://ipaisa.co.in/deeplinkpage?orderid=" + dto.getOrderId());
//        
//        if (dto.getPhone() != null) {
//            gatewayUrl.append("&phone=").append(URLEncoder.encode(dto.getPhone(), "UTF-8"));
//        }
//        if (dto.getAmount() > 0) {
//            gatewayUrl.append("&amount=").append(URLEncoder.encode(String.valueOf(dto.getAmount()), "UTF-8"));
//        }
//        if (dto.getOrderId() != null) {
//            gatewayUrl.append("&orderId=").append(URLEncoder.encode(dto.getOrderId(), "UTF-8"));
//        }
//        if (dto.getRedirectUrl() != null) {
//            gatewayUrl.append("&redirectUrl=").append(URLEncoder.encode(dto.getRedirectUrl(), "UTF-8"));
//        }
//
//        
//        String reactAppUrl = "https://www.yourapp.com/payment";
//        String redirectUrl = reactAppUrl + "?orderId=" + URLEncoder.encode(dto.getOrderId(), "UTF-8")
//                               + "&amount=" + URLEncoder.encode(String.valueOf(dto.getAmount()), "UTF-8")
//                               + "&phone=" + URLEncoder.encode(dto.getPhone(), "UTF-8");
//         
////        		response.sendRedirect(URL) ;
//        return redirectUrl;
//        		
//    }

	public ResponseEntity<String> GenrateKey(KeyGenrateDto dto) {
	    try {
	        // Validate each parameter
//	        if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
//	            return new ResponseEntity<>("Phone number is required.", HttpStatus.BAD_REQUEST);
//	        }
//	        if (dto.getAmount() <= 0) {
//	            return new ResponseEntity<>("Amount must be greater than zero.", HttpStatus.BAD_REQUEST);
//	        }
//	        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
//	            return new ResponseEntity<>("Email is required.", HttpStatus.BAD_REQUEST);
//	        }
//	        if (dto.getFurl() == null || dto.getFurl().isEmpty()) {
//	            return new ResponseEntity<>("Failure URL (furl) is required.", HttpStatus.BAD_REQUEST);
//	        }
//	        if (dto.getSurl() == null || dto.getSurl().isEmpty()) {
//	            return new ResponseEntity<>("Success URL (surl) is required.", HttpStatus.BAD_REQUEST);
//	        }
//	        if (dto.getTxnid() == null || dto.getTxnid().isEmpty()) {
//	            return new ResponseEntity<>("Transaction ID (txnid) is required.", HttpStatus.BAD_REQUEST);
//	        }
//	        if (dto.getFirstname() == null || dto.getFirstname().isEmpty()) {
//	            return new ResponseEntity<>("First name is required.", HttpStatus.BAD_REQUEST);
//	        }
//	        if (dto.getProductinfo() == null || dto.getProductinfo().isEmpty()) {
//	            return new ResponseEntity<>("Product information is required.", HttpStatus.BAD_REQUEST);
//	        }

	        // Construct the data string to be encrypted
	        String dataToEncrypt = "phone"+dto.getPhone() + "&" +
	                               "amount"+dto.getAmount() + "&" +
	                                "email"+dto.getEmail() + "&" +
//	                                 dto.getFurl() + "&" +
                                   "furl"+dto.getSurl() + "&" +
	                               "txnid"+dto.getTxnid() ;
//	                                dto.getFirstname() + "&" +
//	                                dto.getProductinfo();

	        // Encrypt the constructed data string
//	        String key = SECRET_KEY;
	        String encryptedData = encrypt(dataToEncrypt);
	        // Print the encrypted data to console (for debugging purposes)
	        System.out.println("Encrypted Data: " + encryptedData);
	        
	        // Return the encrypted data
	        return new ResponseEntity(new GenratedKey(true,encryptedData) , HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("An error occurred while generating the key.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
//	public Map<String, String> decryptData(DecryptPayloadWithKey obj) {
//		String data = obj.getData();
//		try {
//			String decryptedData = decrypt(data);
//			return convertToMap(decryptedData);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return Collections.emptyMap(); // Return an empty map in case of an error
//		}
//	}

	 public static SecretKey generateKey() throws Exception {
	        KeyGenerator keyGen = KeyGenerator.getInstance(AES);
	        keyGen.init(128); // AES-128
	        return keyGen.generateKey();
	    }
//
//	    public static String encrypt(String data, SecretKey key) throws Exception {
//	        Cipher cipher = Cipher.getInstance(AES);
//	        cipher.init(Cipher.ENCRYPT_MODE, key);
//	        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
//	        return Base64.getEncoder().encodeToString(encryptedBytes);
//	    }
	
	 public String encrypt(String message) throws Exception {
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
	 
	 
	 
	 
	
	 
//	public String decrypt(String encryptedMessage) throws Exception {
//		// Generate a fixed key and IV
//		byte[] digestOfPassword = generateFixedKey();
//		byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
//		byte[] iv = Arrays.copyOf(digestOfPassword, 16);
//		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
//		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
//
//		Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
//		cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
//
//		byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
//		byte[] decryptedBytes = cipher.doFinal(decodedBytes);
//
//		return new String(decryptedBytes, UTF_8);
//	}

	 public Map<String, String> decryptData(String data2) {
	        String data = data2;
	        try {
	            String decryptedData = decrypt(data);
	            return convertToMap(decryptedData);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return Collections.emptyMap(); // Return an empty map in case of an error
	        }
	    }

	    public String decrypt(String encryptedMessage) throws Exception {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] digestOfPassword = md.digest(secretKey.getBytes(UTF8));
	        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
	        byte[] iv = Arrays.copyOf(digestOfPassword, 16);
	        SecretKey key = new SecretKeySpec(keyBytes, "AES");
	        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5);
	        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
	        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
	        byte[] base64Bytes = Base64.getDecoder().decode(encryptedMessage.getBytes(UTF8));
	        byte[] plainTextBytes = cipher.doFinal(base64Bytes);
	        return new String(plainTextBytes, UTF8);
	    }

	    public Map<String, String> convertToMap(String decryptedData) {
	        Map<String, String> dataMap = new HashMap<>();

	        // Split the data into key-value pairs
	        String[] pairs = decryptedData.split("&");
	        for (String pair : pairs) {
	            // Split each pair into key and value
	            String[] keyValue = pair.split("=");
	            if (keyValue.length == 2) {
	                // Add the key-value pair to the map
	                dataMap.put(keyValue[0], keyValue[1]);
	            }
	        }

	        return dataMap;
	    }

	 public ResponseEntity<?> getAllTransactionsByPhone(String txnid) {
	        // Fetch the list of transactions by mobile number
//	        List<DeeplinkPayin> transactions = drepo.findByPhone(mobileno);
	        
	        DeeplinkPayin txn=this.drepo.findByTxnid(txnid);
	        
	        // Check if the list is empty
	        if (txn ==null) {
	            // Return a NOT FOUND response with a message
	            return new ResponseEntity<>(new ApiResponse("false","No transactions found for the provided TransactionId: "+ txnid,"No Content") , HttpStatus.NOT_FOUND);
	        }

	        // Return the list of transactions with OK status
	        return new ResponseEntity<>(txn, HttpStatus.OK);
	    }
	 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
	 
	 
	 
	 
	
}
